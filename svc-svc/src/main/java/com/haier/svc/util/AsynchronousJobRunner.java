package com.haier.svc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsynchronousJobRunner {
    private static final org.apache.log4j.Logger log = org.apache.log4j.LogManager
                                                         .getLogger(AsynchronousJobRunner.class);

    public static class Configuration {
        public static final int MODE_FIXED_DATA  = 0;
        public static final int MODE_STREAM_DATA = 1;

        public int              mode             = MODE_FIXED_DATA;
        public int              jobCount         = 1;
        public int              dataCount        = 1;
        public boolean          isBlock          = true;
        public boolean          batchDataPut     = false;
        public boolean          isMonitor        = true;           //是否启动监控线程
        public IMonitorCallback monitorCallback  = null;

        public static interface IMonitorCallback {
            public void finishJob(Object returnValue);

            public void finishedAllJobs();
        }

        public boolean checkParameters() {
            if (mode < 0) {
                return false;
            }
            if (jobCount < 1 || dataCount < 1) {
                return false;
            }
            return true;
        }
    }

    private static class DataTransThread implements Runnable {
        private ExecutorService  eDataService = null;
        private ExecutorService  eJobService  = null;
        private List<IJobData>   l_data;
        private List<IThreadJob> l_job;
        private Configuration    config;
        private List             data;

        public DataTransThread(ExecutorService eDataService, ExecutorService eJobService,
                               List<IJobData> l_data, List<IThreadJob> l_job, Configuration config) {
            this.eDataService = eDataService;
            this.eJobService = eJobService;
            this.l_data = l_data;
            this.l_job = l_job;
            this.config = config;
        }

        public DataTransThread(ExecutorService eJobService, List data, List<IThreadJob> l_job,
                               Configuration config) {
            this.eDataService = null;
            this.eJobService = eJobService;
            this.data = data;
            this.l_job = l_job;
            this.config = config;
        }

        @Override
        public void run() {
            int counter = 0;
            if (l_job.size() == 0)
                return;
            if (eDataService != null) {//动态数据获取
                while (!eDataService.isTerminated()) {
                    for (IJobData data : l_data) {
                        if (config.batchDataPut) {
                            if (counter >= l_job.size()) {
                                counter = 0;
                            }
                            l_job.get(counter).addData(data.popData());
                            counter++;
                        } else {
                            for (Object obj : data.popData()) {
                                if (counter >= l_job.size()) {
                                    counter = 0;
                                }
                                l_job.get(counter).addData(obj);
                                counter++;
                            }
                        }
                    }
                }
            } else {//固定数据
                for (Object obj : data) {
                    if (counter >= l_job.size()) {
                        counter = 0;
                    }
                    l_job.get(counter).addData(obj);
                    counter++;
                    //System.out.println(counter);
                }
            }

            log.info("Data Fetch Finished.");
            for (IThreadJob job : l_job) {
                job.dataFinish();
            }
        }

    }

    public void startJob(final Configuration config, Class<? extends IJobData> data,
                         Class<? extends IThreadJob> job) {
        if (config == null || !config.checkParameters()) {
            log.error("Configuration is not valid or null.");
            return;
        }

        ExecutorService eDataService = Executors.newFixedThreadPool(config.dataCount);
        final ExecutorService eJobService = Executors.newFixedThreadPool(config.jobCount);

        List<IJobData> l_data = new ArrayList<IJobData>();
        List<IThreadJob> l_job = new ArrayList<IThreadJob>();
        long beginTime = System.currentTimeMillis();
        try {
            for (int i = 0; i < config.dataCount; i++) {
                IJobData c_data = data.newInstance();
                c_data.setIndex(i);
                l_data.add(c_data);
                eDataService.submit(new Thread(c_data));
            }

            for (int i = 0; i < config.jobCount; i++) {
                IThreadJob c_job = job.newInstance();
                c_job.setMonitorCallback(config.monitorCallback);
                l_job.add(c_job);
                eJobService.submit(new Thread(c_job));
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
        eDataService.shutdown();
        eJobService.shutdown();

        //启动数据传输线程
        Thread t = new Thread(new DataTransThread(eDataService, eJobService, l_data, l_job, config));
        t.start();
        if (config.isBlock) {
            try {
                eDataService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                eJobService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                if (config.monitorCallback != null)
                    config.monitorCallback.finishedAllJobs();
            } catch (InterruptedException e) {
                log.error("", e);
            }
        } else if (config.isMonitor) {
            Thread monitor_thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        long beginTime = System.currentTimeMillis();
                        eJobService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                        long endTime = System.currentTimeMillis();
                        log.info("Sync T+2 Order Elapse Time:" + (endTime - beginTime));
                        //System.out.println("Sync T+2 Order Elapse Time:" + (endTime - beginTime));
                        if (config.monitorCallback != null)
                            config.monitorCallback.finishedAllJobs();
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }

                }
            });
            monitor_thread.start();
        }
        long endTime = System.currentTimeMillis();

        log.info("Job finished or is not block mode. Elapse Time:" + (endTime - beginTime));

    }

    public void startJob(final Configuration config, List data, Class<? extends IThreadJob> job) {
        if (config == null || !config.checkParameters()) {
            log.error("Configuration is not valid or null.");
            return;
        }

        final ExecutorService eJobService = Executors.newFixedThreadPool(config.jobCount);

        List<IThreadJob> l_job = new ArrayList<IThreadJob>();
        long beginTime = System.currentTimeMillis();
        try {

            for (int i = 0; i < config.jobCount; i++) {
                IThreadJob c_job = job.newInstance();
                c_job.setMonitorCallback(config.monitorCallback);
                l_job.add(c_job);
                eJobService.submit(new Thread(c_job));
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
        eJobService.shutdown();

        //启动数据传输线程
        Thread t = new Thread(new DataTransThread(eJobService, data, l_job, config));
        t.start();
        if (config.isBlock) {
            try {
                eJobService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                if (config.monitorCallback != null)
                    config.monitorCallback.finishedAllJobs();
            } catch (InterruptedException e) {
                log.error("", e);
            }
        } else if (config.isMonitor) {
            Thread monitor_thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        long beginTime = System.currentTimeMillis();
                        eJobService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
                        long endTime = System.currentTimeMillis();
                        log.info("Sync T+2 Order Elapse Time:" + (endTime - beginTime));
                        //System.out.println("Sync T+2 Order Elapse Time:" + (endTime - beginTime));

                        if (config.monitorCallback != null)
                            config.monitorCallback.finishedAllJobs();
                    } catch (InterruptedException e) {
                        log.error("", e);
                    }

                }
            });
            monitor_thread.start();
        }
        long endTime = System.currentTimeMillis();

        log.info("Job finished or is not block mode. Elapse Time:" + (endTime - beginTime));

    }
}

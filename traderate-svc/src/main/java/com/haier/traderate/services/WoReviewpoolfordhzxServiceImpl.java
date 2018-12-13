package com.haier.traderate.services;


import com.haier.common.BusinessException;
import com.haier.common.PagerInfo;
import com.haier.common.ServiceResult;
import com.haier.shop.model.Reviewpoolfordhzx;
import com.haier.shop.service.ReviewMiddleService;
import com.haier.shop.service.WoReviewpoolfordhzxDataService;
import com.haier.shop.util.ReviewConstants;
import com.haier.traderate.service.WoReviewMiddleService;
import com.haier.traderate.service.WoReviewpoolfordhzxService;
import com.haier.traderate.services.ws.DemoWs;
import com.haier.traderate.services.ws.DemoWs_Service;
import com.haier.traderate.services.ws.EhaierOrder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/7/11 0011.
 */
@Service
public class WoReviewpoolfordhzxServiceImpl implements WoReviewpoolfordhzxService {
    private static Logger log = LogManager.getLogger(WoReviewpoolfordhzxServiceImpl.class);
    @Resource
    private WoReviewpoolfordhzxDataService woReviewpoolfordhzxDataService;
    @Resource
    private ReviewMiddleService reviewMiddleService;
/*    @Resource
    private ReviewWorkOrderModel reviewWorkOrderModel;*/


    @Override
    public ServiceResult<Boolean> insertReviewpoolDhzx(Reviewpoolfordhzx reviewpoolfordhzx) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
           /* int   count =woReviewpoolfordhzxDataService.countReviewpoolfordhzx(reviewpoolfordhzx);
            if(count==0) {*/
                woReviewpoolfordhzxDataService.insertReviewpoolDhzx(reviewpoolfordhzx);
          /*  }else{
                woReviewpoolfordhzxDataService.updateReviewpoolDhzxForProblemMessage(reviewpoolfordhzx);
            }*/
            serviceResult.setResult(true);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("[ReviewpoolfordhzxServiceImpl][insertReviewpoolDhzx]:添加400工单失败,信息编号"+reviewpoolfordhzx.getMessageNum(), e);
        }
        return  serviceResult;

    }

    @Override
    public ServiceResult<Boolean> updateReviewpoolDhzxForProblemMessage(Reviewpoolfordhzx reviewpoolfordhzx) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
            int   count =woReviewpoolfordhzxDataService.countReviewpoolfordhzx(reviewpoolfordhzx);
            if(count==0) {
                woReviewpoolfordhzxDataService.insertReviewpoolDhzx(reviewpoolfordhzx);
           }else{
                woReviewpoolfordhzxDataService.updateReviewpoolDhzxForProblemMessage(reviewpoolfordhzx);
            }
            serviceResult.setResult(true);
        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            log.error("[ReviewpoolfordhzxServiceImpl][updateReviewpoolDhzxForProblemMessage]修改问题描述的追加:,信息编号"+reviewpoolfordhzx.getMessageNum()+"----工单号"+reviewpoolfordhzx.getReviewPoolId(), e);
        }
        return  serviceResult;

    }

    @Override
    public ServiceResult<List<Reviewpoolfordhzx>> pageReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx, PagerInfo pager) {
        ServiceResult<List<Reviewpoolfordhzx>> serviceResult = new ServiceResult<List<Reviewpoolfordhzx>>();
        try {

            List<Reviewpoolfordhzx> r= woReviewpoolfordhzxDataService.page(reviewpoolfordhzx,pager);
            int total = woReviewpoolfordhzxDataService.pageCount(reviewpoolfordhzx);
            PagerInfo pagerInfo = new PagerInfo();
            pagerInfo.setRowsCount(total);
            serviceResult.setResult(r);
            serviceResult.setPager(pagerInfo);


        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewWorkOrderServiceImpl][page]取分页查询异常:", e);
        }
        return serviceResult;

    }

    @Override
    public ServiceResult<Reviewpoolfordhzx> getQueryReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx) {
        ServiceResult<Reviewpoolfordhzx> serviceResult = new ServiceResult<Reviewpoolfordhzx>();
        Reviewpoolfordhzx r= new Reviewpoolfordhzx();
        try {
          r= woReviewpoolfordhzxDataService.getQueryReviewpoolfordhzx(reviewpoolfordhzx);

            serviceResult.setResult(r);

            serviceResult.setSuccess(true);
            serviceResult.setMessage("操作成功");


        } catch (Exception e) {
            serviceResult.setMessage("获取工单号过多,存在重复工单"+r.getMessageNum());
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewWorkOrderServiceImpl][query]查询单条400数据:", e);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateReviewpoolfordhzx(Reviewpoolfordhzx reviewpoolfordhzx) {


        log.debug("ReviewWorkOrderServiceImpl.updeWorkOrder 修改工单入参400：" + "updateReviewpoolfordhzx" + reviewpoolfordhzx.getId());
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {

            EhaierOrder e = new EhaierOrder();


            if(null!=reviewpoolfordhzx.getProblem()&&!"".equals(reviewpoolfordhzx.getProblem())) {
                reviewMiddleService.insertReviewpoolfordhzxMidd(reviewpoolfordhzx);
                    e.setHandleResult(reviewpoolfordhzx.getProblem());


            }if(null!=reviewpoolfordhzx.getBackContext3()&&!"".equals(reviewpoolfordhzx.getBackContext3())){
                reviewMiddleService.insertReviewpoolfordhzxResult(reviewpoolfordhzx);
                e.setFinalResult(reviewpoolfordhzx.getBackContext3());
            }


            Reviewpoolfordhzx rs=woReviewpoolfordhzxDataService.findReviewByIds(reviewpoolfordhzx);
            if(rs.getType()!=null&&!"".equals(rs.getType())&&rs.getType().equals("1")){

                //        	URL url = this.getClass().getResource(
                //wsdlLocation + "/QueryWAOrderBillFromIHS.wsdl");新加用以区分测试环境
                //todo 放开注释！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！

/*                DemoWs_Service factory = new DemoWs_Service();
                DemoWs Soap = factory.getDemoWsImplPort();
                e.setKey("fe334f3056c9da2bbe94574b795184d8");
                e.setHandlePerson(reviewpoolfordhzx.getUserName());
                e.setOrderId(rs.getMessageNum());
                e.setSourceSystem("ehaier");
                e = Soap.ehaierOrderShare(e);
                if(!e.isSuccess()){
                    log.error("400接收反馈结果失败了:"+e.getOrderId()+"---"+e.getMessage());
                }*/
            }
             woReviewpoolfordhzxDataService.updateReviewpoolDhzx(reviewpoolfordhzx);
            serviceResult.setResult(true);
            serviceResult.setSuccess(true);

        } catch (BusinessException be) {
            serviceResult.setSuccess(false);
            serviceResult.setMessage(be.getMessage());
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("[ReviewWorkOrderServiceImpl][updateReviewpoolfordhzx]修改工单400异常:", e);
        }
        log.debug("ReviewWorkOrderServiceImpl.updateReviewpoolfordhzx 修改工单400完成");
        return serviceResult;
    }

    @Override
    public ServiceResult<Boolean> updateReviewpoolfordhzxTrip(Reviewpoolfordhzx reviewpoolfordhzx) {
        ServiceResult<Boolean> serviceResult = new ServiceResult<Boolean>();
        try {
         Reviewpoolfordhzx rs=woReviewpoolfordhzxDataService.getQueryReviewpoolfordhzx(reviewpoolfordhzx);
         reviewpoolfordhzx.setId(rs.getId());
         woReviewpoolfordhzxDataService.updateReviewpoolDhzx(reviewpoolfordhzx);
        } catch (Exception e) {
            serviceResult.setError(ReviewConstants.SERVICE_RESULT_CODE_SYSERROR, "服务异常，请联系系统管理员。");
            log.error("400跳闸失败:", e);
        }
        return null;
    }
}

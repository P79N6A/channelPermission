package com.haier.afterSale.util;

import java.text.SimpleDateFormat;
import java.util.NoSuchElementException;

import javax.annotation.Resource;

import com.haier.common.BusinessException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class SequenceModel implements InitializingBean{

	private Logger logger= LoggerFactory.getLogger(this.getClass());
	public static final String MAC = "MAC";
	public static final int TIMESTAMP_BIT_NUM = 41;
	public static final int SEQUENCE_BIT_NUM = 12;
	public static final int MAC_BIT_NUM = 3;

	private long BEGIN_TIMESTAMP = 0L;
	private long TIMESTAMP_MAX_VALUE = 0L;
	private int SEQUENCE_MAX_VALUE = 0;
	private int MAC_MAX_VALUE=0;



	private Integer mac;

	private long timestamp=0L;

	private int sequence=0;




	public Long getSnowFlake(){
		long num = 0L;
		synchronized (mac) {
			long currentTimeMillis = System.currentTimeMillis();
			if(timestamp > currentTimeMillis){
				throw new BusinessException("服务器时间异常！");
			}
			if (timestamp == currentTimeMillis) {
				sequence = (sequence+1) & SEQUENCE_MAX_VALUE;
				if (sequence==0) {
					timestamp = waitNextTimestamp();
				}
			} else {
				timestamp = currentTimeMillis;
				sequence = 0;
			}
		}
		return num
				| (((timestamp-BEGIN_TIMESTAMP)&TIMESTAMP_MAX_VALUE) << (MAC_BIT_NUM + SEQUENCE_BIT_NUM))
				| ((this.mac & MAC_MAX_VALUE) << SEQUENCE_BIT_NUM)
				| (sequence & SEQUENCE_MAX_VALUE);
	}

	private long waitNextTimestamp() {
		long currentTimeMillis=0L;
		do{
			currentTimeMillis=System.currentTimeMillis();
		}while(currentTimeMillis <= timestamp);
		return currentTimeMillis;
	}

	/****
	 * 从snowFlake号中获取时间戳
	 * @param snowFlake
	 * @return
	 */
	public Long getTimestampFromSnowFlake(long snowFlake){
		return (snowFlake>>(MAC_BIT_NUM+SEQUENCE_BIT_NUM))+BEGIN_TIMESTAMP;
	}

	/****
	 * 获取流水号
	 * @param snowFlake
	 * @return
	 */
	public Integer getSequenceFromSnowFlake(long snowFlake) {
		return (int)(snowFlake&SEQUENCE_MAX_VALUE);
	}

	/****
	 * 获取机器号
	 * @param snowFlake
	 * @return
	 */
	public Integer getMacFromSnowFlake(long snowFlake){
		return (int)(snowFlake >> SEQUENCE_BIT_NUM)&MAC_MAX_VALUE;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		BEGIN_TIMESTAMP=new SimpleDateFormat("yyyyMMddHHmmss").parse("20000101000000").getTime();
		timestamp=System.currentTimeMillis();
	    mac=0;
		TIMESTAMP_MAX_VALUE=Long.parseLong(StringUtils.leftPad("",TIMESTAMP_BIT_NUM,'1'),2);
		logger.info(TIMESTAMP_BIT_NUM+"bit位时间戳最大值:"+TIMESTAMP_MAX_VALUE);
		SEQUENCE_MAX_VALUE=Integer.valueOf(StringUtils.leftPad("",SEQUENCE_BIT_NUM,'1'),2);
		logger.info(SEQUENCE_BIT_NUM+"bit位流水号最大值:"+SEQUENCE_MAX_VALUE);
		MAC_MAX_VALUE=Integer.valueOf(StringUtils.leftPad("",MAC_BIT_NUM,'1'),2);
		logger.info(MAC_BIT_NUM+"bit机器号最大值:"+MAC_MAX_VALUE);
		logger.info("MAC:"+(this.mac&MAC_MAX_VALUE));
	}
}

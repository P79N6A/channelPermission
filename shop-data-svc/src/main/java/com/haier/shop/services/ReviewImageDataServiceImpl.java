package com.haier.shop.services;

import com.haier.shop.dao.workorder.ReviewImageDao;
import com.haier.shop.model.ReviewImage;
import com.haier.shop.service.ReviewImageDataService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 图片操作
 * 
 * @Filename: ReviewImageModel.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 *
 */
@Service
public class ReviewImageDataServiceImpl implements ReviewImageDataService {
	@Resource
	private ReviewImageDao reviewImageDao;

	/**
	 * 读取图片，用于下载
	 * 
	 * @return
	 */
	public ReviewImage findReviewImageById(String id) {
		return reviewImageDao.findReviewImageById(id);
	}

	/**
	 * 查看是否存在图片
	 * 
	 * @return
	 */
	public int selectCount(String reviewId,String info) {
		return reviewImageDao.selectCount(reviewId,info);
	}

	/**
	 * 查找多张图片
	 * 
	 * @param reviewId
	 * @return
	 */
	public List<ReviewImage> findReviewImgsById(String reviewId) {
		return reviewImageDao.findReviewImgsById(reviewId);
	}

	/**
	 * 根据Id删除图片
	 * 
	 * @param id
	 * @return
	 */
	public int delById(String id) {
		return reviewImageDao.delById(id);
	}

	/**
	 * 删除指定日期之前的图片
	 * @param time
	 * @return
	 */
	public int deleteByPoolColse(String time){
		return reviewImageDao.deleteByPoolColse(time);
	}

}

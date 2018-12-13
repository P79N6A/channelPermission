package com.haier.svc.api.controller.workorder;

import com.haier.common.ServiceResult;
import com.haier.common.util.JsonUtil;
import com.haier.shop.model.ReviewImage;
import com.haier.svc.api.controller.util.Base64Image;
import com.haier.svc.api.controller.util.WebReviewConstants;
import com.haier.traderate.service.ReviewImageService;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/image", produces = "text/html;charset=UTF-8")
public class WebReviewImageController {
	private static Logger log = LogManager.getLogger(WebReviewImageController.class);
	@Resource
	private ReviewImageService reviewImageService;

	// private static org.slf4j.Logger psilog =
	// LoggerFactory.getLogger("psilogger");
	/**
	 * 设置响应头
	 * 
	 * @param response
	 * @param imagePostfix
	 *            图片后缀扩展名
	 */
	public void setResponseHeader(HttpServletResponse response, String imagePostfix) {
		try {
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + java.net.URLEncoder.encode("下载", "UTF-8") + imagePostfix);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			log.error("Exception:", ex);
		}
	}

	/**
	 * 通过Id下载图片
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "/downloadimage.ajax", method = RequestMethod.GET)
	public void downImage(@RequestParam(value = "id", required = false) String id, HttpServletRequest request,
			HttpServletResponse response) {
		ServiceResult<ReviewImage> result = reviewImageService.findReviewImageById(id);
		if (result.getSuccess()) {
			OutputStream out = null;
			try {
				out = response.getOutputStream();
				String imageStr = result.getResult().getInformation();
				String image = imageStr.substring(imageStr.indexOf("base64,") + 7, imageStr.length());
				String imagePostfix = imageStr.substring(imageStr.indexOf("/") + 1, imageStr.indexOf(";"));
				setResponseHeader(response, "." + imagePostfix);
				Base64Image.GenerateImage(image, out);
			} catch (IOException e) {
				log.error("Exception:", e);
			}
		}
	}

	/**
	 * 通过工单ID查看图片是否存在
	 * 
	 * @param reviewId
	 * @return
	 */
	@RequestMapping(value = { "/findreviewimagebyid.ajax" }, method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String findReviewImageById(@RequestParam(value = "reviewId", required = false) String reviewId,
			HttpServletRequest request) {
		ServiceResult<String> serviceResult = new ServiceResult<String>();
		try {

			ServiceResult<Boolean> result = reviewImageService.findImageFlg(reviewId);
			if (result.getSuccess()) {
				return JsonUtil.toJson(result);
			} else {
				return result.getMessage();
			}
		} catch (Exception e) {
			serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
			log.error(WebReviewConstants.SYSTEM_ERROR, e);
			return JsonUtil.toJson(serviceResult);
		}
	}

	/**
	 * 删除图片
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = { "/delById.ajax" }, method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String delById(@RequestParam(value = "id", required = true) String id, HttpServletRequest request) {
		ServiceResult<String> serviceResult = new ServiceResult<String>();
		try {
			ServiceResult<Boolean> result = reviewImageService.delById(id);
			return JsonUtil.toJson(result);

		} catch (Exception e) {
			serviceResult.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
			log.error(WebReviewConstants.SYSTEM_ERROR, e);
			return JsonUtil.toJson(serviceResult);
		}
	}

	/**
	 * 通过工单号查看图片
	 * 
	 * @param reviewId
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = { "/getimage.ajax" }, method = { RequestMethod.POST, RequestMethod.GET })
	public String lookImage(@RequestParam(value = "reviewId", required = false) String reviewId,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceResult<String> r = new ServiceResult<String>();
		try {

			ServiceResult<ReviewImage> result = reviewImageService.findReviewImageById(reviewId);
			if (result.getSuccess() && result.getResult() != null) {
				r.setResult(result.getResult().getInformation());
			} else {
				r.setSuccess(false);
				r.setMessage("未获取到对应图片！");
			}
			return JsonUtil.toJson(r);
		} catch (Exception e) {
			r.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
			log.error(WebReviewConstants.SYSTEM_ERROR, e);
			return JsonUtil.toJson(r);
		}

	}

	/**
	 * 通过工单号查询多张图片
	 * 
	 * @param reviewId
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findimgs.ajax" }, method = { RequestMethod.POST, RequestMethod.GET })
	public String findImgs(@RequestParam(value = "reviewId", required = false) String reviewId,
			HttpServletRequest request, HttpServletResponse response) {

		ServiceResult<List<ReviewImage>> result = new ServiceResult<List<ReviewImage>>();
		try {

			result = reviewImageService.findReviewImgsById(reviewId);

			return JsonUtil.toJson(result);
		} catch (Exception e) {
			result.setError(WebReviewConstants.SYSTEM_ERROR_CODE, WebReviewConstants.SYSTEM_ERROR);
			log.error(WebReviewConstants.SYSTEM_ERROR, e);
			return JsonUtil.toJson(result);
		}
	}
}

package com.haier.svc.api.controller.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public final class ExportExcelUtil {
		public static void exportCommon(InputStream is, String fileName, HttpServletResponse res) throws IOException{
			res.reset();
	        res.setContentType("application/vnd.ms-excel;charset=utf-8");
	        res.setHeader("Content-Disposition", "attachment;filename="
	            + new String((fileName + ".xls").getBytes("gb2312"), "iso-8859-1"));
	        ServletOutputStream out = res.getOutputStream();
	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;
	   
	        try {
	          bis = new BufferedInputStream(is);
	          bos = new BufferedOutputStream(out);
	          byte[] buff = new byte[2048];
	          int bytesRead;
	          // Simple read/write loop.
	          while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	            bos.write(buff, 0, bytesRead);
	          }
	        } catch (Exception e) {
	          // TODO: handle exception
	          e.printStackTrace();
	        } finally {
	          if (bis != null)
	            bis.close();
	          if (bos != null)
	            bos.close();
	        }
			
		}

}

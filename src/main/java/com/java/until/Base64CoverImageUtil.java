package com.java.until;

import com.java.until.ftp.UpUtils;

import java.io.*;
import java.util.Base64;
import java.util.Base64.Decoder;


public class Base64CoverImageUtil {

	public static String GenerateImage(String ftpQmUrl, String imgStr) {
		try {
			// 对字节数组字符串进行Base64解码并生成图片
			if (StringUtil.isNull(imgStr) || imgStr.contains("QmImage")) // 图像数据为空
				return StringUtil.isNull(imgStr) ? "" : imgStr;
			
			if(!imgStr.contains(";base64")) {
				return "false";
			}
			//去除前部分的标识如：data:image/png;base64,
			String[] imgArr = imgStr.split(",");
			String imagBese64 = imgArr[1];
			
			//BASE64Decoder decoder = new BASE64Decoder();
			Decoder decoder = Base64.getDecoder();
			// Base64解码
			byte[] b = decoder.decode(imagBese64);
			String jpgName = UUIDUtil.getUUID() + ".jpg";
			//ftp连续上传时，出现无反应现象
			InputStream in = new ByteArrayInputStream(b);
			UpUtils.upload(in, "/regImage/", jpgName);
			
			/*String path = "D:/ftp/regImage/" + jpgName;
			File file = new File(path);
            OutputStream out = new FileOutputStream(file);
            out.write(b);
            out.flush();
            out.close();*/
			return "/regImage/" + jpgName;
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
	}

}

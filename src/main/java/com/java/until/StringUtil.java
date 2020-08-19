package com.java.until;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.regex.Pattern;

public class StringUtil extends org.apache.commons.lang3.StringUtils {
	

	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * 转换为String类型
	 */
	public static String toString(Object val) {
		if (val == null) {
			return "";
		}
		return val.toString();
	}




	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0.00;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0.00;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}

	public static Boolean toBoolean(Object obj) {
		Boolean Bl = new Boolean(toString(obj));
		boolean bl = Bl.booleanValue();
		return bl;
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		// return s.substring(0, 1).toUpperCase() + s.substring(1);
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}



	/**
	 * 生成随机数字验证码
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length) { // length表示生成字符串的长度
		String base = "6482570319";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/***
	 * 首字母转换成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCase(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	/***
	 * 首字母转成小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str) {
		return str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}

	/***
	 * 判断子字符串是不是为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		return null == str || "".equals(str);
	}
	public static boolean isNull(Object object) {
		return null == object || "".equals(object);
	}

	/**
	 * 去掉 字符串尾部的/
	 * 
	 * @param str
	 * @return 不以/结尾的新字符创
	 */
	public static String dropStr(String str) {
		String teml = str.substring(str.length() - 1, str.length());
		if ("/".equals(teml)) {// 是以/结尾 就
			return dropStr(str.substring(0, str.length() - 1));
		} else {
			return str;
		}

	}

	/**
	 * 获取默认字符串
	 * 
	 * @param obj
	 * @param defaultStr
	 * @return
	 */
	public static String getString(Object obj, String defaultStr) {
		if (obj != null && !"".equals(obj)) {
			return String.valueOf(obj);
		}
		return defaultStr;
	}

	/**
	 * 
	 * <li>描述:通配符验证</li>
	 * <li>参数:@param pattern
	 * <li>参数:@param str
	 * <li>参数:@return</li>
	 * <li>返回类型:boolean</li>
	 * <li>最后更新作者:gaoqs</li>
	 */
	public static boolean wildMatch(String pattern, String str) {
		try {
			
			pattern = pattern.replace("*", ".*").toUpperCase()+"$";
			str = str.toUpperCase();
			return Pattern.matches(pattern,str);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 将null，NULL字符串转换为空字符
	 * @param str
	 * @return
	 * @author siyq
	 * @Date 2018年3月21日 上午10:50:13
	 */
	public static String nullToBlankStr(String str){
		if(str==null||str.equals("null")||str.equals("NULL")){
			str = "";
		}
		return str;
	}

}

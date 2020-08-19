package com.java.moudle.common.utils.properties;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 读取Properties文件 File: bltwsProperties.java
 */
public final class PropertiesUtil {

	protected static Properties dictProp;
	protected static Properties sysProp;

	static {
		dictProp = new Properties();
		//sysProp = new Properties();
		try {
			dictProp.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("dict.properties"));
			//sysProp.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sys.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void putDict(String key, String value) {

		// 获取文件的位置
		String filePath = Thread.currentThread().getContextClassLoader().getResource("dict.properties").getFile();

		// System.out.println(filePath);
		try {
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			dictProp.setProperty(key, value);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			dictProp.store(fos, "Update '" + key + "' value");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getDict(String key) {
		Object obj = dictProp.get(key);
		if (null == obj)
			return "";
		return obj.toString();
	}

	public static void putSys(String key, String value) {

		// 获取文件的位置
		String filePath = Thread.currentThread().getContextClassLoader().getResource("sys.properties")
				.getFile();

		// System.out.println(filePath);
		try {
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			sysProp.setProperty(key, value);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			sysProp.store(fos, "Update '" + key + "' value");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getSys(String key) {
		Object obj = sysProp.get(key); 
		if (null == obj)
			return "";
		return obj.toString();
	}


	/**
	 * 私有构造方法，不需要创建对象
	 */
	private PropertiesUtil() {
	}

	
	public static void main(String[] args) {
		String a = PropertiesUtil.getSys("super_username");
		System.out.println(a);
	}
}
package com.java.until;

import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static String getIdentCode() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++)
		{
			result += random.nextInt(10);
		}
		return result;
	}
}

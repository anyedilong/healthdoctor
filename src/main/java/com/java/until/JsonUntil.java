package com.java.until;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;

public class JsonUntil {
	
	private static final FastJsonConfig jsonConfigSnakeCase;
	 /**
	  * class 序列化为下划线
	  */
	 private static final SerializeConfig serializeConfigSnakeCase;
	 // 一般格式
	 private static final SerializeConfig serializeConfigCamelCase;

	 /**
	  * 下划线参数转驼峰class
	  */
	 private static final ParserConfig parserConfigSnakeCase;
	 // 一般格式
	 private static final ParserConfig parserConfigCamelCase;
	 static {
	  jsonConfigSnakeCase = new FastJsonConfig();
	  jsonConfigSnakeCase.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);
	  parserConfigSnakeCase = new ParserConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题
	  parserConfigSnakeCase.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

	  serializeConfigSnakeCase = new SerializeConfig(); // 生产环境中，config要做singleton处理，要不然会存在性能问题
	  serializeConfigSnakeCase.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
	  jsonConfigSnakeCase.setSerializeConfig(serializeConfigSnakeCase);
	//  jsonConfigSnakeCase.setParserConfig(parserConfigSnakeCase);
	  jsonConfigSnakeCase.setSerializerFeatures(SerializerFeature.PrettyFormat);

	  serializeConfigCamelCase = new SerializeConfig();
	  serializeConfigCamelCase.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;
	  parserConfigCamelCase = new ParserConfig();
	  parserConfigCamelCase.propertyNamingStrategy = PropertyNamingStrategy.CamelCase;

	 }

	 public static FastJsonConfig getJsonconfigsnakecase() {
	  return jsonConfigSnakeCase;
	 }

	 public static SerializeConfig getSerializeconfigsnakecase() {
	  return serializeConfigSnakeCase;
	 }

	 public static ParserConfig getParserconfigsnakecase() {
	  return parserConfigSnakeCase;
	 }

	 public static SerializeConfig getSerializeconfigcamelcase() {
	  return serializeConfigCamelCase;
	 }

	 public static ParserConfig getParserconfigcamelcase() {
	  return parserConfigCamelCase;
	 }
}

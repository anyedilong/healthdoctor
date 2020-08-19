package com.java.moudle.common.domain;

import java.io.Serializable;

/**
 * 
 * @ClassName ResponseStatus
 * @Description 服务器相应状态
 * @author sen
 * @Date 2016年11月21日 下午3:22:09
 * @version 1.0.0
 */
public class ResponseStatus implements Serializable {

	/**
	 * @Field @serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;

	private int code = 200;
	private String message = "OK";

	public ResponseStatus() {

	}

	public ResponseStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 200:ok
	 */
	public final static ResponseStatus HTTP_OK = new ResponseStatus(200, "OK");

	/**
	 * 错误的请求 400:Bad Request
	 */
	public final static ResponseStatus HTTP_BAD_REQUEST = new ResponseStatus(400, "错误的请求");
	/**
	 * 身份验证错误<br/>
	 * 401:Unauthorized
	 */
	public final static ResponseStatus HTTP_UNAUTHORIZED = new ResponseStatus(401, "身份验证错误");
	/**
	 * 服务器拒绝请求<br/>
	 * 402: Payment Required
	 */
	public final static ResponseStatus HTTP_NOTAUTH = new ResponseStatus(402, "服务器拒绝请求");
	/**
	 * 用户冻结<br/>
	 * 403: Payment Required
	 */
	public final static ResponseStatus HTTP_FREEZE = new ResponseStatus(403, "用户冻结");
	/**
	 * 请求过时<br/>
	 * 408： Request Timeout
	 */
	public final static ResponseStatus HTTP_REQUEST_TIMEOUT = new ResponseStatus(408, "请求过时");
	/**
	 * 服务器错误<br/>
	 * 500:
	 */
	public final static ResponseStatus HTTP_SERVER_ERROR = new ResponseStatus(500, "服务器错误");

	/**
	 * 设备身份认证错误
	 */
	public final static ResponseStatus HTTP_UNDEVICEAUTO = new ResponseStatus(701, "服务器错误");
	public static final ResponseStatus HTTP_DEVICENOTFIND = new ResponseStatus(702, "设备不存在");
	public static final ResponseStatus HTTP_DEVICEFREEZE = new ResponseStatus(703, "设备已冻结");
	public static final ResponseStatus HTTP_DEVICENOTBIND = new ResponseStatus(704, "设备未入网注册");
	public static final ResponseStatus HTTP_DEVICENOTACTIVATE = new ResponseStatus(705, "设备未激活，需激活后使用");
	public static final ResponseStatus HTTP_DEVICEERROR=new ResponseStatus(706,"设备已删除");
	public static final ResponseStatus HTTP_USERERROR=new ResponseStatus(707,"用户无法使用该设备");
	public static final ResponseStatus  HTTP_USERSTATUS=new ResponseStatus(708,"用户已冻结");
	

}

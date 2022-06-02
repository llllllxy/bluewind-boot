package com.bluewind.boot.common.base;

import java.io.Serializable;

/**
 * @author liuxingyu01
 * @date 2020-03-18-13:45
 * @description Json统一返回消息类
 */
public class BaseResult implements Serializable {
	private static final long serialVersionUID = -1491499610244557029L;
	public static int CODE_SUCCESS = 0; // 成功
	public static int CODE_FAILURED = -1; // 失败
	public static int CODE_LIMIT = -2; // 访问过快，限制
	public static int CODE_KICKOUT = -3; // 账号在别处登陆，被顶下线
	public static int CODE_NO_PERMISSION = 403; // 无权限访问
	public static int UNAUTHORIZED = 401; // 未登录或会话已失效


	public static String[] NOOP = new String[]{};

	/**
	 * 处理状态：0: 成功
	 */
	private int code;

	/**
	 * 返回信息
	 */
	private String message;

	/**
	 * 返回数据
	 */
	private Object data;

	public BaseResult(int code, String message, Object data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	/**
	 * 处理成功，并返回数据
	 * 
	 * @param data 数据对象
	 * @return data
	 */
	public static BaseResult success(Object data) {
		return new BaseResult(CODE_SUCCESS, "操作成功", data);
	}
	
	/**
	 * 处理成功
	 * 
	 * @return data
	 */
	public static BaseResult success() {
		return new BaseResult(CODE_SUCCESS, "操作成功", NOOP);
	}

	/**
	 * 处理成功
	 * 
	 * @param message 消息
	 * @return data
	 */
	public static BaseResult success(String message) {
		return new BaseResult(CODE_SUCCESS, message, NOOP);
	}

	/**
	 * 处理成功
	 * 
	 * @param message 消息
	 * @param data 数据对象
	 * @return data
	 */
	public static BaseResult success(String message, Object data) {
		return new BaseResult(CODE_SUCCESS, message, data);
	}

	/**
	 * 处理失败，并返回数据（一般为错误信息）
	 * 
	 * @param code 错误代码
	 * @param message 消息
	 * @return data
	 */
	public static BaseResult failure(int code, String message) {
		return new BaseResult(code, message, NOOP);
	}

	/**
	 * 处理失败
	 * 
	 * @param message 消息
	 * @return data
	 */
	public static BaseResult failure(String message) {
		return failure(CODE_FAILURED, message);
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
	
	
}

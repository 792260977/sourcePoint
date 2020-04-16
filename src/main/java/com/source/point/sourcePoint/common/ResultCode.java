package com.source.point.sourcePoint.common;

public enum ResultCode implements RespCode {
	/**
	 * 成功
	 */
	SUCCESS("000", "success"),
	/**
	 * 失败
	 */
	FAIL("001", "fail"),
	/**
	 * 参数异常
	 */
	PARAM_ERROR("002", "param error"),
	
	/**
	 * 偷懒异常
	 */
	UNKNOWN_ERROR("999", "自己定义内容"),;

	private String code;

	private String msg;

	ResultCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}

}
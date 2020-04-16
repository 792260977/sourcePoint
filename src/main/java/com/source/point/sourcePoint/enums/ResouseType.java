package com.source.point.sourcePoint.enums;

import org.apache.commons.lang3.StringUtils;

import com.source.point.sourcePoint.common.ResultCode;
import com.source.point.sourcePoint.common.ServiceException;

public enum ResouseType {

	TYPE_1("1", "应用程序"), TYPE_2("2", "程序功能/菜单"), TYPE_3("3", "应用服务/外部接口"),;

	private String type;

	private String msg;

	ResouseType(String type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public String getMsg() {
		return msg;
	}
	
	public static String getDescByValue(String type) {
		ServiceException ex = new ServiceException(ResultCode.UNKNOWN_ERROR, "资源类型不存在");
		if (StringUtils.isBlank(type)) {
			throw ex;
		}
		for (ResouseType e : values()) {
			if (e.getType().equalsIgnoreCase(type)) {
				return e.getMsg();
			}
		}
		throw ex;
	}
}
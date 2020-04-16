package com.source.point.sourcePoint.utils;

import com.source.point.sourcePoint.common.RespCode;
import com.source.point.sourcePoint.common.RespRlt;
import com.source.point.sourcePoint.common.ResultCode;

public class RespUtil {

	/***
	 * 返回成功，结果为空
	 *
	 */
	public static <T> RespRlt<T> success() {
		RespRlt<T> result = new RespRlt<>(ResultCode.SUCCESS);
		return result;
	}

	/**
	 * 返回成功
	 *
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> RespRlt<T> success(T data) {
		RespRlt<T> result = new RespRlt<>(ResultCode.SUCCESS);
		result.setData(data);
		return result;
	}

	/**
	 * 返回失败
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> RespRlt<T> faile() {
		return new RespRlt<>(ResultCode.FAIL);
	}

	/**
	 * 返回失败
	 *
	 * @param source
	 *            错误码
	 * @return
	 */
	public static <T> RespRlt<T> fail(RespCode source, T data) {
		RespRlt<T> result = new RespRlt<>(source);
		result.setData(data);
		return result;
	}
	
	/**
	 * 返回失败
	 *
	 * @param source
	 *            错误码
	 * @return
	 */
	public static <T> RespRlt<T> fail(RespCode source) {
		return new RespRlt<>(source);
	}

}
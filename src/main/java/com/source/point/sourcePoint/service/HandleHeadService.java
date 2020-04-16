/*package com.source.point.sourcePoint.service;

import com.transsnet.palmpay.cfront.dto.common.SignDto;
import com.transsnet.palmpay.cfront.dto.member.CreatePaperwork;
import com.transsnet.palmpay.cfront.dto.member.MemberDto;
import com.transsnet.palmpay.cfront.dto.member.RequestInfoResp;
import com.transsnet.palmpay.common.RespCode;
import com.transsnet.palmpay.common.ServiceException;
import com.transsnet.palmpay.riskdto.common.request.CommonReqDto;
import com.transsnet.palmpay.riskdto.constants.RiskConstants;

public interface HandleHeadService {
	
	public String getMemberId();

	public SignDto checkSign();
	
	public void checkMySelfDevice(String imei);
	
	public RequestInfoResp getRequestInfo();
	
	public MemberDto getMemberInfo();

	public SignDto getHeadInfo();
	
	public String getSystemType();
	
	public Integer getSubClientVer();
	
	*//**
	 * 获取风控请求参数
	 *//*
	public CommonReqDto getRiskCommonReqDto(RiskConstants.TransType transType, String blackBox,String subTransType);

	*//**
	 * 获取风控请求参数 没token
	 *//*
	public CommonReqDto getRiskCommonReqDtoNoToken(RiskConstants.TransType transType, String blackBox, String subTransType);
	
	*//**
	 * 风控事后通知
	 * @param payStatus
	 * @param commonReqDto
	 *//*
	public void handleRiskResult(String payStatus, CommonReqDto commonReqDto);
	
	*//**
	 * 风控事后注册补全用户ID
	 * @param payStatus
	 * @param commonReqDto
	 *//*
	public CommonReqDto handleRiskRegist(String memberId, CommonReqDto commonReqDto);
	
	*//**
	 * 加纳扩展信息构建
	 *//*
	public CreatePaperwork buildCreatePaperwork(String certificatesId, String certificatesType, String certificatesPhotoPath);
	
	*//**
	 * 不同国家, 返回不同的客服信息
	 *//*
	public ServiceException buildSpecialServiceException(RespCode respCode);
	
	*//**
	 * 判断活动接口是否可用
	 *//*
	void hasViralWaitlist();

	*//**
	 * 获取app来源:0-PalmPay,1-PalmPromoter
	 * 友情提醒:只能获取到app传值,app才有设备ID,h5使用此方法时请确定是否有设备ID
	 * Exception:找不到设备直接抛异常
	 *//*
	int getAppSource();
	
	*//**
	 * 处理风控拒绝场景,true-风控拒绝
	 *//*
	public boolean riskHasReject(String disposal);
	
}
*/
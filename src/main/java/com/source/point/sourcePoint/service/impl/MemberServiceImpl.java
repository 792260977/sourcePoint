/*package com.source.point.sourcePoint.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.transsnet.palmpay.cfront.config.ConfigHandler;
import com.transsnet.palmpay.cfront.dto.account.AddWithdrawBankAccReq;
import com.transsnet.palmpay.cfront.dto.account.ApplyVirtualCardReq;
import com.transsnet.palmpay.cfront.dto.account.LinkAccount;
import com.transsnet.palmpay.cfront.dto.cashinout.BankAccountValidateRequest;
import com.transsnet.palmpay.cfront.dto.cashinout.BankAccountValidateResponse;
import com.transsnet.palmpay.cfront.dto.device.DevicesInfoDto;
import com.transsnet.palmpay.cfront.dto.marketing.IncentiveRecordDetailDto;
import com.transsnet.palmpay.cfront.dto.marketing.IncentiveRecordGroupReq;
import com.transsnet.palmpay.cfront.dto.marketing.IncentiveRecordPageReq;
import com.transsnet.palmpay.cfront.dto.mcustomermanagement.HomeCard;
import com.transsnet.palmpay.cfront.dto.member.AddMemberContactsReq;
import com.transsnet.palmpay.cfront.dto.member.AmountThresholdDto;
import com.transsnet.palmpay.cfront.dto.member.AppResetPinReq;
import com.transsnet.palmpay.cfront.dto.member.BenefitDto;
import com.transsnet.palmpay.cfront.dto.member.BvnVerifyAndAddAccReq;
import com.transsnet.palmpay.cfront.dto.member.BvnVerifyNoAddAccReq;
import com.transsnet.palmpay.cfront.dto.member.BvnVerifyReq;
import com.transsnet.palmpay.cfront.dto.member.ChangeNameReq;
import com.transsnet.palmpay.cfront.dto.member.ChangePhoneReq;
import com.transsnet.palmpay.cfront.dto.member.CheckPinTouchTokenReq;
import com.transsnet.palmpay.cfront.dto.member.CreatePaperwork;
import com.transsnet.palmpay.cfront.dto.member.ForceLogoutReq;
import com.transsnet.palmpay.cfront.dto.member.Member;
import com.transsnet.palmpay.cfront.dto.member.MemberContactsDto;
import com.transsnet.palmpay.cfront.dto.member.MemberDto;
import com.transsnet.palmpay.cfront.dto.member.MemberPhoto;
import com.transsnet.palmpay.cfront.dto.member.MemberPhotoDto;
import com.transsnet.palmpay.cfront.dto.member.ModifyMember;
import com.transsnet.palmpay.cfront.dto.member.ModifyMemberPhone;
import com.transsnet.palmpay.cfront.dto.member.ModifyMemberPin;
import com.transsnet.palmpay.cfront.dto.member.MoneyRoleDto;
import com.transsnet.palmpay.cfront.dto.member.Paperwork;
import com.transsnet.palmpay.cfront.dto.member.QrCode;
import com.transsnet.palmpay.cfront.dto.member.QueryBenefit;
import com.transsnet.palmpay.cfront.dto.member.QueryInvitListResp;
import com.transsnet.palmpay.cfront.dto.member.QueryMemberContactsConditions;
import com.transsnet.palmpay.cfront.dto.member.QueryMemberId;
import com.transsnet.palmpay.cfront.dto.member.QueryMemberPhoto;
import com.transsnet.palmpay.cfront.dto.member.QueryMemberResp;
import com.transsnet.palmpay.cfront.dto.member.QueryPhotoReq;
import com.transsnet.palmpay.cfront.dto.member.QueryRecommendReq;
import com.transsnet.palmpay.cfront.dto.member.QueryRegisterInfoResp;
import com.transsnet.palmpay.cfront.dto.member.ResetPinReq;
import com.transsnet.palmpay.cfront.dto.member.ResetPinSelfileReq;
import com.transsnet.palmpay.cfront.dto.member.SetPinReq;
import com.transsnet.palmpay.cfront.dto.member.UpdateMemInfoByApproveReq;
import com.transsnet.palmpay.cfront.dto.member.UpdatePinReq;
import com.transsnet.palmpay.cfront.dto.member.UpgradeReq;
import com.transsnet.palmpay.cfront.dto.member.ValidatePhoneAndPin;
import com.transsnet.palmpay.cfront.dto.member.ValidatePin;
import com.transsnet.palmpay.cfront.dto.sendmoney.SimpleSendMoneyOrder;
import com.transsnet.palmpay.cfront.dto.validator.BvnInfoResp;
import com.transsnet.palmpay.cfront.dto.validator.FaceDetectReq;
import com.transsnet.palmpay.cfront.dto.validator.FaceVerifyReq;
import com.transsnet.palmpay.cfront.dto.viralwait.CreateOrQueryViralwaitUserReq;
import com.transsnet.palmpay.cfront.dto.viralwait.ViralwaitUserDto;
import com.transsnet.palmpay.cfront.enums.Constants;
import com.transsnet.palmpay.cfront.enums.Constants.ResetPinStep;
import com.transsnet.palmpay.cfront.enums.RespCfrontCode;
import com.transsnet.palmpay.cfront.enums.SubTransTypeEnum;
import com.transsnet.palmpay.cfront.mq.dto.sms.LoyaltyRewardDto;
import com.transsnet.palmpay.cfront.mq.producer.LoyaltyRewardProducer;
import com.transsnet.palmpay.cfront.service.BasicDataService;
import com.transsnet.palmpay.cfront.service.HandleHeadService;
import com.transsnet.palmpay.cfront.service.MemberService;
import com.transsnet.palmpay.cfront.service.PayService;
import com.transsnet.palmpay.cfront.service.RedisService;
import com.transsnet.palmpay.cfront.service.feginclient.AccountClient;
import com.transsnet.palmpay.cfront.service.feginclient.DeviceClient;
import com.transsnet.palmpay.cfront.service.feginclient.LoanClient;
import com.transsnet.palmpay.cfront.service.feginclient.MCustomerManagementClient;
import com.transsnet.palmpay.cfront.service.feginclient.MarketingClient;
import com.transsnet.palmpay.cfront.service.feginclient.MemberClient;
import com.transsnet.palmpay.cfront.service.feginclient.PayRouteClient;
import com.transsnet.palmpay.cfront.service.feginclient.RiskControlClient;
import com.transsnet.palmpay.cfront.service.feginclient.SendMoneyClient;
import com.transsnet.palmpay.cfront.service.feginclient.ValidatorClient;
import com.transsnet.palmpay.cfront.utils.CheckUtils;
import com.transsnet.palmpay.common.CountryCodeHolder;
import com.transsnet.palmpay.common.PageDto;
import com.transsnet.palmpay.common.RespResult;
import com.transsnet.palmpay.common.RespUtil;
import com.transsnet.palmpay.common.ServiceException;
import com.transsnet.palmpay.enums.CountryEnum;
import com.transsnet.palmpay.riskdto.binding.request.BankAccountBindingDto;
import com.transsnet.palmpay.riskdto.binding.request.CertificateBindingDto;
import com.transsnet.palmpay.riskdto.common.recheck.RiskReturnReCheckDto;
import com.transsnet.palmpay.riskdto.common.request.CommonReqDto;
import com.transsnet.palmpay.riskdto.common.request.RiskReqDto;
import com.transsnet.palmpay.riskdto.common.response.RiskResult;
import com.transsnet.palmpay.riskdto.constants.RiskConstants;
import com.transsnet.palmpay.util.GsonUtils;
import com.transsnet.palmpay.util.JsonUtil;
import com.transsnet.palmpay.util.RandomUtils;
import com.transsnet.palmpay.util.StringUtil;

@Service
public class MemberServiceImpl implements MemberService {
	
	private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);
	
	private static final String CHECK_PIN_TOUCH_PREFIX_ = "CHECK_PIN_TOUCH_PREFIX_";//校验PIN或者touch前缀

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private MemberClient memberClient;
	
	@Autowired
	private SendMoneyClient sendMoneyClient;
	
	@Autowired
	private ValidatorClient validatorClient;
	
	@Autowired
	private DeviceClient deviceClient;
	
	@Autowired
	private PayRouteClient payRouteClient;
	
	@Autowired
	private AccountClient accountClient;
	
	@Autowired
	private MarketingClient marketingClient;
	
	@Autowired
	private RiskControlClient riskControlClient;
	
	@Autowired
	private HandleHeadService handleHeadService;
	
	@Autowired
	private BasicDataService basicDataService;
	
	@Autowired
	private MCustomerManagementClient mCustomerManagementClient;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private LoanClient loanClient;
	
	@Autowired
	private LoyaltyRewardProducer loyaltyRewardProducer;
	
	@Deprecated
	@Override
	public RespResult<Void> setPin(String memberId, SetPinReq req) {
		FaceDetectReq faceDetectReq = new FaceDetectReq();
		faceDetectReq.setImageContent(req.getSelfieUrlBase());
		RespResult<Boolean> detectRlt = validatorClient.detect(faceDetectReq);
		if (!Boolean.TRUE.equals(RespUtil.getRespResult(detectRlt))) {
			throw new ServiceException(RespCfrontCode.RESET_PIN_SEFILE_ERROR);
		}
		RespResult<MemberDto> memberRlt = memberClient.queryMemberById(new QueryMemberId(memberId));
		MemberDto member = RespUtil.getRespResult(memberRlt);
		if (member == null) {
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		if (!"0".equals(member.getPinStatus())) {
			throw new ServiceException(RespCfrontCode.PIN_STATUS_ERROR);
		}
		ModifyMember command = new ModifyMember();
		command.setMemberId(memberId);
		command.setPayPassword(req.getPin());
		command.setPinStatus("1");
		RespResult<Void> rlt = memberClient.modify(command);
		RespUtil.getRespResult(rlt);

		List<MemberPhotoDto> target = new ArrayList<>();
		MemberPhotoDto memberPhotoDto = new MemberPhotoDto();
		memberPhotoDto.setMemberId(memberId);
		memberPhotoDto.setPhotoBase(req.getSelfieUrlBase());
		memberPhotoDto.setPhotoType("2");
		target.add(memberPhotoDto);
		RespUtil.getRespResult(memberClient.create(target));

		return rlt;
	}

	@Override
	public RespResult<RiskReturnReCheckDto> changePin(UpdatePinReq req) {
		String memberId = req.getMemberId();
		RespResult<MemberDto> memberRlt = memberClient.queryMemberById(new QueryMemberId(memberId));
		MemberDto member = RespUtil.getRespResult(memberRlt);
		if(member == null){
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		ValidatePhoneAndPin vpp = new ValidatePhoneAndPin();
		vpp.setPhone(member.getPhone());
		vpp.setPayPassword(req.getOldPin());
		RespResult<ValidatePin> rlt = memberClient.validatePhoneAndPin(vpp);
		LOG.info("MemberServiceImpl.changePin------>memberClient.validatePhoneAndPin param={},rlt={}", GsonUtils.toJson(vpp), GsonUtils.toJson(rlt));
		ValidatePin validatePin = RespUtil.getRespResult(rlt);
		if (Boolean.TRUE.equals(validatePin.getValidate())) {
			RespResult<RiskReturnReCheckDto> updateRlt = updatePwdRisk(
					new ModifyMemberPin(memberId, req.getNewPin(), req.getBlackBox()));
			RiskReturnReCheckDto riskResult = RespUtil.getRespResult(updateRlt);
			if (RiskConstants.RiskDisposal.PASS.getCode().equals(riskResult.getDisposal())) {
				forceAllDeviceLogoutExceptOne(memberId, req.getDeviceId(), req.getAppSource());
			}
			return updateRlt;
		}else{
			throw new ServiceException(RespCfrontCode.MEMBER_PIN_ERROR);
		}
	}

	@Deprecated
	@Override
	public RespResult<Void> resetPinSelfile(String memberId, ResetPinSelfileReq req) {
		if(memberId == null){
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		//1、校验第一步是否成功
		String step = redisService.getRedisValue(Constants.RESET_PIN_PREFIX + memberId);
		if(StringUtils.isEmpty(step)) {
			return RespUtil.fail(RespCfrontCode.RESET_PIN_OTC_EXPRIED);
		}
		LOG.info("1:one time code 校验结果未超时");
			
		//是不是人脸
		FaceDetectReq reqFaceDetect = new FaceDetectReq();
		reqFaceDetect.setImageContent(req.getSelfile());
		RespResult<Boolean> detect = validatorClient.detect(reqFaceDetect);
		Boolean detectRlt = RespUtil.getRespResult(detect);
		if(!Boolean.TRUE.equals(detectRlt)) {
			LOG.info("2:selfile人脸定位检测失败");
			return RespUtil.fail(RespCfrontCode.RESET_PIN_SEFILE_ERROR);
		}
		LOG.info("2:selfile人脸定位检测正确");
		
		//人脸比对
		QueryMemberPhoto queryMemberPhoto = new QueryMemberPhoto();
		queryMemberPhoto.setMemberId(memberId);
		queryMemberPhoto.setPhotoType("2");
		RespResult<List<MemberPhotoDto>> queryPhotoByMemberId = memberClient.queryPhotoByMemberId(queryMemberPhoto);
		RespUtil.getRespResult(queryPhotoByMemberId);
		if(CollectionUtils.isEmpty(queryPhotoByMemberId.getData())) {
			return RespUtil.fail(RespCfrontCode.RESET_PIN_SEFILE_NULL);
		}
		if(StringUtils.isEmpty(queryPhotoByMemberId.getData().get(0).getPhotoBase())) {
			return RespUtil.fail(RespCfrontCode.RESET_PIN_SEFILE_NULL);
		}
		FaceVerifyReq faceVerifyReq = new FaceVerifyReq();
		faceVerifyReq.setImageContent1(req.getSelfile());
		faceVerifyReq.setImageContent2(queryPhotoByMemberId.getData().get(0).getPhotoBase());
		RespResult<Boolean> faceVerify = validatorClient.faceVerify(faceVerifyReq);
		Boolean faceVerifyRlt = RespUtil.getRespResult(faceVerify);
		if(Boolean.TRUE.equals(faceVerifyRlt)) {
			LOG.info("3:selfile人脸比对成功");
			//如果selfile校验成功，存放第二步校验通过结果到redis
			redisService.setRedis(Constants.RESET_PIN_PREFIX + memberId, ResetPinStep.SELFILE.getStep(), Constants.RESET_PIN_SECONDS);
			return RespUtil.success();
		} else {
			LOG.info("3:selfile人脸比对失败");
			return RespUtil.fail(RespCfrontCode.RESET_PIN_SEFILE_VERIFY);
		}
	}

	@Deprecated
	@Override
	public RespResult<Void> resetPinNewPassword(String memberId, ResetPinReq req) {
		if(memberId == null){
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		//1、校验第二步是否成功
		String step = redisService.getRedisValue(Constants.RESET_PIN_PREFIX + memberId);
		if (StringUtils.isEmpty(step) || !ResetPinStep.SELFILE.getStep().equals(step)) {
			return RespUtil.fail(RespCfrontCode.RESET_PIN_SEFILE_EXPRIED);
		}
		LOG.info("1:人脸对比结果未超时");
		ModifyMember modifyMember = new ModifyMember();
		modifyMember.setPayPassword(req.getNewPin());
		modifyMember.setPinStatus("1");//支付密码状态0初始化1正常2冻结(志伟强烈要求加的)
		modifyMember.setMemberId(memberId);
		return memberClient.modify(modifyMember);
		
	}

	@Override
	public RespResult<Void> setSelfie(String memberId, String selfieUrlBase) {
		List<MemberPhotoDto> target = new ArrayList<>();
		MemberPhotoDto memberPhotoDto = new MemberPhotoDto();
		memberPhotoDto.setMemberId(memberId);
		memberPhotoDto.setPhotoPath(selfieUrlBase);
		memberPhotoDto.setPhotoType("2");
		target.add(memberPhotoDto);
		RespResult<Void> rlt =  memberClient.create(target);
		return rlt;
	}
	
	@Deprecated
	@Override
	public RespResult<Boolean> checkFaceSetSelfie(String selfieUrlBase) {
		FaceDetectReq reqFaceDetect = new FaceDetectReq();
		reqFaceDetect.setImageContent(selfieUrlBase);
		RespResult<Boolean> detect = validatorClient.detect(reqFaceDetect);
		Boolean detectRlt = RespUtil.getRespResult(detect);
		if(!Boolean.TRUE.equals(detectRlt)) {
			throw new ServiceException(RespCfrontCode.RESET_PIN_SEFILE_ERROR);
		}
		return RespUtil.success(true);
	}
	
	@Override
	public RespResult<String> getSetSelfieByType(QueryMemberPhoto req) {
		req.setPhotoUrl(null);
		RespResult<List<MemberPhotoDto>> queryPhotoByMemberId = memberClient.queryPhotoByMemberId(req);
		LOG.info("MemberServiceImpl.getSetSelfieByType------>memberClient.queryPhotoByMemberId param={},rlt={}", GsonUtils.toJson(req), GsonUtils.toJson(queryPhotoByMemberId));
		List<MemberPhotoDto> selfRlt = RespUtil.getRespResult(queryPhotoByMemberId);
		if(CollectionUtils.isEmpty(selfRlt) || StringUtils.isEmpty(selfRlt.get(0).getPhotoBase())) {
			return RespUtil.success("");
		}
		return RespUtil.success(selfRlt.get(0).getPhotoPath());
	}
	
	@Override
	public RespResult<Void> setSetSelfieByType(QueryMemberPhoto req) {
		List<MemberPhotoDto> target = new ArrayList<>();
		MemberPhotoDto memberPhotoDto = new MemberPhotoDto();
		memberPhotoDto.setMemberId(req.getMemberId());
		memberPhotoDto.setPhotoPath(req.getPhotoUrl());
		memberPhotoDto.setPhotoType(req.getPhotoType());
		target.add(memberPhotoDto);
		RespResult<Void> rlt =  memberClient.create(target);
		return rlt;
	}
	
	@Override
	public BvnInfoResp bvnVerify(BvnVerifyReq req) {
		BvnInfoResp result = null;
		RespResult<Boolean> bvnRlt = validatorClient.bvnVerify(req);
		Boolean rltFalg= RespUtil.getRespResult(bvnRlt);
		if(Boolean.TRUE.equals(rltFalg)){
			RespResult<BvnInfoResp> bvnInfoRlt =  validatorClient.queryBvnInfo(req.getBvn());
			LOG.info("MemberServiceImpl.bvnVerify------>validatorClient.queryBvnInfo param={},rlt={}", GsonUtils.toJson(req.getBvn()), GsonUtils.toJson(bvnInfoRlt));
			if (bvnInfoRlt.getData() == null) {
				throw new ServiceException(RespCfrontCode.BVN_CHECK_ERROR);
			} 
			result = bvnInfoRlt.getData();
			if(Boolean.TRUE.equals(req.getUpdateMemberInfo())){
				//bvn通过后,修改用户数据前,过风控
				bvnRisk(req, result);
			}
		}else{
			throw new ServiceException(RespCfrontCode.BVN_CHECK_ERROR);
		}
		return result;
	}
	
	//bvn风控业务模块
	private void bvnRisk(BvnVerifyReq bvnVerifyReq, BvnInfoResp bvnInfoResp){
		CommonReqDto commonReqDto = null;
		try {
			commonReqDto = bvnBindingRisk(bvnVerifyReq, bvnInfoResp);//bvn绑定过风控
			//========业务========
			ModifyMember modifyMember = new ModifyMember();
			modifyMember.setMemberId(bvnVerifyReq.getMemberId());
			updateMemberByBvnVerify(modifyMember, bvnInfoResp);
			//========业务========
			handleHeadService.handleRiskResult(RiskConstants.SyncResultEnum.SUCCESS.getCode(), commonReqDto);
		} catch (ServiceException e) {
			handleHeadService.handleRiskResult(RiskConstants.SyncResultEnum.FAILURE.getCode(), commonReqDto);
			throw e;
		}
	}
	
	private CommonReqDto bvnBindingRisk(BvnVerifyReq bvnVerifyReq, BvnInfoResp bvnInfoResp) {
		CommonReqDto commonReqDto = handleHeadService.getRiskCommonReqDto(RiskConstants.TransType.BINDING, bvnVerifyReq.getBlackBox(), SubTransTypeEnum._1.getSubtransType());
		CertificateBindingDto body = new CertificateBindingDto();
		body.setFirstName(bvnInfoResp.getFirstName());
	    body.setMiddleName(bvnInfoResp.getMiddleName());
	    body.setLastName(bvnInfoResp.getLastName());
	    if(StringUtils.isNotBlank(bvnInfoResp.getDateOfBirth())){
	    	String birthday = CheckUtils.formatEn(bvnInfoResp.getDateOfBirth()).toString();
	    	body.setBirthday(birthday);
	    }
	    body.setCertificatesId(bvnInfoResp.getBvn());
	    body.setCertificatesType("BVN");
	    body.setCertificatesEnrollmentBank(bvnInfoResp.getEnrollmentBank());
	    body.setCertificatesEnrollmentBranch(bvnInfoResp.getEnrollmentBranch());
	    body.setCertificatesPhoneNumber(bvnInfoResp.getPhoneNumber());
	    body.setBvn(bvnInfoResp.getBvn());
		
		RiskReqDto<CertificateBindingDto> riskReqDto = new RiskReqDto<>();
		riskReqDto.setBody(body);
		riskReqDto.setCommonReqDto(commonReqDto);
		LOG.info("certificateBinding begin param={}",GsonUtils.toJson(riskReqDto));
		long time1 = System.currentTimeMillis();
		RespResult<RiskResult> respResult = riskControlClient.certificateBinding(riskReqDto);
		long time2 = System.currentTimeMillis();
		LOG.info("certificateBinding end rlt={},time={}ms",GsonUtils.toJson(respResult), (time2-time1));
		RiskResult riskResult = RespUtil.getRespResult(respResult);
		if(!RiskConstants.RiskDisposal.PASS.getCode().equals(riskResult.getDisposal())){
			commonReqDto = null;
//			throw handleHeadService.buildSpecialServiceException(RespCfrontCode.BVN_RISK_ERROR);
			throw new ServiceException(RespCfrontCode.BVN_RISK_ERROR);//BUG:5465 要求改的
		}
		if(StringUtils.isBlank(riskResult.getFlowId())){
			commonReqDto = null;
		}
		return commonReqDto;
	}
	
	private void updateMemberByBvnVerify(ModifyMember modifyMember, BvnInfoResp bvnInfo) {
		if(modifyMember == null || StringUtils.isBlank(modifyMember.getMemberId()) || bvnInfo == null){
			LOG.info("updateMemberByBvnVerify modifyMember={},bvnInfo={}",
					GsonUtils.toJson(modifyMember), GsonUtils.toJson(bvnInfo));
			return;
		}
		modifyMember.setBvn(bvnInfo.getBvn());
		modifyMember.setFirstName(bvnInfo.getFirstName());
		modifyMember.setLastName(bvnInfo.getLastName());
		modifyMember.setMobileMoneyAccountTier("2");
		LocalDate birthday = CheckUtils.formatEn(bvnInfo.getDateOfBirth());
		if(birthday != null){
			modifyMember.setBirthday(birthday);
		}
		if (StringUtils.isBlank(bvnInfo.getMiddleName())) {
			modifyMember.setFullName(bvnInfo.getFirstName() + " " + bvnInfo.getLastName());
		} else {
			modifyMember.setMiddleName(bvnInfo.getMiddleName());
			modifyMember.setFullName(bvnInfo.getFirstName() + " " + bvnInfo.getMiddleName() + " " + bvnInfo.getLastName());
		}
		RespResult<Void> memberRlt = memberClient.modify(modifyMember);
		LOG.info("MemberServiceImpl.updateMemberByBvnVerify------>memberClient.modify param={},rlt={}",
				GsonUtils.toJson(modifyMember), GsonUtils.toJson(memberRlt));
		RespUtil.getRespResult(memberRlt);
	}
	
	@Override
	public RespResult<Void> createBenefit(String myId, String orderId, String nickName,String benefitType) {
		RespResult<SimpleSendMoneyOrder> rlt = sendMoneyClient.queryOrderDetail(orderId);
		LOG.info("MemberServiceImpl.createBenefit------>sendMoneyClient.queryOrderDetail param={},rlt={}", GsonUtils.toJson(orderId), GsonUtils.toJson(rlt));
		SimpleSendMoneyOrder sendMoneyOrder = RespUtil.getRespResult(rlt);
		if (sendMoneyOrder == null) {
			throw new ServiceException(RespCfrontCode.NO_ORDER_DATA);
		}
		
		
		BenefitDto req = new BenefitDto();
		req.setRecipientFirstName(sendMoneyOrder.getRecipientFirstName());
		req.setRecipientLastName(sendMoneyOrder.getRecipientLastName());
		if (StringUtils.isBlank(sendMoneyOrder.getRecipientFirstName())
				&& StringUtils.isNotBlank(sendMoneyOrder.getRecipientLastName())) {
			req.setFullName(sendMoneyOrder.getRecipientLastName());
		}else if(StringUtils.isNotBlank(sendMoneyOrder.getRecipientFirstName())
				&& StringUtils.isBlank(sendMoneyOrder.getRecipientLastName())){
			req.setFullName(sendMoneyOrder.getRecipientFirstName());
		} else if(StringUtils.isNotBlank(sendMoneyOrder.getRecipientFirstName())
				&& StringUtils.isNotBlank(sendMoneyOrder.getRecipientLastName())){
			req.setFullName(sendMoneyOrder.getRecipientFirstName() + " " + sendMoneyOrder.getRecipientLastName());
		}
		if(StringUtils.isNotBlank(req.getFullName())){
			List<String> name = new ArrayList<>();
			String[] str = req.getFullName().split(" ");
			for (int i = 0; i < str.length; i++) {
				if (StringUtils.isNotBlank(str[i])) {
					name.add(str[i]);
				}
			}
			String fullName = "";
			for (int i = 0; i < name.size(); i++) {
				if(i == (name.size()-1)){
					fullName+=name.get(i);
				}else{
					fullName+=(name.get(i)+ " ");
				}
			}
			req.setFullName(fullName);
		}
		req.setDefaulAmount(new BigDecimal(sendMoneyOrder.getAmount()));
		req.setMemberId(myId);
		if ("2".equals(benefitType)) {
			req.setAccountNumber(sendMoneyOrder.getRecipientBankAccount());
		} else {
			req.setAccountNumber(sendMoneyOrder.getRecipientAccountId());
		}
		req.setNickname(nickName);
		req.setBankCode(sendMoneyOrder.getRecipientBankCode());
		req.setBank(sendMoneyOrder.getRecipientBankName());
		MemberDto memberDto = null;
		if(StringUtils.isNotBlank(sendMoneyOrder.getRecipientMemberId())){
			RespResult<MemberDto> rltMember = memberClient.queryMemberById(new QueryMemberId(sendMoneyOrder.getRecipientMemberId()));
			if(rltMember != null && rltMember.getData() != null){
				memberDto = rltMember.getData();
				req.setPhone(memberDto.getPhone());
			}
		}else{
			req.setPhone(sendMoneyOrder.getRecipientPhone());
		}
		req.setBenefitType(benefitType);
		return memberClient.createBenefit(req);
	}
	
	@Override
	public RespResult<RiskReturnReCheckDto> changePhone(ChangePhoneReq req) {
		// NG需要判断是不是palmcredit,是的话就不能修改手机, 目前只有NG有贷款模块
		if (CountryEnum.NG.toString().equals(ConfigHandler.getInstance().getCOUNTRY_CODE())) {
			RespResult<Boolean> respResult = loanClient.isCreditUser(req.getMemberId());
			LOG.info("loanClient.isCreditUser param={},rlt={}", req.getMemberId(), GsonUtils.toJson(respResult));
			Boolean loanRlt = RespUtil.getRespResult(respResult);
			if (Boolean.TRUE.equals(loanRlt)) {
				throw new ServiceException(RespCfrontCode.PALMCREDIT_USER);
			}
		}
		//校验短信
		RespResult<Boolean> tokenRlt = checkSmsToken(req.getSmsToken(), req.getMobileNo());
		Boolean flage = RespUtil.getRespResult(tokenRlt);
		if (!Boolean.TRUE.equals(flage)) {
			throw new ServiceException(RespCfrontCode.RESET_PIN_OTC_EXPRIED);
		}
		//校验用户
		QueryMemberId queryMemberId = new QueryMemberId();
		queryMemberId.setPhone(req.getMobileNo());
		RespResult<MemberDto> memberRlt = memberClient.queryMemberById(queryMemberId);
		MemberDto memberDto = RespUtil.getRespResult(memberRlt);
		if (memberDto != null) {
			throw new ServiceException(RespCfrontCode.PHONE_REGISTERED);
		}
		//风控修改手机
		return updatePhone(new ModifyMemberPhone(req.getMemberId(), req.getMobileNo(), req.getBlackBox()));
	}
	
	private RespResult<Boolean> checkSmsToken(String smsToken, String phone) {
		// 与StartAppBasicServiceImpl.checkSmsToken保持一致，因为特殊原因复制一遍调用，而不是直接接口调用
		String key = StartAppBasicServiceImpl.SEND_SMS_PREFIX+smsToken;
		String verifyCode = redisService.getRedisValue(key);
		if(phone != null && phone.equals(verifyCode)){
			redisService.delRedis(key);
			return RespUtil.success(true);
		}else{
			return RespUtil.success(false);
		}
	}
	
	@Override
	public RespResult<Void> changeName(ChangeNameReq req) {
		String memberId = req.getMemberId();
		RespResult<MemberDto> memberRlt = memberClient.queryMemberById(new QueryMemberId(memberId));
		MemberDto memberDto = RespUtil.getRespResult(memberRlt);
		if(memberDto == null){
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		ModifyMember modifyMember = new ModifyMember();
		if ("2".equals(memberDto.getMobileMoneyAccountTier())) {
			if (StringUtils.isNotBlank(req.getFirstName()) || StringUtils.isNotBlank(req.getMiddleName())
					|| StringUtils.isNotBlank(req.getLastName())) {
				throw new ServiceException(RespCfrontCode.NO_CHANGE_MEMBER_INFO);
			}
		}
		modifyMember.setMemberId(req.getMemberId());
		modifyMember.setNickName(req.getNickName());
		if(StringUtils.isNotBlank(req.getFirstName()) && StringUtils.isBlank(req.getMiddleName()) &&StringUtils.isNotBlank(req.getLastName())){
			modifyMember.setFirstName(req.getFirstName());
			modifyMember.setLastName(req.getLastName());
			modifyMember.setFullName(req.getFirstName()+" "+req.getLastName());
		}else if(StringUtils.isNotBlank(req.getFirstName()) && StringUtils.isNotBlank(req.getMiddleName()) &&StringUtils.isNotBlank(req.getLastName())){
			modifyMember.setFirstName(req.getFirstName());
			modifyMember.setMiddleName(req.getMiddleName());
			modifyMember.setLastName(req.getLastName());
			modifyMember.setFullName(req.getFirstName()+" "+req.getMiddleName()+" "+req.getLastName());
		}
		RespResult<Void> updateRlt = memberClient.modify(modifyMember);
		return updateRlt;
	}
	
	@Override
	public RespResult<Void> forceSomeDeviceLogout(ForceLogoutReq req) {
		if (CollectionUtils.isNotEmpty(req.getDeviceIds())) {
			for (String deviceId : req.getDeviceIds()) {
				forceDeviceLogout(deviceId);
			}
		} else {
			forceAllDeviceLogoutExceptOne(req.getMemberId(), null, req.getAppSource());
		}
		return RespUtil.success();
	}
	
	private List<String> forceAllDeviceLogoutExceptOne(String memberId, String oneDeviceId, Integer appSource){
		List<String> devices = new ArrayList<>();
		RespResult<List<DevicesInfoDto>> deviceRlt = deviceClient.queryDevicesList(memberId, appSource);
		LOG.info("MemberServiceImpl.forceSomeDeviceLogout------>deviceClient.queryDevicesList param={},rlt={}",
				GsonUtils.toJson(memberId), GsonUtils.toJson(deviceRlt));
		if (deviceRlt != null && CollectionUtils.isNotEmpty(deviceRlt.getData())) {
			for (DevicesInfoDto devicesInfoDto : deviceRlt.getData()) {
				String deviceId = devicesInfoDto.getImei();
				if(!deviceId.equals(oneDeviceId)){
					devices.add(deviceId);
					forceDeviceLogout(deviceId);
				}
			}
		}
		return devices;
	}
	
	@Override
	public RespResult<Void> forceDeviceLogout(String deviceId) {
		String key1 = CountryCodeHolder.getCountryCode() + StartAppBasicServiceImpl.START_APP_PREFIX + deviceId;
		String token = redisService.getRedisValue(key1);
		LOG.info("forceDeviceLogout param={},token{}",deviceId,token);
		if (StringUtils.isNotBlank(token)) {
			redisService.delRedis(key1);
			String key2 = CountryCodeHolder.getCountryCode() + StartAppBasicServiceImpl.START_APP_PREFIX + token;
			redisService.delRedis(key2);
		}
		// 修改设备ID,退出不收消息
		deviceClient.updateClientIdByImei(deviceId);
		return RespUtil.success();
	}
	
	@Override
	public RespResult<RiskReturnReCheckDto> appResetPin(AppResetPinReq req) {
		RespResult<MemberDto> memberRlt = memberClient.queryMemberById(new QueryMemberId(req.getMemberId()));
		MemberDto memberDto = RespUtil.getRespResult(memberRlt);
		if(memberDto == null){
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		RespResult<Boolean> tokenRlt = checkSmsToken(req.getSmsToken(), memberDto.getPhone());
		Boolean flage = RespUtil.getRespResult(tokenRlt);
		if(!Boolean.TRUE.equals(flage)){
			throw new ServiceException(RespCfrontCode.RESET_PIN_OTC_EXPRIED);
		}
		return updatePwdRisk(new ModifyMemberPin(req.getMemberId(), req.getNewPin(), req.getBlackBox()));
		
	}

	@Override
	public RespResult<String> queryPhotoByIdOrPhone(QueryPhotoReq req) {
		String memberId = req.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			QueryMemberId queryMemberId = new QueryMemberId();
			queryMemberId.setPhone(req.getPhone());
			RespResult<MemberDto> memberRlt = memberClient.queryMemberById(queryMemberId);
			MemberDto member = RespUtil.getRespResult(memberRlt);
			if (member == null) {
				throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
			}
			memberId = member.getMemberId();
		}
		QueryMemberPhoto queryMemberPhoto = new QueryMemberPhoto();
		queryMemberPhoto.setMemberId(memberId);
		queryMemberPhoto.setPhotoType(req.getPhotoType());
		return getSetSelfieByType(queryMemberPhoto);
	}
	
	@Override
	public RespResult<Void> bvnVerifyAndAddAcc(MemberDto memberDto, BvnVerifyAndAddAccReq req) {
		String bvn = memberDto.getBvn();
		String firstName = null;
		String lastName = null;
		BvnInfoResp bvnRlt = null;
		if (StringUtils.isBlank(bvn)) {
			BvnVerifyReq bvnVerifyReq = new BvnVerifyReq();
			bvnVerifyReq.setMemberId(req.getMemberId());
			bvnVerifyReq.setSmsCode(req.getSmsCode());
			bvnVerifyReq.setTransactionReference(req.getTransactionReference());
			bvnVerifyReq.setBvn(req.getBvn());
			bvnVerifyReq.setUpdateMemberInfo(false);
			bvnVerifyReq.setBlackBox(req.getBlackBox());
			bvnRlt = bvnVerify(bvnVerifyReq);
			if(null != bvnRlt){
				firstName = bvnRlt.getFirstName();
				lastName = bvnRlt.getLastName();
				bvn = bvnRlt.getBvn();
			}
		}else{
			firstName = memberDto.getFirstName();
			lastName = memberDto.getLastName();
		}
		BankAccountValidateRequest nameReq = new BankAccountValidateRequest();
		nameReq.setBankAccount(req.getBankAccountNo());
		nameReq.setBankCode(req.getBankCode());
		RespResult<BankAccountValidateResponse> nameRlt = payRouteClient.accountValidate(nameReq);
		LOG.info("MemberServiceImpl.bvnVerifyAndAddAcc------>payRouteClient.accountValidate param={},rlt={}",
				GsonUtils.toJson(nameReq), GsonUtils.toJson(nameRlt));
		if(nameRlt == null || nameRlt.getData() == null || StringUtils.isBlank(nameRlt.getData().getAccountName())){
			throw new ServiceException(RespCfrontCode.BANK_ACCOUN_NOT_FIND);
		}
		String accountName = nameRlt.getData().getAccountName();
		String userName = CheckUtils.filterSpecialStr(accountName, " ");
		boolean nameCheckRlt = CheckUtils.nameCheckRlt(userName, firstName, lastName);
		if(!nameCheckRlt){
			throw new ServiceException(RespCfrontCode.BANK_ACCOUN_NOT_MATCH);
		}
		//绑定银行账户风控,修改数据前,过风控 
		RespResult<Void> rlt = addWithdrawBankAccRisk(firstName, lastName, userName, memberDto.getMemberId(), req);
		RespUtil.getRespResult(rlt);
		
		if (StringUtils.isBlank(memberDto.getBvn()) || ("1".equals(memberDto.getMobileMoneyAccountTier()))) {
			ModifyMember modifyMember = new ModifyMember();
			modifyMember.setMemberId(memberDto.getMemberId());
			modifyMember.setMobileMoneyAccountTier("2");
			modifyMember.setBvn(bvn);
			LocalDate birthday = CheckUtils.formatEn(bvnRlt == null ? null : bvnRlt.getDateOfBirth());
			if(birthday != null){
				modifyMember.setBirthday(birthday);
			}
			modifyMember.setFirstName(firstName);
			modifyMember.setLastName(lastName);
			RespResult<Void> memberRlt = memberClient.modify(modifyMember);
			LOG.info("MemberServiceImpl.bvnVerifyAndAddAcc------>memberClient.modify param={},rlt={}",
					GsonUtils.toJson(modifyMember), GsonUtils.toJson(memberRlt));
		}
		
		return rlt;
	}
	
	// 绑定银行账户风控业务模块
	private RespResult<Void> addWithdrawBankAccRisk(String firstName, String lastName, String userName, String memberId,
			BvnVerifyAndAddAccReq req) {
		CommonReqDto commonReqDto = null;
		try {
			commonReqDto = bankAccountRisk(firstName, lastName, req);// 绑定银行账户风控
			// ========业务========
			AddWithdrawBankAccReq addWithdrawBankAccReq = new AddWithdrawBankAccReq();
			addWithdrawBankAccReq.setBankAccountNo(req.getBankAccountNo());
			addWithdrawBankAccReq.setBankCode(req.getBankCode());
			addWithdrawBankAccReq.setMemberId(memberId);
			addWithdrawBankAccReq.setUserName(userName);
			RespResult<Void> rlt = accountClient.addWithdrawBankAcc(addWithdrawBankAccReq);
			LOG.info("MemberServiceImpl.bvnVerifyAndAddAcc------>accountClient.addWithdrawBankAcc param={},rlt={}",
					GsonUtils.toJson(addWithdrawBankAccReq), GsonUtils.toJson(rlt));
			// ========业务========
			handleHeadService.handleRiskResult(RiskConstants.SyncResultEnum.SUCCESS.getCode(), commonReqDto);
			return rlt;
		} catch (ServiceException e) {
			handleHeadService.handleRiskResult(RiskConstants.SyncResultEnum.FAILURE.getCode(), commonReqDto);
			throw e;
		}
	}
		
	private CommonReqDto bankAccountRisk(String firstName, String lastName, BvnVerifyAndAddAccReq req) {
		CommonReqDto commonReqDto = handleHeadService.getRiskCommonReqDto(RiskConstants.TransType.BINDING, req.getBlackBox(), SubTransTypeEnum._2.getSubtransType());
		BankAccountBindingDto body = new BankAccountBindingDto();
		body.setFirstName(firstName);
		body.setLastName(lastName);
		body.setBankAccountNo(req.getBankAccountNo());
		body.setBankCode(req.getBankCode());
		body.addCertificate(req.getBvn(), "BVN");
		RiskReqDto<BankAccountBindingDto> riskReqDto = new RiskReqDto<>();
		riskReqDto.setBody(body);
		riskReqDto.setCommonReqDto(commonReqDto);
		LOG.info("bankAccount begin param={}", GsonUtils.toJson(riskReqDto));
		long time1 = System.currentTimeMillis();
		RespResult<RiskResult> respResult = riskControlClient.bankAccount(riskReqDto);
		long time2 = System.currentTimeMillis();
		LOG.info("bankAccount end rlt={},time={}ms", GsonUtils.toJson(respResult), (time2 - time1));
		RiskResult riskResult = RespUtil.getRespResult(respResult);
		if (!RiskConstants.RiskDisposal.PASS.getCode().equals(riskResult.getDisposal())) {
			commonReqDto = null;
			//throw handleHeadService.buildSpecialServiceException(RespCfrontCode.BANK_ACC_RISK_ERROR);
			throw new ServiceException(RespCfrontCode.BANK_ACC_RISK_ERROR);//BUG:5465 要求改的
		}
		if (StringUtils.isBlank(riskResult.getFlowId())) {
			commonReqDto = null;
		}
		return commonReqDto;
	}
	
	@Override
	public RespResult<PageDto<IncentiveRecordDetailDto>> recordList(IncentiveRecordPageReq req) {
		RespResult<PageDto<IncentiveRecordDetailDto>> rlt = marketingClient.recordList(req);
		return buildIncentiveRecordDetailDto(rlt);
	}

	private RespResult<PageDto<IncentiveRecordDetailDto>> buildIncentiveRecordDetailDto(RespResult<PageDto<IncentiveRecordDetailDto>> rlt) {
		if(rlt == null || rlt.getData() == null || CollectionUtils.isEmpty(rlt.getData().getList())){
			return rlt;
		}
		List<String> list = new ArrayList<>();
		for (IncentiveRecordDetailDto incentiveRecordDetailDto : rlt.getData().getList()) {
			String otherParty = incentiveRecordDetailDto.getOtherParty();
			if(StringUtils.isNotBlank(otherParty) && (!list.contains(otherParty))){
				list.add(otherParty);
			}
		}
		Map<String, String> map = new HashMap<>();
		RespResult<List<MemberDto>> members = memberClient.queryByIds(list);
		if(members == null || CollectionUtils.isEmpty(members.getData())){
			return rlt;
		}else{
			for (MemberDto memberDto : members.getData()) {
				map.put(memberDto.getMemberId(), CheckUtils.formartMemberName(memberDto.getFirstName(), memberDto.getLastName()));
			}
		}
		
		for (IncentiveRecordDetailDto incentiveRecordDetailDto : rlt.getData().getList()) {
			String otherParty = incentiveRecordDetailDto.getOtherParty();
			if(StringUtils.isNotBlank(otherParty) && (null != map.get(otherParty))){
				incentiveRecordDetailDto.setOtherPartyName(map.get(otherParty));
			}
		}
		return rlt;
	}

	@Override
	public RespResult<Void> loginForceAllDeviceLogoutExceptOne(String deviceId, String memberId, Integer appSource) {
		// 登陆踢其他设备下线专用,放入redis特定token,方便过滤器做特别下线处理(返回特定错误码)
		List<String> deviceIds = forceAllDeviceLogoutExceptOne(memberId, deviceId, appSource);
		if(CollectionUtils.isEmpty(deviceIds)){
			return RespUtil.success();
		}
		for (String device : deviceIds) {
			String key = CountryCodeHolder.getCountryCode() + StartAppBasicServiceImpl.START_APP_PREFIX + device;
			redisService.setRedis(key, StartAppBasicServiceImpl.FORCED_OFFLINE_SUFFIX_, StartAppBasicServiceImpl.SECONDS);
		}
		return RespUtil.success();
	}

	@Override
	public RespResult<PageDto<QueryInvitListResp>> queryInvitList(QueryRecommendReq req) {
		RespResult<PageDto<MemberDto>> memberRlt = memberClient.countDetailByMemberId(req);
		PageDto<MemberDto> memberPage = RespUtil.getRespResult(memberRlt);
		PageDto<QueryInvitListResp> pageRlt = null;
		if (memberPage == null) {
			pageRlt = new PageDto<>(0, req.getPageNum(), req.getPageSize(), new ArrayList<>());
		} else {
			List<QueryInvitListResp> list = buildQueryInvitListResp(req.getAgentId(), memberPage.getList());
			pageRlt = new PageDto<>(list.size(), req.getPageNum(), req.getPageSize(), list);
		}
		return RespUtil.success(pageRlt);
	}

	private List<QueryInvitListResp> buildQueryInvitListResp(String agentId, List<MemberDto> list) {
		List<QueryInvitListResp> rlt = new ArrayList<>();
		if(CollectionUtils.isEmpty(list)){
			return rlt;
		}
		Map<String, QueryInvitListResp> invitMap = new HashMap<>();
		List<String> otherPartys  = new ArrayList<>();
		for (MemberDto source : list) {
			QueryInvitListResp target = new QueryInvitListResp();
			String memberId = source.getMemberId();
			target.setFirstName(source.getFirstName());
			target.setFullName(source.getFullName());
			target.setLastName(source.getLastName());
			target.setMemberId(memberId);
			target.setPhone(source.getPhone());
			target.setPointsAmount(0L);
			target.setInvitTime(source.getCreateTime() == null ? null : Timestamp.valueOf(source.getCreateTime()));
			invitMap.put(memberId, target);
			otherPartys.add(memberId);
		}
		
		Map<String, Long> markMap = new HashMap<>();
		RespResult<List<IncentiveRecordDetailDto>> markRlt = marketingClient.incentiveGroupOtherParty(new IncentiveRecordGroupReq(agentId, otherPartys));
		if(markRlt != null && CollectionUtils.isNotEmpty(markRlt.getData())){
			for (IncentiveRecordDetailDto incentiveRecordDetailDto : markRlt.getData()) {
				String otherParty = incentiveRecordDetailDto.getOtherParty();
				Long pointsAmount = incentiveRecordDetailDto.getPointsAmount() == null ? 0
						: incentiveRecordDetailDto.getPointsAmount();
				markMap.put(otherParty, pointsAmount);
			}
		} 
		
		for (String otherParty : otherPartys) {
			QueryInvitListResp queryInvitListResp = invitMap.get(otherParty);
			Long pointsAmount = markMap.get(otherParty);
			if(pointsAmount != null){
				queryInvitListResp.setPointsAmount(pointsAmount);
			}
			rlt.add(queryInvitListResp);
		}
		return rlt;
	}
	
	@Override
	public RespResult<List<BenefitDto>> queryBenefitByMemberId(QueryBenefit req, MemberDto memberDto) {
		String memberId = memberDto.getMemberId();
		req.setMemberId(memberId);
		RespResult<List<BenefitDto>> rlt = memberClient.queryBenefitEx(req);
		if(rlt != null && CollectionUtils.isNotEmpty(rlt.getData())){
			List<String> bankName = new ArrayList<>();
			List<String> bankCode = new ArrayList<>();
			for (BenefitDto benefitDto : rlt.getData()) {
				if ("2".equals(benefitDto.getBenefitType()) && StringUtils.isNotBlank(benefitDto.getBank())) {
					bankName.add(benefitDto.getBank());
				}
				if ("2".equals(benefitDto.getBenefitType()) && StringUtils.isNotBlank(benefitDto.getBankCode())) {
					bankCode.add(benefitDto.getBankCode());
				}
				String fullName = benefitDto.getFullName();
				if (StringUtils.isNotBlank(fullName)) {
					List<String> name = new ArrayList<>();
					String[] str = fullName.split(" ");
					for (int i = 0; i < str.length; i++) {
						if (StringUtils.isNotBlank(str[i])) {
							name.add(str[i]);
						}
					}
					if(name.size() == 1){
						benefitDto.setRecipientFirstName(name.get(0));
					}else if (name.size() == 2) {
						benefitDto.setRecipientFirstName(name.get(0));
						benefitDto.setRecipientLastName(name.get(1));
					} else if (name.size() == 3) {
						benefitDto.setRecipientFirstName(name.get(0));
						benefitDto.setRecipientLastName(name.get(2));
					}
				}
			}
			rlt = buildBenefitDto(rlt, bankName, bankCode, memberDto.getCountryCode());
		}
    	return rlt;
    }
	
	private RespResult<List<BenefitDto>> buildBenefitDto(RespResult<List<BenefitDto>> rlt, List<String> bankName, List<String> bankCode, String countryCode) {
		if(CollectionUtils.isEmpty(bankName) && CollectionUtils.isEmpty(bankCode)){
			return rlt;
		}
		Map<String, String> map = basicDataService.queryBankInfoMap(countryCode, null, bankName);
		for (BenefitDto result : rlt.getData()) {
			if(StringUtils.isNotBlank(result.getBank())){
				result.setBankUrl(map.get(result.getBank()));
			}
		}
		
		Map<String, String> map2 = basicDataService.queryBankInfoMap(countryCode, bankCode, null);
		for (BenefitDto result : rlt.getData()) {
			if(StringUtils.isNotBlank(result.getBankCode()) && StringUtils.isBlank(result.getBankUrl())){
				result.setBankUrl(map2.get(result.getBank()));
			}
		}
		return rlt;
	}
	
	@Override
	public RespResult<QueryMemberResp> queryMemberDetailInfo(MemberDto source, LinkAccount linkAccount) {
		QueryMemberResp target = null;
		target = new QueryMemberResp();
		BeanUtils.copyProperties(source, target);
		if (source.getBirthday() != null) {
			target.setBirthday(
					String.valueOf(Timestamp.valueOf(LocalDateTime.of(source.getBirthday(), LocalTime.MIN)).getTime()));
		}
		if (source.getCreateTime() != null) {
			target.setCreateTime(String.valueOf(Timestamp.valueOf(source.getCreateTime()).getTime()));
		}
		if (source.getModifyTime() != null) {
			target.setModifyTime(String.valueOf(Timestamp.valueOf(source.getModifyTime()).getTime()));
		}
		if (Integer.valueOf(1).equals(source.getPalmpromoterLogin())) {
			target.setIsPalmpromoter(true);
		} else {
			target.setIsPalmpromoter(false);
		}

		target.setIsUpdate(source.getIsUpdate());// 是否可以修改用户信息标志

		// cashInOut相关的
		target.setCashInOutEntrance("00");
		target.setMobileSupportEntrance("00");
		target.setIsCustomerAgree(false);
		target.setIsAgentAgree(false);

		if (source.getMoneyRole() != null) {
			MoneyRoleDto moneyRoleDto = source.getMoneyRole();
			if (Integer.valueOf(1).equals(moneyRoleDto.getIsCustomer())) {
				target.setCashInOutEntrance("01");
			}
			if (Integer.valueOf(1).equals(moneyRoleDto.getIsAgent())) {
				target.setMobileSupportEntrance("01");
			} else if (Integer.valueOf(2).equals(moneyRoleDto.getIsAgent())) {
				target.setMobileSupportEntrance("02");
			}
			if (moneyRoleDto.getIsCustomerAgree() != null) {
				target.setIsCustomerAgree(moneyRoleDto.getIsCustomerAgree());
			}
			if (moneyRoleDto.getIsAgentAgree() != null) {
				target.setIsAgentAgree(moneyRoleDto.getIsAgentAgree());
			}
		}

		// 组装一个头像
		QueryMemberPhoto queryMemberPhoto = new QueryMemberPhoto();
		queryMemberPhoto.setMemberId(source.getMemberId());
		queryMemberPhoto.setPhotoType("1");
		queryMemberPhoto.setMaxNumber(1);
		RespResult<List<MemberPhotoDto>> queryPhotoByMemberId = memberClient.queryPhotoByMemberId(queryMemberPhoto);
		LOG.info("MemberController.queryMemberDetailInfo------>memberClient.queryPhotoByMemberId param={},rlt={}",
				GsonUtils.toJson(queryMemberPhoto), GsonUtils.toJson(queryPhotoByMemberId));
		List<MemberPhotoDto> selfRlt = RespUtil.getRespResult(queryPhotoByMemberId);
		if (CollectionUtils.isNotEmpty(selfRlt) && StringUtils.isNotEmpty(selfRlt.get(0).getPhotoPath())) {
			target.setHeadPortrait(selfRlt.get(0).getPhotoPath());
		}

		target = hasLoanCard(source.getMemberId(), target);
		
		Integer virtualCardStatus = 0;
		if (linkAccount != null) {
			Integer accountStauts = linkAccount.getAccountStauts();
			if (Integer.valueOf(1).equals(accountStauts)) {
				virtualCardStatus = 1;
			} else if (Integer.valueOf(5).equals(accountStauts)) {
				virtualCardStatus = 2;
			}
		}
		target.setVirtualCardStatus(virtualCardStatus);
		
		return RespUtil.success(target);
	}
	
	private QueryMemberResp hasLoanCard(String memberId, QueryMemberResp target) {
		// 判断是否需要贷款卡片,M端配置的
		RespResult<List<HomeCard>> result= mCustomerManagementClient.getHomeCard();
		if(result != null && CollectionUtils.isNotEmpty(result.getData())){
			for (HomeCard homeCard : result.getData()) {
				if("00".equals(homeCard.getCardType()) && homeCard.getHiddenFlag() != null ){
					target.setLoanCard(homeCard.getHiddenFlag());
				} else if ("01".equals(homeCard.getCardType()) && homeCard.getHiddenFlag() != null ){
					target.setInvitationEntrance(homeCard.getHiddenFlag());
				}
			}
			return target;
		}
		return target;
	}

	@Override
	public RespResult<RiskReturnReCheckDto> createUpdateThresholdRisk(AmountThresholdDto req) {
		CommonReqDto commonReqDto = handleHeadService.getRiskCommonReqDto(RiskConstants.TransType.MODIFY,
				req.getBlackBox(), SubTransTypeEnum._1.getSubtransType());
		req.setReqDto(commonReqDto);
		RespResult<RiskReturnReCheckDto> rlt = memberClient.updateThreshold(req);
		
		RiskReturnReCheckDto riskResult = RespUtil.getRespResult(rlt);
		if(handleHeadService.riskHasReject(riskResult.getDisposal())){
			//throw handleHeadService.buildSpecialServiceException(RespCfrontCode.UPDATE_THRESHOLD_RISK_ERROR);
			throw new ServiceException(RespCfrontCode.UPDATE_THRESHOLD_RISK_ERROR);//BUG:5465 要求改的
		}
		
		return rlt;
	}
	
	@Override
	public RespResult<RiskReturnReCheckDto> updatePwdRisk(ModifyMemberPin req) {
		//M端重置PIN流程才改为不要 登录token的
		CommonReqDto commonReqDto = handleHeadService.getRiskCommonReqDtoNoToken(RiskConstants.TransType.MODIFY, req.getBlackBox(), SubTransTypeEnum._2.getSubtransType());
		req.setReqDto(commonReqDto);
		RespResult<RiskReturnReCheckDto> rlt = memberClient.updatePin(req);
		
		RiskReturnReCheckDto riskResult = RespUtil.getRespResult(rlt);
		if(handleHeadService.riskHasReject(riskResult.getDisposal())){
			//throw handleHeadService.buildSpecialServiceException(RespCfrontCode.UPDATE_PIN_RISK_ERROR);
			throw new ServiceException(RespCfrontCode.UPDATE_PIN_RISK_ERROR);//BUG:5465 要求改的
		}
		
		return rlt;
	}
	
	private RespResult<RiskReturnReCheckDto> updatePhone(ModifyMemberPhone req) {
		CommonReqDto commonReqDto = handleHeadService.getRiskCommonReqDtoNoToken(RiskConstants.TransType.MODIFY,
				req.getBlackBox(), SubTransTypeEnum._4.getSubtransType());
		req.setReqDto(commonReqDto);
		req.setTransTime(commonReqDto.getTransTime());
		RespResult<RiskReturnReCheckDto> rlt = memberClient.updatePhone(req);

		RiskReturnReCheckDto riskResult = RespUtil.getRespResult(rlt);
		if (handleHeadService.riskHasReject(riskResult.getDisposal())) {
			//throw handleHeadService.buildSpecialServiceException(RespCfrontCode.UPDATE_PHONE_RISK_ERROR);
			throw new ServiceException(RespCfrontCode.UPDATE_PHONE_RISK_ERROR);//BUG:5465 要求改的
		}
		return rlt;
	}
	
	@Override
	public boolean upgradeKYCGh(UpgradeReq req) {
		RespResult<Boolean> memberRlt = memberClient.upgrade(req);
		LOG.info("memberClient.modify rlt={}", GsonUtils.toJson(memberRlt));
		return RespUtil.getRespResult(memberRlt);
	}
	
	@Override
	public RespResult<Void> bvnVerifyNoAddAcc(BvnVerifyNoAddAccReq req) {
		BvnVerifyReq bvnVerifyReq = new BvnVerifyReq();
		bvnVerifyReq.setMemberId(req.getMemberId());
		bvnVerifyReq.setSmsCode(req.getSmsCode());
		bvnVerifyReq.setTransactionReference(req.getTransactionReference());
		bvnVerifyReq.setBvn(req.getBvn());
		bvnVerifyReq.setUpdateMemberInfo(true);
		bvnVerifyReq.setBlackBox(req.getBlackBox());
		bvnVerify(bvnVerifyReq);
		return RespUtil.success();
	}
	
	@Override
	public RespResult<String> checkPinTouch(CheckPinTouchTokenReq req) {
		boolean rlt = false;
		if ("0".equals(req.getPayPinOrTouchFlag())) {
			rlt = payService.checkPayPin(req.getMemberId(), req.getPayPinOrTouch());
		} else {
			rlt = payService.checkPayTouch(req.getOrderNo(), req.getPayPinOrTouch());
		}
		if(!rlt){
			throw new ServiceException(RespCfrontCode.ORDER_CHECK_ERROR);
		}
		String key = CHECK_PIN_TOUCH_PREFIX_+req.getOrderNo();
		String value = RandomUtils.getUUIDStr();
		redisService.setRedis(key, value, 180L);
		return RespUtil.success(value);
	}
	
	@Override
	public void checkPinTouchToken(String orderNo, String pinTouchToken) {
		ServiceException e = new ServiceException(RespCfrontCode.ORDER_TIME_OUT_DATA);
		if(CheckUtils.strIsNull(orderNo, pinTouchToken)){
			throw e;
		}
		String key = CHECK_PIN_TOUCH_PREFIX_+orderNo;
		String value = redisService.getRedisValue(key);
		if(!pinTouchToken.equals(value)){
			throw e;
		}
		redisService.delRedis(key);
	}
	
	@Override
	public RespResult<Void> updateMemInfoByApprove(UpdateMemInfoByApproveReq req, MemberDto memberDto) {
		if(!Integer.valueOf(1).equals(memberDto.getIsUpdate())){
			throw new ServiceException(RespCfrontCode.CAN_NOT_UPDATE_MEMBER_INFO);
		}
		RespResult<Void> rlt = mCustomerManagementClient.updateMemInfoByApprove(req);
		return rlt;
	}

	@Async
	@Override
	public void addMemberContacts(MemberDto member, String countryCode) {
		CountryCodeHolder.setCountryCode(countryCode);
		try {
			LOG.info("MemberServiceImpl==addMemberContacts==param={}", member);
			if("02".equals(member.getAgentType()) && member.getLastLoginTime() == null) {
				AddMemberContactsReq req = new AddMemberContactsReq();
				req.setMemberId(member.getAgentId());
				req.setContactsMemberId(member.getMemberId());
				req.setRequestSource("3");
				RespResult<Void> result = memberClient.addMemberContacts(req);
				LOG.info("MemberServiceImpl==addMemberContacts==result={}", result);
			}
		} catch (Exception e) {
			CountryCodeHolder.clearCountryCode();
			throw e;
		}
		CountryCodeHolder.clearCountryCode();
	}

	@Override
	public String createMemberQrCode(String memberId) {
		LOG.info("MemberServiceImpl==createMemberQrCode==param={}", memberId);
        
		String id = RandomUtils.getRandomReqMsgId();
		String memberQrCodeUrl = ConfigHandler.getInstance().getMemberQrCodeUrl();

		QrCode qrCode = new QrCode();
		qrCode.setId(id);
		qrCode.setQrJsonStr(memberId);
		RespResult<Integer> rlt = memberClient.create(qrCode);
		if(!Integer.valueOf(1).equals(RespUtil.getRespResult(rlt))){
			throw new ServiceException(RespCfrontCode.RECEIPT_CODE_REEOR);
		}
		
		return memberQrCodeUrl + id;
	}
     
	@Override
	public MemberContactsDto analysisMemberQrCode(String memberQrCodeUrl) {
		LOG.info("MemberServiceImpl==analysisMemberQrCode==param={}", memberQrCodeUrl);

		String qrCodeUrl = ConfigHandler.getInstance().getMemberQrCodeUrl();
		String qrCodeId = memberQrCodeUrl.substring(qrCodeUrl.length(), memberQrCodeUrl.length());
		if(StringUtil.isEmpty(qrCodeId)){
			throw new ServiceException(RespCfrontCode.RECEIPT_CODE_REEOR);
		}
		
		RespResult<QrCode> rlt = memberClient.queryById(qrCodeId);
		QrCode qrCode = RespUtil.getRespResult(rlt);
		if(qrCode == null || StringUtils.isBlank(qrCode.getQrJsonStr())){
			throw new ServiceException(RespCfrontCode.RECEIPT_CODE_REEOR);
		}
		
		String contactsMemberId = qrCode.getQrJsonStr();
		QueryMemberContactsConditions conditions = new QueryMemberContactsConditions();
		conditions.setMemberId(handleHeadService.getMemberId());
		conditions.setContactsMemberId(contactsMemberId);
		RespResult<MemberContactsDto> memberContactsResult = memberClient.queryMemberContacts(conditions);
		
		return RespUtil.getRespResult(memberContactsResult);
	}
	
	@Async
	@Override
	public void handleViralwaitRegister(String imei, String mobileNo, String memberId, String countryCode) {
		CountryCodeHolder.setCountryCode(countryCode);
		try {
			handleViralwait(imei, mobileNo, memberId, countryCode);
		} catch (Exception e) {
			CountryCodeHolder.clearCountryCode();
			throw e;
		}
		CountryCodeHolder.clearCountryCode();
	}

	private void handleViralwait(String imei, String mobileNo, String memberId, String countryCode) {
		try {
			handleHeadService.hasViralWaitlist();
		} catch (ServiceException e) {
			if (RespCfrontCode.NO_ACTIVE_INTERFACE_ERROR.getCode().equals(e.getCode())) {
				LOG.info("ViralWaitlist close");
				return;
			}
			throw e;
		}

		CreateOrQueryViralwaitUserReq queryUser = new CreateOrQueryViralwaitUserReq();
		queryUser.setPhone(mobileNo);
		LOG.info("memberClient.queryViralwaitUser param={}", queryUser);
		RespResult<ViralwaitUserDto> respResult = memberClient.queryViralwaitUser(queryUser);
		LOG.info("memberClient.queryViralwaitUser rlt={}", respResult);
		if (respResult == null || respResult.getData() == null) {
			return;
		}
		ViralwaitUserDto user = RespUtil.getRespResult(respResult);
		if(!imei.equals(user.getImei())){
			//踢人下线
			forceDeviceLogout(user.getImei());
		}
		if (!Integer.valueOf(1).equals(user.getSpareTire())) {
			// 不是注册用户不可送积分
			return;
		}
		if (Integer.valueOf(1).equals(user.getTire())) {
			// 已经注册过用户不可送积分
			return;
		}
		if (user.getPointNum() == null || user.getPointNum() < 1) {
			// 积分不足
			return;
		}

		LoyaltyRewardDto dto = new LoyaltyRewardDto();
		String uniqueIde = UUID.randomUUID().toString().replaceAll("-", "");
		dto.setUniqueIde(uniqueIde);
		dto.setBusinessOrderId(uniqueIde);
		Long[] groupIds = { 1L };
		dto.setGroupIds(groupIds);
		dto.setBusinessType("1");
		dto.setBusinessAmount(0L);
		dto.setPointsAmount(user.getPointNum());
		dto.setMemberId(memberId);
		dto.setCountryCode(countryCode);
		dto.setTransType("waitlist");
		String message = JsonUtil.fromObject(dto);
		LOG.info("活动用户赠送积分,param={},{}", message, mobileNo);
		Boolean isSend = loyaltyRewardProducer.send(dto);
		LOG.info("活动用户赠送积分,rlt={},{}", message, mobileNo);

		if (isSend) {
			LOG.info("memberClient.updateViralwaitUserTire param={}", queryUser);
			RespResult<Void> updateResult = memberClient.updateViralwaitUserTire(queryUser);
			LOG.info("memberClient.updateViralwaitUserTire rlt={}", updateResult);
		}
	}
	
	@Override
	public RespResult<Void> applyVirtualCardUpdateMember(ApplyVirtualCardReq req) {
		ModifyMember modifyMember = new ModifyMember();
		modifyMember.setMemberId(req.getMemberId());
		modifyMember.setPhone(req.getPhone());
		modifyMember.setFirstName(req.getFirstName());
		modifyMember.setLastName(req.getLastName());
		modifyMember.setFullName(req.getFirstName() + " " + req.getLastName());
		modifyMember.setNickName(req.getFirstName());
		CreatePaperwork createPaperwork = handleHeadService.buildCreatePaperwork(req.getCertificatesId(),
				req.getCertificatesType(), req.getPhotoPath());
		modifyMember.setPaperworks(createPaperwork == null ? null : Arrays.asList(createPaperwork));
		RespResult<Void> updateRlt = memberClient.modify(modifyMember);
		RespUtil.getRespResult(updateRlt);
		return setSelfie(req.getMemberId(), req.getSelfiePhotoPath());
	}
	
	@Override
	public RespResult<QueryRegisterInfoResp> queryRegisterInfo(String memberId) {
		RespResult<Member> respResult = memberClient.queryAllMemberByIds(memberId);
		Member member = RespUtil.getRespResult(respResult);
		if (member == null) {
			return RespUtil.success(null);
		}
		QueryRegisterInfoResp rlt = new QueryRegisterInfoResp();
		rlt.setFirstName(member.getFirstName());
		rlt.setLastName(member.getLastName());

		List<Paperwork> paperworks = member.getPaperworks();
		if (CollectionUtils.isNotEmpty(paperworks)) {
			Paperwork paperwork = paperworks.get(paperworks.size()-1);
			rlt.setCertificatesId(paperwork.getPaperworkId());
			rlt.setCertificatesType(paperwork.getPaperworkType());
			rlt.setPhotoPath(paperwork.getPaperworkUrl());
		}

		List<MemberPhoto> memberPhotos = member.getMemberPhoto();
		if (CollectionUtils.isNotEmpty(memberPhotos)) {
			for (MemberPhoto memberPhoto : memberPhotos) {
				if ("2".equals(memberPhoto.getPhotoType())) {
					rlt.setSelfiePhotoPath(memberPhoto.getPhotoPath());
				}
			}
		}

		return RespUtil.success(rlt);
	}
}
*/
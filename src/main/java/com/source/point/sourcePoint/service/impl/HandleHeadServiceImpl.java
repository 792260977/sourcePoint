/*package com.source.point.sourcePoint.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.transsnet.palmpay.cfront.config.ConfigHandler;
import com.transsnet.palmpay.cfront.dto.common.SignDto;
import com.transsnet.palmpay.cfront.dto.common.StartAppParamConvert;
import com.transsnet.palmpay.cfront.dto.member.CreatePaperwork;
import com.transsnet.palmpay.cfront.dto.member.MemberDto;
import com.transsnet.palmpay.cfront.dto.member.QueryMemberId;
import com.transsnet.palmpay.cfront.dto.member.RequestInfoResp;
import com.transsnet.palmpay.cfront.enums.CustomerServiceEnum;
import com.transsnet.palmpay.cfront.enums.GHIdRuleEnum;
import com.transsnet.palmpay.cfront.enums.HeadDeviceTypeEnum;
import com.transsnet.palmpay.cfront.enums.RespCfrontCode;
import com.transsnet.palmpay.cfront.enums.TZIdRuleEnum;
import com.transsnet.palmpay.cfront.mq.producer.RltNotifyRiskProducer;
import com.transsnet.palmpay.cfront.service.HandleHeadService;
import com.transsnet.palmpay.cfront.service.RedisService;
import com.transsnet.palmpay.cfront.service.feginclient.MemberClient;
import com.transsnet.palmpay.cfront.utils.CheckUtils;
import com.transsnet.palmpay.cfront.utils.GsonUtils;
import com.transsnet.palmpay.cfront.utils.RSAUtils;
import com.transsnet.palmpay.common.CountryCodeHolder;
import com.transsnet.palmpay.common.RespCode;
import com.transsnet.palmpay.common.RespResult;
import com.transsnet.palmpay.common.RespUtil;
import com.transsnet.palmpay.common.ServiceException;
import com.transsnet.palmpay.enums.CountryEnum;
import com.transsnet.palmpay.riskdto.common.request.CommonReqDto;
import com.transsnet.palmpay.riskdto.common.request.RiskReqDto;
import com.transsnet.palmpay.riskdto.constants.RiskConstants;
import com.transsnet.palmpay.riskdto.sync.request.SyncResultDto;
import com.transsnet.palmpay.util.DateUtil;
import com.transsnet.palmpay.util.RandomUtils;

@Service
public class HandleHeadServiceImpl implements HandleHeadService {
	
	private static final Logger LOG = LoggerFactory.getLogger(HandleHeadServiceImpl.class);
	
	public static final String PROMOTER_ = "PROMOTER_";//promoter设备imei前缀,如 imei="PROMOTER_e1161fe05896ea9d721QACRG623C9";

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private MemberClient memberClient;
	
	@Autowired
	private RltNotifyRiskProducer rltNotifyRiskProducer;
	
	@Override
	public String getMemberId(){
		HttpServletRequest request = getRequest();
		String token = StartAppParamConvert.getHeadToken(request);
		String key = CountryCodeHolder.getCountryCode() + StartAppBasicServiceImpl.START_APP_PREFIX + token;
		String memberId = redisService.getRedisValue(key);
		if (StringUtils.isBlank(memberId)) {
			LOG.info("=====TOKEN_TIMEOUT======MemberServiceImpl.getMemberId  token={}", token);
			throw new ServiceException(RespCfrontCode.TOKEN_TIMEOUT);
		}
		return memberId;
	}
	
	@Override
	public SignDto checkSign() {
		HttpServletRequest request = getRequest();
		SignDto signDto = StartAppParamConvert.getStartAppHeadInfo(request);
		if (null == signDto) {
			throw new ServiceException(RespCfrontCode.PARAM_NULL);
		}
		try {
			if (!RSAUtils.verify(StartAppParamConvert.startAppSignDataConvert(signDto).getBytes(), signDto.getSign(), getSubClientVer(), getAppSource())) {
				throw new ServiceException(RespCfrontCode.VERIFY_SIGN_FAIL);
			}
		} catch (Exception e) {
			throw new ServiceException(RespCfrontCode.VERIFY_SIGN_FAIL);
		}
		LOG.info("StartAppBasicController.checkSign signDto={}", GsonUtils.toJson(signDto));
		return signDto;
	}
	
	@Override
	public void checkMySelfDevice(String imei){
		HttpServletRequest request = getRequest();
		String deviceId = StartAppParamConvert.getHeadDeviceId(request);
		if(deviceId.equals(imei)){
			throw new ServiceException(RespCfrontCode.NO_OWN_DEVICE_ERROR);
		}
	}
	
	@Override
	public RequestInfoResp getRequestInfo() {
		HttpServletRequest request = getRequest();
		LOG.info("x-forwarded-for:{}",request.getHeader("x-forwarded-for"));
		String ipAddress = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ipAddress) || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				try {
					InetAddress inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
					LOG.info("StartAppBasicController.getRequestInfo exception={}", e);
				}
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (!StringUtils.isBlank(ipAddress) && ipAddress.indexOf(",") > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		String deviceId = StartAppParamConvert.getHeadDeviceId(request);
		Double lng = StartAppParamConvert.getHeadLng(request);
		Double lat = StartAppParamConvert.getHeadLat(request);
		RequestInfoResp rlt = new RequestInfoResp();
		rlt.setIpAddress(ipAddress);
		rlt.setDeviceFinger(deviceId);
		rlt.setImei(deviceId);
		rlt.setLng(lng);
		rlt.setLat(lat);
		return rlt;
	}
	
	@Override
	public MemberDto getMemberInfo() {
		String memberId = getMemberId();
		QueryMemberId req = new QueryMemberId();
		req.setMemberId(memberId);
		RespResult<MemberDto> result = memberClient.queryMemberById(req);
		MemberDto source = RespUtil.getRespResult(result);
		if(source == null){
			throw new ServiceException(RespCfrontCode.NO_MEMBER_ERROR);
		}
		return source;
	}
	
	@Override
	public SignDto getHeadInfo() {
		return StartAppParamConvert.getHeadInfo(getRequest());
	}
	
	private HttpServletRequest getRequest(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	@Override
	public String getSystemType() {
		String deviceType = StartAppParamConvert.getHeadDeviceType(getRequest());
		LOG.info("HandleHeadServiceImpl-->getSystemType deviceType={}", deviceType);
		 RiskConstants.SystemTypeEnum systemType = null;
		if(HeadDeviceTypeEnum.ANDROID.getCode().equalsIgnoreCase(deviceType)){
			systemType = RiskConstants.SystemTypeEnum.ANDROID;
		} else if(HeadDeviceTypeEnum.IOS.getCode().equalsIgnoreCase(deviceType)){
			systemType = RiskConstants.SystemTypeEnum.IOS;
		} else if(HeadDeviceTypeEnum.WEB.getCode().equalsIgnoreCase(deviceType)){
			systemType = RiskConstants.SystemTypeEnum.WEB;
		} else {
			throw new ServiceException(RespCfrontCode.DEVICE_TYPE_ERROR);
		}
		return systemType.getCode();
	}
	
	@Override
	public Integer getSubClientVer() {
		HttpServletRequest request = getRequest();
		SignDto signDto = StartAppParamConvert.getStartAppHeadInfo(request);
		if (null == signDto) {
			throw new ServiceException(RespCfrontCode.PARAM_NULL);
		}
		String[] clientVers = signDto.getClientVer().split("&");// 和前端约定下划线切割
		if (clientVers.length == 1) {
			return 0;// 老版本
		}
		return Integer.valueOf(clientVers[1]);
	}
	
	@Override
	public CommonReqDto getRiskCommonReqDto(RiskConstants.TransType transType, String blackBox,String subTransType) {
		String memberId = getMemberId();
		return buildCommonReqDto(transType, memberId, blackBox, subTransType);
	}

	private CommonReqDto buildCommonReqDto(RiskConstants.TransType transType, String memberId, String blackBox, String subTransType) {
		String riskFlowId = RandomUtils.getRandomReqMsgId();
		String riskTransTime = DateUtil.getDateTimeByCountryCode(ConfigHandler.getInstance().getCOUNTRY_CODE());
		CommonReqDto commonReqDto = new CommonReqDto();
		commonReqDto.setMemberId(memberId);
		commonReqDto.setSystemType(getSystemType());
		commonReqDto.setFlowId(riskFlowId);
		commonReqDto.setTransTime(riskTransTime);
		commonReqDto.setTransType(transType.getCode());
		commonReqDto.setBlackBox(blackBox);
		commonReqDto.setSubTransType(subTransType);
		RequestInfoResp requestInfoResp = getRequestInfo();
		commonReqDto.setIpAddress(requestInfoResp.getIpAddress());
		return commonReqDto;
	}
	
	@Override
	public CommonReqDto getRiskCommonReqDtoNoToken(RiskConstants.TransType transType, String blackBox, String subTransType) {
		return buildCommonReqDto(transType, null, blackBox, subTransType);
	}
	
	@Override
	public void handleRiskResult(String payStatus, CommonReqDto commonReqDto) {
		LOG.info("HandleHeadServiceImpl-->handleRiskResult payStatus={},commonReqDto={}", payStatus, GsonUtils.toJson(commonReqDto));
		if (commonReqDto == null) {
			return;
		}
		
		SyncResultDto syncResultDto = new SyncResultDto();
		syncResultDto.setPayStatus(payStatus);
		
		commonReqDto.setTransTime(DateUtil.getDateTimeByCountryCode(ConfigHandler.getInstance().getCOUNTRY_CODE()));
		
		RiskReqDto<SyncResultDto> req = new RiskReqDto<>();
		req.setCommonReqDto(commonReqDto);
		req.setBody(syncResultDto);
		
		LOG.info("rltNotifyRiskProducer mq begin param={}", GsonUtils.toJson(req));
		Boolean flag = rltNotifyRiskProducer.send(req);
		LOG.info("rltNotifyRiskProducer mq end rlt={}", flag);
	}
	
	@Override
	public CommonReqDto handleRiskRegist(String memberId, CommonReqDto commonReqDto) {
		if(StringUtils.isBlank(memberId) || commonReqDto == null){
			return commonReqDto;
		}
		commonReqDto.setMemberId(memberId);
		return commonReqDto;
	}
	
	@Override
	public CreatePaperwork buildCreatePaperwork(String certificatesId, String certificatesType, String certificatesPhotoPath) {
		CreatePaperwork createPaperwork = null;
		if (CheckUtils.strIsNull(certificatesId, certificatesType)) {
			return createPaperwork;
		}
		if (CountryEnum.GH.toString().equals(ConfigHandler.getInstance().getCOUNTRY_CODE())) {
			createPaperwork = buildGhCreateExtend(createPaperwork, certificatesId, certificatesType, certificatesPhotoPath);
		} else {
			createPaperwork = buildTzCreateExtend(createPaperwork, certificatesId, certificatesType, certificatesPhotoPath);
		}
		LOG.info("createPaperwork ={}", GsonUtils.toJson(createPaperwork));
		return createPaperwork;
	}
	
	private CreatePaperwork buildGhCreateExtend(CreatePaperwork createPaperwork, String certificatesId,
			String certificatesType, String certificatesPhotoPath) {
		GHIdRuleEnum gHIdRuleEnum = GHIdRuleEnum.getGHIdRuleEnum(certificatesType);
		if (gHIdRuleEnum == null) {
			return createPaperwork;
		}

		boolean rs = Pattern.compile(gHIdRuleEnum.getRule()).matcher(certificatesId).matches();
		if (!rs) {
			throw new ServiceException(gHIdRuleEnum.getError());
		}

		if (gHIdRuleEnum.getAuthLevel().equals("1")) {
			createPaperwork = new CreatePaperwork(certificatesId, certificatesType, certificatesPhotoPath, 0, null, null);
		} else if (gHIdRuleEnum.getAuthLevel().equals("2")) {
			createPaperwork = new CreatePaperwork(certificatesId, certificatesType, certificatesPhotoPath, 1,
					Timestamp.valueOf(LocalDateTime.now()), null);
		} else {
			throw new ServiceException(RespCfrontCode.PARAM_ERROR);
		}
		
		
		createExtend = new CreateExtend();
		if (gHIdRuleEnum.equals(GHIdRuleEnum.ASSOCIATION)) {
			createExtend.setAssociationId(certificatesId);
		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.DRIVER)) {
			createExtend.setDriverLicense(certificatesId);
		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.EMPLOYEE)) {
			createExtend.setEmployeeId(certificatesId);
//		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.NHIS)) {
//			createExtend.setNhis(certificatesId);
//		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.NIA)) {
//			createExtend.setNia(certificatesId);
		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.PASSPORT)) {
			createExtend.setPassport(certificatesId);
		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.SSNIT)) {
			createExtend.setSsnitId(certificatesId);
		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.STUDENT)) {
			createExtend.setStudentId(certificatesId);
		} else if (gHIdRuleEnum.equals(GHIdRuleEnum.VOTER)) {
			createExtend.setVoterId(certificatesId);
		} else {
			createExtend = null;
		}
		
		return createPaperwork;
	}
	
	private CreatePaperwork buildTzCreateExtend(CreatePaperwork createPaperwork, String certificatesId,
			String certificatesType, String certificatesPhotoPath) {
		TZIdRuleEnum tZIdRuleEnum = TZIdRuleEnum.getTZIdRuleEnum(certificatesType);
		if (tZIdRuleEnum == null) {
			return createPaperwork;
		}
		
		boolean rs = Pattern.compile(tZIdRuleEnum.getRule()).matcher(certificatesId).matches();
		if (!rs) {
			throw new ServiceException(tZIdRuleEnum.getError());
		}
		
		if (tZIdRuleEnum.getAuthLevel().equals("1")) {
			createPaperwork = new CreatePaperwork(certificatesId, certificatesType, certificatesPhotoPath, 0, null, null);
		} else {
			throw new ServiceException(RespCfrontCode.PARAM_ERROR);
		}
		
		
		createExtend = new CreateExtend();
		if (tZIdRuleEnum.equals(TZIdRuleEnum.DRIVER)) {
			createExtend.setDriverLicense(certificatesId);
		} else if (tZIdRuleEnum.equals(TZIdRuleEnum.NATIONAL)) {
			createExtend.setNationalId(certificatesId);
		} else if (tZIdRuleEnum.equals(TZIdRuleEnum.PASSPORT)) {
			createExtend.setPassport(certificatesId);
		} else if (tZIdRuleEnum.equals(TZIdRuleEnum.VOTER)) {
			createExtend.setVoterId(certificatesId);
		} else {
			createExtend = null;
		}
		
		return createPaperwork;
	}
	
	@Override
	public ServiceException buildSpecialServiceException(RespCode respCode) {
		if(respCode == null){
			LOG.info("buildSpecialServiceException data error");
			throw new ServiceException(RespCfrontCode.DATA_ERROR);
		}
		String msg = respCode.getMsg()+CustomerServiceEnum.getDescByValue(ConfigHandler.getInstance().getCOUNTRY_CODE());
		return new ServiceException(respCode.getCode(), msg);
	}
	
	// 活动过期返回统一结果
	@Override
	public void hasViralWaitlist() {
		boolean rlt = false;
		String country = CountryCodeHolder.getCountryCode();
		if (CountryEnum.NG.toString().equalsIgnoreCase(country) && (!ConfigHandler.getInstance().isViralWaitlistNG())) {
			rlt = true;
		} else if (CountryEnum.GH.toString().equalsIgnoreCase(country)
				&& (!ConfigHandler.getInstance().isViralWaitlistGH())) {
			rlt = true;
		} else if (CountryEnum.TZ.toString().equalsIgnoreCase(country)
				&& (!ConfigHandler.getInstance().isViralWaitlistTZ())) {
			rlt = true;
		}
		if (rlt) {
			throw new ServiceException(RespCfrontCode.NO_ACTIVE_INTERFACE_ERROR);
		}
	}
	
	@Override
	public int getAppSource() {
		HttpServletRequest request = getRequest();
		String deviceId = StartAppParamConvert.getHeadDeviceId(request);
		LOG.info("getAppSource deviceId={}", deviceId);
		if (StringUtils.isBlank(deviceId)) {
			throw new ServiceException(RespCfrontCode.NO_DEVICE_ERROR);
		}
		return deviceId.startsWith(PROMOTER_) ? 1 : 0;
	}
	
	@Override
	public boolean riskHasReject(String disposal) {
		List<String> rejectList = Arrays.asList(RiskConstants.RiskDisposal.REJECT.getCode(),
				RiskConstants.RiskDisposal.REJECT_FREEZ_MEMBER.getCode(),
				RiskConstants.RiskDisposal.REJECT_FREEZ_ACCOUNT.getCode(), RiskConstants.RiskDisposal.HANGUP.getCode());
		if (rejectList.contains(disposal)) {
			return true;
		}
		return false;
	}

}
*/
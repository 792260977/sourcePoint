/*package com.source.point.sourcePoint.service;

import java.util.List;

import com.transsnet.palmpay.cfront.dto.account.ApplyVirtualCardReq;
import com.transsnet.palmpay.cfront.dto.account.LinkAccount;
import com.transsnet.palmpay.cfront.dto.marketing.IncentiveRecordDetailDto;
import com.transsnet.palmpay.cfront.dto.marketing.IncentiveRecordPageReq;
import com.transsnet.palmpay.cfront.dto.member.AmountThresholdDto;
import com.transsnet.palmpay.cfront.dto.member.AppResetPinReq;
import com.transsnet.palmpay.cfront.dto.member.BenefitDto;
import com.transsnet.palmpay.cfront.dto.member.BvnVerifyAndAddAccReq;
import com.transsnet.palmpay.cfront.dto.member.BvnVerifyNoAddAccReq;
import com.transsnet.palmpay.cfront.dto.member.BvnVerifyReq;
import com.transsnet.palmpay.cfront.dto.member.ChangeNameReq;
import com.transsnet.palmpay.cfront.dto.member.ChangePhoneReq;
import com.transsnet.palmpay.cfront.dto.member.CheckPinTouchTokenReq;
import com.transsnet.palmpay.cfront.dto.member.ForceLogoutReq;
import com.transsnet.palmpay.cfront.dto.member.MemberContactsDto;
import com.transsnet.palmpay.cfront.dto.member.MemberDto;
import com.transsnet.palmpay.cfront.dto.member.ModifyMemberPin;
import com.transsnet.palmpay.cfront.dto.member.QueryBenefit;
import com.transsnet.palmpay.cfront.dto.member.QueryInvitListResp;
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
import com.transsnet.palmpay.cfront.dto.validator.BvnInfoResp;
import com.transsnet.palmpay.common.PageDto;
import com.transsnet.palmpay.common.RespResult;
import com.transsnet.palmpay.riskdto.common.recheck.RiskReturnReCheckDto;

public interface MemberService {

	RespResult<Void> setPin(String memberId, SetPinReq req);

	RespResult<RiskReturnReCheckDto> changePin(UpdatePinReq req);
	
	RespResult<Void> resetPinSelfile(String memberId, ResetPinSelfileReq req);
	
	RespResult<Void> resetPinNewPassword(String memberId, ResetPinReq req);

	RespResult<Void> setSelfie(String memberId, String selfieUrlBase);
	
	RespResult<Boolean> checkFaceSetSelfie(String selfieUrlBase);

	RespResult<String> getSetSelfieByType(QueryMemberPhoto req);

	BvnInfoResp bvnVerify(BvnVerifyReq req);

	RespResult<Void> createBenefit(String memberId, String orderId, String nickName,String benefitType);

	RespResult<RiskReturnReCheckDto> changePhone(ChangePhoneReq req);

	RespResult<Void> changeName(ChangeNameReq req);
	
	RespResult<Void> forceSomeDeviceLogout(ForceLogoutReq req);
	
	RespResult<Void> forceDeviceLogout(String deviceId);

	RespResult<Void> setSetSelfieByType(QueryMemberPhoto req);

	RespResult<RiskReturnReCheckDto> appResetPin(AppResetPinReq req);

	RespResult<String> queryPhotoByIdOrPhone(QueryPhotoReq req);

	RespResult<Void> bvnVerifyAndAddAcc(MemberDto memberDto, BvnVerifyAndAddAccReq req);

	RespResult<PageDto<IncentiveRecordDetailDto>> recordList(IncentiveRecordPageReq req);

	RespResult<Void> loginForceAllDeviceLogoutExceptOne(String deviceId, String memberId, Integer appSource);

	RespResult<PageDto<QueryInvitListResp>> queryInvitList(QueryRecommendReq req);

	RespResult<List<BenefitDto>> queryBenefitByMemberId(QueryBenefit req, MemberDto memberDto);
	
	RespResult<QueryMemberResp> queryMemberDetailInfo(MemberDto memberDto, LinkAccount linkAccount);

	RespResult<RiskReturnReCheckDto> createUpdateThresholdRisk(AmountThresholdDto req);
	
	RespResult<RiskReturnReCheckDto> updatePwdRisk(ModifyMemberPin req);
	
	boolean upgradeKYCGh(UpgradeReq req);

	RespResult<Void> bvnVerifyNoAddAcc(BvnVerifyNoAddAccReq req);

	RespResult<String> checkPinTouch(CheckPinTouchTokenReq req);

	void checkPinTouchToken(String orderNo, String pinTouchToken);

	RespResult<Void> updateMemInfoByApprove(UpdateMemInfoByApproveReq req, MemberDto memberDto);
	
	void addMemberContacts(MemberDto member, String countryCode);

	String createMemberQrCode(String memberId);
	
	MemberContactsDto analysisMemberQrCode(String memberQrCodeUrl);

	void handleViralwaitRegister(String imei, String mobileNo, String memberId, String countryCode);
	
	RespResult<Void> applyVirtualCardUpdateMember(ApplyVirtualCardReq req);

	RespResult<QueryRegisterInfoResp> queryRegisterInfo(String memberId);
}
*/
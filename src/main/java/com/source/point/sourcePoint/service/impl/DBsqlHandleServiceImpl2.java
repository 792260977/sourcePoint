/*package com.source.point.sourcePoint.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.source.point.sourcePoint.common.ResultCode;
import com.source.point.sourcePoint.common.ServiceException;
import com.source.point.sourcePoint.dto.dbsqlhandle.AutoSqlParam;
import com.source.point.sourcePoint.dto.dbsqlhandle.AutoSqlReq;
import com.source.point.sourcePoint.enums.ResouseType;
import com.source.point.sourcePoint.service.DBsqlHandleService;

import io.swagger.annotations.ApiModelProperty;

@Service
public class DBsqlHandleServiceImpl2 implements DBsqlHandleService {

	private static final String EGC_COURT="00000000000000000000000000000000";
	private static final String SCP_COURT="11111111111111111111111111111111";
	private static final String SPLIT=",";
	private static final String SQL="SQL";
	private static final String ROLLBACKSQL="ROLLBACKSQL";
	
	
	@Override
	public Map<String, List<String>> autoSql(AutoSqlReq req) {
		Integer type = req.getResouseType();
		Map<String, List<String>> map =  null;
		if (ResouseType.TYPE_1.getType().equals(String.valueOf(type))) {
			map = autoSqlType1(req);
		} else if (ResouseType.TYPE_2.getType().equals(String.valueOf(type))) {
			map = autoSqlType2(req);
		} else if (ResouseType.TYPE_3.getType().equals(String.valueOf(type))) {
			map = autoSqlType3(req);
		} else {
			throw new ServiceException(ResultCode.UNKNOWN_ERROR, "这段代码还没写");
		}
		return map;
		// TODO
//		handleRlt(map);
	}

	private void handleRlt(Map<String, List<String>> map) {

	}
	
	
	private Map<String, List<String>> autoSqlType1(AutoSqlReq req) {
		List<String> sqlList = new ArrayList<>();
		List<String> rollbackSqlList = new ArrayList<>();
		List<AutoSqlParam> params = getParam(req);
		for (AutoSqlParam source : params) {
			String resourceSql = "INSERT INTO \"um\".\"resource\"(\"uuid\", \"resource_type\", \"resource_name\", "
					+ "\"app_code\", \"menu_code\", \"icon\", \"parent_resource_uuid\", \"resource_group\", "
					+ "\"resource_url\", \"button\", \"sort\", \"remark\", \"user_type\", \"delete_flag\", "
					+ "\"create_time\", \"create_user\", \"update_time\", \"update_user\", \"court_uuid\") "
					+ "VALUES (replace(public.uuid_generate_v4() || '', '-', ''), '1', '" + source.getResourceName()// resource_name
					+ "', '" + source.getAppCode()// app_code
					+ "', NULL, NULL, NULL, 't', NULL, 'f', " + source.getSort()// sort
					+ ", NULL, NULL, 1, now(), 'sys_init', now(), 'sys_init', '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "');";

			String resourceRollbackSql = "delete from um.resource where delete_flag='1' and court_uuid = '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and app_code = '" + source.getAppCode()// app_code
					+ "' and resource_type='1';";

			String authoritySql = "INSERT INTO um.authority ( \"uuid\", \"role_uuid\", \"resource_uuid\", \"delete_flag\", \"create_time\", "
					+ "\"create_user\", \"update_time\", \"update_user\", \"court_uuid\") "
					+ "SELECT replace(public.uuid_generate_v4() || '', '-', ''), "
					+ "a.uuid, b.uuid, 1, now(), 'sys_init', now(), 'sys_init', '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' FROM um.ROLE a, um.resource b WHERE a.role_type = 0 and a.court_uuid = '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and b.delete_flag='1' and b.court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and b.app_code = '" + source.getAppCode()// app_code
					+ "' and b.resource_type='1';";

			String authorityRollbackSql = "delete from um.authority where resource_uuid in (select uuid from um.resource "
					+ "where delete_flag='1' and court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and app_code = '" + source.getAppCode()// app_code
					+ "' and resource_type='1';";

			// 顺序不能乱
			sqlList.add(resourceSql);
			sqlList.add(authoritySql);
			rollbackSqlList.add(authorityRollbackSql);
			rollbackSqlList.add(resourceRollbackSql);
			// 顺序不能乱
		}
		Map<String, List<String>> map = new HashMap<>();
		map.put(SQL, sqlList);
		map.put(ROLLBACKSQL, rollbackSqlList);
		return map;
	}

	private Map<String, List<String>> autoSqlType2(AutoSqlReq req) {
		List<String> sqlList = new ArrayList<>();
		List<String> rollbackSqlList = new ArrayList<>();
		List<AutoSqlParam> params = getParam(req);
		for (AutoSqlParam source : params) {

			String resourceSql = "INSERT INTO \"um\".\"resource\"(\"uuid\", \"resource_type\", \"resource_name\", "
					+ "\"app_code\", \"menu_code\", \"icon\", \"parent_resource_uuid\", \"resource_group\", "
					+ "\"resource_url\", \"button\", \"sort\", \"remark\", \"user_type\", \"delete_flag\", "
					+ "\"create_time\", \"create_user\", \"update_time\", \"update_user\", \"court_uuid\") "
					+ "VALUES (replace(public.uuid_generate_v4() || '', '-', ''), '" + "2" + "', '"
					+ source.getResourceName()// resource_name
					+ "', '" + source.getAppCode()// app_code
					+ "', '" + source.getMenuCode()// menu_code
					+ "', " + source.getIcon()// icon
					+ ", "
					// 是否还有上级菜单
					+ (source.getParentMenuCode().equals("NULL") ? "NULL"
							: ("(select uuid from um.resource where resource_type = '2' and app_code = '"
									+ source.getAppCode()// app_code
									+ "' and menu_code = '" + source.getParentMenuCode()// parent_menu_code
									+ "' and delete_flag = '1' and court_uuid = '"
									+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
									+ "' LIMIT 1)"))
					+ ", '" + source.getResourceGroup()// resource_group
					+ "', '" + source.getResourceUrl()// resource_url
					+ "', '" + source.getButton()// button
					+ "', " + source.getSort()// sort
					+ ", NULL, NULL, 1, now(), 'sys_init', now(), 'sys_init', '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "');";

			String resourceRollbackSql = "delete from um.resource where delete_flag='1' and court_uuid = '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and app_code = '" + source.getAppCode()// app_code
					+ "' and menu_code = '" + source.getMenuCode()// menu_code
					+ "';";

			String authoritySql = "INSERT INTO um.authority ( \"uuid\", \"role_uuid\", \"resource_uuid\", \"delete_flag\", \"create_time\", "
					+ "\"create_user\", \"update_time\", \"update_user\", \"court_uuid\") "
					+ "SELECT replace(public.uuid_generate_v4() || '', '-', ''), "
					+ "a.uuid, b.uuid, 1, now(), 'sys_init', now(), 'sys_init', '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' FROM um.ROLE a, um.resource b WHERE a.role_type = 0 and a.court_uuid = '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and b.delete_flag='1' and b.court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and b.app_code = '" + source.getAppCode()// app_code
					+ "' and b.menu_code =  '" + source.getMenuCode()// menu_code
					+ "';";

			String authorityRollbackSql = "delete from um.authority where resource_uuid in (select uuid from um.resource "
					+ "where delete_flag='1' and court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and app_code = '" + source.getAppCode()// app_code
					+ "' and menu_code =  '" + source.getMenuCode()// menu_code
					+ "';";

			// 顺序不能乱
			sqlList.add(resourceSql);
			sqlList.add(authoritySql);
			rollbackSqlList.add(authorityRollbackSql);
			rollbackSqlList.add(resourceRollbackSql);
			// 顺序不能乱
		}
		Map<String, List<String>> map = new HashMap<>();
		map.put(SQL, sqlList);
		map.put(ROLLBACKSQL, rollbackSqlList);
		return map;
	}

	private Map<String, List<String>> autoSqlType3(AutoSqlReq req) {

		List<String> sqlList = new ArrayList<>();
		List<String> rollbackSqlList = new ArrayList<>();
		List<AutoSqlParam> params = getParam(req);
		for (AutoSqlParam source : params) {
			
			String resourceSql = "INSERT INTO \"um\".\"resource\"(\"uuid\", \"resource_type\", \"resource_name\", "
					+ "\"app_code\", \"menu_code\", \"icon\", \"parent_resource_uuid\", \"resource_group\", "
					+ "\"resource_url\", \"button\", \"sort\", \"remark\", \"user_type\", \"delete_flag\", "
					+ "\"create_time\", \"create_user\", \"update_time\", \"update_user\", \"court_uuid\") "
					+ "VALUES (replace(public.uuid_generate_v4() || '', '-', ''), '3', '" + source.getResourceName()// resource_name
					+ "', 'allsystem', NULL, NULL, NULL, 'f', '" + source.getResourceUrl()// resource_url
					+ "', 'f', NULL, NULL, NULL, 1, now(), 'sys_init', now(), 'sys_init', '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "');";

			String resourceRollbackSql = "delete from um.resource where delete_flag='1' and court_uuid = '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and app_code = 'allsystem' and resource_url = '" + source.getResourceUrl()// resource_url
					+ "';";
			

			String authoritySql = "INSERT INTO um.authority ( \"uuid\", \"role_uuid\", \"resource_uuid\", \"delete_flag\", \"create_time\", "
					+ "\"create_user\", \"update_time\", \"update_user\", \"court_uuid\") "
					+ "SELECT replace(public.uuid_generate_v4() || '', '-', ''), "
					+ "a.uuid, b.uuid, 1, now(), 'sys_init', now(), 'sys_init', '"
					+ (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' FROM um.ROLE a, um.resource b WHERE a.role_type = 2 and a.role_name = '" + source.getRoleName()// role_name
					+ "' and a.delete_flag = '1' and a.court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and b.delete_flag='1' and b.court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and b.app_code = 'allsystem' and b.resource_url =  '" + source.getResourceUrl()// resource_url
					+ "';";
			
			String authorityRollbackSql = "delete from um.authority where resource_uuid in (select uuid from um.resource "
					+ "where delete_flag='1' and court_uuid = '" + (req.getHasEgc() ? EGC_COURT : SCP_COURT)// 云端和小区端
					+ "' and app_code = 'allsystem' and resource_url =  '" + source.getResourceUrl()// resource_url
					+ "';";

			// 顺序不能乱
			sqlList.add(resourceSql);
			sqlList.add(authoritySql);
			rollbackSqlList.add(authorityRollbackSql);
			rollbackSqlList.add(resourceRollbackSql);
			// 顺序不能乱
		}
		Map<String, List<String>> map = new HashMap<>();
		map.put(SQL, sqlList);
		map.put(ROLLBACKSQL, rollbackSqlList);
		return map;
	}

	
	private List<AutoSqlParam> getParam(AutoSqlReq req) {
		List<AutoSqlParam> rlt = new ArrayList<>();
		Integer type = req.getResouseType();
		List<String> list = req.getList();
		for (int i = 0; i < list.size(); i++) {
			String[] ss = list.get(i).split(SPLIT);
			if (ResouseType.TYPE_1.getType().equals(String.valueOf(type))) {
				if (ss.length != 3) {
					throw new ServiceException(ResultCode.UNKNOWN_ERROR, "resouse=1时,数组长度要为3,位置:" + i);
				}
				rlt.add(new AutoSqlParam(ss[0].trim(), ss[1].trim(), ss[2].trim()));
			} else if (ResouseType.TYPE_2.getType().equals(String.valueOf(type))) {
				if (ss.length != 9) {
					throw new ServiceException(ResultCode.UNKNOWN_ERROR, "resouse=2时,数组长度要为3,位置:" + i);
				}
				rlt.add(new AutoSqlParam(ss[0].trim(), ss[1].trim(), ss[2].trim(), ss[3].trim(), ss[4].trim(),
						ss[5].trim(), ss[6].trim(), ss[7].trim(), ss[8].trim()));
			} else if (ResouseType.TYPE_3.getType().equals(String.valueOf(type))) {
				if (ss.length != 3) {
					throw new ServiceException(ResultCode.UNKNOWN_ERROR, "resouse=3时,数组长度要为3,位置:" + i);
				}
				AutoSqlParam autoSqlParam = new AutoSqlParam();
				autoSqlParam.setResourceName(ss[0].trim());
				autoSqlParam.setResourceUrl(ss[1].trim());
				autoSqlParam.setRoleName(ss[2].trim());
				rlt.add(autoSqlParam);
			} else {
				throw new ServiceException(ResultCode.UNKNOWN_ERROR, "这段代码还没写");
			}
		}
		return rlt;
	}
}*/
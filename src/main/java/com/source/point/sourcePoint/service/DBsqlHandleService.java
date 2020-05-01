package com.source.point.sourcePoint.service;

import java.util.List;

import com.source.point.sourcePoint.dto.dbsqlhandle.AutoSqlReq;
import com.source.point.sourcePoint.dto.excel.AutoSqlExcelModel;

public interface DBsqlHandleService {

	void autoSql(AutoSqlReq req);

	List<String> buildSqlDataList(List<AutoSqlExcelModel> lists);

}

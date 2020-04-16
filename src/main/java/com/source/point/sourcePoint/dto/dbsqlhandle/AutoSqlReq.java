package com.source.point.sourcePoint.dto.dbsqlhandle;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.source.point.sourcePoint.common.SerializedEntity;

import io.swagger.annotations.ApiModelProperty;

public class AutoSqlReq extends SerializedEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "脚本名称", required = true)
	@NotBlank
	private String sqlName;

	@ApiModelProperty(value = "资源类型:1-应用程序,2-程序功能/菜单,3-应用服务/外部接口", required = true)
	@Min(value = 1, message = "最小值为1")
	@Max(value = 3, message = "最大值为3")
	@NotNull
	private Integer resouseType;

	@ApiModelProperty(value = "sql参数拼接顺序逗号分隔  \n"
			+ "Type_1(sort,resource_name,app_code); \n"
			+ "Type_2(sort,button,resource_group,resource_name,app_code,parent_menu_code,menu_code,resource_url,icon); \n"
			+ "Type_3(resource_name,resource_url,role_name)"
			, required = true)
	@NotEmpty
	private List<String> list;

	@ApiModelProperty(value = "是否是小区端,默认是")
	private Boolean hasEgc = true;

	public String getSqlName() {
		return sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

	public Integer getResouseType() {
		return resouseType;
	}

	public void setResouseType(Integer resouseType) {
		this.resouseType = resouseType;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Boolean getHasEgc() {
		return hasEgc;
	}

	public void setHasEgc(Boolean hasEgc) {
		this.hasEgc = hasEgc;
	}
}

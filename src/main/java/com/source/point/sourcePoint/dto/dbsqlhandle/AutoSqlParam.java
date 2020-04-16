package com.source.point.sourcePoint.dto.dbsqlhandle;

import com.source.point.sourcePoint.common.SerializedEntity;

import io.swagger.annotations.ApiModelProperty;

public class AutoSqlParam extends SerializedEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "resource_name")
	private String resourceName;

	@ApiModelProperty(value = "app_code")
	private String appCode;

	@ApiModelProperty(value = "sort")
	private String sort;

	@ApiModelProperty(value = "menu_code")
	private String menuCode;

	@ApiModelProperty(value = "icon")
	private String icon;

	@ApiModelProperty(value = "resource_group")
	private String resourceGroup;

	@ApiModelProperty(value = "resource_url")
	private String resourceUrl;

	@ApiModelProperty(value = "button")
	private String button;

	@ApiModelProperty(value = "parent_menu_code")
	private String parentMenuCode;

	@ApiModelProperty(value = "role_name")
	private String roleName;

	public AutoSqlParam() {
	}

	public AutoSqlParam(String sort, String resourceName, String appCode) {
		this.sort = sort;
		this.resourceName = resourceName;
		this.appCode = appCode;
	}

	public AutoSqlParam(String sort, String button, String resourceGroup, String resourceName, String appCode,
			String parentMenuCode, String menuCode, String resourceUrl, String icon) {
		this.sort = sort;
		this.button = button;
		this.resourceGroup = resourceGroup;
		this.resourceName = resourceName;
		this.appCode = appCode;
		this.parentMenuCode = parentMenuCode;
		this.menuCode = menuCode;
		this.resourceUrl = resourceUrl;
		this.icon = icon;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getResourceGroup() {
		return resourceGroup;
	}

	public void setResourceGroup(String resourceGroup) {
		this.resourceGroup = resourceGroup;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}

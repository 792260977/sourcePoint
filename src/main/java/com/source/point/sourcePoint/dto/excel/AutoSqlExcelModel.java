package com.source.point.sourcePoint.dto.excel;

import com.source.point.sourcePoint.common.ExcelColumn;

public class AutoSqlExcelModel {

	@ExcelColumn(value = "排序", col = 1)
	private String sort;

	@ExcelColumn(value = "是不是按钮", col = 2)
	private String button;

	@ExcelColumn(value = "是不是菜单", col = 3)
	private String resourceGroup;

	@ExcelColumn(value = "资源名称", col = 4)
	private String resourceName;

	@ExcelColumn(value = "顶级菜单码", col = 5)
	private String appCode;

	@ExcelColumn(value = "上级菜单码", col = 6)
	private String parentMenuCode;

	@ExcelColumn(value = "菜单码", col = 7)
	private String menuCode;

	@ExcelColumn(value = "资源URL", col = 8)
	private String resourceUrl;

	@ExcelColumn(value = "图标", col = 9)
	private String icon;

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public String getResourceGroup() {
		return resourceGroup;
	}

	public void setResourceGroup(String resourceGroup) {
		this.resourceGroup = resourceGroup;
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

	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}

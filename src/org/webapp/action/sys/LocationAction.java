package org.webapp.action.sys;

import com.opensymphony.xwork2.ActionSupport;

public class LocationAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private int locationId;
	private String cnName;
	private String enName;
	private String locationNo;
	private String parentNo;
	private String levelCnName;
	private String levelEnName;
	private String rs;

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public String getParentNo() {
		return parentNo;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}

	public String getLevelCnName() {
		return levelCnName;
	}

	public void setLevelCnName(String levelCnName) {
		this.levelCnName = levelCnName;
	}

	public String getLevelEnName() {
		return levelEnName;
	}

	public void setLevelEnName(String levelEnName) {
		this.levelEnName = levelEnName;
	}

	public String getRs() {
		return rs;
	}

	@Override
	public String execute() throws Exception {

		return SUCCESS;
	}

	public String tree() throws Exception {

		return SUCCESS;
	}

	public String treeGrid() throws Exception {

		return SUCCESS;
	}

	public String one() throws Exception {
		return SUCCESS;
	}

	public String save() throws Exception {
		return SUCCESS;
	}

	public String delete() throws Exception {
		return SUCCESS;
	}
}

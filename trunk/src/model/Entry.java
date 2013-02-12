package model;

import java.util.Date;

public class Entry {
	private String _personEMail;
	private String _title;
	private String _materialsUsed;
	private String _fabricsUsed;
	private Date _dateSubmitted;
	private String _categoryName;
	
	public String getPersonEMail() {
		return _personEMail;
	}
	public void setPersonEMail(String personEMail) {
		this._personEMail = personEMail;
	}
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String title) {
		this._title = title;
	}
	
	public String getMaterialsUsed() {
		return _materialsUsed;
	}
	public void setMaterialsUsed(String materialsUsed) {
		this._materialsUsed = materialsUsed;
	}
	
	public String getFabricsUsed() {
		return _fabricsUsed;
	}
	public void setFabricsUsed(String fabricsUsed) {
		this._fabricsUsed = fabricsUsed;
	}
	
	public Date getDateSubmitted() {
		return _dateSubmitted;
	}
	public void setDateSubmitted(Date dateSubmitted) {
		this._dateSubmitted = dateSubmitted;
	}
	
	public String getCategoryName() {
		return _categoryName;
	}
	public void setCategoryName(String categoryName) {
		_categoryName = categoryName;
	}
	
}
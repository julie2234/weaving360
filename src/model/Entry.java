package model;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {

	private static final long serialVersionUID = -7102871377339337042L;
	
	private String _email;
	private String _title;
	private String _materials;
	private String _techniques;
	private String _description;
	private String _category;
	private Date _dateSubmitted;
	
	public String getEmail() {
		return _email;
	}
	
	public void setEmail(String email) {
		this._email = email;
	}
	
	public String getTitle() {
		return _title;
	}
	
	public void setTitle(String title) {
		this._title = title;
	}
	
	public String getMaterials() {
		return _materials;
	}
	
	public void setMaterials(String materials) {
		this._materials = materials;
	}
	
	public String getTechniques() {
		return _techniques;
	}
	
	public void setTechniques(String techniques) {
		this._techniques = techniques;
	}
	
	public String getDescription() {
		return _description;
	}
	
	public void setDescription(String description) {
		this._description = description;
	}
	
	public String getCategory() {
		return _category;
	}
	
	public void setCategory(String category) {
		this._category = category;
	}

	public Date getDateSubmitted() {
		return _dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this._dateSubmitted = dateSubmitted;
	}
	
}

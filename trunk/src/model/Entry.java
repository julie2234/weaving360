package model;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import weavedraft.WeaveDraft;

public class Entry implements Serializable {

	private static final long serialVersionUID = -7102871377339337042L;
	
	private String _email;
	private String _title;
	private String _materials;
	private String _techniques;
	private String _description;
	private String _categoryName;
	private Date _dateSubmitted;
	//private WeaveDraft _draft;
	private byte[] _image;
	
	public byte[] getImage() {    
	    return _image;
	}
	
	public void setImage(byte[] image) {    
	    _image = image;   
	}
	
	/*
	public WeaveDraft getDraft() {
		return _draft;
	}
	*/
	
	/*
	public void setDraft(WeaveDraft draft) {	
		this._draft = draft;	
	}
	*/
	
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
	
	public String getCategoryName() {
		return _categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this._categoryName = categoryName;
	}

	public Date getDateSubmitted() {
		return _dateSubmitted;
	}

	public void setDateSubmitted(Date dateSubmitted) {
		this._dateSubmitted = dateSubmitted;
	}
	
	public String getID() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMDDHHmmss");
		return _email + "." + format.format(_dateSubmitted);
	}
	
	@Override 
	public int hashCode() {
		return getID().hashCode();
	}

	@Override 
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;
		return getID().equals(((Entry) obj).getID());
	}
	
	public boolean isComplete() {
		if (_title.equals("") || _materials.equals("") 
				|| _techniques.equals("") || _description.equals("")) {
			return false;
		}
		return true;
	}
	
}

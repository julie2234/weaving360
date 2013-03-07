package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry implements Serializable {

	private static final long serialVersionUID = -7102871377339337042L;
	
	private String _email;
	private String _title;
	private String _materials;
	private String _techniques;
	private String _description;
	private String _categoryName;
	private Date _dateSubmitted;
	private WeavingDraft _draft;
	private int _award;
	
	/**
	 * Sets the award as 1st, 2nd, or 3rd
	 * 
	 * @param awardplace The place the entry won.
	 * @throws Exception The place can only be 1, 2, or 3
	 */
	public void setAward(int awardplace) throws Exception{
		
		if(awardplace > 3 || awardplace < 0){
			throw new Exception("Invalid award");
		}
		else {
			_award = awardplace;
		}
		
	}
	
	public int getAward() {
		return _award;
	}
	
	public WeavingDraft getDraft() {
		
		return _draft;
		
	}
	
	public void setDraft(WeavingDraft draft) {
		
		this._draft = draft;
		
	}
	
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

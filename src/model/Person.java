package model;

import java.io.Serializable;

public class Person implements Serializable {
	
	private static final long serialVersionUID = -4687435575857981833L;
	
	private String _eMail;
	public String getEMail() {
		return _eMail;
	}
	public void setEMail(String eMail) {
		_eMail = eMail;
	}
	
	private String _firstName;
	public String getFirstName() {
		return _firstName;
	}
	public void setFirstName(String firstName) {
		_firstName = firstName;
	}
	
	private String _lastName;
	public String getLastName() {
		return _lastName;
	}
	public void setLastName(String lastName) {
		_lastName = lastName;
	}
	
    public int hashCode() {
        return _eMail.hashCode();
    }
    
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;
        return _eMail.equals(((Person)obj).getEMail());        
    }
}

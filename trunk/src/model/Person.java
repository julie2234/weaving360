package model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -4687435575857981833L;

	private String _eMail;
	private String _firstName;
	private String _lastName;
	private String _password;
	private Role _role;
	private String _phoneNumber;

	public Role getRole() {
		return _role;
	}

	public void setRole(Role role) {
		this._role = role;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this._phoneNumber = phoneNumber;
	}

	public String getEMail() {
		return _eMail;
	}

	public void setEMail(String eMail) {
		_eMail = eMail;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		this._password = password;
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
		return _eMail.equals(((Person) obj).getEMail());
	}
}

package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

	private static final long serialVersionUID = -155895514971235921L;

	private String _name;
	private List<Person> _judges;
	
	public Category() {
		_judges = new ArrayList<Person>();
	}
	
	public String getName() {
		return _name;
	}
	public void setName(String name) {
		_name = name;
	}
	
	public List<Person> getJudges() {
		return _judges;
	}
	public void setJudges(List<Person> judges) {
		_judges = judges;
	}
	
	public int hashCode() {
		return _name.hashCode();
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;
		return _name.equals(((Category) obj).getName());
	}
}

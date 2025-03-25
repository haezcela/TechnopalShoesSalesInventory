package com.laponhcet.person;
import com.laponhcet.student.StudentDTO;
//import com.laponhcet.itemunit.ItemUnitDTO;
import com.mytechnopal.base.DTOBase;
import com.mytechnopal.dto.UserDTO;

public class PersonDTO extends DTOBase {
	private static final long serialVersionUID = 1L;

	public static String SESSION_PERSON = "SESSION_PERSON";
	public static String SESSION_PERSON_LIST = "SESSION_PERSON_LIST";
	public static String SESSION_PERSON_DATA_TABLE = "SESSION_PERSON_DATA_TABLE";
	public static String ACTION_SEARCH_BY_LASTNAME = "ACTION_SEARCH_BY_LASTNAME";
	
	//add session uploadfile
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String picture;
	
	public PersonDTO() {
		firstName = "";
		middleName = "";
		lastName = "";
		gender = "";
		picture = "";
	}
	
	public PersonDTO getPerson() {
		PersonDTO person = new PersonDTO();
		person.setId(super.getId());
		person.setCode(String.valueOf(person.getCode()));
		person.setFirstName(this.firstName);
		person.setMiddleName(this.middleName);
		person.setLastName(this.lastName);
		person.setGender(this.gender);
		person.setPicture(this.picture);
	        
	    return person;
	}
//	public PersonDTO setPerson() {
//		PersonDTO person = new PersonDTO();
//		person.setId(super.getId());
//		person.setCode(String.valueOf(person.getCode()));
//		person.setFirstName(person.getFirstName());
//		person.setMiddleName(this.middleName);
//		person.setLastName(this.lastName);
//		person.setGender(this.gender);
//		person.setPicture(this.picture);
//	        
//	    return person;
//	}
	
	public void setPerson(PersonDTO person) {
	    this.setId(person.getId());
	    this.setCode(person.getCode());
	    this.setFirstName(person.getFirstName());
	    this.setMiddleName(person.getMiddleName());
	    this.setLastName(person.getLastName());
	    this.setGender(person.getGender());
	    this.setPicture(person.getPicture());
	}

//	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}

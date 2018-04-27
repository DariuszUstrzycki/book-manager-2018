package unrelated.to.bookmanager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "personDetails")
public class PersonDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(min = 2,	max	= 30, message="1.Enter your first name. Message hardcoded")
	private String firstName;
	@NotNull
	@Size(min = 2,	max	= 30) //, message="1.You must enter your last name.Message hardcoded")
	private String lastName;
	private String streetNumber;
	private String street;
	private String city;
	
	private String gender;
	private String country;
	private String notes;
	private boolean mailingList;
	/*private List<String> programmingSkills;
	private List<String> hobbies;*/
	
	
	/*
	public List<String> getProgrammingSkills() {
		return programmingSkills;
	}
	public void setProgrammingSkills(List<String> programmingSkills) {
		this.programmingSkills = programmingSkills;
	}
	public List<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}*/
	
	
	
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public boolean isMailingList() {
		return mailingList;
	}
	public void setMailingList(boolean mailingList) {
		this.mailingList = mailingList;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}

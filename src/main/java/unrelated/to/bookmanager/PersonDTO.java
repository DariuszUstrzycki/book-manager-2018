package unrelated.to.bookmanager;


import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
	
	private String login;
	private String password;
	private String email;	
	
	private String firstName;
	private String lastName;
	private String streetNumber;
	private String street;
	private String city;
	
	private String gender;
	private String country;
	private String notes;
	private boolean mailingList;
	private List<String> programmingSkills = new ArrayList<>();
	private List<String> hobbies = new ArrayList<>();
	
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	}
	@Override
	public String toString() {
		return "PersonDTO [login=" + login + ", password=" + password + ", email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", streetNumber=" + streetNumber + ", street=" + street + ", city=" + city
				+ ", gender=" + gender + ", country=" + country + ", notes=" + notes + ", mailingList=" + mailingList
				+ ", programmingSkills=" + programmingSkills + ", hobbies=" + hobbies + "]";
	}
	
	

}

package unrelated.to.bookmanager;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "persons")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank
	private String login;
	
	@NotBlank
	/*@NotNull
	@Size(min=2,	max=30)
	@StartWith()*/
	private String password;
	
	
	@Email // on CharSequence
	@NotBlank
	private String email;
	
	/*
	Cascading only makes sense only for Parent – Child associations (the Parent entity 
	state transition being cascaded to its Child entities). Cascading from Child to 
	Parent is not very useful and usually, it’s a mapping code smell.
	*/
	// unidirectional : utworzy FK personDetails_id w Person
	@OneToOne(cascade = CascadeType.ALL) // its enough to persist/delete Person and PersonDetails will follow suit
	@JoinColumn(name = "personDetails_id") 
	private	PersonDetails personDetails;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public PersonDetails getPersonDetails() {
		return personDetails;
	}

	public void setPersonDetails(PersonDetails personDetails) {
		this.personDetails = personDetails;
	}

	
	
	

}

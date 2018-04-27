package pl.coderslab.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.pl.PESEL;

import unrelated.to.bookmanager.AllowedAge;
import unrelated.to.bookmanager.Over18;
import unrelated.to.bookmanager.StartWith;

@Entity
@Table(name = "authors")
public class Author implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*@NotBlank 
	@Size(min=2) - te dwa lepiej zastapic @NotNull i @Size(min=2,	max=30)
	*/
	
	
	@NotNull
	@Size(min=2,	max=30)
	//@StartWith()
	@Column(name = "first_name", length=100,nullable=false)
	private String firstName;
	
	@NotNull
	@Size(min=2,	max=30)
	@Column(name = "last_name", length=100, nullable=false)
	private String lastName;
	
	@PESEL // dopoki nie będę probowal ustawiac pesela przy pomocy setPesel, to validator zaakceptuje null pesel!!!
	private String pesel;
	
	@Email // dopoki nie będę probowal ustawiac email przy pomocy setPesel, to validator zaakceptuje null pesel!!!
	private String email;
	
	//@AllowedAge(5) - DZIAŁA 
	@Digits(fraction = 0, integer = 4)
	@Column(name = "year_of_birth", length=4)
	//@Over18
	Integer yearOfBirth; //UnexpectedTypeException: HV000030: No validator could be found for constraint 'pl.coderslab.validator.Over18' validating type 'java.lang.Integer'
	//private String yearOfBirth; 
	
	//////////////many to many biderectional Author/Book /////////////////////////////////////////////
	                                  // bez fetchtype EAGER, books nie ładowały mi się gdy wyświetlalem Authors
	                               // był błąd  “failed to lazily initialize a collection of role” exception
 // more: https://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	@ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER) //  the inverse side =  mappedBy side
    private List<Book> books = new ArrayList<Book>(); // collections and maps are not fetched implicitely!!!
	// mappedBy = CHILD
	
	///////////////////////////////////////////////////////
	// https://vladmihalcea.com/a-beginners-guide-to-jpa-and-hibernate-cascade-types/
	// Tam te metody są włąśnie tutaj
	/*public void addBook(Book book) {
        books.add(book);
        book.getAuthors().add(this);
    }
 
    public void removeBook(Book book) {
        books.remove(book);
        book.getAuthors().remove(this);
    }
 
    public void remove() {
        for(Book book : new ArrayList<>(books)) {
            removeBook(book);
        }
    }*/
	
	/////////////////////////////////////////////////////////
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
	
	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/////////////////////////////////////// YEAR OF BIRTH ZMIANA TYPU ///////////////
	

	public Integer getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	
	
	/*
	
	public String getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(String yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}
	*/
	///////////////////////////////////////

	@Override
	public String toString() {
		return "Author: " + firstName + " " + lastName;
		//return "Author [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}



	@Override
	public boolean equals(Object o) {
		 if ( this == o ) {
	            return true;
	        }
	        if ( o == null || getClass() != o.getClass() ) {
	            return false;
	        }
	        Author author = (Author) o;
	        return Objects.equals( this.id, author.id ) &&
	                Objects.equals( this.firstName, author.firstName ) &&
	                  Objects.equals( this.lastName, author.lastName );
	}
	
	@Override
    public int hashCode() {
        return Objects.hash( this.id, this.firstName, this.lastName );
    }	
	
}

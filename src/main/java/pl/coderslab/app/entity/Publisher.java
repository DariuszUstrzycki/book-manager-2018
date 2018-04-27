package pl.coderslab.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.pl.NIP;
import org.hibernate.validator.constraints.pl.REGON;

@Entity
@Table(name = "publishers")
public class Publisher implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@NotNull
	@Size(min=2)
	@Column(length=100, nullable=false)
	private String name;
	
	@REGON
	private String regon;
	
	@NIP
	private String nip;
	 
	@OneToMany(mappedBy = "publisher",   // cascade = CascadeType.ALL, - przez to nie dawąlo sie deletować Book
			fetch = FetchType.EAGER) // orphanRemoval = true) - nie ma po co usuwac Book jesli publisher okazal sie byc wrong...
	private List<Book> books = new ArrayList<Book>();
	
	
	
	//////////////////// sync is on the parent side in one-to-many bi ///////////////////////////////////////

	public void addBook(Book book) {
	    //prevent endless loop
	    /*if (this.books.contains(book))
	      return ;*/
	    
	    this.books.add(book);
	    book.setPublisher(this);
	  }

	public void removeBook(Book book) {
	    //prevent endless loop
	    /*if (!this.books.contains(book))
	      return ;*/
	    
	    this.books.remove(book);
	    book.setPublisher(null);
	  }

	/////////////////////////////////////////////////////////
	
	public List<Book> getBooks() {
		
		//defensive copy, nobody will be able to change 
	    //the list from the outside
		
		//return new ArrayList<Book>(books);
		
		return this.books;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRegon() {
		return regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}
	
	
	
	//////////////////////////////

	@Override
	public String toString() {
		//return "Pub[" + id + ", " + name + "]";
		return " " + name + " ";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Publisher other = (Publisher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

package pl.coderslab.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.NotEmpty;

import pl.coderslab.app.validator.BookValidator;
import pl.coderslab.app.validator.PropositionValidator;

@Entity 
@Table(name = "books")
public class Book implements Serializable{
		
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@NaturalId(mutable=true)
	@Column(columnDefinition="CHAR(13)", unique=true, nullable=true)
	private String isbn;
	
	// @DecimalMin(value="5", inclusive=true) -- accept the String representation of a BigDecimal 
	// @Min(value=5)  -- int, long
	
	
	//@NotNull(groups = {BookValidator.class, PropositionValidator.class} ) // potrzebny?!
	@Size(min=5, groups =  {BookValidator.class, PropositionValidator.class} )  // strings and collections  and arrays
	@Column(nullable = false) // nie moge sobie poradzic aby było rownoczesnie nullable=false i jakas defaultValue
	private String title;
	
	/*
	 * 
	
	For data entry for a NOT NULL column that has no explicit DEFAULT clause, if an INSERT or REPLACE statement 
	includes no value for the column, or an UPDATE statement sets the column to NULL, MySQL handles the column 
	according to the SQL mode in effect at the time:

		If strict SQL mode is not enabled, MySQL sets the column to the implicit default value for the column data type.
		If strict mode is enabled, an error occurs for transactional tables and the statement is rolled back. 
		For nontransactional tables, an error occurs, but if this happens for the second or subsequent row of a 
		multiple-row statement, the preceding rows will have been inserted.
		
		SOLUTION
	@PrePersist
	public void prePersist() {
	if(title == null) 
		title = "not specified";
	}
	*/
	
	
	
	//@Range(min=1, max=10)
	@Min(value=1, groups = BookValidator.class)
	@Max(value = 10, groups = BookValidator.class, message="{book.rating.over.maximum")
	@Column(scale=2, precision=4)
	private BigDecimal rating;
	
	@Size(min=1, max=600, groups =  PropositionValidator.class)
	@Column(columnDefinition="TEXT")
	// @NotNull() - view nie wysyła null jak nic nie wpiszesz, tylko ""
	private String description;
	
	@Min(value = 2, groups = BookValidator.class)
	@Column (columnDefinition = "INT  default '0'")
	private int pages; 
	//private Integer pages; // nie robil walidacji gdy był Integer
	
	@Column(columnDefinition = "boolean not null default false")
	private boolean proposition;
	
	////////////////many to many biderectional///////////////////////////////////////////
	
	@ManyToOne
	private Category category;
	
	
	//////////////// one to many biderectional///////////////////////////////////////////
	@NotNull(groups = BookValidator.class)
	@ManyToOne (fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}) //, CascadeType.PERSIST  - musi byc usunięte, bo gdy persistuje Book, a Publisher jest juz w bd, to usiłuje też persist tego Publishera(you cant persist what is already persisted) i rzuca błedem detached entity passed to persist
	private Publisher publisher;
	
	///////////////////////////////////////////////////////////
	// many to many biderectional Author/Book
	
	//The many-to-many association generates way too many redundant SQL statements and often, they are very difficult to tune. 
	// The many-to-many CascadeType.ALL is another code smell, 
	//The CascadeType.REMOVE is automatically inherited when using CascadeType.ALL, but the
	//entity removal is not only applied to the link table, but to the other side 
	//of the association as well.
	
	// gdy cascade = {CascadeType.PERSIST, CascadeType.MERGE}, rzuca wyjatkiem:
	//"detached entity passed to persist hibernate jpa" 
	// https://stackoverflow.com/questions/13370221/jpa-hibernate-detached-entity-passed-to-persist?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	// problem wynika z tego, ze Author juz jest w db, wiec powinien byc merge ?!
	@NotEmpty(groups = BookValidator.class)
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })//{CascadeType.MERGE, ) - musi byc usunięte, bo gdy persistuje Book, a author jest juz w bd, to usiłuje też persist tego Authora (you cant persist what is already persisted) i rzuca błedem detached entity passed to persist
	 
	private List<Author> authors = new ArrayList<Author>();
	
	////////////////////in sync///////////////////////////////////////
	// the Parent side should contain the addChild and removeChild combo
	// it’s good practice to provide helper methods for adding or removing child entities. 
	//In PARENT only!
	
	public void addAuthor(Author author) {
		authors.add( author );
        author.getBooks().add(this);
    }

    public void removeAuthor(Author author) {
    	author.getBooks().remove(this);
    	authors.remove( author );
    }
    
    /////////////////////////////////////////////////////////
	public Book() {
		System.out.println("------------------------Book constructor is called");
	}	
		
	////////////////////////////////////////
	
	public List<Author> getAuthors() {
		System.out.println("------------------------------getAuthors() is called with " + authors);
		return authors;
	}
	
	//  necessary - bez tego dostaję że Field error in object 'book' on field 'authors': rejected value [[]];
	public void setAuthors(List<Author> authors) {
		System.out.println("------------------------------setAuthors() is called with " + authors);
		this.authors = authors;
	}
	
	public Publisher getPublisher() {
		System.out.println("------------------------------getpublisher() is called and returns  " + this.publisher);
		return publisher;
	}
	
	public void setPublisher(Publisher publisher) {
		System.out.println("------------------------------setPublisher(Publisher publisher)is called with " + publisher);
		this.publisher = publisher;
	}
	
	
	/////////////////////////////////////////////
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public boolean isProposition() {
		return proposition;
	}

	public void setProposition(boolean proposition) {
		this.proposition = proposition;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		//return "[" + id + ", T:" + title + ", P:" + publisher + ", A:" + authors + "]";
		return "@@@" + " " + title + publisher + " "  + authors;
	}
	
	public String toStringSimple() {
		return id + "|" + title;
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
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

	/*@Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals( isbn, book.isbn );
    }

    @Override
    public int hashCode() {
        return Objects.hash( isbn );
    }*/
	
	/*Because the  class has a @NaturalId column (the isbn number being unique), 
	the equals() and the hashCode() can make use of this property, and so the removePhone() logic 
	is reduced to the remove() Java Collection method.*/
	
}

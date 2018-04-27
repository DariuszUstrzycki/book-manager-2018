package pl.coderslab.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pl.coderslab.app.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	
	/*
	Autora dla zadanego email
	- Autora dla zadanego peselu.
	- Listę autorów o zadanym nazwisku*/
	
	Author findByEmail(String email);
	Author findByPeselIgnoreCase(String pesel);
	List<Author> findByLastNameIgnoreCase(String lastName);
	
	
	///////////////////////////////////////////////////////////////////////////////////////////
	// Listę autorów, których email zaczyna się od wskazanego ciągu znaków.
	// D
	@Query("SELECT a FROM Author a WHERE a.email LIKE :start%")	
	List<Author> findByEmailStartsWith(@Param("start") String emailStart);
	
	/*
	 działa także te 2 queries:
	@Query("SELECT a FROM Author a WHERE a.email LIKE (?1%)")
	@Query(value = "select * from authors where email like ?1% ", nativeQuery = true)
	List<Author> findByFirstNameStartsWith(String emailStart);
	*/
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	
	//Listę autorów których pesel zaczyna się od wskazanych znaków, np 83
	
	
	@Query("SELECT a FROM Author a WHERE a.pesel LIKE (?1%)")
	//@Query(value = "select * from authors where pesel like ?1% ", nativeQuery = true)
	List<Author> findByFirstNameStartsWith(String emailStart);
	
	
	
	
	
}

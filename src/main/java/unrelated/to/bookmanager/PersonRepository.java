package unrelated.to.bookmanager;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
/*
Utworzone	przez	nas	PersonRepository  zawiera	implementację	wszystkich	metody  zawierających	się	w:
	JpaRepository
	PagingAndSortingRepository
	CrudRepository
Wynika	to	z	określonej	hierarchii dziedziczenia. Dokumentacja: http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html

*/
public interface PersonRepository extends JpaRepository<Person,	Long>	{
/*
	@Override
	default <S extends Person> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default Person findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	default void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	default List<Person> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
*/
}

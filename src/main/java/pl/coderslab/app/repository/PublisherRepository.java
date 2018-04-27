package pl.coderslab.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.app.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	/*
	 Utwórz metody pobierające:
		 - Wydawcę dla zadanego nipu
		 - Wydawcę dla zadanego regonu.
*/
	
	Publisher findByNip(String nip);
	Publisher findByRegon(String regon);
	
	
	
}

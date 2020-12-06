package com.ynov.microservices.tlm.author;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
	
	@Query("SELECT DISTINCT authors FROM Author authors WHERE authors.pseudo LIKE :pseudo%")
	@Transactional(readOnly = true)
	Collection<Author> findByPseudo(@Param("pseudo") String pseudo);
}

package com.ynov.microservices.tlm.quote;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QuoteRepository extends CrudRepository<Quote, Integer> {

	@Query("SELECT quotes FROM Quote quotes WHERE quotes.author = :author")
	@Transactional(readOnly = true)
	Collection<Quote> findByAuthor(@Param("author") Integer author);
	
	@Query("SELECT quotes FROM Quote quotes ORDER BY quotes.upVote DESC")
	@Transactional(readOnly = true)
	Collection<Quote> getTrending();
}

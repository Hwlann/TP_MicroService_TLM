package com.ynov.microservices.tlm.comment;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	@Query("SELECT comments FROM Comment comments WHERE comments.quote = :quote")
	@Transactional(readOnly = true)
	Collection<Comment> findByQuote(@Param("quote") Integer quote);
	
	@Query("SELECT comments FROM Comment comments WHERE comments.author = :author")
	@Transactional(readOnly = true)
	Collection<Comment> findByAuthor(@Param("author") String author);
}

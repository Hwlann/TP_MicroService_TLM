package com.ynov.microservices.tlm.comment;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

	@Query("SELECT quote FROM Comment comment WHERE comment.quote = :quote")
	@Transactional(readOnly = true)
	Collection<Comment> findByQuote(@Param("quote") Integer quote);
}

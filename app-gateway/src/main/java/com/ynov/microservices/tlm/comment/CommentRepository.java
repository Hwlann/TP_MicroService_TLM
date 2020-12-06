package com.ynov.microservices.tlm.comment;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "comment-service")
public interface CommentRepository{
	
	@GetMapping("/comments")
	Iterable<Comment>findAll();

	@GetMapping("/comments/{id}")
	Optional<Comment> findById(@PathVariable("id") Integer id);
	
	@GetMapping("/comments/quotes/{id}")
	Iterable<Comment> findByQuote(@PathVariable("id")Integer id);
	
	@GetMapping("/comments/author/{author}")
	Iterable<Comment> findByAuthor(@PathVariable("author")String author);
	
	@PostMapping("/comments/")
	void save(@RequestBody Comment comment);
}

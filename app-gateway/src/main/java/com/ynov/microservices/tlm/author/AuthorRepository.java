package com.ynov.microservices.tlm.author;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "author-service")
public interface AuthorRepository {

	@GetMapping("/authors/findByPseudo/{pseudo}")
	Collection<Author> findByPseudo(@PathVariable("pseudo") String pseudo);
	
	@GetMapping("/authors")
	Collection<Author> findAll();
	
	@GetMapping("/authors/{id}")
	public Author findById(@PathVariable("id") Integer id);

	@PostMapping("/authors/")
	void save(@RequestBody Author author);
}

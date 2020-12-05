package com.ynov.microservices.tlm.author;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@DeleteMapping("/authors/remove")
	void deleteById(@RequestParam("id") Integer id);
}

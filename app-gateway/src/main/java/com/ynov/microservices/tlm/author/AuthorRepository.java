package com.ynov.microservices.tlm.author;

import java.util.Collection;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "author-service")
public interface AuthorRepository {

	@GetMapping("/owners/findByPseudo/{pseudo}")
	Collection<Author> findByPseudo(@PathVariable("pseudo") String pseudo);
	
	@GetMapping("/owners/{id}")
	public Author findById(@PathVariable("id") Integer id);

	@PostMapping("/owners/")
	void save(@RequestBody Author author);
}

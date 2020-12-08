package com.ynov.microservices.tlm.quote;


import java.util.Collection;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "quote-service")
public interface QuoteRepository{
	/******************************************** GET MAPPING ******************************/
	@GetMapping("/quotes/{id}")
	public Optional<Quote> findById(@PathVariable("id") Integer id);
	
	@PostMapping("/quotes/")
	void save(@RequestBody Quote quote);
	
	@GetMapping("/quotes")
	Iterable<Quote> findAll();
	
	@GetMapping("/quotes/author/{author}")
	Collection<Quote> findByAuthor(@PathVariable("author") Integer author);	
	
	@GetMapping("/quotes/trending")
	public Collection<Quote> getTrending();
	
	/******************************************** POST MAPPING ******************************/
	@DeleteMapping("/quotes/{id}")
	public Quote deleteById(@PathVariable("id") Integer id);
}

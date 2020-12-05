package com.ynov.microservices.tlm.quote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(value = "quote-service")
public interface QuoteRepository{
	@GetMapping("/quotes/{id}")
	public Quote findById(@PathVariable("id") Integer id);
	
	@PostMapping("/quotes")
	void save(Quote quote);
}

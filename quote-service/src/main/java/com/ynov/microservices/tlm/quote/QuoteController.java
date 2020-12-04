package com.ynov.microservices.tlm.quote;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class QuoteController {
	
	@Autowired
	private QuoteRepository quotes;
	
	public QuoteController(QuoteRepository quotes) {
		this.quotes = quotes;
	}
	
	/*
	 * GET MAPPING 
	 */
	@GetMapping("/quote/")
	public Iterable<Quote> getQuote(){
			return quotes.findAll();
	}
	
	@GetMapping("/quote/{id}")
	public Optional<Quote> getQuote(@PathVariable("id") Integer id){
			return quotes.findById(id);
	}
	
	/*
	@GetMapping("/quote/from/{author}")
	public Iterable<Quote> getQuoteById(@PathVariable("authorId") Integer authorId){
			return quotes.findByIds(authorId);
	}
	*/
	
	/*
	 * POST MAPPING 
	 */
	/*
	@PostMapping("/quote/")
	public void addQuote(@RequestParam("authorId") Integer id, @RequestParam("text") String text){
			Quote quote = new Quote();
			quote.setAuthorId(id);
			quote.setQuote(text);
			quotes.save(quote);
	}
	
	/*
	 * DELETE MAPPING 
	 */
	@DeleteMapping("/quote/{id}")
	public void deleteQuote(@PathVariable("id") Integer id){
		quotes.deleteById(id);
	}
	
	@DeleteMapping("/quotes/")
	public void deleteQuotes(){
		quotes.deleteAll();
	}
}
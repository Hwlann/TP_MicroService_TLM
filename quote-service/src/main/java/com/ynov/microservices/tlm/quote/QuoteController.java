package com.ynov.microservices.tlm.quote;

import java.util.Collection;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
	
	/******************************************** VARIABLES ******************************************/
	private QuoteRepository quotes;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public QuoteController(QuoteRepository quotes) {
		this.quotes = quotes;
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/	
	@GetMapping("/quotes")
	public Iterable<Quote> getQuotes() {
		return quotes.findAll();
	}
	
	@GetMapping("/quotes/{id}")
	public Optional<Quote> getQuoteById(@PathVariable("id") Integer id) {
		return quotes.findById(id);
	}
	
	@GetMapping("/quotes/{author}/quotes")
	public Iterable<Quote> getQuoteByAuthor(@PathVariable("author") Integer author) {
		return quotes.findByAuthor(author);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/quotes") 
	public Quote addQuote(@RequestBody Quote quote) {
		return this.quotes.save(quote);
	}
	
	@PostMapping("/quotes/new")
	public Quote addQuote(@RequestParam("content") String content) {
		Quote quote = new Quote();
		Collection<Quote> quoteList = (Collection<Quote>) quotes.findAll();
		if(quoteList.isEmpty()) {
			quote.setId(1);
		}
		else {
			quote.setId(quoteList.size()+1);
		}
		quote.setAuthor(0);
		quote.setUpVote(0);
		quote.setDownVote(0);
		quote.setContent(content);
		return quotes.save(quote);
	}
	
	@PostMapping("/quotes/{quoteId}/addComment")
	public void addVisitToPet(@PathVariable("quoteId") Integer quoteId, @RequestParam("commentId") Integer commentId) {
		Optional<Quote> quoteOpt = quotes.findById(quoteId);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.addComment(commentId);
			quotes.save(quote);
		}

	}
	
	/****************************************************************************************************/
	/******************************************** PUT MAPPING *******************************************/
	/****************************************************************************************************/		
	@PutMapping("/quotes/{id}/edit/content")
	public Quote editQuoteContent(@PathVariable("id") Integer id, @RequestParam("content") String content) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setContent(content);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}
	
	@PutMapping("/quotes/{id}/edit/upvote")
	public Quote editQuoteUpVote(@PathVariable("id") Integer id) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setUpVote(quote.getUpVote()+1);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}
	
	@PutMapping("/quotes/{id}/edit/downvote")
	public Quote editQuoteDownVote(@PathVariable("id") Integer id) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setDownVote(quote.getDownVote()+1);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/	
	@DeleteMapping("/quotes/{id}")
	public void deleteQuote(@PathVariable("id") Integer id) {
		quotes.deleteById(id);
	}
}
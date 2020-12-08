package com.ynov.microservices.tlm.quote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class QuoteController {
	/******************************************** VARIABLES ******************************************/
	private QuoteRepository quotes;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public QuoteController(QuoteRepository quotes) {
		this.quotes = quotes;
		
		// Create some Quotes
		Quote quote = new Quote();
		quote.setId(1);
		quote.setAuthor(1);
		quote.setContent("Bonsoir Jacquie");
		quote.setUpVote(0);
		quote.setDownVote(0);
		quotes.save(quote);
		
		quote.setId(2);
		quote.setAuthor(1);
		quote.setContent("Bonsoir Michel");
		quote.setUpVote(0);
		quote.setDownVote(0);
		quotes.save(quote);
	}
	
	/*
	 *  Annotation @HystrixCommand on App-gateway's QuoteRepository's methods
	 */
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/	
	@GetMapping("/quotes")
	@HystrixCommand
	public Iterable<Quote> getQuotes() {
		return quotes.findAll();
	}
	
	@GetMapping("/quotes/{id}")
	@HystrixCommand
	public Optional<Quote> getQuoteById(@PathVariable("id") Integer id) {
		return quotes.findById(id);
	}
	
	@GetMapping("/quotes/author/{author}")
	@HystrixCommand
	public Collection<Quote> getQuoteByAuthor(@PathVariable("author") Integer author) {
		return quotes.findByAuthor(author);
	}
	
	@GetMapping("/quotes/trending")
	@HystrixCommand
	public Collection<Quote> getTrendings() {
		Collection<Quote> quotesIter = quotes.getTrending();	
		Iterator<Quote> iter = quotesIter.iterator();
		ArrayList<Quote> topTen = new ArrayList<Quote>();
		int i = 0 ;
		while(iter.hasNext()) {
			if(i < 10) topTen.add(iter.next());
			i++;
		}	
		return quotes.getTrending();
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/quotes/") 
	@HystrixCommand
	public Quote save(@RequestBody Quote quote) {
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
	
	@PostMapping("/quotes/{id}/add-comment")
	@HystrixCommand
	public Quote addCommentToQuote(@PathVariable("id") Integer id, @RequestParam("commentId") Integer commentId) {
		if(commentId == null)  return null;
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.addComment(commentId);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}
	
	/****************************************************************************************************/
	/******************************************** PUT MAPPING *******************************************/
	/****************************************************************************************************/	
	@PutMapping("/quotes/{id}/edit/content")
	@HystrixCommand
	public Quote editQuoteContent(@PathVariable("id") Integer id, @RequestParam("content") String content) {
		if(!StringUtils.hasLength(content)) return null;
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setContent(content);
			quotes.save(quote);
			return quote;
		}
		return null;
	}
	
	@PutMapping("/quotes/{id}/edit/upvote")
	@HystrixCommand
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
	@HystrixCommand
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
	@HystrixCommand
	public void deleteQuote(@PathVariable("id") Integer id) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if(!quoteOpt.isEmpty()) {
			quotes.deleteById(quoteOpt.get().getId());
		}		
	}
}
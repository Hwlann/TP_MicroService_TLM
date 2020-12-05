package com.ynov.microservices.tlm.quote;

import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class QuoteController {
	
	private QuoteRepository quotes;
	
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
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/quotes") 
	public Quote addQuote(@RequestBody Quote quote) {
		return this.quotes.save(quote);
	}
	
	@PostMapping("/quotes/new")
	public Quote addQuote() {
		Quote quote = new Quote();
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
	@PutMapping("/quotes/{id}")
	public Quote editQuote(@PathVariable("id") Integer id, @RequestParam("content") String content,
			@RequestParam("upVote") Integer upVote, @RequestParam("downVote") Integer downVote) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setContent(content);
			quote.setUpVote(upVote);
			quote.setDownVote(downVote);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/	
	@DeleteMapping("/quotes/{id}")
	public void deletePet(@PathVariable("id") Integer id) {
		quotes.deleteById(id);
	}
}
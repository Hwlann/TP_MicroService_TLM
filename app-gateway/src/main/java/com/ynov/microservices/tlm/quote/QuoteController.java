package com.ynov.microservices.tlm.quote;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ynov.microservices.tlm.author.Author;
import com.ynov.microservices.tlm.author.AuthorRepository;
import com.ynov.microservices.tlm.comment.Comment;
import com.ynov.microservices.tlm.comment.CommentRepository;

@RestController
public class QuoteController {
	
	/******************************************** VARIABLES **************************************************/
	private final QuoteRepository quotes;
	private final AuthorRepository authors;
	private final CommentRepository comments;
	
	/******************************************** CONSTRUCTOR ************************************************/
	public QuoteController (QuoteRepository quotes, AuthorRepository authors, CommentRepository comments) {
		this.quotes = quotes;
		this.authors = authors;
		this.comments = comments;
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	@GetMapping("/quotes")
	@HystrixCommand
	public Iterable<Quote> findQuotes() {
		return quotes.findAll();			
	}
	
	@GetMapping("/quotes/{id}")
	@HystrixCommand
	public Optional<Quote> findQuoteById(@PathVariable("id") Integer id) {
		return quotes.findById(id);			
	}

	@GetMapping("/quotes/pseudo/{pseudo}")
	@HystrixCommand
	public Iterable<Quote> findQuoteByPseudo(@PathVariable("id") Integer author) {
		return quotes.findByAuthor(author);			
	}
	
	@GetMapping("/quotes/trending")
	@HystrixCommand
	public Collection<Quote> getTrendings() {
		return quotes.getTrending();
	}
		
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/quotes/new")
	@HystrixCommand
	public Quote addQuote(@RequestParam("pseudo") String pseudo, @RequestParam("content") String content) {
		// REPO
		if(!StringUtils.hasLength(content)) return null;
		if(!StringUtils.hasLength(pseudo)) return null;
		
		Collection<Quote> quoteFullList = (Collection<Quote>) quotes.findAll();
		Collection<Author> authorFullList = (Collection<Author>) authors.findAll();
		
		// AUTHOR
		Author author = new Author();
		Quote quote = new Quote();
		Integer authorId = 0;
		Optional<Author> authorOpt = authors.findPseudo(pseudo);
		if (authorOpt.isPresent()) {
			author = authorOpt.get();
			authorId = author.getId();
		}
		
		if(authorId == 0){
			author.setPseudo(pseudo);
			author.setId(authorFullList.size()+1);
			authorId = author.getId();
		}		
		// QUOTE
		quote.setContent(content);
		if(quoteFullList.isEmpty()) {
			quote.setId(1);
		}
		else {
			quote.setId(quoteFullList.size()+1);
		}
		
		quote.setAuthor(authorId);
		quote.setUpVote(0);
		quote.setDownVote(0);
		author.addQuote(quote.getId());
		this.authors.save(author);
		this.quotes.save(quote);
		return quote;
	}
	
	@PostMapping("/quotes/{id}/add-comment")
	@HystrixCommand
	public Comment addComment(@PathVariable("id") Integer id, @RequestParam("pseudo") String pseudo, @RequestParam("content") String content) {
		
		Collection<Comment> commentList = (Collection<Comment>) comments.findAll();
		Collection<Author> authorFullList = (Collection<Author>) authors.findAll();
		
		if(!StringUtils.hasLength(content)) return null;
		if(!StringUtils.hasLength(pseudo)) return null;		

		Author author = new Author();
		Comment comment = new Comment();
		Quote quote = new Quote();
		
		// AUTHOR		
		Integer authorId = 0;
		Integer quoteId = 0;
		
		Optional<Author> authorOpt = authors.findPseudo(pseudo);
		if (authorOpt.isPresent()) {
			author = authorOpt.get();
			authorId = author.getId();
		}
		
		if(authorId == 0){
			author.setPseudo(pseudo);
			author.setId(authorFullList.size()+1);
			authorId = author.getId();
		}
		
		// QUOTE
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			quote = quoteOpt.get();
			quoteId = quote.getId();

			// COMMENT
			if(commentList.isEmpty()) {
				comment.setId(1);
			}
			else {
				comment.setId(commentList.size()+1);
			}			
			comment.setContent(content);
			comment.setAuthor(authorId);
			comment.setQuote(quoteId);
			
			author.addComment(comment.getId());
			quote.addComment(comment.getId());
			
			this.quotes.save(quote);
			this.authors.save(author);
			this.comments.save(comment);		
			return comment;
		}
		return null;
	}
	
	/****************************************************************************************************/
	/******************************************** PUT MAPPING *******************************************/
	/****************************************************************************************************/
	@PutMapping("/quotes/{id}/edit/content")
	@HystrixCommand
	public Quote editQuoteContent(@PathVariable("id") Integer id, @RequestParam("content") String content) {
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
	@DeleteMapping("quotes/{id}")
	@HystrixCommand
	public void deleteQuote(@PathVariable("id") Integer id) {
		authors.deleteById(id);
		Iterable<Comment> commentList = comments.findByQuote(id);
		Iterator<Comment> iter = commentList.iterator();
		while(iter.hasNext()) {
			comments.deleteById(iter.next().getId());
		}	
	}
}
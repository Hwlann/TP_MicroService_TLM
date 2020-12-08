package com.ynov.microservices.tlm.comment;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommentController {

	/******************************************** VARIABLES ********************************************/
	private final CommentRepository comments;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public CommentController(CommentRepository comments) {
		this.comments = comments;
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	
	/******************************************** GET COMMENT *******************************************/
	@GetMapping("/comments")
	public Iterable<Comment> getComments() {
		return comments.findAll();
	}

	/******************************************** GET COMMENT BY ID *******************************************/
	@GetMapping("/comments/{id}")
	public Optional<Comment> getCommentById(@PathVariable("id") Integer id) {
		return comments.findById(id);
	}
	
	/******************************************** GET COMMENT BY QUOTE *******************************************/
	@GetMapping("/comments/quotes/{quote}")
	public Iterable<Comment> getCommentByQuote(@PathVariable("quote") Integer quote) {
		return comments.findByQuote(quote);
	}
	
	/******************************************** GET COMMENT BY QUOTE *******************************************/
	@GetMapping("/comments/author/{author}")
	public Iterable<Comment> getCommentByQuote(@PathVariable("author") String author) {
		return comments.findByAuthor(author);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/	
	@DeleteMapping("/comments/{id}")
	public String deleteComment(@PathVariable("id") Integer id) {
		comments.deleteById(id);
		return null;	
	}
}

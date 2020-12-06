package com.ynov.microservices.tlm.comment;

import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	@GetMapping("/comments")
	public Iterable<Comment> getComments() {
		return comments.findAll();
	}

	@GetMapping("/comments/{id}")
	public Optional<Comment> getCommentById(@PathVariable("id") Integer id) {
		return comments.findById(id);
	}
	
	@GetMapping("/comments/quotes/{quote}")
	public Iterable<Comment> getCommentByQuote(@PathVariable("quote") Integer quote) {
		return comments.findByQuote(quote);
	}
	
	@GetMapping("/comments/author/{author}")
	public Iterable<Comment> getCommentByQuote(@PathVariable("author") String author) {
		return comments.findByAuthor(author);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/comments/new")
	public Comment addComment(@RequestParam("author") String author, @RequestParam("content") String content) {
		Comment comment = new Comment();
		Collection<Comment> commentList = (Collection<Comment>) comments.findAll();
		if(commentList.isEmpty()) {
			comment.setId(1);
		}
		else {
			comment.setId(commentList.size()+1);
		}
		if(!StringUtils.hasLength(author)) {
			comment.setAuthor("Anonymous");	
		}
		else {
			comment.setAuthor(author);
		}
		comment.setContent(content);
		comment.setQuote(0);
		comments.save(comment);
		return comment;
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/	
}

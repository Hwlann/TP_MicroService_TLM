package com.ynov.microservices.tlm.comment;

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
public class CommentController {

	@Autowired
	private CommentRepository comments;
	
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
	
	@GetMapping("/comments/findByQuoteId/{quoteId}")
	public Iterable<Comment> findByCommentId(@PathVariable("quoteId") Integer quoteId) {
		return comments.findByQuote(quoteId);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/visits")
	public Comment addVisit(@RequestParam("author") String author, @RequestParam("content") String content) {
		Comment comment = new Comment();
		comment.setAuthor(author);
		comment.setContent(content);
		return comments.save(comment);
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("/visits/{id}")
	public void deleteVisit(@PathVariable("id") Integer id) {
		comments.deleteById(id);
	}
}

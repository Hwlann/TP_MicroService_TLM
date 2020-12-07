package com.ynov.microservices.tlm.comment;

import java.util.Collection;
import java.util.Optional;

import org.springframework.util.StringUtils;
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
public class CommentController {

	private CommentRepository comments;
	
	public CommentController(CommentRepository comments) {
		this.comments = comments;
		
		Comment comment = new Comment();
		comment.setId(1);
		comment.setAuthor(0);
		comment.setContent("RTFM");
		comment.setQuote(0);
		comments.save(comment);
		
		comment.setId(2);
		comment.setAuthor(0);
		comment.setContent("RTFM");
		comment.setQuote(0);
		comments.save(comment);		
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
	public Comment addComment(@RequestParam("content") String content) {
		Comment comment = new Comment();
		Collection<Comment> commentList = (Collection<Comment>) comments.findAll();
		if(commentList.isEmpty()) {
			comment.setId(1);
		}
		else {
			comment.setId(commentList.size()+1);
		}
		comment.setAuthor(0);
		comment.setContent(content);
		comment.setQuote(0);
		return comments.save(comment);
	}
	
	@PostMapping("/comments/")
	public Comment save(@RequestBody Comment comment){
			return comments.save(comment);
	}
	
	/****************************************************************************************************/
	/******************************************** PUT MAPPING ******************************************/
	/****************************************************************************************************/
	@PutMapping("/comments/{id}/edit")
	public Comment editComment(@PathVariable("id") Integer id, @RequestParam("content") String content) {
		Optional<Comment> commentOpt = comments.findById(id);
		if (commentOpt.isPresent()) {
			Comment comment = commentOpt.get();
			comment.setContent(content);
			comments.save(comment);
		}
		return commentOpt.get();
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("/comments/{id}")
	public void deleteVisit(@PathVariable("id") Integer id) {
		comments.deleteById(id);
	}
}

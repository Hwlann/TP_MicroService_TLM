package com.ynov.microservices.tlm.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CommentController {

	@Autowired
	private CommentRepository comments;
	
	public CommentController(CommentRepository comments) {
		this.comments = comments;
	}
	
	/*
	 * GET MAPPING
	 */
	@GetMapping("/quotes/{id}/comments")
	public Iterable<Comment> getAllCommentsFromQuoteId() {
		
		return null;		
	}
	
	
	/*
	 * POST MAPPING
	 */
	
	/*
	 * DELETE MAPPING
	 */
	
}

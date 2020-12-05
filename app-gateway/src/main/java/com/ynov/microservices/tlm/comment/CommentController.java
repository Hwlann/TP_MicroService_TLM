package com.ynov.microservices.tlm.comment;

import org.springframework.stereotype.Controller;

@Controller
public class CommentController {

	/******************************************** VARIABLES ********************************************/
	private final CommentRepository comments;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public CommentController(CommentRepository comments) {
		this.comments = comments;
	}
}

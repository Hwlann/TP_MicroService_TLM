package com.ynov.microservices.tlm.quote;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quotes")
public class Quote {	
	
	/****************************************************************************************************/
	/******************************************** VARIABLES *********************************************/
	/****************************************************************************************************/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	@Column
	private String content;
	@Column
	private Integer upVote;	
	@Column
	private Integer downVote;	
	
	private Integer author;
	
	/****************************************************************************************************/
	/******************************************** METHODS ***********************************************/
	/****************************************************************************************************/
	
	/******************************************** ID -***************************************************/
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/******************************************** CONTENT ************************************************/
	public String getContent() {
		return this.content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	/******************************************** UPVOTE ************************************************/
	public Integer getUpVote() {
		return this.upVote;
	}

	public void setUpVote(Integer upVote) {
		this.upVote = upVote;
	}

	/******************************************** DOWNVOTE ************************************************/
	public Integer getDownVote() {
		return this.downVote;
	}

	public void setDownVote(Integer downVote) {
		this.downVote = downVote;
	}

	/******************************************** AUTHOR ************************************************/
	public Integer getAuthor() {
		return this.author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}
}
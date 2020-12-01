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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String quote;

	@Column
	private Integer upVote;
	
	@Column
	private Integer downVote;
	
	private Integer authorId;
	
	/*
	 * METHODS
	 */
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public Integer getUpVote() {
		return upVote;
	}

	public void setUpVote(Integer upVote) {
		this.upVote = upVote;
	}

	public Integer getDownVote() {
		return downVote;
	}

	public void setDownVote(Integer downVote) {
		this.downVote = downVote;
	}

	public Integer getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
}
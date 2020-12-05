package com.ynov.microservices.tlm.comment;

import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "comments")
public class Comment {
	
	/****************************************************************************************************/
	/******************************************** VARIABLES *********************************************/
	/****************************************************************************************************/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String content;
	@Column
	private String author;
	@Column
	private Integer quote;	
	
	/****************************************************************************************************/
	/******************************************** METHODS ***********************************************/
	/****************************************************************************************************/
	
	/******************************************** ID -***************************************************/	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	/******************************************** QUOTE ***************************************************/	
	public Integer getQuote() {
		return quote;
	}
	public void setQuote(Integer quote) {
		this.quote = quote;
	}
	
	/******************************************** AUTHOR **************************************************/	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/******************************************** TEXT ****************************************************/	
	public String getContent() {
		return content;
	}
	public void setContent(String text) {
		this.content = text;
	}	
	
}

package com.ynov.microservices.pojo;

public class AuthorInfoPojo {
		private String pseudo;
		private Integer avgUpvote = 0;
		private Integer avgDownvote = 0;
		
		public String getPseudo() {
			return pseudo;
		}
		public void setPseudo(String pseudo) {
			this.pseudo = pseudo;
		}
		public Integer getAvgUpvote() {
			return avgUpvote;
		}
		public void setAvgUpvote(Integer avgUpvote) {
			this.avgUpvote = avgUpvote;
		}
		public Integer getAvgDownvote() {
			return avgDownvote;
		}
		public void setAvgDownvote(Integer avgDownvote) {
			this.avgDownvote = avgDownvote;
		}
}

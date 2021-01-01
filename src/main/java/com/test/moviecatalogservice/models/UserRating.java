package com.test.moviecatalogservice.models;

import java.util.List;

public class UserRating {
	
	public UserRating() {
		// TODO Auto-generated constructor stub
	}
	
	

	public UserRating(List<Rating> userRating) {
		super();
		this.userRating = userRating;
	}



	private List<Rating> userRating;

	public List<Rating> getUserRating() {
		return userRating;
	}

	public void setUserRating(List<Rating> userRating) {
		this.userRating = userRating;
	}
	
	
}

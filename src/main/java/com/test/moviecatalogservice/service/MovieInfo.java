package com.test.moviecatalogservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.moviecatalogservice.models.CatalogItem;
import com.test.moviecatalogservice.models.Movie;
import com.test.moviecatalogservice.models.Rating;

@Service
public class MovieInfo {
	
	@Autowired
	private RestTemplate restTemplate;
	

	 @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
			 commandProperties = {
					 @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="2000"),
					 @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="5"),
					 @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="50"),
					 @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="5000")
			 })
	public CatalogItem getCatalogItem(Rating rating) {
		 Movie movie = restTemplate.getForObject("http://movie-details-service/movies/"+rating.getMovieId(), Movie.class);
		 return new CatalogItem(movie.getName(),"test",rating.getRating());
		
	}
	 //bulkheadpattern
	 @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
			 threadPoolKey = "movieInfoPool",
			 threadPoolProperties =  {
					 @HystrixProperty(name = "coreSize", value="20"),
					 @HystrixProperty(name = "maxQueueSize", value="10"),
					
			 })
	public CatalogItem getCatalogItem2(Rating rating) {
		 Movie movie = restTemplate.getForObject("http://movie-details-service/movies/"+rating.getMovieId(), Movie.class);
		 return new CatalogItem(movie.getName(),"test",rating.getRating());
		
	}
	 
	 public CatalogItem getFallbackCatalogItem(Rating rating) {
		 return new CatalogItem("Movie name not found","",rating.getRating());
	 }

}

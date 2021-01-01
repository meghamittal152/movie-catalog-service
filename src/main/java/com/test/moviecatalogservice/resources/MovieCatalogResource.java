package com.test.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import com.test.moviecatalogservice.models.CatalogItem;
import com.test.moviecatalogservice.models.UserRating;
import com.test.moviecatalogservice.service.MovieInfo;
import com.test.moviecatalogservice.service.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	
	//good practice is to autowire it by creating a single instance
	
	@Autowired
	private WebClient.Builder webClientBuilder;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	Logger logger = LoggerFactory.getLogger(MovieCatalogResource.class);
	
	
	//@Autowired
//	private DiscoveryClient discoveryClient;
	

	 @RequestMapping("/{userId}") 
	
	 public List<CatalogItem>getCatalog(@PathVariable("userId") String userId){
		 logger.trace("Input userid is"+ userId);
		 
		 
		 
		 //creating webclient
		// WebClient.Builder builder = WebClient.builder();
		 
		 //creating a rest template here
		// RestTemplate restTemplate = new RestTemplate();
		// Movie movie = restTemplate.getForObject("http://localhost:8081/movies/megha", Movie.class);
		 
		 
		 //hard coded ratings
			/*
			 * List<Rating> ratings = Arrays.asList( new Rating("1234", 3), new
			 * Rating("4567", 4) );
			 */
		// get all rated movieIds(ratings data service)
		 //hardcoded port number
	//	UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId, UserRating.class);
		//using service discovery
		 UserRating ratings = userRatingInfo.getUserRating(userId);
		 
		//For each movieId, call movie info service to get get details of the movie
		 return ratings.getUserRating().stream().map(rating-> movieInfo.getCatalogItem(rating))
				 .collect(Collectors.toList());
				/*
				 * Movie movie = webClientBuilder.build() .get()
				 * .uri("http://localhost:8082/movies/"+rating.getMovieId()) .retrieve()
				 * .bodyToMono(Movie.class) .block();
				 *///blocking execution till the mono is fulfilled
			
				//put them all together
			
		
		 
	 }

		 
/*	 public List<CatalogItem>getCatalog(@PathVariable("userId") String userId){
		 return Collections.singletonList( 
				 new CatalogItem("Transformers","test",4) 
				 ); 
		 }*/
	 
}

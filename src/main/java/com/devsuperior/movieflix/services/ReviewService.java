package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private AuthService authService;

	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		Review review = new Review();
		review.setText(dto.getText());
		review.setMovie(movieRepository.getReferenceById(dto.getMovieId()));
		review.setUser(authService.authenticated());
		review = repository.save(review);
		return toDTO(review);
	}

	private ReviewDTO toDTO(Review entity) {
		ReviewDTO dto = new ReviewDTO();
		dto.setId(entity.getId());
		dto.setText(entity.getText());

		Movie movie = entity.getMovie();
		if (movie != null) {
			dto.setMovieId(movie.getId());
		}

		User user = entity.getUser();
		if (user != null) {
			dto.setUserId(user.getId());
			dto.setUserName(user.getName());
			dto.setUserEmail(user.getEmail());
		}
		return dto;
	}
}

package com.devsuperior.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.MovieReviewsDTO;
import com.devsuperior.movieflix.dto.ReviewMinDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.projections.ReviewUserProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Movie movie = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		return toDetailsDTO(movie);
	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findMovies(Long genreId, Pageable pageable) {
		Genre genre = (genreId == null || genreId == 0) ? null : new Genre(genreId, null);
		return repository.searchByGenre(genre, pageable).map(this::toCardDTO);
	}

	@Transactional(readOnly = true)
	public MovieReviewsDTO findReviews(Long id) {
		Movie movie = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found"));
		List<ReviewUserProjection> reviews = reviewRepository.findReviewsByMovie(id);
		return toMovieReviewsDTO(movie, reviews);
	}

	private MovieCardDTO toCardDTO(MovieProjection projection) {
		MovieCardDTO dto = new MovieCardDTO();
		dto.setId(projection.getId());
		dto.setTitle(projection.getTitle());
		dto.setSubTitle(projection.getSubTitle());
		dto.setYear(projection.getYear());
		dto.setImgUrl(projection.getImgUrl());
		return dto;
	}

	private MovieDetailsDTO toDetailsDTO(Movie entity) {
		MovieDetailsDTO dto = new MovieDetailsDTO();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setSubTitle(entity.getSubTitle());
		dto.setYear(entity.getYear());
		dto.setImgUrl(entity.getImgUrl());
		dto.setSynopsis(entity.getSynopsis());

		Genre genre = entity.getGenre();
		if (genre != null) {
			GenreDTO genreDTO = new GenreDTO();
			genreDTO.setId(genre.getId());
			genreDTO.setName(genre.getName());
			dto.setGenre(genreDTO);
		}
		return dto;
	}

	private MovieReviewsDTO toMovieReviewsDTO(Movie movie, List<ReviewUserProjection> reviews) {
		MovieReviewsDTO dto = new MovieReviewsDTO();
		dto.setTitle(movie.getTitle());
		dto.setSubTitle(movie.getSubTitle());
		dto.setYear(movie.getYear());
		dto.setImgUrl(movie.getImgUrl());
		dto.setSynopsis(movie.getSynopsis());

		for (ReviewUserProjection r : reviews) {
			dto.getReviews().add(new ReviewMinDTO(r.getText(), r.getUserName()));
		}
		return dto;
	}
}

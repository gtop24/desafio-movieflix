package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT obj.id AS id, obj.title AS title, obj.subTitle AS subTitle, "
			+ "obj.year AS year, obj.imgUrl AS imgUrl "
			+ "FROM Movie obj WHERE (:genre IS NULL OR obj.genre = :genre)")
	Page<MovieProjection> searchByGenre(@Param("genre") Genre genre, Pageable pageable);
}

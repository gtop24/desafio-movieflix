package com.devsuperior.movieflix.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;

@Service
public class GenreService {

	@Autowired
	private GenreRepository repository;

	@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		return repository.findAll(Sort.by("id")).stream().map(this::toDTO).toList();
	}

	private GenreDTO toDTO(Genre entity) {
		GenreDTO dto = new GenreDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		return dto;
	}
}

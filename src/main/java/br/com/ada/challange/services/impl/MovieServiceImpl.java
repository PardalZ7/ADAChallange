package br.com.ada.challange.services.impl;

import br.com.ada.challange.clients.ClientOMBDFeing;
import br.com.ada.challange.config.OMDBProperties;
import br.com.ada.challange.domain.dto.MovieDTO;
import br.com.ada.challange.domain.dto.OMDBMovieDTO;
import br.com.ada.challange.domain.entities.MovieEntity;
import br.com.ada.challange.repositories.MovieRepository;
import br.com.ada.challange.services.exceptions.ObjectNotFoundException;
import br.com.ada.challange.services.interfaces.MovieServiceInterface;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@Service
public class MovieServiceImpl implements MovieServiceInterface {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ModelMapper skipNullMapper;

    @Override
    public MovieDTO create(MovieDTO movieDTO) {

        if (movieDTO.getEnable() == null)
            movieDTO.setEnable(true);

        return this.mapper.map(this.repository.save(this.mapper.map(movieDTO, MovieEntity.class)), MovieDTO.class);
    }

    @Override
    public MovieDTO findById(Long id) {
        Optional<MovieEntity> user = repository.findById(id);
        if (!user.isPresent())
            throw new ObjectNotFoundException("Movie not found");
        return this.mapper.map(user.get(), MovieDTO.class);
    }

    @Override
    public List<MovieDTO> findAll(Pageable pageable, Boolean showAll) {
        if (showAll)
            return this.repository.findAll(pageable).stream().map(x -> mapper.map(x, MovieDTO.class)).toList();
        else
            return this.repository.findAllEnable(pageable).stream().map(x -> mapper.map(x, MovieDTO.class)).toList();
    }

    @Override
    public MovieDTO update(MovieDTO MovieDTO) {

        MovieDTO movieOnDB = this.findById(MovieDTO.getId());
        this.skipNullMapper.map(MovieDTO, movieOnDB);

        return this.mapper.map(this.repository.save(this.mapper.map(movieOnDB, MovieEntity.class)),
                MovieDTO.class);

    }

    @Override
    public void deleteById(Long id) {
        MovieDTO user = this.findById(id);
        user.setEnable(false);
        this.repository.save(this.mapper.map(user, MovieEntity.class));
    }

}

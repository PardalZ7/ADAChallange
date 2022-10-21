package br.com.ada.challange.resources;

import br.com.ada.challange.domain.dto.MovieDTO;
import br.com.ada.challange.services.impl.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/admin/movie")
@RestController
public class MovieResource {

    public static final String ID = "/{id}";

    @Autowired
    private MovieServiceImpl service;

    @GetMapping(value = ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<MovieDTO>> findAll(@RequestParam(required=false) Integer page,
                                                 @RequestParam(required=false) Integer pageSize,
                                                 @RequestParam(required=false) Boolean showAll
                                                ) {

        showAll = showAll == null ? false : showAll;
        page = ((page == null) || (page < 0)) ? 0 : page;
        pageSize = ((pageSize == null) || (pageSize < 1)) ? 100 : pageSize;

        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, pageSize), showAll));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@RequestBody MovieDTO movieBody) {
        MovieDTO newMovie = service.create(movieBody);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID).buildAndExpand(newMovie.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody @NotNull MovieDTO movieBody){
        movieBody.setId(id);
        return ResponseEntity.ok().body(service.update(movieBody));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

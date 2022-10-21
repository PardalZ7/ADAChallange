package br.com.ada.challange.resources;

import br.com.ada.challange.domain.dto.UserDTO;
import br.com.ada.challange.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RequestMapping
@RestController
public class UserResource {

    public static final String ID = "/{id}";
    public static final String USER = "/user";
    public static final String ADMIN_ROLE = "/admin";

    @Autowired
    private UserServiceImpl service;

    @GetMapping(value = USER + ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping(value = USER)
    public ResponseEntity<List<UserDTO>> findAll(@RequestParam(required=false) Integer page,
                                                 @RequestParam(required=false) Integer pageSize,
                                                 @RequestParam(required=false) Boolean showAll
                                                ) {

        showAll = showAll == null ? false : showAll;
        page = ((page == null) || (page < 0)) ? 0 : page;
        pageSize = ((pageSize == null) || (pageSize < 1)) ? 100 : pageSize;

        return ResponseEntity.ok().body(service.findAll(PageRequest.of(page, pageSize), showAll));
    }

    @PostMapping(value = ADMIN_ROLE + USER)
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userBody) {
        UserDTO newUser = service.create(userBody);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID).buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ADMIN_ROLE + USER + ID)
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody @NotNull UserDTO userBody){
        userBody.setId(id);
        return ResponseEntity.ok().body(service.update(userBody));
    }

    @DeleteMapping(value = ADMIN_ROLE + USER + ID)
    public ResponseEntity<Void> delete(@PathVariable Long id){
        this.service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

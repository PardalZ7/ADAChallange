package br.com.ada.challange.clients;

import br.com.ada.challange.domain.dto.OMDBMovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "omdbMovies", url = "https://www.omdbapi.com/")
public interface ClientOMBDFeing {

    @RequestMapping(method = RequestMethod.GET)
    OMDBMovieDTO getFilm(@RequestParam("t") String title, @RequestParam("apiKey") String key);

}

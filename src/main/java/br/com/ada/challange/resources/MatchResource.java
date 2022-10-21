package br.com.ada.challange.resources;

import br.com.ada.challange.domain.dto.MatchDTO;
import br.com.ada.challange.domain.dto.NextRoundResponseDTO;
import br.com.ada.challange.config.JwtProperties;
import br.com.ada.challange.domain.dto.RankingDTO;
import br.com.ada.challange.domain.enums.QuizResponse;
import br.com.ada.challange.utils.SecurityUtils;
import br.com.ada.challange.services.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/match")
@RestController
public class MatchResource {

    public static final String NEXT_ROUND = "/nextround";
    public static final String ANSWER = "/answer";
    public static final String RANKING = "/ranking";
    public static final String BYUSER = "/byUser";

    public static final String RESPONSE = "/{response}";
    public static final String ID = "/{id}";

    @Autowired
    private MatchServiceImpl service;

    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping(value = NEXT_ROUND, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NextRoundResponseDTO> nextRound(@RequestHeader(JwtProperties.HEADER_STRING) String token) {
        return ResponseEntity.ok().body(service.next(securityUtils.getUserFromToken(token).get()));
    }

    @PostMapping(value = ANSWER + RESPONSE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MatchDTO> answer(@RequestHeader(JwtProperties.HEADER_STRING) String token,
                                           @PathVariable String response) {

        return ResponseEntity.ok().body(service.answer(securityUtils.getUserFromToken(token).get(),
                QuizResponse.valueOf(response)));

    }

    @GetMapping(value = RANKING, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RankingDTO>> ranking() {
        return ResponseEntity.ok().body(service.ranking());
    }

    @GetMapping(value = BYUSER + ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MatchDTO>> byUser(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.matchesByUser(id));
    }

}

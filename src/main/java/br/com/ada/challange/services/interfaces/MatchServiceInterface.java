package br.com.ada.challange.services.interfaces;

import br.com.ada.challange.domain.dto.MatchDTO;
import br.com.ada.challange.domain.dto.NextRoundResponseDTO;
import br.com.ada.challange.domain.dto.RankingDTO;
import br.com.ada.challange.domain.dto.UserDTO;
import br.com.ada.challange.domain.enums.QuizResponse;

import java.util.List;

public interface MatchServiceInterface {

    NextRoundResponseDTO next(UserDTO user);
    MatchDTO answer(UserDTO userDto, QuizResponse response);
    List<RankingDTO> ranking();
    List<MatchDTO> matchesByUser(Long userId);

}

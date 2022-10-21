package br.com.ada.challange.services.impl;

import br.com.ada.challange.helpers.UserHelpers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MovieServiceImpl movieService;

    @Autowired
    private MatchServiceImpl matchService;

    @BeforeEach
    void setUp() {

        userService.create(UserHelpers.getUserAdmin01());
        userService.create(UserHelpers.getUser01());

    }

    @Test
    void next() {

    }

    @Test
    void answer() {
    }

    @Test
    void ranking() {
    }

    @Test
    void matchesByUser() {
    }
}
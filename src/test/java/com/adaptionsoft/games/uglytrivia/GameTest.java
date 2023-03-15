package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @Test
    void aGameMustHaveAtLeastTwoPlayers() {
        Game game = new Game("Player1", "Player2");

        assertEquals(2, game.howManyPlayers());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void add_playerNameIsEmpty_throwsException(String name) {
        Game game = new Game("Player1", "Player2");

        assertThrows(IllegalArgumentException.class, () -> game.add(name));
    }

}
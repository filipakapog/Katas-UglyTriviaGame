package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @ParameterizedTest
    @MethodSource
    void aGameMustHaveAtLeastTwoPlayers(List<String> playerNames) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> Game.newGame(playerNames)
        );

        assertEquals("We need at least two players", exception.getMessage());
    }

    private static Stream<List<String>> aGameMustHaveAtLeastTwoPlayers() {
        return Stream.of(Collections.EMPTY_LIST, List.of("Player 1"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void add_playerNameIsEmpty_throwsException(String name) {
        Game game = Game.newGame(List.of("Player1", "Player2"));

        assertThrows(IllegalArgumentException.class, () -> game.add(name));
    }
}
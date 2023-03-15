package com.adaptionsoft.games.uglytrivia;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.LinkedList;
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
    @MethodSource
    void aGameHavingPlayerNamesEmpty_throwsException(List<String> playerNames) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> Game.newGame(playerNames)
        );
        assertEquals("Name should not be blank", exception.getMessage());
    }

    private static Stream<List<String>> aGameHavingPlayerNamesEmpty_throwsException() {
        return Stream.of(
                List.of("", ""),
                List.of("Player1", ""),
                List.of("", "Player 1"),
                List.of("   ", " \t\n "),
                nullableElementsList(null, null),
                nullableElementsList("Player", null),
                nullableElementsList(null, "Player")
        );
    }

    private static List<String> nullableElementsList(String ... nullableElements) {
        List<String> result = new LinkedList<>();
        for (String nullableElement : nullableElements) {
            result.add(nullableElement);
        }
        return result;
    }
}
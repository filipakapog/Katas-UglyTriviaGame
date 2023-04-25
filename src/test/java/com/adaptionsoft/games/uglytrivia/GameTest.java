package com.adaptionsoft.games.uglytrivia;

import com.adaptionsoft.games.utils.NamesGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

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


    @Test
    void aGameMustHaveAtMostSixPlayers() {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> Game.newGame(NamesGenerator.generate7Names())
        );

        assertEquals("We need at most six players", exception.getMessage());
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

    @ParameterizedTest
    @MethodSource
    void aGameMustHaveUniquePlayersNames(List<String> playerNames) {
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> Game.newGame(playerNames)
        );

        assertEquals("Player names must be unique", exception.getMessage());
    }

    @Test
    void roll_currentPlayerOutsidePenaltyBoxAndInitialPositionIs0AndRollIs2_playerIsAdvancedNewPositionCategoryAndTheSportsQuestionArePrinted() {
        // Given
        Game game = Game.newGame(List.of("Joe", "Jane"));
        Integer currentPlayerPosition = game.getCurrentPlayerPositionTest();
        int diceValue = 2;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        // When
        game.roll(diceValue);

        // Then
        int newPlayerPosition = currentPlayerPosition + diceValue;
        assertEquals(newPlayerPosition, game.getCurrentPlayerPositionTest());
        assertPositionCategoryAndSportsQuestionArePrinted(baos);
    }

    private void assertPositionCategoryAndSportsQuestionArePrinted(ByteArrayOutputStream baos) {
        assertTrue(baos.toString().contains("Joe's new location is 2"));
        assertTrue(baos.toString().contains("The category is Sports"));
        assertTrue(baos.toString().contains("Sports Question 0"));
    }

    @Test
    void roll_currentPlayerOutsidePenaltyBoxInitialPositionIs11AndRollIs1_playerPositionChangesTo0() {
        // Given
        Game game = Game.newGame(List.of("Joe", "Jane"));
        game.setCurrentPlayerPositionTest(11);
        int diceValue = 1;


        // When
        game.roll(diceValue);

        // Then
        assertEquals(0, game.getCurrentPlayerPositionTest());
    }

    @Test
    void roll_currentPlayerInPenaltyBoxDiceIsEven_playerRemainsInPenaltyBox() {
        // Given
        Game game = Game.newGame(List.of("Joe", "Jane"));
        game.setCurrentPlayer("Joe");
        game.placeCurrentPlayerInPenaltyBoxTest();

        // When
        game.roll(4);

        assertTrue(game.isPlayerInPenaltyBoxTest("Joe"));
    }

    private static Stream<List<String>> aGameMustHaveUniquePlayersNames() {
        return Stream.of(
                List.of("Player 1", "Player 1"),
                List.of("Player 1", "Player 2", "Player 1"),
                List.of("Player 2", "Player 1", "Player 1")
        );
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
        Collections.addAll(result, nullableElements);
        return result;
    }
}
package com.adaptionsoft.games.uglytrivia;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {

    @ParameterizedTest
    @NullAndEmptySource
    void add_playerNameIsEmpty_throwsException(String name) {
        Game game = new Game();

        assertThrows(IllegalArgumentException.class, () -> game.add(name));
    }

}
package com.adaptionsoft.games.uglytrivia;

public enum Category {
    POP,
    SCIENCE,
    ROCK,
    SPORTS;

    @Override
    public String toString() {
        String lowerCase = this.name().toLowerCase();
        return getFirstCharacter(lowerCase).toUpperCase() + getAllCharactersExceptTheFirst(lowerCase);
    }

    private static String getFirstCharacter(String lowerCase) {
        return lowerCase.substring(0, 1);
    }

    private static String getAllCharactersExceptTheFirst(String lowerCase) {
        return lowerCase.substring(1);
    }
}

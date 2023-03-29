package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class GameQuestions {

    private final LinkedList<String> popQuestions = new LinkedList<>();
    private final LinkedList<String> scienceQuestions = new LinkedList<>();
    private final LinkedList<String> sportsQuestions = new LinkedList<>();
    private final LinkedList<String> rockQuestions = new LinkedList<>();

    public GameQuestions(int nrOfQuestions) {
        for (int i = 0; i < nrOfQuestions; i++) {
            popQuestions.addLast(createPopQuestion(i));
            scienceQuestions.addLast(createScienceQuestion(i));
            sportsQuestions.addLast(createSportsQuestion(i));
            rockQuestions.addLast(createRockQuestion(i));
        }
    }

    private static String createPopQuestion(int index) {
        return "Pop Question " + index;
    }

    private String createScienceQuestion(int index) {
        return "Science Question " + index;
    }

    private String createSportsQuestion(int index) {
        return "Sports Question " + index;
    }

    public String createRockQuestion(int index) {
        return "Rock Question " + index;
    }

    public String drawAPopQuestion() {
        return popQuestions.removeFirst();
    }

    public String drawAScienceQuestion() {
        return scienceQuestions.removeFirst();
    }

    public String drawASportsQuestion() {
        return sportsQuestions.removeFirst();
    }

    public String drawARockQuestion() {
        return rockQuestions.removeFirst();
    }
}

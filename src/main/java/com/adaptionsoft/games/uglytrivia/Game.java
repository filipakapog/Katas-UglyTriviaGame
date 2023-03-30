package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private static final int NR_OF_COINS_TO_END_THE_GAME = 6;

	private final List<String> players = new ArrayList<>();
	private List<Integer> places = new ArrayList<>();
	private List<Integer> purses = new ArrayList<>();
	private List<Boolean> inPenaltyBox = new ArrayList<>();

	private final GameQuestions gameQuestions;

	private int currentPlayer = 0;
	private boolean isGettingOutOfPenaltyBox;

	private Game() {
		gameQuestions = new GameQuestions(50);
	}

	public static Game newGame(List<String> playerNames) {
		Game basicGame = new Game();

		checkIfMinimumNumberOfPlayers(playerNames);
		addPlayersInGame(basicGame, playerNames);
		initGame(basicGame);

		return basicGame;
	}

	private static void checkIfMinimumNumberOfPlayers(List<String> playerNames) {
		if (playerNames.size() < 2) {
			throw new IllegalArgumentException("We need at least two players");
		}
	}

	private static void addPlayersInGame(Game basicGame, List<String> playerNames) {
		for (String playerName : playerNames) {
			throwExceptionIfPlayerNameIsNotUnique(basicGame, playerName);
			basicGame.add(playerName);
		}
	}

	private static void throwExceptionIfPlayerNameIsNotUnique(Game basicGame, String playerName) {
		if (basicGame.players.contains(playerName)) {
			throw new IllegalArgumentException("Player names must be unique");
		}
	}

	private void add(String playerName) {
		if (null == playerName || playerName.isBlank()) {
			throw new IllegalArgumentException("Name should not be blank");
		}

		players.add(playerName);

		print(playerName + " was added");
		print("They are player number " + players.size());
	}

	private void print(String msg) {
		System.out.println(msg);
	}

	private static void initGame(Game game) {
		game.places = new ArrayList<>(howManyPlayers(game));
		game.purses = new ArrayList<>(howManyPlayers(game));
		game.inPenaltyBox = new ArrayList<>(howManyPlayers(game));

		for (int i = 0; i < howManyPlayers(game); i++) {
			game.places.add(0);
			game.purses.add(0);
			game.inPenaltyBox.add(false);
		}
	}

	private static int howManyPlayers(Game game) {
		return game.players.size();
	}

	public void roll(int roll) {
		print(getCurrentPlayer() + " is the current player");
		print("They have rolled a " + roll);

		if (isCurrentPlayerInPenaltyBox()) {
			advancePlayerIfDiceIsNotEven(roll);
		} else {
			advancePlayer(roll);
		}
	}

	private void advancePlayerIfDiceIsNotEven(int roll) {
		if (roll % 2 != 0) {
			currentPlayerGetsOutOfPenaltyBox();
			print(getCurrentPlayer() + " is getting out of the penalty box");
			advancePlayer(roll);
		} else {
			print(getCurrentPlayer() + " is not getting out of the penalty box");
			currentPlayerRemainsInPenaltyBox();
		}
	}

	private void advancePlayer(int roll) {
		advanceCurrentPlayer(roll);
		if (getCurrentPlayerPosition() > 11) advanceCurrentPlayer(-12);

		print(getCurrentPlayer()
				+ "'s new location is "
				+ getCurrentPlayerPosition());
		print("The category is " + currentCategory());
		askQuestion();
	}

	private void advanceCurrentPlayer(int nrOfStepsToAdvance) {
		places.set(currentPlayer, getCurrentPlayerPosition() + nrOfStepsToAdvance);
	}

	private void currentPlayerGetsOutOfPenaltyBox() {
		isGettingOutOfPenaltyBox = true;
	}

	private void currentPlayerRemainsInPenaltyBox() {
		isGettingOutOfPenaltyBox = false;
	}

	private Integer getCurrentPlayerPosition() {
		return places.get(currentPlayer);
	}

	/* Used in tests*/
	Integer getCurrentPlayerPositionTest() {
		return getCurrentPlayerPosition();
	}

	/* Used in tests*/
	void settCurrentPlayerPositionTest(int position) {
		places.set(currentPlayer, position);
	}

	private String getCurrentPlayer() {
		return players.get(currentPlayer);
	}

	private boolean isCurrentPlayerInPenaltyBox() {
		return Boolean.TRUE.equals(inPenaltyBox.get(currentPlayer));
	}

	private void askQuestion() {
		switch (currentCategory()) {
			case POP -> print(gameQuestions.drawAPopQuestion());
			case ROCK -> print(gameQuestions.drawARockQuestion());
			case SPORTS -> print(gameQuestions.drawASportsQuestion());
			case SCIENCE -> print(gameQuestions.drawAScienceQuestion());
		}
	}

	private Category currentCategory() {
		return switch (getCurrentPlayerPosition()) {
			case 0, 4, 8 -> Category.POP;
			case 1, 5, 9 -> Category.SCIENCE;
			case 2, 6, 10 -> Category.SPORTS;
			default -> Category.ROCK;
		};
	}

	public boolean wasCorrectlyAnswered() {
		if (isCurrentPlayerInPenaltyBox()) {
			if (isGettingOutOfPenaltyBox) {
				print("Answer was correct!!!!");
				addACoinToCurrentPlayersPurse();
				print(getCurrentPlayer()
						+ " now has "
						+ getCurrentPlayersNrOfCoins()
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				switchToNextPlayer();
				return winner;
			} else {
				switchToNextPlayer();
				return true;
			}
		} else {
			print("Answer was corrent!!!!");
			addACoinToCurrentPlayersPurse();
			print(getCurrentPlayer()
					+ " now has "
					+ getCurrentPlayersNrOfCoins()
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			switchToNextPlayer();
			return winner;
		}
	}

	private void addACoinToCurrentPlayersPurse() {
		purses.set(currentPlayer, purses.get(currentPlayer) + 1);
	}

	public boolean wrongAnswer() {
		print("Question was incorrectly answered");
		print(getCurrentPlayer() + " was sent to the penalty box");
		placeCurrentPlayerInPenaltyBox();
		switchToNextPlayer();
		return true;
	}

	private void switchToNextPlayer() {
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
	}

	private void placeCurrentPlayerInPenaltyBox() {
		inPenaltyBox.set(currentPlayer, true);
	}

	private boolean didPlayerWin() {
		return getCurrentPlayersNrOfCoins() != NR_OF_COINS_TO_END_THE_GAME;
	}

	private Integer getCurrentPlayersNrOfCoins() {
		return purses.get(currentPlayer);
	}
}

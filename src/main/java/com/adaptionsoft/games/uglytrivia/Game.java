package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private static final int NR_OF_COINS_TO_END_THE_GAME = 6;

	List<String> players = new ArrayList<>();
	List<Integer> places = new ArrayList<>();
	List<Integer> purses = new ArrayList<>();
	List<Boolean> inPenaltyBox = new ArrayList<>();

	GameQuestions gameQuestions;

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

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

	public void roll(int roll) {
		print(getCurrentPlayer() + " is the current player");
		print("They have rolled a " + roll);

		if (isCurrentPlayerInPenaltyBox()) {
			if (roll % 2 != 0) {
				currentPlayerGetsOutOfPenaltyBox();
				print(getCurrentPlayer() + " is getting out of the penalty box");
				places.set(currentPlayer, places.get(currentPlayer) + roll);
				if (getCurrentPlayerPosition() > 11) places.set(currentPlayer, getCurrentPlayerPosition() - 12);

				print(getCurrentPlayer()
						+ "'s new location is "
						+ getCurrentPlayerPosition());
				print("The category is " + currentCategory());
				askQuestion();
			} else {
				print(getCurrentPlayer() + " is not getting out of the penalty box");
				currentPlayerRemainsInPenaltyBox();
			}
		} else {
			places.set(currentPlayer, places.get(currentPlayer) + roll);
			if (getCurrentPlayerPosition() > 11) places.set(currentPlayer, getCurrentPlayerPosition() - 12);

			print(getCurrentPlayer()
					+ "'s new location is "
					+ getCurrentPlayerPosition());
			print("The category is " + currentCategory());
			askQuestion();
		}
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
				purses.set(currentPlayer, purses.get(currentPlayer) + 1);
				print(getCurrentPlayer()
						+ " now has "
						+ purses.get(currentPlayer)
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
			purses.set(currentPlayer, purses.get(currentPlayer) + 1);
			print(getCurrentPlayer()
					+ " now has "
					+ purses.get(currentPlayer)
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			switchToNextPlayer();
			return winner;
		}
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
		return !(purses.get(currentPlayer) == NR_OF_COINS_TO_END_THE_GAME);
	}
}

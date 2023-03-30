
package com.adaptionsoft.games.trivia.runner;
import java.util.List;
import java.util.Random;

import com.adaptionsoft.games.uglytrivia.Game;




	private static boolean notAWinner;

	private static Random random;

	public static void main(String[] args) {
		Game aGame = Game.newGame(List.of("Chet", "Pat", "Sue"));
		Random rand = getRandom();

		do {
			aGame.roll(rand.nextInt(5) + 1);
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);
	}

	public static void setRandom(Random random) {
		GameRunner.random = random;
	}

	private static Random getRandom() {
		if (GameRunner.random == null) {
			GameRunner.random = new Random();
		}
		return GameRunner.random;
	}
}

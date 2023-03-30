# Ugly Trivia Game Kata ![example workflow](https://github.com/filipakapog/UglyTriviaGameKata/actions/workflows/build.yml/badge.svg?event=push)

This repo applies refactorings to the Ugly Trivia Game Kata defined [here](https://kata-log.rocks/ugly-trivia-kata).

This is how the [initial code](https://github.com/jbrains/trivia/tree/master/java) looked like

# Some Implemented Requirements
Regarding the requirements defined [here](https://github.com/victorrentea/kata-trivia-java), I've implemented the following:
- [ ] The max number of players must be changed to 6
- [ ] Add a new category of questions "Geography"
- [x] There must be minimum 2 players to start the game
- [x] The game must not start until all players are added. In other words, new players can't join after the game has started.
- [x] No two players are allowed to have the same name.
- [ ] [hard] After a wrong answer, a player only goes to Penalty box if they fail to answer a second question in the same category. In other words, he/she is given a 'second chance' from the same category.
- [ ] [hard] Load the question from 4 properties files: rock.properties, sports.properties ...
- [ ] [hard] A streak is a consecutive sequence of correct answers for a given player. After providing 3 consecutive correct answers, a player earns 2 points with any subsequent correct answer. When a player gives a wrong answer: (a) if (s)he was on a streak, the streak ends OR (b) if there was no streak, the player goes to Penalty box. (In other words, with a running active streak a player does not go to Penalty box, but instead he/she looses the streak). In addition, the game should be won at a double amount of points.

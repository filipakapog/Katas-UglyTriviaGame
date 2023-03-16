package com.adaptionsoft.games.trivia;


import com.adaptionsoft.games.trivia.runner.GameRunner;
import com.adaptionsoft.games.utils.TestResourcesFileReader;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Random;

import static com.adaptionsoft.games.utils.TestResourcesFileReader.readAsString;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GoldenMaster {

	@Test
	void masterIsMaster() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);

		GameRunner.setRandom(new Random(2));
		GameRunner.main(new String[]{});

		assertEquals(readAsString("trivia/expectedResponse.txt"), baos.toString());
	}
}

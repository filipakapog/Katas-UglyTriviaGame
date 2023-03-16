package com.adaptionsoft.games.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TestResourcesFileReader {

    public static String readAsString(String filePath) {
        try (InputStream inputStream = getClassLoader().getResourceAsStream(filePath)) {
            return tryToReadFromStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ClassLoader getClassLoader() {
        return TestResourcesFileReader.class.getClassLoader();
    }

    private static String tryToReadFromStream(InputStream input) throws IOException {
        return new String(input.readAllBytes(), StandardCharsets.UTF_8);
    }
}

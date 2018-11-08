package uk.gov.hmcts.probate.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class TestUtils {

    public static String getJSONFromFile(String fileName) throws IOException {
      return new String(Files.readAllBytes(Paths.get("src/test/resources", fileName)));
    }

    private TestUtils(){
    }
}

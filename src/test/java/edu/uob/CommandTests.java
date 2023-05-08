package edu.uob;

import com.alexmerz.graphviz.ParseException;
import edu.uob.GameExceptions.GameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.nio.file.Paths;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class CommandTests {

    private GameServer server;

    // Make a new server for every @Test (i.e. this method runs before every @Test test case)
    @BeforeEach
    void setup() throws IOException, ParseException, ParserConfigurationException, SAXException, GameException {
        File entitiesFile = Paths.get("config" + File.separator + "entities.dot").toAbsolutePath().toFile();
        File actionsFile = Paths.get("config" + File.separator + "actions.xml").toAbsolutePath().toFile();
        server = new GameServer(entitiesFile, actionsFile);
        //client = new GameClient("Simon");
    }

    // Test to spawn a new server and send a simple "look" command
    @Test
    void testLookingAroundStartLocation() {
        server.handleCommand("Simon: get axe");
        server.handleCommand("Simon: get paper");
        server.handleCommand("Simon: get scissor");
        server.handleCommand("Simon: goto forest");

        String response = server.handleCommand("Simon: inv").toLowerCase();
        assertTrue(response.contains("axe"), "Did not see description of artifacts in response to inv");
        assertTrue(response.contains("scissor"), "Did not see description of artifacts in response to inv");
        assertTrue(response.contains("paper"), "Did not see description of furniture in response to inv");

        String response2 = server.handleCommand("Simon: cut down paper").toLowerCase();
        assertTrue(response2.contains("cut the paper"), "Did not see description of action in response to cut");

        String response3 = server.handleCommand("Simon: cut down tree").toLowerCase();
        assertTrue(response3.contains("more than one"), "Did not see description of action in response to cut");

        String response4 = server.handleCommand("Simon: cutdown tree").toLowerCase();
        assertTrue(response4.contains("cut down the tree"), "Did not see description of action in response to cut");
    }
}
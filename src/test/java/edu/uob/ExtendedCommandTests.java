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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// PLEASE READ:
// The tests in this file will fail by default for a template skeleton, your job is to pass them
// and maybe write some more, read up on how to write tests at
// https://junit.org/junit5/docs/current/user-guide/#writing-tests
final class ExtendedCommandTests {

    private GameServer server;

    // Make a new server for every @Test (i.e. this method runs before every @Test test case)
    @BeforeEach
    void setup() throws IOException, ParseException, ParserConfigurationException, SAXException, GameException {
        File entitiesFile = Paths.get("config" + File.separator + "extended-entities.dot").toAbsolutePath().toFile();
        File actionsFile = Paths.get("config" + File.separator + "extended-actions.xml").toAbsolutePath().toFile();
        server = new GameServer(entitiesFile, actionsFile);
    }

    // Test to spawn a new server and send a simple "look" command
    @Test
    void testLookingAroundStartLocation() {
        String response = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response.contains("a log cabin"), "Did not see description of room in response to look");
        assertTrue(response.contains("magic potion"), "Did not see description of artifacts in response to look");
        assertTrue(response.contains("sharp axe"), "Did not see description of furniture in response to look");
        assertTrue(response.contains("silver coin"), "Did not see description of furniture in response to look");
        assertTrue(response.contains("locked wooden trapdoor"), "Did not see description of furniture in response to look");
    }

    @Test
    void testIntegrationCommands(){
        String response = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response.contains("a log cabin"), "Did not see description of room in response to look");
        assertTrue(response.contains("magic potion"), "Did not see description of artifacts in response to look");
        assertTrue(response.contains("sharp axe"), "Did not see description of furniture in response to look");
        assertTrue(response.contains("silver coin"), "Did not see description of furniture in response to look");
        assertTrue(response.contains("locked wooden trapdoor"), "Did not see description of furniture in response to look");
        assertTrue(response.contains("forest"), "Did not see description of path in response to look");

        String response2 = server.handleCommand("Simon: Get Magic Potion").toLowerCase();
        assertTrue(response2.contains("picked up a potion"), "Did not see description of artifacts in response to get");

        // Test ambiguous command
        String response3 = server.handleCommand("Simon: Get the axe and coin").toLowerCase();
        assertTrue(response3.contains("more than one thing you can 'get' here"), "The command should not be executed");

        String response4 = server.handleCommand("Simon: Get a coin").toLowerCase();
        assertTrue(response4.contains("picked up a coin"), "Did not see description of artifacts in response to get");

        String response5 = server.handleCommand("Simon: Get an axe").toLowerCase();
        assertTrue(response5.contains("picked up a axe"), "Did not see description of artifacts in response to get");

        String response6 = server.handleCommand("Simon: inv").toLowerCase();
        assertTrue(response6.contains("magic potion"), "Did not see description of artifacts in response to inv");
        assertTrue(response6.contains("sharp axe"), "Did not see description of artifacts in response to inv");
        assertTrue(response6.contains("silver coin"), "Did not see description of artifacts in response to inv");

        String response7 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response7.contains("a log cabin"), "Did not see description of room in response to look");
        assertFalse(response7.contains("magic potion"), "You should have already picked up this item");
        assertFalse(response7.contains("sharp axe"), "You should have already picked up this item");
        assertFalse(response7.contains("silver coin"), "You should have already picked up this item");
        assertTrue(response7.contains("forest"), "Did not see description of path in response to look");

        String response8 = server.handleCommand("Simon: drop the potion").toLowerCase();
        assertTrue(response8.contains("dropped a potion"), "Did not see description of artefacts in response to drop");

        // Test dropped item in current location
        String response9 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response9.contains("a log cabin"), "Did not see description of room in response to look");
        assertTrue(response9.contains("magic potion"), "Did not see description of room in response to look");
        assertFalse(response9.contains("sharp axe"), "You should have already picked up this item");
        assertFalse(response9.contains("silver coin"), "You should have already picked up this item");
        assertTrue(response9.contains("forest"), "Did not see description of path in response to look");

        String response10 = server.handleCommand("Simon: goto forest on foot").toLowerCase();
        assertTrue(response10.contains("went to forest"), "Did not see description of action in response to goto");

        String response11 = server.handleCommand("Simon: look around").toLowerCase();
        assertTrue(response11.contains("deep dark forest"), "Did not see description of room in response to look");
        assertTrue(response11.contains("rusty old key"), "Did not see description of room in response to look");
        assertTrue(response11.contains("tall pine tree"), "Did not see description of room in response to look");
        assertTrue(response11.contains("cabin"), "Did not see description of path in response to look");
        assertTrue(response11.contains("riverbank"), "Did not see description of path in response to look");

        String response12 = server.handleCommand("Simon: inv").toLowerCase();
        assertFalse(response12.contains("magic potion"), "Did not see description of artifacts in response to inv");
        assertTrue(response12.contains("sharp axe"), "Did not see description of artifacts in response to inv");
        assertTrue(response12.contains("silver coin"), "Did not see description of artifacts in response to inv");

        // Test valid action containing a trigger and AT LEAST ONE subject
        String response13 = server.handleCommand("Simon:chop").toLowerCase();
        assertTrue(response13.contains("unable to match"), "This command is ambiguous, it should not be executed");

        String response14 = server.handleCommand("Simon: chop the tree").toLowerCase();
        assertTrue(response14.contains("cut down the tree"), "Did not see description of action in response to chop");

        // Check if tree has been consumed from current location, and item produced to current location
        String response15 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response15.contains("deep dark forest"), "Did not see description of room in response to look");
        assertTrue(response15.contains("rusty old key"), "Did not see description of room in response to look");
        assertFalse(response15.contains("tall pine tree"), "This item should not exist anymore");
        assertTrue(response15.contains("heavy wooden log"), "Did not see description of room in response to look");
        assertTrue(response15.contains("cabin"), "Did not see description of path in response to look");
        assertTrue(response15.contains("riverbank"), "Did not see description of path in response to look");

        String response16 = server.handleCommand("Simon: get the rusty key").toLowerCase();
        assertTrue(response16.contains("picked up a key"), "Did not see description of artifacts in response to get");

        String response17 = server.handleCommand("Simon: get the wooden log").toLowerCase();
        assertTrue(response17.contains("picked up a log"), "Did not see description of artifacts in response to get");

        String response18 = server.handleCommand("Simon: goto riverbank").toLowerCase();
        assertTrue(response18.contains("went to riverbank"), "Did not see description of action in response to goto");

        String response19 = server.handleCommand("Simon: get horn").toLowerCase();
        assertTrue(response19.contains("picked up a horn"), "Did not see description of artifacts in response to goto");

        String response20 = server.handleCommand("Simon: bridge with log").toLowerCase();
        assertTrue(response20.contains("reach the other side"), "Did not see description of path in response to bridge");

        // Reach clearing
        String response21 = server.handleCommand("Simon: goto clearing").toLowerCase();
        assertTrue(response21.contains("went to clearing"), "Did not see description of action in response to goto");

        String response22 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response22.contains("clearing in the woods"), "Did not see description of room in response to look");
        assertTrue(response22.contains("the soil has been recently disturbed"), "Did not see description of room in response to look");

        server.handleCommand("Simon: goto riverbank");
        server.handleCommand("Simon: goto forest");
        server.handleCommand("Simon: goto cabin");

        // Unlock the trapdoor with key
        String response23 = server.handleCommand("Simon: unlock with key").toLowerCase();
        assertTrue(response23.contains("see steps leading down into a cellar"), "Did not see description of path in response to unlock");

        String response24 = server.handleCommand("Simon: goto cellar").toLowerCase();
        assertTrue(response24.contains("went to cellar"), "Did not see description of action in response to goto");

        String response25 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response25.contains("dusty cellar"), "Did not see description of room in response to look");
        assertTrue(response25.contains("angry looking elf"), "Did not see description of room in response to look");
        assertTrue(response25.contains("cabin"), "Did not see description of path in response to look");

        String response26 = server.handleCommand("Simon: hit elf").toLowerCase();
        assertTrue(response26.contains("lose some health"), "Did not see description of action in response to hit");

        server.handleCommand("Simon: goto cabin");
        server.handleCommand("Simon: get potion");

        String response27 = server.handleCommand("Simon: drink the potion").toLowerCase();
        assertTrue(response27.contains("your health improves"), "Did not see description of action in response to hit");

        server.handleCommand("Simon: goto cellar");

        String response28 = server.handleCommand("Simon: pay elf").toLowerCase();
        assertTrue(response28.contains("produces a shovel"), "Did not see description of action in response to pay");

        server.handleCommand("Simon: get shovel");
        String response29 = server.handleCommand("Simon: inv").toLowerCase();
        assertFalse(response29.contains("magic potion"), "This item has been used");
        assertTrue(response29.contains("sharp axe"), "Did not see description of artifacts in response to inv");
        assertFalse(response29.contains("silver coin"), "This item has been used");
        assertTrue(response29.contains("sturdy shovel"), "Did not see description of artifacts in response to inv");

        server.handleCommand("Simon: goto cabin");
        server.handleCommand("Simon: goto forest");
        server.handleCommand("Simon: goto riverbank");
        server.handleCommand("Simon: goto clearing");

        String response30 = server.handleCommand("Simon: dig the ground with shovel").toLowerCase();
        assertTrue(response30.contains("unearth a pot of gold"), "Did not see description of action in response to dig");
        server.handleCommand("Simon: get gold");
        server.handleCommand("Simon: goto riverbank");

        String response31 = server.handleCommand("Simon: blow the horn").toLowerCase();
        assertTrue(response31.contains("a lumberjack appears"), "Did not see description of action in response to dig");

        String response32 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response32.contains("burly wood cutter"), "Did not see description of artifacts in response to look");

        server.handleCommand("Simon: goto forest");
        server.handleCommand("Simon: goto cabin");
        server.handleCommand("Simon: goto cellar");
        server.handleCommand("Simon: attack the elf");
        server.handleCommand("Simon: hit elf");

        String response34 = server.handleCommand("Simon: fight with the elf").toLowerCase();
        assertTrue(response34.contains("you died"), "You should have died");

        String response35 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response35.contains("a log cabin"), "Did not see description of room in response to look");

        server.handleCommand("Simon: goto forest");
        server.handleCommand("Simon: goto riverbank");
        String response36 = server.handleCommand("Simon: look").toLowerCase();
        assertTrue(response36.contains("lumberjack"), "Did not see description of action in response to look");

    }

    @Test
    void testMoreThanOneValidAction(){
        String response1 = server.handleCommand("Simon: look and inv").toLowerCase();
        assertTrue(response1.contains("more than one possible actions"), "Did not see error message in response to multiple actions");

        server.handleCommand("Simon: goto forest");
        String response2 = server.handleCommand("Simon: look and chop down tree").toLowerCase();
        assertTrue(response2.contains("more than one possible actions"), "Did not see error message in response to multiple actions");
    }

    @Test
    void testMoreThanOneValidAction2(){
        String response1 = server.handleCommand("Simon: look and inv").toLowerCase();
        assertTrue(response1.contains("more than one possible actions"), "Did not see error message in response to multiple actions");

        // Compared with testMoreThanOneValidAction, change the expression and position of chop down tree command
        server.handleCommand("Simon: goto forest");
        String response2 = server.handleCommand("Simon: cut the tree and look").toLowerCase();
        assertTrue(response2.contains("more than one possible actions"), "Did not see error message in response to multiple actions");

        server.handleCommand("Simon: goto riverbank");
        server.handleCommand("Simon: get horn");
        server.handleCommand("Simon: goto forest");
        server.handleCommand("Simon: get key");
        server.handleCommand("Simon: goto cabin");
        server.handleCommand("Simon: open with key");
        server.handleCommand("Simon: goto cellar");

        String response3 = server.handleCommand("Simon: hit the elf").toLowerCase();
        assertTrue(response3.contains("lose some health"), "Did not see description of action in response to hit");

        // Test more than one valid command
        String response4 = server.handleCommand("Simon: hit the elf and blow the horn").toLowerCase();
        assertTrue(response4.contains("more than one possible actions"), "Did not see description of action in response to hit");
    }

    @Test
    void testIncompleteCommand(){
        String response = server.handleCommand("Simon:goto forest").toLowerCase();
        assertTrue(response.contains("went to forest"), "Did not see description of action in response to goto");

        String response2 = server.handleCommand("Simon: cut the tree and look").toLowerCase();
        assertTrue(response2.contains("more than one possible actions"), "Did not see error message in response to multiple actions");

        String response3 = server.handleCommand("Simon: goto").toLowerCase();
        assertTrue(response3.contains("command is incomplete"), "The command should not be executed because the command is incomplete");
    }

}

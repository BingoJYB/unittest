package com.bingo.services;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import com.bingo.model.Movie;
import com.bingo.model.Race;
import com.bingo.model.Ring;
import com.bingo.model.TolkienCharacter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class DataServiceTest {

    DataService dataService;

    @BeforeEach
    void setup() {
        dataService = new DataService();
    }

    @Test
    void ensureThatInitializationOfTolkeinCharactorsWorks() {
        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, Race.HOBBIT);

        assertEquals(33, frodo.age, "check that age is 33");
        assertEquals("Frodo", frodo.getName(), "check that name is 'Frodo'");
        assertNotEquals("Frodon", frodo.getName(), "check that name is not 'Frodon'");
    }

    @Test
    void ensureThatEqualsWorksForCharaters() {
        Object jake = new TolkienCharacter("Jake", 43, Race.HOBBIT);
        Object sameJake = jake;
        Object jakeClone = new TolkienCharacter("Jake", 12, Race.HOBBIT);

        assertEquals(sameJake, jake, "jake is equal to sameJake");
        assertNotEquals(jakeClone, jake, "jake is not equal to jakeClone");
    }

    @Test
    void checkInheritance() {
        TolkienCharacter tolkienCharacter = dataService.getFellowship().get(0);

        assertNotEquals(Movie.class, tolkienCharacter.getClass(),
                "check that tolkienCharacter.getClass is not a movie class");
    }

    @Test
    void ensureFellowShipCharacterAccessByNameReturnsNullForUnknownCharacter() {

        assertNull(dataService.getFellowshipCharacter("Lars"),
                "check that dataService.getFellowshipCharacter returns null");
    }

    @Test
    void ensureFellowShipCharacterAccessByNameWorksGivenCorrectNameIsGiven() {

        assertNotNull(dataService.getFellowshipCharacter("Frodo"),
                "check that dataService.getFellowshipCharacter returns a fellowship");
    }

    @Test
    void ensureThatFrodoAndGandalfArePartOfTheFellowsip() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        TolkienCharacter frodo = new TolkienCharacter("Frodo", 33, Race.HOBBIT);
        TolkienCharacter gandalf = new TolkienCharacter("Gandalf", 2020, Race.MAIA);

        assertTrue(fellowship.contains(frodo), "check that Frodo is part of the fellowship");
        assertTrue(fellowship.contains(gandalf), "check that Gandalf are part of the fellowship");
    }

    @Test
    void ensureThatOneRingBearerIsPartOfTheFellowship() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();
        Map<Ring, TolkienCharacter> ringBearers = dataService.getRingBearers();

        assertTrue(ringBearers.values().stream().anyMatch(ringBearer -> fellowship.contains(ringBearer)),
                "test that at least one ring bearer is part of the fellowship");
    }

    @Test
    @Tag("slow")
    @RepeatedTest(1000)
    @DisplayName("Minimal stress testing: run this test 1000 times to ")
    void ensureThatWeCanRetrieveFellowshipMultipleTimes() {
        dataService = new DataService();
        assertNotNull(dataService.getFellowship());
    }

    @Test
    void ensureOrdering() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        // ensure that the order of the fellowship is:
        // frodo, sam, merry,pippin, gandalf,legolas,gimli,aragorn,boromir
        assertEquals(dataService.getFellowshipCharacter("Frodo"), fellowship.get(0));
        assertEquals(dataService.getFellowshipCharacter("Sam"), fellowship.get(1));
        assertEquals(dataService.getFellowshipCharacter("Merry"), fellowship.get(2));
        assertEquals(dataService.getFellowshipCharacter("Pippin"), fellowship.get(3));
        assertEquals(dataService.getFellowshipCharacter("Gandalf"), fellowship.get(4));
        assertEquals(dataService.getFellowshipCharacter("Legolas"), fellowship.get(5));
        assertEquals(dataService.getFellowshipCharacter("Gimli"), fellowship.get(6));
        assertEquals(dataService.getFellowshipCharacter("Aragorn"), fellowship.get(7));
        assertEquals(dataService.getFellowshipCharacter("Boromir"), fellowship.get(8));
    }

    @Test
    void ensureAge() {
        List<TolkienCharacter> fellowship = dataService.getFellowship();

        assertTrue(fellowship.stream()
                .filter(fellow -> fellow.getRace().equals(Race.HOBBIT) || fellow.getRace().equals(Race.MAN))
                .allMatch(fellow -> fellow.age < 100),
                "test ensure that all hobbits and men are younger than 100 years");

        assertTrue(
                fellowship
                        .stream().filter(fellow -> fellow.getRace().equals(Race.ELF)
                                || fellow.getRace().equals(Race.DWARF) || fellow.getRace().equals(Race.MAIA))
                        .allMatch(fellow -> fellow.age > 100),
                "ensure that the elfs, dwars the maia are all older than 100 years");
    }

    @Test
    void ensureThatFellowsStayASmallGroup() {

        List<TolkienCharacter> fellowship = dataService.getFellowship();

        assertThrows(IndexOutOfBoundsException.class, () -> fellowship.get(20),
                "a test to get the 20 element from the fellowship throws an IndexOutOfBoundsException");
    }

    @Test
    void timeoutNotExceededWithResult() {
        assertTimeout(ofSeconds(3), () -> dataService.update());
    }

}

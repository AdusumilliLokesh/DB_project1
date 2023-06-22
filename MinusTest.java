import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class MinusTest {

    @Test
    public void testMinus() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        // Create the movie table
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        // Create the cinema table
        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        // Create the films
        var film0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345};
        var film1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345};
        var film2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125};
        var film3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355};
        var film4 = new Comparable [] { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };

        // Add films to movie and cinema tables
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);
        cinema.insert(film2);
        cinema.insert(film3);
        cinema.insert(film4);

        // Create the expected table
        var expectedTable = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");
        expectedTable.insert(film0);
        expectedTable.insert(film1);

        // Perform the movie table minus cinema table operation
        var result = movie.minus(cinema);

        // Get access to the "tuples" field using reflection
        Field tuplesField = Table.class.getDeclaredField("tuples");
        tuplesField.setAccessible(true);

        // Get the tuples list from the result and expected tables
        var resultTuples = (ArrayList<Comparable[]>) tuplesField.get(result);
        var expectedTuples = (ArrayList<Comparable[]>) tuplesField.get(expectedTable);

        // Verify the result is the same as expected
        assertEquals(expectedTuples, resultTuples);
    }
}

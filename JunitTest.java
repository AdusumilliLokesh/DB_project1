import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;


public class JunitTest {
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
        var film4 = new Comparable [] {"Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890};

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
        assertEquals(expectedTuples.size(), resultTuples.size());
        for (int i = 0; i < expectedTuples.size(); i++) {
            assertArrayEquals(expectedTuples.get(i), resultTuples.get(i));
        }
    }
    
    @Test
    public void testUnion() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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
        movie.insert(film4);

        // Create the expected table
        var expectedTable = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");
        expectedTable.insert(film0);
        expectedTable.insert(film1);
        expectedTable.insert(film2);
        expectedTable.insert(film3);
        expectedTable.insert(film4);
        
        // Perform the movie table union cinema table operation
        var result = movie.union(cinema);

        // Get access to the "tuples" field using reflection
        Field tuplesField = Table.class.getDeclaredField("tuples");
        tuplesField.setAccessible(true);

        // Get the tuples list from the result and expected tables
        var resultTuples = (ArrayList<Comparable[]>) tuplesField.get(result);
        var expectedTuples = (ArrayList<Comparable[]>) tuplesField.get(expectedTable);

        // Verify the result is the same as expected
        assertEquals(expectedTuples.size(), resultTuples.size());
        for (int i = 0; i < expectedTuples.size(); i++) {
            assertArrayEquals(expectedTuples.get(i), resultTuples.get(i));
        }
    }
    
    @Test
    public void testSelect() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
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

        // Add films to movie and cinema tables
        movie.insert(film0);
        movie.insert(film1);
        movie.insert(film2);
        movie.insert(film3);

        // Create the expected table with only "title" equals to "Star_Wars" and "year" equals to "1977"
        var expectedTable = new Table("expected movie select", "title year genre", "String Integer String", "title year");
        // Insert films into expectedTable with only "title" and "year" column
        expectedTable.insert(film0);

        // Create the expected table with only "year" less than "1980"
        var expectedTable2 = new Table("expected movie select2", "title year genre", "String Integer String", "title year");
        // Insert films into expectedTable2 with year less than 1980
        expectedTable2.insert(film0);
        expectedTable2.insert(film3);
        
        // Perform the movie table project operation
        var result = movie.select (t -> t[movie.col("title")].equals ("Star_Wars") &&
                t[movie.col("year")].equals (1977));

        var result2 = movie.select (t -> (Integer) t[movie.col("year")] < 1980);
        
        // Get access to the "tuples" field using reflection
        Field tuplesField = Table.class.getDeclaredField("tuples");
        tuplesField.setAccessible(true);

        // Get the tuples list from the result and expected tables
        var resultTuples = (ArrayList<Comparable[]>) tuplesField.get(result);
        var expectedTuples = (ArrayList<Comparable[]>) tuplesField.get(expectedTable);
        var resultTuples2 = (ArrayList<Comparable[]>) tuplesField.get(result2);
        var expectedTuples2 = (ArrayList<Comparable[]>) tuplesField.get(expectedTable2);
        
        // Verify the result is the same as expected
        assertEquals(expectedTuples.size(), resultTuples.size());
        for (int i = 0; i < expectedTuples.size(); i++) {
            assertArrayEquals(expectedTuples.get(i), resultTuples.get(i));
        }
        assertEquals(expectedTuples2.size(), resultTuples2.size());
        for (int i = 0; i < expectedTuples2.size(); i++) {
            assertArrayEquals(expectedTuples2.get(i), resultTuples2.get(i));
        }
    }
    
    @Test
    public void testJoin() throws NoSuchFieldException, IllegalAccessException {
        // Create the movie table
        var movie = new Table("movie", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");

        // Create the cinema table
        var cinema = new Table("cinema", "title year length genre studioName producerNo",
                "String Integer Integer String String Integer", "title year");
        
        var studio = new Table ("studio", "name address presNo",
                "String String Integer", "name");
        
        // Create the employees
        var film0 = new Comparable [] { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        var film1 = new Comparable [] { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        var film2 = new Comparable [] { "Rocky", 1985, 200, "action", "Universal", 12125 };
        var film3 = new Comparable [] { "Rambo", 1978, 100, "action", "Universal", 32355 };
        var film4 = new Comparable [] { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };

        // Create the projects
        var studio0 = new Comparable [] { "Fox", "Los_Angeles", 7777 };
        var studio1 = new Comparable [] { "Universal", "Universal_City", 8888 };
        var studio2 = new Comparable [] { "DreamWorks", "Universal_City", 9999 };

        // Add employees to employee table
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);

        // Add projects to project table
        cinema.insert (film2);
        cinema.insert (film3);
        cinema.insert (film4);

        studio.insert (studio0);
        studio.insert (studio1);
        studio.insert (studio2);
        
        // Create the expected table with joined columns
        var expectedTable = new Table("movie", "title year length genre studioName producerNo name address presNo",
                "String Integer Integer String String Integer String Integer", "name title year");

        // Insert data into expectedTable with joined columns
        var expectedRow0 = new Comparable[]{"Star_Wars", 1977, 124, "sciFi", "Fox", 12345, "Fox", "Los_Angeles", 7777};
        var expectedRow1 = new Comparable[]{"Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345, "Fox","Los_Angeles", 7777};
        var expectedRow2 = new Comparable[]{"Rocky", 1985, 200, "action", "Universal", 12125, "Universal","Universal_City", 8888};
        var expectedRow3 = new Comparable[]{"Rambo", 1978, 100, "action", "Universal", 32355, "Universal","Universal_City", 8888};

        expectedTable.insert(expectedRow0);
        expectedTable.insert(expectedRow1);
        expectedTable.insert(expectedRow2);
        expectedTable.insert(expectedRow3);

        //Create the expected2 Table
        var expectedTable2 = new Table("expected2 Movie", "title year length genre studioName producerNo", 
        		"String Integer Integer String String Integer","title year");
        
        var expected2Row0 = new Comparable [] { "Rocky", 1985, 200, "action", "Universal", 12125 };
        var expected2Row2 = new Comparable [] { "Rambo", 1978, 100, "action", "Universal", 32355 };
        
        expectedTable2.insert(expected2Row0);
        expectedTable2.insert(expected2Row2);
        
        var expectedTable3 = new Table("expected3 Movie", "title year length genre studioName producerNo title2 year2 length2 genre2 studioName2 producerNo2", 
        		"String Integer Integer String String Integer String Integer Integer String String Integer","title year");
        
        var expected3Row0 = new Comparable [] {"Rocky", 1985, 200, "action", "Universal", 12125, "Rocky", 1985, 200, "action", "Universal", 12125};
        var expected3Row2 = new Comparable [] {"Rambo", 1978, 100, "action", "Universal", 32355, "Rambo", 1978, 100, "action", "Universal", 32355};
        
        expectedTable3.insert(expected3Row0);
        expectedTable3.insert(expected3Row2);
        
        expectedTable3.print();
        // Perform the join operation
        var result = movie.join("studioName", "name", studio);
        var result2 = movie.join (cinema);
        var result3 = movie.join("year == year", cinema);

        result3.print();
        // Get access to the "tuples" field using reflection
        Field tuplesField = Table.class.getDeclaredField("tuples");
        tuplesField.setAccessible(true);

        // Get the tuples list from the result and expected tables
        var resultTuples = (ArrayList<Comparable[]>) tuplesField.get(result);
        var expectedTuples = (ArrayList<Comparable[]>) tuplesField.get(expectedTable);
        var resultTuples2 = (ArrayList<Comparable[]>) tuplesField.get(result2);
        var expectedTuples2 = (ArrayList<Comparable[]>) tuplesField.get(expectedTable2);
        var resultTuples3 = (ArrayList<Comparable[]>) tuplesField.get(result3);
        var expectedTuples3 = (ArrayList<Comparable[]>) tuplesField.get(expectedTable3);
        
        // Verify the result is the same as expected
        assertEquals(expectedTuples.size(), resultTuples.size());
        for (int i = 0; i < expectedTuples.size(); i++) {
        	assertArrayEquals(expectedTuples.get(i), resultTuples.get(i));
        }
        // Verify the result2 is the same as expected
        assertEquals(expectedTuples2.size(), resultTuples2.size());
        for (int i = 0; i < expectedTuples2.size(); i++) {
        	assertArrayEquals(expectedTuples2.get(i), resultTuples2.get(i));
        }
        // Verify the result2 is the same as expected
        assertEquals(expectedTuples3.size(), resultTuples3.size());
        for (int i = 0; i < expectedTuples3.size(); i++) {
        	assertArrayEquals(expectedTuples3.get(i), resultTuples3.get(i));
        }
    }
    
    @Test
    public void testProject() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        // Create the employee table
        var employee = new Table("employee", "id name department",
                "Integer String String", "id");

        // Create the employees
        var employee0 = new Comparable[]{1, "John", "HR"};
        var employee1 = new Comparable[]{2, "Jane", "Finance"};
        var employee2 = new Comparable[]{3, "Mike", "Marketing"};

        // Add employees to the employee table
        employee.insert(employee0);
        employee.insert(employee1);
        employee.insert(employee2);

        // Create the expected table with only "name" column
        var expectedTable = new Table("employee", "name", "String", "id");

        // Insert names into expectedTable
        var expectedRow0 = new Comparable[]{"John"};
        var expectedRow1 = new Comparable[]{"Jane"};
        var expectedRow2 = new Comparable[]{"Mike"};

        expectedTable.insert(expectedRow0);
        expectedTable.insert(expectedRow1);
        expectedTable.insert(expectedRow2);

        // Perform the employee table project operation
        var result = employee.project("name");

        // Get access to the "tuples" field using reflection
        Field tuplesField = Table.class.getDeclaredField("tuples");
        tuplesField.setAccessible(true);

        // Get the tuples list from the result and expected tables
        var resultTuples = (ArrayList<Comparable[]>) tuplesField.get(result);
        var expectedTuples = (ArrayList<Comparable[]>) tuplesField.get(expectedTable);

        // Verify the result is the same as expected
        assertEquals(expectedTuples.size(), resultTuples.size());
        for (int i = 0; i < expectedTuples.size(); i++) {
            assertArrayEquals(expectedTuples.get(i), resultTuples.get(i));
        }
    }
}


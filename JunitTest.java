package implement;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import static java.lang.System.out;
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
        assertEquals(expectedTuples, resultTuples);
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
        expectedTable.print();

        // Perform the employee table project operation
        var result = employee.project("name");
        result.print();

        // Get access to the "tuples" field using reflection
        Field tuplesField = Table.class.getDeclaredField("tuples");
        tuplesField.setAccessible(true);

        // Get the tuples list from the result and expected tables
        var resultTuples = (ArrayList<Comparable[]>) tuplesField.get(result);
        var expectedTuples = (ArrayList<Comparable[]>) tuplesField.get(expectedTable);

        // Verify the result is the same as expected
        assertEquals(expectedTuples, resultTuples);
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
        assertEquals(expectedTuples, resultTuples);
        assertEquals(expectedTuples2, resultTuples2);
    }
    
    @Test
    public void testJoin() throws NoSuchFieldException, IllegalAccessException {
        // Create the employee table
        var employee = new Table("employee", "id name department",
                "Integer String String", "id");

        // Create the project table
        var project = new Table("project", "id name duration",
                "Integer String Integer", "id");

        // Create the department table
        var department = new Table("department", "name manager",
                "String String", "name");

        // Create the employees
        var employee0 = new Comparable[]{1, "John", "HR"};
        var employee1 = new Comparable[]{2, "Jane", "Finance"};
        var employee2 = new Comparable[]{3, "Mike", "Marketing"};

        // Create the projects
        var project0 = new Comparable[]{1, "ProjectA", 10};
        var project1 = new Comparable[]{2, "ProjectB", 15};
        var project2 = new Comparable[]{3, "ProjectC", 20};

        // Add employees to employee table
        employee.insert(employee0);
        employee.insert(employee1);
        employee.insert(employee2);

        // Add projects to project table
        project.insert(project0);
        project.insert(project1);
        project.insert(project2);

        // Create the expected table with joined columns
        var expectedTable = new Table("expected employee JOIN project", "id name department id2 name duration",
                "Integer String String Integer String Integer", "id");

        // Insert data into expectedTable with joined columns
        var expectedRow0 = new Comparable[]{1, "John", "HR", 1, "ProjectA", 10};
        var expectedRow1 = new Comparable[]{2, "Jane", "Finance", 2, "ProjectB", 15};
        var expectedRow2 = new Comparable[]{3, "Mike", "Marketing", 3, "ProjectC", 20};

        expectedTable.insert(expectedRow0);
        expectedTable.insert(expectedRow1);
        expectedTable.insert(expectedRow2);
        expectedTable.print();

        // Perform the join operation
        var result = employee.join("id", "id", project);

        result.print();

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


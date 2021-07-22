import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Scrabble {
    public static void main(String[] args) {  
	// specify a dictionary to scan
        File dictionary = new File(args[0]);

    // specify available tiles to match
        String tiles = alphabetize(args[1]);

	// search for words and their anagrams
        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner s = new Scanner(dictionary)) {
            while (s.hasNext()) {
                String word = s.next();
                groups.computeIfAbsent(alphabetize(word), (x) -> new TreeSet<>()).add(word);
            }
        } catch (FileNotFoundException ex) {}

        if (groups.containsKey(tiles))
            System.out.println(groups.get(tiles));


        // Print word of the day from database:
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:scrabble.db");
            // connection.prepareStatement("CREATE TABLE words (id INT PRIMARY KEY, word VARCHAR)").execute();
            // connection.prepareStatement("INSERT INTO words (word) VALUES ('act')").execute();
            ResultSet resultSet = connection.prepareStatement("select * from words").executeQuery();
            while (resultSet.next()) {
                System.out.println("Word of the day: " + resultSet.getString("word"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String alphabetize(String s) {
        char[] letters = s.toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}
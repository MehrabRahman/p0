import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class ScrabbleTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Running ScrabbleTest...");
        int passed = 0, failed = 0;
        Class<?> unitTestsClass = Class.forName("UnitTests");
        for (Method m : unitTestsClass.getMethods()) {
            if (m.isAnnotationPresent(UnitTest.class)) {
                try {
                    m.invoke(unitTestsClass.newInstance());
                    passed++;
                    System.out.println("Test " + m.getName() + " passed!");
                } catch (Throwable ex) {
                    failed++;
                    System.err.println("Test " + m.getName() + " failed: " + ex.getCause());
                }
            }
        }

        System.out.println("Passed: " + passed + ", Failed: " + failed);
    }
}

/**
 * UnitTest is a marker interface used as an annotation by ScrabbleTest
 * Can be parsed and used with Method.isAnnotationPresent()
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface UnitTest {}

/**
 * UnitTests is an example class with methods annotated by @UnitTest, meant 
 * to be scanned and invoked by ScrabbleTest
 */
class UnitTests {
    @UnitTest
    public void testConstructor() {
        new Scrabble();
        System.out.println("testConstructor passed");
    }

    @UnitTest
    public void testAlphabetize() {
        String expected = "act";
        String actual = Scrabble.alphabetize("cat");
        if (expected.equals(actual))
            System.out.println("testAlphabetize passed");
    }

    @UnitTest
    public void blah() {
        
    }
}

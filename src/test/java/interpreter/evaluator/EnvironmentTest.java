package interpreter.evaluator;


import interperter.evaluator.Environment;
import interperter.evaluator.value.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnvironmentTest {

    private record MockValue(String data) implements Value {
        public String inspect() {
            return data;
        }
    }


    private Environment env;
    private Value testValue1;
    private Value testValue2;

    @BeforeEach
    void setUp() {
        env = new Environment();
        testValue1 = new MockValue("test1");
        testValue2 = new MockValue("test2");
    }

    @Test
    void testGetValueFromEmptyEnvironment() {
        assertNull(env.getValue("nonExistentVariable"));
    }

    @Test
    void testPutAndGetValue() {
        env.putValue("x", testValue1);
        Value retrieved = env.getValue("x");
        assertEquals(testValue1, retrieved);
    }

    @Test
    void testOverwriteValue() {
        env.putValue("x", testValue1);
        env.putValue("x", testValue2);
        Value retrieved = env.getValue("x");
        assertEquals(testValue2, retrieved);
    }

    @Test
    void testNestedEnvironmentFindInOuter() {
        env.putValue("outerVar", testValue1);

        Environment nestedEnv = new Environment(env);

        Value retrieved = nestedEnv.getValue("outerVar");
        assertEquals(testValue1, retrieved);
    }

    @Test
    void testNestedEnvironmentShadowing() {
        env.putValue("x", testValue1);

        Environment nestedEnv = new Environment(env);
        nestedEnv.putValue("x", testValue2);

        Value retrieved = nestedEnv.getValue("x");
        assertEquals(testValue2, retrieved);

        retrieved = env.getValue("x");
        assertEquals(testValue1, retrieved);
    }

    @Test
    void testNullValueStorage() {
        env.putValue("nullVar", null);
        assertNull(env.getValue("nullVar"));
    }

    @Test
    void testReturnValueOfPutValue() {
        Value returned = env.putValue("x", testValue1);
        assertEquals(testValue1, returned);
    }
}
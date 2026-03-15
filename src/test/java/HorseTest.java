import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void isNameNullThenException() {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1d));
        Assertions.assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @CsvFileSource(resources ="horse.csv")
    void isNullOrEmptyNameThenException(String name, double speed, double distance) {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        System.out.println(exception.getMessage() + " horse name = |" + name + "|");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "horse.csv", numLinesToSkip = 1)
    void isEmptyNameThenExceptionContainsMessage(String name, double speed, double distance) {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        Assertions.assertTrue("Name cannot be blank.".equals(exception.getMessage()));
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, -1.0, 1.0"})
    void isNegativeSpeedThenException(String name, double speed, double distance) {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, -2.1, 1.0"})
    void isNegativeSpeedThenExceptionAndContainsMessage(String name, double speed, double distance) {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        Assertions.assertTrue("Speed cannot be negative.".equals(exception.getMessage()));
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, 1.0, -1.0"})
    void isNegativeDistanceThenException(String name, double speed, double distance) {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, 2.1, -1.0"})
    void isNegativeDistanceThenExceptionAndContainsMessage(String name, double speed, double distance) {
        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, speed, distance));
        Assertions.assertTrue("Distance cannot be negative.".equals(exception.getMessage()));
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, 1.0, 1.0"})
    void isMethodGetNameReturnNameSendToConstructor(String name, double speed, double distance) {
        Horse horse =  new Horse(name, speed, distance);
        Assertions.assertEquals(name, horse.getName());
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, 1.0, 1.0"})
    void isMethodGetSpeedReturnNameSendToConstructor(String name, double speed, double distance) {
        Horse horse =  new Horse(name, speed, distance);
        Assertions.assertTrue(Double.compare(speed, horse.getSpeed()) == 0);
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, 1.0, 1.0"})
    void isMethodReturnThirdValueSendToConstructor(String name, double speed, double distance) {
        Horse horse =  new Horse(name, speed, distance);
        Assertions.assertTrue(Double.compare(distance, horse.getDistance()) == 0);
    }

    @ParameterizedTest
    @CsvSource(value = {"Kim, 1.0"})
    void isMethodReturn0IfSendOnlyTwoParameters(String name, double speed) {
        Horse horse =  new Horse(name, speed);
        Assertions.assertTrue(0 == horse.getDistance());
    }

    @ParameterizedTest
    @CsvSource(value = {"Samira, 1.0, 1.0"})
    void checkExecuteGetRandomDouble(String name, double speed, double distance) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse(name, speed, distance);
            horse.move();
            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }


    @ParameterizedTest
    @CsvSource(value = {"Samira, 1.0, 1.0, 2.0",
            "Samira, 1.0, 1.0, 4.0"})
    void checkDistanceFormulaMethodMove(String name, double speed, double distance, double expected) {
        try (MockedStatic<Horse> horse = Mockito.mockStatic(Horse.class)) {
            horse.when(() -> Horse.getRandomDouble(0.2, 0.9))
                    .thenReturn(expected);
            Horse mockHorse = new Horse(name, speed, distance);
            mockHorse.move();
            Assertions.assertEquals(distance + speed * expected, mockHorse.getDistance());
        }
    }

}
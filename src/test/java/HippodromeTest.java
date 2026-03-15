import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void isHorsesNullThenThrowsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void isHorsesEmptyThenThrowsException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {new Hippodrome(new ArrayList());
        });
        Assertions.assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesReturnRightOrderedList() {
        int lenght = 30;
        List<Horse> horses = new ArrayList<>(lenght);
        for (int i = 0; i < lenght; i++) {
            horses.add(new Horse("Horse" + i, i * 1.0, i * 1.0));
        }
        Hippodrome h = new Hippodrome(horses);
        for (int i = 0; i < lenght; i++) {
            assertEquals(horses.get(i), h.getHorses().get(i));
        }
    }

    @Test
    void methodMoveExecuteMoveAllHorses() {
        int lenght = 50;
        List<Horse> horses = new ArrayList<>(lenght);
        for (int i = 0; i < lenght; i++) {
            horses.add(mock(Horse.class));
        }
        Hippodrome h = new Hippodrome(horses);
        h.move();
        for (int i = 0; i < lenght; i++) {
            Mockito.verify(horses.get(i), times(1)).move();
        }
    }

    @Test
    void methodGetWinnerReturnHorseWithMaxDistance() {
        Horse horse = new Horse("Sokol", 1.0, 4.0);
        List<Horse> horses = List.of(new Horse("Linda", 1.0, 2.0),
                horse,
                new Horse("Betty", 1.0, 3.0));
        Hippodrome h = new Hippodrome(horses);
        Assertions.assertEquals(horse, h.getWinner());
    }

}
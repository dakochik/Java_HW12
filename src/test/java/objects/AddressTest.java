package objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AddressTest {
    @Test
    public void creatingClientObject_EverythingShouldBeWell(){
        Assertions.assertDoesNotThrow(() -> {
            Address address = new Address("R", "M", "T", 1);
        });
    }

    @Test
    public void creatingClientObject_NegativeStreetNumber_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Address address = new Address("R", "M", "T", -3);
        });
    }

    @Test
    public void creatingClientObject_ZeroStreetNumbers_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> {
            Address address = new Address("R", "M", "T", 0);
        });
    }
}

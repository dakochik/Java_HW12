package objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class ClientTest {
    @Test
    public void creatingClientObjectWithoutEmail_EverythingShouldBeWell(){
        Assertions.assertDoesNotThrow( ()->{ Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});});
    }

    @Test
    public void creatingClientObjectWithEmail_EverythingShouldBeWell(){
        Assertions.assertDoesNotThrow( ()->{ Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}}, "EMAIL@EMAIL.COM");});
    }

    @Test
    public void creatingClientObjectWithoutEmail_EmptyPhone_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{ Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>());});
    }

    @Test
    public void creatingClientObjectWithEmail_EmptyEmail_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{ Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}}, "fakeEmail");});
    }

    @Test
    public void creatingClientObjectWithoutEmail_NullPhone_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{ Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                null);});
    }

    @Test
    public void creatingClientObjectWithoutEmail_NullEmail_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, ()->{ Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}}, null);});
    }

    @Test
    public void addPhoneNumb_UniquePhones(){
        Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});

        client.tryAddPhoneNumber(new ArrayList<>(){{add("+7(***)**4-**-**");}});

        Assertions.assertTrue(client.getPhoneNumbers().contains("+7(***)***-**-**") &&
                client.getPhoneNumbers().contains("+7(***)**4-**-**") && client.getPhoneNumbers().size() == 2);
    }

    @Test
    public void addPhoneNumb_NonUniquePhones(){
        Client client = new Client("K", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});

        client.tryAddPhoneNumber(new ArrayList<>(){{add("+7(***)***-**-**");}});

        Assertions.assertTrue(client.getPhoneNumbers().contains("+7(***)***-**-**") &&
                client.getPhoneNumbers().size() == 1);
    }

    @Test
    public void clientsEquals_SameClients(){
        Client client1 = new Client("K", "d", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});

        Client client2 = new Client("k", "D", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});

        Assertions.assertEquals(client2, client1);
    }

    @Test
    public void clientsEquals_DifferentClients(){
        Client client1 = new Client("K", "d", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});

        Client client2 = new Client("k", "A", "A",
                new Address("R", "M", "K", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(***)***-**-**");}});

        Assertions.assertNotEquals(client2, client1);
    }
}

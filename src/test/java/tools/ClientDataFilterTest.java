package tools;

import objects.Address;
import objects.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientDataFilterTest {
    private static ArrayList<Client> clients;
    private static ClientDataFilter filter;

    @BeforeAll
    public static void initClients(){
        filter = new ClientDataFilter();

        clients = new ArrayList<>();
        clients.add(new Client("K", "D", "A",
                new Address("R", "M3", "K1", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(132)***-**-**");}}));

        clients.add(new Client("L", "D", "A",
                new Address("R", "M1", "K2", 12),
                LocalDate.of(1998,1,1),
                new ArrayList<>(){{add("+7(321)***-**-**");}}));

        clients.add(new Client("Q", "F", "A",
                new Address("R", "M2", "K3", 12),
                LocalDate.of(1,1,1),
                new ArrayList<>(){{add("+7(231)***-**-**");}}));

        clients.add(new Client("K", "F", "A",
                new Address("R", "M2", "K4", 12),
                LocalDate.of(1,1,13),
                new ArrayList<>(){{add("+7(312)***-**-**");}}));

        clients.add(new Client("K", "Q", "Z",
                new Address("R", "M1", "K5", 12),
                LocalDate.of(1,1,13),
                new ArrayList<>(){{add("+7(123)***-**-**");}}));
    }

    // - - - - - - - - - - - - - - - - - - Searching

    // - - - - - - - - - - - - - - - - - - Search by name

    @Test
    public void searchByNameBeginning_FindOneElements(){
        ArrayList<Client> array = filter.searchByNameBeginning(clients, "Q");
        Assertions.assertEquals(array.get(0),  clients.get(4));
    }

    @Test
    public void searchByNameBeginning_FindFewElements(){
        ArrayList<Client> array = filter.searchByNameBeginning(clients, "D");
        Assertions.assertTrue((array.get(0).equals(clients.get(0)) && array.get(1).equals(clients.get(1))) ||
                (array.get(0).equals(clients.get(1)) && array.get(1).equals(clients.get(0))));
    }

    @Test
    public void searchByNameBeginning_Empty(){
        ArrayList<Client> array = filter.searchByNameBeginning(clients, "Zzz");
        Assertions.assertEquals(array.size(), 0);
    }

    @Test
    public void searchByNameBeginning_NullArray_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->filter.searchByNameBeginning(null, "Q"));
    }

    // - - - - - - - - - - - - - - - - - - Search by birth date

    @Test
    public void searchByBirthDate_FindOneElements(){
        ArrayList<Client> array = filter.searchByBirthDate(clients, 1998, null ,null);
        Assertions.assertEquals(array.get(0),  clients.get(1));
    }

    @Test
    public void searchByBirthDate_FindFewElements(){
        ArrayList<Client> array = filter.searchByBirthDate(clients, null, null, 13);
        Assertions.assertTrue((array.get(0).equals(clients.get(4)) && array.get(1).equals(clients.get(3))) ||
                (array.get(0).equals(clients.get(3)) && array.get(1).equals(clients.get(4))));
    }

    @Test
    public void searchByBirthDate_Empty(){
        ArrayList<Client> array = filter.searchByBirthDate(clients, null, 22, null);
        Assertions.assertEquals(array.size(), 0);
    }

    @Test
    public void searchByBirthDate_NullArray_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->filter.searchByBirthDate(null, 1,1,1));
    }

    // - - - - - - - - - - - - - - - - - - Search by phone

    @Test
    public void searchByPhone_FindOneElements(){
        ArrayList<Client> array = filter.searchByPhoneNumbBeginning(clients, "+7(123)");
        Assertions.assertEquals(array.get(0),  clients.get(4));
    }

    @Test
    public void searchByPhone_FindFewElements(){
        ArrayList<Client> array = filter.searchByPhoneNumbBeginning(clients, "+7");
        Assertions.assertEquals(array, clients);
    }

    @Test
    public void searchByPhone_Empty(){
        ArrayList<Client> array = filter.searchByPhoneNumbBeginning(clients, "22");
        Assertions.assertEquals(array.size(), 0);
    }

    @Test
    public void searchByPhone_NullArray_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->filter.searchByPhoneNumbBeginning(null, "Q"));
    }

    // - - - - - - - - - - - - - - - - - - Grouping

    // - - - - - - - - - - - - - - - - - - Group by country

    @Test
    public void groupByCountry_FindOneGroup(){
        Map<String, List<Client>> groups = filter.groupByCountry(clients);
        Assertions.assertEquals(groups.keySet().size(), 1);
    }

    @Test
    public void groupByCountry_NullArray_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->filter.groupByCountry(null));
    }

    // - - - - - - - - - - - - - - - - - - Group by city

    @Test
    public void groupByCity_FindThreeGroups(){
        Map<String, List<Client>> groups = filter.groupByTown(clients);
        Assertions.assertEquals(groups.keySet().size(), 3);
    }

    @Test
    public void groupByCity_NullArray_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->filter.groupByTown(null));
    }

    // - - - - - - - - - - - - - - - - - - Group by street

    @Test
    public void groupByStreet_FindThreeGroups(){
        Map<String, List<Client>> groups = filter.groupByStreet(clients);
        Assertions.assertEquals(groups.keySet().size(), 5);
    }

    @Test
    public void groupByStreet_NullArray_ShouldThrowIAException(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->filter.groupByStreet(null));
    }

    // - - - - - - - - - - - - - - - - - - Add new client

    @Test
    public void addNewClientOrNumber_AddNewClient(){
        ArrayList<Client> emptyA = new ArrayList<>();

        filter.addNewClientOrNumber(emptyA, clients.get(0));

        Assertions.assertEquals(emptyA.get(0), clients.get(0));
    }

    @Test
    public void addNewClientOrNumber_AddNewPhone(){
        ArrayList<Client> emptyA = new ArrayList<>();

        Client c = clients.get(0);
        filter.addNewClientOrNumber(emptyA, c);
        filter.addNewClientOrNumber(emptyA, new Client(c.getName(), c.getSurname(), c.getPatronymic(),
                c.getAddress(), c.getBirthDate(), new ArrayList<>(){{add("8(222)***-**-**");}}));

        Assertions.assertEquals(emptyA.size(), 1);
    }

    // - - - - - - - - - - - - - - - - - - Remove client

    @Test
    public void tryRemoveClient_SuccessfullyRemoveClient(){
        ArrayList<Client> emptyA = new ArrayList<>();

        Client c = clients.get(0);
        filter.addNewClientOrNumber(emptyA, clients.get(0));

        Assertions.assertEquals(filter.tryRemoveClient(emptyA,
                c.getName(), c.getSurname(), c.getPatronymic(), c.getPhoneNumbers().get(0)), emptyA.size() == 0);
    }

    @Test
    public void tryRemoveClient_NotEmptyArray_UnsuccessfullyRemoveClient(){
        ArrayList<Client> emptyA = new ArrayList<>();

        Client c = clients.get(0);
        filter.addNewClientOrNumber(emptyA, clients.get(0));

        Assertions.assertEquals(!filter.tryRemoveClient(emptyA,
                c.getName(), c.getSurname(), c.getPatronymic(), "234"), emptyA.size() == 1);
    }

    @Test
    public void tryRemoveClient_EmptyArray_UnsuccessfullyRemoveClient(){
        ArrayList<Client> emptyA = new ArrayList<>();

        Client c = clients.get(0);

        Assertions.assertEquals(!filter.tryRemoveClient(emptyA,
                c.getName(), c.getSurname(), c.getPatronymic(), "234"), emptyA.size() == 0);
    }
}

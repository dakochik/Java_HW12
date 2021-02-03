package tools;

import objects.Client;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class which makes necessary strings, groupings, additions and removing.
 */
public class ClientDataFilter {

    /**
     * Filters array with clients by theirs full name. Full name has to be written with the following pattern^
     * {surname} {name} {patronymic}
     * Name and patronymic can be skipped. In this case you don't need to write a gaps.
     *
     * @param clients array with clients where we need to make searching.
     * @param nameBeg beginning of searching full name with the mentioned pattern
     * @return clients with the same full name beginning
     * @throws IllegalArgumentException if input array of users is null
     */
    public ArrayList<Client> searchByNameBeginning(ArrayList<Client> clients, String nameBeg) throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        return clients.stream().filter(it -> (String.format("%s %s %s", it.getSurname(), it.getName(), it.getPatronymic()))
                .toLowerCase().subSequence(0, Math.min(nameBeg.length(),
                        String.format("%s %s %s", it.getSurname(), it.getName(), it.getPatronymic()).length()))
                .equals(nameBeg.toLowerCase()))
                .sorted(Comparator.comparing(Client::getName)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Filters array with clients by theirs birth date. You can user year and/or month and/or day of month.
     *
     * @param clients array with clients where we need to make searching.
     * @param year    year number for filtering
     * @param month   month number for filtering
     * @param day     day of month number for filtering
     * @return clients with the same full name beginning
     * @throws IllegalArgumentException if input array of users is null
     */
    public ArrayList<Client> searchByBirthDate(ArrayList<Client> clients, Integer year, Integer month, Integer day)
            throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        return clients.stream().filter(it -> (year == null || it.getBirthDate().getYear() == year) &&
                (month == null || it.getBirthDate().getMonthValue() == month) &&
                (day == null || it.getBirthDate().getDayOfMonth() == day))
                .sorted(Comparator.comparing(Client::getSurname)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Filters array with clients by theirs phone numbers. You can write a whole number or just beginning.
     *
     * @param clients   array with clients where we need to make searching.
     * @param phoneNumb phone number for filtering
     * @return clients with the same full name beginning
     * @throws IllegalArgumentException if input array of users is null
     */
    public ArrayList<Client> searchByPhoneNumbBeginning(ArrayList<Client> clients, String phoneNumb)
            throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        return clients.stream().filter(it -> it.getPhoneNumbers().stream().anyMatch(numb -> numb.toLowerCase().
                subSequence(0, Math.min(phoneNumb.length(), numb.length())).equals(phoneNumb.toLowerCase())))
                .sorted(Comparator.comparing(Client::getPatronymic)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Groups clients by their countries names.
     *
     * @param clients array with clients where we need to make searching.
     * @return pairs, where the key is country name, and t he value is list of users, which are living in this country
     * @throws IllegalArgumentException if input array of users is null
     */
    public Map<String, List<Client>> groupByCountry(ArrayList<Client> clients) throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        return clients.stream().collect(Collectors.groupingBy(it -> it.getAddress().getCountry()));
    }

    /**
     * Groups clients by their towns names.
     *
     * @param clients array with clients where we need to make searching.
     * @return pairs, where the key is town name, and t he value is list of users, which are living in this city
     * @throws IllegalArgumentException if input array of users is null
     */
    public Map<String, List<Client>> groupByTown(ArrayList<Client> clients) throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        return clients.stream().collect(Collectors.groupingBy(it -> it.getAddress().getTown()));
    }

    /**
     * Groups clients by their streets names.
     *
     * @param clients array with clients where we need to make searching.
     * @return pairs, where the key is street name, and t he value is list of users, which are living on this street
     * @throws IllegalArgumentException if input array of users is null
     */
    public Map<String, List<Client>> groupByStreet(ArrayList<Client> clients) throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        return clients.stream().collect(Collectors.groupingBy(it -> it.getAddress().getStreet()));
    }

    /**
     * Adds new client to array with clients. If there are already such clients, it adds new phone numbers to this clients.
     *
     * @param clients array with clients where we need to make searching.
     * @param client  new client
     * @throws IllegalArgumentException if input array of users is null
     */
    public void addNewClientOrNumber(ArrayList<Client> clients, Client client) throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        Optional<Client> opt = clients.stream().filter(it -> it.equals(client)).findAny();

        if (opt.isPresent()) {
            opt.get().tryAddPhoneNumber(client.getPhoneNumbers());
        } else {
            clients.add(client);
        }
    }

    /**
     * Removes client from the array with clients.
     *
     * @param clients    array with clients where we need to make searching.
     * @param surname    surname of client we need to remove
     * @param name       name of client we need to remove
     * @param patronymic patronymic of client we need to remove
     * @param phoneNumb  one of phone numbers of client we need to remove
     * @return if there was such client
     * @throws IllegalArgumentException if input array of users is null
     */
    public boolean tryRemoveClient(ArrayList<Client> clients, String surname, String name, String patronymic, String phoneNumb)
            throws IllegalArgumentException {
        if (clients == null) {
            throw new IllegalArgumentException("Clients array can't be null.");
        }

        Optional<Client> opt = clients.stream().filter(it -> it.getName().toLowerCase().equals(name.toLowerCase()) &&
                it.getSurname().toLowerCase().equals(surname.toLowerCase()) &&
                it.getPatronymic().toLowerCase().equals(patronymic.toLowerCase()) &&
                it.getPhoneNumbers().contains(phoneNumb)).findAny();

        if (opt.isPresent()) {
            clients.remove(opt.get());
            return true;
        }

        return false;
    }
}

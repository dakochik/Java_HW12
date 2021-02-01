package tools;

import objects.Client;

import java.util.*;
import java.util.stream.Collectors;

public class ClientDataFilter {

    public ArrayList<Client> searchByNameBeginning(ArrayList<Client> clients, String nameBeg) throws IllegalArgumentException {
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        return clients.stream().filter(it -> (String.format("%s %s %s", it.getSurname(), it.getName(), it.getPatronymic()))
                .toLowerCase().subSequence(0, Math.min(nameBeg.length(),
                        String.format("%s %s %s", it.getSurname(), it.getName(), it.getPatronymic()).length()))
                .equals(nameBeg.toLowerCase()))
                .sorted(Comparator.comparing(Client::getName)).collect(Collectors.toCollection(ArrayList::new));
    }

//    public ArrayList<Client> searchBySurnameBeginning(ArrayList<Client> clients, String surnameBeg) throws IllegalArgumentException {
//        if (clients == null) {
//            throw new IllformedLocaleException("Clients array can't be null.");
//        }
//
//        return clients.stream().filter(it -> it.getSurname().toLowerCase().
//                subSequence(0, surnameBeg.length() - 1).equals(surnameBeg.toLowerCase()))
//                .sorted(Comparator.comparing(Client::getSurname)).collect(Collectors.toCollection(ArrayList::new));
//    }
//
//    public ArrayList<Client> searchByPatronymicBeginning(ArrayList<Client> clients, String pBeg) throws IllegalArgumentException {
//        if (clients == null) {
//            throw new IllformedLocaleException("Clients array can't be null.");
//        }
//
//        return clients.stream().filter(it -> it.getPatronymic().toLowerCase().
//                subSequence(0, pBeg.length() - 1).equals(pBeg.toLowerCase()))
//                .sorted(Comparator.comparing(Client::getPatronymic)).collect(Collectors.toCollection(ArrayList::new));
//    }

    public ArrayList<Client> searchByBirthDate(ArrayList<Client> clients, Integer year, Integer month, Integer day)
            throws IllegalArgumentException {
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        return clients.stream().filter(it -> (year == null || it.getBirthDate().getYear() == year) &&
                (month == null || it.getBirthDate().getMonthValue() == month) &&
                (day == null || it.getBirthDate().getDayOfMonth() == day))
                .sorted(Comparator.comparing(Client::getSurname)).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Client> searchByPhoneNumbBeginning(ArrayList<Client> clients, String phoneNumb)
            throws IllegalArgumentException {
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        return clients.stream().filter(it -> it.getPhoneNumbers().stream().anyMatch(numb -> numb.toLowerCase().
                subSequence(0, Math.min(phoneNumb.length(), numb.length())).equals(phoneNumb.toLowerCase())))
                .sorted(Comparator.comparing(Client::getPatronymic)).collect(Collectors.toCollection(ArrayList::new));
    }

    public Map<String, List<Client>> groupByCountry(ArrayList<Client> clients) throws IllegalArgumentException{
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        return clients.stream().collect(Collectors.groupingBy(it -> it.getAddress().getCountry()));
    }

    public Map<String, List<Client>> groupByTown(ArrayList<Client> clients) throws IllegalArgumentException{
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        return clients.stream().collect(Collectors.groupingBy(it -> it.getAddress().getTown()));
    }

    public Map<String, List<Client>> groupByStreet(ArrayList<Client> clients) throws IllegalArgumentException{
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        return clients.stream().collect(Collectors.groupingBy(it -> it.getAddress().getStreet()));
    }

    public void addNewClientOrNumber(ArrayList<Client> clients, Client client) throws IllegalArgumentException{
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        Optional<Client> opt = clients.stream().filter(it -> it.equals(client)).findAny();

        if(opt.isPresent()){
            opt.get().tryAddPhoneNumber(client.getPhoneNumbers());
        }
        else{
            clients.add(client);
        }
    }

    public boolean tryRemoveClient(ArrayList<Client> clients, String surname, String name, String patronymic, String phoneNumb)
            throws IllegalArgumentException{
        if (clients == null) {
            throw new IllformedLocaleException("Clients array can't be null.");
        }

        Optional<Client> opt = clients.stream().filter(it -> it.getName().toLowerCase().equals(name.toLowerCase()) &&
                it.getSurname().toLowerCase().equals(surname.toLowerCase()) &&
                it.getPatronymic().toLowerCase().equals(patronymic.toLowerCase()) &&
                it.getPhoneNumbers().contains(phoneNumb)).findAny();

        if(opt.isPresent()){
            clients.remove(opt.get());
            return true;
        }

        return false;
    }
}

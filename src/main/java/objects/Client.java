package objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Represents a class, which contains information about one user
 */
public class Client {
    /**
     * User's name
     */
    private final String name;

    /**
     * User's surname
     */
    private final String surname;

    /**
     * User's patronymic
     */
    private final String patronymic;

    /**
     * User's address
     */
    private final Address address;

    /**
     * User's birth date
     */
    private final LocalDate birthDate;

    /**
     * List of user's phone numbers
     */
    private ArrayList<String> phoneNumbers;

    /**
     * User's e-mail. If user doesn't have, it will be null.
     */
    private String email;

    /**
     * Constructor of Client class
     *
     * @param name         client's name
     * @param surname      client's surname
     * @param patronymic   client's patronymic
     * @param address      client's address
     * @param birthDate    client's birth date
     * @param phoneNumbers list of client's phone numbers
     * @throws IllegalArgumentException if list of phone numbers have null value or it's empty
     */
    public Client(String name, String surname, String patronymic, Address address, LocalDate birthDate,
                  ArrayList<String> phoneNumbers)
            throws IllegalArgumentException {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.birthDate = birthDate;

        if (phoneNumbers == null || phoneNumbers.size() == 0) {
            throw new IllegalArgumentException("You have to enter at least one phone number.");
        }

        // я час пытался разобраться с проверкой номера на корретность для любой страны, но ничего не вышло, я сдаюсь

        this.phoneNumbers = phoneNumbers.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
        email = null;
    }

    /**
     * Constructor of Client class
     *
     * @param name         client's name
     * @param surname      client's surname
     * @param patronymic   client's patronymic
     * @param address      client's address
     * @param birthDate    client's birth date
     * @param phoneNumbers list of client's phone numbers
     * @param email        user's e-mail
     * @throws IllegalArgumentException if e-mail are fake.
     */
    public Client(String name, String surname, String patronymic, Address address, LocalDate birthDate,
                  ArrayList<String> phoneNumbers, String email)
            throws IllegalArgumentException {

        this(name, surname, patronymic, address, birthDate, phoneNumbers);

        Pattern p = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

        if (email == null || !p.matcher(email).matches()) {
            throw new IllegalArgumentException("You have to valid email.");
        }

        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Adds new phone number to the list of user's phone numbers. Than deletes the extra copies.
     *
     * @param newNumbs list of new phone numbers.
     */
    public void tryAddPhoneNumber(List<String> newNumbs) {
        phoneNumbers.addAll(newNumbs);
        phoneNumbers = phoneNumbers.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Checks if two objects are the same.
     *
     * @param obj another objects, with which we need to compare current obj.
     * @return returns true if two objects are descendants of Client class and if the fields
     * name, surname, patronymic, birthDAte and address are the same, otherwise it returns false;
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Client) {
            return name.toLowerCase().equals(((Client) obj).name.toLowerCase())
                    && surname.toLowerCase().equals(((Client) obj).surname.toLowerCase())
                    && patronymic.toLowerCase().equals(((Client) obj).patronymic.toLowerCase())
                    && birthDate.equals(((Client) obj).birthDate)
                    && address.equals(((Client) obj).address);
        }

        return false;
    }
}

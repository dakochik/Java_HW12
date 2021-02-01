package objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Client {
    private String name;
    private String surname;
    private String patronymic;
    private Address address;
    private LocalDate birthDate;
    private ArrayList<String> phoneNumbers;
    private String email;

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
        email = " - ";
    }

    public Client(String name, String surname, String patronymic, Address address, LocalDate birthDate,
                  ArrayList<String> phoneNumbers, String email)
            throws IllegalArgumentException {

        this(name, surname, patronymic, address, birthDate, phoneNumbers);

        if (email.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")) {
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

    public void tryAddPhoneNumber(List<String> newNumbs) {
        phoneNumbers.addAll(newNumbs);
        phoneNumbers = phoneNumbers.stream().distinct().collect(Collectors.toCollection(ArrayList::new));
    }

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

package tools;


import objects.Address;
import objects.Client;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which reads information about new client and creating this class.
 */
public class NewClientDataReader {
    /**
     * Helps us to save logs information
     */
    protected static Logger log;

    /**
     * Reads information about client and creating Client object
     *
     * @param in  takes input information from it
     * @param out prints messages for user there
     * @return new client
     */
    public Client clientReader(InputStream in, PrintStream out) {
        log.log(Level.INFO, "Creating new client");

        System.out.println("Now you need to input information about new user.");

        Scanner sc = new Scanner(in);

        out.print("Write your name:");
        String name = sc.nextLine();

        out.print("Write your surname:");
        String sName = sc.nextLine();

        out.print("Write your patronymic:");
        String pName = sc.nextLine();

        log.log(Level.INFO, "Getting new client's address");
        Address address = addressReader(in, out);

        log.log(Level.INFO, "Getting new client's date of birth");
        LocalDate calendar = readDate(in, out);

        log.log(Level.INFO, "Getting new client's phone numbers");
        ArrayList<String> phoneNumbers = readPhones(in, out);

        out.println("Would you like, to enter your e-mail? If so, enter it, or press Enter button otherwise.");
        String email = sc.nextLine();

        Client client;

        if (email == null || email.length() == 0) {
            log.log(Level.INFO, "Client's email is null or it's length is 0");
            client = new Client(name, sName, pName, address, calendar, phoneNumbers);
        } else {
            try {
                log.log(Level.INFO, "Trying to create a client with e-mail");
                client = new Client(name, sName, pName, address, calendar, phoneNumbers, email);
            } catch (IllegalArgumentException e) {
                log.log(Level.INFO, String.format("Clients e-mail was fake: %s", email));
                out.println("You make a mistake with e-mail, so it will be empty");
                client = new Client(name, sName, pName, address, calendar, phoneNumbers);
            }
        }

        return client;
    }

    /**
     * Reads information about client's address and creating Address object
     *
     * @param in  takes input information from it
     * @param out prints messages for user there
     * @return new address
     */
    public Address addressReader(InputStream in, PrintStream out) {
        System.out.println("Now you need to input your address.");

        Scanner sc = new Scanner(in);

        out.print("Write country name:");
        String country = sc.nextLine();

        out.print("Write city name:");
        String city = sc.nextLine();

        out.print("Write street name:");
        String street = sc.nextLine();

        out.print("Write house number:");
        int house = readInt(in, out, 0, Integer.MAX_VALUE);

        return new Address(country, city, street, house);
    }

    /**
     * Reads information about client's birth date and creating LocalDate object
     *
     * @param in  takes input information from it
     * @param out prints messages for user there
     * @return new date
     */
    private LocalDate readDate(InputStream in, PrintStream out) {
        System.out.println("Now you need to input your birth date.");

        System.out.print("Input the year of your birth:");
        int year = readInt(in, out, 1900, LocalDate.now().getYear());

        System.out.print("Input the month number of your birth:");
        int month = readInt(in, out, 0, 11);

        System.out.print("Input the day of your birth:");
        int day = readInt(in, out, 1, 31);

        return LocalDate.of(year, month, day);
    }

    /**
     * Reads information about client's phone numbers
     *
     * @param in  takes input information from it
     * @param out prints messages for user there
     * @return list of clients phone numbers represented by strings
     */
    private ArrayList<String> readPhones(InputStream in, PrintStream out) {
        System.out.println("Now you need to input your phone numbers.");

        Scanner sc = new Scanner(in);
        System.out.print("Input the number of your phone numbers:");
        int numb = readInt(in, out, 1, 20);
        ArrayList<String> res = new ArrayList<>();

        for (int i = 0; i < numb; ++i) {
            res.add(sc.nextLine());
        }

        return res;
    }

    /**
     * Reads integer number from input stream
     *
     * @param in     takes input information from it
     * @param out    prints messages for user there
     * @param minVal minimum possible value of input number
     * @param maxVal maximum possible value of input number
     * @return integer number
     */
    public int readInt(InputStream in, PrintStream out, int minVal, int maxVal) {
        Scanner sc = new Scanner(in);
        int res = 0;
        boolean stop;
        do {
            stop = sc.hasNextInt();
            if (stop) {
                res = sc.nextInt();
                if (res < minVal || res > maxVal) {
                    out.printf("You have to input an integer number not less than %d, and not more than %d\n", minVal, maxVal);
                    stop = false;
                }
            } else {
                sc.next();
                out.printf("You have to input an integer number not less than %d, and not more than %d\n", minVal, maxVal);
            }
        } while (!stop);
        return res;
    }

}

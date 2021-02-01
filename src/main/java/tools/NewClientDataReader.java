package tools;


import objects.Address;
import objects.Client;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class NewClientDataReader {

    public Client clientReader(InputStream in, PrintStream out){
        System.out.println("Now you need to input information about new user.");

        Scanner sc = new Scanner(in);

        out.print("Write your name:");
        String name = sc.nextLine();

        out.print("Write your surname:");
        String sName = sc.nextLine();

        out.print("Write your patronymic:");
        String pName = sc.nextLine();

        Address address = addressReader(in, out);

        LocalDate calendar = readDate(in, out);

        ArrayList<String> phoneNumbers = readPhones(in, out);

        out.println("Would you like, to enter your e-mail? If so, enter it, or press Enter button otherwise.");
        String email = sc.nextLine();

        Client client;

        if(email == null || email.length() == 0){
            client = new Client(name, sName, pName, address, calendar, phoneNumbers);
        }
        else {
            try {
                client = new Client(name, sName, pName, address, calendar, phoneNumbers, email);
            } catch (IllegalArgumentException e) {
                out.println("You make a mistake with e-mail, so it will be empty");
                client = new Client(name, sName, pName, address, calendar, phoneNumbers);
            }
        }

        return client;
    }

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

    private LocalDate readDate(InputStream in, PrintStream out){
        System.out.println("Now you need to input your birth date.");

        System.out.print("Input the year of your birth:");
        int year = readInt(in, out, 1900, LocalDate.now().getYear());

        System.out.print("Input the month number of your birth:");
        int month = readInt(in, out, 0, 11);

        System.out.print("Input the day of your birth:");
        int day = readInt(in, out, 1, 31);

        return LocalDate.of(year, month, day);
    }

    private ArrayList<String> readPhones(InputStream in, PrintStream out){
        System.out.println("Now you need to input your phone numbers.");

        Scanner sc = new Scanner(in);
        System.out.print("Input the number of your phone numbers:");
        int numb = readInt(in, out, 1, 20);
        ArrayList<String> res = new ArrayList<>();

        for(int i =0; i < numb; ++i){
            res.add(sc.nextLine());
        }

        return res;
    }

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

package tools;

import com.sun.tools.javac.Main;
import objects.Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class which creates dialog with user by number of commands
 */
public class UserInterfaceSupplier {
    /**
     * Helps us to save logs information
     */
    private static Logger log;

    static {
        try (FileInputStream ins = new FileInputStream("./././log.config")) {
            LogManager.getLogManager().readConfiguration(ins);
            log = Logger.getLogger(UserInterfaceSupplier.class.getName());
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    private final InputStream in;
    private final PrintStream out;
    private final ClientDataFilter filter;
    private final NewClientDataReader reader;
    private final ContactsWriterAndReader fReader;

    /**
     * All commands, which user can use
     */
    private enum UserCommands {
        INFO("info"),
        SEARCH("search"),
        SEARCH_BY_NAME("name"),
        SEARCH_BY_YEAR("year"),
        SEARCH_BY_MONTH("month"),
        SEARCH_BY_DAY_OF_MONTH("day"),
        SEARCH_BY_PHONE("phone"),
        GROUPING("grouping"),
        GROUPING_BY_COUNTRY("country"),
        GROUPING_BY_TOWN("town"),
        GROUPING_BY_STREET("street"),
        ADD("add"),
        DELETE("delete"),
        SHOW_ALL("all"),
        CANCEL("e");

        private final String label;

        UserCommands(String label) {
            this.label = label;
        }
    }

    /**
     * Creates class which will provide user interface
     *
     * @param in  takes input information from it
     * @param out prints message for user there
     * @throws IllegalArgumentException if InputStream and/or OutputStream are/is null
     */
    public UserInterfaceSupplier(InputStream in, PrintStream out) throws IllegalArgumentException {
        log.log(Level.INFO, "Creating an UserInterfaceSupplier object");

        NewClientDataReader.log = log;

        if (in == null || out == null) {
            log.log(Level.WARNING, "One of stream parameters was null");
            throw new IllegalArgumentException("None of input streams can be null");
        }

        this.in = in;
        this.out = out;
        filter = new ClientDataFilter();
        reader = new NewClientDataReader();
        fReader = new ContactsWriterAndReader();
    }

    /**
     * Provides whole aspects of dialog user. It reads and writes users information, reads and executes input commands
     */
    public void phoneBookExecutor() {
        log.log(Level.INFO, "Start executing dialog with user");

        out.println("It's a \"Phone Book\" program.\nPlease, enter \"info\" to get command info.");
        ArrayList<Client> clients;
        try {
            log.log(Level.INFO, "Reading clients data base");

            clients = fReader.readJson();
        } catch (IOException e) {
            log.log(Level.WARNING, "Problems with clients data reading", e);

            out.println("Impossible to read a clients data. Please, check permissions and try again later.");
            return;
        }

        byte steps = 1;

        log.log(Level.INFO, "Asking user");

        do {
            if (steps == 0) {
                try {
                    fReader.writeJson(clients);
                } catch (IOException e) {
                    out.println("Impossible to save client base. Please, check permissions and try again later.");
                    return;
                }
            }

            steps = (byte) ((++steps) % (byte) 20);

            out.println("You are in the main menu. What would you like to do?\n");

        } while (readingCommandProvider(clients));

        log.log(Level.INFO, "Finishing dialog with user");

        try {
            log.log(Level.INFO, "Writing clients data base");

            fReader.writeJson(clients);
        } catch (IOException e) {
            log.log(Level.WARNING, "Problems with clients data bse writing", e);

            out.println("Impossible to save client base. Please, check permissions and try again later.");
        }
    }

    /**
     * Reads which command did user write ans execute appropriate function
     *
     * @param clients current clients array
     * @return should the program still executing
     */
    private boolean readingCommandProvider(ArrayList<Client> clients) {
        log.log(Level.INFO, "Reading user command");

        Scanner sc = new Scanner(in);
        String response;
        do {
            response = sc.nextLine();
            if (UserCommands.SEARCH.label.equals(response)) {
                log.log(Level.INFO, "Preparing for searching");

                createSearching(clients);
                return true;
            } else if (UserCommands.GROUPING.label.equals(response)) {
                log.log(Level.INFO, "Preparing for grouping");

                createGrouping(clients);
                return true;
            } else if (UserCommands.ADD.label.equals(response)) {
                log.log(Level.INFO, "Preparing for addition");

                addClient(clients);
                return true;
            } else if (UserCommands.DELETE.label.equals(response)) {
                log.log(Level.INFO, "Preparing for deletion");

                removeClient(clients);
                return true;
            } else if (UserCommands.SHOW_ALL.label.equals(response)) {
                log.log(Level.INFO, "Preparing for showing all clients");

                printClientDataInfo(clients);
                return true;
            } else if (UserCommands.CANCEL.label.equals(response)) {
                log.log(Level.INFO, "Preparing for the end of the program");

                return false;
            } else if (UserCommands.INFO.label.equals(response)) {
                log.log(Level.INFO, "Preparing for printing information");

                printInfo();
            } else {
                out.printf("What do you mean, dude? Please, try again or use \"%s\" to get info about \n", UserCommands.INFO.label);
            }

        }
        while (true);
    }

    /**
     * Prints commands info
     */
    private void printInfo() {
        log.log(Level.INFO, "Printing info");

        out.println("\n~ ~ ~ ~ ~ ~ ~ ~ INFO ~ ~ ~ ~ ~ ~ ~ ~\nThere are commands you can use:\n\n");

        out.printf("%s - show main information about commands.\n\n", UserCommands.INFO.label);

        out.printf("%s - find client by data.\n\n", UserCommands.SEARCH.label);
        out.printf("\t%s - find client by it's full name.\n", UserCommands.SEARCH_BY_NAME.label);
        out.printf("\t%s - find client by it's birth year.\n", UserCommands.SEARCH_BY_YEAR.label);
        out.printf("\t%s - find client by it's birth month.\n", UserCommands.SEARCH_BY_MONTH.label);
        out.printf("\t%s - find client by it's birth day of month.\n", UserCommands.SEARCH_BY_DAY_OF_MONTH.label);
        out.printf("\t%s - find client by one of it's phone numbers.\n\n", UserCommands.SEARCH_BY_PHONE.label);

        out.printf("%s - group clients by address.\n\n", UserCommands.GROUPING.label);
        out.printf("\t%s - group clients by their country.\n", UserCommands.GROUPING_BY_COUNTRY.label);
        out.printf("\t%s - group clients by their town.\n", UserCommands.GROUPING_BY_TOWN.label);
        out.printf("\t%s - group clients by their street.\n\n", UserCommands.GROUPING_BY_STREET.label);

        out.printf("%s - add new client or number to phone book.\n\n", UserCommands.ADD.label);

        out.printf("%s - delete client from phone book.\n\n", UserCommands.DELETE.label);

        out.printf("%s - show all clients.\n\n", UserCommands.SHOW_ALL.label);

        out.printf("%s - finish current process or program.\n\n~ ~ ~ ~ ~ ~ ~ ~ END ~ ~ ~ ~ ~ ~ ~ ~\n\n", UserCommands.CANCEL.label);
    }

    /**
     * Create searching by filed which user have to choose by command
     *
     * @param clients array we need to sort
     */
    private void createSearching(ArrayList<Client> clients) {
        log.log(Level.INFO, "Asking for type of sorting");

        ArrayList<Client> filtered;
        String response;
        Scanner sc = new Scanner(in);

        out.println("Please, enter what property would you use to find a client.");
        do {
            response = sc.nextLine();
            if (UserCommands.SEARCH_BY_NAME.label.equals(response)) {
                log.log(Level.INFO, "Sorting by name");

                out.println("Please, enter surname (also you can add name and patronymic)");
                filtered = filter.searchByNameBeginning(clients, sc.nextLine());
                break;
            } else if (UserCommands.SEARCH_BY_PHONE.label.equals(response)) {
                log.log(Level.INFO, "Sorting by phone number");

                out.println("Please, enter phone number");
                filtered = filter.searchByPhoneNumbBeginning(clients, sc.nextLine());
                break;
            } else if (UserCommands.SEARCH_BY_YEAR.label.equals(response)) {
                log.log(Level.INFO, "Sorting by year");

                out.println("Please, enter birth year");
                filtered = filter.searchByBirthDate(clients, reader.readInt(in, out, 1900, LocalDate.now().getYear()), null, null);
                break;
            } else if (UserCommands.SEARCH_BY_MONTH.label.equals(response)) {
                log.log(Level.INFO, "Sorting by month");

                out.println("Please, enter birth month");
                filtered = filter.searchByBirthDate(clients, null, reader.readInt(in, out, 0, 11), null);
                break;
            } else if (UserCommands.SEARCH_BY_DAY_OF_MONTH.label.equals(response)) {
                log.log(Level.INFO, "Sorting by day of month");

                out.println("Please, enter birth day of month");
                filtered = filter.searchByBirthDate(clients, null, null, reader.readInt(in, out, 1, 31));
                break;
            } else if (UserCommands.CANCEL.label.equals(response)) {
                log.log(Level.INFO, "Cancel sorting");

                return;
            } else if (UserCommands.INFO.label.equals(response)) {
                printInfo();
            } else {
                out.printf("What do you mean ,dude? please, try again or use \"%s\" to get info about \n", UserCommands.INFO.label);
            }
        }
        while (true);

        log.log(Level.INFO, "Finishing the sorting");

        printClientDataInfo(filtered);
    }

    /**
     * Create grouping by filed which user have to choose by command
     *
     * @param clients array we have to group
     */
    private void createGrouping(ArrayList<Client> clients) {
        log.log(Level.INFO, "Asking for type of grouping");

        Map<String, List<Client>> filtered;
        String response;
        Scanner sc = new Scanner(in);

        out.println("Please, enter what property would you use to create a grouping.");
        do {
            response = sc.nextLine();

            if (UserCommands.GROUPING_BY_COUNTRY.label.equals(response)) {
                log.log(Level.INFO, "Grouping by country");

                filtered = filter.groupByCountry(clients);
                break;
            } else if (UserCommands.GROUPING_BY_TOWN.label.equals(response)) {
                log.log(Level.INFO, "Grouping by town");

                filtered = filter.groupByTown(clients);
                break;
            } else if (UserCommands.GROUPING_BY_STREET.label.equals(response)) {
                log.log(Level.INFO, "Grouping by street");

                filtered = filter.groupByStreet(clients);
                break;
            } else if (UserCommands.CANCEL.label.equals(response)) {
                log.log(Level.INFO, "Cancel grouping");

                return;
            } else if (UserCommands.INFO.label.equals(response)) {
                printInfo();
            } else {
                out.printf("What do you mean ,dude? please, try again or use \"%s\" to get info about \n", UserCommands.INFO.label);
            }
        } while (true);

        log.log(Level.INFO, "Finishing the grouping");

        printClientDataInfo(filtered);
    }

    /**
     * Gets info about new client and adds him to the array
     *
     * @param clients current list of clients
     */
    private void addClient(ArrayList<Client> clients) {
        log.log(Level.INFO, "PReading new client data");
        Client newClient = reader.clientReader(in, out);

        log.log(Level.INFO, "Making insertion of a new client");
        filter.addNewClientOrNumber(clients, newClient);
    }

    /**
     * Gets information about user we need to delete and tries to delete him
     *
     * @param clients current list of clients
     */
    private void removeClient(ArrayList<Client> clients) {
        out.println("To remove client, please, write full name and one od the phone numbers. Separate it with single gap.");
        out.printf("To cancel removing enter \"%s\"\n", UserCommands.CANCEL.label);
        Scanner sc = new Scanner(in);
        Client c;

        do {
            log.log(Level.INFO, "Reading main info about client, we need to delete");

            String answer = sc.nextLine();
            String[] data = answer.split(" ");

            if (data.length == 4) {

                if (filter.tryRemoveClient(clients, data[0], data[1], data[2], data[3])) {
                    log.log(Level.INFO, "Successfully delete the client");
                    out.println("You have deleted this client successfully.");
                    return;
                } else {
                    log.log(Level.INFO, "There was no such client");
                    out.println("There are no such client.");
                }

            } else if (answer.equals(UserCommands.CANCEL.label)) {
                log.log(Level.INFO, "MDeletion was canceled");
                return;
            } else {
                out.println("You have to enter surname, name, patronymic and a phone number. Please, try again.");
            }

        } while (true);
    }

    /**
     * Prints information about grouped list of clients
     *
     * @param clientsMap map of clients grouped by map key
     */
    private void printClientDataInfo(Map<String, List<Client>> clientsMap) {
        log.log(Level.INFO, "Printing info about clients");

        if (clientsMap.isEmpty()) {
            log.log(Level.INFO, "There are no such clients");
            out.println("\n - - - - - - -> No such elements <- - - - - - - \n");
        }

        log.log(Level.INFO, "There are more than 0 clients");
        for (String key : clientsMap.keySet()) {
            out.printf("\n - - - %s - - - \n\n", key);
            printClientDataInfo(new ArrayList<>() {{
                addAll(clientsMap.get(key));
            }});
        }
    }

    /**
     * Prints info about every client in list
     *
     * @param clients current list of clients
     */
    private void printClientDataInfo(ArrayList<Client> clients) {
        if (clients.isEmpty()) {
            out.println("\n - - - - - - -> No such elements <- - - - - - - \n");
        }

        for (Client c : clients) {

            out.printf(">\t%s %s %s\te-mail: %s\n", c.getSurname(), c.getName(), c.getPatronymic(),
                    (c.getEmail() == null ? "-" : c.getEmail()));

            out.printf("\t\tAddress: %s, %s, %s, %s", c.getAddress().getHomeNumb(), c.getAddress().getStreet(),
                    c.getAddress().getTown(), c.getAddress().getCountry());

            out.printf("\tDate of birth: %s\n", c.getBirthDate().toString());

            ArrayList<String> numbers = c.getPhoneNumbers();

            out.printf("\tPhone numbers: %s\n", numbers.get(0));

            for (int i = 1; i < numbers.size(); ++i) {
                out.printf("\t               %s\n", numbers.get(i));
            }

            out.print("\n");
        }
    }
}

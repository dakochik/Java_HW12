import objects.Address;
import objects.Client;
import tools.ClientDataFilter;
import tools.ContactsWriterAndReader;
import tools.NewClientDataReader;
import tools.UserInterfaceSupplier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {

    public static void main(String[] args){
//        ArrayList<Client> clients = new ArrayList<>();
//        clients.add(new Client("D", "D", "D",
//                new Address("R", "M", "f",  2),
//                new GregorianCalendar(2001, 2, 3), new ArrayList<String>(){ {add("+7(926)230-86-43");}}));
//
//        try {
//            new ContactsWriterAndReader().writeJson(clients);
//            new ContactsWriterAndReader().readJson();
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        //new NewClientDataReader().addressReader(System.in, System.out);
        //System.out.println(new NewClientDataReader().readDate(System.in, System.out).toString());

        UserInterfaceSupplier supp = new UserInterfaceSupplier(System.in, System.out);

        supp.phoneBookExecutor();
//
//        System.out.println("a b c d e".subSequence(0, 3));
    }
}

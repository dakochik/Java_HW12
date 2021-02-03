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
        UserInterfaceSupplier supp = new UserInterfaceSupplier(System.in, System.out);

        supp.phoneBookExecutor();
    }
}

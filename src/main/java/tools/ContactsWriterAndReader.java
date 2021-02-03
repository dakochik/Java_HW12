package tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import objects.Client;

import java.io.*;
import java.util.ArrayList;

/**
 * Class which reads and writes clients data using JSON format
 */
public class ContactsWriterAndReader {
    /**
     * Path to file with data
     */
    private final String DATA_FILE_NAME = "./././allContacts.txt";

    /**
     * Writes JSON ArrayList of Clients objects into data file
     *
     * @param clients array of clients we need to write
     * @throws IOException if there was problems with writing
     */
    public void writeJson(ArrayList<Client> clients) throws IOException {
        Gson gson = new Gson();
        try (FileWriter fw = new FileWriter(DATA_FILE_NAME)) {
            fw.write(gson.toJson(clients));
        }
    }

    /**
     * Reads JSON ArrayList of Clients objects from data file
     *
     * @return new array with clients from data file
     * @throws IOException if there was problems with reading
     */
    public ArrayList<Client> readJson() throws IOException {
        Gson gson = new Gson();

        BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_NAME));

        ArrayList<Client> response = gson.fromJson(reader.readLine(), (new TypeToken<ArrayList<Client>>() {
        }).getType());

        if (response == null) {
            return new ArrayList<>();
        }

        return response;
    }
}

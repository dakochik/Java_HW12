package tools;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import objects.Client;

import java.io.*;
import java.util.ArrayList;

public class ContactsWriterAndReader {
    private final String DATA_FILE_NAME = "./././allContacts.txt";

    public void writeJson(ArrayList<Client> clients) throws IOException{
        Gson gson = new Gson();
        try(FileWriter fw = new FileWriter(DATA_FILE_NAME)){
            fw.write(gson.toJson(clients));
        }
    }

    public ArrayList<Client> readJson() throws IOException{
        Gson gson = new Gson();

        BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE_NAME));

       ArrayList<Client> response = gson.fromJson(reader.readLine(), (new TypeToken<ArrayList<Client>>(){}).getType());

       if(response == null){
           return new ArrayList<>();
       }

       return  response;
    }
}

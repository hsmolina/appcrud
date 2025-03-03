package appcrud;

// Java program to read from file "friendsContact.txt"
// and display the contacts

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.NumberFormatException;

class DisplayFriends {

    public static String getContacts() {
        StringBuilder contactos = new StringBuilder();

        try {
            String nameNumberString;
            String name;
            long number;

            // Using file pointer creating the file.
            File file = new File("friendsContact.txt");

            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading mode.
            RandomAccessFile raf = new RandomAccessFile(file, "r");

            // Traversing the file
            while (raf.getFilePointer() < raf.length()) {
                // reading line from the file.
                nameNumberString = raf.readLine();

                // splitting the string to get name and number
                String[] lineSplit = nameNumberString.split("!");

                // separating name and number.
                name = lineSplit[0];
                number = Long.parseLong(lineSplit[1]);

                // Storing the contact data
                contactos.append("Nombre: ").append(name)
                         .append(", Número: ").append(number).append("\n");
            }

            raf.close();

        } catch (IOException ioe) {
            return "Error de lectura del archivo.";
        } catch (NumberFormatException nef) {
            return "Formato de número inválido.";
        }

        return contactos.length() > 0 ? contactos.toString() : "No hay contactos.";
    }
}

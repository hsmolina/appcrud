package appcrud;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.NumberFormatException;

class AddFriend {

	public static String addFriend(String newName, long newNumber) {
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

			// Opening file in reading and write mode.
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Checking whether the name of contact already exists.
            while (raf.getFilePointer() < raf.length()) {
                // Reading line from the file.
                nameNumberString = raf.readLine();

                // Splitting the string to get name and number.
                String[] lineSplit = nameNumberString.split("!");

                // Separating name and number.
                name = lineSplit[0];
                number = Long.parseLong(lineSplit[1]);

                // If condition to find existence of record.
                if (name.equals(newName) || number == newNumber) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                // Enter the if block when a record is not already present in the file.
                nameNumberString = newName + "!" + newNumber;

                // WriteBytes function to write a string as a sequence of bytes.
                raf.writeBytes(nameNumberString);

                // To insert the next record in new line.
                raf.writeBytes(System.lineSeparator());

                // Closing the resources.
                raf.close();

                return "El contacto " + newName + " - " + newNumber + " ha sido agregado.";
            } else {
                // Closing the resources.
                raf.close();

                return "El contacto " + newName + " - " + newNumber + " ya existe.";
            }
        } catch (IOException ioe) {
            return "Error de E/S: " + ioe.getMessage();
        }
    }
}

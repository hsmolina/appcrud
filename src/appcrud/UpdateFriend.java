package appcrud;

// Java program to update in the file "friendsContact.txt"
// and change the number of an old contact

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.NumberFormatException;

class UpdateFriend {

    // Method to update a contact in the file "friendsContact.txt"
    public static void updateFriend(String newName, long newNumber) {
        try {
            String nameNumberString;
            String name;
            long number;
            int index;

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
            // getFilePointer() gives the current offset
            // value from start of the file.
            while (raf.getFilePointer() < raf.length()) {
                // Reading line from the file.
                nameNumberString = raf.readLine();

                // Splitting the string to get name and number
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

            // Update the contact if record exists.
            if (found) {
                // Creating a temporary file
                // with file pointer as tmpFile.
                File tmpFile = new File("temp.txt");

                // Opening this temporary file
                // in ReadWrite Mode
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                // Set file pointer to start
                raf.seek(0);

                // Traversing the friendsContact.txt file
                while (raf.getFilePointer() < raf.length()) {
                    // Reading the contact from the file
                    nameNumberString = raf.readLine();

                    index = nameNumberString.indexOf('!');
                    name = nameNumberString.substring(0, index);

                    // Check if the fetched contact
                    // is the one to be updated
                    if (name.equals(newName)) {
                        // Update the number of this contact
                        nameNumberString = name + "!" + newNumber;
                    }

                    // Add this contact in the temporary file
                    tmpraf.writeBytes(nameNumberString);

                    // Add the line separator in the temporary file
                    tmpraf.writeBytes(System.lineSeparator());
                }

                // The contact has been updated now
                // So copy the updated content from
                // the temporary file to original file.

                // Set both files pointers to start
                raf.seek(0);
                tmpraf.seek(0);

                // Copy the contents from
                // the temporary file to original file.
                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }

                // Set the length of the original file
                // to that of temporary.
                raf.setLength(tmpraf.length());

                // Closing the resources.
                tmpraf.close();
                raf.close();

                // Deleting the temporary file
                tmpFile.delete();

                System.out.println("Friend updated.");
            } else {
                // Closing the resources.
                raf.close();

                // Print the message
                System.out.println("Input name does not exist.");
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        } catch (NumberFormatException nef) {
            System.out.println(nef);
        }
    }

    // Main method to execute from command line
    public static void main(String data[]) {
        if (data.length < 2) {
            System.out.println("Usage: java UpdateFriend <name> <newNumber>");
            return;
        }

        try {
            String newName = data[0];
            long newNumber = Long.parseLong(data[1]);
            updateFriend(newName, newNumber);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format.");
        }
    }
}


package appcrud;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

class DeleteFriend {

    // Método para eliminar un contacto
    //El método recibe los datos de nombre y número a elimianr newName y newnumber
    public static String deleteFriend(String newName, long numberInput) {
        try {
            String nameNumberString;
            String name;
            long number;
            int index;

            // Using file pointer creating the file.
            //Se crea un objeto File que representa nuestro archivo "friendsContact.txt".
            File file = new File("friendsContact.txt");

            //Si el archivo no existe se crea uno nuevo con file.createNewFile()
            if (!file.exists()) {
                // Create a new file if not exists.
                file.createNewFile();
            }

            // Opening file in reading and write mode.
            //Con RandomAccessFile abrimos el archivo en modo lectura y escritura rw
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            boolean found = false;

            // Checking whether the name of contact exists.
            // getFilePointer() give the current offset
            // value from start of the file.
            //Se realiza la busqueda del nombre y número indicado
            while (raf.getFilePointer() < raf.length()) {
                // reading line from the file.
                nameNumberString = raf.readLine();

                // splitting the string to get name and number
                String[] lineSplit = nameNumberString.split("!");

                // separating name and number.
                name = lineSplit[0];
                number = Long.parseLong(lineSplit[1]);

                // if condition to find existence of record.
                //Ambas condiciones deben ser verdaderas para poder eliminar
                if (name.equals(newName) && number == numberInput) {
                    found = true;
                    break;
                }
            }

            // Delete the contact if record exists.
            if (found) {
                // Creating a temporary file with file pointer as tmpFile.
                File tmpFile = new File("temp.txt");

                // Opening this temporary file in ReadWrite Mode
                RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");

                // Set file pointer to start
                raf.seek(0);

                // Traversing the friendsContact.txt file
                //Creamos un archivo temporal en el que irán los contactos a excepción del que se desea eliminar
                while (raf.getFilePointer() < raf.length()) {
                    // Reading the contact from the file
                    nameNumberString = raf.readLine();

                    index = nameNumberString.indexOf('!');
                    name = nameNumberString.substring(0, index);

                    // Check if the fetched contact is the one to be deleted
                    if (name.equals(newName)) {
                        // Skip inserting this contact into the temporary file
                        continue;
                    }

                    // Add this contact in the temporary file
                    tmpraf.writeBytes(nameNumberString);
                    // Add the line separator in the temporary file
                    tmpraf.writeBytes(System.lineSeparator());
                }

                // Copy the updated content from the temporary file to original file.
                raf.seek(0);
                tmpraf.seek(0);

                while (tmpraf.getFilePointer() < tmpraf.length()) {
                    raf.writeBytes(tmpraf.readLine());
                    raf.writeBytes(System.lineSeparator());
                }

                // Set the length of the original file to that of temporary.
                raf.setLength(tmpraf.length());

                // Closing the resources.
                tmpraf.close();
                raf.close();

                // Deleting the temporary file
                tmpFile.delete();

                return "El contacto ha sido eliminado.";
            } else {
                raf.close();
                return "El contacto no existe.";
            }
        } catch (IOException ioe) {
            return "Error: " + ioe.getMessage();
        }
    }
}

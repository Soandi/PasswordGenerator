import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Engine{
    static String generationPassword() {
        String txt = "";
        String[] symbolsPass = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] symbolsSpecial = new String[]{"@", "%", "#", "&", "$", "!", "-", "_"};
        for (int i = 0; i < (int) mainWindow.getKolvo().getValue(); i++) {
            txt += symbolsPass[new Random().nextInt(symbolsPass.length)];
        }
        if (mainWindow.specialChars.isSelected()) {
            txt = txt.replace(String.valueOf(txt.charAt(new Random().nextInt(txt.length()))), symbolsSpecial[new Random().nextInt(symbolsSpecial.length)]);
        }

        mainWindow.tx1.setText(txt);
        return txt;
    }

   static void toClip() {
        String textOfPassword = mainWindow.tx1.getText();
        StringSelection stringSelection = new StringSelection(textOfPassword);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

   static void saveToFile() {
        String pathme = "src/mypasswords.txt";
        File newFile = new File(pathme);
        Path path = Paths.get(pathme);
        String line;
        ArrayList<String> chtenie = new ArrayList<>();

        if (!newFile.exists()) {
            try {
                Files.createFile(path);
            } catch (Exception e) {
                System.out.println("Ошибка создания файла");
            }
        }


        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathme));
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    chtenie.add(line.trim());
                }
            }

            if (mainWindow.descriptionTxt.isSelected()) {
                chtenie.add(mainWindow.tx2.getText() + " : " + mainWindow.tx1.getText());
            } else {
                chtenie.add(mainWindow.tx1.getText());
            }

            String txtToWrite = "";
            FileOutputStream writeTo = new FileOutputStream(pathme);
            for (String string : chtenie) {
                txtToWrite = string;
                txtToWrite += "\r\n";
                byte[] bufferMe = txtToWrite.getBytes();
                writeTo.write(bufferMe);
            }

            writeTo.close();
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");
        }


    }
}

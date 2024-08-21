import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

class mainWindow extends JFrame {
    JTextField tx1;
    JSpinner kolvo;
    JCheckBox specialChars;
    JTextField tx2;
    JCheckBox descriptionTxt;
    mainWindow(String name){
        super(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,280);
        setLayout(null);


        tx1 = new JTextField();
        tx1.setBounds(10,10,290,40);
        tx1.setFont(new Font("Arial",Font.PLAIN,18));
        tx1.setMargin(new Insets(0,10,0,10));

        tx2 = new JTextField();
        tx2.setBounds(tx1.getX(),tx1.getY()+tx1.getHeight()+10,tx1.getWidth(),tx1.getHeight());
        tx2.setFont(new Font("Arial",Font.PLAIN,18));
        tx2.setEnabled(false);

        kolvo = new JSpinner();
        kolvo.setBounds(310,10,60,40);
        kolvo.setValue(6);

        specialChars = new JCheckBox();
        specialChars.setBounds(10,110,300,30);
        specialChars.setText("Добавить спец.символы: (@%$&!-#)");
        specialChars.setFocusPainted(false);
        specialChars.setFont(new Font("Arial", Font.PLAIN,16));

        descriptionTxt = new JCheckBox();
        descriptionTxt.setBounds(10,specialChars.getY()+30,300,specialChars.getHeight());
        descriptionTxt.setText("Включить описание для пароля");
        descriptionTxt.setFocusPainted(false);
        descriptionTxt.setFont(new Font("Arial", Font.PLAIN,16));
        descriptionTxt.addChangeListener(e->{
            if(descriptionTxt.isSelected()) {
                tx2.setEnabled(true);
            }else{
                tx2.setEnabled(false);
            }
        });

        JButton saveTxt = new JButton("Сохранить");
        saveTxt.setBounds(270,190,100,40);
        saveTxt.setFocusPainted(false);
        saveTxt.addActionListener(e->{
            saveToFile();
        });

        JButton cop = new JButton("Скопировать в буфер");
        cop.setBounds(110,190,160,40);
        cop.setFocusPainted(false);
        cop.addActionListener(e->{
            toClip();
        });

        JButton genTxt = new JButton("Новый");
        genTxt.setBounds(10,190,100,40);
        genTxt.setFocusPainted(false);
        genTxt.addActionListener(e->{generationPassword();});

        generationPassword();


        add(specialChars);
        add(descriptionTxt);
        add(kolvo);
        add(tx1);
        add(tx2);
        add(genTxt);
        add(saveTxt);
        add(cop);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    String generationPassword(){
        String txt = "";
        String[] symbolsPass = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","1","2","3","4","5","6","7","8","9","0","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        String[] symbolsSpecial = new String[]{"@","%","#","&","$","!","-","_"};
        for(int i=0;i<(int)kolvo.getValue();i++){
            txt += symbolsPass[new Random().nextInt(symbolsPass.length)];
        }
        if(specialChars.isSelected()){
            txt = txt.replace(String.valueOf(txt.charAt(new Random().nextInt(txt.length()))),symbolsSpecial[new Random().nextInt(symbolsSpecial.length)]);
        }

        tx1.setText(txt);
        return txt;
    }

    void toClip(){
        String textOfPassword = tx1.getText();
        StringSelection stringSelection = new StringSelection(textOfPassword);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection,null);

    }

    void saveToFile(){
        String pathme = "src/mypasswords.txt";
        File newFile = new File(pathme);
        Path path = Paths.get(pathme);
        String line;
        ArrayList<String> chtenie = new ArrayList<>();

        if(!newFile.exists()){
            try {
                Files.createFile(path);
            }catch(Exception e){
                System.out.println("Ошибка создания файла");
            }
        }


        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathme));
            while((line = bufferedReader.readLine()) != null){
                if(!line.isEmpty()){
                    chtenie.add(line.trim());
                }
            }

            if(descriptionTxt.isSelected()) {
                chtenie.add(tx2.getText() + " : " + tx1.getText());
            }else{
                chtenie.add(tx1.getText());
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
        }catch(Exception e){
            System.out.println("Ошибка чтения файла");
        }



    }

}

public class GeneratorCode {
    public static void main(String[] args) {
        new mainWindow("Генератор паролей");
    }
}

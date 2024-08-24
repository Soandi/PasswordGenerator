import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class mainWindow extends JFrame {
    static JTextField tx1;
    private static JSpinner kolvo;
    static JCheckBox specialChars;
    static JTextField tx2;
    static JCheckBox descriptionTxt;
    private static String linkToPass = "src/mypasswords.txt";
    private static String linkToPassDefault = "src/mypasswords.txt";
    static String pathSettings = "src/settings.txt";
    static String authorInfo = "Разработчик: Чиркунов Александр. \r\nДата разработки: 22.08.2024г.\r\nВерсия программы: 1.0.0.2";
    static String filePassName = "/passwords.txt";

static void getLinkFromTxt(){
    String path = pathSettings;
    if(!tryFile(path)){
        makeFile(path);
    }
    try {
        BufferedReader buf = new BufferedReader(new FileReader(path));
        String xLine;
        while((xLine = buf.readLine()) != null){
            if(xLine.length() > 5) {
                if(!tryFile(cleanLinkToPass(xLine))) {
                    if(!makeFile(cleanLinkToPass(xLine))) {
                        xLine = linkToPassDefault;
                    }
                }
                setLinkToPass(xLine);
            }
        }
    }catch(Exception e){
        System.out.println("Не получается прочитать файл");
    }
}

static boolean tryFile(String path){
    File getFile = new File(path);
    if(getFile.exists()){
        return true;
    }else{
        return false;
    }
}

static boolean makeFile(String path){
    Path location = Paths.get(path);
    boolean success = false;
    try{
        Files.createFile(location);
        success = true;
    }catch(Exception e){
        System.out.println("Ошибка при создании файла");
        success = false;
    }
    return success;
}

public void toSettings(String path){
    String lastSymbolOfPath = String.valueOf(path.charAt(path.length()-1));
    String newLine = "";
    String tempPath = "";
    if(lastSymbolOfPath.equals("\\")){
        for(int i=0;i<path.length()-1;i++){
            newLine += path.charAt(i);
        }
        tempPath = cleanLinkToPass(newLine) + filePassName;
    }else {
        tempPath = cleanLinkToPass(path + filePassName);
    }



    if(!tryFile(tempPath)){
        if(!makeFile(tempPath)){
            JOptionPane.showMessageDialog(mainWindow.this,"Невозможно получить доступ к файлу");
        }
    }
    Path pat = Paths.get(pathSettings);
    byte[] buf = tempPath.getBytes();
    try {
        Files.write(pat, buf);
    }catch(Exception e) {
        JOptionPane.showMessageDialog(mainWindow.this,"Невозможно внести сохранить путь в файл настроек.");
    }
    getLinkFromTxt();
}


    static String getLinkPass(){
        return linkToPass;
    }

    static String cleanLinkToPass(String path){
        String templinkToPass = path;
        templinkToPass = templinkToPass.replace("\\","/");
        return templinkToPass;
    }

    static void setLinkToPass(String path){
        linkToPass = path;
    }

    static JSpinner getKolvo() {
        return kolvo;
    }

    mainWindow(String name) {
        super(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        getLinkFromTxt();

        JPanel mpanel = new JPanel();
        mpanel.setBorder(BorderFactory.createEtchedBorder());
        mpanel.setBounds(10,10,380,260);
        mpanel.setLayout(null);

        tx1 = new JTextField();
        tx1.setBounds(10, 10, 290, 40);
        tx1.setFont(new Font("Arial", Font.PLAIN, 18));
        tx1.setMargin(new Insets(0, 10, 0, 10));

        tx2 = new JTextField();
        tx2.setBounds(tx1.getX(), tx1.getY() + tx1.getHeight() + 10, tx1.getWidth(), tx1.getHeight());
        tx2.setFont(new Font("Arial", Font.PLAIN, 18));
        tx2.setEnabled(false);

        kolvo = new JSpinner();
        kolvo.setBounds(310, 10, 60, 40);
        kolvo.setValue(6);

        specialChars = new JCheckBox();
        specialChars.setBounds(10, 110, 300, 30);
        specialChars.setText("Добавить спец.символы: (@%$&!-#)");
        specialChars.setFocusPainted(false);
        specialChars.setFont(new Font("Arial", Font.PLAIN, 16));

        descriptionTxt = new JCheckBox();
        descriptionTxt.setBounds(10, specialChars.getY() + 30, 300, specialChars.getHeight());
        descriptionTxt.setText("Включить описание для пароля");
        descriptionTxt.setFocusPainted(false);
        descriptionTxt.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionTxt.addChangeListener(e -> {
            if (descriptionTxt.isSelected()) {
                tx2.setEnabled(true);
            } else {
                tx2.setEnabled(false);
            }
        });

        class innerWindow{
            innerWindow() {
                JFrame dopWin = new JFrame("Панель настроек");
                dopWin.setLayout(null);
                dopWin.setSize(400,200);
                dopWin.setResizable(false);

                JLabel textForPathSav = new JLabel("<html>Введите путь к папке, в которой <br/>хотите хранить файл паролей</html>");
                textForPathSav.setBounds(10,10,300,30);


                JTextField pathSav = new JTextField();
                pathSav.setBounds(10,textForPathSav.getY()+textForPathSav.getHeight()+15,300,40);
                pathSav.setMargin(new Insets(0,10,0,10));
                pathSav.setText(linkToPass);

                JFileChooser vibratFolder = new JFileChooser();
                vibratFolder.setAcceptAllFileFilterUsed(false);
                vibratFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                JButton chooseFolder = new JButton("Выбрать папку");
                chooseFolder.setBounds(pathSav.getX(),pathSav.getY()+pathSav.getHeight(),150,30);
                chooseFolder.setFocusPainted(false);
                chooseFolder.addActionListener(e -> {
                    try {
                        vibratFolder.showOpenDialog(null);
                        pathSav.setText(vibratFolder.getSelectedFile().toString());
                    }catch(Exception ev){
                        System.out.println("Файл не выбран");
                    }
                });

                JButton saveNewPathHere = new JButton("Сохранить");
                saveNewPathHere.setBounds(pathSav.getX()+(pathSav.getWidth()/2),pathSav.getY()+pathSav.getHeight(),150,30);
                saveNewPathHere.setFocusPainted(false);
                saveNewPathHere.addActionListener(e -> {
                    toSettings(pathSav.getText());
                    dopWin.dispose();
                    JOptionPane.showMessageDialog(mainWindow.this,"Успешно сохранено!","Сохранение",JOptionPane.INFORMATION_MESSAGE);
                });

                dopWin.add(chooseFolder);
                dopWin.add(saveNewPathHere);
                dopWin.add(textForPathSav);
                dopWin.add(pathSav);
                dopWin.setLocationRelativeTo(null);
                dopWin.setVisible(true);
            }
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu jmenu = new JMenu("Настройки");
        JMenu aboutMenu = new JMenu("Разработчик");
        jmenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    new innerWindow();
                }catch(NullPointerException E){
                    System.out.println("Окно настроек закрыто");
                }

            }
        });
        menuBar.add(jmenu);
        aboutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JOptionPane.showMessageDialog(mainWindow.this, authorInfo, "Информация об авторе", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBar.add(aboutMenu);
        add(menuBar,BorderLayout.NORTH);

        JButton saveTxt = new JButton("Сохранить");
        saveTxt.setBounds(270, 190, 100, 40);
        saveTxt.setFocusPainted(false);
        saveTxt.addActionListener(e -> {
            Engine.saveToFile();
        });

        JButton cop = new JButton("Скопировать в буфер");
        cop.setBounds(110, 190, 160, 40);
        cop.setFocusPainted(false);
        cop.addActionListener(e -> {
            Engine.toClip();
        });

        JButton genTxt = new JButton("Новый");
        genTxt.setBounds(10, 190, 100, 40);
        genTxt.setFocusPainted(false);
        genTxt.addActionListener(e -> {
            Engine.generationPassword();
        });

        Engine.generationPassword();



        mpanel.add(specialChars);
        mpanel.add(descriptionTxt);
        mpanel.add(kolvo);
        mpanel.add(tx1);
        mpanel.add(tx2);
        mpanel.add(genTxt);
        mpanel.add(saveTxt);
        mpanel.add(cop);
        add(mpanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}



import javax.swing.*;
import java.awt.*;

class mainWindow extends JFrame {
    static JTextField tx1;
    private static JSpinner kolvo;
    static JCheckBox specialChars;
    static JTextField tx2;
    static JCheckBox descriptionTxt;


    static JSpinner getKolvo() {
        return kolvo;
    }

    mainWindow(String name) {
        super(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 280);
        setLayout(null);


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
}


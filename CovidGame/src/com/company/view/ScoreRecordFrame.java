package com.company.view;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ScoreRecordFrame extends JFrame {


    JTextArea textArea;
    JLabel label;
    JButton recordButton;
    JPanel panel;
    GameFrame gameFrame;
    protected static final String FILE_ADDRESS = "C:\\Users\\EnesGuven\\Desktop\\adsfasd\\Records.txt";


    public ScoreRecordFrame(GameFrame gameFrame) {

        this.gameFrame = gameFrame;
        textArea = new JTextArea(1, 15);
        Font font = textArea.getFont();
        textArea.setFont(font.deriveFont((float) 20));
        recordButton = new JButton("SAVE");

        recordButton.addActionListener(actionEvent -> {
            gameFrame.setName(textArea.getText());
            recordRank();
            System.exit(0);
        });

        label = new JLabel("Enter your name : ");
        panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Save Score"));


        panel.add(label);
        panel.add(textArea);
        panel.add(recordButton, BorderLayout.CENTER);
        add(panel);

        setTitle("Save Record");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(321, 166);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void recordRank() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_ADDRESS, true));

            String[] words = gameFrame.data().split(" ");
            for (String word: words) {
                writer.write(word);
                writer.write(" ");
            }
            writer.newLine();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

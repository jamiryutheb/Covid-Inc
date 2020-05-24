package com.company.view;

import com.company.controller.RankRenderer;
import com.company.model.RankModel;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ScoreDisplayFrame extends JFrame {

    JList<String> jList;

    public ScoreDisplayFrame() {

        jList = new JList<>();
        jList.setModel(new RankModel(readObjectTRY()));
        jList.setCellRenderer(new RankRenderer());

        JButton button = new JButton("BACK");

        button.addActionListener(e -> {
            dispose();
            new MenuFrame();
        });

        setLayout(new BorderLayout());

        add(new JScrollPane(jList));
        add(button, BorderLayout.PAGE_END);
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    ArrayList<String> readObjectTRY() {

        ArrayList<String> arrayList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ScoreRecordFrame.FILE_ADDRESS), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                arrayList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (arrayList.size() == 0) arrayList.add("No Record Found.");
        return arrayList;
    }




}

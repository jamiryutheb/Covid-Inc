package com.company.view;

import com.company.model.Difficulty;
import com.company.controller.GameEngine;

import javax.swing.*;
import java.awt.*;


public class MenuFrame extends JFrame {

    GameEngine gameEngine;

    public MenuFrame() {


        gameEngine = new GameEngine();
        gameEngine.setMenuFrame(this);


        JPanel panel = new JPanel();
        JButton newgameButton = new JButton("New Game");
        JButton highScoresButton = new JButton("High Scores");
        JButton exitButton = new JButton("Exit");


        //  **************    BUTTONS    ************************
        newgameButton.addActionListener((actionEvent -> {
            dispose();
            new DiffMenu();
        }));
        highScoresButton.addActionListener(actionEvent -> {
            dispose();
            new ScoreDisplayFrame();
        });
        exitButton.addActionListener((actionEvent -> System.exit(0)));


        panel.setLayout(new GridLayout(3, 1));
        panel.add(newgameButton);
        panel.add(highScoresButton);
        panel.add(exitButton);

        add(panel);
        setTitle("Main Menu");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}

class DiffMenu extends JFrame {

    DiffMenu() {

        JButton b1 = new JButton("Hard");
        JButton b2 = new JButton("Medium");
        JButton b3 = new JButton("Easy");
        JPanel panel = new JPanel();
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);


        b1.addActionListener((actionEvent) ->
                {
                    setVisible(false);
                    new GameFrame(Difficulty.HARD);
                }
        );
        b2.addActionListener((actionEvent) ->
                {
                    setVisible(false);
                    new GameFrame(Difficulty.MEDIUM);
                }
        );
        b3.addActionListener((actionEvent) ->
                {
                    setVisible(false);
                    new GameFrame(Difficulty.EASY);
                }
        );

        panel.setLayout(new GridLayout(3, 1));
        add(panel);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


}
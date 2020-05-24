package com.company.view;

import com.company.model.Country;
import com.company.model.Difficulty;
import com.company.controller.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class GameFrame extends JFrame {

    JButton finishButton;
    JLabel timeLabel,
            scoreLabel,
            infectedLabel,
            deadLabel,
            recoveredLabel,
            infectedCountryLabel;
    static JPanel interactPanel;
    GamePanel gamePanel;
    GameEngine gameEngine;
    static Timer timer;
    LocalDate localDate = LocalDate.now();
    public Difficulty difficulty;
    public int timeNo = 0;
    String playerName = "";
    private static boolean running = true;
    public static long SCORE;

    private String IMAGE_PATH = "C:\\Users\\EnesGuven\\Desktop\\CovidGame\\world.gif";


    public static JPanel getInteractPane() {
        return interactPanel;
    }

    public GameFrame(Difficulty difficulty) {


        interactPanel = new JPanel();
        gamePanel = new GamePanel();

        interactPanel.setLayout(new GridLayout(4, 2));

        infectedLabel = new JLabel();
        infectedLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        infectedLabel.setOpaque(true);
        infectedLabel.setForeground(Color.RED);

        infectedCountryLabel = new JLabel();
        infectedCountryLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        infectedCountryLabel.setOpaque(true);
        infectedCountryLabel.setForeground(Color.RED);

        deadLabel = new JLabel();
        deadLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        deadLabel.setOpaque(true);
        deadLabel.setForeground(Color.RED);

        recoveredLabel = new JLabel();
        recoveredLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        recoveredLabel.setOpaque(true);
        recoveredLabel.setForeground(Color.RED);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 25));
        timeLabel.setOpaque(true);
        timeLabel.setForeground(Color.black);

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font(Font.SERIF, Font.ITALIC, 25));
        scoreLabel.setOpaque(true);
        scoreLabel.setForeground(Color.BLUE);

        this.difficulty = difficulty;
        gameEngine = new GameEngine();
        gameEngine.setGameFrame(this);
        gameEngine.setAction();

        // date counter
        timer = new Timer(1000, actionEvent -> {
            timeLabel.setText("DATE : " + localDate.getDayOfMonth() + "/" + localDate.getMonth()
                    + "/" + localDate.getYear() + " TIME PASSED :" + Long.toString(timeNo));
            localDate = localDate.plusDays(1);
            timeNo++;
        });
        timer.start();

        // score counter
        new Timer(400, actionEvent -> {
            scoreLabel.setText("SCORE : " + SCORE);

            SCORE = GameEngine.getCountryList().size() != 0 ?
                    (timeNo * Arrays.asList(Difficulty.values()).indexOf(this.difficulty) * 5) : timeNo + SCORE + GameEngine.getCountryList().size();

        }).start();

        // infected counter
        new Timer(400, actionEvent -> infectedLabel.setText("INFECTED : " + GameEngine.getTotalInfected())).start();

        // infected country counter
        new Timer(400, actionEvent -> infectedCountryLabel.setText("INFECTED COUNTRY NUMBER: " + GameEngine.getInfectedCountries() + " out of " + GameEngine.getCountryList().size())).start();

        // death counter
        new Timer(400, actionEvent -> deadLabel.setText("DEATH : " + GameEngine.getTotalDeath())).start();

        // recovered counter
        new Timer(400, actionEvent -> recoveredLabel.setText("RECOVERED : " + GameEngine.getTotalRecovered())).start();


        finishButton = new JButton("Finish");
        finishButton.addActionListener(actionEvent -> endGame());


        interactPanel.add(finishButton);
        interactPanel.add(timeLabel);
        interactPanel.add(scoreLabel);
        interactPanel.add(infectedLabel);
        interactPanel.add(recoveredLabel);
        interactPanel.add(deadLabel);
        interactPanel.add(infectedCountryLabel);


        interactPanel.setBorder(BorderFactory.createTitledBorder("Enter Info"));
        gamePanel.setBorder(BorderFactory.createTitledBorder("Game"));

        this.setLayout(new BorderLayout());
        add(interactPanel, BorderLayout.PAGE_START);
        add(gamePanel);


        setTitle("Coronavirus-Covid-19 Simulation");
        setSize(1360, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void endGame() {
        timer.stop();
        Thread.currentThread().interrupt();
        running = false;
        new ScoreRecordFrame(this);
    }


    public String data() {
        return "Player --> " + playerName + " | Rescue time : " + this.timeNo + " | Difficulty : " + this.difficulty + " | Score : " + SCORE;
    }

    public void setName(String playerName) {
        this.playerName = playerName;
    }


    public class GamePanel extends JPanel {

        private BufferedImage image;
        Hashtable<Point, Country> table;
        private int radius;
        private int DIVISOR = 2;

        public GamePanel() {
            table = new Hashtable<>();
            try {
                image = ImageIO.read(new File(IMAGE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }

            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    Point point = new Point(e.getX(), e.getY());
                    try {
                        Country country = gameEngine.infectionListener(JOptionPane.showInputDialog("Please enter country title "));
                        gameEngine.addTransportation(country, JOptionPane.showInputDialog("Enter transportation types for this country, Options :" + "\n" + "AIRLINE" + "\n" +
                                "VEHICLE\n" +
                                "SHIP\n" +
                                "BUS\n" +
                                "TRAIN"));


                        if (country != null) {
                            table.put(point, country);
                        }
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(gamePanel, "COUNTRY HAVE NOT BEEN INSERTED");
                    }
                }
            });

            new Thread(() -> {
                while (true) {
                    repaint();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // world picture
            Graphics2D g2d = (Graphics2D) g.create();
            if (image != null) g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            // draw point
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.red);

            for (Point point : table.keySet()) {

                if (radius < 100) {
                    if (table.get(point).getInfected() == 0) {
                        radius = 0;
                    } else {
                        radius = (table.get(point).getInfected() / DIVISOR) + 5;
                    }
                }
                g2.fillOval(point.x, point.y, radius, radius);
                DIVISOR += 1;
            }

        }
    }
}



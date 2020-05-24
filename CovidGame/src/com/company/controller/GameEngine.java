package com.company.controller;


import com.company.model.Country;
import com.company.model.Transportation;
import com.company.view.CountryListFrame;
import com.company.view.GameFrame;
import com.company.view.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

interface Upgrades {

    void decreaseExternalPossibility();
    void decreaseInternalSpread(Country country);
    void applyLockDown(Country country);
    void developUsefulMedic(Country country);
    void obligateMask(Country country);
    void donationFromBillioner(Country country);
    void pushPeopleToExercise(Country country);
    void improveOnlineShopping(Country country);
    void developUsefulVaccine(Country country);

}

public class GameEngine implements Upgrades {


    static JFrame gameFrame;
    static JFrame menuFrame;
    static ArrayList<Country> countryList = new ArrayList<>();
    private double countrySpreadRate;
    public static HashMap<Runnable, Thread> threadMap = new HashMap<>();
    private static final Random rand = new Random();
    JButton buttonUpgrade;
    private long SCORE_LIMIT = 500;

    public GameEngine() {

        countrySpreadRate = 0.75;


    }

    public static int getTotalInfected() {
        int total = 0;
        for (Country c : countryList) total += c.getInfected();
        return total;
    }

    public static int getTotalRecovered() {
        int total = 0;
        for (Country c : countryList) total += c.getRecovered();
        return total;
    }

    public static int getTotalDeath() {
        int total = 0;
        for (Country c : countryList) total += c.getDeath();
        return total;
    }

    public static int getInfectedCountries() {
        int counter = 0;
        for (Country c : countryList) {
            if (c.hasCase)
                counter++;
        }
        return counter;
    }


    public void setAction() {
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gameFrame.dispose();
                new MenuFrame();
                System.out.println("Interrupted");
            }
        };

        KeyStroke keyStroke1 = KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK);
        gameFrame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke1, "TRy");
        gameFrame.getRootPane().getActionMap().put("TRy", action);

    }

    public Country infectionListener(String cName) {


        for (Country c : countryList) if (c.getcName().equalsIgnoreCase(cName)) return null;

        Country country = new Country(cName);

        Thread thread = new Thread(country);

        threadMap.put(country, thread);
        thread.start();
        countryList.add(country);
        if (cName.equalsIgnoreCase("China")) {
            country.setInfected(2);
            setPandemicThread();
        }
        return country;
    }

    private synchronized void setPandemicThread() {
        Thread spreadThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (Math.random() < countrySpreadRate)
                        spreadOtherCountries();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        spreadThread.start();
    }

    public void setGameFrame(JFrame gameFrame) {
        GameEngine.gameFrame = gameFrame;
        buttonUpgrade = new JButton("Get Upgrade");

        new Thread(() -> buttonUpgrade.addActionListener(e -> {

            String[] choices = {"Decrease External Possibility ~ (500 SCORE POINT)",
                    "Decrease Internal Spread ~ (500 SCORE POINT)",
                    "Apply Lock down ~ (500 SCORE POINT)",
                    "Develop Useful Medicine ~ (500 SCORE POINT)",
                    "Develop Vaccine ~ (2000 SCORE POINT)",
                    "Encourage People to Work Out ~ (500 SCORE POINT)",
                    "Improve Online Shopping System ~ (500 SCORE POINT)",
                    "Make Obligation to Use Mask - (500 SCORE POINT)",
                    "Donation Made by Billioners (1000 SCORE POINT)",
            };

            String input = (String) JOptionPane.showInputDialog(null, "CHOOSE AN UPGRADE",
                    "The Choice", JOptionPane.QUESTION_MESSAGE, null,
                    choices,
                    choices[0]);
            Country c;
            CountryListFrame frameList;
            try {
                switch (input) {
                    case "Decrease External Possibility ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT)
                            decreaseExternalPossibility();
                        else
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        break;
                    case "Decrease Internal Spread ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                decreaseInternalSpread(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Apply Lock down ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                applyLockDown(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Develop Useful Medicine ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                developUsefulMedic(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Develop Vaccine ~ (2000 SCORE POINT)":
                        if (GameFrame.SCORE > 2000) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                developUsefulVaccine(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Encourage People to Work Out ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                pushPeopleToExercise(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Improve Online Shopping System ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                improveOnlineShopping(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Make Obligation to Use Mask ~ (500 SCORE POINT)":
                        if (GameFrame.SCORE > SCORE_LIMIT) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                obligateMask(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                    case "Donation Made by Billioners ~ (1000 SCORE POINT)":
                        if (GameFrame.SCORE > 1000) {
                            if (countryList.size() != 0) {
                                frameList = new CountryListFrame(countryList);
                                c = frameList.getCountry();
                                donationFromBillioner(c);
                            } else {
                                JOptionPane.showMessageDialog(gameFrame, "THERE IS NO COUNTRY TO SELECT!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(gameFrame, "YOU DO NOT HAVE ENOUGH SCORE POINTS!");
                        }
                        break;
                }
            } catch (NullPointerException ignored) {
            }
        })).start();

        GameFrame.getInteractPane().add(buttonUpgrade);

    }

    public void setMenuFrame(JFrame menuFrame) {
        GameEngine.menuFrame = menuFrame;
    }

    public static ArrayList<Country> getCountryList() {
        return countryList;
    }


    private void spreadOtherCountries() {
        //TODO infect the virus other countries with a random selection of country (using country array).
        if (countryList.size() != 0) {

            boolean process = true;

            while (process) {
                int index1 = (int) (Math.random() * countryList.size()),
                        index2 = (int) (Math.random() * countryList.size());
                Country tmp1 = countryList.get(index1);
                Country tmp2 = countryList.get(index2);

                if (tmp1.matchConnection(tmp2) && index1 != index2) {
                    tmp2.setInfected((int) (Math.random() * 2) - 1);
                    process = false;
                }
            }


        }
    }

    @Override
    public void decreaseExternalPossibility() {
        this.countrySpreadRate -= 0.10;
    }

    @Override
    public void decreaseInternalSpread(Country country) {
        country.INCREASE_RATE -= 10;
    }

    @Override
    public void applyLockDown(Country country) {
        country.setBorderStatusToFalse();
    }

    @Override
    public void developUsefulMedic(Country country) {
        country.RECOVER_RATE = +20;
    }

    @Override
    public void obligateMask(Country country) {
        country.INCREASE_RATE -= 5;
    }

    @Override
    public void donationFromBillioner(Country country) {
        SCORE_LIMIT = 300;
    }

    @Override
    public void pushPeopleToExercise(Country country) {
        country.RECOVER_RATE += 10;
    }

    @Override
    public void improveOnlineShopping(Country country) {
        country.INCREASE_RATE -= 5;
    }

    @Override
    public void developUsefulVaccine(Country country) {
        country.relativePossibilityR = 1;
    }


    public void addTransportation(Country country, String showInputDialog) {

        String[] words = showInputDialog.split("\\s+");

        for (int i = 0; i < words.length; i++) {

            words[i] = words[i].replaceAll("[^\\w]", "");
            try {
                country.getTransportationList().add(Transportation.valueOf(words[i].toUpperCase()));
            } catch (IllegalArgumentException | NullPointerException ignored) {
            }
        }


    }
}
package com.company.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Country implements Runnable {

    private final Random rand = new Random();
    public boolean borderON;
    public boolean hasCase;
    public int
            RECOVER_RATE,
            DEATH_RATE,
            INCREASE_RATE;
    public int
            infected,
            recovered,
            death,
            totalCase;
    public double relativePossibilityR;
    String cName;
    ArrayList<Transportation> transportationList;


    public Country(String cName) {

        this.totalCase = 0;
        this.cName = cName;
        this.hasCase = false;
        this.borderON = true;
        this.infected = 0;
        this.recovered = 0;
        this.death = 0;
        this.INCREASE_RATE = 30;
        this.RECOVER_RATE = 5;
        this.DEATH_RATE = 5;
        this.relativePossibilityR = 0.4;
        this.transportationList = new ArrayList<>();


    }


    public synchronized void setInfected(int infected) {
        this.totalCase = this.totalCase + infected;
        this.infected = infected;
        this.hasCase = true;
    }

    @Override
    public void run() {


        while (this.hasCase) {


            spread();
            recover();
            if (this.infected == 0) {
                this.hasCase = false;
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private synchronized void recover() {



        if (Math.random() < relativePossibilityR) {
            int recover = rand.nextInt(this.RECOVER_RATE);
            if (recover < this.infected) {
                this.recovered += recover;
                this.infected -= recover;
            }
        } else {
            int die = rand.nextInt(this.DEATH_RATE);
            if(die < this.infected ) {
                setDeath(die);
                this.infected -= die;
            }
        }
    }

    private synchronized void spread() {
        int spread = rand.nextInt(this.INCREASE_RATE);
        this.infected += spread;
        this.totalCase += spread;
        this.INCREASE_RATE += 5;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death += death;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public int getInfected() {
        return infected;
    }

    public synchronized ArrayList<Transportation> getTransportationList() {
        return transportationList;
    }


    public boolean matchConnection(Country tmp2) {
        boolean result = false;
        for (Transportation transportation : this.transportationList) {
            if (tmp2.getTransportationList().contains(transportation))
                result = true;
        }
        return result;
    }

    public void setBorderStatusToFalse() {
        this.borderON = false;
        this.INCREASE_RATE -= 10;
    }
}

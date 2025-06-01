package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Table extends JPanel {
    public static final int X = 250;
    public static final int Y = 150;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    private List<Philosoph> philosophs;
    private List<Fork> forks;


    public Table() {
        philosophs = new ArrayList<>();
        forks = new ArrayList<>();

        setBackground(Color.cyan);
        setLayout(null);
        setBounds(X, Y, WIDTH, HEIGHT);

        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        int radius = 180;
        int numberOfPhilosophers = 6;
        int angleStep = 360 / numberOfPhilosophers;
        int philosopherSize = 100;
        int forkWidth = 20;
        int forkHeight = 30;
        int forkRadius = radius - 40;

        for (int i = 0; i < numberOfPhilosophers; i++) {
            double forkAngle = Math.toRadians((i * angleStep + angleStep / 2.0) - 90);
            int forkX = (int) (centerX + forkRadius * Math.cos(forkAngle)) - forkWidth / 2;
            int forkY = (int) (centerY + forkRadius * Math.sin(forkAngle)) - forkHeight / 2;

            Fork f = new Fork(i, forkX, forkY, this);
            forks.add(f);
            this.add(f);
        }

        for (int i = 0; i < numberOfPhilosophers; i++) {
            double angle = Math.toRadians(i * angleStep - 90);
            int philX = (int) (centerX + radius * Math.cos(angle)) - philosopherSize / 2;
            int philY = (int) (centerY + radius * Math.sin(angle)) - philosopherSize / 2 + 20;

            int leftIndex = (i + numberOfPhilosophers - 1) % numberOfPhilosophers;
            int rightIndex = i;

            Fork leftFork = forks.get(leftIndex);
            Fork rightFork = forks.get(rightIndex);

            Philosoph p = new Philosoph(rightFork, leftFork, i, philX, philY, philosopherSize, philosopherSize, this);
            philosophs.add(p);
            this.add(p);
        }
    }

    public List<Philosoph> getPhilosophs() {
        return this.philosophs;
    }

    public List<Fork> getForks() {
        return this.forks;
    }
    public synchronized boolean requestToEat(Philosoph p) {
        Fork left = p.getForkLeft();
        Fork right = p.getForkRight();

        if (left.isAvailable() && right.isAvailable()) {
            left.setUnavailable();
            right.setUnavailable();

            left.setLeftForkNextPhilosoph(p);
            right.setRhightForkNextPhilosoph(p);

            return true;
        }
        return false;
    }
}



package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RemoveOne extends JButton {
    public static final int X = 100;
    public static final int Y = 100;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private int x;
    private int y;
    private int width;
    private int height;
    private JPanel perent;
    private Table table;
    private List<Fork> forks;
    private List<Philosoph> philosophs;


    public RemoveOne(Table table) {
        this.table = table;
        this.forks = table.getForks();
        this.philosophs = table.getPhilosophs();

        this.x = X;
        this.y = Y;
        this.width = WIDTH;
        this.height = HEIGHT;
        setBackground(Color.cyan);
        setLayout(null);
        setBounds(X, Y, WIDTH, HEIGHT);
        this.setText("remove");


        this.setContentAreaFilled(true);
        this.setBorderPainted(true);
        this.setFocusPainted(true);
        this.setVisible(true);
        //removeButton.addActionListener(onStartClicked);
        //removeButton.add(this);

//
//        this.addActionListener(e -> {
//            List<Philosoph> runningPhilosophers = new ArrayList<>();
//
//            for (int i = 0; i < this.philosophs.size(); i++) {
//                Philosoph p = philosophs.get(i);
//                if (p.isRunning()) {
//                    runningPhilosophers.add(p);
//                }
//            }
//
//            if (!runningPhilosophers.isEmpty()) {
//                int index = new Random().nextInt(runningPhilosophers.size());
//                Philosoph toStop = runningPhilosophers.get(index);
//                toStop.stop();
//
//
//                System.out.println("פילוסוף מספר " + toStop.getPhilosophNumber() + " נעצר");
//                System.out.println("מזלג מספר " + toStop.getForkRight().getNumber() + " נעצר");
//            }
//            repaint();
//        });


        this.addActionListener(e -> {
            List<Philosoph> runningPhilosophers = new ArrayList<>();

            for (int i = 0; i < this.philosophs.size(); i++) {
                Philosoph p = philosophs.get(i);
                if (p.isRunning()) {
                    runningPhilosophers.add(p);
                }
            }

            if (!runningPhilosophers.isEmpty()) {
                int index = new Random().nextInt(runningPhilosophers.size());
                Philosoph toStop = runningPhilosophers.get(index);
                philosophs.remove(toStop);
                toStop.stop();

                System.out.println("פילוסוף מספר " + toStop.getPhilosophNumber() + " נעצר");
                System.out.println("מזלג מספר " + toStop.getForkRight().getNumber() + " נעצר");
            }

            repaint();
        });
    }

    public void relinkForks() {
        int n = philosophs.size();

        for (int i = 0; i < n; i++) {
            Philosoph p = philosophs.get(i);

            Fork left = forks.get(i % forks.size());
            Fork right = forks.get((i + 1) % forks.size());


        }
    }
}






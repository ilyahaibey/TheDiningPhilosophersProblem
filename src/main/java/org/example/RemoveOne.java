package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class RemoveOne extends JButton {
    public static final int X = 100;
    public static final int Y = 100;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private int x;
    private int y;
    private int width ;
    private int height;
    private JPanel perent ;




    public RemoveOne(JPanel perent,List<Philosoph> philosophers) {
        this.perent = perent;
        this.x = X;
        this.y = Y;
        this.width = WIDTH;
        this.height = HEIGHT;
        setBackground(Color.cyan);
        setLayout(null);
        setBounds(X, Y, WIDTH, HEIGHT);


        this.setContentAreaFilled(true);
        this.setBorderPainted(true);
        this.setFocusPainted(true);
        this.setVisible(true);
        //removeButton.addActionListener(onStartClicked);
        //removeButton.add(this);
        perent.add(this);


//        this.addActionListener(e -> {
//            List<Philosoph> runningPhilosophers = new java.util.ArrayList<>();
//            for (Philosoph p : philosophers) {
//                if (p.isRunning()) {
//                    runningPhilosophers.add(p);
//                }
//            }
//            if(true);
//        repaint();
//
//
//
    }


}


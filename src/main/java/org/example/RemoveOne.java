package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RemoveOne extends JButton {
    public static final int X = 100;
    public static final int Y = 100;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;

    private final Table table;
    private final List<Fork> forks;
    private final List<Philosoph> philosophs;

    public RemoveOne(Table table) {
        this.table = table;
        this.forks = table.getForks();
        this.philosophs = table.getPhilosophs();

        setBackground(Color.cyan);
        setLayout(null);
        setBounds(X, Y, WIDTH, HEIGHT);
        setText("remove");
        setContentAreaFilled(true);
        setBorderPainted(true);
        setFocusPainted(true);
        setVisible(true);

        addActionListener(e -> removeRandomPhilosoph());
    }

    private void removeRandomPhilosoph() {
        List<Philosoph> running = new ArrayList<>();
        for (Philosoph p : philosophs) {
            if (p.isRunning()) running.add(p);
        }
        if (running.isEmpty()) return;

        
        Philosoph toRemove = running.get(new Random().nextInt(running.size()));
        toRemove.stop();


        SwingUtilities.invokeLater(() -> {
            table.remove(toRemove);
            philosophs.remove(toRemove);


            Fork forkToRemove = toRemove.getForkRight();
            forkToRemove.stop();
            table.remove(forkToRemove);
            forks.remove(forkToRemove);

            relinkForksAfterRemoval();

            table.revalidate();
            table.repaint();


            System.out.println("Removed philosopher #" + toRemove.getPhilosophNumber());
            for (int i = 0 ; i <philosophs.size() ; i ++) {
                philosophs.get(i).revalidate();
                System.out.println(philosophs.get(i).getWorkerThread().isAlive());
                System.out.println(philosophs.get(i).getState());
                philosophs.get(i).updateState(1);
                System.out.println("THIS PHILO " + philosophs.get(i).getPhilosophNumber() +
                        " right " + philosophs.get(i).getForkRight().getNumber() +
                        " left " + philosophs.get(i).getForkLeft().getNumber());
            }

        });
    }

    public void relinkForksAfterRemoval() {

        for (int i = 0; i < forks.size(); i++) {
            forks.get(i).setNumber(i);
        }

        int n = philosophs.size();
        for (int i = 0; i < n; i++) {
            Philosoph current = philosophs.get(i);
            Fork left = forks.get((i + forks.size() - 1) % forks.size());
            Fork right = forks.get(i);
            current.setForks(left, right);
            current.setPhilosophNumber(i);
        }
    }
}


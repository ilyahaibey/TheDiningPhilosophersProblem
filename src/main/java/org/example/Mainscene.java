package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Mainscene extends JPanel {
    public static final int HIGHT = 800;
    public static final int WIGHT = 1200;



    public Mainscene() {

        JFrame window = new JFrame("start screen");
        window.setSize(1200,800);
        window.setLocationRelativeTo(null);
        window.setLayout(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);


        setLayout(null);
        setBounds(0, 0, WIGHT, HIGHT);
        setBackground(Color.black);
        setVisible(true);

        Table table = new Table();
        this.add(table);
        RemoveOne removeOne = new RemoveOne(table );
        this.add(removeOne);

        window.add(this);
        window.setVisible(true);


    }
}

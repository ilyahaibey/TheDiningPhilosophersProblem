package org.example;

import javax.swing.*;
import java.awt.*;

public class Philosoph extends JPanel {
    public static final int EAT = 1;
    public static final int THINKING = 2;
    public static final int WAIT_FOR_RIGHT = 3;
    public static final int WAIT_FOR_LEFT = 4;

    private int philosophNumber;
    private int state;

    private Image philosophWait;
    private Image philosophEat;


    public Philosoph(int philosophNumber, int x, int y, int width, int height) {
        this.philosophNumber = philosophNumber;
        this.state = THINKING;

        // טוען תמונות לפי מצב
        philosophWait = new ImageIcon(getClass().getResource("/wait.png")).getImage();
        philosophEat = new ImageIcon(getClass().getResource("/eat.png")).getImage();


        setLayout(null);
        setFocusable(true);
        setBounds(x, y, width, height); // שימוש בפרמטרים שהועברו
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image current;
        if (state == EAT ){
            current = this.philosophEat;
        }
        else current = this.philosophWait ;

        g.drawImage(current, 0, 0, getWidth(), getHeight(), this);
    }

    public void setState(int newState) {
        this.state = newState;
        repaint();
    }

    public int getState() {
        return state;
    }

    public int getPhilosophNumber() {
        return philosophNumber;
    }
}
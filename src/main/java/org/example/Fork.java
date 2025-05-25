package org.example;

import javax.swing.*;
import java.awt.*;

public class Fork extends JPanel {
    private int number;
    private int x;
    private int y;
    private Philosoph heldBY;
    private boolean available;
    private Image fork1;
    private Image fork0;
    private Table table ;


    public Fork(int number, int x, int y , Table table) {
        this.table = table ;
        this.available = true;
        this.x = x;
        this.y = y;
        this.number = number;
        fork0 = new ImageIcon(getClass().getResource("/red.png")).getImage();
        fork1 = new ImageIcon(getClass().getResource("/grey.png")).getImage();
        setLayout(null);
        setFocusable(true);
        setBounds(x, y, 50, 50); // שימוש בפרמטרים שהועברו
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image current;
        if (this.available) {
            current = this.fork1;
        } else current = this.fork0;

        g.drawImage(current, 0, 0, getWidth(), getHeight(), this);
    }

    public boolean isAvailable() {
        return available;

    }

    public void setUnavailable() {
        available = false;
        repaint();
    }

    public void setAvailable() {
        available = true;
        repaint();
    }
    public void setLeftForkNextPhilosoph(Philosoph philosoph){

         int philosophX = philosoph.getX();
        int philosophY = philosoph.getY()+30;
        setBounds(philosophX , philosophY , getWidth() ,getHeight());
        repaint();

    }
    public void setRhightForkNextPhilosoph(Philosoph philosoph){

        int philosophX = philosoph.getX()+50;
        int philosophY = philosoph.getY()+30;
        setBounds(philosophX , philosophY , getWidth() ,getHeight());
        repaint();

    }
    public void setForkToNormal(){
        setBounds(x ,y ,getWidth() , getHeight());
        repaint();
    }
    public int getNumber(){
       return this.number;
    }

    public void stop(){
        this.available = false ;
        setVisible(false);
        table.remove(this);

    }
    public void setNumber(int newIndex){
        this.number = newIndex ;
    }

}



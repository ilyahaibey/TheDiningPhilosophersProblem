package org.example;

import javax.swing.*;
import java.awt.*;

public class Fork extends JPanel {
    private int number;
    private int x ;
    private int y ;
    private Philosoph heldBY;
    private boolean available ;
    private Image fork1;
    private Image fork0;;
    private Image philosophWait;



    public Fork(int number,int x , int y ){
        this.available = true ;
        this.x = x ;
        this.y = y ;
        this.number = number ;
        fork0 = new ImageIcon(getClass().getResource("/red.png")).getImage();
        fork1 = new ImageIcon(getClass().getResource("/grey.png")).getImage();
        setLayout(null);
        setFocusable(true);
        setBounds(x,y , 50,50); // שימוש בפרמטרים שהועברו
        setOpaque(false);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image current;
        if (this.available){
            current=this.fork1;
        }
        else current = this.fork0;

        g.drawImage(current, 0, 0, getWidth(), getHeight(), this);
    }
    public void setAvailable(){
        if (!this.available) {
            this.available = true ;
        } else {
            this.available = false ;
        }
    }
    private int getNumber(){
        return this.number;
    }
}



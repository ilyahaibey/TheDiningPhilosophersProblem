package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Philosoph extends JPanel {
    public static final int EAT = 1;
    public static final int THINKING = 2;
    public static final int WAIT_FOR_RIGHT = 3;
    public static final int WAIT_FOR_LEFT = 4;

    private JPanel statePanel;
    private int eatCount = 0;

    private int philosophNumber;
    private int state;
    private final Fork forkRight;
    private Fork forkLeft;

    private Image philosophWait;
    private Image philosophEat;

    public Philosoph(Fork left, Fork right, int philosophNumber, int x, int y, int width, int height) {
        this.forkRight = right;
        this.forkLeft = left;
        this.philosophNumber = philosophNumber;
        this.state = THINKING;

        philosophWait = new ImageIcon(getClass().getResource("/wait.png")).getImage();
        philosophEat = new ImageIcon(getClass().getResource("/eat.png")).getImage();

        setLayout(null);
        setFocusable(true);
        setBounds(x, y, width, height);
        setOpaque(false);
        createStatePanel();
        //startLogic();
        cheakLogic();

        //System.out.println(String.valueOf(this.philosophNumber)+String.valueOf(this.forkLeft.getNumber())+String.valueOf(this.forkRight.getNumber()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // רקע

        Image current;
        if (state == EAT) {
            current = philosophEat;
        } else {
            current = philosophWait;
        }

        g.drawImage(current, 0, 0, getWidth(), getHeight(), this);
    }

    private void createStatePanel() {
        statePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.PLAIN, 12));
                String status = "";
                switch (state) {
                    case EAT:
                        status = "eating";
                        break;
                    case THINKING:
                        status = "thinking";
                        break;
                    case WAIT_FOR_LEFT:
                        status = "waiting left";
                        break;
                    case WAIT_FOR_RIGHT:
                        status = "waiting right";
                        break;
                    default:
                        status = "";
                }
                ;

                g.drawString(status, 0, 10);
                g.drawString("Ate " + eatCount + " times", 0, 22);
            }
        };

        statePanel.setBounds(0, 0, 100, 30);
        statePanel.setOpaque(false);
        this.add(statePanel);
    }

    public void setState(int newState) {
        this.state = newState;
        repaint();
        statePanel.repaint();
    }

    public int getState() {
        return state;
    }

    public int getPhilosophNumber() {
        return philosophNumber;
    }

    public void startLogic() {
        new Thread(() -> {
            try {
                Random rand = new Random();
                while (true) {
                    setState(THINKING);
                    Thread.sleep(rand.nextInt(100));

                    boolean ate = false;
                    while (!ate) {
                        synchronized (forkLeft) {
                            if (!forkLeft.isAvailable()) {
                                setState(WAIT_FOR_LEFT);
                                Thread.sleep(100);
                                continue;
                            }
                            forkLeft.setLeftForkNextPhilosoph(this);
                            forkLeft.setUnavailable();
                            Thread.sleep(rand.nextInt(1000));


                            synchronized (forkRight) {
                                if (!forkRight.isAvailable()) {
                                    setState(WAIT_FOR_RIGHT);
                                    forkLeft.setAvailable();
                                    Thread.sleep(100);
                                    continue;
                                }
                                setState(EAT);
                                forkRight.setRhightForkNextPhilosoph(this);
                                forkRight.setUnavailable();


                                eatCount++;
                                Thread.sleep(rand.nextInt(100));

                                // שחרור
                                forkLeft.setAvailable();
                                forkLeft.setForkToNormal();
                                forkRight.setAvailable();
                                forkRight.setForkToNormal();

                                ate = true;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public Fork getForkRight(){
        return forkRight ;
    }
    public Fork getForkLeft(){
        return forkLeft ;
    }
    public void cheakLogic() {
        new Thread(() -> {
            try {
                Random rand = new Random();
                while (true) {
                    setState(THINKING);
                    Thread.sleep(rand.nextInt(1100));

                    boolean ate = false;
                    while (!ate) {
                            if (!forkLeft.isAvailable()) {
                                setState(WAIT_FOR_LEFT);
                                Thread.sleep(100);
                                continue;
                            }
                            forkLeft.setLeftForkNextPhilosoph(this);
                            forkLeft.setUnavailable();
                            Thread.sleep(rand.nextInt(1000));


                                if (!forkRight.isAvailable()) {
                                    setState(WAIT_FOR_RIGHT);
                                    forkLeft.setAvailable();
                                    Thread.sleep(100);
                                    continue;
                                }
                                setState(EAT);
                                forkRight.setRhightForkNextPhilosoph(this);
                                forkRight.setUnavailable();


                                eatCount++;
                                Thread.sleep(rand.nextInt(1100));

                                // שחרור
                                forkLeft.setAvailable();
                                forkLeft.setForkToNormal();
                                forkRight.setAvailable();
                                forkRight.setForkToNormal();

                                ate = true;


                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}



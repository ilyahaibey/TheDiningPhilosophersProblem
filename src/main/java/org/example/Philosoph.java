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
    private boolean running = true; //שייך לעצירת הפילוסוף

    private Image philosophWait;
    private Image philosophEat;
    private Table table;


    public Philosoph(Fork left, Fork right, int philosophNumber, int x, int y, int width, int height, Table table) {
        this.table = table;
        this.forkRight = right;
        this.forkLeft = left;
        this.philosophNumber = philosophNumber;
        this.state = THINKING;

        philosophWait = new ImageIcon(getClass().getResource("/wait.png")).getImage();
        philosophEat = new ImageIcon(getClass().getResource("/eat.png")).getImage();

        setLayout(null);
        setFocusable(true);
        setBounds(x, y-20, width, height+40 );
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

        g.drawImage(current, 0, 20, getWidth(), 100, this);
    }

    public void stop() {
        this.running = false;
        setVisible(false);
        this.table.remove(this);
        forkRight.stop();

    }

    boolean isRunning() {
        return running;
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

                g.drawString(status, 5, 11);
                g.drawString("Ate " + eatCount + " times", 0, 22);
            }
        };

        statePanel.setBounds(0, 0, 120, 40);
        statePanel.setOpaque(false);
        this.add(statePanel);
        getBounds();
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

    public Fork getForkRight() {
        return forkRight;
    }

    public Fork getForkLeft() {
        return forkLeft;
    }

    public void cheakLogic() {
        new Thread(() -> {
            try {
                Random rand = new Random();
                while (running) {
                    setState(THINKING);
                    Thread.sleep(rand.nextInt(10));

                    boolean ate = false;
                    while (!ate) {
                        if (!forkLeft.isAvailable()) {
                            setState(WAIT_FOR_LEFT);
                            Thread.sleep(10);
                            continue;

                        }
                        forkLeft.setLeftForkNextPhilosoph(this);
                        forkLeft.setUnavailable();
                        Thread.sleep(rand.nextInt(10));


                        if (!forkRight.isAvailable()) {
                            setState(WAIT_FOR_RIGHT);
                            forkLeft.setAvailable();
                            Thread.sleep(10);
                            continue;
                        }
                        setState(EAT);
                        forkRight.setRhightForkNextPhilosoph(this);
                        forkRight.setUnavailable();


                        eatCount++;
                        System.out.println(this.philosophNumber+"philo");
                        System.out.println(this.forkRight.getNumber()+"right");
                        System.out.println(this.forkLeft.getNumber()+"left");
                        Thread.sleep(rand.nextInt(5000));

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





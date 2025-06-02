package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Philosoph extends JPanel {
    public static final int EAT = 1;
    public static final int THINKING = 2;
    public static final int WAIT_FOR_RIGHT = 3;
    public static final int WAIT_FOR_LEFT = 4;
    private static final int HUNGER_THRESHOLD = 10;

    private JPanel statePanel;
    private int eatCount = 0;

    private int philosophNumber;
    private int state;
    private Fork forkRight;
    private Fork forkLeft;
    private volatile boolean running = true;

    private int hungerCount = 10;
    private Thread workerThread;

    private Image philosophWait;
    private Image philosophEat;
    private Table table;

    public Philosoph(Fork left, Fork right, int philosophNumber,
                     int x, int y, int width, int height, Table table) {
        this.table = table;
        this.forkRight = right;
        this.forkLeft = left;
        this.philosophNumber = philosophNumber;
        this.state = THINKING;

        philosophWait = new ImageIcon(getClass().getResource("/wait.png")).getImage();
        philosophEat = new ImageIcon(getClass().getResource("/eat.png")).getImage();

        setLayout(null);
        setFocusable(true);
        setBounds(x, y - 20, width, height + 40);
        setOpaque(false);
        createStatePanel();

        workerThread = new Thread(this::runLogic, "Philosoph-" + philosophNumber);
        workerThread.start();


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image current = (state == EAT) ? philosophEat : philosophWait;
        g.drawImage(current, 0, 20, getWidth(), 100, this);
    }

    public void stop() {
        running = false;
        if (workerThread != null) {
            workerThread.interrupt();

            try {
                workerThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            table.remove(this);
            table.revalidate();
            table.repaint();
        });

        Fork forkToRemove = this.forkRight;
        forkToRemove.stop();
        SwingUtilities.invokeLater(() -> {
            table.remove(forkToRemove);
            table.getForks().remove(forkToRemove);
            table.revalidate();
            table.repaint();
        });
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
                String status;

                if (state == EAT) {
                    status = "eating";
                } else if (state == THINKING) {
                    status = "thinking";
                } else if (state == WAIT_FOR_LEFT) {
                    status = "waiting left";
                } else if (state == WAIT_FOR_RIGHT) {
                    status = "waiting right";
                } else {
                    status = "";
                }

                g.drawString(status, 5, 11);
                g.drawString("Ate " + eatCount + " times", 0, 22);
            }
        };
        statePanel.setBounds(0, 0, 120, 40);
        statePanel.setOpaque(false);
        add(statePanel);
    }

    private void runLogic() {
        Random rand = new Random();
        try {
            while (running) {
                updateState(THINKING);
                Thread.sleep(rand.nextInt(10));

                boolean ate = false;
                while (!ate && running) {
                    if (table.requestToEat(this)) {
                        updateState(EAT);
                        eatCount++;
                        hungerCount = 0;
                        Thread.sleep(rand.nextInt(10));

                        synchronized (forkLeft) {
                            forkLeft.setAvailable();
                            forkLeft.setForkToNormal();
                        }
                        synchronized (forkRight) {
                            forkRight.setAvailable();
                            forkRight.setForkToNormal();
                        }
                        ate = true;

                    } else {
                        hungerCount++;
                        updateState(THINKING);
                        Thread.sleep(rand.nextInt(1));
                    }
                }
            }
        } catch (InterruptedException e) {

        }
    }

    public void updateState(int newState) {
        this.state = newState;
        SwingUtilities.invokeLater(() -> {
            repaint();
            statePanel.repaint();
        });
    }

    public Fork getForkRight() {
        return forkRight;
    }

    public Fork getForkLeft() {
        return forkLeft;
    }

    public void setForks(Fork left, Fork right) {
        this.forkLeft = left;
        this.forkRight = right;
    }

    public void setPhilosophNumber(int number) {
        this.philosophNumber = number;
    }

    public int getHungerCount() {
        return hungerCount;
    }
}


package lab7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel {
    private ArrayList<Ball> listOfBalls;
    private int size = 20;
    private Timer timer;
    private final int DELAY = 16;

    // dla 30fps -> 1s/30 = 0,033s
    public Panel() {
        listOfBalls = new ArrayList<>();
        setBackground(Color.BLACK);
        addMouseListener(new Event());
        addMouseMotionListener(new Event());
        addMouseWheelListener(new Event());
        timer = new Timer(DELAY, new Event());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Ball k : listOfBalls) {
            g.setColor(k.color);
            g.drawOval(k.x, k.y, k.size, k.size);
        }
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(listOfBalls.size()), 40, 40);
    }

    private class Event implements MouseListener, MouseMotionListener, MouseWheelListener,
            ActionListener {

        @Override
        public void mousePressed(MouseEvent e) {
            listOfBalls.add(new Ball(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            listOfBalls.add(new Ball(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            timer.start();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            timer.stop();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            size += e.getWheelRotation();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Ball k : listOfBalls) {
                k.update();
                k.ifWall();
                k.bounce(listOfBalls);
            }
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            listOfBalls.add(new Ball(e.getX(), e.getY(), size));
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            listOfBalls.add(new Ball(e.getX(), e.getY(), size));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
        }
    }

    private class Ball {
        public int x, y, size, xspeed, yspeed;
        public float r;
        public Color color;
        private final int MAX_SPEED = 10;

        public Ball(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            r = (float) size / 2;
            color = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
            while(true)
            {
                xspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
                if(xspeed!=0)
                break;
            }
            while(true)
            {
                yspeed = (int) (Math.random() * MAX_SPEED * 2 - MAX_SPEED);
                if(yspeed!=0)
                break;
            }

        }

        public void change(Ball k) {
            if (this.equals(k) == false) {
                int kx = xspeed, ky = yspeed;
                xspeed = k.xspeed;
                yspeed = k.yspeed;
                k.xspeed = kx;
                k.yspeed = ky;
            }
        }

        public void ifWall() {
            if (x - size / 2 <= 0 || x >= getWidth() - size / 2) {
                xspeed = -xspeed;
            }
            if (y - size / 2 <= 0 || y >= getHeight() - size / 2) {
                yspeed = -yspeed;
            }
        }

        public void outEdge() {
            if (x - size / 2 <= 0)
                x = size;
            if (x >= getWidth() - size / 2)
                x = getWidth() - size;
            if (y - size / 2 <= 0)
                y = size;
            if (y >= getHeight() - size / 2)
                y = getHeight() - size;
        }

        public void update() {
            x += xspeed;
            y += yspeed;
        }

        public void bounce(ArrayList<Ball> listOfBalls) {
            for (Ball k : listOfBalls) {
                float distance = (float) Math.sqrt((Math.pow((x - k.x), 2) + Math.pow((y - k.y), 2)));
                if (distance <= (r + k.r)) {
                    czyBallwkuli(k);
                }
            }
        }

        public void czyBallwkuli(Ball k) {
            float distance = (float) Math.sqrt((Math.pow((x - k.x), 2) + Math.pow((y - k.y), 2)));
            float w = (-distance + (r + k.r) + 4) / 2;
            if (this.equals(k) == false) {
                if ((95 / 100 * (r + k.r)) < distance) {
                    int kx = xspeed, ky = yspeed;

                    if (x - k.x < 0) {
                        k.x += w;
                        x -= w;
                    }
                    if (x - k.x > 0) {
                        k.x -= w;
                        x += w;
                    }
                    if (y - k.y < 0) {
                        k.y += w;
                        y -= w;
                    }
                    if (y - k.y > 0) {
                        k.y -= w;
                        y += w;
                    }
                }
                change(k);
                ifWall();
                k.ifWall();
                outEdge();
                k.outEdge();
            }
        }
    }
}
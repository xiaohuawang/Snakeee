import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {

    private boolean flag = true;
    private int score = 0;
    public static final int ROWS = 20;
    public static final int COLUMNS = 20;
    public static final int BLOCK_SIZE = 20;
    Snake snake = new Snake(this);
    Egg egg = new Egg();
    Image offscreenImage = null;

    @Override
    public void paint(Graphics g) {
        System.out.println("paint");
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        for (int i = 1; i < ROWS; i++) {
            g.setColor(Color.BLACK);
            g.drawLine(0, BLOCK_SIZE * i, BLOCK_SIZE * COLUMNS, BLOCK_SIZE * i);
        }

        for (int i = 1; i < COLUMNS; i++) {
            g.setColor(Color.BLACK);
            g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
        }
//        egg.appearAgain();

        // draw the score
        g.setColor(Color.yellow);
        g.drawString("score= "+score,10,10);

        snake.eat(egg);
        snake.draw(g);
        egg.draw(g);
        g.setColor(c);
    }

//    @Override
//    public void update(Graphics g) {
//        if (offscreenImage == null)
//            offscreenImage = this.createImage(COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
//        Graphics gOff = offscreenImage.getGraphics();
//        paint(gOff);
//        g.drawImage(offscreenImage, 0, 0, null);
//    }

    public void stop(){
        flag = false;
    }


    public void launch() {
        // set the launch location
        this.setLocation(500, 200);
        this.setSize(COLUMNS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setVisible(true);

        KeyMonitor l = new KeyMonitor();
        addKeyListener(l);
        new Thread(new PaintThread()).start();
    }

    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("key pressed!!!");
            snake.keyPressed(e);
        }
    }

    // start a new thread
    private class PaintThread implements Runnable {

        int i = 0;

        @Override
        public void run() {
            while (flag) {
                try {
                    repaint();
                    System.out.println(i++);
                    System.out.println();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

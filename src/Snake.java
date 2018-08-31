import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {

    private Yard yard;
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    private Node node = new Node(15, 15, Dir.UP);

    public Snake(Yard yard) {
        head = node;
        tail = node;
        size = 1;
        this.yard = yard;
    }

    private class Node {
        int width = Yard.BLOCK_SIZE;
        int height = Yard.BLOCK_SIZE;
        int row;
        int col;
        Dir dir;
        Node next;
        Node pre;

        public Node(int row, int col, Dir dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
        }

        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.blue);
            g.fillRect(row * Yard.BLOCK_SIZE, col * Yard.BLOCK_SIZE, width, height);
            g.setColor(c);
        }
    }

    // the snake will move
    private void move() {
        System.out.println("current dir= " + head.dir);
        addToHead();
        deleteFromTail();
    }

    // delete node from tail
    private void deleteFromTail() {
        if (size == 0)
            return;
        else {
            tail = tail.pre;
            tail.next = null;
            size--;
        }
    }

    // add node to the head
    private void addToHead() {
        Node currentNode = null;
        switch (head.dir) {
            case LEFT:
                currentNode = new Node(head.row - 1, head.col, head.dir);
                break;
            case RIGHT:
                currentNode = new Node(head.row + 1, head.col, head.dir);
                break;
            case UP:
                currentNode = new Node(head.row, head.col - 1, head.dir);
                break;
            case DOWN:
                currentNode = new Node(head.row, head.col + 1, head.dir);
                break;
        }
        currentNode.next = head;
        head.pre = currentNode;
        head = currentNode;
        size++;
    }

    public void draw(Graphics g) {
        if (size <= 0)
            return;
        checkDeath();
        move();
        node = head;
        System.out.println("size= " + size);
        // draw the snake
        while (node != null) {
            node.draw(g);
            // addToHead();
            node = node.next;
        }
    }

    private void checkDeath() {
        if (head.row < 1 || head.row >= Yard.ROWS || head.col < 1 || head.col >= Yard.COLUMNS) {
            System.out.println("--- game over ---");
            System.exit(0);
//            yard.stop();
        }

//        snake zij
//        for (Node n = head.next; n != null; n=n.next) {
//            if(head.row==n.row&&head.col==n.col){
//                System.exit(0);
//            }
//        }
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("key pressed detected");
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                if (head.dir == Dir.RIGHT)
                    return;
                head.dir = Dir.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                if (head.dir == Dir.LEFT)
                    return;
                head.dir = Dir.RIGHT;
                break;
            case KeyEvent.VK_UP:
                if (head.dir == Dir.DOWN)
                    return;
                head.dir = Dir.UP;
                break;
            case KeyEvent.VK_DOWN:
                if (head.dir == Dir.UP)
                    return;
                head.dir = Dir.DOWN;
                break;
            case KeyEvent.VK_ESCAPE:
                System.out.println("--- game ended ---");
                System.exit(0);
                break;
//            case KeyEvent.VK_PAUSE:
        }
    }

    private Rectangle getRect() {
        return new Rectangle(head.col * Yard.BLOCK_SIZE, head.row * Yard.BLOCK_SIZE, head.width, head.height);
    }

    public void eat(Egg e) {
        System.out.println("eat");
//        System.out.println("this rec= " + this.getRect());
//        System.out.println("egg rec= " + e.getRect());
        if (this.getRect().intersects(e.getRect())) {
            System.out.println("--------");
            e.appearAgain();
            addToHead();
            size++;
        }
    }

}

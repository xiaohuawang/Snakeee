import java.awt.*;
import java.util.Random;

public class Egg {

    private int row;
    private int column;
    private int width = Yard.BLOCK_SIZE;
    private int height = Yard.BLOCK_SIZE;
    private static Random r = new Random();
    // set default color
    private Color color = Color.green;

    public Egg(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Egg() {
        this(r.nextInt(Yard.ROWS - 2), r.nextInt(Yard.COLUMNS - 2));
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        // swith color
        if (color == Color.green)
            color = Color.yellow;
        else
            color = Color.green;
        g.setColor(color);
        g.fillOval(Yard.BLOCK_SIZE * row, Yard.BLOCK_SIZE * column, width, height);
        g.setColor(c);
    }

    public void appearAgain() {
        this.row = r.nextInt(Yard.ROWS - 1);
        this.column = r.nextInt(Yard.COLUMNS - 1);
        System.out.println("row= " + this.row);
        System.out.println("col= " + this.column);
    }

    public Rectangle getRect() {
        return new Rectangle(this.column * Yard.BLOCK_SIZE, this.row * Yard.BLOCK_SIZE, this.width, this.height);
    }
}

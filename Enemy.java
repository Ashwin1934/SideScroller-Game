import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy extends Block {

    int row, col;
    String direction;
    boolean captured;

    public Enemy(int x, int y, BufferedImage image, int row, int col) {
        super(x, y, image);
        direction = "right";
        this.row=row;
        this.col=col;
    }

    public boolean isCaptured()
    {
        return captured;
    }

    public void capture()
    {
        captured = true;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), 150, 100);
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public void setRow(int row)
    {
        this.row=row;
    }

    public void setCol(int col)
    {
        this.col=col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }
    
}
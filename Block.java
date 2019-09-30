import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Block
{
    private int x, y;
    private BufferedImage image;

    public Block(int x, int y, BufferedImage image)
    {
        this.x=x;
        this.y=y;
        this.image=image;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(x, y, 150, 50);
    }

    public void shiftX(int change)
    {
        x += change;
    }

    public static Block[][] moveArray(Block[][] map, boolean right, boolean left)
    {
        for(Block[] r: map)
        {
            for(Block c: r)
            {
                if(c != null)
                {
                    if(right)
                    {
                        c.shiftX(-5);    
                    }
                    if(left)
                    {
                        c.shiftX(5);
                    }
                }
            }
        }
        return map;
    }


}
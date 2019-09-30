import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Hero
{

    private int x;
    private int y;
    private int height;
    private int counter;
    private BufferedImage[] right;
    private BufferedImage[] left;
    private boolean jumping;
    private boolean moving;

    public Hero(int x, int y, BufferedImage[] right, BufferedImage[] left)
    {
        this.x=x;
        this.y=y;
        this.right=right;
        this.left = left;
        counter = 0;
    }

    public boolean getIfMoving()
    {
        return moving;
    }

    public void jump()
    {
        jumping = true;
        moving = true;
        height = y - 200;
    }

    public void gravity(Block[][] arr)
    {
        counter++;

        if(counter == 9)
            counter = 0;

        if(jumping && canMoveUp(arr))
        {
            y-=20;
            if(y == height)
                jumping = false;
            return;
        }

        if(canMoveDown(arr))
        {
            y+=20;
        }
        else
        {
            moving = false;
        }
    }

    public boolean canMoveUp(Block[][] arr)
    {
        Rectangle up = new Rectangle(x, y - 20, right[0].getWidth(), right[0].getHeight());
        for(int r = 0; r < arr.length; r++)
        {
            for(int c = 0; c < arr[0].length; c++)
            {
                if(arr[r][c] != null && !(arr[r][c] instanceof Enemy) && arr[r][c].getRectangle().intersects(up))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveDown(Block[][] arr)
    {
        Rectangle down = new Rectangle(x, y + 20, right[0].getWidth(), right[0].getHeight());
        for(int r = 0; r < arr.length; r++)
        {
            for(int c = 0; c < arr[0].length; c++)
            {
                if(arr[r][c] != null && !(arr[r][c] instanceof Enemy) &&  arr[r][c].getRectangle().intersects(down))
                {
                    return false;
                }
            }
        }
        return true;       
    }

    public boolean canMoveRight(Block[][] arr)
    {
        Rectangle rect = new Rectangle(x + 5, y, right[0].getWidth(), right[0].getHeight());
        for(int r = 0; r < arr.length; r++)
        {
            for(int c = 0; c < arr[0].length; c++)
            {
                if(arr[r][c] != null && !(arr[r][c] instanceof Enemy) && arr[r][c].getRectangle().intersects(rect))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMoveLeft(Block[][] arr)
    {
        Rectangle rect = new Rectangle(x - 5, y, right[0].getWidth(), right[0].getHeight());
        for(int r = 0; r < arr.length; r++)
        {
            for(int c = 0; c < arr[0].length; c++)
            {
                if(arr[r][c] != null && !(arr[r][c] instanceof Enemy) && arr[r][c].getRectangle().intersects(rect))
                {
                    return false;
                }
            }
        }
        return true;
        
    }

    public Rectangle getRectangle()
    {
        return new Rectangle(x, y, right[0].getWidth(), right[0].getHeight());
    }

    public BufferedImage getImage(String direction)
    {
        if(direction.equals("right"))
        {
            return right[counter];
        }
        if(direction.equals("left"))
        {
            return left[counter];
        }
        return right[0];
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }
}
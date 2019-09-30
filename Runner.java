import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.*;
import java.math.*;
import java.time.Year;
import java.util.ArrayList;
import java.awt.image.*;
import java.applet.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;

public class Runner extends JPanel implements KeyListener, Runnable
{
    public static void main(String [] args) 
    {
        Runner r = new Runner();
    }

    JFrame frame;
    Thread t;
    boolean right, left;
    BufferedImage mountains, trees, ground;
    int mountainsX, treesX, groundX;

    Block[][] arr = new Block[10][50];
    Enemy[] arrE = new Enemy[4];
    int index = 0;

    BufferedImage block;
    BufferedImage enemyI;
    Hero hero;

    boolean running = true;

    public Runner()
    {
        frame=new JFrame();

        try 
        {
            mountains = ImageIO.read(new File("mountains.png"));
            mountainsX = -mountains.getWidth();

            trees = ImageIO.read(new File("trees.png"));
            treesX = -trees.getWidth();
        
            ground = ImageIO.read(new File("ground.png"));
            groundX = -ground.getWidth();

            block = ImageIO.read(new File("block.png"));

            BufferedImage spriteSheet = ImageIO.read(new File("st1.png"));

            BufferedImage[] rightI, leftI;
            rightI = new BufferedImage[9];
            leftI = new BufferedImage[9];

            rightI[0] = spriteSheet.getSubimage(25, 0, 100, 130);
            rightI[1] = spriteSheet.getSubimage(125, 0, 90, 130);
            rightI[2] = spriteSheet.getSubimage(215, 0, 110, 130);
            rightI[3] = spriteSheet.getSubimage(218, 0, 110, 130);
            rightI[4] = spriteSheet.getSubimage(328, 0, 110, 130);
            rightI[5] = spriteSheet.getSubimage(438, 0, 110, 130);
            rightI[6] = spriteSheet.getSubimage(548, 0, 90, 130);
            rightI[7] = spriteSheet.getSubimage(638, 0, 120, 130);
            rightI[8] = spriteSheet.getSubimage(758, 0, 100, 130);

            leftI[0] = spriteSheet.getSubimage(0, 140, 100, 130);
            leftI[1] = spriteSheet.getSubimage(110, 140, 100, 130);
            leftI[2] = spriteSheet.getSubimage(220, 130, 110, 130);
            leftI[3] = spriteSheet.getSubimage(310, 130, 110, 130);
            leftI[4] = spriteSheet.getSubimage(420, 130, 110, 130);
            leftI[5] = spriteSheet.getSubimage(530, 140, 110, 130);
            leftI[6] = spriteSheet.getSubimage(540, 140, 110, 130);
            leftI[7] = spriteSheet.getSubimage(650, 130, 110, 130);
            leftI[8] = spriteSheet.getSubimage(770, 130, 90, 130);

            hero = new Hero(700, 600, rightI, leftI);

            enemyI = ImageIO.read(new File("enemy.png"));
        } 
        catch (IOException io) 
        {
			System.err.println("File does not exist");
        }

        try {
            BufferedReader input = new BufferedReader(new FileReader(new File("blocks.txt")));
            String text;
            int row = 0;
            int col = 0;
            int e = 0;
			while( (text=input.readLine())!= null) {
                for(String s: text.split(""))
                {
                    if(s.equals("B"))
                    {
                        arr[row][col] = new Block(col * 150, row * 100, block);
                    }
                    if(s.equals("E"))
                    {
                        arr[row][col] = new Enemy(col * 150, row * 100, enemyI, row, col);
                        arrE[e] = (Enemy) arr[row][col];
                        e++;
                    }
                    col++;
                }
                row++;
                col=0;
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
  
        frame.addKeyListener(this);
		frame.add(this);
		frame.setSize(1500, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        t = new Thread(this);
		t.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() ==  39)
        {
            right = true;
        }
        if(e.getKeyCode() == 37) 
        {
            left = true;
        }
        if(e.getKeyCode() == 38)
        {
            if(!hero.getIfMoving())
                hero.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() ==  39)
        {
            right = false;
        }
        if(e.getKeyCode() == 37) 
        {
            left = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2d = (Graphics2D)g;

        graphics2d.setColor(Color.BLACK);
        graphics2d.fillRect(0, 0, 1500, 1000);

        graphics2d.setColor(Color.WHITE);
        graphics2d.setFont(new Font("Times New Roman", Font.BOLD, 100));
        graphics2d.drawString("Collect the ghosts", 0, 100);

        if(running)
        {
            graphics2d.drawImage(mountains, mountainsX, 100, null);
            graphics2d.drawImage(mountains, mountainsX + mountains.getWidth(), 100, null);
            graphics2d.drawImage(trees, treesX, -600, null);
            graphics2d.drawImage(trees, treesX + trees.getWidth(), -600, null);
            graphics2d.drawImage(ground, groundX, -600, null);
            graphics2d.drawImage(ground, groundX + ground.getWidth(), -600, null);
    
            for(int r = 0; r < arr.length; r++)
            {
                for(int c = 0; c < arr[0].length; c++)
                {
                    if(arr[r][c] != null)
                        if(!(arr[r][c] instanceof Enemy))
                            if(arr[r][c].getX() >= -200 && arr[r][c].getX() <= 1600)
                                graphics2d.drawImage(arr[r][c].getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT), arr[r][c].getX(), arr[r][c].getY(), null);
                }
            }

            for(int i = 0; i < arrE.length; i++)
                if(!arrE[i].isCaptured())
                    graphics2d.drawImage(arrE[i].getImage().getScaledInstance(150, 100, Image.SCALE_DEFAULT), arrE[i].getX(), arrE[i].getY(), null);
    
            if(right)
                graphics2d.drawImage(hero.getImage("right"), hero.getX(), hero.getY(), null);
            else if(left)
                graphics2d.drawImage(hero.getImage("left"), hero.getX(), hero.getY(), null);
            else
                graphics2d.drawImage(hero.getImage(""), hero.getX(), hero.getY(), null);
        } 
        else
        {
        
        }
    }

    @Override
    public void run() {
        while(true)
        {
            if(running)
            {

                for(int i = 0; i < arrE.length; i++)
                        if(arrE[i].getRectangle().intersects(hero.getRectangle()))
                           arrE[i].capture();

                hero.gravity(arr);
    
                arr = Block.moveArray(arr, right && hero.canMoveRight(arr), left && hero.canMoveLeft(arr));
    
                index++;
                if(index % 10 == 0)
                {
                    for(int i = 0; i < arrE.length; i++)
                    {
                        if(!arrE[i].isCaptured())
                        {
                            if(arrE[i].getDirection().equals("right"))
                            {
                                if(arr[arrE[i].getRow()][arrE[i].getCol() + 1] == null)
                                {
                                    arr[arrE[i].getRow()][arrE[i].getCol() + 1] = new Enemy(arrE[i].getX()+150, arrE[i].getY(), arrE[i].getImage(), arrE[i].getRow(), arrE[i].getCol() + 1);
                                    arr[arrE[i].getRow()][arrE[i].getCol()] = null;
    
                                    arrE[i] = (Enemy)arr[arrE[i].getRow()][arrE[i].getCol() + 1];
                                    arrE[i].setDirection("right");
                                }
                                else
                                    arrE[i].setDirection("left"); 
                            }
                            else
                            {
                                if(arr[arrE[i].getRow()][arrE[i].getCol() - 1] == null)
                                {
                                    arr[arrE[i].getRow()][arrE[i].getCol() - 1] = new Enemy(arrE[i].getX()-150, arrE[i].getY(), arrE[i].getImage(), arrE[i].getRow(), arrE[i].getCol() - 1);
                                    arr[arrE[i].getRow()][arrE[i].getCol()] = null;
    
                                    arrE[i] = (Enemy)arr[arrE[i].getRow()][arrE[i].getCol() - 1];
                                    arrE[i].setDirection("left");
                                }
                                else
                                    arrE[i].setDirection("right"); 
                            }
                        }
                    }  
                    
                }
    
                if(right && hero.canMoveRight(arr))
                {
                    mountainsX--;
                    treesX -= 2;
                    groundX -= 3;
                }

                if(left && hero.canMoveLeft(arr))
                {
                    mountainsX++;
                    treesX += 2;
                    groundX += 3;
                }
    
                if(mountainsX >= 0) 
                    mountainsX -= mountains.getWidth();
                if(mountainsX <= -mountains.getWidth()) 
                    mountainsX += mountains.getWidth();

                if(groundX >= 0) 
                    groundX -= ground.getWidth();
                if(groundX <= -ground.getWidth()) 
                    groundX += ground.getWidth();
        
                if(treesX >= 0) 
                    treesX -= trees.getWidth();
                if(treesX <= -trees.getWidth()) 
                    treesX +=  trees.getWidth();
    
                repaint();
            }
            else
            {

            }

            try 
            {
                t.sleep(50);
            } catch (Exception e) 
            {

            }
        }
    }
}
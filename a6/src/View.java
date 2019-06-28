import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{
    //Member variables   kate View.java > /dev/null 2>&1
    Model model;

    //background & ground images initialized
    Image background;
    Image ground;
    
    //View constructor
    View(Controller c, Model m)
    {
        model = m;
        
        //Load background and ground images
        try
        {
            background = ImageIO.read(new File("background.png"));
            ground = ImageIO.read(new File("ground.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    //Sets color of program, and and draws pictures
    public void paintComponent(Graphics g)
    {       
        //Clear the screen
        g.setColor(new Color(128, 255, 255));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        //draw background image
        g.drawImage(background,0,0,null);
        
        //Draw the ground image
        g.drawImage(ground, -500-model.scrollPos, 595, 5000,650, null);
        
        //DRAW SPRITES
        for (int i = 0; i < model.sprites.size(); i++)
        {
            Sprite s = model.sprites.get(i);
            s.draw(g,model.scrollPos); //Calls every sprite draw method through polymorphism
        }
    }
}

import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.*;
public class Brick extends Sprite
{
    //Brick Image Array
    static Image[] brick_image = null;
 
    //Constructor
    Brick(int xx, int yy, int ww, int hh, Model m)
    {
        super(m);
        x = xx;
        y = yy;
        w = ww;
        h = hh;
        type = "Brick";
        
        //Load Pictures
        brick_image = new Image[1];
        try
        {
            brick_image[0] = ImageIO.read(new File("brick.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    //Copy constructor
    Brick(Brick copyme, Model thenewmodel)
    {
        super(copyme, thenewmodel);
    }
    
    //Unmarshalling constructor for json load
    Brick(Json ob, Model m)
    {  
        super(m);
        x = (int)ob.getLong("x");  
        y = (int)ob.getLong("y");        
        w = (int)ob.getLong("w");        
        h = (int)ob.getLong("h");     
        
        brick_image = new Image[1];
        try
        {
            brick_image[0] = ImageIO.read(new File("brick.png"));
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    //Marshall brick values for save
    Json marshall()
    {
        Json ob = Json.newObject();
        ob.add("type", type);
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }
    
    void update(Model m){}
    
    //Draw Brick
    void draw(Graphics g, int scrollPos)
    {
        g.drawImage(brick_image[0], this.x-scrollPos, this.y, this.w, this.h, null);
    }
    
    Brick cloneme(Model thenewmodel)
    {
        return new Brick(this, thenewmodel);
    }
    
    boolean am_i_a_brick()
    {
        return true;
    }
    
    boolean am_i_a_coinBlock()
    {
        return false;
    }

}

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.Random;

class Coin extends Sprite
{
    //Member variables
    double vvel;
    
    static Image[] coin_pic = null; // Image array for Coin

    //Constructor
    Coin(int xx, int yy, Model m)
    {
        super(m);
        x = xx;
        y = yy - h;
        w = 75;
        h = 75;

        //Load Picture
        if(coin_pic == null)
        {
            coin_pic = new Image[1];
            try
            {
                coin_pic[0] = ImageIO.read(new File("coin.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }  
        }
    }
    
    Coin(Coin copyme, Model thenewmodel)
    {
        super(copyme, thenewmodel);
        vvel = copyme.vvel;
    }
    
    //UNMARSHALLING CONSTRUCTOR
    Coin(Json ob, Model m)
    {
        super(m);
        x = (int)ob.getLong("x");
        x = (int)ob.getLong("y");
        w = 75;
        h = 75;
    }
    
    //Marshall Coin values - I don't even use this.
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
    
    //Update the coin and how it flies out of CoinBlock
    void update(Model m) 
    {
        vvel += 3.14159; //constant vertical velocity
        y += vvel;
        y -= 30.14; //Makes coin go upward
        
        x += hvel; //random horizontal velocity


        //If coin reaches certain Y value, set boolean variable to true
        if(y > 5000)
        {
           coinDead = true;
        }
    }
    
    //Draw coin
    void draw(Graphics g, int scrollPos)
    {
        g.drawImage(coin_pic[0], x - scrollPos, y, null);
    }
    
    Coin cloneme(Model thenewmodel)
    {
        return new Coin(this, thenewmodel);
    }
    
}

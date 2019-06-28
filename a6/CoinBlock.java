import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.Random;

class CoinBlock extends Sprite
{
    //Member variables
    int count;
    
    static Image[] block_pics = null; //CoinBlock image array initilized to null

    boolean dead = false; //boolean for when Coinblock has been hit 5 times
    
    //Constructor
    CoinBlock(int xx, int yy, Model m)
    {
        super(m);
        int count = 0; //counter set to 0
        x = xx;
        y = yy;
        w = 89;
        h = 83;
        type = "CoinBlock";

        //Load coinblock images
        if(block_pics == null)
        {
            block_pics = new Image[2];
            try
            {
                block_pics[0] = ImageIO.read(new File("block1.png"));
                block_pics[1] = ImageIO.read(new File("block2.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }
    
    CoinBlock(CoinBlock copyme, Model thenewmodel)
    {
        super(copyme, thenewmodel);
        count = copyme.count;
        dead = copyme.dead;
    }

    //Unmarshalling constructor for load
    CoinBlock(Json ob, Model m)
    {  
        super(m);
        int count = 0;
        type = ob.getString("type");
        x = (int)ob.getLong("x");  
        y = (int)ob.getLong("y");        
        w = 89;
        h = 83;
    
        //Load images from loading it
        if(block_pics == null)
        {
            block_pics = new Image[2];
            try
            {
                block_pics[0] = ImageIO.read(new File("block1.png"));
                block_pics[1] = ImageIO.read(new File("block2.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }    
    
    //Marshall method for save method
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
    
    //Draw coinblocks depending if its been hit 5 times or not
    void draw(Graphics g, int scrollPos)
    {
        if(!this.dead) // less than 5 hits
        g.drawImage(block_pics[0], x - model.scrollPos, y, null);
        if(this.dead) //5 hits
        g.drawImage(block_pics[1], x - model.scrollPos, y, null);
    }
    
    //Update the coinblock and add a coin 
    void update(Model m) 
    {
        if(count == 5)
        {
            dead = true;
        }
        if(super.addcoin == true)
        {
            if(count <= 5) //if count is less than 5, add a coin then set addcoin boolean to false upon completion
            {
            model.addCoin(x,y);
            }
            super.addcoin = false;
        }
    }

    CoinBlock cloneme(Model thenewmodel)
    {
        return new CoinBlock(this, thenewmodel);
    }
    
    
    boolean am_i_a_coinBlock()
    {
        return true;
    }
    
    boolean am_i_a_brick()
    {
        return false;
    }    
    
}
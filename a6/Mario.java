import java.util.Iterator;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.util.*;

class Mario extends Sprite
{
    //Where Mario was
    int prev_x;
    int prev_y;
    
    Model model;
    boolean bottom = false; //boolean for when mario hits bottom of sprite

    int frames_since_last_on_ground; //frame count since mario was last on ground

    static Image[] mario_images = null; //array of mario images
    
    //Constructor
    Mario(Model m)
    {
        super(m);
        w = 60;
        h = 95;
        prev_x = 0;
        prev_y = 0;
        int frames_since_last_on_ground = 0;
        type = "Mario";
        
        if(mario_images == null) //EXAMPLE OF 'LAZYLOADING'
        {
            mario_images = new Image[5];
            model = m;
            
            //Load Images
            try
            {
                mario_images[0] = ImageIO.read(new File("mario1.png"));
                mario_images[1] = ImageIO.read(new File("mario2.png"));
                mario_images[2] = ImageIO.read(new File("mario3.png"));
                mario_images[3] = ImageIO.read(new File("mario4.png"));
                mario_images[4] = ImageIO.read(new File("mario5.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }        
    }
    
    //Copy Constructor
    Mario(Mario copyme, Model thenewmodel)
    {
        super(copyme,thenewmodel);
        vert_vel = copyme.vert_vel;
        prev_x = copyme.prev_x;
        prev_y = copyme.prev_y;
        bottom = copyme.bottom;
        frames_since_last_on_ground = copyme.frames_since_last_on_ground;
    }
    
    //Unmarshalling constructor for load
    Mario(Json ob, Model m)
    {  
        super(m);
        type = ob.getString("type");
        x = (int)ob.getLong("x");  
        y = (int)ob.getLong("y");        
        w = (int)ob.getLong("w");        
        h = (int)ob.getLong("h");         
        vert_vel = (double)ob.getDouble("vert_vel");
    }
    
    //Last Location 
    void LastLocation()
    {
        prev_x = x;
        prev_y = y;
    }

    //Get out of the sprite method
    void getOut(Sprite s)
    {
        if(x + w >= s.x && prev_x + w <= s.x) //comes in from left side
        {
           x = s.x - w;
        }
        else if(x <= s.x + s.w && prev_x >= s.x+s.w) //comes in from right side
        {
            x = s.x + s.w;
        }
        if(y + h >= s.y && prev_y + h <= s.y) //comes in from above
        {
            y = s.y - h;
            vert_vel = 0;
           frames_since_last_on_ground = 0; //reset frames so user can jump while on a brick
        }
        else if( y <= s.y + s.h && prev_y >= s.y + s.h) //comes in from below
        {
            y = s.y + s.h;
            vert_vel = 0;
            bottom = true;
        }
    }

    //Update method
    void update(Model m)
    {
        m.scrollPos = x - 200; //offsets mario, and scrolls
        vert_vel += 3.14159;
        y += vert_vel;
        
        //Stop when Mario hits ground.
        if(y > 500)
        {
            vert_vel = 0.0;
            y = 500;
            frames_since_last_on_ground = 0;

        }
        
        //Is Mario colliding? Iterate through sprites
        Iterator<Sprite> it = m.sprites.iterator();
        while(it.hasNext())
        {
           Sprite s = it.next();
            
            //If it's a brick or a coin block => Collide
            if(s.am_i_a_brick() || s.am_i_a_coinBlock())
            {
                if(doesCollide(s))
                {
                    getOut(s);     
                    if(s.am_i_a_coinBlock() && bottom) //If it's a coinblock and mario hits bottom, increment count, set addcoin boolean true, bottom boolean to false
                    {   
                       (((CoinBlock)s).count)++;
                       s.addcoin = true;
                       bottom = false;
                    }
                }
                // else
                //WHILE NOT COLLIDING CONDITION System.out.println("");
            }
        }
        frames_since_last_on_ground++; //increment frames
    }
    
    //Draw Mario
    void draw(Graphics g, int scrollPos)
    {
        int marioFrame = (Math.abs(x) / 20) % 5; //MarioFrames
        g.drawImage(mario_images[marioFrame], x - scrollPos, y, null);
    }
    
    //Marshall Mario values for Load
    Json marshall()
    {
        Json ob = Json.newObject();
        ob.add("type", type);
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("vert_vel", vert_vel);
        return ob;
    }
    
    boolean am_i_a_brick()
    {
        return false;
    }
    
    boolean am_i_a_coinBlock()
    {
        return false;
    }
    
    boolean am_i_mario()
    {
        return true;
    }
    
    Mario cloneme(Model thenewmodel)
    {
        return new Mario(this,thenewmodel);
    }
    
}
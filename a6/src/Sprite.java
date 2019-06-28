import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.event.ActionEvent;
abstract class Sprite
{
    //Member Variables
    int x;
    int y;
    int w;
    int h;
    double vert_vel = 0;
    int hvel;
    Model model;
    String type;
    
    abstract void update(Model m); 
    abstract void draw(Graphics g, int scrollPos);
    abstract Json marshall();
    abstract Sprite cloneme(Model thenewmodel);
    
    boolean addcoin;
    boolean coinDead = false;
    
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
        return false;
    }
    
    //Constructor that takes a model m
    Sprite(Model m)
    {
        model = m;
    }
    
    //Copy constructor for sprite
    Sprite(Sprite copyme, Model thenewmodel)
    {
        model = thenewmodel;
        x = copyme.x;
        y = copyme.y;
        w = copyme.w;
        h = copyme.h;
        hvel = copyme.hvel;
        type = copyme.type;
        vert_vel = copyme.vert_vel;
    }
    
    //Sprite collision method
    boolean doesCollide(Sprite that)
    {
        if(this.x + this.w <= that.x)
            return false;
        if(this.x >= that.x + that.w)
            return false;
        if(this.y + this.h <= that.y)
            return false;
        if(this.y >= that.y + that.h)
          return false;
        return true;
    }
}

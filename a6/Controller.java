import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
    //Member variables
    View view;
    Model model;
    boolean keyLeft;
    boolean keyRight;
    boolean keyUp;
    boolean keyDown;
    boolean keySpace;
    int mouseDownX;
    int mouseDownY;

    Controller(Model m)
    {
        model = m;
    }

    //Removes button upon click
    public void actionPerformed(ActionEvent e)
    {

    }
	
    //Lets caller set the object that 'view' references
    void setView(View v)
    {
        view = v;
    }

    //Mouse click methods
    public void mousePressed(MouseEvent e)
    {
        mouseDownX = e.getX();
        mouseDownY = e.getY();     
    }

    public void mouseReleased(MouseEvent e) 
    {    
        int x1 = mouseDownX;
        int x2 = e.getX();
        int y1 = mouseDownY;
        int y2 = e.getY();
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1,y2);
       // System.out.println("x:" + (left+model.scrollPos));
        //System.out.println("y:" + top);
        if(e.getButton() == 1)
        {
            model.addBrick(left + model.scrollPos, top, right - left, bottom -  top);
        }
        if(e.getButton() == 3)
        {
            model.addCoinBlock(x1 + model.scrollPos, y1);
        }

    }

    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {    }

    //Arrow key & key methods
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = true; break;
            case KeyEvent.VK_LEFT: keyLeft = true; break;
            case KeyEvent.VK_UP: keyUp = true; break;
            case KeyEvent.VK_DOWN: keyDown = true; break;
            case KeyEvent.VK_S: model.save("map.json"); break;
            case KeyEvent.VK_L: model.load("map.json"); break;
            case KeyEvent.VK_SPACE: keySpace = true; break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = false; break;
            case KeyEvent.VK_LEFT: keyLeft = false; break;
            case KeyEvent.VK_UP: keyUp = false; break;
            case KeyEvent.VK_DOWN: keyDown = false; break;
            case KeyEvent.VK_SPACE: keySpace = false; break;
            
        }
    }

    public void keyTyped(KeyEvent e)
    {
    }
	
    //Side scroller, arrow keys and space method
    void update()
    {
        model.mario.LastLocation();
    
        if(keyRight)
        {
            model.mario.x += 10;

        }
        if(keyLeft) 
        {
            model.mario.x -= 10;
        }
        if(keySpace)
        {
            if(model.mario.frames_since_last_on_ground < 5)
                model.mario.vert_vel = -20.1;
        }
        
 
	// Evaluate each possible action
	double score_run = model.evaluateAction(Model.Action.run, 0);
	double score_jump = model.evaluateAction(Model.Action.jump, 0);
// 	double score_jump_and_run =
// 		model.evaluateAction(Model.Action.run_and_jump, 0);
System.out.println("j: " + score_jump + "r: " + score_run);
	// Do the best one
//         if(score_jump_and_run > score_jump &&score_jump_and_run > score_run){
//                 System.out.println("runNjumpscore " + score_jump_and_run);
// 		model.do_action(Model.Action.run_and_jump);}
	/*else*/ if(score_jump > score_run){
            	//System.out.println("Jumpscore " + score_jump + "runscore " + score_run);
		model.do_action(Model.Action.jump);}
	else{
             //   System.out.println("Jumpscore " + score_jump + "runscore " + score_run);
		model.do_action(Model.Action.run);}
    
    }

}

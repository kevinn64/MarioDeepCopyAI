import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
    //Member variables of game class
    Model model;
    View view;
    Controller controller;

    //Game constructor
    public Game()
    {
        model = new Model();
        controller = new Controller(model);
        view = new View(controller,model);
        this.setTitle("my mediocre mario game");
        this.setSize(2000, 700); //500,500
        this.setFocusable(true);
        this.getContentPane().add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(controller);
        this.addKeyListener(controller);
    }
	
    //Running the game method
    public void run()
    {
        //Delay so class can see window
        while(true)
        {
            controller.update();
            model.update();
            view.repaint(); // Indirectly calls View.paintComponent
            Toolkit.getDefaultToolkit().sync(); // Updates screen

            // Go to sleep for 40 miliseconds : 1000ms / 40ms = 25 frames/sec
            try
            {
                Thread.sleep(40);
            } catch(Exception e) {
                e.printStackTrace();
                    System.exit(1);
                }
        }
    }
    
    //Main code, executes and runs the game
    public static void main(String[] args)
    {
        Game g = new Game();
        g.run();
    }
}

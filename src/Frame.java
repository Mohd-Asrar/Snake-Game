import javax.swing.JFrame;
import java.awt.*;

public class Frame  extends JFrame{

    Frame(){

        this.add(new Panel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\Mohd Asrar\\IdeaProjects\\Snake Game\\src\\snake.png");
        this.setIconImage(icon);


    }


}

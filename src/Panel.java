import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {

    static int width = 600;
    static int height = 600;
    static  int unit = 30;

    Random random;

    boolean flag =false;

    Timer timer;

    int fx, fy;

    int [] xsnake = new int[240];
    int [] ysnake = new int[240];
    int length =3;

    int score =0;

    char dir = 'R';




    Panel(){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(new Color(2, 2, 2));
        this.addKeyListener(new Key());
        this.setFocusable(true);
        random = new Random();
        gameStart();

    }


    public void gameStart(){
        flag=true;
        spawnfood();
        timer = new Timer(130, this);
        timer.start();

    }



    // Creating Food  at random position!
    public void spawnfood(){
        fx = random.nextInt(width/unit)*unit;
        fy = random.nextInt(height/unit)*unit;

    }

    // use for creating graphics like food snake etc
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }

    public void draw(Graphics graphic){


        // We Create a grid(Matrix shape to visualise where food appear every time and where to where snake moving and how many space we have in screen)
//        for (int i = 0; i < height/unit; i++) {
//            graphic.drawLine(i*unit,0,i*unit, height);
//            graphic.drawLine(0,i*unit,width, i*unit);
//        }


        if(flag){

            // Creating graphic for  the food Particle
            graphic.setColor(new Color(241, 12, 12, 255));
            graphic.fillOval(fx, fy, unit,unit);

            // creating a snake!
            for (int i = 0; i < length; i++) {
                if(i==0){
                    graphic.setColor(new Color(45, 231, 17));
                    graphic.fillOval(xsnake[i], ysnake[i], unit, unit);
                }else {
//                    graphic.setColor(new Color(239, 6, 6));
                    graphic.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    graphic.fillOval(xsnake[i], ysnake[i], unit, unit);
                }

            }


            // Printing the Score
            graphic.setColor(Color.yellow);
            graphic.setFont(new Font("Serif Bold Italic", Font.BOLD, 30));
            FontMetrics fme  = getFontMetrics(graphic.getFont());
            graphic.drawString("Score:" + score, (width-fme.stringWidth("Score:" + score))/2, graphic.getFont().getSize());

        }else{
            gameover(graphic);

        }
    }

    public void gameover(Graphics graphic){


        // Final Score
        graphic.setColor(Color.blue);
        graphic.setFont(new Font("Serif Bold Italic", Font.BOLD, 30));
        FontMetrics fme  = getFontMetrics(graphic.getFont());
        graphic.drawString("Score:" + score, (width-fme.stringWidth("Score:" + score))/2, graphic.getFont().getSize());

        // Game over
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Serif Bold Italic", Font.BOLD, 60));
         fme  = getFontMetrics(graphic.getFont());
        graphic.drawString("GAME OVER", (width-fme.stringWidth("GAME OVER"))/2, height/2);


        //  Restart the game
        graphic.setColor(Color.GREEN);
        graphic.setFont(new Font("Serif Bold Italic", Font.BOLD, 40));
        fme  = getFontMetrics(graphic.getFont());
        graphic.drawString("Press R to Replay", (width-fme.stringWidth("Press R to Replay"))/2, 3*height/4);

    }

    public void eat(){
        if((fx == xsnake[0]) && (fy == ysnake[0])){
            length++;
            score++;
            spawnfood();
        }
    }

    public void hit(){
        // to check hit with own body

        for (int i = length-1; i >0 ; i--) {

            if ((xsnake[0] == xsnake[i] && ysnake[0] == ysnake[i])) {
                flag = false;

            }
        }
        
        if(xsnake[0]<0){
            flag =false;

        } else if (xsnake[0]>width) {
            flag= false;

        } else if (ysnake[0]<0) {
            flag=false;

        } else if (ysnake[0]>height) {
            flag= false;

        }

        if(!flag){
            timer.stop();
        }

    }

    public void move() {

        // Update the cordinate of the body except head of the snake
        for (int i = length - 1; i > 0; i--) {
            xsnake[i] = xsnake[i - 1];
            ysnake[i] = ysnake[i - 1];
        }

        // to update the head
        switch (dir) {
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                break;
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;

        }

    }


    public class Key extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    if(dir != 'D'){
                        dir = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if(dir != 'U'){
                        dir = 'D';
                    }
                    break;

                case KeyEvent.VK_LEFT:
                    if(dir != 'R'){
                        dir = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if(dir != 'L'){
                        dir = 'R';
                    }
                    break;


                case KeyEvent.VK_R:
                    if(!flag){
                        score=0;
                        length=3;
                        dir='R';
                        Arrays.fill(xsnake,0);
                        Arrays.fill(ysnake, 0);
                        gameStart();
                    }
                    break;
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        if(flag){
            move();
            hit();
            eat();
        }
        repaint();

    }
}

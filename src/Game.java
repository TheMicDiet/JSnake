import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

/**
 * @author Michael Dietrich
 */
public class Game extends JPanel implements ActionListener {
    private int height = 300;
    private int width = 300;


    private Image apple;
    private Image head;
    private Image tail;

    private int tail_amount = 3;
    private int snake_width = 10;
    private int snake_x[] = new int[width*height/(snake_width*snake_width)];
    private int snake_y[] = new int[width*height/(snake_width*snake_width)];
    private int apple_x;
    private int apple_y;
    private boolean running = true;
    private Timer t;
    private int difficulty = 200;

    public static int direction;

    public Game() {
        addKeyListener(new SnakeListener());
        setPreferredSize(new Dimension(width,height));
        setFocusable(true);
        setBackground(Color.black);

        ImageIcon snake_apple =  new ImageIcon("apple.png");
        ImageIcon snake_head = new ImageIcon("head.png");
        ImageIcon snake_tail = new ImageIcon("tail.png");
        apple = snake_apple.getImage();
        head = snake_head.getImage();
        tail = snake_tail.getImage();
        /*try {
           tail = ImageIO.read(new File("/tail.png"));
            head = ImageIO.read(new File("/head.png"));
            apple = ImageIO.read(new File("/apple.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        for (int i = 0; i < tail_amount; i++) {
           snake_x[i] = 100 - i * 10;
           snake_y[i] = 50;
        }

        running = true;
        t = new Timer(difficulty,this);
        t.start();
        spawnApple();

    }

    private void spawnApple() {
        int random = (int) (Math.random()*29);
        apple_x = random * snake_width;
        random = (int) (Math.random()*29);
        apple_y = random * snake_width;

        for (int i = 0; i < snake_x.length; i++) {
            if ((apple_x == snake_x[i]) || (apple_y == snake_y[i])) {
                spawnApple();
            }
        }


    }

   @Override public void actionPerformed(ActionEvent e) {
        if(running) {
            check_apple();
            check_death();
            move_snake();
        }
        repaint();

   }

   @Override public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 1; i < tail_amount; i++) {
                g.drawImage(tail, snake_x[i], snake_y[i], this);
            }
            g.drawImage(head, snake_x[0], snake_y[0], this);

            Toolkit.getDefaultToolkit().sync();
        } else {
            Font f = new Font("Arial",Font.BOLD,20);
            FontMetrics fm = getFontMetrics(f);
            g.setColor(Color.white);
            g.setFont(f);
            g.drawString("Game over - You died",width - fm.stringWidth("Game over - You died"),height/2);
       }

   }

    private void move_snake() {
        for (int i = tail_amount; i > 0; i--) {
            snake_x[i]  = snake_x[i-1];
            snake_y[i] = snake_y[i-1];
        }
        /* 0 left 1 right 2 up 3 down */
        switch (direction) {
            case 0:
                snake_x[0] -= snake_width;
                break;
            case 1:
                snake_x[0] += snake_width;
                break;
            case 2:
                snake_y[0] -= snake_width;
                break;
            case 3:
                snake_y[0] += snake_width;
                break;
            default:
                break;
        }
    }

    private void check_death() {
        for (int i = tail_amount; i > 3; i-- ) {
            if(snake_x[0] == snake_x[i] || snake_y[0]  == snake_y[i]) {
                running = false;
            }
        }
        if (snake_y[0] >= height || snake_x[0] >= width || snake_x[0] < 0 || snake_y[0] < 0) {
            running = false;
        }
        if (!running) {
            t.stop();
        }
    }

    private void check_apple() {
        if(snake_x[0] == apple_x && snake_y[0] ==  apple_y) {
            tail_amount++;
            spawnApple();
        }
    }

}

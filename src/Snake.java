import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Dietrich
 */
public class Snake extends JFrame{




    public Snake() {

        add(new Game());
        setResizable(false);
        pack();
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame snake = new Snake();
                snake.setVisible(true);

            }
        });

    }



}

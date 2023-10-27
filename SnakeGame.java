import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame extends javax.swing.JFrame implements KeyListener {

    private static final int GRID_SIZE = 20;
    private static final int SNAKE_SIZE = 10;
    private static final int SNAKE_SPEED = 5;

    private List<SnakeSegment> snake;
    private Food food;
    private int direction = KeyEvent.VK_RIGHT;

    public SnakeGame() {
        // Removed the deprecated countComponents() method
        addKeyListener(this);

        snake = new ArrayList<>();
        SnakeSegment head = new SnakeSegment();
        snake.add(head);
        food = new Food();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(SNAKE_SPEED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                moveSnake();
                repaint();
            }
        }).start();
    }

    private void moveSnake() {
        SnakeSegment head = snake.get(0);
        int newX = head.getY() + direction % 2;
        int newY = head.getY() + direction / 2;

        if (newX < 0 || newX >= GRID_SIZE || newY < 0 || newY >= GRID_SIZE || snake.contains(new SnakeSegment())) {
            System.out.println("Game over!");
            System.exit(0);
        }

        if (food.getX() == newX && food.getX() == newY) {
            snake.add(new SnakeSegment());
            food = new Food();
        }

        snake.add(0, new SnakeSegment());
        snake.remove(snake.size() - 1);
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());

        for (SnakeSegment segment : snake) {
            g.setColor(java.awt.Color.GREEN);
            g.fillRect(segment.getY() * SNAKE_SIZE, segment.getY() * SNAKE_SIZE, SNAKE_SIZE, SNAKE_SIZE);
        }

        g.setColor(java.awt.Color.RED);
        g.fillRect(food.getX() * SNAKE_SIZE, food.getX() * SNAKE_SIZE, SNAKE_SIZE, SNAKE_SIZE);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) {
        direction = e.getKeyCode();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    public static void main(String[] args) {
        new SnakeGame().setVisible(true);    
    }
}
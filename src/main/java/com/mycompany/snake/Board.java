/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.Interfaces.DrawSquareInterface;
import com.mycompany.snake.Interfaces.GameOverInterface;
import com.mycompany.snake.Interfaces.Incrementer;
import com.mycompany.snake.Interfaces.InitGamer;
import static com.mycompany.snake.SquareType.HEAD;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;

/**
 *
 * @author fabzamgri
 */
public class Board extends javax.swing.JPanel implements DrawSquareInterface, InitGamer{

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Board.class.getName());
    
    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(snake.getDirection() != Direction.RIGHT) {
                        snake.changeDirection(Direction.LEFT);
                    }
                    System.out.println("LEFT");
                    break;
                case KeyEvent.VK_RIGHT:
                    if(snake.getDirection() != Direction.LEFT) {
                       snake.changeDirection(Direction.RIGHT);
                    }
                    System.out.println("RIGHT");
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.changeDirection(Direction.UP);
                    }
                    System.out.println("UP");
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                       snake.changeDirection(Direction.DOWN);
                    }
                    System.out.println("DOWN");
                    break;
                case KeyEvent.VK_SPACE:
                    pause();
                default:
                    break;
            }
            repaint();
        }
    }
    
    public static int NUM_ROWS = 40;
    public static int NUM_COLS = 20;
    private Snake snake;
    private Timer timer;
    private DrawSquareInterface drawSquareInterface;
    public static final int DELTA_TIME = 100;
    private Food food;
    private Incrementer incrementer;
    private SpecialFood specialFood;
    private ScoreBoard sb;
    private GameOverInterface gameOverInterface;
    
    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new MyKeyAdapter());
        
        specialFood = null;
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        initGame();
    }
    
    public void initGame() {
        if (incrementer != null) {
            incrementer.reset();
        }
        timer.start();
        snake = new Snake(this);
        food = new Food(snake, this);
        specialFood = new SpecialFood(snake, this);
    }
    
    private void tick() {
        if (snake.canMoveAny()) {
            snake.Move();
            if (snake.eats(food)) {
                snake.grow(1);
                snake.addNode(food);
                food = new Food(snake, this);
                //El agujero negro solo sale si cambias la secuencia en la que se ejecuta el food XD.
                System.out.println("eat it");
                incrementer.incrementScore(1);
            }
            if (snake.eats(specialFood)) {
                snake.grow(3);
                snake.addNode(specialFood);
                specialFood = new SpecialFood(snake, this);
                System.out.println("hooo my cock");
                incrementer.incrementScore(3);
            }
            
            if (snake.colidesWithItself(food)) {
                System.out.println("Salvation chuqubuke ijeanlli añauwu");
            }
        } else {
            gameOver();
        }
        repaint();
    }
    
    //Comenzar cuando haya terminado el programa
    private void tickBlackVoid() {
        if (snake.canMoveAny()) {
            snake.Move();
            snake.colidesWithItself(food);
            snake.canMoveAny();
            if (snake.eats(food)) {
                snake.grow(1);
                food = new Food(snake, this);
                snake.addNode(food);
                
                //El agujero negro solo sale si cambias la secuencia en la que se ejecuta el food XD.
                System.out.println("eat it");
                incrementer.incrementScore(1);
            }
            if (snake.eats(specialFood)) {
                snake.grow(3);
                specialFood = new SpecialFood(snake, this);
                snake.addNode(specialFood);
                
                System.out.println("hooo my cock");
                incrementer.incrementScore(3);
            }
        }
        repaint();
    }
    
    public void gameOver() {
        timer.stop();
        gameOverInterface.setVisible(this);
    }
    
    public void setGameOverInterface(GameOverInterface gmInterface) {
        this.gameOverInterface = gmInterface;
    }
    
    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }
    
    private void pause() {
        timer.stop();
    }
    
    private int squareWidth() {
        return getWidth() / NUM_COLS;
    }

    private int squareHeight() {
        return getHeight() / NUM_ROWS;
    }
    
    public void drawSquare(Graphics g, int row, int col,
            SquareType type) {
        Color colors[] = {new Color(0, 0, 0),
            new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };
        int x = col * squareWidth();
        int y = row * squareHeight();
        //Color color = isHead ? new Color(204, 102, 102) : new Color(102, 102, 204);
        Color color = getSquareColor(type);
        g.setColor(color);
        g.fillRect(x + 1, y + 1, squareWidth() - 2,
                squareHeight() - 2);
        g.setColor(color.brighter());
        g.drawLine(x, y + squareHeight() - 1, x, y);
        g.drawLine(x, y, x + squareWidth() - 1, y);
        g.setColor(color.darker());
        g.drawLine(x + 1, y + squareHeight() - 1,
                x + squareWidth() - 1, y + squareHeight() - 1);
        g.drawLine(x + squareWidth() - 1,
                y + squareHeight() - 1,
                x + squareWidth() - 1, y + 1);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.paint(g);
        food.paintFood(g);
        specialFood.paintFood(g);
        Toolkit.getDefaultToolkit().sync();
    }
    
    private void paintBorderBoard(Graphics g) {
        g.setColor(Color.BLACK);
        int width = squareWidth() * NUM_COLS;
        int heigth = squareHeight() * NUM_ROWS;
        g.drawRect(0, 0, width, heigth);
    }
    
    private Color getSquareColor(SquareType type) {
        switch(type) {
            case HEAD:
                return new Color(131, 39, 154);
            case BODY:
                return new Color(19, 181, 208);
            case FOOD:
                return new Color(241, 61, 171);
            case SUPERFOOD:
                return new Color(102, 102, 204);    
            
            default:
                throw new AssertionError();
        }
    }
    
    /*
    No se que estoy haciendo:
    - Falta añadir un bloqueo de aparicion de la fruta, si la fruta aparece en el cuerpo de la fruta, que se vaya a otra parte donde este vacio
    - Poder cambiar de modos entre normal a locura extrema DAAAAAAA
    - Falta añadir el configdialog para decirle al jugador en cuanto deltatime quiere que vaya el juego
    */

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

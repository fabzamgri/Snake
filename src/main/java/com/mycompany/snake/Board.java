/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

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
public class Board extends javax.swing.JPanel implements DrawSquareInterface{

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
    public static final int DELTA_TIME = 300;
    private Food food;
    
    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new MyKeyAdapter());
        
        snake = new Snake(this);
        timer = new Timer(DELTA_TIME, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        initGame();
    }
    
    private void initGame() {
        timer.start();
        food = new Food(snake, this);
    }
    
    private void tick() {
        if (snake.canMove()) {
            snake.Move();
            if (snake.eats(food)) {
                snake.grow(1);
                food = new Food(snake, this);
                snake.addNode(food);
            }
        }
        repaint();
    }
    
    private void tick2() {
        if (snake.canMove()) {
            snake.Move();
            if (snake.eats(food)) {
                snake.grow(1);
                food = new Food(snake, this);
                //Patentalo, agujero negro, nuevo modo
                snake.addNode(food);
            }
        }
        repaint();
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

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
import java.awt.Font;
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
                    if(snake.getDirection() != Direction.RIGHT && !turning) {
                        snake.changeDirection(Direction.LEFT);
                        turning = true;
                    }
                    System.out.println("LEFT");
                    break;
                case KeyEvent.VK_RIGHT:
                    if(snake.getDirection() != Direction.LEFT && !turning) {
                       snake.changeDirection(Direction.RIGHT);
                       turning = true;
                    }
                    System.out.println("RIGHT");
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN && !turning) {
                        snake.changeDirection(Direction.UP);
                        turning = true;
                    }
                    System.out.println("UP");
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP && !turning) {
                       snake.changeDirection(Direction.DOWN);
                       turning = true;
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
    private Timer timerCountDown;
    private int timeTrialCount;
    private DrawSquareInterface drawSquareInterface;
    public int deltaTime = 200;
    private Food food;
    private Incrementer incrementer;
    private SpecialFood specialFood;
    private ScoreBoard sb;
    private GameOverInterface gameOverInterface;
    private GameOverDialog gameOverDialog;
    private boolean isNormalMode = true;
    private boolean isSnakeOrSpider = true;
    private boolean isTimeTrial = true;
    private boolean turning;
    
    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new MyKeyAdapter());
        
        specialFood = null;
        
        //El timer recibira un deltaTime dinamico
        timer = new Timer(setDeltaTime(deltaTime), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (isNormalMode) {
                    tick();
                } else {
                    tickBlackVoid();
                }
            }
        });
        initGame();
    }
    
    public void initGame() {
        if (timerCountDown != null) {
            timerCountDown.stop();
        }
        isTimeTrial = false;
        timeTrialCount = 0;
        
        //Los sout sirven para saber como se estan haciendo las operaciones en la terminal misma
        System.out.println("initGame ejecutado, deltaTime = " + this.deltaTime);
        if (timer != null) {
            timer.stop();
        } 

        //Resetear el marcador
        if (incrementer != null) {
            incrementer.reset();
        } else {
        System.out.println("INCREMENTER ES NULL");  // ← añade esto
    }

        //Crear los objetos PRIMERO
        snake = new Snake(this);
        food = new Food(snake, this);
        specialFood = new SpecialFood(snake, this);
        System.out.println("Aplicando delay: " + this.deltaTime);
        //Caso nuevo: Inicializar velocidad dinamica
        timer.setDelay(this.deltaTime);

        //Iniciar el cronómetro AL FINAL
        timer.start();
    }
    
    /*
    No se que estoy haciendo:
    - Corregir el error de chocarse consigo mismo en la misma linea en la que va.
    - Terminar el TimeTrail.
    */
    
    public void tick() {
        if (snake.canMoveAny()) {
            if (isSnakeOrSpider) {
                snake.Move();
            } else {
                snake.MoveSpider();
            }

            if (snake.eats(food)) {
                snake.grow(1);
                snake.addNode(food);
                food = new Food(snake, this);
                //El agujero negro solo sale si cambias la secuencia en la que se ejecuta el food XD.
                System.out.println("eat it");
                incrementer.incrementScore(1);
                if (isTimeTrial) timeTrialCount += 1;
            }
            if (snake.eats(specialFood)) {
                snake.grow(3);
                snake.addNode(specialFood);
                snake.addNode(specialFood);
                snake.addNode(specialFood);
                specialFood = new SpecialFood(snake, this);
                System.out.println("hooo my cock");
                incrementer.incrementScore(3);
                if (isTimeTrial) timeTrialCount += 3;
            }

            if (snake.colidesWithItself(food)) {
                System.out.println("Salvation chuqubuke ijeanlli añauwu");
            }
        } else {
            gameOver();
        }
        repaint();
        turning = false;
    }
    
    //Comenzar cuando haya terminado el programa
    public void tickBlackVoid() {
        if (snake.canMoveAny()) {
            if(isSnakeOrSpider) {
                snake.Move();
            } else {
                snake.MoveSpider();
            }
            
            if (snake.eats(food)) {
                snake.grow(1);
                snake.addNode(food);
                food = new Food(snake, this);
                //El agujero negro solo sale si cambias la secuencia en la que se ejecuta el food XD.
                System.out.println("eat it");
                incrementer.incrementScore(1);
                if (isTimeTrial) timeTrialCount += 1;
            }
            //Solo se le añadira esa funcion de agujero negro a la fruta especial
            if (snake.eats(specialFood)) {
                snake.grow(3);
                specialFood = new SpecialFood(snake, this);
                snake.addNode(specialFood);
                snake.addNode(specialFood);
                snake.addNode(specialFood);
                System.out.println("hooo my cock");
                incrementer.incrementScore(3);
                if (isTimeTrial) timeTrialCount += 3;
            }
            
            if (snake.colidesWithItself(food)) {
                System.out.println("Salvation chuqubuke ijeanlli añauwu");
            }
        } else {
            gameOver();
        }
        repaint();
        turning = false;
    }
    
    public void setMode(boolean mode) {
        this.isNormalMode = mode;
    }
    
    public void setSnakeBody(boolean snakeBody) {
        this.isSnakeOrSpider = snakeBody;
    }
    
    public int setDeltaTime(int speed) {
        System.out.println("setDeltaTime llamado con: " + speed);
        this.deltaTime = speed;
        if (timer != null) {
            timer.setDelay(speed);
        }
        return speed;
    }
    
    public void setTimeTrial(int seconds) {
        this.isTimeTrial = true;
        this.timeTrialCount = seconds;

        if (timerCountDown != null) {
            timerCountDown.stop();
        }

        timerCountDown = new Timer(1000, e -> {
            timeTrialCount--;
            System.out.println("Tiempo restante: " + timeTrialCount);
            if (timeTrialCount <= 0) {
                timerCountDown.stop();
                gameOver();
            }
        });
        timerCountDown.start();
    }
    
    public void gameOver() {
        timer.stop();
    // Verificamos si la interfaz ha sido asignada antes de usarla
    if (gameOverInterface != null) {
        gameOverInterface.setVisible(this);
    } else {
        System.err.println("Error: GameOverInterface no ha sido inicializada.");
        // Opcional: podrías reiniciar el juego automáticamente aquí o imprimir un log
    }
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
        paintBorderBoard(g);
        food.paintFood(g);
        specialFood.paintFood(g);
        if (isTimeTrial) paintTimeTrial(g);
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
    
    // Muestra el tiempo restante en la esquina superior derecha, igual que draw
    private void paintTimeTrial(Graphics g) {
        Color color = timeTrialCount <= 3 ? new Color(204, 102, 102)   // rojo si quedan ≤3 seg
                                     : new Color(37, 211, 102);    // verde normal
        g.setColor(color);
        g.setFont(new Font("Open Sans Semibold", Font.BOLD, 18));
        g.drawString("Time: " + timeTrialCount, getWidth() - 80, 20);
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

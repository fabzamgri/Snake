/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import com.mycompany.snake.Interfaces.DrawSquareInterface;
import java.awt.Graphics;

/**
 *
 * @author fabzamgri
 */
public class Food extends Node {
    
    public DrawSquareInterface drawSquareInterface;
    
    public Food(Snake snake, DrawSquareInterface drawSquareInterface) {
        super(0, 0);
        this.drawSquareInterface = drawSquareInterface;
        do {
        int row = (int)(Math.random() * Board.NUM_ROWS);
        int col = (int)(Math.random() * Board.NUM_COLS);
        setRow(row);
        setCol(col);
        } while (snake.isFruitInsideSnake(this));
    }
    
    public void paintFood(Graphics g) {
        drawSquareInterface.drawSquare(g, getRow(), getCol(), SquareType.FOOD);
    }
}

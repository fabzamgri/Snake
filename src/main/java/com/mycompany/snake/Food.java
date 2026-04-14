/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;

/**
 *
 * @author fabzamgri
 */
public class Food extends Node {
    
    private DrawSquareInterface draw;
    
    public Food(Snake snake, DrawSquareInterface drawSquareInterface) {
        super(0, 0);
        this.draw = drawSquareInterface;
      //  do {
        int row = (int)(Math.random() * Board.NUM_ROWS);
        int col = (int)(Math.random() * Board.NUM_COLS);
        setRow(row);
        setCol(col);
        //} while (snake.colitionBody(this));
    }
    
    public void paintFood(Graphics g) {
        draw.drawSquare(g, getRow(), getCol(), SquareType.FOOD);
    }
}

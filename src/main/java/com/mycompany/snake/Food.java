/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author fabzamgri
 */
public class Food extends Node {
    
    public Food() {
        super(0, 0);
        int row = (int)(Math.random() * Board.NUM_ROWS);
        int col = (int)(Math.random() * Board.NUM_COLS);
        setRow(row);
        setCol(col);
    }
}

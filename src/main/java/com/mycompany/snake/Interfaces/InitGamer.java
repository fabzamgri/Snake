/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.snake.Interfaces;

/**
 *
 * @author fabzamgri
 */
public interface InitGamer {
    public void initGame();
    //Podria hacerse con un case posiblemente pero mejor hago otro boolean
    public void setMode(boolean blackVoid);
    //Mejor hago un booleano de que snake prefiere el jugador
    public void setSnakeBody(boolean snakeBody);
}

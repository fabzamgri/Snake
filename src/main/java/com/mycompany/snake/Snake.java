/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fabzamgri
 */
public class Snake {
    private List<Node> nodes;
    private Direction direction;
    private DrawSquareInterface drawSquareInterface;
    private int nodesToGrow;
   
   
    public Snake(DrawSquareInterface drawSquareInterface) {
        nodes = new ArrayList<Node>();
        direction = Direction.RIGHT;
        this.drawSquareInterface = drawSquareInterface;
        int middle = Board.NUM_COLS / 2;
        for (int i = 0; i < 4; i++) {
            Node node = new Node(middle, middle + i);
            addNode(node);
        }
        nodesToGrow = 0;
       
    }
    
    public Direction getDirection() {
        return direction;
    }
   
    public void addNode(Node node) {
        nodes.add(0, node);
    }
    
    public void grow(int amount) {
        nodesToGrow += amount;
    }
   
    public void paint(Graphics g) {
        boolean first = true;
        for(Node node : nodes) {
            drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), SquareType.HEAD);
            if (first) {
                first = false;
            }
        }
    }
    
    public boolean canMove() {
        switch(direction) {
            case UP:
                return nodes.getFirst().getRow() - 1 >= 0;
            case DOWN:
                return nodes.getFirst().getRow() + 1 < Board.NUM_ROWS;
            case LEFT:
                return nodes.getFirst().getCol() - 1 >= 0;
            case RIGHT:
                return nodes.getFirst().getCol() + 1 < Board.NUM_COLS;
        }
        return true;
    }
    
    public void Move() {
        int row = nodes.getFirst().getRow();
        int col = nodes.getFirst().getCol();
        Node node = null;
        switch(direction) {
            case UP:
                node = new Node(row - 1, col);
                break;
            case DOWN:
                node = new Node(row + 1, col);
                break;
            case LEFT:                         //Modo insano GAAAAAAAAAAAA
                node = new Node(row, col - 1); //(col, row - 1)
               break;                          //DELTATIME = 100
            case RIGHT:
                node = new Node(row, col + 1); //(col, row + 1)
                break;
        }
        nodes.addFirst(node);
        nodes.remove(nodes.getLast());
    }
    
    public void changeDirection(Direction newDirection) {
        direction = newDirection;
    }
    
    public boolean eats(Food food) {
        int row = nodes.getFirst().getRow();
        int col = nodes.getFirst().getCol();
        return (food.getRow() == row && food.getCol() == col);
    }
    
    /*ublic boolean colitionBody(Node node) {
        for (Node n : nodes) {
            if (node.getRow() == n.getRow() && node.getCol() == n.getCol()) {
                return node.
            }
        }
        return false;
    }
*/
}

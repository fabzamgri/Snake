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
            Node node = new Node(middle, middle - i);
            addNode(node);
        }
        nodesToGrow = 0;
       
    }
   
    public void addNode(Node node) {
        nodes.add(0, node);
    }
   
    public void paint(Graphics g) {
        boolean first = true;
        for(Node node : nodes) {
            drawSquareInterface.drawSquare(g, node.getRow(), node.getCol(), first);
            if (first) {
                first = false;
            }
        }
    }
    
    public void canMove(int row, int col) {
        switch(direction) {
            case UP:
                if (nodes.getFirst() && nodes.get(col) > 0) {
                    
                }
        }
    }
}

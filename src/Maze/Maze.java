package Maze;

import java.util.*;
import java.io.*;

/**
 * Generate random maze
 */
public class Maze {

    private int size;
    private Cell[][] maze;
    private int visitedCount;

    public Maze(int size){
        this.size = size;
        maze = new Cell[size][size];
        visitedCount = 0;
    }

    public void init(){
        //initialize all cells in the maze as unvisited and unblocked
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                maze[i][j] = new Cell(i, j, size);
            }
        }

        Cell startCell = randGen();
        dfs(startCell);
        printMaze();
    }

    //generate a random cell
    public Cell randGen(){
        Random rand = new Random();
        int startRow = rand.nextInt(size);
        int startCol = rand.nextInt(size);
        return maze[startRow][startCol];
    }

    //use dfs to build maze
    private void dfs(Cell startCell){
        Stack<Cell> stack = new Stack<>();
        stack.push(startCell);
        startCell.setVisited(true);
        Random rand = new Random();
        while(!stack.empty()){
            Cell curr = stack.pop();
            if(rand.nextDouble() < 0.3) {
                curr.setBlocked(true);
            }

            Cell[] neighbours = findNextUnvisited(curr);
            for(int i = 0; i < 4; i++){
                  if(neighbours[i] != null && !neighbours[i].isVisited()){
                      neighbours[i].setVisited(true);
                      visitedCount++;
                      stack.push(neighbours[i]);
                  }
            }
        }

        //if there remains unvisited nodes, choose an unvisited node and repeat dfs
        if(visitedCount != size * size - 1){
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(!maze[i][j].isVisited()){
                        dfs(maze[i][j]);
                    }
                }
            }
        }
    }

    //find the list of unvisited adjacent cells, return empty list if reached dead end
    private Cell[] findNextUnvisited(Cell curr){
        Cell[] toReturn = new Cell[4];
        Cell toAdd = null;
        int row = curr.getRow();
        int col = curr.getColumn();
        for(int i = 0; i < 4; i++){
            if(curr.getAdj()[i] != null){
                switch(i){
                    case 0: toAdd = maze[row - 1][col];
                            break;
                    case 1: toAdd = maze[row][col + 1];
                            break;
                    case 2: toAdd = maze[row + 1][col];
                            break;
                    case 3: toAdd = maze[row][col - 1];
                            break;
                }
            }
            if(toAdd != null && !toAdd.isVisited()){
               toReturn[i] = toAdd;
            }
        }
        return toReturn;
    }

    //output the maze to text file
    private void printMaze(){
        try {
            File output = new File("Maze.txt");
            PrintWriter writer = new PrintWriter(output);
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(maze[i][j].isBlocked()) {
                        writer.print(1);
                    } else {
                        writer.print(0);
                    }
                }
                writer.println();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public Cell[][] getMaze(){
        return maze;
    }
}

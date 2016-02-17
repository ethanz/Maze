package Maze;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Repeated A* search
 */
public class RepeatedAStar {

    private Cell[][] maze;
    private int counter;
    private Cell startCell;
    private Cell currCell;
    private Cell goalCell;
    private BinaryHeap heap;//open list
    private ArrayList<Cell> path;
    private ArrayList<Cell> reversePath;
    private boolean preferLarge;

    public RepeatedAStar(Maze maze, Cell[] cells, boolean preferLarge){
        this.maze = maze.getMaze();
        counter = 0;
        currCell = cells[0];
        startCell = cells[0];
        goalCell = cells[1];
        heap = new BinaryHeap(preferLarge);
        this.preferLarge = preferLarge;
        path = new ArrayList<>();
        reversePath = new ArrayList<>();
    }

    public void run(){
        System.out.println("Start cell: " + currCell.getRow() + ", " + currCell.getColumn());
        System.out.println("Goal cell: " + goalCell.getRow() + ", " + goalCell.getColumn());
        while(!currCell.equals(goalCell.getPriv())){
            //clearOnPath();
            counter++;
            currCell.setGValue(0);
            currCell.setSearch(counter);
            goalCell.setGValue((int)Double.POSITIVE_INFINITY);
            goalCell.setSearch(counter);
            heap = new BinaryHeap(preferLarge);
            currCell = findBlocked(currCell);
            heap.add(setManhattanDistance(currCell, goalCell));
            computePath();
            if(heap.isEmpty()){
                System.out.println("I cannot reach the target.");
                return;
            }
            int index = traceBack();
            //if(index != 0) {
                currCell = reversePath.get(index);
                //currCell = updateCost(currCell, reversePath.get(index - 1));
            //}
            while(deadEnd(currCell)){
                updateNeighbours(currCell);
                if(index < reversePath.size() - 1) {
                    currCell = reversePath.get(index + 1);
                }
            }
            //clearPriv(reversePath);
        }
        //findPath();
        System.out.println("I have reached the target.");
    }

    private void findPath(){
        Cell cell = goalCell;
        while(cell.getPriv() != startCell){
            cell.setExpanded(true);
            cell = cell.getPriv();
        }
    }

    private boolean deadEnd(Cell cell){
        int[] actionCosts = cell.getActionCosts();
        Cell[] neighbours = getNeighbours(cell);
        int counter = 0;
        for(int i = 0; i < actionCosts.length; i++){
            if(neighbours[i] != null){
                if(actionCosts[i] == -1 || neighbours[i].isBlocked()){
                    counter++;
                }
            }
        }
        return counter == 4;
    }
    private Cell findBlocked(Cell cell){
        Cell[] neighbours = getNeighbours(cell);
        for(int i = 0; i < neighbours.length; i++){
            if(neighbours[i] != null && neighbours[i].isBlocked()){
                cell.setActionCosts(i, -1);
                updateNeighbours(neighbours[i]);
            }
        }
        return cell;
    }

    private void updateNeighbours(Cell cell){
        Cell[] neighbours = getNeighbours(cell);
        for(int i = 0; i < neighbours.length; i++){
            if(neighbours[i] != null){
                neighbours[i].setActionCosts(getReverse(i), -1);
            }
        }
    }

    private int traceBack(){
        reversePath = new ArrayList<>();
        path = new ArrayList<>();
        Cell tmp = goalCell.getPriv();
        reversePath.add(goalCell);
        reversePath.add(tmp);
        while(tmp.getPriv() != currCell){
            reversePath.add(tmp.getPriv());
            tmp = tmp.getPriv();
        }
        for(int i = reversePath.size() - 1; i > 1; i--){
            currCell = reversePath.get(i);
            currCell.setOnPath(true);
            path.add(currCell);
            if(reversePath.get(i - 1).isBlocked()){
                return i;
            }
        }
        reversePath.get(1).setOnPath(true);
        return 1;
    }

    private Cell updateCost(Cell cell, Cell blocked){
        Cell[] neighbours = getNeighbours(cell);
        for(int i = 0; i < neighbours.length; i++){
            if(neighbours[i] != null && neighbours[i].equals(blocked)){
                cell.setActionCosts(i, (int)Double.POSITIVE_INFINITY);
            }
        }
        return cell;
    }

    private void computePath(){
        while(heap.peek() != null && goalCell.getGValue() > heap.peek().getFValue()){
            Cell temp = heap.remove();
            Cell[] neighbours = getNeighbours(temp);
            int[] actionCosts = temp.getActionCosts();
            for(int i = 0; i < neighbours.length; i++){
                if(actionCosts[i] != -1) {
                    Cell next = neighbours[i];
                    if (next != null && next.getSearch() < counter) {
                        next.setGValue((int) Double.POSITIVE_INFINITY);
                        next.setSearch(counter);
                    }
                    if (next != null && next.getGValue() > actionCosts[i] + temp.getGValue()) {
                        next.setGValue(actionCosts[i] + temp.getGValue());
                        //if(next.getPriv() == null){
                            next.setPriv(temp);
                        //}
                        //next.setActionCosts(getReverse(i), -1);
                        int index = heap.contain(next);
                        if (index != 0) {
                            heap.remove(index);
                        }
                        heap.add(setManhattanDistance(next, goalCell));
                    }
                }
            }
        }
    }

    private int getReverse(int i){
        switch(i){
            case 0: return 2;
            case 1: return 3;
            case 2: return 0;
            case 3: return 1;
        }
        return -1;
    }

    private Cell[] getNeighbours(Cell cell){
        Cell[] cells = new Cell[4];
        int row = cell.getRow();
        int col = cell.getColumn();
        if(row > 0){
            cells[0] = (maze[row - 1][col]);
        }
        if(col < maze.length - 1){
            cells[1] = (maze[row][col + 1]);
        }
        if(row < maze.length - 1){
            cells[2] = (maze[row + 1][col]);
        }
        if(col > 0){
            cells[3] = (maze[row][col - 1]);
        }
        return cells;
    }

    private Cell setManhattanDistance(Cell curr, Cell goal){
        int h_value = Math.abs(curr.getColumn() - goal.getColumn()) + Math.abs(curr.getRow() - goal.getRow());
        curr.setHVAlue(h_value);
        return curr;
    }
}

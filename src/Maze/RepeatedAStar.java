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
    private ArrayList<Cell> reversePath;
    private int expandCount;
    private boolean adaptive;
    private boolean forward;
    private ArrayList<Cell> visited;
    private ArrayList<Cell> closeList;

    public RepeatedAStar(Maze maze){
        this.maze = maze.getMaze();
        counter = 0;
        expandCount = 0;

    }

    public int run(Cell[] cells, boolean preferLarge, boolean forward, boolean adaptive){
        this.forward = forward;
        currCell = cells[0];
        startCell = cells[0];
        goalCell = cells[1];
        heap = new BinaryHeap(preferLarge);
        reversePath = new ArrayList<>();
        closeList = new ArrayList<>();
        visited = new ArrayList<>();
        visited.add(startCell);
        visited.add(goalCell);
        this.adaptive = adaptive;
        expandCount = 0;
        while(!currCell.equals(goalCell.getPriv())){
            counter++;
            heap = new BinaryHeap(preferLarge);
            currCell = findBlocked(currCell);

            goalCell.setSearch(counter);
            currCell.setSearch(counter);
            if(forward){
                goalCell.setGValue((int)Double.POSITIVE_INFINITY);
                currCell.setGValue(0);
                heap.add(setManhattanDistance(currCell, goalCell));
                computePath(goalCell);
            } else {
                goalCell.setGValue(0);
                goalCell.setPriv(goalCell);
                currCell.setGValue((int)Double.POSITIVE_INFINITY);
                heap.add(setManhattanDistance(goalCell, currCell));
                computePath(currCell);
            }

            if(heap.isEmpty()){
                return 0;
            }
            int index = traceBack();
            currCell = reversePath.get(index);

            while(deadEnd(currCell)){
                updateNeighbours(currCell);
                if(index < reversePath.size() - 1) {
                    currCell = reversePath.get(index + 1);
                }
            }
        }
        //clearExpanded();
        return expandCount;
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
        Cell tmp;
        if(forward){
            tmp = goalCell.getPriv();
            reversePath.add(goalCell);
            reversePath.add(tmp);
            while(tmp.getPriv() != currCell){
                reversePath.add(tmp.getPriv());
                tmp = tmp.getPriv();
            }
            for(int i = reversePath.size() - 1; i > 1; i--){
                currCell = reversePath.get(i);
                currCell.setOnPath(true);
                if(reversePath.get(i - 1).isBlocked()){
                    return i;
                }
            }
        } else {
            tmp = currCell.getPriv();
            reversePath.add(currCell);
            reversePath.add(tmp);
            while(tmp.getPriv() != goalCell){
                reversePath.add(tmp.getPriv());
                tmp = tmp.getPriv();
            }
            for(int i = 0; i < reversePath.size() - 1; i++){
                currCell = reversePath.get(i);
                currCell.setOnPath(true);
                if(reversePath.get(i + 1).isBlocked()){
                    return i;
                }
            }
            reversePath.get(reversePath.size() - 2).setOnPath(true);
            return reversePath.size() - 1;
        }

        reversePath.get(1).setOnPath(true);
        return 1;
    }

    private void computePath(Cell goal){
        while(heap.peek() != null && goal.getGValue() > heap.peek().getFValue()){
            Cell temp = heap.remove();
            expandCount++;
            closeList.add(temp);
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
                        next.setPriv(temp);
                        int index = heap.contain(next);
                        if (index != 0) {
                            heap.remove(index);
                        }
                        Cell toAdd = setManhattanDistance(next, goal);
                        if(toAdd == null){
                            System.out.println("WTF");
                        }
                        heap.add(toAdd);
                        if(!visited.contains(toAdd)){
                            visited.add(toAdd);
                        }
                    }
                }
            }
        }
        if(adaptive){
            for(int i = 0; i < closeList.size(); i++){
                closeList.get(i).setHVAlue(goal.getGValue() - closeList.get(i).getGValue());
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

    private void clearExpanded(){
        for(int i = 0; i < visited.size(); i++){
            Cell toClear = visited.get(i);
            boolean blocked = toClear.isBlocked();
            int row = toClear.getRow();
            int col = toClear.getColumn();
            maze[row][col] = new Cell(row, col, maze.length);
            if(blocked){
                maze[row][col].setBlocked(true);
            }
        }
    }
}

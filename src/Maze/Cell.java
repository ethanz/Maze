package Maze;

/**
 * Represents a cell in the maze
 */
public class Cell {

    private int row;
    private int column;
    private boolean visited;
    private boolean blocked;
    private Boolean[] adj;//list of status of adjacent cells starting with north and goes clockwise. null if not exist
    private Cell priv;//previous cell in the path
    private int f;

    public Cell(int row, int column, int size){
        this.row = row;
        this.column = column;
        visited = false;
        blocked = false;
        adj = new Boolean[]{true, true, true, true};//initialize as adjacent cells are not blocked
        priv = null;
        if(row == 0){
            adj[0] = null;
        }
        if(column == 0){
            adj[3] = null;
        }
        if(row == size - 1){
            adj[2] = null;
        }
        if(column == size - 1){
            adj[1] = null;
        }
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public boolean isVisited(){
        return visited;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public boolean isBlocked(){
        return blocked;
    }

    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }

    public Boolean[] getAdj(){
        return adj;
    }

    public void setAdj(Boolean[] adj){
        this.adj = adj;
    }

    public Cell getPriv(){
        return priv;
    }

    public void setPriv(Cell priv){
        this.priv = priv;
    }

    public int getFValue(){
        return f;
    }
}

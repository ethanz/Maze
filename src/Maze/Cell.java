package Maze;

/**
 * Represents a cell in the maze
 */
public class Cell {

    private int row;
    private int column;
    private boolean visited;
    private boolean blocked;
    private boolean expanded;
    private Boolean[] adj;//list of status of adjacent cells starting with north and goes clockwise. null if not exist
    private Cell priv;//previous cell in the path
    private int[] actionCosts;
    private int g_value;
    private int h_value;
    private int search;
    private boolean onPath;

    public Cell(int row, int column, int size){
        this.row = row;
        this.column = column;
        visited = false;
        blocked = false;
        onPath = false;
        expanded = false;
        adj = new Boolean[]{true, true, true, true};//initialize as adjacent cells are not blocked
        priv = null;
        g_value = -1;
        h_value = -1;
        search = 0;
        actionCosts = new int[]{1, 1, 1, 1};
        if(row == 0){
            adj[0] = null;
            actionCosts[0] = (int)Double.POSITIVE_INFINITY;
        }
        if(column == 0){
            adj[3] = null;
            actionCosts[3] = (int)Double.POSITIVE_INFINITY;
        }
        if(row == size - 1){
            adj[2] = null;
            actionCosts[2] = (int)Double.POSITIVE_INFINITY;
        }
        if(column == size - 1){
            adj[1] = null;
            actionCosts[1] = (int)Double.POSITIVE_INFINITY;
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
        return g_value + h_value;
    }

    public int getGValue() {
        return g_value;
    }

    public void setGValue(int g){
        g_value = g;
    }

    public void setHVAlue(int h){
        h_value = h;
    }

    public boolean equals(Cell cell){
        if (cell == null){
            return false;
        }
        return row == cell.getRow() && column == cell.getColumn();
    }

    public void setSearch(int search){
        this.search = search;
    }

    public int getSearch(){
        return search;
    }

    public boolean isOnPath(){
        return onPath;
    }

    public void setOnPath(boolean bool){
        onPath = bool;
    }

    public int[] getActionCosts(){
        return actionCosts;
    }

    public void setActionCosts(int index, int value){
        actionCosts[index] = value;
    }

    public void setExpanded(boolean bool){
        expanded = bool;
    }

    public boolean isExpanded(){
        return expanded;
    }

}

package Maze;

/**
 * Launcher
 */

public class MazeLauncher {

    public static void main(String[] args){
        Maze maze = new Maze(101);
        maze.init();
        RepeatedAStar search = new RepeatedAStar(maze, maze.genStartGoal(), false );//break ties prefer larger value of g
        search.run();
        maze.printMaze("Finished.txt");
    }

}

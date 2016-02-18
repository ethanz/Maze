package Maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Test runs for backward/forward, tie-breaking and adaptive A*.
 */
public class TestCases {
    RepeatedAStar search;
    Cell[] cells;
    Maze maze;

    public TestCases(Maze maze){
        this.maze = maze;
        search = new RepeatedAStar(maze);
        cells = this.maze.genStartGoal();
    }

    public void run(){
        File compare = new File("CompareResults.txt");
        PrintWriter writer;
        try{
            writer = new PrintWriter(compare);
            writer.println("Start Cell: " + cells[0].getRow() + " " + cells[0].getColumn());
            writer.println("Goal Cell: " + cells[1].getRow() + " " + cells[1].getColumn());
            writer.println();
            runAStar(writer);
            testTiebreak(writer);
            testBackward(writer);
            testAdaptive(writer);
            writer.close();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }

    public void runAStar(PrintWriter writer){
        int expanded = search.run(cells, true, true, false);
        maze.printMaze("RepeatedAStar.txt");
        if(expanded == 0){
            writer.println("Repeated A* cannot reach the target.");
            writer.println();
        } else {
            writer.println("Repeated A* have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
    }

    public void testTiebreak(PrintWriter writer){
        int expanded = search.run(cells, false, true, false);
        maze.printMaze("PreferSmallG.txt");
        if(expanded == 0){
            writer.println("Repeated A* by preferring smaller g value cannot reach the target.");
            writer.println();
        } else {
            writer.println("Repeated A* by preferring smaller g value have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
    }

    public void testBackward(PrintWriter writer){
        int expanded = search.run(cells, true, false, false);
        maze.printMaze("BackwardAStar.txt");
        if(expanded == 0){
            writer.println("Backward Repeated A* cannot reach the target.");
            writer.println();
        } else {
            writer.println("Backward Repeated A* have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
    }

    public void testAdaptive(PrintWriter writer){
        int expanded = search.run(cells, true, true, true);
        maze.printMaze("AdaptiveAStar.txt");
        if(expanded == 0){
            writer.println("Adaptive A* cannot reach the target.");
            writer.println();
        } else {
            writer.println("Adaptive A* have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
    }
}

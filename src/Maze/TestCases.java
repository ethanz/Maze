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
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int x = 0;
        int y = 0;
        int z = 0;
        int counter = 0;
        while(counter < 1000){
            try{
                maze = new Maze(101);
                maze.init();
                search = new RepeatedAStar(maze);
                cells = this.maze.genStartGoal();
                writer = new PrintWriter(compare);
                writer.println("Start Cell: " + cells[0].getRow() + " " + cells[0].getColumn());
                writer.println("Goal Cell: " + cells[1].getRow() + " " + cells[1].getColumn());
                writer.println();
                a = runAStar(writer);
                b = testTiebreak(writer);
                c = testBackward(writer);
                d = testAdaptive(writer);
                writer.close();
            } catch (FileNotFoundException e){
                System.out.println("File not found.");
            }
            if(a != 0){
                if(b < a){
                    x++;
                }
                if(c < a){
                    y++;
                }
                if(d < a){
                    z++;
                }
            }
            counter++;
        }
        System.out.println("Small G faster: " + x);
        System.out.println("Backward faster: " + y);
        System.out.println("Adaptive faster: " + z);
        System.out.println();
        System.out.println();
    }

    public int runAStar(PrintWriter writer){
        int expanded = search.run(cells, true, true, false);
        //maze.printMaze("RepeatedAStar.txt");
        if(expanded == 0){
            writer.println("Repeated A* cannot reach the target.");
            writer.println();
        } else {
            writer.println("Repeated A* have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
        return expanded;
    }

    public int testTiebreak(PrintWriter writer){
        int expanded = search.run(cells, false, true, false);
        //maze.printMaze("PreferSmallG.txt");
        if(expanded == 0){
            writer.println("Repeated A* by preferring smaller g value cannot reach the target.");
            writer.println();
        } else {
            writer.println("Repeated A* by preferring smaller g value have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
        return expanded;
    }

    public int testBackward(PrintWriter writer){
        int expanded = search.run(cells, true, false, false);
        //maze.printMaze("BackwardAStar.txt");
        if(expanded == 0){
            writer.println("Backward Repeated A* cannot reach the target.");
            writer.println();
        } else {
            writer.println("Backward Repeated A* have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
        return expanded;
    }

    public int testAdaptive(PrintWriter writer){
        int expanded = search.run(cells, true, true, true);
        //maze.printMaze("AdaptiveAStar.txt");
        if(expanded == 0){
            writer.println("Adaptive A* cannot reach the target.");
            writer.println();
        } else {
            writer.println("Adaptive A* have reached the target.");
            writer.println(expanded + " cells expanded.");
            writer.println();
        }
        return expanded;
    }
}

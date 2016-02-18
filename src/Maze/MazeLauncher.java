package Maze;

/**
 * Launcher
 */

public class MazeLauncher {

    public static void main(String[] args){
        Maze maze = new Maze(101);
        maze.init();
        TestCases tests = new TestCases(maze);
        tests.run();
        System.out.println("Please see results at the following output files: ");
        System.out.println();
        System.out.println("    CompareResults.txt");
        System.out.println();
        System.out.println("Please refer to the original maze and paths by different algorithms at the following output files: ");
        System.out.println();
        System.out.println("    Maze.txt");
        System.out.println("    RepeatedAStar.txt");
        System.out.println("    PreferSmallG.txt");
        System.out.println("    BackwardAStar.txt");
        System.out.println("    AdaptiveAStar.txt");

    }

}

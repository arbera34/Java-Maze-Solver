import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A depth-first, recursive backtracking maze solver. */
public final class MazeSolver {
    private MazeSolver() {}

    /** The path contains directions from S to E when this method returns true. */
    public static boolean solveMaze(char[][] maze, int row, int col,
                                    ArrayList<String> path, MazeVisualizer visualizer) {
        return search(maze, row, col, path, visualizer, 0);
    }

    private static boolean search(char[][] maze, int row, int col,
                                  ArrayList<String> path, MazeVisualizer visualizer, int delayMs) {
        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length) return false;
        char tile = maze[row][col];
        if (tile == '#' || tile == '+' || tile == '.') return false;
        if (tile == 'E') return true;

        maze[row][col] = '+';
        visualizer.repaint();
        pause(delayMs);

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        String[] names = {"up", "down", "left", "right"};
        for (int i = 0; i < 4; i++) {
            if (search(maze, row + dr[i], col + dc[i], path, visualizer, delayMs)) {
                path.add(names[i]);
                maze[row][col] = tile == 'S' ? 'S' : '.';
                visualizer.repaint();
                return true;
            }
        }
        maze[row][col] = tile == 'S' ? 'S' : ' ';
        visualizer.repaint();
        pause(delayMs);
        return false;
    }

    private static void pause(int delayMs) {
        if (delayMs <= 0) return;
        try { Thread.sleep(delayMs); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public static void main(String[] args) {
        String file = "mazes/maze1.txt";
        boolean gui = true;
        int delayMs = 50;
        for (String arg : args) {
            if (arg.equals("--no-gui")) gui = false;
            else if (arg.equals("--fast")) delayMs = 0;
            else if (arg.endsWith(".txt")) file = arg;
            else { System.err.println("Unknown argument: " + arg); System.exit(2); }
        }
        try {
            Maze maze = new Maze(file);
            System.out.println("Maze loaded from " + file + ":");
            maze.printMaze();
            ArrayList<String> path = new ArrayList<>();
            MazeVisualizer visualizer = new MazeVisualizer(maze.getMaze(), path, gui);
            visualizer.display();
            // Retain original recursive algorithm behavior, with a configurable delay.
            boolean solved = search(maze.getMaze(), maze.getStartRow(), maze.getStartCol(),
                                    path, visualizer, visualizer.isEnabled() ? delayMs : 0);
            System.out.println(solved ? "\nMaze solved!" : "\nNo solution found.");
            maze.printMaze();
            if (solved) {
                Collections.reverse(path);
                System.out.println("Moves (" + path.size() + "): " + String.join(" -> ", path));
            }
        } catch (Exception error) {
            System.err.println("Error: " + error.getMessage());
            System.exit(1);
        }
    }
}

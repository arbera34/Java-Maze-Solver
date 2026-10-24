import java.util.ArrayList;
import java.util.Arrays;

/** Minimal dependency-free test runner. */
public final class MazeSolverTest {
    private static int passed;

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
        passed++;
    }

    private static void rejects(Runnable code, String message) {
        try { code.run(); throw new AssertionError(message); }
        catch (IllegalArgumentException expected) { passed++; }
    }

    public static void main(String[] args) {
        Maze maze = new Maze(Arrays.asList("#####", "#S E#", "#####"));
        ArrayList<String> moves = new ArrayList<>();
        boolean solved = MazeSolver.solveMaze(maze.getMaze(), maze.getStartRow(), maze.getStartCol(),
                                            moves, new MazeVisualizer(maze.getMaze(), moves, false));
        check(solved, "Simple maze should be solved");
        check(moves.size() == 2, "Shortest corridor needs 2 moves");
        check(moves.equals(Arrays.asList("right", "right")), "Original solver accumulates moves in reverse order");

        Maze blocked = new Maze(Arrays.asList("#####", "#S#E#", "#####"));
        ArrayList<String> blockedMoves = new ArrayList<>();
        check(!MazeSolver.solveMaze(blocked.getMaze(), blocked.getStartRow(), blocked.getStartCol(), blockedMoves,
                                   new MazeVisualizer(blocked.getMaze(), blockedMoves, false)), "Blocked maze should fail");
        check(blockedMoves.isEmpty(), "No moves should be returned when blocked");
        check(blocked.getMaze()[1][1] == 'S', "Start marker must be preserved");

        rejects(() -> new Maze(Arrays.asList("#S", "##E")), "Nonrectangular maze should fail");
        rejects(() -> new Maze(Arrays.asList("###", "#S#", "###")), "Missing exit should fail");
        rejects(() -> new Maze(Arrays.asList("###", "#E#", "###")), "Missing start should fail");
        rejects(() -> new Maze(Arrays.asList("S?E")), "Unknown tile should fail");
        System.out.println("All " + passed + " maze checks passed.");
    }
}

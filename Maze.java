import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Validates and stores a text maze. # = wall, S = start, E = exit, space = path. */
public final class Maze {
    private final char[][] cells;
    private final int startRow;
    private final int startCol;

    public Maze(String file) throws IOException {
        this(Files.readAllLines(Path.of(file)));
    }

    public Maze(List<String> rows) {
        if (rows.isEmpty() || rows.get(0).isEmpty()) {
            throw new IllegalArgumentException("Maze must contain at least one nonempty row");
        }
        int width = rows.get(0).length();
        cells = new char[rows.size()][width];
        int foundStart = 0, foundExit = 0, sr = -1, sc = -1;
        for (int r = 0; r < rows.size(); r++) {
            String row = rows.get(r);
            if (row.length() != width) {
                throw new IllegalArgumentException("All maze rows must have equal length (row " + (r + 1) + ")");
            }
            for (int col = 0; col < width; col++) {
                char tile = row.charAt(col);
                if (tile != '#' && tile != ' ' && tile != 'S' && tile != 'E') {
                    throw new IllegalArgumentException("Invalid tile '" + tile + "' at row " + (r + 1));
                }
                cells[r][col] = tile;
                if (tile == 'S') { foundStart++; sr = r; sc = col; }
                if (tile == 'E') foundExit++;
            }
        }
        if (foundStart != 1 || foundExit != 1) {
            throw new IllegalArgumentException("Maze needs exactly one S and one E");
        }
        startRow = sr;
        startCol = sc;
    }

    public char[][] getMaze() { return cells; }
    public int getStartRow() { return startRow; }
    public int getStartCol() { return startCol; }

    public void printMaze() {
        for (char[] row : cells) System.out.println(new String(row));
    }
}

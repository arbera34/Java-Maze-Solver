# Java Maze Solver — Recursive Backtracking Visualizer

A complete, runnable Java application that solves text-based mazes using **recursive depth-first search (DFS) and backtracking**. Developed from my original coursework-based `MazeSolver` implementation and extended with the missing maze loader, Swing visualizer, sample mazes, and tests.

## Features

- Loads rectangular mazes from a plain-text file.
- Validates walls, open spaces, exactly one start, and exactly one exit.
- Solves mazes using recursive DFS (up, down, left, right).
- Marks explored cells and highlights the successful path.
- Optionally animates the search in a Java Swing window.
- Runs in headless/terminal mode (useful for GitHub CI and remote servers).
- Prints the path and movement directions when a solution exists.
- Includes dependency-free automated tests for valid and invalid mazes.

## Project structure

```text
JavaMazeSolver/
├── src/
│   ├── Maze.java            # Loads and validates the maze
│   ├── MazeSolver.java      # DFS / recursive backtracking
│   └── MazeVisualizer.java  # Swing-based animation
├── mazes/
│   └── maze1.txt            # Example maze
├── tests/
│   └── MazeSolverTest.java
├── .gitignore
└── README.md
```

## Requirements

- Java Development Kit (JDK) 17 or newer
- No third-party libraries

Check that Java is installed:

```bash
java -version
javac -version
```

## Compile and run

Open a terminal in the `JavaMazeSolver` folder.

**Windows (PowerShell):**

```powershell
mkdir build
javac -d build src\Maze.java src\MazeVisualizer.java src\MazeSolver.java
java -cp build MazeSolver
```

**macOS / Linux:**

```bash
mkdir -p build
javac -d build src/*.java
java -cp build MazeSolver
```

An animated Swing window opens on a computer with a graphical display. To solve in the terminal without animation:

```bash
java -cp build MazeSolver --no-gui --fast
```

You can specify a different maze file:

```bash
java -cp build MazeSolver mazes/maze1.txt --no-gui
```

## Run tests

Compile tests after compiling the main source files:

```bash
javac -cp build -d build tests/MazeSolverTest.java
java -cp build MazeSolverTest
```

## Maze format

| Symbol | Meaning |
| --- | --- |
| `#` | Wall |
| `S` | Starting position |
| `E` | Exit |
| Space | Open cell |
| `+` | Explored cell during animation |
| `.` | Final solution path |

All lines must have identical lengths, and the file must contain exactly one `S` and one `E`.

Example:

```text
#####
#S E#
#####
```

## How the algorithm works

1. Start at `S` and reject cells outside the maze, walls, or previously visited cells.
2. Mark the current cell as explored.
3. Recursively try neighboring cells in the order up, down, left, right.
4. If a branch fails, restore the cell and backtrack.
5. When `E` is reached, save the successful path and print the directions.

**Complexity:** Because the algorithm restores cells during backtracking, it can revisit a cell on different search branches; the worst-case search cost can grow exponentially with the number of open cells. The maze uses **O(rows × columns)** storage, and recursion stack depth is bounded by the number of cells on the active path. It finds **a** valid path, not necessarily the shortest path.

## Future improvements

- Add breadth-first search (BFS) to calculate shortest paths.
- Support maze generation and multiple difficulty levels.
- Add controls for animation speed and search strategy.
- Package the application as an executable JAR.

## Author

**Arber Aliu** — Computer Science, Western University

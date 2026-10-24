import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** Optional visual animation. Does nothing when --no-gui or headless. */
public final class MazeVisualizer {
    private final char[][] maze;
    private final List<String> path;
    private final boolean enabled;
    private JFrame frame;
    private JPanel panel;
    private static final int CELL = 30;

    public MazeVisualizer(char[][] maze, List<String> path) {
        this(maze, path, !GraphicsEnvironment.isHeadless());
    }

    public MazeVisualizer(char[][] maze, List<String> path, boolean enabled) {
        this.maze = maze;
        this.path = path;
        this.enabled = enabled && !GraphicsEnvironment.isHeadless();
    }

    public void display() {
        if (!enabled) return;
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("Java Maze Solver - Recursive Backtracking");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                panel = new JPanel() {
                    @Override protected void paintComponent(Graphics graphics) {
                        super.paintComponent(graphics);
                        Graphics2D g = (Graphics2D) graphics;
                        for (int r = 0; r < maze.length; r++) {
                            for (int c = 0; c < maze[r].length; c++) {
                                switch (maze[r][c]) {
                                    case '#': g.setColor(new Color(34, 50, 70)); break;
                                    case 'S': g.setColor(new Color(60, 170, 100)); break;
                                    case 'E': g.setColor(new Color(230, 90, 85)); break;
                                    case '+': g.setColor(new Color(70, 150, 230)); break;
                                    case '.': g.setColor(new Color(255, 210, 90)); break;
                                    default: g.setColor(Color.WHITE);
                                }
                                g.fillRect(c * CELL, r * CELL, CELL, CELL);
                                g.setColor(new Color(210, 218, 228));
                                g.drawRect(c * CELL, r * CELL, CELL, CELL);
                            }
                        }
                    }
                };
                panel.setPreferredSize(new Dimension(maze[0].length * CELL, maze.length * CELL));
                frame.add(panel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } catch (Exception error) {
            throw new IllegalStateException("Could not open maze visualizer", error);
        }
    }

    public void repaint() {
        if (enabled && panel != null) panel.repaint();
    }

    public boolean isEnabled() { return enabled; }
}

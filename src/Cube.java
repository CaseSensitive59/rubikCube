import java.util.Arrays;

/**
 * Created by Case-Sensitive on 2016/11/28.
 */
public class Cube {

    public static void main(String[] args) {
        Cube r = new Cube();
        r.rotateCounterclockwise(3);

        System.out.println(r);
    }

    public static class Surface {
        public int[][] color;

        public Surface(int color) {
            // 3 * 3 color tile
            this.color = new int[3][3];
            for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) {
                this.color[i][j] = color;
            }
        }

        /**
         * getEdgeColorsXY[i][0]: x coordinate for ith color tile
         * getEdgeColorsXY[i][1]: y coordinate for ith color tile
         */
        public int[][] getEdgeColorsXY(char edgeMark) {
            switch(edgeMark) {
                case 'N':
                    return new int[][] {{0,0}, {0,1}, {0,2}};
                case 'n':
                    return new int[][] {{0,2}, {0,1}, {0,0}};
                case 'E':
                    return new int[][] {{0,2}, {1,2}, {2,2}};
                case 'e':
                    return new int[][] {{2,2}, {1,2}, {0,2}};
                case 'S':
                    return new int[][] {{2,2}, {2,1}, {2,0}};
                case 's':
                    return new int[][] {{2,0}, {2,1}, {2,2}};
                case 'W':
                    return new int[][] {{2,0}, {1,0}, {0,0}};
                case 'w':
                    return new int[][] {{0,0}, {1,0}, {2,0}};
                default:
                    throw new IllegalArgumentException();
            }
        }

        public String toString() {
            String s = "{";
            for (int i = 0; i < 3; i++) {
                s += Arrays.toString(color[i]);
                if (i < 2) s += "\n";
            }
            s += "}";
            return s;
        }
    }

    private Surface[] surfaces;
    private int[][] adjacentSurfaceColor = {{1,5,4,2}, {3,5,0,2}, {1,0,4,3}, {4,5,1,2}, {0,5,3,2}, {1,3,4,0}};
    private char[][] adjacentSurfaceEdgeMark = {{'s','w','n','e'}, {'s','n','n','n'}, {'w','w','w','w'},
            {'s','e','n','w'}, {'s','s','n','s'}, {'e','e','e','e'}};

    public Cube() {
        this.surfaces = new Surface[6];
        for (int i = 0; i < 6; i++) {
            this.surfaces[i] = new Surface(i);
        }
    }

    public void rotateCounterclockwise(int color) {
        int[][] edges = new int[4][3];
        char[] edgeMarks = adjacentSurfaceEdgeMark[color];

        // allEdgesXY[i][j][0]: x coordinate for jth color tile, on ith adjacent surface
        // allEdgesXY[i][j][1]: y coordinate for jth color tile, on ith adjacent surface
        int[][][] allEdgesXY = new int[4][3][2]; // Stores the XY coordinates for ALL adjacent edges
        for (int i = 0; i < 4; i++) {
            allEdgesXY[i] = surfaces[adjacentSurfaceColor[color][i]].getEdgeColorsXY(edgeMarks[i]);
        }

        // Rotate (similar to tmp = x; x = y; y = tmp)
        int[] tmp = new int[3];
        for (int j = 0; j < 3; j++) {//
            tmp[j] = this.surfaces[adjacentSurfaceColor[color][0]].color[allEdgesXY[0][j][0]][allEdgesXY[0][j][1]];
        }
        for (int i = 0; i < 4 - 1; i++) for (int j = 0; j < 3; j++) {
            this.surfaces[adjacentSurfaceColor[color][i]].color[allEdgesXY[i][j][0]][allEdgesXY[i][j][1]] =
                    this.surfaces[adjacentSurfaceColor[color][i+1]].color[allEdgesXY[i+1][j][0]][allEdgesXY[i+1][j][1]];
        }
        for (int j = 0; j < 3; j++) {
            this.surfaces[adjacentSurfaceColor[color][3]].color[allEdgesXY[3][j][0]][allEdgesXY[3][j][1]] = tmp[j];
        }
    }

    public void rotateClockwise(int color) {
        for (int i = 0; i < 3; i++) rotateCounterclockwise(color);
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < 6; i++) {
            s += surfaces[i].toString();
            if (i < 5) s += "\n\n";
        }
        return s;
    }
}
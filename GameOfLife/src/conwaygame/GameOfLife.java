package conwaygame;
import java.util.ArrayList;
/**
 * Conway's Game of Life Class holds various methods that will
 * progress the state of the game's board through it's many iterations/generations.
 *
 * Rules 
 * Alive cells with 0-1 neighbors die of loneliness.
 * Alive cells with >=4 neighbors die of overpopulation.
 * Alive cells with 2-3 neighbors survive.
 * Dead cells with exactly 3 neighbors become alive by reproduction.

 * @author Seth Kelley 
 * @author Maxwell Goldberg
 */
public class GameOfLife {

    // Instance variables
    private static final boolean ALIVE = true;
    private static final boolean  DEAD = false;

    private boolean[][] grid;    // The board has the current generation of cells
    private int totalAliveCells; // Total number of alive cells in the grid (board)

    /**
    * Default Constructor which creates a small 5x5 grid with five alive cells.
    * This variation does not exceed bounds and dies off after four iterations.
    */
    public GameOfLife() {
        grid = new boolean[5][5];
        totalAliveCells = 5;
        grid[1][1] = ALIVE;
        grid[1][3] = ALIVE;
        grid[2][2] = ALIVE;
        grid[3][2] = ALIVE;
        grid[3][3] = ALIVE;
    }

    /**
    * Constructor used that will take in values to create a grid with a given number
    * of alive cells
    * @param file is the input file with the initial game pattern formatted as follows:
    * An integer representing the number of grid rows, say r
    * An integer representing the number of grid columns, say c
    * Number of r lines, each containing c true or false values (true denotes an ALIVE cell)
    */
    public GameOfLife (String file) {

        StdIn.setFile(file); 
        int rows = StdIn.readInt();  
        int cols = StdIn.readInt(); 

        totalAliveCells = 0; 

        grid = new boolean[rows][cols]; 

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){

                if (StdIn.readBoolean() == true){
                    grid[i][j] = ALIVE; 
                    totalAliveCells++; 
                } else {
                    grid[i][j] = DEAD; 
                }

            }
        }
        
        
    }

    /**
     * Returns grid
     * @return boolean[][] for current grid
     */
    public boolean[][] getGrid () {
        return grid;
    }
    
    /**
     * Returns totalAliveCells
     * @return int for total number of alive cells in grid
     */
    public int getTotalAliveCells () {
        return totalAliveCells;
    }

    /**
     * Returns the status of the cell at (row,col): ALIVE or DEAD
     * @param row row position of the cell
     * @param col column position of the cell
     * @return true or false value "ALIVE" or "DEAD" (state of the cell)
     */
    public boolean getCellState (int row, int col) {

        boolean [][] cellStateGrid = getGrid(); 

        if (cellStateGrid[row][col] == ALIVE){
            return true; 
        }

        return false; // update this line, provided so that code compiles
    }

    /**
     * Returns true if there are any alive cells in the grid
     * @return true if there is at least one cell alive, otherwise returns false
     */
    public boolean isAlive () {

        boolean [][] checkAliveGrid = getGrid(); 

        for (int i = 0; i < checkAliveGrid.length; i++){
            for (int j = 0; j < checkAliveGrid[i].length; j++){

                if (checkAliveGrid[i][j] == ALIVE){
                    return true; 
                }

            }
        }
        
        return false; // update this line, provided so that code compiles
    }

    /**
     * Determines the number of alive cells around a given cell.
     * Each cell has 8 neighbor cells which are the cells that are 
     * horizontally, vertically, or diagonally adjacent.
     * 
     * @param col column position of the cell
     * @param row row position of the cell
     * @return neighboringCells, the number of alive cells (at most 8).
     */
    public int numOfAliveNeighbors (int row, int col) {
        
        int numAliveNeighbor = 0; 
        int neighbor_row = 0; 
        int neighbor_col = 0; 

        for (int i = row - 1; i <= row + 1; i++){
            for (int j = col - 1; j <= col + 1; j++){

                neighbor_row = i; 
                neighbor_col = j; 
                
                // checks if the index is going into the negatives
                if (neighbor_row < 0){
                    neighbor_row = grid.length - 1; 
                }

                if (neighbor_col < 0){
                    neighbor_col = grid[row].length - 1; 
                }

                // checks if index is getting larger than size of array 

                if (neighbor_row == grid.length){
                    neighbor_row = 0; 
                }

                if (neighbor_col == grid[row].length){
                    neighbor_col = 0; 
                }

                if (grid[neighbor_row][neighbor_col] == ALIVE){ 
                    numAliveNeighbor++; 
                }
            }
        }

        if (grid[row][col] == ALIVE){
            numAliveNeighbor -= 1; 
        }

        return numAliveNeighbor; 
    }

    /**
     * Creates a new grid with the next generation of the current grid using 
     * the rules for Conway's Game of Life.
     * 
     * @return boolean[][] of new grid (this is a new 2D array)
     */
    public boolean[][] computeNewGrid () {
        
        boolean [][] newGeneration = new boolean[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                newGeneration[i][j] = grid[i][j]; 
            }
        }

        int aliveNeighbor = 0; 

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                
                aliveNeighbor = numOfAliveNeighbors(i, j); 


                if (grid[i][j] == ALIVE){
                    if (aliveNeighbor <= 3 || aliveNeighbor >= 2){
                        newGeneration[i][j] = ALIVE; 
                    }

                    if (aliveNeighbor <= 1){
                        newGeneration[i][j] = DEAD;
                    }

                    if (aliveNeighbor >= 4){
                        newGeneration[i][j] = DEAD;
                    }

                } else {
                    if (aliveNeighbor == 3){
                        newGeneration[i][j] = ALIVE;
                    }
                }

            }
        }
        
        return newGeneration; 
    }

    /**
     * Updates the current grid (the grid instance variable) with the grid denoting
     * the next generation of cells computed by computeNewGrid().
     * 
     * Updates totalAliveCells instance variable
     */
    public void nextGeneration () {        
        grid = computeNewGrid(); 

        totalAliveCells = 0; 

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] == ALIVE){
                    totalAliveCells++; 
                }
            }
        }


    }

    /**
     * Updates the current grid with the grid computed after multiple (n) generations. 
     * @param n number of iterations that the grid will go through to compute a new grid
     */
    public void nextGeneration (int n) {

        for (int i = 0; i < n; i++){
            nextGeneration();  
        }
    }

    /**
     * Determines the number of separate cell communities in the grid
     * @return the number of communities in the grid, communities can be formed from edges
     */
    public int numOfCommunities() {
        
        
        int numCommunities = 0; 
        int row_length = grid.length; 
        int col_length = grid[0].length; 

        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(row_length,col_length);        

        int neighbor_row = 0; 
        int neighbor_col = 0; 

        for (int r = 0; r < row_length; r++){
            for (int c = 0; c < col_length; c++){

                for (int i = r - 1; i <= r + 1; i++){
                    for (int j = c - 1; j <= c + 1; j++){
        
                        neighbor_row = i; 
                        neighbor_col = j; 
                        
                        // checks if the index is going into the negatives
                        if (neighbor_row < 0){
                            neighbor_row = grid.length - 1; 
                        }
        
                        if (neighbor_col < 0){
                            neighbor_col = grid[0].length - 1; 
                        }
        
                        // checks if index is getting larger than size of array 
        
                        if (neighbor_row == grid.length){
                            neighbor_row = 0; 
                        }
        
                        if (neighbor_col == grid[0].length){
                            neighbor_col = 0; 
                        }
        
                        if (grid[neighbor_row][neighbor_col] == ALIVE && grid[r][c] == ALIVE){ 
                            
                            //if (r != neighbor_row && c != neighbor_col){
                                uf.union(neighbor_row, neighbor_col, r, c); 
                            //}
                            

                            
                        }
                    }
                }
        
            }
        }



        int [] parents = new int [row_length*col_length]; 
        int parents_counter = 0; 

        for (int i = 0; i < row_length; i++){
            for (int j = 0; j < col_length; j++){
                parents[parents_counter] = uf.find(i, j);
                parents_counter++; 
            }
        }

        /*
        for (int i = 0; i < parents.length; i++){
            System.out.print(parents[i] + " "); 
            if (i % 9 == 0 && i != 0){
                System.out.println(); 
            }
        }
        */


        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] == ALIVE){
                    // this counts how many single-cell communities there are
                    if (numOfAliveNeighbors(i, j) == 0){
                        numCommunities++; 
                    }

                } 
            }
        }

        ArrayList <Integer> parent_repeats = new ArrayList <Integer>(); 

        for (int i = 0; i < parents.length; i++){
            for (int j = i + 1; j < parents.length; j++){
                if (parents[i] == parents[j]){
                    parent_repeats.add(parents[j]); 
                }
            }
        }

        /* 
        for (int i = 0; i < parent_repeats.size(); i++){
            System.out.println(parent_repeats.get(i) + " "); 
        }
        */
        int distinct_check = 1;

        for (int i = 1; i < parent_repeats.size(); i++) {
            int j = 0;
            for (j = 0; j < i; j++)
                if (parent_repeats.get(i) == parent_repeats.get(j)){
                    break;
                }

        if (i == j)
            distinct_check++;
        } 

        
        numCommunities += distinct_check; 

        // check for 0 communities (all cells are dead) 
        if (totalAliveCells == 0){
            numCommunities = 0; 
        }

        return numCommunities; // update this line, provided so that code compiles
    }


     
}


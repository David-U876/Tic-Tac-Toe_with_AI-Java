package tictactoe;

import java.util.Scanner;


public class Main {
    final static String cap = "---------";
    static Coordinate userCoord = new Coordinate();
    static int playerX = -1; // 0 for person, 1 for easy, 2 for medium, 3 for hard
    static int playerO = -1; // 0 for person, 1 for easy, 2 for medium, 3 for hard
    static int turnCount;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("To start the game, input: start <playerX> <playerO>");
        System.out.println("<playerX>/<playerO> can be 'user', 'easy', 'medium', or 'hard'.");
        
        // process user input, start command, user or easy, user or easy
        while (true) {
            if (!isStartMenu(userInput.nextLine())) break;
        }
        if (playerX != -1 && playerO != -1) {
            //Initialize mainBoard
            char[][] mainBoard = initializeBoard();
            //print starting mainBoard
            printBoard(mainBoard);

            do {
                gameManager(playerX, playerO, calcTurn(turnCount), mainBoard);
                printBoard(mainBoard);
                turnCount++;
            } while (!isFinished(mainBoard));
        }
    }

    private static void gameManager(int playerX, int playerO, char turn, char[][] board) {
        if (turn == 'X') {
            makeMove(playerX, turn, board);
        } else if (turn == 'O') {
            makeMove(playerO, turn, board);
        }
    }

    private static void makeMove(int player, char turn, char[][] board) {
        switch (player) {
            case 0:
                userMove(board, turn);
                break;
            case 1:
                easyMove(board, turn);
                break;
            case 2:
                mediumMove(board, turn);
                break;
            case 3:
                hardMove(board, turn);
                break;
        }
    }

    private static void hardMove(char[][] board, char turn) {
        System.out.println("Making move level \"hard\"");
        Coordinate bestMove = minimax(board, turn, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).bestMove;
        processCoordInput(board, bestMove, turn);
    }
    
    private static MoveResult minimax(char[][] board, char turn, int depth, int alpha, int beta) {
        if (isFinished(board, false)) {
            return new MoveResult(evaluateBoard(board), null);
        }
    
        Coordinate bestMove = null;
        int bestScore = (turn == 'X') ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    
        for (Coordinate move : getAllPossibleMoves(board)) {
            processCoordInput(board, move, turn);
            int score = minimax(board, (turn == 'X') ? 'O' : 'X', depth + 1, alpha, beta).score;
    
            board[move.getX() - 1][move.getY() - 1] = '_'; // Undo the move
    
            if (turn == 'X') {
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                alpha = Math.max(alpha, bestScore);
            } else {
                if (score < bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
                beta = Math.min(beta, bestScore);
            }
    
            if (beta <= alpha) {
                break; // Alpha-beta pruning
            }
        }
    
        return new MoveResult(bestScore, bestMove);
    }
    
    private static int evaluateBoard(char[][] board) {
        char winner = ' ';
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != '_' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0];
            }
            if (board[0][i] != '_' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winner = board[0][i];
            }
        }
        if (board[0][0] != '_' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
        }
        if (board[0][2] != '_' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2];
        }
        if (winner == 'X') return 10;
        if (winner == 'O') return -10;
        return 0; // If the game is still ongoing or a draw.
    }
    
    private static class MoveResult {
        int score;
        Coordinate bestMove;
    
        MoveResult(int score, Coordinate bestMove) {
            this.score = score;
            this.bestMove = bestMove;
        }
    }

    private static void mediumMove(char[][] board, char turn) {
        System.out.println("Making move level \"medium\"");

        // Get all possible moves from the board
        Coordinate[] allPossibleMoves = getAllPossibleMoves(board);

        // Check for winning move
        for (Coordinate coord : allPossibleMoves) {
            if (isWinningMove(coord, board, turn)) {
                processCoordInput(board, coord, turn);
                return;
            }
        }

        // Check for blocking move
        for (Coordinate coord : allPossibleMoves) {
            if (isBlockingMove(board, coord, turn)) {
                processCoordInput(board, coord, turn);
                return;
            }
        }

        // Fall back to a random move
        generateRandomMove(board, turn);
    }

    // Extracted method to fetch all possible moves
    private static Coordinate[] getAllPossibleMoves(char[][] board) {
        Coordinate[] allPossibleMoves = new Coordinate[0];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    Coordinate tempCoord = new Coordinate();
                    tempCoord.setCoordinate(i + 1, j + 1); // Creates a temp coordinate

                    // Expanding the array to add the new coordinate
                    Coordinate[] tempPossibleMoves = new Coordinate[allPossibleMoves.length + 1];
                    System.arraycopy(allPossibleMoves, 0, tempPossibleMoves, 0, allPossibleMoves.length);
                    tempPossibleMoves[allPossibleMoves.length] = tempCoord;

                    // Update main array
                    allPossibleMoves = tempPossibleMoves;
                }
            }
        }
        return allPossibleMoves;
    }

    private static boolean isWinningMove(Coordinate coord,char[][] board, char turn) {
        char[][] testBoard = new char[3][3];
        for (int i = 0; i < 3; i++) testBoard[i] = board[i].clone(); // Deep copy
        processCoordInput(testBoard, coord, turn);
        if (isFinished(testBoard, false)) {
            System.out.println("isWinningMove");
            return true;
        }
        return false;
    }

    private static boolean isBlockingMove(char[][] board, Coordinate coord, char turn) {
        char[][] testBoard = new char[3][3];
        for (int i = 0; i < 3; i++) testBoard[i] = board[i].clone(); // Deep copy
        char newTurn = (turn == 'X') ? 'O' : 'X';
        processCoordInput(testBoard, coord, newTurn);
        if (isFinished(testBoard, false)) {
            System.out.println("isBlockingMove");
            return true;
        }
        return false;
    }

    private static void userMove(char[][] board, char turn) {
        Scanner userInput = new Scanner(System.in);
        do {
            System.out.print("Enter the coordinates: ");
            String str = userInput.nextLine();
            userCoord.setCoordinate(str);
        } while (!processCoordInput(board, userCoord, turn));
    }

    private static boolean isStartMenu(String input) {
        try {
            String[] words = input.trim().split(" ");
            if (words.length != 3 && words.length != 1) {
                throw new IllegalArgumentException("Bad parameters! Please Try again!");
            }
            if (words[0].equals("start")) {
                switch (words[1]) {
                    case "user" -> playerX = 0;
                    case "easy" -> playerX = 1;
                    case "medium" -> playerX = 2;
                    case "hard" -> playerX = 3;
                    default -> throw new IllegalArgumentException("Bad parameters! Please Try again!");
                }
                switch (words[2]) {
                    case "user" -> playerO = 0;
                    case "easy" -> playerO = 1;
                    case "medium" -> playerO = 2;
                    case "hard" -> playerO = 3;
                    default -> throw new IllegalArgumentException("Bad parameters! Please Try again!");
                }
            } else if (words[0].equals("exit")) {
                return false;
            }
            else {
                throw new IllegalArgumentException("Bad parameters! Please Try again!");
                }
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Bad parameters!");
            return true;
        }
    }

    private static void easyMove(char[][] board, char turn) {
        System.out.println("Making move level \"easy\"");
        generateRandomMove(board,calcTurn(turn));
    }

    private static void generateRandomMove(char[][] board,char turn) {
        Coordinate coord = new Coordinate();
        int int1 = (int) (Math.random() * 3 + 1);
        int int2 = (int) (Math.random() * 3 + 1);
        coord.setCoordinate(int1, int2);
        if (!processCoordInput(board, coord, turn)) {
            generateRandomMove(board,turn);
        }
    }
    
    public static char calcTurn(int turnCount){
        return turnCount % 2 == 0 ? 'X' : 'O';
    }

    public static char[][] initializeBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '_';
            }
        }
        return board;
    }

    /**
     * prints the board
     */
    public static void printBoard(char[][] board) {
        System.out.println(cap);
        for (char[] row : board) {
            System.out.print("| ");
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println("|");
        }
        System.out.println(cap);
    }

    /**
     * Returns false if game is still going; Returns true if game is over.
     */
    public static boolean isFinished(char[][] board, boolean print) {
        int countX = 0;
        int countO = 0;
        int countEmpty = 0;
        int countWinners = 0;
        char winner = ' ';
        // gets count of each token
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == 'X') {
                    countX++;
                } else if (cell == 'O') {
                    countO++;
                } else {
                    countEmpty++;
                }
            }
        }
        // TODO: Get rid of this? If the check is needed it needs to be reimplemented, it breaks checking for blocking moves.
        /*
 Checks for even turn count
        if (abs(countX - countO) >= 2) {
            if (print) {
                System.out.println("Impossible");
            }
            return true;
        }
 Checks for winners
*/
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] != '_') {
                    countWinners++;
                    winner = board[i][0];
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] != '_') {
                    countWinners++;
                    winner = board[0][i];
                }
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] != '_') {
                countWinners++;
                winner = board[0][0];
            }
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] != '_') {
                countWinners++;
                winner = board[0][2];
            }
        }
        if (countWinners == 0 && countEmpty > 0) {
            return false;
        }
        if (countWinners > 0) { // changed from ==1 to >0 for the scenario below TODO: remove this comment when the todo below is addressed.
            if (print) {
                System.out.println(winner + " wins");
            }
            return true;
        }
        // TODO: is the below needed? it is possible to complete 2 lines of 3 in normal expected gameplay. This should be deletable now that we don't take a user starting position.
//        } else if (countWinners > 1) {
//            System.out.println("Impossible");
//            return true;
//        }
        if (countWinners == 0 && countEmpty == 0) {
            if (print) {
                System.out.println("Draw");
            }
        }
        return true;
    }

    public static boolean isFinished(char[][] board) {
        return isFinished(board, true);
    }

    /**
     * takes the user input validates it's a valid move and returns true if move was successful.
     */
    public static boolean processCoordInput(char[][] board, Coordinate Coord, char turn) {
        if (Coord.getX() == 0 || Coord.getY() == 0) {
            return false;
        }
        if (Coord.getX() < 1 || Coord.getX() > 3 || Coord.getY() < 1 || Coord.getY() > 3) {
            System.out.println("Coordinates should be from 1 to 3!");
            return false;
        }
        if (board[Coord.getX() - 1][Coord.getY() - 1] != '_') {
            System.out.println("This cell is occupied! Choose another one!");
            return false;
        }
        board[Coord.getX() - 1][Coord.getY() - 1] = turn;
        return true;
    }

    static class Coordinate {
        private int x;
        private int y;
        public int getX() {
            return this.x;
        }
        public int getY() {
            return this.y;
        }

        /**
         * Sets coordinate after validating the input and catching common errors
         */
        public void setCoordinate(String str) {
            String[] parts = str.split(" ");
            int tempX = 0;
            int tempY = 0;
            try {
                tempX = Integer.parseInt(parts[0]);
                tempY = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You should input exactly two numbers separated by a single space!");
            }
            this.x = tempX;
            this.y = tempY;
        }

        public void setCoordinate(int int1, int int2) {
            this.x = int1;
            this.y = int2;
        }


    }
}
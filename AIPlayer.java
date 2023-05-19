package ataxx;

import java.util.ArrayList;

/**
 * A Player that computes its own moves.
 */
class AIPlayer extends Player {

    /**
     * Maximum minimax search depth before going to static evaluation.
     */
    private static final int MAX_DEPTH = 5;

    /**
     * A position magnitude indicating a win (for red if positive, blue
     * if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;

    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;


    /**
     * A new AIPlayer for GAME that will play MYCOLOR.
     * SEED is used to initialize a random-number generator,
     * increase the value of SEED would make the AIPlayer move automatically.
     * Identical seeds produce identical behaviour.
     */
    AIPlayer(Game game, PieceState myColor, long seed) {
        super(game, myColor);
    }

    /**
     * Indicates that this player is an automatic player.
     *
     * @return true, indicating that this player is automatic
     */
    @Override
    boolean isAuto() {
        return true;
    }

    /**
     * Returns the next Ataxx move as a string.
     *
     * @return the string representation of the next Ataxx move
     */
    @Override
    String getAtaxxMove() {
        Move move = findMove();
        getAtaxxGame().reportMove(move, getMyState());
        return move.toString();
    }

    /**
     * Return a move for me from the current position, assuming there
     * is a move.
     */
    private Move findMove() {
        Board b = new Board(getAtaxxBoard());
        lastFoundMove = null;
        if (b.nextMove() == PieceState.RED) {
            findMove(b, MAX_DEPTH, true, 1, -INFTY, INFTY);
        } else if (b.nextMove() == PieceState.BLUE) {
            findMove(b, MAX_DEPTH, true, -1, -INFTY, INFTY);
        }
        if (lastFoundMove == null) {
            lastFoundMove = Move.pass();
        }
        return lastFoundMove;
    }

    /**
     * Finds and returns a move for the current player using a recursive minimax algorithm.
     *
     * @param board    the current state of the game board
     * @param depth    the current depth of the minimax search
     * @param saveMove indicates whether the move should be saved for later
     * @param sense    the color of the current player (1 for red, -1 for blue)
     * @param alpha    the current alpha value for the alpha-beta pruning
     * @param beta     the current beta value for the alpha-beta pruning
     * @return the value of the best move for the current player
     */
    private int findMove(Board board, int depth, boolean saveMove, int sense, int alpha, int beta) {
        // Ensure that board is not null
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null.");
        }

        // Ensure that depth is not negative
        if (depth < 0) {
            throw new IllegalArgumentException("Depth cannot be negative.");
        }

        // Ensure that alpha and beta are valid
        if (alpha > beta) {
            throw new IllegalArgumentException("Alpha cannot be greater than beta.");
        }

        int value = 0;
        int currPlayer = board.getColorNums(board.nextMove());
        int oppPlayer = board.getColorNums(board.nextMove().opposite());
        PieceState winner = board.getWinner();
        if (winner == board.nextMove()) {
            return WINNING_VALUE;
        } else if (winner == board.nextMove().opposite()) {
            return -WINNING_VALUE;
        } else {
            if (depth == 0) {
                return currPlayer - oppPlayer;
            } else if (sense == 1) {
                value = -INFTY;
                ArrayList<Move> listOfMoves = possibleMoves(board, board.nextMove());
                for (Move move : listOfMoves) {
                    Board copyBoard = new Board(board);
                    copyBoard.createMove(move);
                    int possible = findMove(copyBoard, depth - 1, false, -1, alpha, beta);
                    if (saveMove && possible > value) lastFoundMove = move;
                    value = Math.max(value, possible);
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha) break;
                    return value;
                }
            } else {
                value = INFTY;
                ArrayList<Move> listOfMoves = possibleMoves(board, board.nextMove());
                for (Move move : listOfMoves) {
                    Board copyBoard = new Board(board);
                    copyBoard.createMove(move);
                    int possible = findMove(copyBoard, depth - 1, false, 1, alpha, beta);
                    if (saveMove && possible < value) lastFoundMove = move;
                    value = Math.min(value, possible);
                    beta = Math.min(beta, value);
                    if (beta <= alpha) break;
                    return value;
                }
            }
            return 0;
        }
    }


    /**
     * The move found by the last call to the findMove method above.
     */
    private Move lastFoundMove;


    /**
     * Return all possible moves for a color.
     *
     * @param board   the current board.
     * @param myColor the specified color.
     * @return an ArrayList of all possible moves for the specified color.
     */
    private ArrayList<Move> possibleMoves(Board board, PieceState myColor) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (char row = '7'; row >= '1'; row--) {
            for (char col = 'a'; col <= 'g'; col++) {
                int index = Board.index(col, row);
                if (board.getContent(index) == myColor) {
                    ArrayList<Move> addMoves = assistPossibleMoves(board, row, col);
                    possibleMoves.addAll(addMoves);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Returns an Arraylist of legal moves.
     *
     * @param board the board for testing
     * @param row   the row coordinate of the center
     * @param col   the col coordinate of the center
     */
    private ArrayList<Move>
    assistPossibleMoves(Board board, char row, char col) {
        ArrayList<Move> assistPossibleMoves = new ArrayList<>();
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                if (i != 0 || j != 0) {
                    char row2 = (char) (row + j);
                    char col2 = (char) (col + i);
                    Move currMove = Move.move(col, row, col2, row2);
                    if (board.moveLegal(currMove)) {
                        assistPossibleMoves.add(currMove);
                    }
                }
            }
        }
        return assistPossibleMoves;
    }
}

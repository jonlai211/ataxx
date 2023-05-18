package ataxx;

import java.util.ArrayList;

// Final Project Part A.2 Ataxx AI Player (A group project)

/** A Player that computes its own moves. */
class AIPlayer extends Player {

    /** Maximum minimax \\search depth before going to static evaluation. */
    private static final int MAX_DEPTH = 5;
    /** A positsion magnitude indicating a win (for red if positive, blue
     *  if negative). */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 1;
    /** A magnitude greater than a normal value. */
    private static final int INFTY = Integer.MAX_VALUE;

    
    /** A new AIPlayer for GAME that will play MYCOLOR.
     *  SEED is used to initialize a random-number generator,
	 *  increase the value of SEED would make the AIPlayer move automatically.
     *  Identical seeds produce identical behaviour. */
    AIPlayer(Game game, PieceState myColor, long seed) {
        super(game, myColor);
    }

    @Override
    boolean isAuto() {
        return true;
    }

    @Override
    String getAtaxxMove() {
        Move move = findMove();
        getAtaxxGame().reportMove(move, getMyState());
        return move.toString();
    }

    /** Return a move for me from the current position, assuming there
     *  is a move. */
    private Move findMove() {
        Board b = new Board(getAtaxxBoard());
        lastFoundMove = null;

        // Here we just have the simple AI to randomly move.
        // However, it does not meet with the requirements of Part A.2.
        // Therefore, the following codes should be modified
        // in order to meet with the requirements of Part A.2.
        // You can create add your own method and put your method here.
		
        ArrayList<Move> listOfMoves = possibleMoves(b, b.nextMove());
        int moveArrayLength = listOfMoves.size();
        int randomIndex = (int) (Math.random() * moveArrayLength);
        for(int i = 0; i < moveArrayLength; i++){
            if (i == randomIndex){
                b.createMove(listOfMoves.get(i));
                lastFoundMove = listOfMoves.get(i);
            }
        }



        // Please do not change the codes below
        if (lastFoundMove == null) {
            lastFoundMove = Move.pass();
        }
        return lastFoundMove;
    }

    private int findMove(Board board, int depth, boolean saveMove, int sense, int alpha, int beta) {
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


    /** The move found by the last call to the findMove method above. */
    private Move lastFoundMove;


    /** Return all possible moves for a color.
     * @param board the current board.
     * @param myColor the specified color.
     * @return an ArrayList of all possible moves for the specified color. */
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

    /** Returns an Arraylist of legal moves.
     * @param board the board for testing
     * @param row the row coordinate of the center
     * @param col the col coordinate of the center */
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

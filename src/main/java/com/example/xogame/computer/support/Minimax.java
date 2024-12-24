package com.example.xogame.computer.support;

import com.example.xogame.data.match.Game;
import com.example.xogame.data.match.Move;
import com.example.xogame.data.match.Pocket;

import java.util.ArrayList;
import java.util.Random;

/**
 * Thuật toán Minimax với cắt tỉa Alpha-Beta cho trò chơi.
 * Sử dụng để tìm nước đi tốt nhất cho một người chơi trong trò chơi.
 */
public class Minimax {
    private String player;
    private final int DEPTH_MAX;

    /**
     * Khởi tạo thuật toán Minimax với độ sâu tối đa cho phép.
     *
     * @param depth Độ sâu tối đa cho thuật toán.
     */
    public Minimax(int depth) {
        this.DEPTH_MAX = depth;
    }

    /**
     * Tìm nước đi tốt nhất cho trò chơi hiện tại.
     *
     * @param game Trạng thái hiện tại của trò chơi.
     * @return Nước đi tốt nhất cho người chơi hiện tại.
     */
    public Move findBestMove(Game game) {
        this.player = game.getStep() % 2 == 1 ? game.getPlayerB() : game.getPlayerA();
        int step = game.getStep() + 1;

        ArrayList<Move> bestMove = new ArrayList<>();
        int bestVal = Integer.MIN_VALUE;
        Move[][] squares = game.getSquares();

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 3; ++col) {
                for (int size = 0; size < 3; ++size) {
                    Move square = game.getSquares()[row][col];
                    if (square == null || (!square.getUsername().equals(player) && square.getSize() < size)) {
                        // Move
                        Move move = new Move(player, game.getStep() + 1, row, col, size);
                        if (!game.move(move)) continue;

                        // Minimax
                        int val = this._minimax(game, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

                        // Un_move
                        game.getMoves().remove(squares[row][col]);
                        squares[row][col] = square;
                        Pocket pocket = player.equals(game.getPlayerA()) ? game.getPocketA() : game.getPocketB();
                        pocket.increase(size);

                        if (val > bestVal) {
                            bestMove.clear();
                            bestMove.add(move);
                            bestVal = val;
                            continue;
                        }
                        if (val != bestVal) continue;
                        bestMove.add(move);
                    }
                }
            }
        }
        Random rand = new Random();
        if (bestMove.isEmpty()) return null;
        return bestMove.get(rand.nextInt(bestMove.size()));
    }

    /**
     * Thuật toán Minimax chính.
     *
     * @param game   Trạng thái hiện tại của trò chơi.
     * @param depth  Độ sâu hiện tại của cây quyết định.
     * @param alpha  Giá trị tốt nhất hiện tại của người chơi tối đa.
     * @param beta   Giá trị tốt nhất hiện tại của người chơi tối thiểu.
     * @param isMax  True nếu lượt của người chơi tối đa, false nếu lượt của người chơi tối thiểu.
     * @return Giá trị tốt nhất mà người chơi có thể đạt được từ trạng thái hiện tại.
     */
    private int _minimax(Game game, int depth, int alpha, int beta, Boolean isMax) {
        int best;

        String winner = game.getWinner();
        if (winner != null) {
            if (winner.equals("N/A")) return 0;
            if (winner.equals(this.player)) return 10;
            return -10;
        }

        if (depth == this.DEPTH_MAX) return 5;

        if (isMax) {
            best = Integer.MIN_VALUE;
            Move[][] squares = game.getSquares();
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 3; ++col) {
                    for (int size = 0; size < 3; ++size) {
                        Move square = squares[row][col];
                        if (square == null || (!square.getUsername().equals(player) && square.getSize() < size)) {
                            // Move
                            Move move = new Move(player, game.getStep() + 1, row, col, size);
                            if (!game.move(move)) continue;

                            // Minimax
                            best = Math.max(best, this._minimax(game, depth + 1, alpha, beta, !isMax));

                            // Un_move
                            game.getMoves().remove(squares[row][col]);
                            squares[row][col] = square;
                            Pocket pocket = player.equals(game.getPlayerA()) ? game.getPocketA() : game.getPocketB();
                            pocket.increase(size);

                            // Alpha-Beta pruning
                            alpha = Math.max(alpha, best);
                            if (beta <= alpha) break;
                        }
                    }
                }
            }
        } else {
            best = Integer.MAX_VALUE;
            Move[][] squares = game.getSquares();
            for (int row = 0; row < 3; ++row) {
                for (int col = 0; col < 3; ++col) {
                    for (int size = 0; size < 3; ++size) {
                        Move square = squares[row][col];
                        if (square == null || (!square.getUsername().equals(game.getOpponent(player)) && square.getSize() < size)) {
                            // Move
                            Move move = new Move(game.getOpponent(player), game.getStep() + 1, row, col, size);
                            if (!game.move(move)) continue;

                            // Minimax
                            best = Math.min(best, this._minimax(game, depth + 1, alpha, beta, !isMax));

                            // Un_move
                            game.getMoves().remove(squares[row][col]);
                            squares[row][col] = square;
                            Pocket pocket = game.getOpponent(player).equals(game.getPlayerA()) ? game.getPocketA() : game.getPocketB();
                            pocket.increase(size);

                            // Alpha-Beta pruning
                            beta = Math.min(beta, best);
                            if (beta <= alpha) break;
                        }
                    }
                }
            }
        }
        if (best == Integer.MIN_VALUE) return 9;
        if (best == Integer.MAX_VALUE) return -9;
        return best;
    }
}

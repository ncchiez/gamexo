package com.example.xogame.data.match;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Game đại diện cho một trò chơi giữa hai người chơi, quản lý các nước đi và trạng thái của trò chơi.
 */
public class Game {
    private final String playerA;
    private final String playerB;
    private final List<Move> moves;
    private final Move[][] squares = new Move[3][3];
    private final Pocket pocketA;
    private final Pocket pocketB;
    private boolean isGameOver;
    private String winner;

    /**
     * Khởi tạo một trò chơi mới với hai người chơi.
     *
     * @param playerA Tên của người chơi A.
     * @param playerB Tên của người chơi B.
     */
    public Game(String playerA, String playerB) {
        moves = new ArrayList<>();
        this.isGameOver = false;
        this.playerA = playerA;
        this.playerB = playerB;
        this.pocketA = new Pocket();
        this.pocketB = new Pocket();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                squares[i][j] = null;
            }
        }
    }

    /**
     * Kết thúc trò chơi và xác định người thắng cuộc.
     *
     * @param winner Tên của người thắng cuộc.
     */
    public void endGame(String winner) {
        if (this.isGameOver) return;

        this.isGameOver = true;
        this.winner = winner;
    }

    // Các phương thức getter

    /**
     * Lấy tên của người chơi A.
     *
     * @return Tên của người chơi A.
     */
    public String getPlayerA() {
        return playerA;
    }

    /**
     * Lấy tên của người chơi B.
     *
     * @return Tên của người chơi B.
     */
    public String getPlayerB() {
        return playerB;
    }

    /**
     * Lấy tên của người chơi đối thủ.
     *
     * @param username Tên của người chơi hiện tại.
     * @return Tên của người chơi đối thủ.
     */
    public String getOpponent(String username) {
        if (username.equals(playerA)) return playerB;
        if (username.equals(playerB)) return playerA;

        return null;
    }

    // Các phương thức liên quan đến Pocket

    /**
     * Lấy Pocket của người chơi A.
     *
     * @return Pocket của người chơi A.
     */
    public Pocket getPocketA() {
        return pocketA;
    }

    /**
     * Lấy Pocket của người chơi B.
     *
     * @return Pocket của người chơi B.
     */
    public Pocket getPocketB() {
        return pocketB;
    }

    /**
     * Lấy Pocket của người chơi dựa trên tên đăng nhập.
     *
     * @param username Tên của người chơi.
     * @return Pocket của người chơi tương ứng.
     */
    public Pocket getPocket(String username) {
        if (username.equals(playerA)) return pocketA;
        if (username.equals(playerB)) return pocketB;
        return null;
    }

    // Các phương thức quản lý nước đi

    /**
     * Lấy mảng ô cờ hiện tại.
     *
     * @return Mảng ô cờ hiện tại.
     */
    public Move[][] getSquares() {
        return squares;
    }

    /**
     * Lấy danh sách các nước đi đã thực hiện.
     *
     * @return Danh sách các nước đi.
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Kiểm tra xem trò chơi có kết thúc hay không.
     *
     * @return true nếu trò chơi đã kết thúc, false nếu chưa.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Lấy số bước đã thực hiện trong trò chơi.
     *
     * @return Số bước.
     */
    public int getStep() {
        return moves.size();
    }

    /**
     * Hoàn tác nước đi cuối cùng.
     */
    public void undoMove() {
        Move lastMove = moves.getLast();
        Move oldMove = null;
        for (int i = 0; i < moves.size() - 1; i++) {
            if (moves.get(i).getRow() == lastMove.getRow() && moves.get(i).getCol() == lastMove.getCol()) {
                oldMove = moves.get(i);
            }
        }
        squares[lastMove.getRow()][lastMove.getCol()] = oldMove;
        this.getPocket(lastMove.getUsername()).increase(lastMove.getSize());
        moves.removeLast();
    }

    /**
     * Thực hiện một nước đi.
     *
     * @param m Nước đi cần thực hiện.
     * @return true nếu nước đi hợp lệ và được thực hiện, false nếu không.
     */
    public boolean move(Move m) {
        if (isGameOver) return false;
        if (!correctMove(m)) return false;

        // Kiểm tra số lượng quân có đủ không
        Pocket pocket = m.getUsername().equals(playerA) ? pocketA : pocketB;
        if (pocket.useSize(m.getSize())) {
            this.moves.add(m);
            this.squares[m.getRow()][m.getCol()] = m;
            return true;
        }

        return false;
    }

    /**
     * Lấy tên của người chơi hiện tại.
     *
     * @return Tên của người chơi hiện tại.
     */
    public String getCurrentPlayer() {
        return (this.moves.size() % 2 == 0) ? playerA : playerB;
    }

    /**
     * Kiểm tra xem nước đi có hợp lệ hay không.
     *
     * @param move Nước đi cần kiểm tra.
     * @return true nếu nước đi hợp lệ, false nếu không.
     */
    private boolean correctMove(Move move) {
        // Kiểm tra Step
        if (move.getStep() != this.moves.size() + 1) return false;

        // Kiểm tra token
        if (!move.getUsername().equals(this.playerA) && !move.getUsername().equals(this.playerB)) return false;
        if (!move.getUsername().equals(this.getCurrentPlayer())) return false;

        int row = move.getRow();
        int col = move.getCol();

        // Kiểm tra ô cờ trống
        Move square = squares[row][col];
        if (square == null) return true;

        // Kiểm tra: Đánh lên ô cờ của đối thủ và size phải lớn hơn
        return !move.getUsername().equals(square.getUsername()) && move.getSize() > square.getSize();
    }

    /**
     * Lấy tên của người thắng cuộc nếu có.
     *
     * @return Tên của người thắng cuộc hoặc null nếu chưa có.
     */
    public String getWinner() {
        String winner = checkRow();
        if (winner == null) winner = checkCol();
        if (winner == null) winner = checkCross();
        if (winner != null) return winner;

        return (pocketA.isEmpty() && pocketB.isEmpty()) ? "N/A" : null;
    }

    private String checkRow() {
        for (int i = 0; i < 3; i++) {
            int count = 1;
            if (squares[i][0] == null) continue;

            for (int j = 1; j < 3; j++) {
                if (squares[i][j] == null) break;

                String prev = squares[i][j - 1].getUsername();
                String curr = squares[i][j].getUsername();

                if (prev.equals(curr)) {
                    count++;
                    if (count == 3) return curr;
                }
            }
        }
        return null;
    }

    private String checkCol() {
        for (int j = 0; j < 3; j++) {
            int count = 1;
            if (squares[0][j] == null) continue;

            for (int i = 1; i < 3; i++) {
                if (squares[i][j] == null) break;

                String prev = squares[i - 1][j].getUsername();
                String curr = squares[i][j].getUsername();

                if (prev.equals(curr)) {
                    count++;
                    if (count == 3) return curr;
                }
            }
        }
        return null;
    }

    private String checkCross() {
        Move square = squares[1][1];
        if (square == null) return null;

        String token = square.getUsername();

        if (squares[0][0] != null && squares[2][2] != null) {
            if (squares[0][0].getUsername().equals(token) && squares[2][2].getUsername().equals(token)) return token;
        }

        if (squares[0][2] != null && squares[2][0] != null) {
            if (squares[0][2].getUsername().equals(token) && squares[2][0].getUsername().equals(token)) return token;
        }

        return null;
    }

    /**
     * Hiển thị trạng thái hiện tại của bảng cờ.
     */
    public void display() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (squares[i][j] == null) {
                    System.out.print(". ");
                    continue;
                }
                System.out.print(squares[i][j].getUsername().equals(playerA) ? "x " : "o ");
            }
            System.out.println();
        }
    }
}

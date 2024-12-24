package com.example.xogame.computer;

import com.example.xogame.data.match.Game;
import com.example.xogame.data.match.Move;

/**
 * Interface đại diện cho các thuật toán máy tính trong trò chơi.
 * Cung cấp phương thức để tìm nước đi tốt nhất cho máy tính dựa trên trạng thái trò chơi hiện tại.
 */
public interface Computer {
    /**
     * Tìm nước đi tốt nhất cho máy tính dựa trên trạng thái trò chơi.
     *
     * @param game Trạng thái hiện tại của trò chơi.
     * @return Nước đi tốt nhất được chọn.
     */
    Move findBestMove(Game game);
}

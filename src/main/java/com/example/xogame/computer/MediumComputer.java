package com.example.xogame.computer;

import com.example.xogame.computer.support.Minimax;
import com.example.xogame.data.match.Game;
import com.example.xogame.data.match.Move;

/**
 * Đối tượng máy tính trung bình, sử dụng thuật toán Minimax với độ sâu cố định.
 */
public class MediumComputer implements Computer {

    /**
     * Tìm nước đi tốt nhất cho máy tính trung bình.
     *
     * @param game Trạng thái hiện tại của trò chơi.
     * @return Nước đi tốt nhất mà máy tính có thể thực hiện.
     */
    @Override
    public Move findBestMove(Game game) {
        Minimax minimax = new Minimax(5);
        return minimax.findBestMove(game);
    }
}

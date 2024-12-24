package com.example.xogame.computer;

import com.example.xogame.computer.support.Minimax;
import com.example.xogame.data.match.Game;
import com.example.xogame.data.match.Move;

/**
 * Đối tượng máy tính khó, sử dụng thuật toán Minimax với độ sâu tùy thuộc vào bước đi hiện tại.
 */
public class HardComputer implements Computer {

    /**
     * Tìm nước đi tốt nhất cho máy tính khó.
     *
     * @param game Trạng thái hiện tại của trò chơi.
     * @return Nước đi tốt nhất mà máy tính có thể thực hiện.
     */
    @Override
    public Move findBestMove(Game game) {
        Minimax minimax = new Minimax(game.getStep() + 6);
        return minimax.findBestMove(game);
    }
}

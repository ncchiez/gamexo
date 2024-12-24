package com.example.xogame.controller;

import com.example.xogame.data.match.Game;

/**
 * Lớp GameYourself đại diện cho chế độ chơi đơn của trò chơi,
 * nơi người chơi có thể chơi với chính mình trong một trận đấu offline.
 */
public class GameYourself extends GameOffline {

    /**
     * Khởi động trò chơi, khởi tạo thông tin trận đấu và thiết lập kích thước quân cờ.
     */
    @Override
    public void startGame() {
        super.startGame();
        this.game = new Game(profile.getUsername(), opponentProfile.getUsername());
        this.setNumberOfSize(
                game.getPocketA().getNumberOfSize(0),
                game.getPocketA().getNumberOfSize(1),
                game.getPocketA().getNumberOfSize(2),
                game.getPocketB().getNumberOfSize(0),
                game.getPocketB().getNumberOfSize(1),
                game.getPocketB().getNumberOfSize(2)
        );
        this.opponentSmallSize.getStyleClass().add("selected");
    }

    /**
     * Gửi thông báo từ bỏ trận đấu và kết thúc trò chơi.
     * Người thua sẽ được xác định dựa trên lượt đi hiện tại.
     */
    @Override
    void sendResign() {
        endGame(step % 2 == 0 ? profile.getUsername() : opponentProfile.getUsername(), profile.getScore());
    }
}

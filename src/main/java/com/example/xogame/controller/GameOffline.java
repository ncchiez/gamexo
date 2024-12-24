package com.example.xogame.controller;

import com.example.xogame.notification.Popup;
import com.example.xogame.notification.UnknownPopup;
import com.example.xogame.data.match.Game;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Lớp GameOffline đại diện cho các trò chơi không trực tuyến.
 * Nó mở rộng lớp GamePlay và cung cấp các phương thức để quản lý trò chơi.
 */
abstract public class GameOffline extends GamePlay {
    protected Game game;

    /**
     * Lấy đối tượng trò chơi hiện tại.
     *
     * @return Đối tượng trò chơi.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Thiết lập đối tượng trò chơi.
     *
     * @param game Đối tượng trò chơi.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Xử lý sự kiện chọn kích thước của đối thủ.
     *
     * @param event Sự kiện nhấn nút chọn kích thước.
     */
    @Override
    void opponentSelectSize(ActionEvent event) {
        if (!(event.getSource() instanceof Button button)) return;

        if (opponentSelected != null) {
            opponentSelected.getStyleClass().clear();
            opponentSelected.getStyleClass().add("selection");
        } else {
            opponentSmallSize.getStyleClass().clear();
            opponentSmallSize.getStyleClass().add("selection");
        }
        opponentSelected = button;
        opponentSelected.getStyleClass().add("selected");

        HBox parent = (HBox) button.getParent();
        this.opponentSize = opponentSelectionBox.getChildren().indexOf(parent);
    }

    /**
     * Kết thúc trò chơi.
     *
     * @param winnerUsername Tên người thắng cuộc.
     * @param score Điểm số của người chơi.
     */
    @Override
    public void endGame(String winnerUsername, int score) {
        if (isGameOver) return;

        this.setGameOver(true);
        game.endGame(winnerUsername);

        // Đổi chữ button
        this.getBtn().setText("Exit");

        if (winnerUsername.equals("N/A")) {
            Popup popup = new UnknownPopup("Trận đấu hòa!", this::removePopup);
            this.addPopup(popup);
            return;
        }

        Popup popup = new UnknownPopup("Người chơi " + winnerUsername + " chiến thắng!", this::removePopup);
        this.addPopup(popup);
    }
}

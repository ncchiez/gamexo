package com.example.xogame.controls;

import com.example.shared.Message;
import com.example.xogame.ClientSender;
import com.example.xogame.controller.GameComputer;
import com.example.xogame.controller.GameOffline;
import com.example.xogame.controller.GameOnline;
import com.example.xogame.controller.GamePlay;
import com.example.xogame.data.match.Game;
import com.example.xogame.data.match.Move;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * Lớp SquareControl đại diện cho một ô trong trò chơi.
 * Nó xử lý các hành động di chuyển trong trò chơi, cả trực tuyến và ngoại tuyến.
 */
public class SquareControl extends Button {
    private final int row;
    private final int col;
    private final GamePlay gamePlay;

    /**
     * Khởi tạo SquareControl với thông tin trò chơi và tọa độ ô.
     *
     * @param gamePlay Tham chiếu đến đối tượng GamePlay.
     * @param row      Tọa độ hàng của ô.
     * @param col      Tọa độ cột của ô.
     */
    public SquareControl(GamePlay gamePlay, int row, int col) {
        // Load FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/controls/square.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.gamePlay = gamePlay;
        this.row = row;
        this.col = col;
    }

    /**
     * Xử lý sự kiện di chuyển khi người dùng nhấn vào ô.
     *
     * @param event Sự kiện nhấn nút.
     */
    @FXML
    void move(ActionEvent event) {
        if (this.gamePlay == null) return;
        if (this.gamePlay.isGameOver()) return;
        if (this.gamePlay instanceof GameOnline) {
            moveOnline();
        } else {
            moveOffline();
        }
    }

    /**
     * Xử lý di chuyển trong trò chơi trực tuyến.
     */
    private void moveOnline() {
        try {
            // Tạo một đối tượng JSON
            JsonObject jsonObject = getJsonObject();
            if (jsonObject == null) return;

            // Tạo nội dung thông điệp
            String contentMessage = jsonObject.toString();

            // Tạo message để gửi thông điệp
            Message message = new Message("move", contentMessage, "OK");
            ClientSender.send(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý di chuyển trong trò chơi ngoại tuyến.
     */
    private void moveOffline() {
        if (!(gamePlay instanceof GameOffline gameOffline)) return;

        Game game = gameOffline.getGame();
        Move move = new Move(gameOffline.getStep() % 2 == 1 ? game.getPlayerA() : game.getPlayerB(),
                gameOffline.getStep(), row, col,
                gameOffline.getStep() % 2 == 1 ? gameOffline.getSize() : gameOffline.getOpponentSize());
        if (game.move(move)) {
            gameOffline.move(move, 0);
            gameOffline.setStep(move.getStep() + 1);

            // Kiểm tra kết thúc game
            String winner = game.getWinner();
            if (winner != null) {
                gameOffline.endGame(winner, gameOffline.getProfile().getScore());
                return;
            }
        }

        if (!(gameOffline instanceof GameComputer gameComputer)) return;
        if (gameComputer.checkTurnComputer()) {
            gameComputer.computerMove();
        }
    }

    /**
     * Tạo đối tượng JSON để gửi thông tin di chuyển trong trò chơi trực tuyến.
     *
     * @return Đối tượng JSON chứa thông tin di chuyển, hoặc null nếu không phải GameOnline.
     */
    private JsonObject getJsonObject() {
        if (!(gamePlay instanceof GameOnline gameOnline)) return null;

        JsonObject jsonObject = new JsonObject();

        // Thêm các cặp key-value vào đối tượng JSON
        jsonObject.addProperty("idMatch", gameOnline.getIdMatch());
        jsonObject.addProperty("token", gameOnline.getToken());
        jsonObject.addProperty("step", gameOnline.getStep());
        jsonObject.addProperty("row", row);
        jsonObject.addProperty("col", col);
        jsonObject.addProperty("size", gameOnline.getSize());
        return jsonObject;
    }
}

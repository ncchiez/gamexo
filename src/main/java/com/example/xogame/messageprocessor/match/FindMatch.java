package com.example.xogame.messageprocessor.match;

import com.example.shared.Message;
import com.example.xogame.data.account.Profile;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Lớp FindMatch là một triển khai của lớp MessageProcessor.
 * Lớp này xử lý yêu cầu tìm trận đấu và cập nhật giao diện người dùng với thông tin trò chơi.
 */
public class FindMatch extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho yêu cầu tìm trận đấu.
     * Phân tích thông điệp để lấy thông tin về đối thủ và các thông số trận đấu,
     * sau đó khởi tạo màn chơi và hiển thị nó cho người dùng.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        if (appViewController == null) return;

        // Tìm trận đấu thất bại
        if (status.equals("WRONG")) {
            return;
        }

        // Phân tích thông điệp
        JsonObject jsonObject = JsonParser.parseString(decryptedContent).getAsJsonObject();
        Profile opponentProfile = new Gson().fromJson(jsonObject, Profile.class);
        String idMatch = jsonObject.get("idMatch").getAsString();
        String firstPlayerUsername = jsonObject.get("firstPlayer").getAsString();
        boolean myTurn = firstPlayerUsername.equals(appViewController.getProfile().getUsername());
        int timeLimit = jsonObject.get("timeLimit").getAsInt();
        int small = jsonObject.get(myTurn ? "fSmall" : "sSmall").getAsInt();
        int medium = jsonObject.get(myTurn ? "fMedium" : "sMedium").getAsInt();
        int large = jsonObject.get(myTurn ? "fLarge" : "sLarge").getAsInt();
        int oSmall = jsonObject.get(myTurn ? "sSmall" : "fSmall").getAsInt();
        int oMedium = jsonObject.get(myTurn ? "sMedium" : "fMedium").getAsInt();
        int oLarge = jsonObject.get(myTurn ? "sLarge" : "fLarge").getAsInt();

        Platform.runLater(() -> {
            // Xóa cảnh đang tìm trận đấu, hiển thị home
            appViewController.showHome();

            // Tạo gameplay
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/xogame/view/game-online.fxml"));
            try {
                Scene sceneGamePlay = new Scene(fxmlLoader.load());
                sceneGamePlay.setFill(Color.TRANSPARENT);
                this.gameOnlineController = fxmlLoader.getController();
                this.gameOnlineController.setAppView(this.clientListener.getAppViewController());
                this.clientListener.setGamePlayController(this.gameOnlineController);

                // Thiết lập dữ liệu khởi tạo
                gameOnlineController.setToken(appViewController.getToken());
                gameOnlineController.setIdMatch(idMatch);
                gameOnlineController.setProfile(appViewController.getProfile());
                gameOnlineController.setOpponentProfile(opponentProfile);
                gameOnlineController.setNumberOfSize(small, medium, large, oSmall, oMedium, oLarge);
                gameOnlineController.startGame();
                gameOnlineController.countDown(timeLimit, myTurn);

                // Cấu hình Stage với Scene và hiển thị nó
                Stage stage = (Stage) appViewController.getContent().getScene().getWindow();
                stage.setTitle("Play!");
                stage.setScene(sceneGamePlay);
                gameOnlineController.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Tạo phản hồi cho yêu cầu tìm trận đấu.
     * Trong lớp này, phương thức trả về null vì không có phản hồi được tạo.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @return null
     */
    @Override
    protected Message generateResponse(String decryptedContent) {
        return null;
    }
}

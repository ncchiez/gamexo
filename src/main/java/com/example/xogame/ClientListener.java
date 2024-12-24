package com.example.xogame;

import com.example.shared.Message;
import com.example.xogame.controller.AppView;
import com.example.xogame.controller.GameOnline;
import com.example.xogame.messageprocessor.MessageProcessor;
import com.example.xogame.messageprocessor.factory.MessageProcessorFactory;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Lớp ClientListener chịu trách nhiệm lắng nghe và xử lý các thông điệp từ server.
 * Lớp này chạy trên một luồng riêng để xử lý các tin nhắn được gửi qua socket.
 */
public class ClientListener implements Runnable {
    private final Socket socket;
    private AppView appViewController;
    private GameOnline gameOnlineController;

    /**
     * Khởi tạo đối tượng ClientListener với một kết nối Socket.
     *
     * @param socket Socket được kết nối với server
     */
    public ClientListener(Socket socket) {
        this.socket = socket;
    }

    /**
     * Phương thức chính của luồng, lắng nghe và xử lý các thông điệp từ server.
     * Đọc đối tượng Message từ socket và sử dụng MessageProcessor để xử lý thông điệp.
     */
    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            while (true) {
                // Đọc và xử lý đối tượng Message từ server
                Message receivedMessage = (Message) objectInputStream.readObject();

                // Lấy thông tin từ Message
                String actionCode = receivedMessage.getActionCode();
                String content = receivedMessage.getContent();
                String status = receivedMessage.getStatus();

                // Tạo đối tượng MessageProcessor từ MessageProcessorFactory dựa trên actionCode
                MessageProcessor messageProcessor = MessageProcessorFactory.createDecryption(actionCode);
                messageProcessor.setClientListener(this);
                messageProcessor.setAppViewController(appViewController);
                messageProcessor.setGamePlayController(gameOnlineController);

                // Xử lý thông điệp và tạo phản hồi nếu cần
                Message responseMessage = messageProcessor.processMessage(content, status);

                // Gửi phản hồi đến server (nếu có)
                if (responseMessage != null) {
                    ClientSender.send(responseMessage);
                }

                // Hiển thị nội dung của thông điệp
                System.out.println(content);
            }
        } catch (IOException | ClassNotFoundException e) {
            // Kiểm tra và xử lý khi kết nối bị đóng hoặc reset
            if (e.getMessage().equals("Socket closed") || e.getMessage().equals("Connection reset")) {
                Platform.runLater(() -> {
                    if (appViewController != null) {
                        appViewController.logout();
                    }

                    if (gameOnlineController != null) {
                        gameOnlineController.clear();
                    }
                });
                return;
            }

            System.err.println(e.getMessage());
        }
    }

    /**
     * Lấy đối tượng AppView hiện tại.
     *
     * @return Đối tượng AppView đang được sử dụng
     */
    public AppView getAppViewController() {
        return appViewController;
    }

    /**
     * Thiết lập đối tượng AppView để quản lý giao diện.
     *
     * @param appViewController Đối tượng AppView để quản lý giao diện
     */
    public void setAppViewController(AppView appViewController) {
        this.appViewController = appViewController;
    }

    /**
     * Lấy đối tượng GameOnline hiện tại.
     *
     * @return Đối tượng GameOnline đang được sử dụng để quản lý trò chơi trực tuyến
     */
    public GameOnline getGamePlayController() {
        return gameOnlineController;
    }

    /**
     * Thiết lập đối tượng GameOnline để quản lý trò chơi trực tuyến.
     *
     * @param gameOnlineController Đối tượng GameOnline để quản lý trò chơi trực tuyến
     */
    public void setGamePlayController(GameOnline gameOnlineController) {
        this.gameOnlineController = gameOnlineController;
    }
}

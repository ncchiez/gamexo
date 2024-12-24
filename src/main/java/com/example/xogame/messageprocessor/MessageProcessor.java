package com.example.xogame.messageprocessor;

import com.example.shared.Message;
import com.example.xogame.ClientListener;
import com.example.xogame.controller.AppView;
import com.example.xogame.controller.GameOnline;

/**
 * Lớp MessageProcessor là một lớp trừu tượng để xử lý các thông điệp từ server.
 * Lớp này thực hiện giải mã thông điệp, xử lý công việc và tạo phản hồi tương ứng.
 */
public abstract class MessageProcessor {
    protected ClientListener clientListener;
    protected AppView appViewController;
    protected GameOnline gameOnlineController;

    /**
     * Xử lý một thông điệp nhận được từ server. Thông điệp sẽ được giải mã, xử lý
     * công việc cụ thể và tạo ra phản hồi.
     *
     * @param receivedMessage Nội dung thông điệp đã nhận từ server
     * @param status Trạng thái của thông điệp
     * @return Một đối tượng Message chứa phản hồi sau khi xử lý
     */
    public Message processMessage(String receivedMessage, String status) {
        // Giải mã thông điệp
        String decryptedContent = decrypt(receivedMessage);
        // Thực hiện công việc với thông điệp
        performTask(decryptedContent, status);
        // Tạo và trả về phản hồi
        return generateResponse(decryptedContent);
    }

    /**
     * Giải mã nội dung của thông điệp đã mã hóa.
     *
     * @param encryptedContent Nội dung thông điệp đã mã hóa
     * @return Nội dung sau khi giải mã (hoặc không có thay đổi nếu không cần giải mã)
     */
    protected String decrypt(String encryptedContent) {
        return encryptedContent;
    }

    /**
     * Thực hiện các công việc cần thiết dựa trên thông điệp đã giải mã và trạng thái.
     *
     * @param decryptedContent Nội dung thông điệp sau khi giải mã
     * @param status Trạng thái của thông điệp
     */
    protected abstract void performTask(String decryptedContent, String status);

    /**
     * Tạo một phản hồi dựa trên nội dung đã xử lý.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã và xử lý
     * @return Một đối tượng Message chứa phản hồi cho server
     */
    protected abstract Message generateResponse(String decryptedContent);

    /**
     * Trả về ClientListener hiện tại.
     *
     * @return Đối tượng ClientListener đang được sử dụng
     */
    public ClientListener getClientListener() {
        return clientListener;
    }

    /**
     * Thiết lập ClientListener cho MessageProcessor.
     *
     * @param clientListener Đối tượng ClientListener để liên kết với MessageProcessor
     */
    public void setClientListener(ClientListener clientListener) {
        this.clientListener = clientListener;
    }

    /**
     * Trả về AppView hiện tại.
     *
     * @return Đối tượng AppView đang được sử dụng
     */
    public AppView getAppViewController() {
        return appViewController;
    }

    /**
     * Thiết lập AppView cho MessageProcessor.
     *
     * @param appViewController Đối tượng AppView để liên kết với MessageProcessor
     */
    public void setAppViewController(AppView appViewController) {
        this.appViewController = appViewController;
    }

    /**
     * Trả về GameOnline hiện tại.
     *
     * @return Đối tượng GameOnline đang được sử dụng
     */
    public GameOnline getGamePlayController() {
        return gameOnlineController;
    }

    /**
     * Thiết lập GameOnline cho MessageProcessor.
     *
     * @param gameOnlineController Đối tượng GameOnline để liên kết với MessageProcessor
     */
    public void setGamePlayController(GameOnline gameOnlineController) {
        this.gameOnlineController = gameOnlineController;
    }
}

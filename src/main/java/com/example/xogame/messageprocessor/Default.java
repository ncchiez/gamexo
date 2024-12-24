package com.example.xogame.messageprocessor;

import com.example.shared.Message;

/**
 * Lớp Default là một triển khai cụ thể của lớp MessageProcessor.
 * Lớp này thực hiện các công việc mặc định với thông điệp nhận được,
 * không thực hiện bất kỳ tác vụ nào trong phương thức performTask.
 */
public class Default extends MessageProcessor {

    /**
     * Thực hiện công việc cho thông điệp đã giải mã.
     * Trong lớp Default, không có công việc nào được thực hiện.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {
        // Không thực hiện công việc nào
    }

    /**
     * Tạo một phản hồi cho thông điệp đã xử lý.
     * Phản hồi sẽ chứa mã hành động và nội dung đã giải mã với trạng thái "OK".
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @return Một đối tượng Message chứa mã hành động, nội dung và trạng thái "OK"
     */
    @Override
    protected Message generateResponse(String decryptedContent) {
        return new Message("action_code", decryptedContent, "OK");
    }
}

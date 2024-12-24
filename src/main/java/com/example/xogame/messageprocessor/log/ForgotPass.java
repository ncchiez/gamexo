package com.example.xogame.messageprocessor.log;

import com.example.shared.Message;
import com.example.xogame.messageprocessor.MessageProcessor;

/**
 * Lớp ForgotPass là một triển khai của lớp MessageProcessor.
 * Lớp này được sử dụng để xử lý các yêu cầu khôi phục mật khẩu.
 */
public class ForgotPass extends MessageProcessor {

    /**
     * Thực hiện các công việc cần thiết cho yêu cầu khôi phục mật khẩu.
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @param status Trạng thái của thông điệp
     */
    @Override
    protected void performTask(String decryptedContent, String status) {

    }

    /**
     *
     *
     * @param decryptedContent Nội dung thông điệp đã giải mã
     * @return null
     */
    @Override
    protected Message generateResponse(String decryptedContent) {
        return null;
    }
}

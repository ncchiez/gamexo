package com.example.xogame;

import com.example.shared.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Lớp ClientSender chịu trách nhiệm gửi đối tượng Message từ client đến server
 * thông qua kết nối socket và luồng đối tượng (ObjectOutputStream).
 */
public class ClientSender {
    private static ObjectOutputStream objectOutputStream;

    /**
     * Thiết lập luồng ObjectOutputStream dựa trên Socket được cung cấp.
     *
     * @param socket Socket được kết nối với server
     */
    public static void setSocket(Socket socket) {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gửi đối tượng Message đến server thông qua ObjectOutputStream.
     * Phương thức này đồng bộ hóa để đảm bảo việc gửi thông điệp không xảy ra đồng thời.
     *
     * @param message Đối tượng Message cần gửi
     * @throws IOException Nếu ObjectOutputStream chưa được khởi tạo hoặc xảy ra lỗi khi gửi
     */
    public synchronized static void send(Message message) throws IOException {
        if (objectOutputStream == null) throw new IOException();

        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
    }

    /**
     * Lấy ObjectOutputStream hiện tại.
     *
     * @return Đối tượng ObjectOutputStream được sử dụng để gửi thông điệp
     */
    public static ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class FileServer {
    private static final int PORT = 12345;
    private static final String FILE_PATH = "D:\\projects\\java\\cry.mp4";

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (InputStream in = new FileInputStream(FILE_PATH);
                 OutputStream out = socket.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                    out.flush(); // Ensure data is sent immediately
                }
                System.out.println("File sent successfully to " + socket.getRemoteSocketAddress());
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

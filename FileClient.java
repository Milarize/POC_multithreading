import java.io.*;
import java.net.*;

public class FileClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static final String OUTPUT_FILE_PATH = "D:\\projects\\java\\cry.mp4";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             InputStream in = socket.getInputStream();
             FileOutputStream out = new FileOutputStream(OUTPUT_FILE_PATH)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("File downloaded successfully.");
        } catch (IOException ex) {
            System.err.println("An error occurred during file download: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

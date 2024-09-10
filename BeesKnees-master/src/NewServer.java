import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class NewServer {
	public static void main(String[] args) {
		ClientHandler handler = null;
		try {
			ServerSocket server = new ServerSocket(8080);
			while(true) {		
				Socket client = server.accept();
				handler = new ClientHandler(client);
				Thread t = new Thread(handler);
				t.start();
				
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

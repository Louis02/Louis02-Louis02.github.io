import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class testServer {

	
	public static void main(String[] args) {
		try {
			ServerSocket socket = new ServerSocket(8080);
			Socket connection = socket.accept();
			System.out.println("connected");
			InputStream in = connection.getInputStream();
			String line = "";
			int num = in.read();
			while(num!=-1) {
				if(num=='\n') {
					System.out.println(line);
					line="";
					
				}
				else {
					line+=(char)num;
				}
				num=in.read();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

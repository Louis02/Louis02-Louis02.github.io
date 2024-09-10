import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
	Socket socket;
	String[] results;

	BKRunner run;

	public ClientHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		System.out.println("run");
		try {
			String query = "";

			InputStream in = socket.getInputStream();
			String total = "";
			int c = in.read();
			while (c != -1) {
				total += (char) c;
				if (total.equals("\r")) {

					break;
				}

				if (c == '\n') {
					// System.out.println(total);
					if (total.contains("GET ")) {
						String[] words = total.split(" ");
						query = words[1].substring(1);
						// break;
					}
					total = "";

				}

				c = in.read();
			}
			System.out.println("        query " + query);
			if (query.equals("")) {
				BufferedReader read = new BufferedReader(new FileReader("Home.html"));
				String line = read.readLine();
				String file = "";
				while (line != null) {
					file += line;
					line = read.readLine();
				}
				OutputStream out = socket.getOutputStream();
				out.write("HTTP/1.0 200 OK\r\n".getBytes());
				String input = "Content-Length: " + file.length() + "\r\n";
				out.write(input.getBytes());
				out.write("Content-Type: text/html\r\n\r\n".getBytes());
				out.write(file.getBytes());
				out.close();
				in.close();
			} else if (query.contains("search=")) {
				BufferedReader br = null;
				String token ="";
				try {
					br = new BufferedReader(new FileReader("/Users/league/Desktop/tokenn.txt"));
					 token = br.readLine();

					br.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				run = new BKRunner(token);
				
				String data = query.substring(7, query.length());
				results = data.split("&");
				run.callGetData(results[0], results[1], results[2], results[3], results[4]);
				for (int i = 0; i < results.length; i++) {
					
					System.out.println("here are the results at " + i + "     " + results[i]);
				}
				ArrayList<String> dataGet = run.getData(results[0], results[1], results[2], results[3], results[4]);
				String send = "<html>";
				for(int i = 0;i<dataGet.size();i++) {
					String u = dataGet.get(i);
					send += "<a href=\""+u +"\"> "+u+"</a>";
					send+= "<br>";
				}
				send +="</html>";
				OutputStream out = socket.getOutputStream();
				out.write("HTTP/1.0 200 OK\r\n".getBytes());
				String input = "Content-Length: " + send.length() + "\r\n";
				out.write(input.getBytes());
				out.write("Content-Type: text/html\r\n\r\n".getBytes());
				out.write(send.getBytes());
				out.close();
				in.close();
				
				
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(results.length);
	}
	
	public String[] nullSplit(String in){
		String[] ans  = null;
		String add = "";
		int ansCount = 0;
		for (int i = 0; i < in.length(); i++) {
			if(in.charAt(i)!='&') {
				add+=in.charAt(i);
			}
			else {
				ans[ansCount] = add;
				ansCount++;
				if(in.indexOf(i+1)<=in.length()&&in.charAt(i+1)=='&') {
					add+="";
				}
				else if(in.indexOf(i+1)<=in.length()) {
					add="";
				}
			}
		}
		System.out.println(ans);
		return ans;
		
	}

}

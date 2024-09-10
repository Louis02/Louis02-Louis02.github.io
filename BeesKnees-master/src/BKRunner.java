import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.neovisionaries.ws.client.ThreadType;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;

public class BKRunner implements WebSocketListener {
	String token;
	WebSocket socket;
	String startDate;
	String endDate;
	String company;
	String sortby;
	String exclude;
	String domain;
	String language;
	String[] arr;
	boolean searched = false;

	public BKRunner(String t) {

		this.token = t;
		System.out.println("                     token " + token);
		// createUI();

	}

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("/Users/league/Desktop/tokenn.txt"));
		String token = br.readLine();
		br.close();
		new BKRunner(token);

	}

	public static String getStringFromJSONObject(String json, String key) {
		String ans = "";

		for (int i = 1; i < json.length() - key.length() - 1; i++) {
			if (json.substring(i - 1, i + key.length() + 1).equals("\"" + key + "\"")) {
				int j = i + key.length() + 1;
				char d = json.charAt(j);
				while (d != '\"') {
					j++;
					d = json.charAt(j);
				}
				j++;
				d = json.charAt(j);
				while (d != '\"') {
					// System.out.println(json.indexOf('\"'));
					ans += d;
					j++;
					d = json.charAt(j);
				}
//				while(d != ',') {
//					if(Character.isDigit(d)) {
//						num += d;
//					}
//					j++;
//					d = json.charAt(j);
//				}
				break;
			}
		}

		return ans;
	}

	public ArrayList<String> createUI() {
		ArrayList<String> array = new ArrayList<String>();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		JTextField text = new JTextField(20);
		JTextField text2 = new JTextField(20);
		JTextField text3 = new JTextField(20);
		JTextField text4 = new JTextField(20);
		JTextField text5 = new JTextField(20);

		JLabel label = new JLabel();
		JLabel label2 = new JLabel();
		JLabel label3 = new JLabel();

		JLabel searchTermm = new JLabel();
		JLabel fromm = new JLabel();
		JLabel too = new JLabel();
		JLabel domainn = new JLabel();
		JLabel languagee = new JLabel();
		JButton button = new JButton();

		frame.add(panel);

		panel.add(label);
		panel.add(label2);
		panel.add(label3);

		panel.add(searchTermm);
		panel.add(text);
		panel.add(fromm);
		panel.add(text2);
		panel.add(too);
		panel.add(text3);
		panel.add(domainn);
		panel.add(text4);
		panel.add(languagee);
		panel.add(text5);

		panel.add(button);

		button.setText("Search");

		// label.setText(getData(arr[3]));
		// label2.setText(getData(arr[1]));
		// label3.setText(getData(arr[2]));

		searchTermm.setText("Search Term: ");
		fromm.setText("From (Start Date): ");
		too.setText("To (End Date): ");

		domainn.setText("Domain: ");
		languagee.setText("Language: ");
		button.addActionListener((e) -> {
			array.add(text.getText());
			array.add(text2.getText());
			array.add(text3.getText());
			array.add(text4.getText());
			array.add(text5.getText());

			searched = true;
//			System.out.println(searchTerm);
//			System.out.println(from);
//			System.out.println(to);
//			System.out.println(exclude);
//			System.out.println(domain);
//			System.out.println(language);

//			String url = searchNewsApi(array.get(0), array.get(1), array.get(2), array.get(3), array.get(4), array.get(5));
//			arr = findArray(url, "articles");
//			System.out.println(getData(arr[0]));
			String url = searchNewsApi(array.get(0), array.get(1), array.get(2), array.get(3), array.get(4));
			arr = findArray(url, "articles");
			System.out.println("          here is " + arr[0]);
			//System.out.println(getData());
		});

		frame.setVisible(true);
		frame.setSize(1000, 700);

		return null;
	}

	public void callGetData(String company, String startDate, String endDate, String domain, String language) {
		System.out.println("called");
		String url = searchNewsApi(company, startDate, endDate, domain, language);
		arr = findArray(url, "articles");
		//System.out.println(getData());
	}

	public String[] findArray(String json, String key) {

		String s = "";
		for (int i = 1; i < json.length() - key.length() - 1; i++) {
			if (json.substring(i - 1, i + key.length() + 1).equals("\"" + key + "\"")) {
				int j = i + key.length() + 1;
				char c = json.charAt(j);
				while (c != '[') {
					c = json.charAt(++j);

				}
				s += c;
				int bctr = 0;
				while (true) {
					c = json.charAt(++j);
					// System.out.println(c);
					s += c;
					if (c == '[') {
						if (json.charAt(j + 1) == '+') {
							while (c != ']') {
								c = json.charAt(++j);
							}

						} else {
							bctr++;

						}
					} else if (c == ']') {
						if (bctr == 0) {
							break;
						} else {
							// System.out.println("decreasing");
							bctr--;
						}

					} else if (c == '[') {
						if (json.charAt(j + 1) == '+') {
							while (c != ']') {
								c = json.charAt(j);
							}

						} else {
							bctr++;
						}

					}

				}
				break;
			}
		}

		return parseJSONArray(s);
	}

	public static String[] parseJSONArray(String json) {
		ArrayList<String> obs = new ArrayList<String>();
		// System.out.println(json);
		for (int i = 0; i < json.length(); i++) {
			char c = json.charAt(i);
			if (c == '{') {
				String v = "" + c;
				int ctr = 0;
				while (true) {
					i++;
					c = json.charAt(i);
					v += c;
					if (c == '}' && ctr == 0) {
						// System.out.println(v);
						break;
					} else if (c == '}') {
						// System.out.println(ctr);
						ctr--;
					} else if (c == '{') {
						ctr++;
					}
				}
				obs.add(v);
			}

		}

		return obs.toArray(new String[obs.size()]);
	}

	public JsonObject getJsonObjectFromString(String input) {
		JsonReader reader = Json.createReader(new StringReader(input));
		JsonObject obj = null;
		try {
			obj = reader.readObject();
		} catch (Exception e) {
			System.out.println("error");
		}

		return obj;

	}

	ArrayList<String> url = new ArrayList<String>();

	public ArrayList<String> getData(String company, String startDate, String endDate, String domain, String language) {
		String searcher = searchNewsApi(company, startDate, endDate, domain, language);
		arr = findArray(searcher, "articles");
		
	
		for (int i = 0; i < arr.length; i++) {
			url.add(getStringFromJSONObject(arr[i], "url"));
		}

		System.out.println("url what im trying to find       " + url);
		return url;
	}

	public String searchNewsApi(String company, String startDate, String endDate, String domain, String language) {
		if (company.equals("%")) {
			System.out.println("cannot run");
		}
		if (startDate.equals("%")) {
			System.out.println(startDate);
			startDate = "";
		}
		if (endDate.equals("%")) {
			System.out.println(endDate);
			endDate = "";
		}
		if (domain.equals("%")) {
			System.out.println(domain);
			domain = "";
		}
		if (language.equals("%")) {
			System.out.println(language);
			language = "";
		}
		String ans = "";

		try {
			System.out.println("https://newsapi.org/v2/everything?q=" + company + "&from=" + startDate + "&to="
					+ endDate + "&domains=" + domain + "&language=" + language);
			URL url = new URL("https://newsapi.org/v2/everything?q=" + company + "&from=" + startDate + "&to=" + endDate
					+ "&domains=" + domain + "&language=" + language);

			HttpsURLConnection h = (HttpsURLConnection) url.openConnection();
			h.setRequestMethod("GET");
			System.out.println(token);
			h.setRequestProperty("Authorization", token);
			// h.setRequestProperty("User-Agent", "");
			InputStream in = h.getInputStream();
			String data = "";
			int v = in.read();
			while (v != -1) {
				data += (char) v;
				v = in.read();
			}
			System.out.println(data);
			ans = data;
			in.close();
			h.disconnect();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ans;
	}

	@Override
	public void handleCallbackError(WebSocket arg0, Throwable arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBinaryFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBinaryMessage(WebSocket arg0, byte[] arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCloseFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectError(WebSocket arg0, WebSocketException arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(WebSocket arg0, Map<String, List<String>> arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onContinuationFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDisconnected(WebSocket arg0, WebSocketFrame arg1, WebSocketFrame arg2, boolean arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(WebSocket arg0, WebSocketException arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrameError(WebSocket arg0, WebSocketException arg1, WebSocketFrame arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrameSent(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFrameUnsent(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageDecompressionError(WebSocket arg0, WebSocketException arg1, byte[] arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageError(WebSocket arg0, WebSocketException arg1, List<WebSocketFrame> arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPingFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPongFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSendError(WebSocket arg0, WebSocketException arg1, WebSocketFrame arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSendingFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSendingHandshake(WebSocket arg0, String arg1, List<String[]> arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStateChanged(WebSocket arg0, WebSocketState arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextFrame(WebSocket arg0, WebSocketFrame arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextMessage(WebSocket arg0, String arg1) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextMessageError(WebSocket arg0, WebSocketException arg1, byte[] arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onThreadCreated(WebSocket arg0, ThreadType arg1, Thread arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onThreadStarted(WebSocket arg0, ThreadType arg1, Thread arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onThreadStopping(WebSocket arg0, ThreadType arg1, Thread arg2) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUnexpectedError(WebSocket arg0, WebSocketException arg1) throws Exception {
		// TODO Auto-generated method stub

	}

}

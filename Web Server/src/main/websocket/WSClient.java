package main.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WSClient {
	
	private InputStream is;
	private OutputStream os;
	private Socket socket;
	
	public WSClient(Socket socket) throws IOException {
		this.socket = socket;
		is = socket.getInputStream();
		os = socket.getOutputStream();
	}
	
	public WSRequest readRequest() throws WSParseException, IOException {
		try {
			return new WSRequest(is);
		} catch (WSParseException e) {
			if (e.close())
				throw e;
			e.printStackTrace();
			return null;
		}
	}
	
	public void writeResponse(WSResponse response) {
		try {
			int[] bytes = response.getBytes();
			for (int i = 0; i < bytes.length; i++)
				os.write(bytes[i]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return socket;
	}

}

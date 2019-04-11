package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class ClientThreadReader implements Runnable{
	private JTextArea jta;
	private BufferedReader reader;
	private String message;
	
	public ClientThreadReader(Socket socket,JTextArea jta) {
		try {
			this.jta = jta;
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				message = reader.readLine();
				jta.append(message+"\n");
			} catch (IOException e) {
				jta.append("与服务器失去连接\n");
				return;
				//e.printStackTrace();
			}
		}
	}
}

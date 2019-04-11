package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTextArea;

import entity.ChattingRecord;

public class ClientHandle implements Runnable{

	private Socket socket;
	private BufferedReader reader;
	private String message;
	private PrintWriter writer;
	private List<ClientHandle> list;
	private JTextArea jta;
	private JTextArea jta2;
	private List<ChattingRecord> chattingList;
	private String nickname;
	
	public ClientHandle(Socket socket,JTextArea jta,JTextArea jta2,List<ClientHandle> list,List<ChattingRecord> chattingList) {
		try {
			this.socket = socket;
			this.jta = jta;
			this.jta2 = jta2;
			this.list = list;
			this.chattingList = chattingList;
			//创建输入读取对象
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//创建写出对象
			writer = new PrintWriter(socket.getOutputStream(),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message) {
		writer.println(message);
	}
	
	@Override
	public void run() {
		try {
			//线程的功能，循环接收客户端发送过来的信息，然后遍历所有的用户发送之前那个用户发送过来的信息
			while(true) {
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = sdf.format(new Date());
				String receive = reader.readLine();
				nickname = receive.substring(0,receive.indexOf(":"));
				message = time+"\n"+receive;
				jta.append(message+"\n");
				ChattingRecord record = new ChattingRecord();
				record.setTime(time);
				record.setUsername(receive.substring(0,receive.indexOf(":")));
				record.setContent(receive.substring(receive.indexOf(":")+1));
				chattingList.add(record);
				for(int i=0;i<list.size();i++) {
					ClientHandle client = list.get(i);
					client.sendMessage(message);
				}
			}
		} catch (IOException e) {
			jta.append("用户"+nickname+"断开了连接\n");
			for(int i=0;i<list.size();i++) {
				ClientHandle client = list.get(i);
				client.sendMessage("用户"+nickname+"退出了聊天室\n");
			}
			list.remove(this);
			jta2.setText("在线人数："+list.size());
		}finally {
			try {
				socket.close();
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

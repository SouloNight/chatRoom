package chat;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame{
	private ServerSocket serverSocket;
	private ExecutorService threadPool;
	private Socket socket;
	private int num;//用户数量
	//private List<ClientHandle> list = new ArrayList<ClientHandle>();
	private Container container;
	private JTextArea jta = new JTextArea();
	private JScrollPane jsp = new JScrollPane();
	private JTextArea jta2 = new JTextArea();
	private List<ClientHandle> list = new ArrayList<ClientHandle>();
	private List<String> chattingList = new ArrayList<String>();
	private Thread saveChattingThread;
	
	public Server() {
		container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(jsp,BorderLayout.CENTER);
		jta2.setEditable(false);
		container.add(jta2,BorderLayout.NORTH);
		jsp.setViewportView(jta);
		jta.setEditable(false);
		this.setSize(400, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void start() {
		try {
			//初始化服务端套接口
			serverSocket = new ServerSocket(8088);
			//创建线程池
			threadPool = Executors.newFixedThreadPool(50);
			num = 0;
			while(true) {
				//第一个用户
				num++;
				if(num==Integer.MAX_VALUE) {
					num=1;
				}
				System.out.println("等待客户端连接……");
				socket = serverSocket.accept();
				System.out.println("客户端连接成功");
				//创建客户端类对象
				ClientHandle handle = new ClientHandle(socket,num,jta,jta2,list,chattingList);
				list.add(handle);
				//用线程池执行客户端num线程
				threadPool.execute(handle);
				jta2.setText("在线人数："+list.size());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}



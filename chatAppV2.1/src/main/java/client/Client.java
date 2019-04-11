package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import entity.User;

public class Client extends JFrame{
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	
	private Container container;//容器
	private JPanel jp1 = new JPanel(new BorderLayout());//边界布局的内容面板
	private JPanel jp2 = new JPanel(new BorderLayout());//边界布局的内容面板
	private JScrollPane jsp = new JScrollPane();//带滚动条的面板
	private JTextArea jta = new JTextArea();//文本区域面板
	private JTextField jtf = new JTextField();//文本输入框面板
	private String nickname;
	private int age;
	
	public Client(User user) {
		try {
			//与服务器套接口的地址与端口连接
			socket = new Socket("localhost",8088);		
			writer = new PrintWriter(socket.getOutputStream(),true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//获得用户信息
			nickname = user.getNickname();
			age = user.getAge();
			
			//使聊天记录文本不可修改
			jta.setEditable(false);
			jta.append(nickname+"，欢迎您加入聊天\n");
			String reg = "[^0-9]";
			
			//设置聊天窗口的标题
			this.setTitle("聊天窗口  "+nickname);
			container = this.getContentPane();
			//将内容面板1添加到容器中
			container.add(jp1);
			//将内容面板2添加到容器中，放置在容器的南方（下方）；
			container.add(BorderLayout.SOUTH,jp2);
			//在内容面板1上添加带滚动条的面板
			jp1.add(jsp);
			//设置带滚动条的面板的观察口的视图为jta文本域面板
			jsp.setViewportView(jta);
			//在内容面板2上添加文本输入框
			jp2.add(jtf);
			
			//在文本输入框上添加动作监听器
			jtf.addActionListener(new ActionListener() {
				//当文本输入框上回车时，会将当前文本输入框上的字符串写出给服务端并在文本域面板上拼接该字符串，
				//然后清空文本输入框
				@Override
				public void actionPerformed(ActionEvent e) {
					String message = jtf.getText();
					writer.println(nickname+":"+message);
					//jta.append(message+"\n");
					jtf.setText("");
				}
			});
			//新建循环读取服务器上发送过来的内容的线程，然后启动线程
			ClientThreadReader tReader = new ClientThreadReader(socket, jta);
			Thread threadReader = new Thread(tReader);
			threadReader.start();
			
			//设置客户端聊天窗口的尺寸，是否可见，与其他窗口的位置关系（null为无关，默认在显示器中居中），
			//默认关闭按钮形式
			this.setSize(800,600);
			this.setVisible(true);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		} catch(ConnectException e) {
			//服务器没打开的时候，报错生成一个窗口告诉用户，无法连接服务器
			JFrame jf = new JFrame();
			JPanel p = new JPanel();
			p.setBackground(Color.white);
			p.setLayout(new BorderLayout());
			JLabel jl = new JLabel("无法连接服务器",JLabel.CENTER);
			jl.setFont(new Font("宋体",Font.PLAIN,20));
			p.add(jl,BorderLayout.CENTER);
			jf.getContentPane().add(p);
			
			jf.setSize(300, 100);
			jf.setLocationRelativeTo(null);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setVisible(true);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) {
		Client client = new Client();
	}*/
}

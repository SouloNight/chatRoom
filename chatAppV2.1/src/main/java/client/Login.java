package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.User;
import jdbc.ChattingDao;
import publicClass.FailLogin;

public class Login extends JFrame{
	
	private JPanel centerPanel;
	private JLabel labelAccount;
	private JLabel labelPassword;
	private JTextField textAccount;
	private JTextField textPassword;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	private JButton button;
	private JButton buttonReg;
	
	public Login() {
		this.setTitle("登入");
		
		centerPanel = new JPanel();
		this.setContentPane(centerPanel);
		centerPanel.setLayout(new GridLayout(3, 1, 10, 10));
		centerPanel.setPreferredSize(new Dimension(400,200));
		centerPanel.setBackground(Color.white);
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel2.setBackground(Color.WHITE);
		panel3.setBackground(Color.WHITE);
		labelAccount = new JLabel("账号：");
		labelAccount.setFont(new Font("宋体",Font.PLAIN,25));
		labelPassword = new JLabel("密码：");
		labelPassword.setFont(new Font("宋体",Font.PLAIN,25));
		textAccount = new JTextField(20);
		textAccount.setFont(new Font("宋体",Font.PLAIN,25));
		textPassword = new JTextField(20);
		textPassword.setFont(new Font("宋体",Font.PLAIN,25));
		button = new JButton("登入");
		buttonReg = new JButton("注册");
		button.setFont(new Font("宋体",Font.PLAIN,15));
		buttonReg.setFont(new Font("宋体",Font.PLAIN,15));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChattingDao dao = new ChattingDao();
				String account = textAccount.getText();
				String password = textPassword.getText();
				User msgUser = new User();
				msgUser.setUserAccount(account);
				msgUser.setPassword(password);
				User user = dao.login(msgUser);
				if(user!=null) {
					Client client = new Client(user);
					Login.this.dispose();
				}else {
					FailLogin fail = new FailLogin(Login.this);
				}
			}
		});
		
		buttonReg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				RegDialog reg = new RegDialog(Login.this);
				reg.setVisible(true);
			}
		});
		
		centerPanel.add(panel1);
		centerPanel.add(panel2);
		centerPanel.add(panel3);
		panel1.add(labelAccount);
		panel1.add(textAccount);
		panel2.add(labelPassword);
		panel2.add(textPassword);
		panel3.setLayout(new GridLayout(1,5,10,10));
		panel3.add(new JLabel());
		panel3.add(new JLabel());
		panel3.add(button);
		panel3.add(new JLabel());
		panel3.add(buttonReg);
		
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new Login();
	}
}

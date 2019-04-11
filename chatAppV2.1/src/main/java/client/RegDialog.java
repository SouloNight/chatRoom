package client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.User;
import jdbc.ChattingDao;
import publicClass.FailReg;
import publicClass.SuccessReg;

public class RegDialog extends JDialog{
	
	private JPanel[] panels;
	private JPanel jp_center;
	private JPanel jpTop;
	private JLabel labelTop;
	private JPanel jpBot;
	private JButton button;
	private JLabel[] labels;
	private JTextField[] fields;
	
	public RegDialog(JFrame frame) {
		super(frame, "注册",true);
		int size = 4;
		jp_center = new JPanel();
		jp_center.setLayout(new GridLayout(6, 1,10,10));
		jp_center.setPreferredSize(new Dimension(400,350));
		
		labels = new JLabel[] {new JLabel("账号：",JLabel.RIGHT),new JLabel("密码：",JLabel.RIGHT),new JLabel("昵称：",JLabel.RIGHT),new JLabel("年龄：",JLabel.RIGHT)};
		
		fields = new JTextField[size];
		for(int i=0;i<fields.length;i++) {
			fields[i] = new JTextField(30);
		}
		
		jpTop = new JPanel();
		labelTop = new JLabel("请填写以下信息以完成注册账号",JLabel.CENTER);
		labelTop.setFont(new Font("宋体",Font.BOLD,20));
		jpTop.add(labelTop);
		
		jpBot = new JPanel();
		button = new JButton("注册");
		button.setFont(new Font("宋体",Font.BOLD,20));
		jpBot.add(button);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					User user = new User();
					user.setUserAccount(fields[0].getText());
					user.setPassword(fields[1].getText());
					user.setNickname(fields[2].getText());
					user.setAge(Integer.parseInt(fields[3].getText()));
					ChattingDao dao = new ChattingDao();
					dao.reg(user);
					new SuccessReg(RegDialog.this);
					RegDialog.this.dispose();
				}catch(NumberFormatException | SQLException e2) {
					new FailReg(RegDialog.this);
					return;
				}
			}
		});
		
		panels = new JPanel[4];
		jp_center.add(jpTop);
		for(int i=0;i<panels.length;i++) {
			panels[i] = new JPanel();
			jp_center.add(panels[i]);
			panels[i].add(labels[i]);
			panels[i].add(fields[i]);
			labels[i].setFont(new Font("宋体",Font.PLAIN,20));
			fields[i].setFont(new Font("宋体",Font.PLAIN,20));
		}
		
		jp_center.add(jpBot);
		
		this.setContentPane(jp_center);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
	
	/*public static void main(String[] args) {
		JFrame jf = new JFrame();
		jf.setSize(400, 300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		RegDialog frame = new RegDialog(jf);
		frame.setVisible(true);
	}*/
}

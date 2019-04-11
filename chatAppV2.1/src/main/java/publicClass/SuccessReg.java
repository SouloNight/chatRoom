package publicClass;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SuccessReg extends JDialog{
	private JPanel panel;
	private JLabel label;
	private JButton button;
	private JPanel jpbutton;
	
	public SuccessReg(Dialog dialog) {
		super(dialog, "成功",true);
		panel = new JPanel();
		jpbutton = new JPanel();
		jpbutton.setSize(200, 50);
		label = new JLabel("恭喜您，注册成功！",JLabel.CENTER);
		button = new JButton("确定");
		button.setFont(new Font("宋体",Font.BOLD,25));
		button.setPreferredSize(new Dimension(100,45));
		jpbutton.add(button);
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SuccessReg.this.dispose();
			}
		});
		label.setFont(new Font("宋体",Font.BOLD,30));
		panel.setLayout(new GridLayout(2,1,10,10));
		panel.add(label);
		panel.add(jpbutton);
		
		this.setContentPane(panel);
		this.setSize(400, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	/*public static void main(String[] args) {
		JFrame frame = new JFrame();
		new Success(frame);
	}*/
}

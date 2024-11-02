package my3game;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Howtoplay implements ActionListener{
	JFrame frame;
	JButton button1;
	JButton button2;
	JLabel label;
	Howtoplay(){
		 frame = new JFrame();
		 label = new JLabel();
		 button1 = new JButton("Start ►");
		 button2 = new JButton("Back TO main menu");
		 
		 ImageIcon image01 = new ImageIcon("C:/Users/Felix/eclipse-workspace/my3game/src/my3game/howtoplay_img.png");
		 label.setBounds(0, 0, 500, 500);
		 label.setFont(new Font(null,Font.PLAIN,20));
		 label.setIcon(image01);
		 
		 button1.setBounds(25, 400, 150, 40);
			button1.setBackground(new Color(200,250,200));
			button1.setFocusable(false);
			button1.addActionListener(this);
			
			button2.setBounds(300, 400, 150, 40);
			button2.setBackground(new Color(200,200,250));
			button2.setFocusable(false);
			button2.addActionListener(this);
		 
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(500, 500);
			frame.setLayout(null);
			frame.setVisible(true);
			frame.setResizable(false);
			label.add(button1);
			label.add(button2);
			frame.add(label);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==button1) {   frame.dispose();new MyFrame();}
		else if (e.getSource()==button2) {   frame.dispose();new Menu_Main();}
		
	}


}

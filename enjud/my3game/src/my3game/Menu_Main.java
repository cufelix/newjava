package my3game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Menu_Main implements ActionListener{
	JFrame frame;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	Menu_Main(){
		 frame = new JFrame();
		 ImageIcon image1 = new ImageIcon("C:/Users/Felix/eclipse-workspace/my3game/src/my3game/Mainmenu_img.png");
		 JLabel label = new JLabel();
		 label.setBounds(0, 0, 420, 420);
		label.setIcon(image1);
		 
			
		 button1 = new JButton("Start");
		 button2 = new JButton("Rules");
		 button3 = new JButton("How to play");
		 button4 = new JButton("Quit");
	
			button1.setBounds(25, 240, 150, 50);
			button1.setBackground(new Color(200,250,200));
			button1.setFocusable(false);
			button1.addActionListener(this);
			
			button2.setBounds(225, 240, 150, 50);
			button2.setBackground(new Color(200,200,250));
			button2.setFocusable(false);
			button2.addActionListener(this);
			
			button3.setBounds(25, 310, 150, 50);
			button3.setBackground(new Color(200,200,200));
			button3.setFocusable(false);
			button3.addActionListener(this);
			
			button4.setBounds(225, 310, 150, 50);
			button4.setBackground(new Color(250,200,200));
			button4.setFocusable(false);
			button4.addActionListener(this);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(420, 420);
			frame.setLayout(null);
			frame.setVisible(true);
			frame.setResizable(false);
			label.add(button1);
			label.add(button2);
			label.add(button3);
			label.add(button4);
			frame.add(label);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==button1) {   frame.dispose();new MyFrame();}
			else if (e.getSource()==button2) {   frame.dispose();new Rules();}
			else if (e.getSource()==button3) {   frame.dispose();new Howtoplay();}
			else if (e.getSource()==button4) {   frame.dispose();}
}
		
}



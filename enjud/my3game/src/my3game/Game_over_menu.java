package my3game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game_over_menu  implements ActionListener{
	JFrame window = new JFrame();
	JButton button1 = new JButton("again");
	JButton button2 = new JButton("Back To main menu");
	Game_over_menu(){
		ImageIcon image1 = new ImageIcon("C:/Users/Felix/eclipse-workspace/my3game/src/my3game/replay.png");
		ImageIcon image2 = new ImageIcon("C:/Users/Felix/eclipse-workspace/my3game/src/my3game/Gameover_anim.png");
		button1.setBounds(50, 225, 200, 40);
		button1.setIcon(image1);
		button1.setBackground(new Color(200,250,200));
		button1.setFocusable(false);
		button1.addActionListener(this);
		
		button2.setBounds(300, 225, 200, 40);
		button2.setBackground(new Color(200,200,250));
		button2.setFocusable(false);
		button2.addActionListener(this);
		
		JLabel label = new JLabel();
		label.setBounds(0, 0, 561, 580);
		label.setIcon(image2);
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(561,620);
		window.setLayout(null);
		window.setVisible(true);
		window.add(label);
		window.setResizable(false);
		label.add(button1);
		
		
		label.add(button2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==button1) {   window.dispose();new MyFrame();}
		else if (e.getSource()==button2) {   window.dispose();new Menu_Main();}
	}
}

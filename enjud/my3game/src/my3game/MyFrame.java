package my3game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class MyFrame  implements KeyListener {
	MyPanel panel;
	JFrame frame;
	int pole_reserv [][][];
	MyFrame(){
		panel = new MyPanel();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.setResizable(false);
	//	frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
		frame.addKeyListener(this);
	//	frame.getContentPane().setBackground(Color.WHITE);
		pole_reserv = panel.pole;
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	//	System.out.println(e.getKeyChar());
	}
	@Override
	public void keyPressed(KeyEvent e) {
	//	System.out.println(e.getKeyChar());
		if(e.getKeyCode()==40 && panel.cordY<7 && panel.pole[panel.cordYplus][panel.cordX][0]==0&& panel.pole==pole_reserv) {
			panel.move++;
			panel.pole[panel.cordY][panel.cordX][0]=0;
			panel.cordY++;panel.cordYplus++;
			panel.pole[panel.cordY][panel.cordX][0]=1;
			pole_reserv = panel.pole;
			panel.pole=panel.line_check(panel.pole);
			panel.pole[panel.cordY][panel.cordX][0]=1;
			panel.moves++;
			call_gameover (panel.pole);
			if(panel.moves>=9) {
				panel.moves=0;
				panel.cordY=0;panel.cordYplus=1;panel.cordX=panel.new_x(panel.cordX);panel.cordXminus=panel.cordX;panel.cordXplus=panel.cordX;panel.cordXminus--;panel.cordXplus++;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				call_gameover (panel.pole);
			}
			panel.repaint1(panel.pole);
			 if (panel.cordY==7) {
				panel.cordY=0;panel.cordYplus=1;
				panel.cordX=panel.new_x(panel.cordX);panel.cordXminus=panel.cordX;panel.cordXplus=panel.cordX;panel.cordXminus--;panel.cordXplus++;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				panel.pole=panel.line_check(panel.pole);
				panel.moves=0;
				panel.repaint1(panel.pole);
				call_gameover (panel.pole);
				
			}
			 
			 
		}else if (e.getKeyCode()==39 && panel.cordX <7 && panel.pole[panel.cordY][panel.cordXplus][0]==0) {
			panel.move++;
			panel.pole[panel.cordY][panel.cordX][0]=0;
			panel.cordX++;panel.cordXplus++;panel.cordXminus++;
			panel.pole[panel.cordY][panel.cordX][0]=1;
			panel.pole=panel.line_check(panel.pole);
			panel.moves++;
			call_gameover (panel.pole);
			if(panel.moves>=9) {
				panel.moves=0;
				panel.cordY=0;panel.cordYplus=1;panel.cordX=panel.new_x(panel.cordX);panel.cordXminus=panel.cordX;panel.cordXplus=panel.cordX;panel.cordXminus--;panel.cordXplus++;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				call_gameover (panel.pole);
			}
			
			panel.repaint1(panel.pole);
		}else if (e.getKeyCode()==37 && panel.cordX >0 && panel.pole[panel.cordY][panel.cordXminus][0]==0) {
			panel.move++;
			panel.pole[panel.cordY][panel.cordX][0]=0;
			panel.cordX--;panel.cordXminus--;panel.cordXplus--;
			panel.pole[panel.cordY][panel.cordX][0]=1;
			panel.pole=panel.line_check(panel.pole);
			panel.moves++;
			call_gameover (panel.pole);
			if(panel.moves>=9) {
				panel.moves=0;
				panel.cordY=0;panel.cordYplus=1;panel.cordX=panel.new_x(panel.cordX);panel.cordXminus=panel.cordX;panel.cordXplus=panel.cordX;panel.cordXminus--;panel.cordXplus++;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				panel.pole[panel.cordY][panel.cordX][0]=1;
				call_gameover (panel.pole);
			}
			panel.repaint1(panel.pole);
		}else if (e.getKeyCode()==10) {
			if(panel.level<10) {
			panel.cordY=0;panel.cordYplus=1;
			panel.cordX=panel.new_x(panel.cordX);panel.cordXminus=panel.cordX;panel.cordXplus=panel.cordX;panel.cordXminus--;panel.cordXplus++;
			panel.pole[panel.cordY][panel.cordX][0]=1;
			panel.pole=panel.line_check(panel.pole);
			panel.pole[panel.cordY][panel.cordX][0]=1;
			panel.moves=0;
			panel.repaint1(panel.pole);
			}else {
				panel.enterstatus=false;
			}
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	//	System.out.println(e.getKeyCode());
	}
	public void call_gameover(int pole [][][]) {
		boolean result = false;
		result = panel.gameover_check (panel.pole);
//		System.out.println("no jsem zde");
	  if (result == true) {
			frame.dispose();
			new Game_over_menu();
    	}
		
	}
}

package my3game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements ActionListener {
	int points;
	int level;
	int move;
	int cordY;
	int cordYplus;
	int cordX;
	int cordXplus;
	int cordXminus;
	int moves;
	boolean enterstatus= true;
	JButton back_main;
	JLabel label_inf;
	JLabel label[] = new JLabel[65];
public	int pole[][][] = {{{0,0,35},{0,70,35},{0,140,35},{0,210,35},{0,280,35},{0,350,35},{0,420,35},{0,490,35}},
			{{0,0,105},{0,70,105},{0,140,105},{0,210,105},{0,280,105},{0,350,105},{0,420,105},{0,490,105}},
			{{0,0,175},{0,70,175},{0,140,175},{0,210,175},{0,280,175},{0,350,175},{0,420,175},{0,490,175}},
			{{0,0,245},{0,70,245},{0,140,245},{0,210,245},{0,280,245},{0,350,245},{0,420,245},{0,490,245}},
			{{0,0,315},{0,70,315},{0,140,315},{0,210,315},{0,280,315},{0,350,315},{0,420,315},{0,490,315}},
			{{0,0,385},{0,70,385},{0,140,385},{0,210,385},{0,280,385},{0,350,385},{0,420,385},{0,490,385}},
			{{0,0,455},{0,70,455},{0,140,455},{0,210,455},{0,280,455},{0,350,455},{0,420,455},{0,490,455}},
			{{0,0,525},{0,70,525},{0,140,525},{0,210,525},{0,280,525},{0,350,525},{0,420,525},{0,490,525}},
		};
int k=0;
	MyPanel(){
		points=0;
		level =0;
		cordY =0;
		cordYplus = 1;
		cordX =0;
		moves =0;
		cordXplus = cordX;cordXminus = cordX;
		cordXplus ++;cordXminus --;
		cordX = new_x(cordX);
		pole[cordY][cordX][0]=1;
		this.setPreferredSize(new Dimension(560,595));
		this.setLayout(null);
		back_main = new JButton("menu");
		back_main.setBounds(490,0, 70, 35);
		back_main.setBackground(new Color(200,250,200));
		back_main.setFocusable(false);
		back_main.addActionListener(this);
		label_inf = new JLabel();
		label_inf.add(back_main);
		label_inf.setBounds(0,0,560, 35);
		label_inf.setForeground(Color.GREEN);
		label_inf.setBackground(new Color(100,100,100));
		label_inf.setOpaque(true);
		
		
		
		
	
		repaint1 (pole);
		
	}
	public void repaint1 (int pole[][][]) {
		k=0;
		//System.out.println(k+"i am here"+pole[1][1][0]);
		this.removeAll();
		label_inf.setText("moves with block : "+moves+" ( max is 9)    |    enter functionality : "+enterstatus+"    |    points : "+points+"    |    level : "+level);
		this.add(label_inf);
		for (int i =0;i<=7;i++) {
			for (int j =0;j<=7;j++) {
				k++;
				label[k]=new JLabel();
				label[k].setBounds(pole[i][j][1],pole[i][j][2],70,70);
				if(pole[i][j][0]==1) {
					label[k].setBackground(new Color(0,250,250));
					label[k].setOpaque(true);
				}else {
					label[k].setBackground(new Color(30,30,30));
					label[k].setOpaque(true);
				}
				this.add(label[k]);
			}
		}
		this.repaint();
		
	}

	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	public int[][][] line_check(int pole [][][]/*,int moves_with_components*/) {
		if(pole[cordY][0][0]==1 &&pole[cordY][1][0]==1 &&pole[cordY][2][0]==1 &&pole[cordY][3][0]==1  &&pole[cordY][4][0]==1 &&pole[cordY][5][0]==1 &&pole[cordY][6][0]==1 &&pole[cordY][7][0]==1) {
			for (int i = 0;i<8;i++) {
				pole[cordY][i][0]=0;
			}
			points=points+100;
			level++;
		}
		return pole;
	}
	public int new_x (int cordX) {
		Random random = new Random();
		cordX = random.nextInt(7);
		return cordX;
		
	}
	public boolean gameover_check (int pole[][][]) {
		if(level>9  &&  moves <9) {
		if(cordX==7 && cordY!=7 ) {
			if (pole[cordYplus][cordX][0]==1 && pole[cordY][cordXminus][0]==1) {
				
				return true;
			}
		}else if(cordX==0 && cordY!=7 ) {
            if (pole[cordYplus][cordX][0]==1 && pole[cordY][cordXplus][0]==1) {
            	
            	return true;
			}
		}else if (cordX!=0 && cordX!=7 && cordY!= 7) {
			if(pole[cordYplus][cordX][0]==1 && pole[cordY][cordXminus][0]==1 && pole[cordY][cordXplus][0]==1  ) {
				
				return true;
			}
		}
		}
		return false;
	}
/*	void gameover_repaint() {
		this.removeAll();
		JLabel gameoverlabel = new JLabel();
		gameoverlabel.setBounds(0, 0, 650, 695);
		ImageIcon gameoverlabelicon = new ImageIcon("C:/Users/Felix/eclipse-workspace/my3game/src/my3game/Gameover_anim.png");
		gameoverlabel.setIcon(gameoverlabelicon);
		this.add(gameoverlabel);
		this.repaint();
		wait(1000);
	}*/
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==back_main) {   new Menu_Main();}
	}
}



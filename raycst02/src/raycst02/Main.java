package raycst02;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;

    public class Main  implements Runnable, MouseMotionListener, MouseListener, KeyListener  {
    private static final int WIDTH = 1945, HEIGHT = 1200;
    private static final int numLines = 40; // Updated number of lines
    private static final Random random = new Random(100);
    public static volatile int mousex = 500;
	public static volatile int mousey = 500;
    public static volatile int pmousex = 500, pmousey = 500;
    private double view = 0;
    private double dir;
    int [] PImage = {500,297};
    public static volatile int PIX = 630,PIY = 450;
    public static volatile int PI =0;
    private double viewx = 0;
    private Image image1;
    private Image image2;
    final static int killcode = 489765413;
    boolean killavalible = false;
    static volatile boolean kill=false;
    public static volatile boolean alive = true;
    volatile static int xctverce = 500;  //potom smazat
    volatile static int yctverce = 500;  //potom smazat
    volatile static int player2score =0;
    volatile static int myscore =0;
    static volatile int dpoint1 = 115;
    volatile static boolean setup = true;
    static Robot r;
    int cislo=1;
    int vscislo=0;

    static String yn="n";
   static String ip ;
    static DataOutputStream output ;
    static DataInputStream input ;
    
    private static long lastFpsCheck = 0;
    private static int currentFps = 0;
    private static int totalFrames = 0;
    
    private static BufferedImage 
    texture01_1,texture01_2,texture01_3,texture01_4,texture01_5,texture01_6,texture01_7,texture01_8, 
    texture02_1,texture02_2,texture02_3,texture02_4,texture02_5,texture02_6,texture02_7,texture02_8, 
    texture03_1,texture03_2,texture03_3,texture03_4,texture03_5,texture03_6,texture03_7,texture03_8,
    texture04_1,texture04_2,texture04_3,texture04_4,texture04_5,texture04_6,texture04_7,texture04_8,
    texture05_1,texture05_2,texture05_3,texture05_4,texture05_5,texture05_6,texture05_7,texture05_8,
    texture06_1,texture06_2,texture06_3,texture06_4,texture06_5,texture06_6,texture06_7,texture06_8,
    texture07_1,texture07_2,texture07_3,texture07_4,texture07_5,texture07_6,texture07_7,texture07_8,
    texture08_1,texture08_2,texture08_3,texture08_4,texture08_5,texture08_6,texture08_7,texture08_8,
    texture09_1,texture09_2,texture09_3,texture09_4,texture09_5,texture09_6,texture09_7,texture09_8,enemy;

         
    private int[][] Alines = {
        {0,0,1935,0},{0,0,0,1000},{0,100,400,100},{400,100,400,0},{200,200,300,200},
        {200,200,200,300},{200,300,300,300},{300,300,300,200},{500,450,600,450},{600,450,600,350},
        {600,350,500,350},{500,350,500,450},{800,500,1100,500},{800,200,1100,200},{800,200,800,500},
        {1100,200,1100,500},{1300,400,1400,400},{1300,500,1400,500},{1300,400,1300,500},{1400,400,1400,500},
        {1535,0,1935,0},{1535,400,1935,400},{1535,0,1535,400},{1905,0,1905,1000},{0,700,400,700},
        {0,1000,1935,1000},{0,700,0,1000},{400,700,400,1000},{550,700,650,700},{550,600,650,600},
        {550,600,550,700},{650,600,650,700},{800,1000,800,1100},{800,700,1100,700},{800,700,800,1000},
        {1100,700,1100,1000},{1300,600,1500,600},{1300,800,1500,800},{1300,600,1300,800},{1500,600,1500,800},
       /* {1635,1000,1935,700}*/};
    
    
   

    private JPanel panel;
    private LinkedList<Line2D.Float> lines;
    private LinkedList<Line2D.Float> rays;
    private Timer movementTimer;

    private boolean moveUp, moveDown, moveLeft, moveRight;

    public Main() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                render(g);
            }
        };

       
        JFrame frame1 = new JFrame("Raycasting");
        frame1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame1.add(panel);
        panel.addMouseMotionListener(this);
        panel.addMouseListener(this);  // Add MouseListener
        frame1.addKeyListener(this);
        panel.setSize(WIDTH, HEIGHT);
        frame1.setSize(WIDTH, HEIGHT);
        frame1.setLocationRelativeTo(null);
        frame1.setVisible(true);
        

        image1 = new ImageIcon("FOVproject2.png").getImage();
        image2 = new ImageIcon("FOVproject5.png").getImage();

        setupKeyBindings();

        movementTimer = new Timer(15, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePosition();
            }
        });
        movementTimer.start();

        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args)throws IOException {
    	 loadTextures();
    	
    	  try {
 			 r = new Robot();
 		} catch (AWTException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
    	
    	//main menu part
    	ImageIcon image3 = new ImageIcon("mainmenu.png");
    	JFrame framemenu = new JFrame("MAIN MENU");
    	framemenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        framemenu.setLocationRelativeTo(null);
        framemenu.setSize(500, 500);
        framemenu.setResizable(false);
        
        JLabel label=new JLabel();
        label.setBounds(0, 0, 500, 500);
		label.setIcon(image3);
		
		JButton button1 = new JButton("Start");
		JButton button2 = new JButton("Rules");
		

		button1.setBounds(125, 200, 250, 50);
		button1.setBackground(new Color(200,250,200));
		button1.setFocusable(false);
		button1.addActionListener((e) ->{
			framemenu.dispose();
			ImageIcon image4 = new ImageIcon("setuppage.png");
	    	JFrame framesetup = new JFrame("MAIN SETUP");
	    	framesetup.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    	framesetup.setLocationRelativeTo(null);
	    	framesetup.setSize(500, 500);
	    	
	    	 JLabel label2=new JLabel();
	         label2.setBounds(0, 0, 500, 500);
	 		label2.setIcon(image4);
	 		
	 		JButton buttonserver = new JButton("Server");
	 		buttonserver.setBounds(300, 150, 150, 50);
			buttonserver.setBackground(new Color(200,250,200));
			buttonserver.setFocusable(false);
			buttonserver.addActionListener((e1)->{
				yn="y";
				ip="localhost";
				System.out.println("sever");
				setup=false;
				framesetup.dispose();
			});
			
			JTextField textfield =new JTextField();
			textfield.setBounds(175, 325, 250, 50);
			textfield.setBackground(Color.black);
			textfield.setForeground(new Color(100,250,10));
			textfield.setCaretColor(Color.WHITE);
			textfield.setFont(new Font("Consolas",Font.PLAIN,20));
			
			JButton submit= new JButton("Submit");
			submit.setBounds(70, 325, 100, 49);
			submit.setBackground(new Color(200,200,250));
			submit.setFocusable(false);
			submit.addActionListener((e2)->{
			ip = textfield.getText();
			System.out.println(ip);
			setup=false;
			framesetup.dispose();
			});
			
			label2.add(submit);
			label2.add(textfield);
			label2.add(buttonserver);
	 		framesetup.add(label2);
	    	framesetup.setVisible(true);
			//System.out.println("lala");
		});
		
		button2.setBounds(125, 300, 250, 50);
		button2.setBackground(new Color(200,200,250));
		button2.setFocusable(false);
		button2.addActionListener((e)->{
			
	//		System.out.println("nana");
			
		});
		
		label.add(button1);
		label.add(button2);
		framemenu.add(label);
        framemenu.setVisible(true);
        while(setup){
  //   int cfgb=5;
   //  cfgb++;
  //   cfgb--;
   //  System.out.println(setup);
        }
        System.out.println(setup);
    	//////////////////////////////////////////////////////////////////////
    	Scanner scanner = new Scanner(System.in);
  //  	System.out.println("Do you want to make this pc Server y/n ?");
    //	String yn = scanner.nextLine();
    	if(yn.equals("y")) {
    		dpoint1=775;
    		Thread thread3 = new Thread(()->{try (ServerSocket serverSocket = new ServerSocket(5000)) {
				System.out.println("Server started and listening on port 5000");

				Socket client1 = serverSocket.accept();
				System.out.println("Client 1 connected");

				Socket client2 = serverSocket.accept();
				System.out.println("Client 2 connected");

				DataInputStream input1 = new DataInputStream(client1.getInputStream());
				DataOutputStream output1 = new DataOutputStream(client1.getOutputStream());

				DataInputStream input2 = new DataInputStream(client2.getInputStream());
				DataOutputStream output2 = new DataOutputStream(client2.getOutputStream());
				
				int px=0,py=0,px1=0,py1=0;

				while (true) {
					if (input1.available() > 0) {
				    	int x = input1.readInt();
						int y = input1.readInt();
						if(x!=px||y!=py||x==killcode) {
						px=x;py=y;
						output2.writeInt(x);
						output2.writeInt(y);
						}
					}

					if (input2.available() > 0) {
						int x = input2.readInt();
						int y = input2.readInt();
						if(x!=px1||y!=py1 ||x==killcode) {
						px1=x;py1=y;
						output1.writeInt(x);
						output1.writeInt(y);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
    		});
    		thread3.start();
    		
    		
    	}
        new Main();
        Thread thread2 = new Thread(()->{
        	int [] PosI = {630,450,610,420,595,399,585,385,575,365,585,385,595,399,610,420,630,450};
        	while(true) {
            if(PI>0) {
          
            		PIX=PosI[0];
            		PIY=PosI[1];wait(100);
            		PIX=PosI[2];
            		PIY=PosI[3];wait(100);
            		PIX=PosI[4];	
            		PIY=PosI[5];wait(100);
            		PIX=PosI[6];
            		PIY=PosI[7];wait(100);
            		PIX=PosI[8];
            		PIY=PosI[9];wait(100);
            		PIX=PosI[10];
            		PIY=PosI[11];wait(100);
            		PIX=PosI[12];		
            		PIY=PosI[13];wait(100);
            		PIX=PosI[14];
            		PIY=PosI[15];wait(100);
            		PIX=PosI[16];
            		PIY=PosI[17];wait(250);
          if(PI==2) {
        	  wait(2300);
        	  mousex=dpoint1;
        	  mousey=dpoint1;
          }
            		PI=0;
        
            }
        	} });
        thread2.start();
        Thread thread4 = new Thread(()->{
       
        	 
        	   //  JFrame frame;
        	   //  JPanel panel;
        	   //  JLabel wheel;
        	     Socket socket = null;///
        	    ///
        	     ///
        	Scanner scannerip = new Scanner(System.in);
     //   System.out.println("Enter ip or if on this pc is running server type 'localhost'");
        	
        	        try {
						socket = new Socket( ip, 5000);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}///
        	        try {
						output = new DataOutputStream(socket.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}///
        	        try {
						input = new DataInputStream(socket.getInputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} ///

        	 //       frame = new JFrame("Wheel Sync Client");
        	  //     panel = new JPanel() {
        	       //     @Override
        	    //        protected void paintComponent(Graphics g) {
        	    //            super.paintComponent(g);
        	   //             g.setColor(Color.RED);
        	    //            g.fillOval(x, y, 50, 50);
        	     //      }
        	     //   };
        	  //     panel.setPreferredSize(new Dimension(400, 400));
        	    //    panel.setBackground(Color.WHITE);

        	   //     frame.add(panel);
        	    //   frame.pack();
        	    //    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	     //   frame.setVisible(true);

        	     //   panel.addMouseMotionListener(new MouseMotionAdapter() {
        	        //    @Override
        	        //    public void mouseDragged(MouseEvent e) {
        	           //     x = e.getX() - 25;
        	          //      y = e.getY() - 25;
        	            //   panel.repaint();

        	      
        	     //   });

        	        new Thread(() -> {                                        //prijem
        	            try {//prijem
        	                while (true) {//prijem
        	                    if (input.available() > 0) {//prijem
        	                    	int sentx;
        	                        sentx = input.readInt();//prijem
        	                        yctverce = input.readInt();//prijem
        	                        if(sentx==killcode) {
        	                        	alive=false;
        	                      // 	System.out.println("alive is  "+alive);
        	                        	
        	                        }else {
        	                        xctverce = sentx;
        	                        }
        	                        SwingUtilities.invokeLater(() -> {//prijem
        	                      //      x = newX;
        	                       //   y = newY;
        	                      //      panel.repaint();
        	                        });//prijem
        	                    }//prijem
        	                }//prijem
        	            } catch (IOException ex) {//prijem
        	                ex.printStackTrace();//prijem
        	            }//prijem
        	        }).start();//prijem
        	        while(true) {
        	        	if(mousex!=pmousex || mousey!=pmousey||kill) {
        	        		int sendx = mousex;
        	        		if (kill) {
        	        			sendx= killcode;
        	        			kill = false;
        	        			myscore++;
        	        		}
        	        	
    	                try {                                          //odesilani
    	                    output.writeInt(sendx);                        //odesilani 
    	                    output.writeInt(mousey);                        //odesilani 
    	                } catch (IOException ex) {                     //odesilani 
    	                    ex.printStackTrace();                      //odesilani 
    	                }   
        	        	}//odesilani 
    	            }
        	
        });
        thread4.start();
        Thread alive1 = new Thread(()->{
        	while(true) {
        		if(alive) {
        			
        		}else {
        			try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			player2score++;
        			 alive=true;
        		}
        	}
        });
        alive1.start();
       
    }

    private void setupKeyBindings() {
        InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = panel.getActionMap();

   //     System.out.println("ahojaj");
        
        inputMap.put(KeyStroke.getKeyStroke("pressed W"), "moveUpPressed");
        inputMap.put(KeyStroke.getKeyStroke("released W"), "moveUpReleased");
        inputMap.put(KeyStroke.getKeyStroke("pressed S"), "moveDownPressed");
        inputMap.put(KeyStroke.getKeyStroke("released S"), "moveDownReleased");
        inputMap.put(KeyStroke.getKeyStroke("pressed A"), "moveLeftPressed");
        inputMap.put(KeyStroke.getKeyStroke("released A"), "moveLeftReleased");
        inputMap.put(KeyStroke.getKeyStroke("pressed D"), "moveRightPressed");
        inputMap.put(KeyStroke.getKeyStroke("released D"), "moveRightReleased");

        actionMap.put("moveUpPressed", new MoveAction(0, -1, true));
        actionMap.put("moveUpReleased", new MoveAction(0, -1, false));
        actionMap.put("moveDownPressed", new MoveAction(0, 1, true));
        actionMap.put("moveDownReleased", new MoveAction(0, 1, false));
        actionMap.put("moveLeftPressed", new MoveAction(-1, 0, true));
        actionMap.put("moveLeftReleased", new MoveAction(-1, 0, false));
        actionMap.put("moveRightPressed", new MoveAction(1, 0, true));
        actionMap.put("moveRightReleased", new MoveAction(1, 0, false));
    }

    private class MoveAction extends AbstractAction {
        private int dx, dy;
        private boolean pressed;

        public MoveAction(int dx, int dy, boolean pressed) {
            super();
            this.dx = dx;
            this.dy = dy;
            this.pressed = pressed;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (dx == 0 && dy == -1) moveUp = pressed;
            if (dx == 0 && dy == 1) moveDown = pressed;
            if (dx == -1 && dy == 0) moveLeft = pressed;
            if (dx == 1 && dy == 0) moveRight = pressed;
        }
    }

    private void updatePosition() {
        pmousex = mousex;
        pmousey = mousey;
     int pcislo = cislo,pvs=vscislo;
        if (moveUp) {
            mousex += Math.cos(view) * 5;
            mousey += Math.sin(view) * 5;
            
        	
        }
        if (moveDown) {
            mousex -= Math.cos(view) * 5;
            mousey -= Math.sin(view) * 5;
        }
        if (moveLeft) {
            mousex -= Math.cos(view + Math.PI / 2) * 5;
            mousey -= Math.sin(view + Math.PI / 2) * 5;
            cislo--;
        	vscislo--;
        }
        if (moveRight) {
            mousex += Math.cos(view + Math.PI / 2) * 5;
            mousey += Math.sin(view + Math.PI / 2) * 5;
            cislo++;
        	vscislo++;
        }
        mousex = Math.max(3, Math.min(mousex, 1900));
        mousey = Math.max(3, Math.min(mousey, 995));
       boolean colision = checkCollisions();
       
        if(colision&&pcislo!=cislo&&(pmousex==mousex||pmousey==mousey)){
        	cislo=pcislo;
        	vscislo=pvs;
        }
        panel.repaint();
    }

    private boolean checkCollisions() {
        for (int i = 0; i < numLines; i++) {
            if (Alines[i][0] == Alines[i][2]) {
                int PLmousex = Alines[i][0] + 5;
                int Mmousex = Alines[i][0] - 5;
                if ((mousex < PLmousex && mousex >= (Alines[i][2] - 5)) || (mousex > Mmousex && mousex <= (Alines[i][2] + 5))) {
                    if ((mousey > (Alines[i][1] - 5) && mousey < (Alines[i][3] + 5)) || (mousey < (Alines[i][1] + 5) && mousey > (Alines[i][3] - 5))) {
                        mousex = pmousex;
                        
                        /**/ mousey = pmousey; 
                        return true;
                    }
                }
            }
            if (Alines[i][1] == Alines[i][3]) {
                int PLmousey = Alines[i][1] + 5;
                int Mmousey = Alines[i][1] - 5;
                if ((mousey < PLmousey && mousey >= (Alines[i][3] - 5)) || (mousey > Mmousey && mousey <= (Alines[i][3] + 5))) {
                    if ((mousex > (Alines[i][0] - 5) && mousex < (Alines[i][2] + 5)) || (mousex < (Alines[i][0] + 5) && mousex > (Alines[i][2] - 5))) {
                      /**/  mousex = pmousex;
                          mousey = pmousey; 
                          return true;
                    }
                }
            }
        }
		return false;
    }

    private LinkedList<Line2D.Float> buildLines() {
    	
        LinkedList<Line2D.Float> linesList = new LinkedList<>();
        for (int[] line : Alines) {
            linesList.add(new Line2D.Float(line[0], line[1], line[2], line[3]));
        }
        linesList.add(new Line2D.Float(xctverce,yctverce,xctverce+15,yctverce));
        linesList.add(new Line2D.Float(xctverce,yctverce+15,xctverce+15,yctverce+15));
        linesList.add(new Line2D.Float(xctverce,yctverce,xctverce,yctverce+15));
        linesList.add(new Line2D.Float(xctverce+15,yctverce,xctverce+15,yctverce+15));
        return linesList;
    }

    private float dist(float x1, float y1, float x2, float y2) {
        return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private float getRayCast(float p0_x, float p0_y, float p1_x, float p1_y, float p2_x, float p2_y, float p3_x, float p3_y) {
        float s1_x = p1_x - p0_x;
        float s1_y = p1_y - p0_y;
        float s2_x = p3_x - p2_x;
        float s2_y = p3_y - p2_y;

        float s, t;
        s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
        t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

        if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
            float x = p0_x + (t * s1_x);
            float y = p0_y + (t * s1_y);
            return dist(p0_x, p0_y, x, y);
        }
        return -1; // No collision
    }

    @Override
    public void run() {
        do {
            panel.repaint();
          
        }while(false);
    }

    private void render(Graphics g) {
    
    	if (alive) {
    	killavalible = false;
    	 lines = buildLines();
    	 totalFrames ++;
    	 if(System.nanoTime()> lastFpsCheck +1000000000) {
    		 lastFpsCheck = System.nanoTime();
    		 currentFps = totalFrames;
    		 totalFrames=0;
    	 }
   
    	 
        rays = calcRays(lines, mousex, mousey, 386, 1600);
 
        int[][] array = preper3D();
        g.setColor(new Color(153, 255, 255));
        g.fillRect(0, 0, 1935, 600);
        g.setColor(new Color(255, 204, 153));
        g.fillRect(0, 500, 1935, 600);
        int xpaintstart =0;
    	int xpaintfinish =0;
    	int x1enemy =0 ,x2enemy =0,enemyy=0,enemyh=0;
    			
    
        int ienemy =0;
        for (int i = 0; i <= 386; i++) {
            int green = array[i][0];
         //   System.out.println(green);
            boolean color = false;
            if (array[i][2] ==3) {
            	
            color = true;
            } else {
                g.setColor(new Color(green, 45, 45));                                            
        color = false;
            }
            int x = array[i][1];
            if (x < 0) {
                x = 1;
            }
            boolean povoleni =false;
            if (array[i][2]==5) {
            
            }
       
            int yim = (int) (500 - (x / 1.5));
            int imheight1 =(int) (x * 1.5);
           /*  xtexture =array[i][3];
             if(zvmn!=0) {
             if (zvmn==1) {System.out.println(xtexture);
            	 xtexture++;
            	 System.out.println(xtexture);
            	 zvmn = 0;
            	 if (xtexture >8) {
            		 xtexture=0;
            	 }
             }else {
            	 System.out.println(xtexture);
            	 xtexture--;
            	 System.out.println(xtexture);
            	 zvmn = 0;
            	 if (xtexture <0) {
            		 xtexture=8;
            	 }
             }
             }*/
            if (color==false) {
            
            switch (green/28) {
            case 0: switch (array[i][3]) {
            case 1: g.drawImage(texture01_1, i * 5, yim, 5,imheight1, null);break;
            case 2: g.drawImage(texture01_2, i * 5,yim, 5, imheight1, null);break;
            case 3: g.drawImage(texture01_3, i * 5, yim, 5,imheight1, null);break;
            case 4: g.drawImage(texture01_4, i * 5,yim, 5, imheight1, null);break;
            case 5: g.drawImage(texture01_5, i * 5,yim, 5, imheight1, null);break;
            case 6: g.drawImage(texture01_6, i * 5,yim, 5, imheight1, null);break;
            case 7: g.drawImage(texture01_7, i * 5,yim, 5, imheight1, null);break;
            case 8: g.drawImage(texture01_8, i * 5,yim, 5, imheight1, null);break;
            
            }break;
            
            case 1: switch (array[i][3]) {
            case 1: g.drawImage(texture02_1, i * 5, yim, 5,imheight1, null);break;
            case 2: g.drawImage(texture02_2, i * 5,yim, 5, imheight1, null);break;
            case 3: g.drawImage(texture02_3, i * 5, yim, 5,imheight1, null);break;
            case 4: g.drawImage(texture02_4, i * 5,yim, 5, imheight1, null);break;
            case 5: g.drawImage(texture02_5, i * 5,yim, 5, imheight1, null);break;
            case 6: g.drawImage(texture02_6, i * 5,yim, 5, imheight1, null);break;
            case 7: g.drawImage(texture02_7, i * 5,yim, 5, imheight1, null);break;
            case 8: g.drawImage(texture02_8, i * 5,yim, 5, imheight1, null);break;
            
            }break;
            
            case 2:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture03_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture03_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture03_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture03_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture03_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture03_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture03_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture03_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
            	
            case 3:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture04_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture04_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture04_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture04_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture04_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture04_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture04_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture04_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
            case 4:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture05_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture05_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture05_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture05_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture05_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture05_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture05_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture05_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
            	
            case 5:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture06_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture06_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture06_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture06_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture06_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture06_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture06_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture06_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
            	
            case 6:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture07_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture07_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture07_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture07_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture07_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture07_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture07_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture07_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
            	
            case 7:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture08_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture08_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture08_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture08_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture08_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture08_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture08_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture08_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
            	
            default:
            	switch (array[i][3]) {
                case 1: g.drawImage(texture09_1, i * 5, yim, 5,imheight1, null);break;
                case 2: g.drawImage(texture09_2, i * 5,yim, 5, imheight1, null);break;
                case 3: g.drawImage(texture09_3, i * 5, yim, 5,imheight1, null);break;
                case 4: g.drawImage(texture09_4, i * 5,yim, 5, imheight1, null);break;
                case 5: g.drawImage(texture09_5, i * 5,yim, 5, imheight1, null);break;
                case 6: g.drawImage(texture09_6, i * 5,yim, 5, imheight1, null);break;
                case 7: g.drawImage(texture09_7, i * 5,yim, 5, imheight1, null);break;
                case 8: g.drawImage(texture09_8, i * 5,yim, 5, imheight1, null);break;
                
                }
            	break;
           }
         //   System.out.println(green/28);
            }
          
        
            
          if (color) {
        	  ienemy++;
        	  if (x1enemy==0) {
        		  x1enemy =i*5;
        		  enemyy=yim;
        		  enemyh=imheight1;
        	  }else {
        	 
        		 x2enemy=i*5;
        	 
        	  }
        	//  g.setColor(new Color(45, 255-green, 45));
        	 //   g.fillRect(i * 5, (int) (500 - (x / 1.5))+20, 5, (int) (x * 1.5)-20);
          
          }
            
            
          g.drawImage(enemy, x1enemy,enemyy,ienemy*5, enemyh, null);
            
            
            
            if(color) {
            if (x1enemy < 951&& 951 < x2enemy) {
            	killavalible = true;
            }
            } 
       
            
        }
        g.setColor(new Color(0, 0, 0));
        g.drawLine(933, 500, 968, 500);
        g.drawLine(951, 485, 951, 515);
        g.drawImage(image1, PImage[0], PImage[1], null);
        g.drawImage(image2, PIX, PIY, null);
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Ink free",Font.BOLD,35));
        g.drawString(myscore+" : "+player2score, 930, 50);
        g.setFont(new Font("consolas",Font.BOLD,35));
        g.drawString("FPS :"+ currentFps,66,50);
    	}else {
    		 g.setColor(new Color(0, 0, 0));
    	        g.fillRect(0, 0, 1935, 1000);
    	        g.setColor(new Color(0, 250, 250));
    	        g.setFont(new Font("Ink free",Font.BOLD,70));
    	        g.drawString(myscore+" : "+(player2score+1), 930, 125);
    	        g.setColor(new Color(250, 0, 0));
    	        g.setFont(new Font("Ink free",Font.BOLD,105));
    	        g.drawString("you died wait 3 seconds", 550, 250);
    	        mousex=dpoint1;
    	        mousey=dpoint1;
    	       
    	}
    }

    private LinkedList<Line2D.Float> calcRays(LinkedList<Line2D.Float> lines, int x, int y, int resolution, int maxDist) {
        LinkedList<Line2D.Float> rays = new LinkedList<>();
        for (int i = 0; i < resolution; i++) {
            dir = view + 175 + ((Math.PI * 2) * ((double) i / resolution) / 4);
            float minDist = maxDist;
            for (Line2D.Float line : lines) {
                float dist = getRayCast(x, y, x + (float) Math.cos(dir) * maxDist, y + (float) Math.sin(dir) * maxDist, line.x1, line.y1, line.x2, line.y2);
                if (dist < minDist && dist > 0) {
                    minDist = dist;
                }
            }
            rays.add(new Line2D.Float(x, y, x + (float) Math.cos(dir) * minDist, y + (float) Math.sin(dir) * minDist));
        }
        return rays;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int dx = e.getX();
       if(dx>=1900||dx<=50) {
    	 
    	   r.mouseMove(950,200 );
    	   if(dx>=900) {
    		   view = view+0.02;
    		//   cislo++;
    	//	   vscislo++;
    		 //  zvmn=-1;
    	   }else {
    		   view = view-0.02;
    		 //  zvmn=1;
    		 //  cislo--;
    		  // vscislo--;
    	   }
    	  
    	   
       }else {
        if (dx < viewx) {
            view -= 0.02;
            cislo--;
            vscislo--;
        //    zvmn=-1;
        } else if (dx > viewx) {
            view += 0.02;
        //    zvmn=1;
            cislo++;
            vscislo++;
     
        }
        viewx = dx;
        if(e.getY()<125||e.getY()>875) {
     	   r.mouseMove(dx,500);
        }
       }
       
      
       
      //  panel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
   //     System.out.println("ahoj"); // Prints "ahoj" when mouse is clicked
      //  System.out.println(killavalible);
        if(PI<1) {
        PI=1;
        kill=false;
        if (killavalible) {
        	kill=true;
        	PI=2;
        }
        }
   //     System.out.println(kill);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    int[][] preper3D() {
        int[][] array = new int[387][4];
        int heightsl = 0;
        int i = 0;
        cislo=1;
        cislo=cislo+vscislo;
        if (cislo>8) {
        	cislo=(cislo%8)+1;
        }
        if (cislo<1) {
        	cislo=cislo%8+8;

            if (cislo>8) {
            	cislo=(cislo%8)+1;
            }
        }
    //    System.out.println(cislo);
        for (Line2D.Float ray : rays) {
            double dist2 = ((ray.x1 - ray.x2) * (ray.x1 - ray.x2)) + ((ray.y1 - ray.y2) * (ray.y1 - ray.y2));
            double dist = Math.sqrt(dist2);
            double procentmaxdist = 16;
            int col;
            col = (int) (/*255 -*/ (255 / 100 * (dist / procentmaxdist)));
            array[i][0] = col;

            if (dist >= 1599) {
                heightsl = 0;
            } else {
                heightsl = (int) ((1000 / dist * 2) / 0.3 * 10);
            }
            array[i][1] = heightsl;
            
            	
            
            if((xctverce-2<=(int)ray.x2 &&xctverce+17>=(int)ray.x2)&&(yctverce-2<=(int)ray.y2 &&yctverce+17>=(int)ray.y2)) {
            	array[i][2]=3;
            }else {
            	array[i][2]=0;
            		//if(((int)ray.x2%32==0/*||(int)ray.x2==1935*/&&((int)ray.y2%32==0||(int)ray.y2%32==1))||(int)ray.x2==1935&&(int)ray.y2%10==0) {
            			//array[i][2]=5;
            		//}
            if  ((int)ray.x2 % 32==0||(int)ray.x2 ==0) {
            		array[i][2]=5;
            	}
            	
               
            }
            if (cislo>8) {
            	cislo=1;
            }
            if (cislo<1) {
            	cislo=8;
            }
            array[i][3]=cislo;
            cislo++;
           
            
            i++;
        }
        return array;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    	if (e.getKeyCode()==27) {
    		System.exit(0);
    	}
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                moveUp = true;
                break;
            case KeyEvent.VK_S:
                moveDown = true;
                break;
            case KeyEvent.VK_A:
            	
                moveLeft = true;
                break;
            case KeyEvent.VK_D:
            	
                moveRight = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                moveUp = false;
                break;
            case KeyEvent.VK_S:
                moveDown = false;
                break;
            case KeyEvent.VK_A:
                moveLeft = false;
                break;
            case KeyEvent.VK_D:
                moveRight = false;
                break;
        }
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
    private static void loadTextures() {
        try {
                    
            texture01_1 = ImageIO.read(new File("textura01_1.png"));
            texture01_2 = ImageIO.read(new File("textura01_2.png"));
            texture01_3 = ImageIO.read(new File("textura01_3.png"));
            texture01_4 = ImageIO.read(new File("textura01_4.png"));
            texture01_5 = ImageIO.read(new File("textura01_5.png"));
            texture01_6 = ImageIO.read(new File("textura01_6.png"));
            texture01_7 = ImageIO.read(new File("textura01_7.png"));
            texture01_8 = ImageIO.read(new File("textura01_8.png"));
            
            texture02_1 = ImageIO.read(new File("textura02_1.png"));
            texture02_2 = ImageIO.read(new File("textura02_2.png"));
            texture02_3 = ImageIO.read(new File("textura02_3.png"));
            texture02_4 = ImageIO.read(new File("textura02_4.png"));
            texture02_5 = ImageIO.read(new File("textura02_5.png"));
            texture02_6 = ImageIO.read(new File("textura02_6.png"));
            texture02_7 = ImageIO.read(new File("textura02_7.png"));
            texture02_8 = ImageIO.read(new File("textura02_8.png"));
            
            texture03_1 = ImageIO.read(new File("textura03_1.png"));
            texture03_2 = ImageIO.read(new File("textura03_2.png"));
            texture03_3 = ImageIO.read(new File("textura03_3.png"));
            texture03_4 = ImageIO.read(new File("textura03_4.png"));
            texture03_5 = ImageIO.read(new File("textura03_5.png"));
            texture03_6 = ImageIO.read(new File("textura03_6.png"));
            texture03_7 = ImageIO.read(new File("textura03_7.png"));
            texture03_8 = ImageIO.read(new File("textura03_8.png"));
            
            texture04_1 = ImageIO.read(new File("textura04_1.png"));
            texture04_2 = ImageIO.read(new File("textura04_2.png"));
            texture04_3 = ImageIO.read(new File("textura04_3.png"));
            texture04_4 = ImageIO.read(new File("textura04_4.png"));
            texture04_5 = ImageIO.read(new File("textura04_5.png"));
            texture04_6 = ImageIO.read(new File("textura04_6.png"));
            texture04_7 = ImageIO.read(new File("textura04_7.png"));
            texture04_8 = ImageIO.read(new File("textura04_8.png"));
            
            texture05_1 = ImageIO.read(new File("textura05_1.png"));
            texture05_2 = ImageIO.read(new File("textura05_2.png"));
            texture05_3 = ImageIO.read(new File("textura05_3.png"));
            texture05_4 = ImageIO.read(new File("textura05_4.png"));
            texture05_5 = ImageIO.read(new File("textura05_5.png"));
            texture05_6 = ImageIO.read(new File("textura05_6.png"));
            texture05_7 = ImageIO.read(new File("textura05_7.png"));
            texture05_8 = ImageIO.read(new File("textura05_8.png"));
            
            texture06_1 = ImageIO.read(new File("textura06_1.png"));
            texture06_2 = ImageIO.read(new File("textura06_2.png"));
            texture06_3 = ImageIO.read(new File("textura06_3.png"));
            texture06_4 = ImageIO.read(new File("textura06_4.png"));
            texture06_5 = ImageIO.read(new File("textura06_5.png"));
            texture06_6 = ImageIO.read(new File("textura06_6.png"));
            texture06_7 = ImageIO.read(new File("textura06_7.png"));
            texture06_8 = ImageIO.read(new File("textura06_8.png"));
            
            texture07_1 = ImageIO.read(new File("textura07_1.png"));
            texture07_2 = ImageIO.read(new File("textura07_2.png"));
            texture07_3 = ImageIO.read(new File("textura07_3.png"));
            texture07_4 = ImageIO.read(new File("textura07_4.png"));
            texture07_5 = ImageIO.read(new File("textura07_5.png"));
            texture07_6 = ImageIO.read(new File("textura07_6.png"));
            texture07_7 = ImageIO.read(new File("textura07_7.png"));
            texture07_8 = ImageIO.read(new File("textura07_8.png"));
                        
            texture08_1 = ImageIO.read(new File("textura08_1.png"));
            texture08_2 = ImageIO.read(new File("textura08_2.png"));
            texture08_3 = ImageIO.read(new File("textura08_3.png"));
            texture08_4 = ImageIO.read(new File("textura08_4.png"));
            texture08_5 = ImageIO.read(new File("textura08_5.png"));
            texture08_6 = ImageIO.read(new File("textura08_6.png"));
            texture08_7 = ImageIO.read(new File("textura08_7.png"));
            texture08_8 = ImageIO.read(new File("textura08_8.png"));
                        
            texture09_1 = ImageIO.read(new File("textura09_1.png"));
            texture09_2 = ImageIO.read(new File("textura09_2.png"));
            texture09_3 = ImageIO.read(new File("textura09_3.png"));
            texture09_4 = ImageIO.read(new File("textura09_4.png"));
            texture09_5 = ImageIO.read(new File("textura09_5.png"));
            texture09_6 = ImageIO.read(new File("textura09_6.png"));
            texture09_7 = ImageIO.read(new File("textura09_7.png"));
            texture09_8 = ImageIO.read(new File("textura09_8.png"));
            
            enemy = ImageIO.read(new File("enemy.png"));
        } catch (IOException e) {
            System.err.println("Error loading texture: " + e.getMessage());
        }
        
    }


	
}

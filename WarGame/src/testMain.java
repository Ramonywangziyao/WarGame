import java.awt.Color;

import  sun.audio.*;    //import the sun.audio package

import  java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


import javax.print.attribute.standard.Media;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
public class testMain implements Runnable{
	private static String fileName = "Keren";
	static JMenuItem one;
	static JMenuItem two;
	static JMenuItem three; 
	static JMenuItem four; 
	static JMenuItem five;
	static JMenuItem six;
	static int mouseX;
	static int mouseY;
	static Node pOne;
	static Node pTwo;
	static int playX;
	static Node playerOne = null;
	static Node playerTwo = null;
	static int playY;
	static String pOneScore;
	static String pTwoScore;
	static int [] dx = {0,-1,0,1};
	static int [] dy = {-1,0,1,0};
	static long totalOne;
	static long	totalTwo; 
	static long turncount=1;
	static double totalTone=0;
	static double totalTtwo=0;
	static JFrame f;
	static testBoard mm;
	static int oneScore = 0;
	static int twoScore = 0;
	static JMenuBar menubar;
	static JMenu BoardFile =new JMenu("BoardFile");;
	static JMenu algorithm = new JMenu("Strategy");;
	static JMenuItem strategyOne = new JMenuItem("Alpha-B vs Alpha-B");
	static JMenuItem strategyTwo = new JMenuItem("MiniMax vs Minimax");
	static JMenuItem strategyThree = new JMenuItem("Alpha-B vs Minimax");
	static JMenuItem strategyFour = new JMenuItem("Minimax vs Alpha-B");
	static JMenuItem strategyF = new JMenuItem("Human vs Minimax");
	static JMenuItem strategyS = new JMenuItem("Human vs Alpha-B");
	
	
	public static void eatOpponent(Node[][] gameBoard,int playerID,int x,int y)
	{
			gameBoard[x][y].setOwnership(playerID);
	}

	public static class AL extends MouseAdapter implements Runnable
	{
		public void mouseClicked(MouseEvent e)
		{
			//get mouse coordinates
			//player round
	
			
			mouseX = e.getY()/50-1;
			mouseY = e.getX()/50;
			if(mm.gameBoard[mouseX][mouseY].getOwnership()==0)
			{
				//System.out.println("asdasds");
			mm.gameBoard[mouseX][mouseY].setOwnership(1);
			oneScore += mm.gameBoard[mouseX][mouseY].getScore();
			//setup
			boolean pOneconnected = false;
			//check eat or not
			for(int i = 0;i<dx.length;i++)
			{

					int newX = mouseX+dx[i];
					int newY = mouseY+dy[i];
					if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
					{
					if(mm.gameBoard[newX][newY].getOwnership()==1)
					{
						pOneconnected = true;
					}
					}
				
			}
			if(pOneconnected == true)
			{
				for(int i = 0;i<dx.length;i++)
				{
	
						int newX = mouseX+dx[i];
						int newY = mouseY+dy[i];
						if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
						{
						if(mm.gameBoard[newX][newY].getOwnership()!=1&&mm.gameBoard[newX][newY].getOwnership()!=0)
						{
							eatOpponent(mm.gameBoard, 1, newX, newY);
							
							oneScore+=mm.gameBoard[newX][newY].getScore();
							twoScore-=mm.gameBoard[newX][newY].getScore();
						}
						}
					
				}
			}
		
			mm.pOs = Integer.toString(oneScore);
			mm.pTs = Integer.toString(twoScore);
			
			mm.repaint();
			//the agent round
			
			 Thread two = new Thread()
		        {
		        	
		        	public void run()
		        	{
		        		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		long tStartT = System.currentTimeMillis();
		        		try {
		        			//play with minimax
		        			pTwo = mm.minimax(null, true, mm.gameBoard, 2, 3);
		        		} catch (InterruptedException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		}
		        		if(pTwo!=null)
		        		{
		        		mm.gameBoard[pTwo.getX()][pTwo.getY()].setOwnership(2);
		        		}
		        		try
		        		{
		        		twoScore +=mm.gameBoard[pTwo.getX()][pTwo.getY()].getScore();
		        		}
		        		catch(Exception e)
		        		{
		        			
		        		}
		        		//check connected
		        		boolean pTwoconnected = false;
		        		for(int i = 0;i<dx.length;i++)
		        		{

		        				int newX = pTwo.getX()+dx[i];
		        				int newY = pTwo.getY()+dy[i];
		        				if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
		        				{
		        				if(mm.gameBoard[newX][newY].getOwnership()==2)
		        				{
		        					pTwoconnected = true;
		        				}
		        				}
		        			
		        		}
		        		if(pTwoconnected == true)
		        		{
		        			for(int i = 0;i<dx.length;i++)
		        			{

		        					int newX = pTwo.getX()+dx[i];
		        					int newY = pTwo.getY()+dy[i];
		        					if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
		        					{
		        					if(mm.gameBoard[newX][newY].getOwnership()!=2&&mm.gameBoard[newX][newY].getOwnership()!=0)
		        					{
		        						eatOpponent(mm.gameBoard, 2, newX, newY);
		        						pTwo.setAccumulated(pTwo.getAccumulated()+mm.gameBoard[newX][newY].getScore());
		    							twoScore+=mm.gameBoard[newX][newY].getScore();
		    							oneScore-=mm.gameBoard[newX][newY].getScore();
		        					}
		        					}
		        				
		        			}
		        		}
		    			long tEndT = System.currentTimeMillis();
		    			long tDelta = tEndT - tStartT;
		    			double elapsedSeconds = tDelta / 1000.0;
		    			mm.pTt = Double.toString(elapsedSeconds);
		    			mm.pOs = Integer.toString(oneScore);
		    			mm.pTs = Integer.toString(twoScore);
		    			//mm.playeroneNode = playerTwo.nodeExpanded;
		    			totalTwo+=playerTwo.nodeExpanded;
		    			mm.ran = totalTwo/turncount;
		    			mm.rat = totalTtwo/turncount;
		    			mm.counter = 0;
		    			mm.playertwoNode = totalTwo;
		    		
		    			mm.repaint();
		    			turncount+=1;

		        	}
		        };
		        two.start();
			}
			Thread.currentThread().stop();
		}
	      @Override
	      public void mouseReleased(MouseEvent e) {
	       
	      }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}
	public static class ALA extends MouseAdapter implements Runnable
	{
		public void mouseClicked(MouseEvent e)
		{
			//setup get coordinates
			//player round
			mouseX = e.getY()/50-1;
			mouseY = e.getX()/50;
			//System.out.println("x:  "+mouseX+"  y: "+mouseY);
			if(mm.gameBoard[mouseX][mouseY].getOwnership()==0)
			{
			//	System.out.println("asdasds");
			mm.gameBoard[mouseX][mouseY].setOwnership(1);
			oneScore += mm.gameBoard[mouseX][mouseY].getScore();
			boolean pOneconnected = false;
			//check connected
			for(int i = 0;i<dx.length;i++)
			{

					int newX = mouseX+dx[i];
					int newY = mouseY+dy[i];
					if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
					{
					if(mm.gameBoard[newX][newY].getOwnership()==1)
					{
						pOneconnected = true;
					}
					}
				
			}
			//eat
			if(pOneconnected == true)
			{
				for(int i = 0;i<dx.length;i++)
				{
	
						int newX = mouseX+dx[i];
						int newY = mouseY+dy[i];
						if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
						{
						if(mm.gameBoard[newX][newY].getOwnership()!=1&&mm.gameBoard[newX][newY].getOwnership()!=0)
						{
							eatOpponent(mm.gameBoard, 1, newX, newY);
							
							oneScore+=mm.gameBoard[newX][newY].getScore();
							twoScore-=mm.gameBoard[newX][newY].getScore();
						}
						}
					
				}
			}
		
			mm.pOs = Integer.toString(oneScore);
			mm.pTs = Integer.toString(twoScore);
			mm.repaint();
			//player two round
			 Thread two = new Thread()
		        {
		        	
		        	public void run()
		        	{
		        		try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		        		long tStartT = System.currentTimeMillis();
		        		try {
		        			//alphabeta
		        			pTwo = mm.newAlphaBeta(null, true, mm.gameBoard, 2, 3,false);
		        		} catch (InterruptedException e1) {
		        			// TODO Auto-generated catch block
		        			e1.printStackTrace();
		        		}
		        		if(pTwo!=null)
		        		{
		        		mm.gameBoard[pTwo.getX()][pTwo.getY()].setOwnership(2);
		        		}
		        		try
		        		{
		        		twoScore +=mm.gameBoard[pTwo.getX()][pTwo.getY()].getScore();
		        		}
		        		catch(Exception e)
		        		{
		        			
		        		}
		        		boolean pTwoconnected = false;
		        		//check connected
		        		for(int i = 0;i<dx.length;i++)
		        		{

		        				int newX = pTwo.getX()+dx[i];
		        				int newY = pTwo.getY()+dy[i];
		        				if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
		        				{
		        				if(mm.gameBoard[newX][newY].getOwnership()==2)
		        				{
		        					pTwoconnected = true;
		        				}
		        				}
		        			
		        		}
		        		if(pTwoconnected == true)
		        		{
		        			for(int i = 0;i<dx.length;i++)
		        			{

		        					int newX = pTwo.getX()+dx[i];
		        					int newY = pTwo.getY()+dy[i];
		        					if((newX>=0&&newX<mm.gameBoard.length)&&(newY>=0&&newY<mm.gameBoard.length))
		        					{
		        					if(mm.gameBoard[newX][newY].getOwnership()!=2&&mm.gameBoard[newX][newY].getOwnership()!=0)
		        					{
		        						eatOpponent(mm.gameBoard, 2, newX, newY);
		        						pTwo.setAccumulated(pTwo.getAccumulated()+mm.gameBoard[newX][newY].getScore());
		    							twoScore+=mm.gameBoard[newX][newY].getScore();
		    							oneScore-=mm.gameBoard[newX][newY].getScore();
		        					}
		        					}
		        				
		        			}
		        		}
		    			long tEndT = System.currentTimeMillis();
		    			long tDelta = tEndT - tStartT;
		    			double elapsedSeconds = tDelta / 1000.0;
		    			mm.pTt = Double.toString(elapsedSeconds);
		    			mm.pOs = Integer.toString(oneScore);
		    			mm.pTs = Integer.toString(twoScore);
		    			//mm.playeroneNode = playerTwo.nodeExpanded;
		    			totalTwo+=playerTwo.nodeExpanded;
		    			mm.ran = totalTwo/turncount;
		    			mm.rat = totalTtwo/turncount;
		    			mm.counter = 0;
		    			mm.playertwoNode = totalTwo;
		    			mm.repaint();
		    			turncount+=1;

		        	}
		        };
		        two.start();
			}
			Thread.currentThread().stop();
		}
	      @Override
	      public void mouseReleased(MouseEvent e) {
	       
	      }
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	}



	public static void main(String[]args) throws IOException,InterruptedException
	{
		//initialize
		f = new JFrame();
		menubar = new JMenuBar();
		f.setJMenuBar(menubar);
		menubar.add(BoardFile);
		menubar.add(algorithm);
		one = new JMenuItem("Smolensk.txt");
	    one.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					fileName = "Smolensk.txt";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					
					
	            }

	        });
		 two = new JMenuItem("Narvik.txt");
	     two.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					fileName = "Narvik";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
	            }

	        });
		 three = new JMenuItem("Sevastopol.txt");
	     three.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					fileName = "Sevastopol";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
	            }

	        });
		 four = new JMenuItem("Smolensk.txt");
	     four.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					fileName = "Smolensk";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
	            }

	        });
		 five = new JMenuItem("Westerplatte.txt");
	     five.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					fileName = "Westerplatte";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
	            }

	        });
	 	six = new JMenuItem("ziyao.txt");
	     six.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
					fileName = "ziyao";
					try {
						mm = new testBoard(fileName);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					f.dispose();
					f = new JFrame();
					JMenuBar menubar = new JMenuBar();
					menubar.add(BoardFile);
					menubar.add(algorithm);
					f.setJMenuBar(menubar);
					f.setTitle("Game War");
					f.add(mm);
					f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
					f.setBackground(Color.WHITE);
					f.setLocationRelativeTo(null);
					f.setResizable(false);
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					f.setVisible(true);
					Thread.currentThread().stop();
					mm.pOs = "";
					mm.pOt = "";
					mm.pTs = "";
					mm.pTt = "";
					mm.alpha = null;
					mm.beta = null;
					mm.ban = 0;
					mm.bat = 0;
					mm.ran = 0;
					mm.rat = 0;
					mm.counter = 0;
					mm.playeroneNode = 0;
					mm.playertwoNode = 0;
	            }

	        });
		BoardFile.add(one);
		BoardFile.add(two);
		BoardFile.add(three);
		BoardFile.add(four);
		BoardFile.add(five);
		BoardFile.add(six);
		strategyOne.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
            	mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
            	try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	f.dispose();
				f = new JFrame();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				f.setVisible(true);
				alphavsalpha newRound = new alphavsalpha(mm);
            	newRound.start();
		        	
				
            }
        
        });
		strategyTwo.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
            	mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
            	try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	f.dispose();
				f = new JFrame();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				f.setVisible(true);
            	minimaxvsminimax newRound = new minimaxvsminimax(mm);
            	newRound.start();
		        	
				
            }
        
        });
		strategyThree.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
            	mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
            	try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	f.dispose();
				f = new JFrame();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				f.setVisible(true);
            	alphaMini newRound = new alphaMini(mm);
			
            	newRound.start();
		        	
				
            }
        
        });
		strategyFour.addActionListener(new ActionListener()  {
            public void actionPerformed(ActionEvent e) {
            	mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
            	try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	f.dispose();
				f = new JFrame();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				f.setVisible(true);
            	MiniAlpha newRound = new MiniAlpha(mm);
            	newRound.start();
		        	
				
            }
        
        });
		strategyF.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				f.dispose();
				f = new JFrame();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				f.setVisible(true);
				f.addMouseListener(new AL());
			}
			
		});
		strategyS.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				try {
					mm = new testBoard(fileName);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				f.dispose();
				f = new JFrame();
				JMenuBar menubar = new JMenuBar();
				menubar.add(BoardFile);
				menubar.add(algorithm);
				f.setJMenuBar(menubar);
				f.setTitle("Game War");
				f.add(mm);
				f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
				f.setBackground(Color.WHITE);
				f.setLocationRelativeTo(null);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mm.pOs = "";
				mm.pOt = "";
				mm.pTs = "";
				mm.pTt = "";
				mm.alpha = null;
				mm.beta = null;
				mm.ban = 0;
				mm.bat = 0;
				mm.ran = 0;
				mm.rat = 0;
				mm.counter = 0;
				mm.playeroneNode = 0;
				mm.playertwoNode = 0;
				f.setVisible(true);
				f.addMouseListener(new ALA());
			}
			
		});
		algorithm.add(strategyOne);
		algorithm.add(strategyTwo);
		algorithm.add(strategyThree);
		algorithm.add(strategyFour);
		algorithm.add(strategyF);
		algorithm.add(strategyS);
		
		

	
		mm = new testBoard(fileName);
		f.setTitle("Game War");
		f.add(mm);
		f.setSize(mm.gameBoard.length*50, mm.gameBoard.length*50+332);
		f.setBackground(Color.WHITE);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		f.setVisible(true);

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
	
	

}

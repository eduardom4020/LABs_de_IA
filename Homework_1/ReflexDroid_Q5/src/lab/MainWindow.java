package lab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow {

	private JFrame frame = new JFrame("ReflexDroid");
	
	public JLabel[] square = new JLabel[300]; //no total possuimos 300 quadrados
//	public JButton square = new JButton();

	//construtor da mainwindow
	public MainWindow(String file_name) throws FileNotFoundException 
	{
		initialize(file_name);
	}
	
//	square[i].setBackground(Color.white);

	//carrega as configuracoes iniciais da tela
	private void initialize(String file_name) throws FileNotFoundException 
	{
		frame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame();
		frame.setResizable(false);
		
		//o tamanho da tela serah 300x400, considerando que teremos uma matrix 15x20
		//e definindo cada celula como um quadrado 20x20.
		frame.setBounds(0, 0, 300, 350);
		
		frame.setLayout(new GridLayout(15, 20, 1, 1));
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i=0; i<300; i++)
		{
			square[i] = new JLabel();
			square[i].setOpaque(true);

			frame.add(square[i]);
		}
		
		frame.setVisible(true);
		this.executeSimulation(file_name);
	}
	
	public void fillSquare(String coords, int R, int G, int B)
	{
		String[] parts = coords.split(",");
		Integer X = Integer.valueOf(parts[0].substring(1));
		Integer Y = Integer.valueOf(parts[1].substring(0, parts[1].length()-1));
		
		square[X*20 + Y].setBackground(new Color(R,G,B));
		square[X*20 + Y].repaint();
	}
	
	public void executeSimulation(String file_name) throws FileNotFoundException
	{
		String[] parts;
		String lastSquare=null;
		String lastAction=null;
		int R=0;
		int G=0;
		int B=0;
		int steps=0;
		
		FileReader fr = new FileReader(new File(file_name));
		BufferedReader reader = new BufferedReader(fr);
		
		try 
		{
			if(reader.ready())
			{
				if(reader.readLine().equals("-map_creation"))
				{
					for(int i=0; i<300; i++)
					{
						parts = reader.readLine().split(":");
						
						switch(parts[1])
						{
							case "None":
								R=255; G=255; B=255;
								break;
								
							case "Obstacle":
								R=102; G=51; B=255;
								break;
								
							case "Human":
								R=255; G=0; B=0;
								break;
						}
						
						this.fillSquare(parts[0], R, G, B);
					}
				}
				
				if(reader.readLine().equals("-steps"))
				{
					steps = Integer.valueOf(reader.readLine());
				}
				
				if(reader.readLine().equals("-agent_actions"))
				{
					for(int i=0; i<steps; i++)
					{
						if(i>0) this.fillSquare(lastSquare, 255, 255, 255);
						
						parts = reader.readLine().split(":");
						
						this.fillSquare(parts[0], 0, 0, 255);
						
						lastSquare = parts[0];
						lastAction = parts[1];
						
						try 
						{
							frame.repaint();
							TimeUnit.MILLISECONDS.sleep(50);
						} catch (InterruptedException e) {e.printStackTrace();}
					}
				}
			}
			
		} catch (IOException e) {e.printStackTrace();}
	}
}

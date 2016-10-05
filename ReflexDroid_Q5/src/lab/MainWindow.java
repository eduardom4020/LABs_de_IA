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

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow {

	private JFrame frame = new JFrame("ReflexDroid");
	
//	public JLabel[] square = new JLabel[300]; //no total possuimos 300 quadrados
	public JLabel square = new JLabel();

	//construtor da mainwindow
	public MainWindow() 
	{
		initialize();
	}
	
//	square[i].setBackground(Color.white);

	//carrega as configuracoes iniciais da tela
	private void initialize() 
	{
		frame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame();
		frame.setResizable(false);
		
		//o tamanho da tela serah 300x400, considerando que teremos uma matrix 15x20
		//e definindo cada celula como um quadrado 20x20.
		frame.setBounds(0, 0, 300, 400);
		
		frame.setLayout(new GridLayout(15, 20));
		
		for(int i=0; i<300; i++)
		{
//			square[i] = new JLabel(); //depois mudar cor por icone		
//			square[i].setName("("+(300%15)+","+((int)300/20)+")");
			
//			frame.add(square[i]);	//os labels sao adicionados da esquerda para a direita, cima, baixo
			frame.add(square);
		}
		
//		frame.pack();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	public void fillSquare(String coords, int R, int G, int B)
	{
		String[] parts = coords.split(",");
		Integer X = Integer.valueOf(parts[0].substring(1));
		Integer Y = Integer.valueOf(parts[1].substring(0, parts[1].length()-1));
		
		frame.getComponentAt(X, Y).setBackground(new Color(R,G,B));
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
				if(reader.readLine() == "-map_creation")
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
				
				if(reader.readLine() == "-steps")
				{
					steps = Integer.valueOf(reader.readLine());
				}
				
				if(reader.readLine() == "-agent_actions")
				{
					for(int i=0; i<steps; i++)
					{
						if(lastAction == "rescued_human")
						{
							this.fillSquare(lastSquare, 0, 0, 0);
						}
						
						parts = reader.readLine().split(":");
						
						this.fillSquare(parts[0], 0, 0, 255);
						
						lastSquare = parts[0];
						lastAction = parts[1];
						
						try 
						{
							frame.repaint();
							TimeUnit.MILLISECONDS.sleep(300);
						} catch (InterruptedException e) {e.printStackTrace();}
					}
				}
			}
			
		} catch (IOException e) {e.printStackTrace();}
	}
}

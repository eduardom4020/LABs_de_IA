package lab;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow {

	private JFrame frame = new JFrame("ReflexDroid");
	
	public JLabel[] square = new JLabel[300]; //no total possuimos 300 quadrados

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
			square[i] = new JLabel(); //depois mudar cor por icone		
//			square[i].setName("("+) completar isso, a localizacao provavelmente precisa de modulo
			frame.add(square[i]);	//os labels sao adicionados da esquerda para a direita, cima, baixo
		}
		
		frame.pack();		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
	}
	
	public void fillSquere(String coords, String color)
	{
		String[] parts = coords.split(",");
		Integer X = Integer.valueOf(parts[0].substring(1));
		Integer Y = Integer.valueOf(parts[1].substring(0, parts[1].length()-1));
		
		
	}
}

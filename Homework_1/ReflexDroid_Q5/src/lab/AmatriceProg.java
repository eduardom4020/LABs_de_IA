package lab;

import java.io.FileNotFoundException;

import aima.core.agent.Agent;
import aima.core.agent.Environment;
import aima.core.agent.EnvironmentView;
import aima.core.agent.impl.SimpleEnvironmentView;

/**
 * Demonstrates, how to set up a simple environment, place an agent in it,
 * and run it. The vacuum world is used as a simple example.
 * 
 * @author Saulo Oliveira
 */
public class AmatriceProg {
	
	public static final String SIMULATION_NUMBER = "_0";
	public static final int SIMULATION_STEPS = 1500;
	
	public static void main(String[] args) {

		Environment env = new AmatriceEnviroment();
		EnvironmentView view = new SimpleEnvironmentView();
		env.addEnvironmentView(view);
		
		Agent agent = new ReflexDroid();
		
		env.addAgent(agent);
		
		env.step(SIMULATION_STEPS);
		env.notifyViews("Performance=" + env.getPerformanceMeasure(agent));
		
	//executamos a simulacao:		
		try 
		{			
			MainWindow gui = new MainWindow("simulation_output"+ AmatriceProg.SIMULATION_NUMBER +".txt");
			
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
}

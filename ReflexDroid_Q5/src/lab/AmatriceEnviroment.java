package lab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import aima.core.agent.Action;
import aima.core.agent.Agent;
import aima.core.agent.Percept;
import aima.core.agent.impl.AbstractEnvironment;
import aima.core.agent.impl.DynamicAction;

public class AmatriceEnviroment extends AbstractEnvironment {
	// Add new Actions
	public static final Action ACTION_MOVE = new DynamicAction("Move");
	public static final Action ACTION_GRAB = new DynamicAction("Grab");
	public static final Action ACTION_TAKE_OFF = new DynamicAction("Take_Off");

	public enum LocationState {
		Human, None, Obstacle
	};

	protected AmatriceEnvironmentState envState = null;

	public AmatriceEnviroment() {
		envState = new AmatriceEnvironmentState();
	}

	@Override
	public void executeAction(Agent agent, Action action) {
		String[] parts;
		String currentLocation;
		String nextLocation;
		LocationState state;
		String action_for_emulation=null;
		
		if ((ACTION_MOVE == action)) 
		{
			// get current agent location
			currentLocation = envState.getAgentLocation(agent);
			parts = currentLocation.split(",");
			Integer X = Integer.valueOf(parts[0].substring(1));
			Integer Y = Integer.valueOf(parts[1].substring(0, parts[1].length()-1));
			
			switch (envState.agentFront)
			{
				case 6:
					nextLocation = "(" + (X + 1) + "," + Y + ")";					
					break;
				case 8:
					nextLocation = "(" + X + "," + (Y - 1) + ")";
					break;
				case 4:
					nextLocation = "(" + (X - 1) + "," + Y + ")";					
					break;
				case 2:
					nextLocation = "(" + X + "," + (Y + 1) + ")";
					break;
				default:
					nextLocation = null;
					break;
			}
			
			state = envState.getLocationState(nextLocation);
			
			//se a frente existe apenas um tile vazio, ou um humano, entao o agente pode andar
			if((state == LocationState.None) || (state == LocationState.Human))
			{
				envState.setAgentLocation(agent, nextLocation);
				
				action_for_emulation = "moved-";
			}
			else // se nao, o agente sofre uma rotacao em 90 graus randomicamente
			{
				Random rd = new Random();
				if(rd.nextInt(1) == 1)
				{
				//rotaciona -90
					switch (envState.agentFront)
					{
						case 6: envState.agentFront = 2; break;
						case 8: envState.agentFront = 6; break;
						case 4: envState.agentFront = 8; break;
						case 2: envState.agentFront = 4; break;
					}
				}
				else
				{
				//rotaciona 90
					switch (envState.agentFront)
					{
						case 6: envState.agentFront = 8; break;
						case 8: envState.agentFront = 4; break;
						case 4: envState.agentFront = 2; break;
						case 2: envState.agentFront = 6; break;
					}
				}
			}
			
			action_for_emulation = "turned-" + envState.agentFront;
			
			performanceMeasures.put(agent, getPerformanceMeasure(agent)-1);
		}
		if (ACTION_GRAB == action) 
		{
			
			currentLocation = envState.getAgentLocation(agent);

			state = envState.getLocationState(currentLocation);
			if (state == LocationState.Human) 
			{				
				envState.setLocationState(envState.getAgentLocation(agent), LocationState.None);					
			}	
			
			action_for_emulation = "rescued-";
			
			performanceMeasures.put(agent, getPerformanceMeasure(agent)+100);
		}
		if ((ACTION_TAKE_OFF == action))
		{
			//Nao eh necessaria, pois a simulacao irah acabar apos 1500 passos
			//tambem nao cabe ao robo saber quantos humanos existem no ambiente,
			//por isso a condicao de parada nao serah definida.
		}
		
	//para fins de plot: grava cada acao feita pelo agente
		try 
		{
			FileWriter fw = new FileWriter(new File("simulation_output"+ AmatriceProg.SIMULATION_NUMBER +".txt"), true);
			fw.write(envState.getAgentLocation(agent) + ":" + action_for_emulation);
			fw.write(System.lineSeparator());
			fw.close();
			
		} catch (IOException e) {e.printStackTrace();}
				
	//=========================================
	}

	@Override
	public void addAgent(Agent a) {
		envState.setAgentLocation(a, "(1,1)");
		envState.agentFront = 6;
		super.addAgent(a);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		String agentLocation = envState.getAgentLocation(anAgent);
		return new AmatriceEnvironmentPercept(agentLocation, envState.getLocationState(agentLocation));
	}

}

package lab;

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
		
		if ((ACTION_MOVE == action)) {
			// get current agent location
			currentLocation = envState.getAgentLocation(agent);
			parts = currentLocation.split(",");
			
			if(envState.agentFront == 'x')
			{
			//pegamos o numero da coordenada X e adicionamos 1 a mesma				
				nextLocation = "(" + (Integer.valueOf(parts[0].substring(1)) + 1) + "," + parts[1];
			}
			else
			{
				nextLocation = parts[0] + "," + (Integer.valueOf(parts[1].substring(0, parts[1].length()-1)) + 1) + ")";
			}
			
			state = envState.getLocationState(nextLocation);
			
			//se a frente existe apenas um tile vazio, ou um humano, entao o agente pode andar
			if((state == LocationState.None) || (state == LocationState.Human))
			{
				envState.setAgentLocation(agent, nextLocation);
			}
			else // se nao, o agente sofre uma rotacao em 90 graus randomicamente
			{
				
			}

		}
		if (ACTION_GRAB == action) {
			
			currentLocation = envState.getAgentLocation(agent);

			state = envState.getLocationState(currentLocation);
			if (state == LocationState.Human) {
				System.out.println("A HUMAN!! DON'T WORRY, CAUSE I'M HERE!!");
				
				envState.setLocationState(envState.getAgentLocation(agent), LocationState.None);
				
		
				
			}
			
		if ((ACTION_TAKE_OFF == action))
		{

		}			
			

		}
		// Complete the other actions
	}

	@Override
	public void addAgent(Agent a) {
		envState.setAgentLocation(a, "(1,1)");
		envState.agentFront = 'x';
		super.addAgent(a);
	}

	@Override
	public Percept getPerceptSeenBy(Agent anAgent) {
		String agentLocation = envState.getAgentLocation(anAgent);
		return new AmatriceEnvironmentPercept(agentLocation, envState.getLocationState(agentLocation));
	}

}

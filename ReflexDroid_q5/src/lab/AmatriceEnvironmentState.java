package lab;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import aima.core.agent.Agent;
import aima.core.agent.EnvironmentState;
import lab.AmatriceEnviroment.LocationState;

public class AmatriceEnvironmentState implements EnvironmentState, FullyObservableAmatriceEnvironmentPercept, Cloneable {

	private Map<String, AmatriceEnviroment.LocationState> state;
	private Map<Agent, String> agentLocations;
	public char agentFront;
	
	/**
	 * Constructor
	 */
	public AmatriceEnvironmentState() {
		state = new LinkedHashMap<String, AmatriceEnviroment.LocationState>();
		agentLocations = new LinkedHashMap<Agent, String>();
		initWorld();
	}

	private void initWorld() {
		boolean putHuman=false;
		
		// TODO Auto-generated method stub
		// Fill the AmatriceEnviroment specification
		// call the fill method bellow filling each stage
		
		// Example:
		// fill(LocationState.Human, LocationState.Rock, LocationState.Human);
		
		Random rd = new Random();
		
		int n = rd.nextInt(15 - 8) + 8; //faz range de 8 a 15
		this.state.clear();
		
		for(int i=0; i<15; i++)
		{
			for(int j=0; j<20; j++)
			{
				if((i==0) || (i==14) || (j==0) || (j==19))	//criamos as paredes nestas localizacoes
				{
					this.state.put("("+i+","+j+")", LocationState.Obstacle);
				}
				else
				{
					if(rd.nextInt(100)%33 == 0) //possibilidade de existir parede
					{
						this.state.put("("+i+","+j+")", LocationState.Obstacle);
					}
					else if (rd.nextInt(100)%10 == 0) //possibilidade de existir humano
					{
						this.state.put("("+i+","+j+")", LocationState.Human);
						putHuman=true;
					}
					else
					{
						this.state.put("("+i+","+j+")", LocationState.None);
					}
				}
			}
		}
		if(!putHuman) //se nao colocou nenhum humano, colocamos ele na posicao 14x19
		{
			this.state.put("(14,19)", LocationState.Human);
		}
		this.state.put("(1,1)", LocationState.None);
	}
	

	@Override
	public String getAgentLocation(Agent a) {
		return agentLocations.get(a);
	}

	/**
	 * Sets the agent location
	 * 
	 * @param a
	 * @param location
	 */
	public void setAgentLocation(Agent a, String location) {
		agentLocations.put(a, location);
	}

	@Override
	public AmatriceEnviroment.LocationState getLocationState(String location) {
		return state.get(location);
	}

	/**
	 * Sets the location state
	 * 
	 * @param location
	 * @param s
	 */
	public void setLocationState(String location, AmatriceEnviroment.LocationState s) {
		state.put(location, s);
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() == obj.getClass()) {
			AmatriceEnvironmentState s = (AmatriceEnvironmentState) obj;
			return state.equals(s.state) && agentLocations.equals(s.agentLocations);
		}
		return false;
	}

	/**
	 * Override hashCode()
	 * 
	 * @return the hash code for this object.
	 */
	@Override
	public int hashCode() {
		return 3 * state.hashCode() + 13 * agentLocations.hashCode();
	}

	@Override
	public AmatriceEnvironmentState clone() {
		AmatriceEnvironmentState result = null;
		try {
			result = (AmatriceEnvironmentState) super.clone();
			result.state = new LinkedHashMap<String, LocationState>(state);
			agentLocations = new LinkedHashMap<Agent, String>(agentLocations);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Returns a string representation of the environment
	 * 
	 * @return a string representation of the environment
	 */
	@Override
	public String toString() {
		return this.state.toString();
	}

	public boolean isAgentAtHome(Agent agent) {
		// TODO Auto-generated method stub
		int actualLocation = Integer.parseInt(this.getAgentLocation(agent));
		return actualLocation == state.size();
	}

}

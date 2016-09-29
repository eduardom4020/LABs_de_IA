package lab;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.impl.AbstractAgent;
import aima.core.agent.impl.aprog.SimpleReflexAgentProgram;
import aima.core.agent.impl.aprog.simplerule.EQUALCondition;
import aima.core.agent.impl.aprog.simplerule.Rule;


public class ReflexDroid extends AbstractAgent {

	
	public ReflexDroid() {
		super(new SimpleReflexAgentProgram(getRuleSet()));
	}
	
	
	private static Set<Rule> getRuleSet() {
		Set<Rule> rules = new LinkedHashSet<Rule>();

		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE,
				AmatriceEnviroment.LocationState.None),
				AmatriceEnviroment.ACTION_MOVE));
		
		rules.add(new Rule(new EQUALCondition(AmatriceEnvironmentPercept.ATTRIBUTE_STATE,
				AmatriceEnviroment.LocationState.Human),
				AmatriceEnviroment.ACTION_GRAB));
		
		
		return rules;
	}
}

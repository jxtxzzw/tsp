package handler;

public class ConstantValue {

	public static final int EXCEPTION_EXIT_CODE = -1;
	public static final int GENETIC_GROUP_CAPACITY = 200;
	public static final int GENETIC_IERATE_TIMES = 5000;
	public static final double GENETIC_CROSSOVER_PROBABILITY = 0.35;
	public static final double GENETIC_MUTATE_PROBABILITY = 0.4;
	public static final int LOCAL_SEARCH_SUCCESSFULLY_CHANGE_TIMES = 50000;
	public static final int LOCAL_SEARCH_MAXIMUM_CHANGE_TIMES_THRESHOLD = LOCAL_SEARCH_SUCCESSFULLY_CHANGE_TIMES * 50;
	public static final double CHILLING_TEMPERATURE = 1;
	public static final double INITIAL_TEMPERATURE = 500000;
	public static final double COOLING_SPEED = 0.98;
	public static final int SIMULATE_ANNEA_ITERATION_TIMES = GENETIC_IERATE_TIMES;
	
	
	private ConstantValue() {

	}

}

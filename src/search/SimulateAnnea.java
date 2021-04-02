package search;

import data.City;
import data.Route;
import handler.ConstantValue;
import handler.IOHandler;

public class SimulateAnnea {

	public static void run(String fileName) {

		City[] cities = IOHandler.input(fileName);

		double currentTemperature = ConstantValue.INITIAL_TEMPERATURE;

		// 用shuffle随机一条路径作为初始解，并复制一份作为初始最优解
		Route bestRoute = new Route(cities);
		bestRoute.shuffle();
		Route currentRoute = new Route(bestRoute);

		// 循环，直到降温到CHILLING_TEMPERATURE
		while (ConstantValue.CHILLING_TEMPERATURE < currentTemperature) {
//			 System.out.println("Current temperature: " + currentTemperature);
//			 System.out.println("Current best route length: " + bestRoute.getRouteLength());

			/*
			 * 当温度较高的时候，接受劣解的概率比较大，在初始高温下，几乎以100%的概率接受劣解 随着温度的下降，接受劣解的概率逐渐减少
			 * 直到当温度趋于0时，接受劣解的概率也同时趋于0 这样将有利于算法从局部最优解中跳出，求得问题的全局最优解
			 */
			double p = getProbabilityToAcceptWorseRoute(currentTemperature);

			// 每次降温需要满足迭代次数
			for (int i = 0; i < ConstantValue.SIMULATE_ANNEA_ITERATION_TIMES; i++) {
				Route neighbourRoute = currentRoute.generateNeighbour();

				if (neighbourRoute.getRouteLength() <= bestRoute.getRouteLength()) {
					// 如果新解比当前解优，接受
					bestRoute = new Route(neighbourRoute);
					currentRoute = neighbourRoute;
				} else if (metropolis(bestRoute, neighbourRoute, currentTemperature) > p) {
					// 否则按照Metropolis准则确定接受的概率
					bestRoute = new Route(neighbourRoute);
					currentRoute = neighbourRoute;
				}
			}

			// 按照给定的速率降温
			currentTemperature *= ConstantValue.COOLING_SPEED;

		}

		// 输出最优解
		bestRoute.print();

	}

	/**
	 * 能够接受劣解的概率
	 * 
	 * @param currentTemperature
	 *            当前温度
	 * @return 能够接受劣解的概率
	 */
	private static double getProbabilityToAcceptWorseRoute(double currentTemperature) {
		return (currentTemperature - ConstantValue.CHILLING_TEMPERATURE)
				/ (ConstantValue.INITIAL_TEMPERATURE - ConstantValue.CHILLING_TEMPERATURE);
	}

	/**
	 * MetroPolis准则
	 * 
	 * @param bestRoute
	 *            最优解
	 * @param currentRoute
	 *            当前解
	 * @param currentTemperature
	 *            当前温度
	 * @return MetroPolis准则的评估值
	 */
	private static double metropolis(Route bestRoute, Route currentRoute, double currentTemperature) {
		double ret = Math.exp(-(currentRoute.getRouteLength() - bestRoute.getRouteLength()) / currentTemperature);
		return ret;
	}

}

package search;

import data.City;
import data.Route;
import handler.ConstantValue;
import handler.IOHandler;

public class SimulateAnnea {

	public static void run(String fileName) {

		City[] cities = IOHandler.input(fileName);

		double currentTemperature = ConstantValue.INITIAL_TEMPERATURE;

		// ��shuffle���һ��·����Ϊ��ʼ�⣬������һ����Ϊ��ʼ���Ž�
		Route bestRoute = new Route(cities);
		bestRoute.shuffle();
		Route currentRoute = new Route(bestRoute);

		// ѭ����ֱ�����µ�CHILLING_TEMPERATURE
		while (ConstantValue.CHILLING_TEMPERATURE < currentTemperature) {
//			 System.out.println("Current temperature: " + currentTemperature);
//			 System.out.println("Current best route length: " + bestRoute.getRouteLength());

			/*
			 * ���¶Ƚϸߵ�ʱ�򣬽����ӽ�ĸ��ʱȽϴ��ڳ�ʼ�����£�������100%�ĸ��ʽ����ӽ� �����¶ȵ��½��������ӽ�ĸ����𽥼���
			 * ֱ�����¶�����0ʱ�������ӽ�ĸ���Ҳͬʱ����0 �������������㷨�Ӿֲ����Ž�����������������ȫ�����Ž�
			 */
			double p = getProbabilityToAcceptWorseRoute(currentTemperature);

			// ÿ�ν�����Ҫ�����������
			for (int i = 0; i < ConstantValue.SIMULATE_ANNEA_ITERATION_TIMES; i++) {
				Route neighbourRoute = currentRoute.generateNeighbour();

				if (neighbourRoute.getRouteLength() <= bestRoute.getRouteLength()) {
					// ����½�ȵ�ǰ���ţ�����
					bestRoute = new Route(neighbourRoute);
					currentRoute = neighbourRoute;
				} else if (metropolis(bestRoute, neighbourRoute, currentTemperature) > p) {
					// ������Metropolis׼��ȷ�����ܵĸ���
					bestRoute = new Route(neighbourRoute);
					currentRoute = neighbourRoute;
				}
			}

			// ���ո��������ʽ���
			currentTemperature *= ConstantValue.COOLING_SPEED;

		}

		// ������Ž�
		bestRoute.print();

	}

	/**
	 * �ܹ������ӽ�ĸ���
	 * 
	 * @param currentTemperature
	 *            ��ǰ�¶�
	 * @return �ܹ������ӽ�ĸ���
	 */
	private static double getProbabilityToAcceptWorseRoute(double currentTemperature) {
		return (currentTemperature - ConstantValue.CHILLING_TEMPERATURE)
				/ (ConstantValue.INITIAL_TEMPERATURE - ConstantValue.CHILLING_TEMPERATURE);
	}

	/**
	 * MetroPolis׼��
	 * 
	 * @param bestRoute
	 *            ���Ž�
	 * @param currentRoute
	 *            ��ǰ��
	 * @param currentTemperature
	 *            ��ǰ�¶�
	 * @return MetroPolis׼�������ֵ
	 */
	private static double metropolis(Route bestRoute, Route currentRoute, double currentTemperature) {
		double ret = Math.exp(-(currentRoute.getRouteLength() - bestRoute.getRouteLength()) / currentTemperature);
		return ret;
	}

}

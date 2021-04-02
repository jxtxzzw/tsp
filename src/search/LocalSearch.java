package search;

import data.City;
import data.Route;
import handler.IOHandler;

public class LocalSearch {

	public static void run(String fileName) {

		City[] cities = IOHandler.input(fileName);

		Route route = new Route(cities);
		route.shuffle();

		int successfullyChanged = 50000;
		int changed = 1000000;

		while (successfullyChanged != 0 && changed != 0) {

			Route neighbour = route.generateNeighbour();
			if (neighbour.getRouteLength() <= route.getRouteLength()) {
//				System.out.println(route.getRouteLength() + " -> " + neighbour.getRouteLength());
				route = neighbour;
				successfullyChanged--;
			}
			changed--;
		}

		route.print();

	}

}

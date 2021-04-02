package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import data.City;

public class IOHandler {

	public static City[] input(String fileName) {
		Scanner in;
		City[] cities = null;
		try {
			in = new Scanner(new File(fileName));
			int n = in.nextInt();
			cities = new City[n];

			for (int i = 0; i < n; ++i) {
				int index = in.nextInt();
				int x = in.nextInt();
				int y = in.nextInt();

				cities[i] = new City(index, x, y);
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not exist.");
			e.printStackTrace();
			System.exit(ConstantValue.EXCEPTION_EXIT_CODE);
		}

		return cities;

	}

}

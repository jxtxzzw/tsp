package main;

import search.Genetic;
import search.LocalSearch;
import search.SimulateAnnea;

public class Main {

	public static void main(String[] args) {
//		 String fileName = "1.txt";
//		 String fileName = "10-147.txt";
//		 String fileName = "20-870.txt";
//		 String fileName = "31-14700.txt";
		 String fileName = "cn144_location2.txt";

		long beginTime, endTime;

		System.out.println("Genetic:");
		beginTime = System.currentTimeMillis();
		Genetic.run(fileName);
		endTime = System.currentTimeMillis();
		System.out.println("Time cost: " + (endTime - beginTime));
		System.out.println("==========");

		System.out.println("LocalSearch:");
		beginTime = System.currentTimeMillis();
		LocalSearch.run(fileName);
		endTime = System.currentTimeMillis();
		System.out.println("Time cost: " + (endTime - beginTime));
		System.out.println("==========");

		System.out.println("SimulateAnnea:");
		beginTime = System.currentTimeMillis();
		SimulateAnnea.run(fileName);
		endTime = System.currentTimeMillis();
		System.out.println("Time cost: " + (endTime - beginTime));
		System.out.println("==========");

	}

}

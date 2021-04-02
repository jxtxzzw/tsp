package search;

import data.City;
import data.Group;
import data.Species;
import handler.ConstantValue;
import handler.IOHandler;

public class Genetic {

	public static void run(String fileName) {
		
		City[] cities = IOHandler.input(fileName);
	
		int groupCapacity = ConstantValue.GENETIC_GROUP_CAPACITY; // 种群大小
		Group group = new Group(groupCapacity); // 种群

		for (int i=0;i<groupCapacity-0;++i) {
			Species sp = new Species(cities); 
			sp.shuffle(); // 用完全随机的方法初始一条路径，加入到种群中
			group.add(sp);	
			
		}
		
		int iterateTimes = ConstantValue.GENETIC_IERATE_TIMES; // 迭代次数
		
		while (iterateTimes!=0) {
			group.randomSelectAndKill(); // 随机选择，淘汰掉表现不好的
			group.crossOver(); // 以一定概率杂交
			group.mutate(); // 以一定概率变异
			iterateTimes--;
		}
		
		group.getBest().print(); // 找到最优解然后输出
		
	}


}

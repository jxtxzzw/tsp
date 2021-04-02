package search;

import data.City;
import data.Group;
import data.Species;
import handler.ConstantValue;
import handler.IOHandler;

public class Genetic {

	public static void run(String fileName) {
		
		City[] cities = IOHandler.input(fileName);
	
		int groupCapacity = ConstantValue.GENETIC_GROUP_CAPACITY; // ��Ⱥ��С
		Group group = new Group(groupCapacity); // ��Ⱥ

		for (int i=0;i<groupCapacity-0;++i) {
			Species sp = new Species(cities); 
			sp.shuffle(); // ����ȫ����ķ�����ʼһ��·�������뵽��Ⱥ��
			group.add(sp);	
			
		}
		
		int iterateTimes = ConstantValue.GENETIC_IERATE_TIMES; // ��������
		
		while (iterateTimes!=0) {
			group.randomSelectAndKill(); // ���ѡ����̭�����ֲ��õ�
			group.crossOver(); // ��һ�������ӽ�
			group.mutate(); // ��һ�����ʱ���
			iterateTimes--;
		}
		
		group.getBest().print(); // �ҵ����Ž�Ȼ�����
		
	}


}

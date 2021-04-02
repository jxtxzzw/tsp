package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import handler.ConstantValue;

public class Group {

	private int capacity; // ��Ⱥ�������
	private LinkedList<Species> group = new LinkedList<Species>(); // ��Ⱥ��ÿ������Ϊһ������

	/**
	 * ��ʼ��һ������ָ������Ⱥ
	 * @param capacity ��Ⱥ����
	 */
	public Group(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * ����Ⱥ�м�һ������
	 * @param sp ����
	 */
	public void add(Species sp) {
		group.add(sp);
	}

	/**
	 * ���ѡ����̭�����ֲ��õĸ���
	 */
	public void randomSelectAndKill() {
		calculateSelectedRate(); // ������Ӧ�ʺͿ��ܱ�ѡ�еĸ���

		// ֱ�ӰѸ��嶪�����ȼ������У����ڸ���ʵ����Comparable�ӿڣ����Ծ���̵ķ������ֺã����ȼ���
		PriorityQueue<Species> pq = new PriorityQueue<Species>();
		pq.addAll(group);
		// �����Ⱥ��Ȼ���ո���ı��ִӺõ���ŵ���Ⱥ��
		group.clear();
		while (!pq.isEmpty()) { // Ҫô����
			group.add(pq.poll());
			if (group.size() == capacity) // Ҫô��������������Ͳ�����
				break;
		}

	}

	/**
	 * ����ѡ�����
	 * ��������ʿ��Ա��������̶ĵķ����ϣ�
	 */
	private void calculateSelectedRate() {
		double sum = 0; // ����α���ʺ�
		for (Species sp : group) 
			sum += sp.getFitness(); // ����ÿһ�����壬���²���ȡ��Ӧֵ���Ӻ�
		for (Species sp : group)
			sp.setSelectedRate(sp.getFitness() / sum); // ��һ������
	}

	/**
	 * �ӽ�
	 */
	public void crossOver() {
		double p = ConstantValue.GENETIC_CROSSOVER_PROBABILITY;
		double r = Math.random();
		// ��һ���ĸ����ӽ�
		if (r < p) {
			// ���ѡ��һ�Ը�ĸ����֤��ĸ����ͬһ������
			int fatherIndex, motherIndex;
			do {
				fatherIndex = (int) (Math.random() * group.size());
				motherIndex = (int) (Math.random() * group.size());
			} while (fatherIndex == motherIndex);
			Species father = group.get(fatherIndex);
			Species mother = group.get(motherIndex);
			
			// ����һ��ȫ��null�������ź��ӵĻ�������
			int size = father.size();
			ArrayList<City> list = new ArrayList<City>();
			for (int i = 0; i < size; ++i)
				list.add(null);
			
			// ���һ�θ�ĸ�Ļ�������
			int beginIndex = (int) (Math.random() * size);
			int endIndex = (int) (Math.random() * (size + 1));
			// ��֤beginIndex < endIndex
			if (beginIndex > endIndex) {
				int tmp = beginIndex;
				beginIndex = endIndex;
				endIndex = tmp;
			}
			// ֱ�Ӱ���һ�θ��׵Ļ����Ƶ����Ӷ�Ӧ�Ĳ���
			for (int i = beginIndex; i < endIndex; ++i)
				list.set(i, father.get(i));

			// ����ȱ�ٵĻ����ĸ��������
			int i = 0;
			// ��ͷ��ʼ����ĸ�׵Ļ���
			for (int j = 0; j < size; ++j) {
				if (i == beginIndex)
					i = endIndex; // ����������һ��
				City c = mother.get(j); // ��ȡĸ�׵Ļ���
				// ���ĸ�׵Ļ����Ѿ��ں������ϣ����������������
				if (!list.contains(c)) { 
					list.set(i, c);
					i++;
				}
			}

			// �õ����ӵĻ����Դ��½�һ�����ӣ����뵽��Ⱥ
			City[] childCities = new City[size];
			list.toArray(childCities);
			Species child = new Species(childCities);
			group.add(child);

		}

	}

	/**
	 * ����
	 */
	public void mutate() {
		double p = ConstantValue.GENETIC_MUTATE_PROBABILITY;
		// Ҫ����ԭʼ�ĸ��壬��Ϊ�����reverse����immutable�ģ�����������ֽϺõĸ������Ϳ��ܻᶪʧ�ϺõĻ���
		LinkedList<Species> origin = new LinkedList<Species>();
		// �����ÿһ�����嶼�и���
		for (Species sp : group) {
			origin.add(new Species(sp));
			double r = Math.random();
			// ��һ���ĸ��ʱ���
			if (r < p) {
				// ���ѡ��һ�λ�������
				int left = (int) (Math.random() * sp.size());
				int right = (int) (Math.random() * sp.size());
				if (left > right) {
					int tmp = left;
					left = right;
					right = tmp;
				}
				sp.reverse(left, right);
			}
		}

		// Ϊ�˷�ֹ���ֽϺõĸ�������Ժ�ʧ�û����������ڰ�ԭ���Ļ���ȫ��������
		group.addAll(origin);

	}

	/**
	 * �ҵ���õĸ���
	 * @return ������õĸ���
	 */
	public Species getBest() {
		// �����ȼ����еĶ���Ԫ��
		PriorityQueue<Species> pq = new PriorityQueue<Species>();
		pq.addAll(group);
		return pq.peek();
	}

}

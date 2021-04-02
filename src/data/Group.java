package data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

import handler.ConstantValue;

public class Group {

	private int capacity; // 种群最大容量
	private LinkedList<Species> group = new LinkedList<Species>(); // 种群，每个个体为一个方案

	/**
	 * 初始化一个容量指定的种群
	 * @param capacity 种群容量
	 */
	public Group(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * 往种群中加一个个体
	 * @param sp 个体
	 */
	public void add(Species sp) {
		group.add(sp);
	}

	/**
	 * 随机选择，淘汰掉表现不好的个体
	 */
	public void randomSelectAndKill() {
		calculateSelectedRate(); // 计算适应率和可能被选中的概率

		// 直接把个体丢掉优先级队列中，由于个体实现了Comparable接口，所以距离短的方案表现好，优先级高
		PriorityQueue<Species> pq = new PriorityQueue<Species>();
		pq.addAll(group);
		// 清空种群，然后按照个体的表现从好到差放到种群中
		group.clear();
		while (!pq.isEmpty()) { // 要么放完
			group.add(pq.poll());
			if (group.size() == capacity) // 要么超过了最大容量就不放了
				break;
		}

	}

	/**
	 * 计算选择概率
	 * （这个概率可以被用在轮盘赌的方法上）
	 */
	private void calculateSelectedRate() {
		double sum = 0; // 计算伪概率和
		for (Species sp : group) 
			sum += sp.getFitness(); // 对于每一个个体，更新并获取适应值，加和
		for (Species sp : group)
			sp.setSelectedRate(sp.getFitness() / sum); // 归一化处理
	}

	/**
	 * 杂交
	 */
	public void crossOver() {
		double p = ConstantValue.GENETIC_CROSSOVER_PROBABILITY;
		double r = Math.random();
		// 以一定的概率杂交
		if (r < p) {
			// 随机选择一对父母，保证父母不是同一个个体
			int fatherIndex, motherIndex;
			do {
				fatherIndex = (int) (Math.random() * group.size());
				motherIndex = (int) (Math.random() * group.size());
			} while (fatherIndex == motherIndex);
			Species father = group.get(fatherIndex);
			Species mother = group.get(motherIndex);
			
			// 构造一个全是null的数组存放孩子的基因序列
			int size = father.size();
			ArrayList<City> list = new ArrayList<City>();
			for (int i = 0; i < size; ++i)
				list.add(null);
			
			// 随机一段父母的基因序列
			int beginIndex = (int) (Math.random() * size);
			int endIndex = (int) (Math.random() * (size + 1));
			// 保证beginIndex < endIndex
			if (beginIndex > endIndex) {
				int tmp = beginIndex;
				beginIndex = endIndex;
				endIndex = tmp;
			}
			// 直接把这一段父亲的基因复制到孩子对应的部分
			for (int i = beginIndex; i < endIndex; ++i)
				list.set(i, father.get(i));

			// 孩子缺少的基因从母亲那里拿
			int i = 0;
			// 从头开始遍历母亲的基因
			for (int j = 0; j < size; ++j) {
				if (i == beginIndex)
					i = endIndex; // 跳过父亲那一段
				City c = mother.get(j); // 获取母亲的基因
				// 如果母亲的基因已经在孩子身上，就跳过，否则加入
				if (!list.contains(c)) { 
					list.set(i, c);
					i++;
				}
			}

			// 得到孩子的基因，以此新建一个孩子，加入到种群
			City[] childCities = new City[size];
			list.toArray(childCities);
			Species child = new Species(childCities);
			group.add(child);

		}

	}

	/**
	 * 变异
	 */
	public void mutate() {
		double p = ConstantValue.GENETIC_MUTATE_PROBABILITY;
		// 要留下原始的个体，因为下面的reverse不是immutable的，所以如果表现较好的个体变异就可能会丢失较好的基因
		LinkedList<Species> origin = new LinkedList<Species>();
		// 变异对每一个个体都有概率
		for (Species sp : group) {
			origin.add(new Species(sp));
			double r = Math.random();
			// 以一定的概率变异
			if (r < p) {
				// 随机选择一段基因，逆序
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

		// 为了防止表现较好的个体变异以后丢失好基因，所以现在把原来的基因全部补回来
		group.addAll(origin);

	}

	/**
	 * 找到最好的个体
	 * @return 表现最好的个体
	 */
	public Species getBest() {
		// 即优先级队列的队首元素
		PriorityQueue<Species> pq = new PriorityQueue<Species>();
		pq.addAll(group);
		return pq.peek();
	}

}

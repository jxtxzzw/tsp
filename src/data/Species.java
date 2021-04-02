package data;

import java.util.ArrayList;

public class Species extends Route implements Comparable<Species> {
	
	private double fitness; // 适应度

	private double selectedRate; // 被选中的概率，归一化处理

	/**
	 * 以给定的城市序列为方案直接新建一个个体
	 * @param cities 给定的城市数组
	 */
	public Species(City[] cities) {
		super(cities); // 交给父类Route
		updateFiness(); // 只需更新适应度

	}

	/**
	 * 复制一份一模一样的
	 * @param father 给定的父母个体
	 */
	public Species(Species father) {
		// 直接复制并更新适应度
		routeList = new ArrayList<City>();
		routeList.addAll(father.routeList);
		updateFiness();
	}

	/* (non-Javadoc)
	 * @see data.Route#shuffle()
	 */
	@Override
	public void shuffle() {
		super.shuffle();
		updateFiness(); // 随机打乱以后必须更新适应度
	}

	/**
	 * 计算并设置适应度
	 */
	private void calculateAndSetFitness() {
		updateLength(); // 计算适应度之前必须更新长度
		this.fitness = 1.0 / getRouteLength();
	}

	/**
	 * 更新适应度
	 */
	private void updateFiness() {
		calculateAndSetFitness();
	}

	/**
	 * 获得被选中的概率（适用于轮盘赌）
	 * @return 被选中的概率
	 */
	public double getSelectedRate() {
		return selectedRate;
	}

	/**
	 * 设置被选中的概率，归一化处理
	 * @param selectedRate 被选择的概率，归一化处理
	 */
	public void setSelectedRate(double selectedRate) {
		this.selectedRate = selectedRate;
	}

	/**
	 * 获得适应度
	 * @return 适应度
	 */
	public double getFitness() {
		updateFiness(); // 返回之前必须先更新（如果需要）
		return fitness;
	}

	/**
	 * 个体的序列长度，即城市数量
	 * @return 数量
	 */
	public int size() {
		return routeList.size();
	}

	/**
	 * 获得指定位置的城市
	 * @param index 指定位置
	 * @return 在指定位置的城市
	 */
	public City get(int index) {
		return routeList.get(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 按照路径长度为标准，长度越短，表现越好，排序时在前，优先级越高
	 */
	@Override
	public int compareTo(Species o) {
		if (getRouteLength() < o.getRouteLength()) {
			return -1;
		} else if (getRouteLength() == o.getRouteLength()) {
			return 0;
		} else {
			return 1;
		}

	}

	/**
	 * 逆转给定区间
	 * @param left 左端点（包含）
	 * @param right 右端点（包含）
	 */
	public void reverse(int left, int right) {
		// 逆转
		while (left < right) {
			City c = routeList.get(left);
			routeList.set(left, routeList.get(right));
			routeList.set(right, c);
			++left;
			--right;
		}
		/*
		 * 我在想这里做成Immutable的是不是更好，逆转以后return一个逆转了的个体，不影响自己
		 */

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Species [fitness=" + fitness + ", selectedRate=" + selectedRate + ", routeList=" + routeList
				+ ", getRouteLength()=" + getRouteLength() + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(selectedRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Species other = (Species) obj;
		if (Double.doubleToLongBits(fitness) != Double.doubleToLongBits(other.fitness))
			return false;
		if (Double.doubleToLongBits(selectedRate) != Double.doubleToLongBits(other.selectedRate))
			return false;
		return true;
	}

}

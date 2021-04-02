package data;

import java.util.ArrayList;

public class Species extends Route implements Comparable<Species> {
	
	private double fitness; // ��Ӧ��

	private double selectedRate; // ��ѡ�еĸ��ʣ���һ������

	/**
	 * �Ը����ĳ�������Ϊ����ֱ���½�һ������
	 * @param cities �����ĳ�������
	 */
	public Species(City[] cities) {
		super(cities); // ��������Route
		updateFiness(); // ֻ�������Ӧ��

	}

	/**
	 * ����һ��һģһ����
	 * @param father �����ĸ�ĸ����
	 */
	public Species(Species father) {
		// ֱ�Ӹ��Ʋ�������Ӧ��
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
		updateFiness(); // ��������Ժ���������Ӧ��
	}

	/**
	 * ���㲢������Ӧ��
	 */
	private void calculateAndSetFitness() {
		updateLength(); // ������Ӧ��֮ǰ������³���
		this.fitness = 1.0 / getRouteLength();
	}

	/**
	 * ������Ӧ��
	 */
	private void updateFiness() {
		calculateAndSetFitness();
	}

	/**
	 * ��ñ�ѡ�еĸ��ʣ����������̶ģ�
	 * @return ��ѡ�еĸ���
	 */
	public double getSelectedRate() {
		return selectedRate;
	}

	/**
	 * ���ñ�ѡ�еĸ��ʣ���һ������
	 * @param selectedRate ��ѡ��ĸ��ʣ���һ������
	 */
	public void setSelectedRate(double selectedRate) {
		this.selectedRate = selectedRate;
	}

	/**
	 * �����Ӧ��
	 * @return ��Ӧ��
	 */
	public double getFitness() {
		updateFiness(); // ����֮ǰ�����ȸ��£������Ҫ��
		return fitness;
	}

	/**
	 * ��������г��ȣ�����������
	 * @return ����
	 */
	public int size() {
		return routeList.size();
	}

	/**
	 * ���ָ��λ�õĳ���
	 * @param index ָ��λ��
	 * @return ��ָ��λ�õĳ���
	 */
	public City get(int index) {
		return routeList.get(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * ����·������Ϊ��׼������Խ�̣�����Խ�ã�����ʱ��ǰ�����ȼ�Խ��
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
	 * ��ת��������
	 * @param left ��˵㣨������
	 * @param right �Ҷ˵㣨������
	 */
	public void reverse(int left, int right) {
		// ��ת
		while (left < right) {
			City c = routeList.get(left);
			routeList.set(left, routeList.get(right));
			routeList.set(right, c);
			++left;
			--right;
		}
		/*
		 * ��������������Immutable���ǲ��Ǹ��ã���ת�Ժ�returnһ����ת�˵ĸ��壬��Ӱ���Լ�
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

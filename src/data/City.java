package data;

import java.awt.Point;

public class City extends Point {

	private static final long serialVersionUID = 1L;
	private int index; // ���б��

	/**
	 * �½�һ������
	 * @param index ���б��
	 * @param x ����
	 * @param y ����
	 */
	public City(int index, int x, int y) {
		super(x, y); // �����
		this.index = index;
	}

	/* (non-Javadoc)
	 * @see java.awt.Point#toString()
	 */
	@Override
	public String toString() {
		return "City [index=" + index + ", x=" + x + ", y=" + y + "]";
	}

	/* (non-Javadoc)
	 * @see java.awt.geom.Point2D#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + index;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.awt.Point#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (index != other.index)
			return false;
		return true;
	}

}

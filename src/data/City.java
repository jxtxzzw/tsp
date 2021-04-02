package data;

import java.awt.Point;

public class City extends Point {

	private static final long serialVersionUID = 1L;
	private int index; // 城市编号

	/**
	 * 新建一个城市
	 * @param index 城市编号
	 * @param x 坐标
	 * @param y 坐标
	 */
	public City(int index, int x, int y) {
		super(x, y); // 坐标对
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

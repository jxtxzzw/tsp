package data;
import java.util.ArrayList;
import java.util.Collections;

public class Route {

	protected ArrayList<City> routeList = new ArrayList<City>(); // 方案序列
	private double routeLength = 0; // 路径长度
	

	/**
	 * 默认构造器
	 */
	public Route() {
		
	}
	
	/**
	 * 复制一条一模一样的Route
	 * @param route 给定的Route
	 */
	public Route(Route route){
		routeList.addAll(route.routeList);
		routeLength = route.routeLength;
	}
	
	/**
	 * 根据给定的城市，初始化一个1,2,...,n的顺序序列
	 * @param cities 给定的城市数组
	 */
	public Route(City[] cities){
		routeList = new ArrayList<City>();
		for (int i=0;i<cities.length;++i){
			routeList.add(cities[i]);
		}
		updateLength(); // 计算长度
	}

	/**
	 * 随机打乱
	 */
	public void shuffle() {
		Collections.shuffle(routeList); // 利用Collection的接口直接随机打乱
		updateLength(); // 计算打乱后的长度
	}
	
	/**
	 * 重新计算长度
	 */
	protected void updateLength() {
		routeLength = calculateRouteLength(); 
	}

	/**
	 * 计算长度
	 * @return 长度
	 */
	private double calculateRouteLength() {
		double ret = 0;
		double x ;
		// 走到下一个城市
		for (int i=0;i<routeList.size()-1;++i){
			x = routeList.get(i).distance(routeList.get(i+1));
			ret += x;
		}
		// 回到出发点
		x = routeList.get(routeList.size()-1).distance(routeList.get(0));
		ret += x;
		return ret;
	}
	
	/**
	 * 获得长度
	 * @return 长度
	 */
	public double getRouteLength(){
		return routeLength;
	}
	
	/**
	 * 返回一个随机交换两个城市以后的邻域Route
	 * @return 随机交换两个城市以后的邻域Route
	 */
	public Route generateNeighbour(){
		Route neighbour = new Route(this); // 先复制一个一模一样的
		// 随机选两个不同的城市
		int r1,r2;
		do {
			r1 = (int) (Math.random() * neighbour.routeList.size());
			r2 = (int) (Math.random() * neighbour.routeList.size());
		} while (r1==r2);
		
		
		City c1 = neighbour.routeList.get(r1);
		City c2 = neighbour.routeList.get(r2);
		
		// 由于只换了两个城市，不是完全打乱了，所以不需要重新计算长度，只要计算附近影响的那几段就可以
		// 计算受影响的部分长度
		neighbour.routeLength -= neighbour.affectedLength(r1);
		neighbour.routeLength -= neighbour.affectedLength(r2);
		
		// 交换
		neighbour.routeList.set(r1, c2);
		neighbour.routeList.set(r2, c1);
		
		// 计算受影响的部分长度
		neighbour.routeLength += neighbour.affectedLength(r1);
		neighbour.routeLength += neighbour.affectedLength(r2);
		
		return neighbour;
			
	}

	/**
	 * 计算左右两端被影响的长度
	 * @param r 被影响到的城市
	 * @return 左右两端被影响的长度
	 */
	private double affectedLength(int r) {
		int ret = 0;
		// 对第1个和最后一个做特别判断
		if (r==0){
			ret += routeList.get(0).distance(routeList.get(1));
			ret += routeList.get(routeList.size()-1).distance(routeList.get(0));
		} else if (r==routeList.size()-1){
			ret += routeList.get(r-1).distance(routeList.get(r));
			ret += routeList.get(r).distance(routeList.get(0));
		} else {
			ret += routeList.get(r-1).distance(routeList.get(r));
			ret += routeList.get(r).distance(routeList.get(r+1));
		}
		return ret;
	}
	
	
	/**
	 * 输出
	 */
	public void print(){
		for (City c : routeList){
			System.out.print(c +" -> ");
		}
		System.out.println();
		updateLength();
		System.out.println("RouteLength = " + routeLength);
	}
	
	
	
	

}

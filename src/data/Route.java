package data;
import java.util.ArrayList;
import java.util.Collections;

public class Route {

	protected ArrayList<City> routeList = new ArrayList<City>(); // ��������
	private double routeLength = 0; // ·������
	

	/**
	 * Ĭ�Ϲ�����
	 */
	public Route() {
		
	}
	
	/**
	 * ����һ��һģһ����Route
	 * @param route ������Route
	 */
	public Route(Route route){
		routeList.addAll(route.routeList);
		routeLength = route.routeLength;
	}
	
	/**
	 * ���ݸ����ĳ��У���ʼ��һ��1,2,...,n��˳������
	 * @param cities �����ĳ�������
	 */
	public Route(City[] cities){
		routeList = new ArrayList<City>();
		for (int i=0;i<cities.length;++i){
			routeList.add(cities[i]);
		}
		updateLength(); // ���㳤��
	}

	/**
	 * �������
	 */
	public void shuffle() {
		Collections.shuffle(routeList); // ����Collection�Ľӿ�ֱ���������
		updateLength(); // ������Һ�ĳ���
	}
	
	/**
	 * ���¼��㳤��
	 */
	protected void updateLength() {
		routeLength = calculateRouteLength(); 
	}

	/**
	 * ���㳤��
	 * @return ����
	 */
	private double calculateRouteLength() {
		double ret = 0;
		double x ;
		// �ߵ���һ������
		for (int i=0;i<routeList.size()-1;++i){
			x = routeList.get(i).distance(routeList.get(i+1));
			ret += x;
		}
		// �ص�������
		x = routeList.get(routeList.size()-1).distance(routeList.get(0));
		ret += x;
		return ret;
	}
	
	/**
	 * ��ó���
	 * @return ����
	 */
	public double getRouteLength(){
		return routeLength;
	}
	
	/**
	 * ����һ������������������Ժ������Route
	 * @return ����������������Ժ������Route
	 */
	public Route generateNeighbour(){
		Route neighbour = new Route(this); // �ȸ���һ��һģһ����
		// ���ѡ������ͬ�ĳ���
		int r1,r2;
		do {
			r1 = (int) (Math.random() * neighbour.routeList.size());
			r2 = (int) (Math.random() * neighbour.routeList.size());
		} while (r1==r2);
		
		
		City c1 = neighbour.routeList.get(r1);
		City c2 = neighbour.routeList.get(r2);
		
		// ����ֻ�����������У�������ȫ�����ˣ����Բ���Ҫ���¼��㳤�ȣ�ֻҪ���㸽��Ӱ����Ǽ��ξͿ���
		// ������Ӱ��Ĳ��ֳ���
		neighbour.routeLength -= neighbour.affectedLength(r1);
		neighbour.routeLength -= neighbour.affectedLength(r2);
		
		// ����
		neighbour.routeList.set(r1, c2);
		neighbour.routeList.set(r2, c1);
		
		// ������Ӱ��Ĳ��ֳ���
		neighbour.routeLength += neighbour.affectedLength(r1);
		neighbour.routeLength += neighbour.affectedLength(r2);
		
		return neighbour;
			
	}

	/**
	 * �����������˱�Ӱ��ĳ���
	 * @param r ��Ӱ�쵽�ĳ���
	 * @return �������˱�Ӱ��ĳ���
	 */
	private double affectedLength(int r) {
		int ret = 0;
		// �Ե�1�������һ�����ر��ж�
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
	 * ���
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

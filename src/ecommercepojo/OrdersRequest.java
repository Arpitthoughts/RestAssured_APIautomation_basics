package ecommercepojo;

import java.util.List;

public class OrdersRequest {

	List<OrderDetail> orders;

	public List<OrderDetail> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetail> orders) {
		this.orders = orders;
	}
	
	
}

package org.jboss.wise.demo;

import static org.junit.Assert.*;
import generated.orderMgmtService.Customer;
import generated.orderMgmtService.OrderItem;
import generated.orderMgmtService.OrderMgmt;
import generated.orderMgmtService.OrderMgmtService;
import generated.orderMgmtService.OrderStatus;
import generated.orderMgmtService.OrderType;

import org.junit.Test;

public class WiseProjectATest {

	@Test
	public void test() {
		System.out.println("Creating client...");
		OrderMgmtService service = new OrderMgmtService();
		OrderMgmt port = service.getOrderMgmtBeanPort();
		
		System.out.println("Preparing parameters...");
		OrderType order = new OrderType();
		Customer customer = new Customer();
		customer.setCreditCardDetails("1234-4567-890-333");
		customer.setFirstName("Mario");
		customer.setLastName("Rossi");
		order.setCustomer(customer);
		OrderItem orderItem = new OrderItem();
		orderItem.setName("first-order");
		orderItem.setPrice(10.45);
		order.getItems().add(orderItem);
		order.setOrderNum(1234);
		
		System.out.println("Invoking...");
		OrderStatus orderStatus = port.prepareOrder(order);
		
		System.out.println("\nInvocation result:");
		System.out.println("order number   : " + orderStatus.getOrderNum());
		System.out.println("order status   : " + orderStatus.getStatus());
		System.out.println("order discount : " + orderStatus.getDiscount());
		
		assertEquals(1234, orderStatus.getOrderNum());
		assertEquals("Prepared", orderStatus.getStatus());
	}

}

package org.jboss.wise.demo;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.wise.core.client.InvocationResult;
import org.jboss.wise.core.client.WSDynamicClient;
import org.jboss.wise.core.client.WSMethod;
import org.jboss.wise.core.client.WebParameter;
import org.junit.Test;

public class WiseProjectB1Test extends AbstractTest {
	
	@Test
	public void testUsingReflection() throws Exception {
		System.out.println("Creating client...");
		WSDynamicClient client = getClient(WSDL_ADDRESS);
		WSMethod method = show(client).getWSMethod("OrderMgmtService", "OrderMgmtBeanPort", "prepareOrder");
		
		System.out.println("\nPreparing parameters...");
		WebParameter par = method.getWebParams().get("prepareOrder");
		Class<?> orderClass = (Class<?>)par.getType();
		Object order = orderClass.newInstance();
		orderClass.getMethod("setOrderNum", long.class).invoke(order, 1234);
		Class<?> customerClass = orderClass.getDeclaredField("customer").getType();
		Object customer = customerClass.newInstance();
		customerClass.getMethod("setCreditCardDetails", String.class).invoke(customer, "1234-4567-890-333");
		customerClass.getMethod("setFirstName", String.class).invoke(customer, "Mario");
		customerClass.getMethod("setLastName", String.class).invoke(customer, "Rossi");
		orderClass.getMethod("setCustomer", customerClass).invoke(order, customer);
		Class<?> orderItemClass = (Class<?>)((ParameterizedType)(orderClass.getDeclaredField("items").getGenericType())).getActualTypeArguments()[0];
		Object orderItem = orderItemClass.newInstance();
		orderItemClass.getMethod("setName", String.class).invoke(orderItem, "fist-order");
		orderItemClass.getMethod("setPrice", double.class).invoke(orderItem, 10.45);
		((List)(orderClass.getMethod("getItems").invoke(order))).add(orderItem);
		
		System.out.println("Invoking...");
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put(par.getName(), order);
		InvocationResult invRes = method.invoke(reqMap);
		
		System.out.println("\nInvocation result:");
		Class<?> resultClass = (Class<?>)invRes.getResult().get(WSMethod.TYPE_RESULT);
		Object result = invRes.getResult().get(WSMethod.RESULT);
		
		System.out.println("order number   : " + resultClass.getMethod("getOrderNum").invoke(result));
		System.out.println("order status   : " + resultClass.getMethod("getStatus").invoke(result));
		System.out.println("order discount : " + resultClass.getMethod("getDiscount").invoke(result));
		
		assertEquals(1234L, resultClass.getMethod("getOrderNum").invoke(result));
		assertEquals("Prepared", resultClass.getMethod("getStatus").invoke(result));
	}
	
}

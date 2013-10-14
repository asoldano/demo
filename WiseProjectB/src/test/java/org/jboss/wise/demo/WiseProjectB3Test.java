package org.jboss.wise.demo;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.Map;

import org.jboss.wise.core.client.InvocationResult;
import org.jboss.wise.core.client.WSDynamicClient;
import org.jboss.wise.core.client.WSMethod;
import org.jboss.wise.core.client.WebParameter;
import org.jboss.wise.tree.Element;
import org.jboss.wise.tree.ElementBuilder;
import org.jboss.wise.tree.ElementBuilderFactory;
import org.junit.Test;

public class WiseProjectB3Test extends AbstractTest {
	
	@Test
	public void testUsingTreeView() throws Exception {
		System.out.println("Creating client...");
		WSDynamicClient client = getClient(WSDL_ADDRESS);
		WSMethod method = client.getWSMethod("OrderMgmtService", "OrderMgmtBeanPort", "prepareOrder");
		
		System.out.println("\nPreparing parameters...");
		Map<String, ? extends WebParameter> pars = method.getWebParams();
		WebParameter par = pars.get("prepareOrder");
		ElementBuilder builder = ElementBuilderFactory.getElementBuilder().client(client).request(true).useDefautValuesForNullLeaves(true);
		Element order = builder.buildTree(par.getType(), par.getName(), null, true);
		order.getChildByName("orderNum").setValue("1234");
		order.getChildByName("customer").getChildByName("creditCardDetails").setValue("1234-4567-890-333");
		order.getChildByName("customer").getChildByName("firstName").setValue("Mario");
		order.getChildByName("customer").getChildByName("lastName").setValue("Rossi");
		Element orderItem = order.getChildByName("items").incrementChildren();
		orderItem.getChildByName("name").setValue("first-order");
		orderItem.getChildByName("price").setValue("10.45");
		
		System.out.println("Invoking...");
		Map<String, Object> args = new java.util.HashMap<String, Object>();
		args.put(order.getName(), order.toObject());
		InvocationResult invRes = method.invoke(args, null);
		
		System.out.println("\nInvocation result:");
		Class<?> resultClass = (Class<?>)invRes.getResult().get(WSMethod.TYPE_RESULT);
		Object result = invRes.getResult().get(WSMethod.RESULT);
		Element orderStatus = builder.request(false).useDefautValuesForNullLeaves(false).buildTree(resultClass, "orderStatus", result, true);
		
		for (Iterator<? extends Element> it = orderStatus.getChildren(); it.hasNext(); ) {
			Element el = it.next();
			System.out.println(el.getName() + " : " + el.getValue());
		}
		
		assertEquals("1234", orderStatus.getChildByName("orderNum").getValue());
		assertEquals("Prepared", orderStatus.getChildByName("status").getValue());
	}
	

}

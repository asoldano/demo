package org.jboss.wise.demo;

import static org.junit.Assert.assertEquals;

import org.jboss.wise.core.client.InvocationResult;
import org.jboss.wise.core.client.WSDynamicClient;
import org.jboss.wise.core.client.WSMethod;
import org.jboss.wise.core.mapper.SmooksMapper;
import org.junit.Test;

import demo.Cliente;
import demo.ElementoOrdine;
import demo.Ordine;
import demo.StatoOrdine;

public class WiseProjectB2Test extends AbstractTest {
	
	@Test
	public void testUsingSmooks() throws Exception {
		System.out.println("Creating client...");
		WSDynamicClient client = getClient(WSDL_ADDRESS);
		WSMethod method = client.getWSMethod("OrderMgmtService", "OrderMgmtBeanPort", "prepareOrder");
		
		System.out.println("\nPreparing parameters...");
		Ordine ordine = new Ordine();
		Cliente cliente = new Cliente();
		cliente.setNome("Mario");
		cliente.setCognome("Rossi");
		cliente.setDettagliCartaCredito("1234-4567-890-333");
		ordine.setCliente(cliente);
		ordine.setNumeroOrdine(1234);
		ElementoOrdine[] elems = new ElementoOrdine[1];
		elems[0] = new ElementoOrdine();
		elems[0].setNome("first-order");
		elems[0].setPrezzo(10.45);
		ordine.setElementiOrdine(elems);
		
		System.out.println("Invoking...");
		InvocationResult invRes = method.invoke(ordine, new SmooksMapper("smooks-request.xml", client));
		StatoOrdine statoOrdine = (StatoOrdine)invRes.getMappedResult(new SmooksMapper("smooks-response.xml", client)).get("StatoOrdine");
		
		System.out.println("\nInvocation result:");
		System.out.println("order number   : " + statoOrdine.getNumeroOrdine());
		System.out.println("order status   : " + statoOrdine.getStato());
		System.out.println("order discount : " + statoOrdine.getSconto());
		
		assertEquals(1234, statoOrdine.getNumeroOrdine());
		assertEquals("Prepared", statoOrdine.getStato());
	}
	

}

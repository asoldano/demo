package org.jboss.wise.demo;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.wise.core.client.WSDynamicClient;
import org.jboss.wise.core.client.WSEndpoint;
import org.jboss.wise.core.client.WSMethod;
import org.jboss.wise.core.client.WSService;
import org.jboss.wise.core.client.WebParameter;
import org.jboss.wise.core.client.factories.WSDynamicClientFactory;

public abstract class AbstractTest {
	
	protected static final String WSDL_ADDRESS = "http://localhost:8080/jaxws-samples-retail/OrderMgmtService/OrderMgmtBean?wsdl";
	
	protected WSDynamicClient getClient(String address) throws Exception {
		return WSDynamicClientFactory.getJAXWSClientBuilder().keepSource(true).wsdlURL(address).build();
	}

	protected WSDynamicClient show(WSDynamicClient client) {
		for (Entry<String, WSService> serviceEntry : client.processServices().entrySet()) {
			System.out.println("\nService \"" + serviceEntry.getKey() + "\":");
			for (Entry<String, WSEndpoint> endpointEntry : serviceEntry.getValue().processEndpoints().entrySet()) {
				System.out.println("  Endpoint \"" + endpointEntry.getKey() + "\":");
				for (Entry<String, WSMethod> methodEntry : endpointEntry.getValue().getWSMethods().entrySet()) {
					WSMethod method = methodEntry.getValue();
					StringBuilder sb = new StringBuilder();
					Map<String, ? extends WebParameter> pars = method.getWebParams();
					Iterator<String> it = pars.keySet().iterator();
					while (it.hasNext()) {
						String k = it.next();
						sb.append(((Class<?>)pars.get(k).getType()).getName()).append(" ").append(k);
						if (it.hasNext()) {
							sb.append(", ");
						}
					}
					System.out.println("    * " + methodEntry.getKey() + "(" + sb.toString() + ")");
				}
			}
		}
		return client;
	}

}

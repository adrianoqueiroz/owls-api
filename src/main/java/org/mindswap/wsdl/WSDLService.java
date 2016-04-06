// The MIT License
//
// Copyright (c) 2004 Evren Sirin
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to
// deal in the Software without restriction, including without limitation the
// rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
// sell copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

package org.mindswap.wsdl;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.wsdl.Binding;
import javax.wsdl.Message;
import javax.wsdl.Operation;
import javax.wsdl.Port;
import javax.wsdl.Service;
import javax.wsdl.extensions.soap.SOAPAddress;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.wsdl.gen.Parser;
import org.apache.axis.wsdl.symbolTable.BindingEntry;
import org.apache.axis.wsdl.symbolTable.Parameter;
import org.apache.axis.wsdl.symbolTable.Parameters;
import org.apache.axis.wsdl.symbolTable.ServiceEntry;
import org.apache.axis.wsdl.symbolTable.SymTabEntry;
import org.apache.axis.wsdl.symbolTable.SymbolTable;
import org.mindswap.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

/**
 * Represents a WSDL service
 *
 * @author unascribed
 * @version $Rev: 2269 $; $Author: thorsten $; $Date: 2009-08-19 18:21:09 +0300 (Wed, 19 Aug 2009) $
 */
public class WSDLService
{
	private static final Logger logger = LoggerFactory.getLogger(WSDLService.class);
	//private boolean USE_TCPMON = false;

	private Parser wsdlParser = null;
	private URI uri = null;

	private final Map<String, WSDLOperation> operations = new HashMap<String, WSDLOperation>();

	public WSDLService(final URI wsdlURL) throws Exception {
		uri = wsdlURL;

		// Start by reading in the WSDL using Parser
		wsdlParser = new Parser();
		logger.debug("Reading WSDL document from {}", wsdlURL);
		wsdlParser.run(wsdlURL.toString());

		readOperations();
	}

	public static WSDLService createService(final String wsdlLoc) throws Exception {
		return createService(URI.create(wsdlLoc));
	}

	public static WSDLService createService(final URI wsdlLoc) throws Exception {
		WSDLService s = new WSDLService(wsdlLoc);
		return s;
	}

	private String createURI(final QName qname) {
		return qname.getNamespaceURI() + "#" + qname.getLocalPart();
	}

	private String createURI(final String localName) {
		return uri + "#" + localName;
	}

	private void readOperations() {
		try {
			String serviceNS = null;
			String serviceName = null;
			String operationName = null;
			String portName = null;

			Service service = selectService(serviceNS, serviceName);
			org.apache.axis.client.Service dpf =
				new org.apache.axis.client.Service(wsdlParser, service.getQName());

			Port port = selectPort(service.getPorts(), portName);
			if (portName == null) {
				portName = port.getName();
			}
			Binding binding = port.getBinding();

			SymbolTable symbolTable = wsdlParser.getSymbolTable();
			BindingEntry bEntry = symbolTable.getBindingEntry(binding.getQName());
			Parameters parameters = null;

			Iterator<Operation> i = bEntry.getParameters().keySet().iterator();
			while (i.hasNext())
			{
				Operation o = i.next();
				operationName = o.getName();

				Call call = (Call) dpf.createCall(QName.valueOf(portName), QName.valueOf(operationName));
				WSDLOperation op = new WSDLOperation(call);
				op.setService(this);

				operations.put(operationName, op);

				Message inputMessage = o.getInput().getMessage();
				Message outputMessage = o.getOutput().getMessage();

				op.setOperationName(createURI(operationName));
				op.setInputMessageName(createURI(inputMessage.getQName()));
				op.setOutputMessageName(createURI(outputMessage.getQName()));
				op.setPortName(createURI(port.getName()));

				if (logger.isDebugEnabled())
				{
					StringBuilder sb = new StringBuilder();
					sb.append("Operation : ").append(operationName).append(Utils.LINE_SEPARATOR);
					sb.append("Port      : ").append(portName).append(" -> ").append(
						op.getPortName()).append(Utils.LINE_SEPARATOR);
					sb.append("Input Msg : ").append(inputMessage.getQName()).append(
						" -> ").append(op.getInputMessageName()).append(Utils.LINE_SEPARATOR);
					sb.append("Output Msg: ").append(outputMessage.getQName()).append(
						" -> ").append(op.getOutputMessageName());
					logger.debug(sb.toString());
				}


				if(o.getDocumentationElement() != null) {
					Node doc = o.getDocumentationElement().getFirstChild();
					if(doc != null) op.setDocumentation(doc.getNodeValue());
				}

				parameters = (Parameters) bEntry.getParameters().get(o);

				// loop over parameters and set up in/out params
				for (int j = 0; j < parameters.list.size(); ++j) {
					Parameter p = (Parameter) parameters.list.get(j);
					String name = createURI(p.getName());
					QName type = p.getType().getQName();

					if (p.getMode() == Parameter.IN) {           // IN
						op.addInput(name, type);

						logger.debug(" Input     : {} {}", name, type);
					} else if (p.getMode() == Parameter.OUT) {    // OUT
						op.addOutput(name, type);

						logger.debug(" Output    : {} {}", name, type);
					} else if (p.getMode() == Parameter.INOUT) {    // INOUT
						op.addInput(name, type);
						op.addOutput(name, type);
						logger.warn("A WSDL parameter is defined as InOut is not tested yet.");
						logger.debug(" InOut     : {} {}", name, type);
					}

				}

				// set output type
				if (parameters.returnParam != null) {
					Parameter p = parameters.returnParam;
					String name = createURI(p.getName());
					QName type = p.getType().getQName();

					op.addOutput(name, type);

					logger.debug(" Return    : {} {}", name, type);
				}

				if (logger.isDebugEnabled())
				{
					StringBuilder sb = new StringBuilder();
					sb.append("Inputs    : ").append(op.getInputs().size()).append(Utils.LINE_SEPARATOR);
					sb.append("Outputs   : ").append(op.getOutputs().size()).append(Utils.LINE_SEPARATOR);
					sb.append("Document  : ").append(op.getDocumentation());
					logger.debug(sb.toString());
				}
			}

		} catch(Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	private Service selectService(final String serviceNS, final String serviceName) throws Exception
	{
		QName serviceQName = (serviceNS != null && serviceName != null)? new QName(serviceNS, serviceName) : null;
		ServiceEntry serviceEntry = (ServiceEntry) getSymTabEntry(serviceQName, ServiceEntry.class);
		return serviceEntry.getService();
	}

	private SymTabEntry getSymTabEntry(final QName qname, final Class<ServiceEntry> cls)
	{
		HashMap<QName, Vector<SymTabEntry>> map = wsdlParser.getSymbolTable().getHashMap();

		for (Entry<QName, Vector<SymTabEntry>> entry : map.entrySet())
		{
			Vector<SymTabEntry> v = entry.getValue();
			if (qname == null || qname.equals(entry.getKey()))
			{
				for (SymTabEntry symTabEntry : v)
				{
					if (cls.isInstance(symTabEntry))
					{
						return symTabEntry;
					}
				}
			}
		}
		return null;
	}

	private Port selectPort(final Map<String, Port> ports, final String portName) throws Exception {
		for (Entry<String, Port> entry : ports.entrySet())
		{
			String name = entry.getKey();
			if (portName == null || portName.length() == 0)
			{
				Port port = entry.getValue();
				List<Object> list = port.getExtensibilityElements();

				for (int i = 0; (list != null) && (i < list.size()); i++) {
					Object obj = list.get(i);
					if (obj instanceof SOAPAddress) {
						return port;
					}
				}
			} else if ((name != null) && name.equals(portName)) {
				return entry.getValue();
			}
		}
		return null;
	}

	public URI getFileURI() {
		return uri;
	}

	public List<WSDLOperation> getOperations() {
		return new ArrayList<WSDLOperation>(operations.values());
	}

	public WSDLOperation getOperation(final String opName) {
		return operations.get(opName);
	}
}

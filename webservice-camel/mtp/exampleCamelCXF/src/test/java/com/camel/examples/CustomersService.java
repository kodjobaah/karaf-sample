package com.camel.examples;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
import javax.xml.ws.RequestWrapper;

/**
 * @author Ramiro Pugh
 * @email ramiro.pugh@fluxit.com.ar
 * 
 */
@WebService(name = "CustomersService")
@SOAPBinding(style = Style.DOCUMENT)
public interface CustomersService
{

	@WebMethod(operationName = "getCustomerByCuit")
	@RequestWrapper(localName = "filter")
	@WebResult(name = "customer")
	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED, style = Style.DOCUMENT, use = Use.LITERAL)
	public Customer getCustomerByCuit(@WebParam(name = "cuit") String cuit);

}

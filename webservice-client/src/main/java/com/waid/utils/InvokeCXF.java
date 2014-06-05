package com.waid.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import java.beans.IndexedPropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: whatamidoing
 * Date: 03/06/14
 * Time: 00:26
 * To change this template use File | Settings | File Templates.
 */
public class InvokeCXF {

    public Map sendFetchVideo(String videoId, String name) throws Exception {

         ClassLoader classloader =  Thread.currentThread().getContextClassLoader();
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client cl = dcf.createClient("http://192.168.1.5:8181/video/video-info/webservices/video?wsdl",classloader);
        ClientImpl client = (ClientImpl)cl;
        classloader = Thread.currentThread().getContextClassLoader();
        Object videoInput  =   classloader.loadClass("com.waid.webservice.InputFetchVideo").newInstance();

       /*
        val bindingName: QName = new QName("http://webservice.waid.com/", "FetchVideoBinding")
        val boi: BindingOperationInfo  = new BindingOperationInfo(bindingName)
        val inputMessageInfo:  BindingMessageInfo = boi.getInput()
        val parts: util.List[MessagePartInfo] = inputMessageInfo.getMessageParts()
        val partInfo: MessagePartInfo  = parts.get(0)
        val partClass = partInfo.getTypeClass()
          println(m.getName)
        }
        */
        Method m = videoInput.getClass().getMethod("setVideoId", String.class);
        m.invoke(videoInput, videoId);


        for(Method method: videoInput.getClass().getMethods()) {
            System.out.println("hey:"+method.getName());
        }
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        List<String> names = (List<String>) propertyUtilsBean.getProperty(videoInput,"name");
        names.add(name);
        Object[] res = client.invoke("FetchVideo",videoInput);



        Map map = new HashMap<String,String>();
        for(Object r: res) {
             Method getName = r.getClass().getMethod("getName");
             Object n = getName.invoke(r);
             map.put("name",n);
         }
        return map;
   }
}

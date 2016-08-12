package org.osgidemo.uservalidatorwebbundle;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgidemo.uservalidatorwebbundle.web.LoginServlet;

public class Activator implements BundleActivator,ServiceListener {

	private static BundleContext context;
	private ServiceReference ref;
    private Servlet servlet;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		servlet=new LoginServlet(context);
        registerServlet();
        context.addServiceListener(this, "(objectClass=" + HttpService.class.getName() + ")");
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		/*
		try {
			System.out.println("bundle stop unregisterServlet");
            unregisterServlet();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        */

        servlet = null;
		Activator.context = null;
		ref = null;
	}

	public void serviceChanged(ServiceEvent event) {
        switch (event.getType()){
            case ServiceEvent.REGISTERED:
                registerServlet();
                break;

            case ServiceEvent.UNREGISTERING:
            	System.out.println("ServiceEvent.UNREGISTERING unregisterServlet");
                unregisterServlet();
                break;
        }
    }
    
    private void registerServlet() {
        if (ref == null)
            ref = context.getServiceReference(HttpService.class.getName());
     
        if (ref != null) {
            try {
                HttpService http = (HttpService) context.getService(ref);
                http.registerServlet("/demo/login", servlet, null, null);
                http.registerResources("/demo/page","page",null);
                System.out.println("已启动用户登录验证web模块，请通过/demo/page/login.htm访问");
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void unregisterServlet() {
        if (ref != null) {
            try {
                HttpService http = (HttpService) context.getService(ref);
                http.unregister("/demo/login");
                http.unregister("/demo/page");
                System.out.println("已卸载用户登录验证web模块！");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

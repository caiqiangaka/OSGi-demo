package org.qiang.udpipccommunicationimplbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.qiang.ipccommunicationbundle.service.user.IpcCommunication;
import org.qiang.udpipccommunicationimplbundle.impl.UdpIpcCommunicationImpl;


public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration serviceReg = null;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Udp ipc communication 服务被注册");
        
        serviceReg = bundleContext.registerService( IpcCommunication.class.getName(), new UdpIpcCommunicationImpl(), null );
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		
		if(serviceReg != null)
            serviceReg.unregister();
        
        System.out.println("Udp ipc communication 服务被卸载");
	}

}

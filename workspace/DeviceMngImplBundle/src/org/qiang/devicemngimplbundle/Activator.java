package org.qiang.devicemngimplbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.qiang.devicemngbundle.service.user.DeviceMng;
import org.qiang.devicemngimplbundle.impl.DeviceMngImpl;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration serviceReg = null;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		System.out.println("Device Manage 服务被注册");
        
        serviceReg = bundleContext.registerService( DeviceMng.class.getName(), new DeviceMngImpl(), null );
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		
		if(serviceReg != null)
            serviceReg.unregister();
        
        System.out.println("Device Manage 服务被卸载");
	}

}

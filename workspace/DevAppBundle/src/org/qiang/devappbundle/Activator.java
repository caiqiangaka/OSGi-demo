package org.qiang.devappbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import org.qiang.devicemngbundle.service.user.DeviceMng;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static DeviceMng devMng;
	private static DeviceManageUI devMngUI;

	public static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		Activator.context = bundleContext;
		ServiceReference service_ref;
		
		System.out.println("devappbundle activator start ...");
		
		service_ref = context.getServiceReference( DeviceMng.class.getName() );
		devMng = (DeviceMng)context.getService( service_ref );
		if ( null == devMng ) {
			System.err.println( "No usable DeviceMng service" );
            return;
		}
		
		devMngUI = new DeviceManageUI( devMng );
		devMngUI.init();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("devappbundle activator stop ...");
		
		if ( null != devMng ) {
			devMngUI.close();
			devMng.close();
		}
		
		Activator.context = null;
	}

}

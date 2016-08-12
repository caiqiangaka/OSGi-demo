package org.qiang.numcmdipcmsgprotolibimplbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.qiang.ipcmsgprotolibbundle.service.user.IpcMsgProtoLib;
import org.qiang.numcmdipcmsgprotolibimplbundle.impl.NumCmdIpcMsgProtoLibImpl;

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
		
		System.out.println("Number Command Ipc Msg Protocol lib ·þÎñ±»×¢²á");
        
        serviceReg = bundleContext.registerService( IpcMsgProtoLib.class.getName(), new NumCmdIpcMsgProtoLibImpl(), null );
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}

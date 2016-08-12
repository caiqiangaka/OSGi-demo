package org.qiang.devappbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private static Login login;

	public static BundleContext getContext() {
		return context;
	}
	
	public static Login getLogin() {
		return login;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		
		System.out.println("devappbundle activator start ...");
		
		Activator.context = bundleContext;
		
		login = new Login( bundleContext );
		login.init();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		System.out.println("devappbundle activator stop ...");
		
		login.close();
		Activator.context = null;
	}

}

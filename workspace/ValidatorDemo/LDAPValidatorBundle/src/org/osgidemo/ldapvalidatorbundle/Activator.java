package org.osgidemo.ldapvalidatorbundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgidemo.ldapvalidatorbundle.impl.LDAPValidatorImpl;
import org.osgidemo.uservalidatorbundle.service.user.Validator;

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
		
		System.out.println("LDAPValidator服务被注册");
        
        serviceReg = bundleContext.registerService(Validator.class.getName(), new LDAPValidatorImpl(), null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		
		if(serviceReg != null)
            serviceReg.unregister();
        
        System.out.println("LDAPValidator服务被卸载");
	}

}

package org.osgidemo.configfilevalidatorbundle.impl;

import org.osgidemo.uservalidatorbundle.service.user.*;

public class ConfigfileValidatorImpl implements Validator {
	public boolean validate( String username, String password ) throws Exception {
		System.out.println( "Configfile Validator" );
		
		if ( ( "jerry".equals( username ) ) && ( "bluedavy".equals( password ) ) )
			return true;
		
		return false;
	}
}
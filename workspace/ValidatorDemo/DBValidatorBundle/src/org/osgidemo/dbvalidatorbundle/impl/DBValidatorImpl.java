package org.osgidemo.dbvalidatorbundle.impl;

import org.osgidemo.uservalidatorbundle.service.user.*;

public class DBValidatorImpl implements Validator {
	public boolean validate( String username, String password ) throws Exception {
		System.out.println( "DB Validator" );
		
		if ( ( "jerry".equals( username ) ) && ( "bluedavy".equals( password ) ) )
			return true;
		
		return false;
	}
}
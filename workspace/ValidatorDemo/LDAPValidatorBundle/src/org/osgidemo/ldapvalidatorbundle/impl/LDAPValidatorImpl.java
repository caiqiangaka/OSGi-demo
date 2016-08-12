package org.osgidemo.ldapvalidatorbundle.impl;

import org.osgidemo.uservalidatorbundle.service.user.*;

public class LDAPValidatorImpl implements Validator {
	public boolean validate( String username, String password ) throws Exception {
		System.out.println( "LDAP Validator" );
		
		if ( ( "jerry".equals( username ) ) && ( "bluedavy".equals( password ) ) )
			return true;
		
		return false;
	}
}
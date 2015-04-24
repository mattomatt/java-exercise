package com.bosejam.auth

class Role {
    String ROLE_UTENTE='ROLE_USER'
    String ROLE_TRANSLATOR='ROLE_TRADUTTORE'
    String ROLE_PRODUCER='ROLE_PRODUCER'
    String ROLE_FACEBOOK='ROLE_FACEBOOK'

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}

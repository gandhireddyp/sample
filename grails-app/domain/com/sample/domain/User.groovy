package com.sample.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


/**
 * User class associated with Spring Security
 */
@EqualsAndHashCode(includes=['username', "fullName", "email"])
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String fullName
	String username
	String password
    String email
    static hasMany = [orders: Order]


	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired


    static fetchMode = [orders: 'lazy']

	User(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		fullName blank: false
		username blank: false, unique: true
		password blank: false
		email blank: false, email: true
	}

	static mapping = {
		password column: '`password`'
	}
}

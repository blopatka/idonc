package org.lopatka.idonc.web.utils;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.wicket.security.hive.authentication.Subject;
import org.apache.wicket.security.hive.authorization.Principal;

public class IdoncPrincipal implements Principal {

	private static final long serialVersionUID = 8591120946806290654L;
	private String name;

	public IdoncPrincipal(String name) {
		super();
		this.name = name;
		if (name == null) {
			throw new IllegalArgumentException("username must be specified");
		}
	}

	public String getName() {
		return name;
	}

	public boolean implies(Subject subject) {

		return false;
	}

	@Override
	public String toString() {
		return getClass().getName() + ":" + getName();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(61,15)
		.appendSuper(super.hashCode())
		.append(name)
		.toHashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IdoncPrincipal)) {
			return false;
		}
		IdoncPrincipal rhs = (IdoncPrincipal) obj;
		return new EqualsBuilder()
		.appendSuper(super.equals(obj))
		.append(name, rhs.getName())
		.isEquals();
	}
}

package com_spring_core8_$_customvalidate;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * @author thanh.nv
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE,
		PARAMETER, TYPE })
@Constraint(validatedBy = CustomConstraintValidator.class)
public @interface ValidConstraint {

	String message() default "{}";

	boolean stepBystep() default true;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}

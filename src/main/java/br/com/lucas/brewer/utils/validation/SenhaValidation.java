package br.com.lucas.brewer.utils.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 * Validação para garantir que a senha contenha 8 caracteres sendo eles pelo menos 1 letra minuscula, 1 letra maiuscula, 1 numero e 1 caracter especial.
 */

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "^(?:(?:(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]))|(?:(?=.*[a-z])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\\\]))|(?:(?=.*[0-9])(?=.*[A-Z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\\\]))|(?:(?=.*[0-9])(?=.*[a-z])(?=.*[*.!@$%^&(){}[]:;<>,.?/~_+-=|\\\\]))).{8,32}$\")\r\n")
public @interface SenhaValidation {
	
	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "A senha deve conter 8 caracteres com pelo menos 1 letra minuscula, 1 letra maiuscula, 1 numero e 1 caracter especial.";
	
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

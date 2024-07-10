package com.food.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ValorZeroIncluirDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {

	String message() default " descricao obrigatoria invalida!!";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String valorField();

	String descricaoField();

	String descricaoObrigatoria();

}

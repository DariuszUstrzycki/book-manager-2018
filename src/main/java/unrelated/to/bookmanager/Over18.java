package unrelated.to.bookmanager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = Over18Validator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Over18 {
	
	String message() default "{isNotOldEnough.error.message}"; 

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

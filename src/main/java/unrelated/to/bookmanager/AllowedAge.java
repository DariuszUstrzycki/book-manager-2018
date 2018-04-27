package unrelated.to.bookmanager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AllowedAgeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AllowedAge {
	
	
	int	value();
	
	String message() default "There'a an age limit are allowed";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

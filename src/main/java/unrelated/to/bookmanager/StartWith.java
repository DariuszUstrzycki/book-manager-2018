package unrelated.to.bookmanager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = StartWithValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StartWith {
		
	
	String	version(); // >>> @StartWith(version="1.0")
	
	String	value(); // >>> @StartWith("annotValue")
	// Szczególny	przypadek	stanowi	adnotacja, 	która	ma	jeden	parametr,	możemy	wtedy 	pominąć	jego	nazwę,	
	//a	przyjmuje	on domyślnie	nazwę	value.
	
	

	String message() default "It must start with A";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

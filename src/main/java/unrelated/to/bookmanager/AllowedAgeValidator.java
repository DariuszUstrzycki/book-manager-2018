package unrelated.to.bookmanager;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AllowedAgeValidator implements ConstraintValidator<AllowedAge, Integer> {
	
	int allowedAge;
	
	@Override
	public void initialize(AllowedAge constraintAnnotation) {
		this.allowedAge = constraintAnnotation.value();
	}

	@Override // Pierwszy	jej	argument	jest	wartością	do 	walidacji. Drugi	parametr	może	posłużyć	do	dodania
	//dodatkowego	komunikatu	błędu	oraz 	wyłączenia	domyślnego	komunikatu.
	// https://docs.jboss.org/hibernate/validator/4.3/reference/en-US/html/validator-customconstraints.html#example-constraint-validator	
	
	public boolean isValid(Integer inputYearOfBirth, ConstraintValidatorContext context) {
				
		int currentYear = LocalDate.now().getYear();
		int age = currentYear - inputYearOfBirth;
		
		System.out.println("----------------------currentYear: " + currentYear + ", currentYear: " + ", allowedAge: " + allowedAge);
		
		return	age >= allowedAge;
	}
	
	
	
}
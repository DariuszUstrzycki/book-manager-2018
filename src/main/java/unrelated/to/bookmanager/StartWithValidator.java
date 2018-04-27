package unrelated.to.bookmanager;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartWithValidator implements ConstraintValidator<StartWith, String> {
	// Interfejs	ConstraintValidator 	posiada	metodę	isValid,	która	jest	wywoływana	przy	wywołaniu	metody
	//validate	np.	podczas	walidacji	w	kontrolerze  Set<ConstraintViolation<Person>>	violations	=	validator.validate(p2);
	// Metoda	isValid	zostanie	również	wywołana	niejawnie	w	momencie	bindowania	danych	z formularza.
				
	
	private	String	stringBeginning;
	
	
	@Override  
	public void initialize(StartWith constraintAnnotation) {
		this.stringBeginning = constraintAnnotation.value();
	}	
	/*
		Wykorzystujemy	metodę	initialize	do	pobrania	parametru	z	adnotacji	oraz
		modyfikujemy	metodę	isValid	by	z	niego	skorzystała.
	*/
	

	@Override // Pierwszy	jej	argument	jest	wartością	do 	walidacji. Drugi	parametr	może	posłużyć	do	dodania
	//dodatkowego	komunikatu	błędu	oraz 	wyłączenia	domyślnego	komunikatu.
	// https://docs.jboss.org/hibernate/validator/4.3/reference/en-US/html/validator-customconstraints.html#example-constraint-validator	
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return	value.startsWith(stringBeginning);
	}
	
	
	
}
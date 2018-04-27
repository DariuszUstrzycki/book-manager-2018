package unrelated.to.bookmanager;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Over18Validator implements ConstraintValidator<Over18, Integer>{

	@Override
	public void initialize(Over18 constraintAnnotation) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValid(Integer birthYear, ConstraintValidatorContext context) {
		return LocalDate.now().getYear() - birthYear >= 18;
	}

}

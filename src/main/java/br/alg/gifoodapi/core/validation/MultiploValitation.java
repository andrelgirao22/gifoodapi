package br.alg.gifoodapi.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiploValitation implements ConstraintValidator<Multiplo, Number> {

	
	public int numeroMultiplo;

	@Override
	public void initialize(Multiplo constraintAnnotation) {
		this.numeroMultiplo = constraintAnnotation.numero();
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) {
		
		boolean valido = true;
		
		if(value != null) {
			
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			
			var resto = valorDecimal.remainder(multiploDecimal);

			valido = BigDecimal.ZERO.compareTo(resto) == 0;
		}
		
		
		return valido;
	}

	
	
}

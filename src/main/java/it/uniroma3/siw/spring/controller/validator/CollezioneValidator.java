package it.uniroma3.siw.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.model.Quadro;
import it.uniroma3.siw.spring.service.CollezioneService;

@Component 
public class CollezioneValidator implements Validator {
	
	@Autowired
	private CollezioneService collezioneService;
	
	private static final Logger logger = LoggerFactory.getLogger(QuadroValidator.class);

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "curatore", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "stile", "required");

		if (!errors.hasErrors()) {
			logger.debug("confermato: valori non nulli");
			if (this.collezioneService.alreadyExists((Collezione)o)) {
				logger.debug("e' un duplicato");
				errors.reject("collezione.duplicato");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Quadro.class.equals(aClass);
	}

}

package it.uniroma3.siw.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.controller.validator.CollezioneValidator;
import it.uniroma3.siw.spring.model.Collezione;
import it.uniroma3.siw.spring.service.CollezioneService;
import it.uniroma3.siw.spring.service.CuratoreService;
import it.uniroma3.siw.spring.service.QuadroService;


@Controller
public class CollezioneController {
	
	@Autowired
	private CollezioneService collezioneService;
	
    @Autowired
    private CollezioneValidator collezioneValidator;

    @Autowired
	private CuratoreService curatoreService;
    
    @Autowired
    private QuadroService quadroService;
    
    /**
     * azione dell'amministratore
     * Crea un nuovo oggetto collezione e reindirizza alla pagina collezioneForm
     * @param model
     * @return string
     */
    @RequestMapping(value="/admin/collezione", method = RequestMethod.GET)
    public String addCollezione(Model model) {
    	model.addAttribute("collezione", new Collezione());
    	model.addAttribute("curatori", this.curatoreService.tuttiOrdinati());
        return "collezioneForm";
    }

    /**
     * un utente vuole visualizzare la pagina di una specifica collezione
     * @param id
     * @param model
     * @return string
     */
    @RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
    public String getCollezione(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("collezione", this.collezioneService.collezionePerId(id));
    	model.addAttribute("quadri", this.quadroService.perCollezione(this.collezioneService.collezionePerId(id)));
    	return "collezione";
    }

    /**
     * un utente vuole visualizzare la lista di tutte le collezioni salvate
     * @param model
     * @return string
     */
    @RequestMapping(value = "/collezioni", method = RequestMethod.GET)
    public String getCollezioni(Model model) {
    		model.addAttribute("collezioni", this.collezioneService.tuttiOrdinati());
    		return "collezioni";
    }
    
    /**
     * un amministratore vuole cancellare una collezione salvata
     * @param id
     * @param model
     * @return string
     */
    @RequestMapping(value = "/admin/collezione/{id}", method = RequestMethod.GET)
    public String deleteCollezione(@PathVariable("id") Long id, Model model) {
    	this.collezioneService.cancella(id);
    	model.addAttribute("collezioni", this.collezioneService.tuttiOrdinati());
    	return "collezioni";
    }
    
    @RequestMapping(value = "/admin/collezione", method = RequestMethod.POST)
    public String addQuadro(@ModelAttribute("collezione") Collezione collezione, 
    									Model model, BindingResult bindingResult) {
    	this.collezioneValidator.validate(collezione, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	this.collezioneService.inserisci(collezione);
        	
            model.addAttribute("collezioni", this.collezioneService.tuttiOrdinati());
            return "collezioni";
        }
        model.addAttribute("curatori", this.curatoreService.tuttiOrdinati());
        return "collezioneForm";
    }


}

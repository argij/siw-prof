package it.uniroma3.siw.spring.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import it.uniroma3.siw.spring.controller.validator.ArtistaValidator;
import it.uniroma3.siw.spring.misc.FileUploadUtil;
import it.uniroma3.siw.spring.model.Artista;
import it.uniroma3.siw.spring.model.Quadro;
import it.uniroma3.siw.spring.service.ArtistaService;
import it.uniroma3.siw.spring.service.QuadroService;


@Controller
@RequestMapping
public class ArtistaController {
	
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private ArtistaValidator artistaValidator;
	
	@Autowired
	private QuadroService quadroService;
	
	 @RequestMapping(value="/admin/artista", method = RequestMethod.GET)
	    public String addArtista(Model model) {
	    	model.addAttribute("artista", new Artista());
	        return "artistaForm";
	    }

    @RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
    public String getArtista(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("artista", this.artistaService.artistaPerId(id));
    	List<Quadro> quadri = this.quadroService.perArtista(this.artistaService.artistaPerId(id));
    	//voglio mostrare solo i primi sei quadri per evitare di intasare la pagina
    	if (quadri.size() < 6) {
    		model.addAttribute("quadri", quadri);
    	}
    	else {
    		model.addAttribute("quadri", quadri.subList(0, 5));
    	}
    	return "artista";
    }
    
    @RequestMapping(value = "/admin/artista/{id}", method = RequestMethod.GET)
    public String deleteArtista(@PathVariable("id") Long id, Model model) {
    	this.artistaService.cancella(id);
    	model.addAttribute("artisti", this.artistaService.tuttiOrdinati());
    	return "artisti";
    }

    @RequestMapping(value = "/artisti", method = RequestMethod.GET)
    public String getArtisti(Model model) {
    		model.addAttribute("artisti", this.artistaService.tuttiOrdinati());
    		return "artisti";
    }
    
    @RequestMapping(value = "/admin/artista", method = RequestMethod.POST)
    public String addArtista(@ModelAttribute("artista") Artista artista, 
    									Model model, BindingResult bindingResult, @RequestParam("image") MultipartFile multipartFile) throws IOException {
    	this.artistaValidator.validate(artista, bindingResult);
        if (!bindingResult.hasErrors()) {
        	
        	String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        	artista.setRitratto(fileName);
        	this.artistaService.inserisci(artista);
        	String uploadDir = "uploadable/artista-ritratto/" + artista.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);           
            model.addAttribute("artisti", this.artistaService.tuttiOrdinati());
            return "artisti";
        
        }
        
        return "artistaForm";
    
    }

}

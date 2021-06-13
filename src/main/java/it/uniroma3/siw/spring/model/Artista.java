package it.uniroma3.siw.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "artista")
@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @NoArgsConstructor
public class Artista {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@Column(nullable = false)
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataDiNascita;
	@Column(nullable = false)
	private String luogoDiNascita;
	@Column
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dataDiMorte;
	@Column
	private String luogoDiMorte;
	@Column
	private String nazionalita;
	
	@OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Quadro> quadri;
	
	@Column(nullable=true)
	private String ritratto;
	@Column(length=5096)
	private String biografia;

	 @Transient
	    public String getPhotosImagePath() {
	        if (ritratto.equals(null) || id.equals(null)) return null;
	         
	        return "/uploadable/artista-ritratto/" + id + "/" + ritratto;
	    }
}

package it.uniroma3.siw.spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;

@Entity
@Table(name = "opera")
@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @NoArgsConstructor
public class Quadro {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private String titolo;
	@Column
	private int anno;
	@Column(length=5096)
	private String descrizione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="collezione_id")
	private Collezione collezione;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "artista_id")
	private Artista artista;
	
	@Column(nullable=true)
	private String foto;
	
	 @Transient
	    public String getPhotosImagePath() {
	        if (foto.equals(null) || id.equals(null)) return null;
	         
	        return "/uploadable/quadri/" + id + "/" + foto;
	    }

}

package it.uniroma3.siw.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "collezione")
@Getter @Setter @AllArgsConstructor @EqualsAndHashCode @NoArgsConstructor
public class Collezione {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(nullable = false)
	private String nome;
	@Column(length=5096)
	private String descrizione;
	
	@ManyToOne
	@JoinColumn(name = "curatori_id")
	private Curatore curatore;
	
	@Column(nullable = true)
	private Stile stile;
	
	@OneToMany(mappedBy = "collezione", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Quadro> opere;
	
	
	public Collezione(String n, String d) {
		this.nome = n;
		this.descrizione = d;
	}
	

}

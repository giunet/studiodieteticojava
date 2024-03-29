package hibernate;

// Generated 5-feb-2010 10.52.14 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Tipologiadietaspeciale generated by hbm2java
 */
public class Tipologiadietaspeciale implements java.io.Serializable {

	private Integer idTipologiaDietaSpeciale;
	private String nome;
	private String descrizione;
	private Set malattias = new HashSet(0);

	public Tipologiadietaspeciale() {
	}

	public Tipologiadietaspeciale(String nome) {
		this.nome = nome;
	}

	public Tipologiadietaspeciale(String nome, String descrizione, Set malattias) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.malattias = malattias;
	}

	public Integer getIdTipologiaDietaSpeciale() {
		return this.idTipologiaDietaSpeciale;
	}

	public void setIdTipologiaDietaSpeciale(Integer idTipologiaDietaSpeciale) {
		this.idTipologiaDietaSpeciale = idTipologiaDietaSpeciale;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Set getMalattias() {
		return this.malattias;
	}

	public void setMalattias(Set malattias) {
		this.malattias = malattias;
	}

}

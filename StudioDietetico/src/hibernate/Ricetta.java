package hibernate;

// Generated 4-feb-2010 12.32.12 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Ricetta generated by hbm2java
 */
public class Ricetta implements java.io.Serializable {

	private Integer idRicetta;
	private Alimento alimento;
	private String nome;
	private String procedimento;
	private Set composiziones = new HashSet(0);

	public Ricetta() {
	}

	public Ricetta(String nome, String procedimento) {
		this.nome = nome;
		this.procedimento = procedimento;
	}

	public Ricetta(Alimento alimento, String nome, String procedimento,
			Set composiziones) {
		this.alimento = alimento;
		this.nome = nome;
		this.procedimento = procedimento;
		this.composiziones = composiziones;
	}

	public Integer getIdRicetta() {
		return this.idRicetta;
	}

	public void setIdRicetta(Integer idRicetta) {
		this.idRicetta = idRicetta;
	}

	public Alimento getAlimento() {
		return this.alimento;
	}

	public void setAlimento(Alimento alimento) {
		this.alimento = alimento;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProcedimento() {
		return this.procedimento;
	}

	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}

	public Set getComposiziones() {
		return this.composiziones;
	}

	public void setComposiziones(Set composiziones) {
		this.composiziones = composiziones;
	}

}
package hibernate;

// Generated 28-gen-2010 12.14.26 by Hibernate Tools 3.2.4.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import common.GenericBean;

/**
 * Turno generated by hbm2java
 */
public class Turno extends GenericBean implements java.io.Serializable {

	private Integer idTurno;
	private Date oraInizio;
	private Date oraFine;
	private String nome;
	private Set prestaziones = new HashSet(0);

	public Turno() {
	}

	public Turno(Date oraInizio, Date oraFine, String nome) {
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.nome = nome;
	}

	public Turno(Date oraInizio, Date oraFine, String nome, Set prestaziones) {
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.nome = nome;
		this.prestaziones = prestaziones;
	}

	public Integer getIdTurno() {
		return this.idTurno;
	}

	public void setIdTurno(Integer idTurno) {
		this.idTurno = idTurno;
	}

	public Date getOraInizio() {
		return this.oraInizio;
	}

	public void setOraInizio(Date oraInizio) {
		this.oraInizio = oraInizio;
	}

	public Date getOraFine() {
		return this.oraFine;
	}

	public void setOraFine(Date oraFine) {
		this.oraFine = oraFine;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set getPrestaziones() {
		return this.prestaziones;
	}

	public void setPrestaziones(Set prestaziones) {
		this.prestaziones = prestaziones;
	}

}
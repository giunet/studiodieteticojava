package hibernate;

// Generated 4-feb-2010 12.32.12 by Hibernate Tools 3.2.4.GA

import java.util.HashSet;
import java.util.Set;

/**
 * Tipologiavisita generated by hbm2java
 */
public class Tipologiavisita implements java.io.Serializable {

	private Integer idTipologiaVisita;
	private String tipologia;
	private double costoVisita;
	private Set prenotaziones = new HashSet(0);

	public Tipologiavisita() {
	}

	public Tipologiavisita(String tipologia, double costoVisita) {
		this.tipologia = tipologia;
		this.costoVisita = costoVisita;
	}

	public Tipologiavisita(String tipologia, double costoVisita,
			Set prenotaziones) {
		this.tipologia = tipologia;
		this.costoVisita = costoVisita;
		this.prenotaziones = prenotaziones;
	}

	public Integer getIdTipologiaVisita() {
		return this.idTipologiaVisita;
	}

	public void setIdTipologiaVisita(Integer idTipologiaVisita) {
		this.idTipologiaVisita = idTipologiaVisita;
	}

	public String getTipologia() {
		return this.tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public double getCostoVisita() {
		return this.costoVisita;
	}

	public void setCostoVisita(double costoVisita) {
		this.costoVisita = costoVisita;
	}

	public Set getPrenotaziones() {
		return this.prenotaziones;
	}

	public void setPrenotaziones(Set prenotaziones) {
		this.prenotaziones = prenotaziones;
	}

}
package hibernate;

// Generated 5-feb-2010 10.52.14 by Hibernate Tools 3.2.4.GA

/**
 * Intolleranzaallergia generated by hbm2java
 */
public class Intolleranzaallergia implements java.io.Serializable {

	private Integer idIntolleranzaAllergia;
	private Paziente paziente;
	private String flagIntAll;
	private String sostanza;
	private String alimentoPrincipale;
	private String derivati;
	private String grado;
	private String effettiCollaterali;

	public Intolleranzaallergia() {
	}

	public Intolleranzaallergia(Paziente paziente, String flagIntAll,
			String sostanza, String alimentoPrincipale) {
		this.paziente = paziente;
		this.flagIntAll = flagIntAll;
		this.sostanza = sostanza;
		this.alimentoPrincipale = alimentoPrincipale;
	}

	public Intolleranzaallergia(Paziente paziente, String flagIntAll,
			String sostanza, String alimentoPrincipale, String derivati,
			String grado, String effettiCollaterali) {
		this.paziente = paziente;
		this.flagIntAll = flagIntAll;
		this.sostanza = sostanza;
		this.alimentoPrincipale = alimentoPrincipale;
		this.derivati = derivati;
		this.grado = grado;
		this.effettiCollaterali = effettiCollaterali;
	}

	public Integer getIdIntolleranzaAllergia() {
		return this.idIntolleranzaAllergia;
	}

	public void setIdIntolleranzaAllergia(Integer idIntolleranzaAllergia) {
		this.idIntolleranzaAllergia = idIntolleranzaAllergia;
	}

	public Paziente getPaziente() {
		return this.paziente;
	}

	public void setPaziente(Paziente paziente) {
		this.paziente = paziente;
	}

	public String getFlagIntAll() {
		return this.flagIntAll;
	}

	public void setFlagIntAll(String flagIntAll) {
		this.flagIntAll = flagIntAll;
	}

	public String getSostanza() {
		return this.sostanza;
	}

	public void setSostanza(String sostanza) {
		this.sostanza = sostanza;
	}

	public String getAlimentoPrincipale() {
		return this.alimentoPrincipale;
	}

	public void setAlimentoPrincipale(String alimentoPrincipale) {
		this.alimentoPrincipale = alimentoPrincipale;
	}

	public String getDerivati() {
		return this.derivati;
	}

	public void setDerivati(String derivati) {
		this.derivati = derivati;
	}

	public String getGrado() {
		return this.grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getEffettiCollaterali() {
		return this.effettiCollaterali;
	}

	public void setEffettiCollaterali(String effettiCollaterali) {
		this.effettiCollaterali = effettiCollaterali;
	}

}

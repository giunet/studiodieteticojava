package hibernate;

// Generated 4-feb-2010 12.32.12 by Hibernate Tools 3.2.4.GA

/**
 * Assunzionebevande generated by hbm2java
 */
public class Assunzionebevande implements java.io.Serializable {

	private AssunzionebevandeId id;
	private Tipologiabevanda tipologiabevanda;
	private Abitudinialimentari abitudinialimentari;
	private String quantita;

	public Assunzionebevande() {
	}

	public Assunzionebevande(AssunzionebevandeId id,
			Tipologiabevanda tipologiabevanda,
			Abitudinialimentari abitudinialimentari) {
		this.id = id;
		this.tipologiabevanda = tipologiabevanda;
		this.abitudinialimentari = abitudinialimentari;
	}

	public Assunzionebevande(AssunzionebevandeId id,
			Tipologiabevanda tipologiabevanda,
			Abitudinialimentari abitudinialimentari, String quantita) {
		this.id = id;
		this.tipologiabevanda = tipologiabevanda;
		this.abitudinialimentari = abitudinialimentari;
		this.quantita = quantita;
	}

	public AssunzionebevandeId getId() {
		return this.id;
	}

	public void setId(AssunzionebevandeId id) {
		this.id = id;
	}

	public Tipologiabevanda getTipologiabevanda() {
		return this.tipologiabevanda;
	}

	public void setTipologiabevanda(Tipologiabevanda tipologiabevanda) {
		this.tipologiabevanda = tipologiabevanda;
	}

	public Abitudinialimentari getAbitudinialimentari() {
		return this.abitudinialimentari;
	}

	public void setAbitudinialimentari(Abitudinialimentari abitudinialimentari) {
		this.abitudinialimentari = abitudinialimentari;
	}

	public String getQuantita() {
		return this.quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

}

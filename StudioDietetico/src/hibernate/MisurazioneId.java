package hibernate;

// Generated 4-feb-2010 12.32.12 by Hibernate Tools 3.2.4.GA

/**
 * MisurazioneId generated by hbm2java
 */
public class MisurazioneId implements java.io.Serializable {

	private int idPaziente;
	private int idParametroAntropometrico;

	public MisurazioneId() {
	}

	public MisurazioneId(int idPaziente, int idParametroAntropometrico) {
		this.idPaziente = idPaziente;
		this.idParametroAntropometrico = idParametroAntropometrico;
	}

	public int getIdPaziente() {
		return this.idPaziente;
	}

	public void setIdPaziente(int idPaziente) {
		this.idPaziente = idPaziente;
	}

	public int getIdParametroAntropometrico() {
		return this.idParametroAntropometrico;
	}

	public void setIdParametroAntropometrico(int idParametroAntropometrico) {
		this.idParametroAntropometrico = idParametroAntropometrico;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MisurazioneId))
			return false;
		MisurazioneId castOther = (MisurazioneId) other;

		return (this.getIdPaziente() == castOther.getIdPaziente())
				&& (this.getIdParametroAntropometrico() == castOther
						.getIdParametroAntropometrico());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdPaziente();
		result = 37 * result + this.getIdParametroAntropometrico();
		return result;
	}

}

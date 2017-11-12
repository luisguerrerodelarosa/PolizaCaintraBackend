package com.caintra.Modelo;

public class Empresa {

	String nombreEmpresa = "";
	String rfcEmpresa = "";
	String dirCalleEmpresa = "";
	String dirColoniaEmpresa = "";
	boolean practicantes_seguro;
	
	
	public boolean isPracticantes_seguro() {
		return practicantes_seguro;
	}
	public void setPracticantes_seguro(boolean practicantes_seguro) {
		this.practicantes_seguro = practicantes_seguro;
	}
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}
	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}
	public String getDirCalleEmpresa() {
		return dirCalleEmpresa;
	}
	public void setDirCalleEmpresa(String dirCalleEmpresa) {
		this.dirCalleEmpresa = dirCalleEmpresa;
	}
	public String getDirColoniaEmpresa() {
		return dirColoniaEmpresa;
	}
	public void setDirColoniaEmpresa(String dirColoniaEmpresa) {
		this.dirColoniaEmpresa = dirColoniaEmpresa;
	}
	
	
}

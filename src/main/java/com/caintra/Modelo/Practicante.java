package com.caintra.Modelo;

public class Practicante {

	
	String fechaInicioPoliza = "";
	int planSeguro = 0;
	String practicanteId = "";
	String nombrePracticante = "";
	String rfcPracticante = "";
	String dirCallePracticante = "";
	String dirColoniaPracticante = "";
	String fec_inicioContrato= "";
	String fec_terminoContrato="";
	String fec_cancelacion="";
	Empresa empresaPracticante;
	Beneficiario beneficiarioPracticante;
	
	
	public String getPracticanteId() {
		return practicanteId;
	}
	public void setPracticanteId(String practicanteId) {
		this.practicanteId = practicanteId;
	}
	public String getFec_inicioContrato() {
		return fec_inicioContrato;
	}
	public void setFec_inicioContrato(String fec_inicioContrato) {
		this.fec_inicioContrato = fec_inicioContrato;
	}
	public String getFec_terminoContrato() {
		return fec_terminoContrato;
	}
	public void setFec_terminoContrato(String fec_terminoContrato) {
		this.fec_terminoContrato = fec_terminoContrato;
	}
	public String getFec_cancelacion() {
		return fec_cancelacion;
	}
	public void setFec_cancelacion(String fec_cancelacion) {
		this.fec_cancelacion = fec_cancelacion;
	}
	public Empresa getEmpresaPracticante() {
		return empresaPracticante;
	}
	public void setEmpresaPracticante(Empresa empresaPracticante) {
		this.empresaPracticante = empresaPracticante;
	}
	public Beneficiario getBeneficiarioPracticante() {
		return beneficiarioPracticante;
	}
	public void setBeneficiarioPracticante(Beneficiario beneficiarioPracticante) {
		this.beneficiarioPracticante = beneficiarioPracticante;
	}
	public String getFechaInicioPoliza() {
		return fechaInicioPoliza;
	}
	public void setFechaInicioPoliza(String fechaInicioPoliza) {
		this.fechaInicioPoliza = fechaInicioPoliza;
	}
	public int getPlanSeguro() {
		return planSeguro;
	}
	public void setPlanSeguro(int planSeguro) {
		this.planSeguro = planSeguro;
	}
	public String getNombrePracticante() {
		return nombrePracticante;
	}
	public void setNombrePracticante(String nombrePracticante) {
		this.nombrePracticante = nombrePracticante;
	}
	public String getRfcPracticante() {
		return rfcPracticante;
	}
	public void setRfcPracticante(String rfcPracticante) {
		this.rfcPracticante = rfcPracticante;
	}
	public String getDirCallePracticante() {
		return dirCallePracticante;
	}
	public void setDirCallePracticante(String dirCallePracticante) {
		this.dirCallePracticante = dirCallePracticante;
	}
	public String getDirColoniaPracticante() {
		return dirColoniaPracticante;
	}
	public void setDirColoniaPracticante(String dirColoniaPracticante) {
		this.dirColoniaPracticante = dirColoniaPracticante;
	}


	
	
	
	
}

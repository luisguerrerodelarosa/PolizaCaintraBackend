package com.caintra.Modelo;

public class CaintraResponse {

	private boolean respuestaServicio=false;
	private String mensajeRespuesta="";
	private Usuario usuarioLoggeado;
	private Practicante practicanteCaintra;
	private PolizaCaintra polizaPracticante;
	
	public PolizaCaintra getPolizaPracticante() {
		return polizaPracticante;
	}
	public void setPolizaPracticante(PolizaCaintra polizaPracticante) {
		this.polizaPracticante = polizaPracticante;
	}
	public boolean isRespuestaServicio() {
		return respuestaServicio;
	}
	public void setRespuestaServicio(boolean respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}
	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}
	public void setMensajeRespuesta(String mensajeRespuesta) {
		this.mensajeRespuesta = mensajeRespuesta;
	}
	public Usuario getUsuarioLoggeado() {
		return usuarioLoggeado;
	}
	public void setUsuarioLoggeado(Usuario usuarioLoggeado) {
		this.usuarioLoggeado = usuarioLoggeado;
	}
	public Practicante getPracticanteCaintra() {
		return practicanteCaintra;
	}
	public void setPracticanteCaintra(Practicante practicanteCaintra) {
		this.practicanteCaintra = practicanteCaintra;
	}
	
	
}

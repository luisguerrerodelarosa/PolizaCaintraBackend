package com.caintra.Configuracion;

public class RsaPluginResponse {

	private String accionEjecutada= "";
	private boolean ejecucionPlugin=false;
	private String mensajePlugin ="";
	
	private String serialNumberToken="";
	private String OTP = "";
	private String usuarioOTP="";
	private int segundosRestantes= 0 ;
	private boolean tokenExpirado=false;
	
	public String getOTP() {
		return OTP;
	}
	
	public String getAccionEjecutada() {
		return accionEjecutada;
	}
	public boolean isEjecucionPlugin() {
		return ejecucionPlugin;
	}
	public String getMensajePlugin() {
		return mensajePlugin;
	}
	public String getSerialNumberToken() {
		return serialNumberToken;
	}

	public String getUsuarioOTP() {
		return usuarioOTP;
	}
	public int getSegundosRestantes() {
		return segundosRestantes;
	}
	public boolean isTokenExpirado() {
		
		return tokenExpirado;
	}
	public void setAccionEjecutada(String accionEjecutada) {
		try {
			this.accionEjecutada = accionEjecutada;
		} catch (Exception e) {
			this.accionEjecutada = "";
		}
		
	}
	public void setEjecucionPlugin(boolean ejecucionPlugin) {
		try {
			this.ejecucionPlugin = ejecucionPlugin;
		} catch (Exception e) {
			this.ejecucionPlugin = false;
		}
	}
	public void setMensajePlugin(String mensajePlugin) {
		try {
			this.mensajePlugin = mensajePlugin;
		} catch (Exception e) {
			this.mensajePlugin = "";
		}
	}
	public void setSerialNumberToken(String serialNumberToken) {
		try {
			this.serialNumberToken = serialNumberToken;
		} catch (Exception e) {
			this.serialNumberToken = "";
		}
	}

	public void setUsuarioOTP(String usuarioOTP) {
		try {
			this.usuarioOTP = usuarioOTP;
		} catch (Exception e) {
			this.usuarioOTP = "";
		}
		
	}
	public void setSegundosRestantes(int segundosRestantes) {
		try {
			this.segundosRestantes = segundosRestantes;
		} catch (Exception e) {
			this.segundosRestantes = 0;
		}
		
	}
	public void setTokenExpirado(boolean tokenExpirado) {
		try {
			this.tokenExpirado = tokenExpirado;
		} catch (Exception e) {
			this.tokenExpirado = false;
		}
		
	}
	public void setOTP(String oTP) {
		try {
			this.OTP = oTP;
		} catch (Exception e) {
			this.OTP = "";
		}
	}
	
	
	
}

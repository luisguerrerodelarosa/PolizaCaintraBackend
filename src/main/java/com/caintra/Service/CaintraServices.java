package com.caintra.Service;


import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Response;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tempuri.SolicitaPolizaCaintra;

import com.caintra.Configuracion.DBManager;
import com.caintra.Configuracion.RsaPluginResponse;
import com.caintra.Configuracion.Utils;
import com.caintra.Modelo.CaintraResponse;
import com.caintra.PolizaModulo.AccesoService;
import com.caintra.PolizaModulo.PolizaService;
import com.caintra.PolizaModulo.PracticanteService;


@RestController
public class CaintraServices {

	
	Utils utils = new Utils();
	

	@RequestMapping("/hello")
	public String message() {//REST Endpoint.
		PracticanteService service = new PracticanteService();
		SolicitaPolizaCaintra caintra =new SolicitaPolizaCaintra();
		
		
		
		
		return null;
	}
	@RequestMapping(value = "/generarPoliza/practicante/{idPracticante}/opcionPoliza/{opcionPoliza}/fechaInicio/{fechaInicio}/mesDuracion/{mesDuracion}", method = RequestMethod.GET , produces = "application/json")
	public @ResponseBody   Object generarPoliza(@PathVariable String idPracticante,@PathVariable String opcionPoliza,
			@PathVariable String fechaInicio,@PathVariable String mesDuracion) {//Welcome page, non-rest
		PracticanteService practicanteService = null;
		PolizaService polizaService = null;
		CaintraResponse response,response2;
		try {
			practicanteService = new PracticanteService();
			response = practicanteService.getPracticanteInfo(idPracticante);
			polizaService = new PolizaService();
			if(response.isRespuestaServicio()){
				response2 = polizaService.solicitaPoliza(response.getPracticanteCaintra(),opcionPoliza,fechaInicio,mesDuracion);
				return response2;
			}else{
				
				return response;
			}
			
		} catch (Exception e) {
			response = new CaintraResponse();
			response.setRespuestaServicio(false);
			response.setMensajeRespuesta(utils.getProperties("caintra.acceso.ExcepcionGeneral")); 
		}
		
		return response;
	}
	
	
	@RequestMapping(value = "/getPracticante/practicante/{idPracticante}", method = RequestMethod.GET , produces = "application/json")
	public @ResponseBody   Object getPracticante(@PathVariable String idPracticante ) {//Welcome page, non-rest
		PracticanteService practicanteService = null;
		CaintraResponse response;
		try {
			practicanteService = new PracticanteService();
			response = practicanteService.getPracticanteInfo(idPracticante);
		} catch (Exception e) {
			response = new CaintraResponse();
			response.setRespuestaServicio(false);
			response.setMensajeRespuesta(utils.getProperties("caintra.acceso.ExcepcionGeneral")); 
		}
		
		return response;
	}
	
	@RequestMapping(value = "/getAcceso/usuario/{user}/password/{password}", method = RequestMethod.GET , produces = "application/json")
	public @ResponseBody   Object getAcceso(@PathVariable String user,@PathVariable String password ) {//Welcome page, non-rest
		AccesoService accesoService = null;
		CaintraResponse response;
		try {
			accesoService = new AccesoService();
			response = accesoService.accesoUsuario(user, password);
		} catch (Exception e) {
			response = new CaintraResponse();
			response.setRespuestaServicio(false);
			response.setMensajeRespuesta(utils.getProperties("caintra.acceso.ExcepcionGeneral")); 
		}
		
		return response;
	}


}

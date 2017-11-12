package com.caintra.PolizaModulo;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caintra.Configuracion.DBManager;
import com.caintra.Configuracion.RsaPluginResponse;
import com.caintra.Configuracion.Utils;
import com.caintra.Modelo.CaintraResponse;
import com.caintra.Modelo.Practicante;
import com.caintra.Modelo.Usuario;


public class AccesoService {
	Utils utils = new Utils();
	DBManager conexion = null;
	
	public CaintraResponse accesoUsuario(String usr,String pwd){
		CaintraResponse response;
		Usuario usuario;
		try {
			conexion=new DBManager();
			response = new CaintraResponse();
			
			if(! utils.validaConexion(conexion)){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.conexion.error"));
				return response;
			}
			if(usr == null || pwd == null){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.acceso.faltaparametro"));
				return response;
			}
			usr = usr.trim();
			pwd = pwd.trim();
			if(usr.equals("") || pwd.equals("")){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.acceso.faltaparametro"));
				return response;
			}
			usr = usr.toLowerCase();
			pwd.toLowerCase();
			usuario = getUsuario(usr);

			if(usuario == null){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.acceso.usuarioNoExiste"));
				return response;
			}else{
				if(usuario.getPassword().toLowerCase().equals(pwd.toLowerCase().trim())){
					if(usuario.getActivo() == 1){
						if(usuario.getTipoUsuario().equals("A")){
							response.setRespuestaServicio(true);
							response.setMensajeRespuesta(utils.getProperties("caintra.acceso.usuarioLoggeado"));
							response.setUsuarioLoggeado(usuario);
							return response;
						}else{
							response.setRespuestaServicio(false);
							response.setMensajeRespuesta(utils.getProperties("caintra.acceso.tipoAcceso"));
							return response;
						}
					}else{
						response.setRespuestaServicio(false);
						response.setMensajeRespuesta(utils.getProperties("caintra.acceso.usuarioInactivo"));
						return response;
					}
				}else{
					response.setRespuestaServicio(false);
					response.setMensajeRespuesta(utils.getProperties("caintra.acceso.pwdIncorrecto"));
					return response;
				}
				
			}
				
			
		} catch (Exception e) {
			response = new CaintraResponse();
			response.setRespuestaServicio(false);
			response.setMensajeRespuesta(utils.getProperties("caintra.acceso.ExcepcionGeneral"));
			return response;
		}
		
		
	}
	
	public Usuario getUsuario(String usr){
		List<Usuario> usuarios;
 		
 		try {
 			usuarios = DBManager.getQuery(Usuario.class,"SELECT * FROM usuario WHERE login=?",usr);
            if(usuarios == null || usuarios.isEmpty()) {
                return null;
            }
            else{
                 return usuarios.get(0);
            }
		} catch (Exception e) {
			return null;
		}
		
	}
}

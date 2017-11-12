package com.caintra.PolizaModulo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilderFactory;

import org.tempuri.Poliza;
import org.tempuri.PolizaSoap;
import org.tempuri.SolicitaPolizaCaintra;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.caintra.Configuracion.DBManager;
import com.caintra.Configuracion.Utils;
import com.caintra.Modelo.Beneficiario;
import com.caintra.Modelo.CaintraResponse;
import com.caintra.Modelo.Empresa;
import com.caintra.Modelo.Practicante;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class PracticanteService {

	Utils utils = new Utils();
	DBManager conexion = null;

	public CaintraResponse getPracticanteInfo(String idConvenio){
		CaintraResponse response;
		Practicante practicante;
		Empresa empresa;
		Beneficiario beneficiario;
		try {
			conexion=new DBManager();
			response = new CaintraResponse();
			if(! utils.validaConexion(conexion)){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.conexion.error"));
				return response;
			}
			if(idConvenio == null){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.acceso.faltaparametro"));
				return response;
			}	
			idConvenio = idConvenio.trim();
			if(idConvenio.equals("")){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.acceso.faltaparametro"));
				return response;
			}
			practicante = getPracticante(idConvenio);
			if(practicante == null){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.practicante.errorInfoPracticante"));
				return response;
			}
			empresa = getEmpresaPracticante(idConvenio);
			if(empresa == null){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.practicante.errorInfoEmpresa"));
				return response;
			}
			if(!empresa.isPracticantes_seguro()){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.practicante.empresaSinSeguro"));
				return response;
				
			}
			practicante.setEmpresaPracticante(empresa);
			beneficiario = getBeneficiarioPracticante(idConvenio);
			if(beneficiario == null){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.practicante.errorInfoBeneficiario"));
				return response;
			}
			practicante.setBeneficiarioPracticante(beneficiario);
			
			response.setRespuestaServicio(true);
			response.setMensajeRespuesta(utils.getProperties("caintra.practicante.informacion"));
			response.setPracticanteCaintra(practicante);
			
			
		} catch (Exception e) {
			response = new CaintraResponse();
			response.setRespuestaServicio(false);
			response.setMensajeRespuesta(utils.getProperties("caintra.acceso.ExcepcionGeneral"));
			return response;
		}
		return response;
	}

 	public Practicante getPracticante(String idConvenio){
 		List<Practicante> practicantes;
 		String query = "";
 		try {
 			query="SELECT p.practicanteId AS practicanteId,"
 					+ " concat(p.nombre,' ',p.paterno,' ',p.materno) AS nombrePracticante, "
                    + " p.rfc AS rfcPracticante , "
                    + " concat(d.calle,' ',d.numero_exterior) AS dirCallePracticante,"
                    + " col.nombre AS dirColoniaPracticante, "
                    + " col.codigoPostal AS cp "
                    + " FROM practicante p "
                    + " INNER JOIN direccion d "
                    + " ON p.fk_direccionId=d.direccionId "
                    + " INNER JOIN colonia col "
                    + " ON d.fk_coloniaId=col.coloniaId "
                    + " WHERE p.practicanteId= ?"
                    + " LIMIT 1;";
 			practicantes=DBManager.getQuery(Practicante.class,query,idConvenio);
            if(practicantes == null || practicantes.isEmpty()) {
                return null;
            }
            else{
            	
                 return practicantes.get(0);
            
            }
		} catch (Exception e) {
			return null;
		}
 		
 	}
 	public Empresa getEmpresaPracticante(String idConvenio){
 		List<Empresa> empresas;
 		String query = "";
 		try {
 			query="SELECT  e.razon_empresa AS nombreEmpresa,"
 					+ " e.rfc AS rfcEmpresa ,"
                    + " concat(d.calle,' ',d.numero_exterior) AS dirCalleEmpresa,"
                    + " col.nombre AS dirColoniaEmpresa, "
                    + " col.codigoPostal AS cp,"
                    + " e.practicantes_seguro AS practicantes_seguro "
                    + " FROM empresa e "
                    + " INNER JOIN direccion d "
                    + " ON e.fk_direccionId=d.direccionId "
                    + " INNER JOIN colonia col "
                    + " ON d.fk_coloniaId=col.coloniaId "
                    + " INNER JOIN departamento dep "
                    + " ON e.empresaId=dep.fk_empresaId "
                    + " INNER JOIN practicante p "
                    + " ON dep.departamentoId=p.fk_departamentoId "
                    + " where p.practicanteId= ?"
                    + " LIMIT 1";
 			empresas=DBManager.getQuery(Empresa.class,query,idConvenio);
            if(empresas == null || empresas.isEmpty()) {
                return null;
            }
            else{
                 return empresas.get(0);
            
            }
		} catch (Exception e) {
			return null;
		}
 		
 	}
 	public Beneficiario getBeneficiarioPracticante(String idConvenio){
 		List<Beneficiario> beneficiarios = null;
 		String query = "";
 		try {
 			query="SELECT beneficiario1 AS nombreBeneficiario1,"
 					+ " porcentaje1 AS porcentajeBeneficiario1,"
 					+ " parentesco1 AS parentescoBeneficiario1, "
                    + " beneficiario2 AS nombreBeneficiario2,"
                    + " porcentaje2 AS porcentajeBeneficiario2,"
                    + " parentesco2 AS parentescoBeneficiario2"
                    + " FROM polizaseguro "
                    + " WHERE fk_practicanteid= ?"
                    + " LIMIT 1;";
 			beneficiarios=DBManager.getQuery(Beneficiario.class,query,idConvenio);
            if(beneficiarios == null || beneficiarios.isEmpty()) {
                return null;
            }
            else{
            	return beneficiarios.get(0);
            	
            }
		} catch (Exception e) {
			return null;
		}
 		
	}
}

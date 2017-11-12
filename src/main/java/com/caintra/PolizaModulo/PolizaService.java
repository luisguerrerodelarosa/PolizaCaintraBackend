package com.caintra.PolizaModulo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import com.caintra.Modelo.PolizaCaintra;
import com.caintra.Modelo.Practicante;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class PolizaService {

	Utils utils = new Utils();
	DBManager conexion = null;
	public CaintraResponse solicitaPoliza(Practicante p,String opcion,String fechaInicio,String mesDuracion){
		CaintraResponse response = new CaintraResponse();
		Poliza stub;
		String numeroPoliza = "Sin poliza";
		SolicitaPolizaCaintra caintra;
		PolizaCaintra poliza;
		
		try {
			caintra = new SolicitaPolizaCaintra();
			stub = new Poliza(new URL(utils.getProperties("seguro_wsdl")));
			if(stub == null ){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.poliza.nowsdl"));
				return response;
			}
			if(! (opcion.trim().equals("1")||opcion.trim().equals("2"))){
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.poliza.opcionIncorrecta"));
				return response;
			}
			int duracion=0;
			try {
				duracion = Integer.parseInt(mesDuracion);
				if( !(duracion >=1 && duracion<=6)){
					response.setRespuestaServicio(false);
					response.setMensajeRespuesta(utils.getProperties("caintra.poliza.mesIncorrecta"));
					return response;
				}
			} catch (Exception e) {
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.poliza.mesIncorrecta"));
				return response;
			}
			
			PolizaSoap soap = stub.getPolizaSoap();
			caintra.setStrXMLEntrada(conversion(p,opcion,fechaInicio,mesDuracion).toString());
			numeroPoliza = soap.solicitaPolizaCaintra(caintra.getStrXMLEntrada());
			if(numeroPoliza != null){
				if(numeroPoliza.contains("<Poliza>")){
					try {
						numeroPoliza = numeroPoliza.substring(numeroPoliza.indexOf("<Poliza>")+8 ,numeroPoliza.indexOf("</Poliza>"));
						poliza = new PolizaCaintra();
						poliza.setNumeroPoliza(numeroPoliza);
						poliza.setDescargaPoliza(utils.getProperties("seguro_impresion")+numeroPoliza);
						guardaPoliza(p.getPracticanteId(), poliza);
						response.setPolizaPracticante(poliza);
						response.setRespuestaServicio(true);
						response.setMensajeRespuesta(utils.getProperties("caintra.practicante.informacion"));
						
					} catch (Exception e2) {
						response.setRespuestaServicio(false);
						response.setMensajeRespuesta(utils.getProperties("caintra.poliza.okNumPoliza"));
						return response;
					}
				}else{
					response.setRespuestaServicio(false);
					response.setMensajeRespuesta(utils.getProperties("caintra.poliza.noNumPoliza"));
					return response;
				}
			}else{
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.poliza.noXML"));
				return response;
			}
		   } catch (MalformedURLException ex) {
				response = new CaintraResponse();
				response.setRespuestaServicio(false);
				response.setMensajeRespuesta(utils.getProperties("caintra.acceso.ExcepcionGeneral"));
				return response;
			}
			return response;
		
	}
	
    
 	public  StringBuffer conversion(Practicante p,String opcionSeguro,String fechaInicio,String meses){
	        StringBuffer sb=new StringBuffer();
	        try {
	                Document doc;
	                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	                doc = dbf.newDocumentBuilder().newDocument();
							//Elemen
					Element root = doc.createElement("SolicitudPoliza");
					root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
					root.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
					Element conexion = doc.createElement("Conexion");
					Element token = doc.createElement("Token");
									//TODO
					token.appendChild( doc.createTextNode(utils.getProperties("seguro_token")));
					Element ip = doc.createElement("IP");
					ip.appendChild( doc.createTextNode(utils.getProperties("seguro_ip")));
					Element negocio = doc.createElement("Negocio");
					negocio.appendChild( doc.createTextNode(utils.getProperties("seguro_negocio")));
					conexion.appendChild(token);
					conexion.appendChild(ip);
					conexion.appendChild(negocio);
					root.appendChild(conexion);
						 			//generales
					Element generales = doc.createElement("Generales");
					Element plan = doc.createElement("Plan");
					String plan_ = opcionSeguro;//modelo.getNombre_plan().startsWith("Opcion 1")? "1":"2"; Debe ir la opcion a Elegir
					plan.appendChild( doc.createTextNode(plan_));
					Element estatusPago = doc.createElement("EstatusPago");
					estatusPago.appendChild( doc.createTextNode("1"));
					Element inicioVigencia = doc.createElement("InicioVigencia");
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					//inicioVigencia.appendChild( doc.createTextNode(sdf.format(modelo.getVigencia())));
					inicioVigencia.appendChild( doc.createTextNode(fechaInicio));//inicio de Vigencia capturada
					Element duracion = doc.createElement("Duracion");
					duracion.appendChild( doc.createTextNode(meses));//meses Capturados 
			                Element fuente = doc.createElement("Fuente");
			                fuente.appendChild( doc.createTextNode("1"));
					generales.appendChild(plan);
					generales.appendChild(estatusPago);
					generales.appendChild(inicioVigencia);
					generales.appendChild(duracion);
			                //generales.appendChild(fuente);
					root.appendChild(generales);
									//Contratante
					Element contratante = doc.createElement("Contratante");
					Element nombre = doc.createElement("Nombre");
					nombre.appendChild( doc.createTextNode(p.getEmpresaPracticante().getNombreEmpresa()));
					Element rfc = doc.createElement("RFC");
					rfc.appendChild( doc.createTextNode(p.getEmpresaPracticante().getRfcEmpresa()));
					Element calleNumero = doc.createElement("CalleNumero");
					calleNumero.appendChild( doc.createTextNode(p.getEmpresaPracticante().getDirCalleEmpresa()));
					Element colonia = doc.createElement("Colonia");
					colonia.appendChild( doc.createTextNode(p.getEmpresaPracticante().getDirColoniaEmpresa()));
					contratante.appendChild(nombre);
					contratante.appendChild(rfc);
					contratante.appendChild(calleNumero);
					contratante.appendChild(colonia);
					root.appendChild(contratante);
									//Asegurado
					Element asegurado = doc.createElement("Asegurado");
					Element nombre2 = doc.createElement("Nombre");
					nombre2.appendChild( doc.createTextNode(p.getNombrePracticante()));
					Element rfc2 = doc.createElement("RFC");
					rfc2.appendChild( doc.createTextNode(p.getRfcPracticante()));
					Element calleNumero2 = doc.createElement("CalleNumero");
					calleNumero2.appendChild( doc.createTextNode(p.getDirCallePracticante()));
					Element colonia2 = doc.createElement("Colonia");
					colonia2.appendChild( doc.createTextNode(p.getDirColoniaPracticante()));
					asegurado.appendChild(nombre2);
					asegurado.appendChild(rfc2);
					asegurado.appendChild(calleNumero2);
					asegurado.appendChild(colonia2);
					root.appendChild(asegurado);
									//Beneficiarios
					Element beneficiarios = doc.createElement("Beneficiarios");
					Element beneficiario1 = doc.createElement("Beneficiario");
					Element beneficiario2 = doc.createElement("Beneficiario");
					Element nombre1 = doc.createElement("Nombre");
					nombre1.appendChild( doc.createTextNode(p.getBeneficiarioPracticante().getNombreBeneficiario1()));
					Element parentesco1 = doc.createElement("Parentesco");
					parentesco1.appendChild( doc.createTextNode(p.getBeneficiarioPracticante().getParentescoBeneficiario1()));
					Element porcentaje1 = doc.createElement("Porcentaje");
					porcentaje1.appendChild( doc.createTextNode(p.getBeneficiarioPracticante().getPorcentajeBeneficiario1()+""));
					Element nombre2_ = doc.createElement("Nombre");
					nombre2_.appendChild( doc.createTextNode(p.getBeneficiarioPracticante().getNombreBeneficiario2()));
					Element parentesco2 = doc.createElement("Parentesco");
					parentesco2.appendChild( doc.createTextNode(p.getBeneficiarioPracticante().getParentescoBeneficiario2()));
					Element porcentaje2 = doc.createElement("Porcentaje");
					porcentaje2.appendChild( doc.createTextNode(p.getBeneficiarioPracticante().getPorcentajeBeneficiario2()+""));
					beneficiario1.appendChild(nombre1);
					beneficiario1.appendChild(parentesco1);
					beneficiario1.appendChild(porcentaje1);
					beneficiario2.appendChild(nombre2_);
					beneficiario2.appendChild(parentesco2);
					beneficiario2.appendChild(porcentaje2);
					beneficiarios.appendChild(beneficiario1);
					beneficiarios.appendChild(beneficiario2);
					root.appendChild(beneficiarios);
					doc.appendChild(root);
		
			                OutputFormat format = new OutputFormat(doc);
					format.setIndenting(true);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					OutputStreamWriter osw =  new OutputStreamWriter(baos);
					XMLSerializer serializer = new XMLSerializer(osw,format);
					serializer.serialize(doc);
		
			                FileWriter fw = new FileWriter(new File(utils.getProperties("caintra.conexion.error")+"test_alta_dom.xml"));
			                serializer = new XMLSerializer(fw,format);
					serializer.serialize(doc);
					fw.close();
		
			                baos.flush();
					ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
					BufferedReader br = new BufferedReader(new InputStreamReader(bais));
			                StringBuffer xmlEntrada = new StringBuffer();
					String line ="";
					while((line =br.readLine())!=null){
						xmlEntrada.append(line);
						xmlEntrada.append(System.getProperty("line.separator"));
			                       
					}
	                sb=xmlEntrada;
	                

	        } catch (Exception exc) {
	        	return null;
	        }

	        return sb;

	    }
 	

 	 public int guardaPoliza(String idPracticante,PolizaCaintra poliza){
         int resp = -1;
         String sql="INSERT INTO archivo(ruta,mime_type,size,nombre,fk_ligapracticanteId,extension,descripcion)"
                 + " VALUES"
                 + " ('"+poliza.getDescargaPoliza()+"'"
                 + " ,'application/pdf'"
                 + " ,1"
                 + " ,'polizaseguro"+poliza.getNumeroPoliza()+"'"
                 + " ,"+idPracticante
                 + " ,'pdf'"
                 + " ,'poliza generada por el sistema');";
               try {
                 resp = DBManager.insert(sql);
             } catch (SQLException ex) {
                 //Logger.getLogger(dEmpresa.class.getName()).log(Level.SEVERE, null, ex);
                 resp = -1;
             }
         return resp;
      }
}

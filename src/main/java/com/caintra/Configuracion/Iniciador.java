package com.caintra.Configuracion;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class Iniciador extends AbstractAnnotationConfigDispatcherServletInitializer  {

	@Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { Configuracion.class };
    }
  
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }
  
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}

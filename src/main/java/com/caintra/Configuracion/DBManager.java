/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.caintra.Configuracion;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.DbUtils;

public class DBManager {
    public static DataSource getMainDB(){
        Context initContext;
        Context envContext;
        DataSource resp = null;
        DbUtils dbu;
        try
        {
            //Obtiene una Conexion del Pool
            initContext = new InitialContext();
            envContext  = (Context)initContext.lookup("java:/comp/env");
            resp = (DataSource)envContext.lookup("jdbc/sapraPoliza");

        }
        catch (Exception e)
        {
           e.printStackTrace();
        }

        return resp;
    }
    public void conexion(){

    }
    public static List getQuery(Class type,String sql,Object... params) throws SQLException {
        List<Class> tipo=null;
        DataSource db=getMainDB();
        try {
                QueryRunner run = new QueryRunner(db);
                ResultSetHandler<List> h = new BeanListHandler(type);
                tipo = (List)run.query(sql, h, params);
                if(db!=null){
                        DbUtils.close(db.getConnection());
                        db=null;
                    }

        } catch (Exception e) {
           if(db!=null){
                        DbUtils.close(db.getConnection());
                        db=null;
                    }
        }finally{
            if(db!=null){
                         DbUtils.close(db.getConnection());
                        db=null;
                    }
        }
        
        return tipo;
    }

    public static int Update(String sql) throws SQLException {
        DataSource db=getMainDB();
        DbUtils dbm=new DbUtils();
        int x=-1;
        QueryRunner run = new QueryRunner(db);
        try {
             x=run.update(sql);
             if(db!=null){
                        DbUtils.close(db.getConnection());
                        db=null;
                    }
        } catch (Exception e) {
                if(db!=null){                     
                        DbUtils.close(db.getConnection());
                        db=null;
                    }
        }finally{
                if(db!=null){
                        DbUtils.close(db.getConnection());
                        db=null;
                    }
        }

        return x;
    }

    public static int insert(String sql) throws SQLException {
        DataSource db = getMainDB();
        ResultSet rs = null;
        DbUtils dbm=new DbUtils();
        Connection c=db.getConnection();
        Statement st = c.createStatement();
        try {


        st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
        rs = st.getGeneratedKeys();
            if(rs.next()){
                int retorno=rs.getInt(1);
                    if(rs!=null){       
                        DbUtils.close(rs);
                        rs=null;
                    }
                    if(st!=null){
                        DbUtils.close(st);
                        st=null;
                    }
                    if(db!=null){
                       DbUtils.close(c);
                       c=null;
                       db=null;
                        
                    }
                return retorno;
            }
        } catch (Exception e) {
            e.printStackTrace();
               if(rs!=null){
                        DbUtils.close(rs);
                        rs=null;
                    }
                    if(st!=null){
                        DbUtils.close(st);
                        st=null;
                    }
                    if(db!=null){
                        DbUtils.close(db.getConnection());
                        c=null;
                       db=null;
                    }

        }finally{
                   if(rs!=null){
                         DbUtils.close(rs);
                        rs=null;
                    }
                    if(st!=null){
                        DbUtils.close(st);
                        st=null;
                    }
                    if(db!=null){
                        DbUtils.close(db.getConnection());
                        c=null;
                        db=null;
                    }
        }
      return 0;
    }

     public static DataSource cerrarConexion(DataSource ds)
    {
        try
        {
            if(ds != null)
            {
                ds.getConnection().close();
                ds = null;
            }
        }
        catch(Exception e)
        {
            ds = null;
        }
        return ds;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackConsult;

/**
 *
 * @author cuerv
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.util.logging.Level;
import java.util.logging.Logger;
import PackConsult.ConsultSQl;
import java.sql.SQLRecoverableException;

public class ConsultDataBase{
    private String user = "CROWDURA29";
    private String pass = "7Abril20011/F";
    private String url  = "jdbc:oracle:thin:@localhost:1521:XE";
    private PreparedStatement prepstatemnt;
    private Statement stateme;
    private ResultSet resultsetIn;
    private ResultSet resultsetUpd;
    private ResultSet resultsetDel;
    private ResultSet resultsetConSQL;
    private ResultSet resultsetConTab;
    private ResultSet resultsetConTabCol;
    private ConsultSQl objtConsultSql;
    
    public String getUser( ){
       return this.user;
    }
    public String getpass(){
       return this.pass;
    }
    public String getUrlConet(){
       return this.url;   
    }
    public ConsultDataBase(){
        this.objtConsultSql = new ConsultSQl();
    }
    public ResultSet getResultInsert () throws SQLException{
        String[] campSql =  { "NIT", "NAMEF", "NAMEL", "TEL", "ADDRES" };
        this.objtConsultSql.setConsultInsertUserpro( campSql , "USERPRO");
        String sqlInser = this.objtConsultSql.getConsultInsertUserpro();
        
        
        
        System.out.print(sqlInser);
        try( Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)){
            
            this.prepstatemnt = conexion.prepareStatement(sqlInser);
            
            this.prepstatemnt.setString( 1 , "1543838");
            this.prepstatemnt.setString( 2, "Federico");
            this.prepstatemnt.setString( 3, "Cuervo");
            this.prepstatemnt.setInt(4, 2115436);
            this.prepstatemnt.setString(5, "Prueba de dirección");
            
            int valfilas = this.prepstatemnt.executeUpdate();
            System.out.println(valfilas);
            
            this.resultsetIn = this.prepstatemnt.executeQuery();
            
            if(valfilas != 0){
                System.out.println("El registro se insertó correctamente en la base de datos");
            }else{
                System.out.println("No se pudo insertar el registro en la base de datos");
            }
            
            prepstatemnt.close();
            conexion.close();
                       
        } catch (SQLException ex){
            Logger.getLogger(ConsultDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.resultsetIn;
    }
    public String[][] getResultConsult(String[] campSql, String consultable, String[][] consultMatriz) throws SQLException{
        String[][] data = null;
        int cont = 0;
        this.objtConsultSql.setConsultALLUserpro(campSql, consultable, consultMatriz);
        
        String consultSQL = this.objtConsultSql.getConsultALLUserpro();
        
        try(Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)) {
            
            this.stateme = conexion.createStatement();
            
            this.resultsetConSQL = stateme.executeQuery(consultSQL);
            this.resultsetConSQL.next();
             do {
                cont += 1;
                System.out.println(this.resultsetConSQL.getMetaData());
                System.out.println(cont);
            } while (this.resultsetConSQL.next());
            this.stateme.close();
            conexion.close();
             
        }catch(SQLException ex){
           if(ex instanceof java.sql.SQLRecoverableException){
              System.err.println("Error SQlRecoverableException"+ ex.getMessage());
           }else{
              System.err.println("Error al ejecutar la consulta:"+ ex.getMessage());
           }
           
           Logger.getLogger(ConsultDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    public ResultSet getResultUpdate() throws SQLException{
        String[][] campSetDaSql = null;
        String[][] consultMatriz = null;
        this.objtConsultSql.setConsultUpdateUserpro(campSetDaSql, "USERPRO", consultMatriz);
        
        try(Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)){
          this.prepstatemnt = conexion.prepareStatement(this.objtConsultSql.getConsultUpdateUserpro());
          this.resultsetUpd = this.prepstatemnt.executeQuery();
          this.resultsetUpd.next();
        }catch(SQLException ex){
        }
        
       return this.resultsetUpd;
    }
    public ResultSet getResultDelect() throws SQLException{
        return this.resultsetDel;
    }
    public String[] getResultTab() throws SQLException{
        this.resultsetConTab = null;
        objtConsultSql.setConsultTable(user);
        String[] dataTabla = null;
        String data ="";
        int cont = 0;
        try(Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)){
            this.stateme = conexion.createStatement();
            this.resultsetConTab = stateme.executeQuery(objtConsultSql.getConsultTab());
            this.resultsetConTab.next();
              
            do{
                cont += 1;
                data += this.resultsetConTab.getString("table_name")+",";
                
            }while(this.resultsetConTab.next());
            dataTabla = new String[cont];
            dataTabla = data.split(",");
            this.stateme.close();
            conexion.close();
            
        }catch (SQLException ex){
          if(ex instanceof java.sql.SQLRecoverableException){
             System.err.println("Error SQlRecoverableException"+ ex.getMessage());
           }else{
              System.err.println("Error al ejecutar la consulta:"+ ex.getMessage());
           }
          Logger.getLogger(ConsultDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
       return dataTabla;
    }
    public String[][] getResutColum(String[] namTabs) throws SQLException{
        String[][] columns = null;
        String rs = null;
        this.resultsetConTabCol = null;
        objtConsultSql.setConsultTableCol(user,namTabs);
        
        try(Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)){
            String[] data = new String[namTabs.length];
            String refCol = "";
            int cont = 0;
            
            this.prepstatemnt = conexion.prepareStatement(objtConsultSql.getConsultTabCol());
             
            this.resultsetConTabCol = prepstatemnt.executeQuery();
            this.resultsetConTabCol.next();
            do{
                cont += 1;
                for(int i = 0; i < namTabs.length; i++){
                    if(cont == 1){
                       data[i] = this.resultsetConTabCol.getString("col"+namTabs[i].toLowerCase())+",";
                    }else{
                       data[i] += this.resultsetConTabCol.getString("col"+namTabs[i].toLowerCase())+",";
                    }
                } 
            }while(resultsetConTabCol.next());
            
            columns = new String[namTabs.length][cont];
            
            for(int i = 0; i < columns.length; i ++){
                String[] dataCol = data[i].split(",");
                for(int j = 0; j < columns[i].length; j++){
                    columns[i][j] = dataCol[j];
                }
            }
            
            this.prepstatemnt.close();
            conexion.close();
            
        }catch(SQLException ex){
           if(ex instanceof java.sql.SQLRecoverableException){
             System.err.println("Error SQlRecoverableException"+ ex.getMessage());
           }else{
              System.err.println("Error al ejecutar la consulta:"+ ex.getMessage());
           }
          Logger.getLogger(ConsultDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
               
        return columns;
    }
}    


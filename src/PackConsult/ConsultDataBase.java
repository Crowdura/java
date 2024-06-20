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
import PackVal.valData;

public class ConsultDataBase extends valData{
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
    private ResultSet resultsetConTabKey;
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
    public ResultSet getResultInsert (String[] campSql,String[] valCampSql, String namTab) throws SQLException{
        boolean val;
        this.objtConsultSql.setConsultInsertUserpro( campSql , namTab);
        String sqlInser = this.objtConsultSql.getConsultInsertUserpro();
        
        try( Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)){
            
            this.prepstatemnt = conexion.prepareStatement(sqlInser);
            
            for(int i = 0; i < campSql.length; i++){
                val = valStrOrInt(valCampSql[i]);
                if(val){
                  this.prepstatemnt.setInt((i+1), Integer.parseInt(valCampSql[i]));  
                }else{
                  this.prepstatemnt.setString((i+1), valCampSql[i]);
                }
            }
            
            int valfilas = this.prepstatemnt.executeUpdate();
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
    public String[][] getResultConsult(String[] campSql, String[] colVal, String[] colValue, String nameTab) throws SQLException{
        String[][] data = null;
        int cont = 0;
        this.objtConsultSql.setConsultALLUserpro(campSql, colVal, nameTab);
       
        try(Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)) {
            
            this.prepstatemnt = conexion.prepareStatement(this.objtConsultSql.getConsultALLUserpro());
            
            for(int i = 0; i < colVal.length; i++){
               if(valStrOrInt(colVal[i])){
                   System.out.println(colValue[i]);
                   this.prepstatemnt.setInt((i+1), Integer.parseInt(colValue[i]));
               }else{
                   this.prepstatemnt.setString((i+1), colValue[i]);
               }
            }
            
            this.resultsetConSQL = prepstatemnt.executeQuery();
            this.resultsetConSQL.next();
            String a = "";
            
             do {
                for(int i = 0; i < campSql.length; i++){
                    a += this.resultsetConSQL.getString(campSql[i]);
                    if(i < campSql.length - 1){
                        a += ",";
                    }
                }
                cont += 1;
                a += ".";
                
            } while (this.resultsetConSQL.next());
             
             String[] dataS = a.split("."); 
             System.out.println(dataS[1]);
             String[] conten;
             data = new String[campSql.length][cont];
             for(int i = 0; i < data.length; i++){
                conten = dataS[i].split(",");
                 System.out.println(dataS[i]);
                for(int j = 0; j < data[i].length; j++){
 //                  System.out.println(conten[j]);
                   data[i][j] = conten[j];
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
        return data;
    }
    public ResultSet getResultUpdate(String[] colUpd, String[] colVal, String[] colKey,String[] colValKey, String nametab ) throws SQLException{
        
        this.objtConsultSql.setConsultUpdateUserpro(colUpd,colKey,colValKey,nametab);
        
        try(Connection conexion = DriverManager.getConnection(this.url,this.user,this.pass)){
            
          this.prepstatemnt = conexion.prepareStatement(this.objtConsultSql.getConsultUpdateUserpro());
          
          for(int i = 0; i < colUpd.length; i++){
              if(valStrOrInt(colVal[i])){
                this.prepstatemnt.setInt((i+1), Integer.parseInt(colUpd[i]));
              }else{
                this.prepstatemnt.setString((i+1), colVal[i]);
              }
          }
          
          this.resultsetUpd = this.prepstatemnt.executeQuery();
          this.resultsetUpd.next();
          
 
          prepstatemnt.close();
          conexion.close();
        }catch(SQLException ex){
            if(ex instanceof java.sql.SQLRecoverableException){
                System.err.println("Error SQlRecoverableException"+ ex.getMessage());
            }else{
                System.err.println("Error al ejecutar la consulta:"+ ex.getMessage());
            }
            Logger.getLogger(ConsultDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return this.resultsetUpd;
    }
    
    public ResultSet getResultDelect(String[] colKeys, String[] colValu, String nametab) throws SQLException{
        try(Connection conexion = DriverManager.getConnection(this.url, this.user, this.pass)){
            objtConsultSql.setConsultDeleteUserpro(colKeys, nametab);
            System.out.println(objtConsultSql.getConsultDeleteUserpro());
            this.prepstatemnt = conexion.prepareStatement(objtConsultSql.getConsultDeleteUserpro());
            
            for(int i = 0; i < colValu.length; i++){
                if(valStrOrInt(colValu[i])){
                    this.prepstatemnt.setInt((i+1), Integer.parseInt(colValu[i]));
                }else{
                    this.prepstatemnt.setString((i+1), colValu[i]);
                }
            }
            
            this.resultsetDel = prepstatemnt.executeQuery();
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
    public String[] getColKey(String namtab) throws SQLException{
        String[] tabKey = null;
        String data = "";
        try(Connection conexion = DriverManager.getConnection(this.url, this.user, this.pass)){
            this.objtConsultSql.setConsulTabKey(namtab);
            String consulSQL  = this.objtConsultSql.getConsulTabKey();
            this.prepstatemnt = conexion.prepareStatement(consulSQL);
            this.resultsetConTabKey = this.prepstatemnt.executeQuery();
            this.resultsetConTabKey.next();
            
            do{
               data += this.resultsetConTabKey.getString("column_name")+",";
               
            }while(this.resultsetConTabKey.next());
            
            this.prepstatemnt.close();
            conexion.close();
            
            if(data != ""){
               tabKey = data.split(",");
            }else{
                System.out.println("no existen col clave");
                System.exit(0);
            }
        }
        catch(SQLException ex){
           if(ex instanceof java.sql.SQLRecoverableException){
             System.err.println("Error SQlRecoverableException"+ ex.getMessage());
           }else{
              System.err.println("Error al ejecutar la consulta:"+ ex.getMessage());
           }
          Logger.getLogger(ConsultDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tabKey;
    }
    
}    


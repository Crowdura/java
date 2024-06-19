/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackConsult;

/**
 *
 * @author cuerv
 */

// Creación de consultas dinamicas para la base de datos;
public class ConsultSQl {
    
    private String SQLconsultALL;
    private String SQLconsultInsert;
    private String SQLconsultUpdate;
    private String SQLconsultDelet;
    private String SQLconsultDataTabs;
    private String SQlconsultTabCol;
    private String SQLconsultKeyTab;
    private String result;
    
    public void setConsultALLUserpro( String[] campSql,String tableSql, String[][] consultMatriz){
        String conSqli = "";
        String campSqli = "";
        for (int j = 0; j< campSql.length; j++)  {
            campSqli += campSql[j];
            if(j < campSql.length - 1){
              campSqli += ",";
            }
        }
        for(int i = 0; i < consultMatriz.length; i++){
            for(int j = 0; j < consultMatriz[i].length; j++){
                if(i != 0){
                    conSqli += consultMatriz[i][j];
                }
                if(i < consultMatriz.length - 1){
                    conSqli += " ";
                };
            }
        }
        result = "SELECT "+campSqli+" FROM "+tableSql+" WHERE"+conSqli;
     
        this.SQLconsultALL = result;
    }
    
    public void setConsultInsertUserpro(String[] capmSql ,String tableSql ){
        String conSqli = "";
        String campSqli = "";
        conSqli += "VALUES(";
        for(int j = 0; j < capmSql.length; j++){
           campSqli += capmSql[j];
           if( j < capmSql.length - 1 ){
              campSqli += ",";           
           }
           conSqli += "?";
           if( j < capmSql.length - 1 ){
            conSqli +=  ",";  
           } 
        }
        conSqli += ")";
        
        result = "INSERT INTO "+tableSql+"("+campSqli+")"+conSqli;
        this.SQLconsultInsert = result;
    }
    
    public void setConsultUpdateUserpro(String[] colSql,String[] colSqlKey,String[] colSqlKeyVal ,String nameTab){
        String colSet = "";
        String conSqli = "WHERE ";
        
        for(int i = 0; i < colSql.length; i++){
            colSet += colSql[i]+" = ? ";
        }
        for(int j = 0; j < colSqlKey.length; j++){
            conSqli += colSqlKey[j]+" = "+colSqlKeyVal[j]; 
        } 
        result = "UPDATE "+nameTab+" SET "+colSet+" "+conSqli;
        System.out.println(result);
        this.SQLconsultUpdate = result;
    }
    
    public void setConsultDeleteUserpro(String[] campSql,String tableSql, String[][] consultMatriz){
        String conSqlI = "WHERE ";
        
        result = "DELETE "+tableSql+" "+conSqlI;
        this.SQLconsultDelet = result;
    }
    public void setConsultTable(String userData){
        result = "SELECT table_name "
                + "FROM all_tables "
                + "WHERE owner = "+"'"+userData+"'";
        this.SQLconsultDataTabs = result;
    }
    public void setConsultTableCol(String UserData, String[] namTabs){
        String conSQl = "";
        String conculmSQL = "";
        for(int i = 0; i < namTabs.length; i++){
            conculmSQL += " CASE table_name"
                          +" WHEN '"+namTabs[i]+"' THEN column_name"
                          +" END as col"+namTabs[i].toLowerCase()+" ";
            if(i < namTabs.length - 1){
                conculmSQL += ",";
            }
            switch (i) {
                case 0:
                    conSQl = "WHERE table_name = '"+namTabs[i]+"' ";
                    break;
                default:
                    conSQl += "OR table_name = '"+namTabs[i]+"'";
                    break;
            }
        }
            
        result = "SELECT column_name, table_name,"
                + conculmSQL
                + "FROM all_tab_columns "
                + conSQl;
        this.SQlconsultTabCol = result;
    }
    public void setConsulTabKey(String namTab){
        String conSql = "";
        
        conSql = "SELECT column_name "
                +"FROM user_cons_columns "
                + "WHERE constraint_name = ( "
                + "SELECT constraint_name "
                + "FROM user_constraints "
                + "WHERE table_name = '"+namTab+"' "
                + "AND constraint_type = 'P' )";
        
        this.SQLconsultKeyTab = conSql;
    }
    
    public String getConsulTabKey(){
       return this.SQLconsultKeyTab; 
    }
    
    public String getConsultTab(){
        return this.SQLconsultDataTabs;
    }

    public String getConsultTabCol(){
       return this.SQlconsultTabCol;
    };
    
    public String getConsultALLUserpro(){
        return this.SQLconsultALL;
    }
    
    public String getConsultInsertUserpro(){
        return this.SQLconsultInsert;
    }    
    public String getConsultUpdateUserpro(){
        return this.SQLconsultUpdate;
    }
    public String getConsultDeleteUserpro(){
        return this.SQLconsultDelet;
    }
    
}

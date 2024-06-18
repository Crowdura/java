/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Packinterface;

/**
 *
 * @author cuerv
 */
import PackConsult.ConsultDataBase;
import java.sql.SQLException;
import java.util.Scanner;
        
public interface UserInterface {
    public ConsultDataBase objetDatabase = new ConsultDataBase();
    
    public default String[] getTablesLect() throws SQLException{
        String[] dataTabs = objetDatabase.getResultTab();
        return dataTabs;
    } 
    public default String[][] getTablecolumn(String[] nameTab) throws SQLException{
        String[][] colTabs = objetDatabase.getResutColum(nameTab);
        return colTabs;
    }
    public default Boolean getinsertLec() throws SQLException{
        objetDatabase.getResultInsert();
        return true;
    };
    public default boolean getupdateLec() throws SQLException{
        objetDatabase.getResultUpdate();
        return true;
    };
    public default boolean getdeleteLec() throws SQLException{
        objetDatabase.getResultDelect();
        
        return true;
    };
    public default String[][] getconsultLec() throws SQLException{
        
       String[][] data = null;
       String [] campSql = null;
            
       
       objetDatabase.getResultConsult(campSql, "USERPRO", data);
       return data;
    };
}

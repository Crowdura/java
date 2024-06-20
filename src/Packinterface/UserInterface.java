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
    public default String[] getTableKeys(String nameTab) throws SQLException{
        String[] colKeys = objetDatabase.getColKey(nameTab);
        return colKeys;
    }    
    public default Boolean getinsertLec(String[] InfTabCol, String[] valData, String namTab) throws SQLException{
        objetDatabase.getResultInsert(InfTabCol,valData, namTab);
        return true;
    };
    public default boolean getupdateLec(String[] colUpd,String[] colVal,String[] colKey,String[] colValKey, String namTab) throws SQLException{
        objetDatabase.getResultUpdate(colUpd, colVal, colKey, colValKey, namTab);
        return true;
    };
    public default boolean getdeleteLec(String[] colKeys, String[] colVal, String nameTab) throws SQLException{
        objetDatabase.getResultDelect(colKeys, colVal, nameTab);
        
        return true;
    };
    public default String[][] getconsultLec(String[] campSQl,String[] colVal,String[] colValue, String nameTab) throws SQLException{
       String[][] data = objetDatabase.getResultConsult(campSQl ,colVal, colValue, nameTab);
       return data;
    };
}

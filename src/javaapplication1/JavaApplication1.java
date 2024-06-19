/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author cuerv
 */
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import Packinterface.UserInterface;
import PackVal.valData;
import java.util.Scanner;

public class JavaApplication1 implements UserInterface{
    //   Proceso de selección de operación
    private Scanner scanner = new Scanner(System.in);
    public static JavaApplication1 objeto = new JavaApplication1();
    public static valData objetoVal = new valData();
    private static int opci = 0;
    private String[] infTabCop;
    public void resetP() throws SQLException{
        consulTab();
        System.out.print("Elija la opción a procesar en el usuario ");
        System.out.println("Inserta: 1| Update: 2| Delecte: 3| Consultar: 4");
        opci = scanner.nextInt();
                switch (opci) {
            case 1:
                insert();
                break;
            case 2:
                update();
                break;
            case 3:
                delect();
                break;
            case 4:
                consul();
                break;
            default:
                System.out.println("Oprima una opción correcta");
                objeto.resetP();
        }  
    }
    
    public void insert() throws SQLException{
        String dataInftabS = ""; 
        String[] dataInftabA;
        String[] dataColA;
        System.out.println("Ingrese la tabla ha implementar el regitro:");
        String nambTab = scanner.next().toUpperCase();
        for (String infTabCop1 : infTabCop) {
             if(infTabCop1.contains(""+nambTab+"")){
                 dataInftabS = infTabCop1.replaceAll(nambTab+" ", "");
                 break;
             }
        }
        dataInftabS = dataInftabS.replaceAll(nambTab+": ", "");
        dataInftabA = dataInftabS.split(" ");
        System.out.println("Ingrese los datos de los correspondientes campos");
        dataColA = new String[dataInftabA.length];
        
        for(int i = 0; i < dataInftabA.length; i++){
            System.out.println(dataInftabA[i]+":");
            dataColA[i] = scanner.next();
        }
        
        objeto.getinsertLec(dataInftabA, dataColA, nambTab);
    }
 // actualización dinaminaca   
    public void update() throws SQLException{
        String[] dataInftabA;
        String[] colKeys;
        String[] colUpd;
        String[] colKeysVar;
        String valStrDelect = "";
        String dataInftabS = ""; 
        
        System.out.println("Ingrese la tabla a la que actualizara el registro");
        String namTab = scanner.next().toUpperCase();
        if(namTab == ""){
          return;
        }else{
          colKeys = objeto.getTableKeys(namTab);
          System.out.println(colKeys[0]);
        }
        System.out.println("Existen la siguientes keys en la tabla");
        for(int i = 0; i < colKeys.length; i++){
            System.out.println(colKeys[i]);
        }
        System.out.println("indique cuales keys utilizar con una ,");
        String[] colKeyV = scanner.next().split(",");
        colKeysVar = new String[colKeyV.length];
        
        System.out.println("Especifique el valor de cada una de las llaves para la busqueda");
        for(int i = 0; i < colKeyV.length; i++){
            if(objetoVal.valFindArr(colKeys, colKeyV[i])){
               System.out.println(colKeyV[i].toUpperCase()+":");
               colKeysVar[i] = scanner.next();
            }else{
               valStrDelect += colKeyV[i]+",";
               System.out.println("No existe esta columna como llave "+colKeyV[i]);
            }
        }
        
        colKeysVar = objetoVal.valRemovNull(colKeysVar);
 
        colKeyV = objetoVal.valRemovArr(colKeyV, objetoVal.valRemovNull(valStrDelect.split(",")));
        
        if(colKeyV.length == 0){
            System.out.println("Minimo debe de existir un registros");
            System.exit(0);
        }
        
        System.out.println("Ingrese los campos a actualizar Seguidos de una ,");
        dataInftabA = scanner.next().toUpperCase().split(",");
        dataInftabA = objetoVal.valRemovArr(dataInftabA, colKeys);
        if(dataInftabA.length == 0){
            System.out.println("No sea seleccionado una campo por favor recuerde que no se puede modificar campos de tipo keys");
            System.exit(0);
        }
        System.out.println("Ingrese los valores a remplazar");
        colUpd = new String[dataInftabA.length];
        
        for(int i = 0; i < dataInftabA.length; i++){
            System.out.println(dataInftabA[i]+":");
            colUpd[i] = scanner.next();
        }
        
        objeto.getupdateLec(dataInftabA, colUpd, colKeyV, colKeysVar, namTab);
    }
    
    public void delect() throws SQLException{
        objeto.getdeleteLec();
    }
    
    public void consul() throws SQLException{
        objeto.getconsultLec();
    }
    
    public void consulTab() throws SQLException{
        String inf = "";
        System.out.println("Estas son las tablas con campos existentes que estan en la base de datos para tener en cuenta para hacer la crud;");
        String[] tabsExData = objeto.getTablesLect();
        String[][] columns = objeto.getTablecolumn(tabsExData);
        infTabCop = new String[tabsExData.length];
        for(int i = 0; i < columns.length; i++){
            inf  = "";
            inf += tabsExData[i]+": ";
            for(int j = 0; j < columns[i].length; j++){
                inf += columns[i][j]+" ";
            }
            infTabCop[i] = inf.replaceAll("null", "");
            System.out.println(inf.replaceAll("null ", ""));
        }
    }
    
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // TODO code application logic here
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
        objeto.resetP();
        
    }
}

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
import java.util.Scanner;

public class JavaApplication1 implements UserInterface{
    //   Proceso de selección de operación
    private Scanner scanner = new Scanner(System.in);
    public static JavaApplication1 objeto = new JavaApplication1();
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
    
    public void update() throws SQLException{
        objeto.getupdateLec();
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

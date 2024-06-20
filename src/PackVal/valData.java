/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackVal;

/**
 *
 * @author cuerv
 */
import java.util.Arrays;
import java.util.ArrayList;
public class valData {
    public boolean valStrOrInt(String valuVal){
      boolean val;
      if(valuVal.matches(".*[0-9].*")){
        val = true;
      }else{
        val = false;  
      }
      return val;
    }
    public boolean valFindVal(String[] valArr, String camVal){
     boolean val = Arrays.asList(valArr).contains(camVal.toUpperCase());
     return val;
    }
    public String[] valExistArr(String[] arry, String[] ArrVal){
     ArrayList<String> lista = new ArrayList<>(Arrays.asList(ArrVal));
     ArrayList<String> lista2 = new ArrayList<>(Arrays.asList(arry));
     
      for(String element : arry){
          if(lista.contains(element)){
          }else{
            System.out.println("No existe la columna "+element);
            lista2.remove(element);
          }
      }
     return arry = lista2.toArray(new String[0]);
    }
    
    public String[] valRemovNull(String[] arry){
      Arrays.asList(arry).remove("null");
      return arry;
    }
    public String[] valRemovVal(String[] arry, String val){
      Arrays.asList(arry).remove(val);
      return arry;
    }
    public String[] valRemovArr(String[] arry, String[] ArrVal){
      ArrayList<String> lista = new ArrayList<>(Arrays.asList(arry));
      for(String element : ArrVal){
          if(lista.contains(element)){
            lista.remove(element);
          }   
      }
      return arry = lista.toArray(new String[0]);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PackVal;

/**
 *
 * @author cuerv
 */
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
}

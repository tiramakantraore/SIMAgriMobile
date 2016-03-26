/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
import java.util.*;
public class RegisterSender {
    private String nom=""; 
   
    private String numSMS;
    private String messageSMS;
    private String tokenSeparator=" ";
    private LabelAndCodeMapper myMap;
    private SMSSender smsSender;
    RegisterSender(LabelAndCodeMapper theMap, SMSSender psmsSender){
     numSMS=psmsSender.getNumeroSMS();  
     myMap=theMap;
     smsSender=psmsSender;
    }
  

   public void buildMessage(){
     
      String separator=",";
        messageSMS="setuser"+tokenSeparator+"#"+nom;  
    }  
  
  public void setNom(String value){
        nom=value;
    }

      public boolean messageSizeIsOK() {
           buildMessage();
        return (messageSMS.length()<=160); 
      } 
  public String getNom(){
        return nom;
    }  
  public String getMessageEnvoyeText(){
      sendMessage();
      return "Votre message a ete envoyee au numero "+numSMS+" requete codee : "+messageSMS;
  }
  public void sendMessage(){
     buildMessage();  
     smsSender.sendMessage(messageSMS);
  }
  public String  getText(){
    
             return " Votre requête d'inscription de ["+nom+"] : au numero "+numSMS+" taille (en caractères) : "+messageSMS.length();
         
    }  
}


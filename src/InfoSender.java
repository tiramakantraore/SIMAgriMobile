/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
import java.util.*;
public class InfoSender {
    private String infos=""; 
    private List lastList;
    private String numSMS;
    private String messageSMS;
    private String tokenSeparator=" ";
    private LabelAndCodeMapper myMap;
    private SMSSender smsSender;
    InfoSender(LabelAndCodeMapper theMap, SMSSender psmsSender){
     numSMS=psmsSender.getNumeroSMS();  
     myMap=theMap;
     smsSender=psmsSender;
    }
     public void setLastList(List value){
        lastList=value;
    }
     public List getLastList(){
         return lastList;
     } 
     

   public void buildMessage(){
     
      String separator=",";
        messageSMS="setinfos"+tokenSeparator+"#"+infos;  
    }  
  
  public void setInfos(String value){
        infos=value;
    }

      public boolean messageSizeIsOK() {
           buildMessage();
        return (messageSMS.length()<=160); 
      } 
  public String getInfos(){
        return infos;
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
    
             return " Votre requête d'envoie d'information ["+infos+"] : au numero "+numSMS+" taille (en caractères) : "+messageSMS.length();
         
    }  
}

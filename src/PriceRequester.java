/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.microedition.lcdui.List;

/**
 *
 * @author Tiramakan
 */
//import java.util.*;
public class PriceRequester {
    private String marche="";
    private String category="";
    private String produit=""; 
    private String requestChoice="";
    private String numSMS="";
    private String messageSMS;
    private String mesure=""; 
    private List lastList;
    private SMSSender smsSender;
    LabelAndCodeMapper myMap;
    PriceRequester(LabelAndCodeMapper theMap, SMSSender psmsSender){
       numSMS=psmsSender.getNumeroSMS(); 
       myMap=theMap;
       smsSender=psmsSender;
    }
     public void setLastList(List value){
        lastList=value;
    }
     public void buildMessage(){
        String produitCode=myMap.getProduitCode(produit);
        String marcheCode=myMap.getMarcheCode(marche);
        String mesureCode=myMap.getMesureCode(mesure);
         String tokenSeparator = " ";
         if ("".equals(marche)){
           if ("".equals(mesure)){
                messageSMS="getprix"+ tokenSeparator +"#"+produitCode;
             }else                 
                messageSMS="getprix"+ tokenSeparator +"#"+produitCode+"#"+mesureCode;
        }else{
             if ("".equals(mesure)){
              messageSMS="getprix"+ tokenSeparator +"#"+produitCode+"#"+marcheCode;
             }else
          messageSMS="getprix"+ tokenSeparator +"#"+produitCode+"#"+marcheCode+"#"+mesureCode;
        }
        
     
    } 
      public void sendMessage(){
     buildMessage();  
     smsSender.sendMessage(messageSMS);
  }
      public boolean messageSizeIsOK() {
       buildMessage(); 
          return (messageSMS.length()<=160); 
      }
   public void setMesure(String  value){
        mesure=value;
    }
    public String getMesure(){
        return mesure;
    }
     public List getLastList(){
         return lastList;
     } 
     public void setRequestChoice(String value){
        requestChoice=value;
    }
     public String getRequestChoice(){
         return requestChoice;
     } 
    public void setCategory(String value){
        category=value;
    }
     public void setProduit(String value){
        produit=value;
    }
     public String getCategory(){
         return category;
     }
     public String getProduit(){
         return category;
     }
     public void setMarche(String value){
        marche=value;
    }
    public String getMarche(){
         return marche;
     }
   public String getRequestMessage(){ 
      String texteMesure="";
       if (!("".equals(mesure))){
            texteMesure = " évaluée en  "+mesure;
       }
      if ("".equals(marche)){
       return "Demande de prix pour le produit "+produit+" en francs CFA"+texteMesure;
      }else {
          return "Demande de prix pour le produit "+produit+ " dans le marche "+marche+texteMesure;
      }
             
         
    }
     public String getSendedMessage(){    
        sendMessage();
        return "Votre demande de prix a ete envoyee au numéro "+numSMS+" requete codee : "+messageSMS+" taille (en caractères) : "+messageSMS.length();
         
    }
}

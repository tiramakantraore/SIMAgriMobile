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
public class OffreRequester  {  
    private String category="";
    private String produit=""; 
    private String typeOffre="";
    private String numSMS="";
    private String messageSMS;
    private List lastList;
    private String tokenSeparator=" ";
    private LabelAndCodeMapper myMap;
    private SMSSender smsSender;
    OffreRequester(LabelAndCodeMapper theMap, SMSSender psmsSender){
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
   public void sendMessage(){
     buildMessage();  
     smsSender.sendMessage(messageSMS);
    }
    public void setCategory(String value){
        category=value;
    }
    public String getCategory(){
         return category;
     }
  
    
     public void setProduit(String value){
        produit=value;
    }
     
     public String getProduit(){
         return category;
     }
    public void buildMessage(){
        String typeOffreCode=myMap.getTypeOffreCode(typeOffre);
        String produitCode=myMap.getProduitCode(produit);
        messageSMS="getoffre".concat(tokenSeparator).concat("#").concat(typeOffreCode).concat("#").concat(produitCode);
    } 
     
    public void setTypeOffre(String value){
        typeOffre=value;
      }
     
     public String getTypeOffre(){
         return typeOffre;
     }
     
        public boolean messageSizeIsOK() {
           buildMessage();
        return (messageSMS.length()<=160); 
      }  
   
   public String getRequestMessage(){    
        return "Demande ["+typeOffre+"] pour le produit "+produit;
         
    }
     public String getSendedMessage(){    
        sendMessage();
        return "Votre demande d'offre a été envoyée au numero "+numSMS+" requete codée : "+messageSMS+" taille (en caractères) : "+messageSMS.length();
         
    }
}

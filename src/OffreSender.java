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
public class OffreSender {
    private String quantite=""; 
    private String prix="";     
    private String category="";
    private String produit=""; 
    private String requestChoice="";
    private String numSMS="";
    private String mesure="";
    private String categPrix="";
    private String typeOffre="";
    private String note="";
    
    private String tokenSeparator=" ";
    
    private String messageSMS;
    private List lastList;
    private SMSSender smsSender;
    private LabelAndCodeMapper myMap;
    OffreSender(LabelAndCodeMapper theMap, SMSSender psmsSender){
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
     public void setNote(String value){
        note=value;
    }
    public String getNote(){
        return  note;
    }
   public boolean messageSizeIsOK() {
           buildMessage();
        return (messageSMS.length()<=160); 
      }
     public void setRequestChoice(String value){
        requestChoice=value;
    }
      public void sendMessage(){
     buildMessage();  
     smsSender.sendMessage(messageSMS);
  }
    public void buildMessage(){
      String typeOffreCode=myMap.getTypeOffreCode(typeOffre);        
      String textPrixProduits="";
      String separator=",";
      String mesureCode=myMap.getMesureCode(mesure);
      String produitCode=myMap.getProduitCode(produit);
      textPrixProduits+=produitCode+"="+quantite+"/"+mesureCode+"+"+prix+"/"+myMap.getCategPrixCode(categPrix);
      
      messageSMS="setoffre"+tokenSeparator+"#"+typeOffreCode+"#"+textPrixProduits;
       if (!"".equals(note)){
            messageSMS+="__"+note;
       }  
    } 
     public String getRequestChoice(){
         return requestChoice;
     } 
    public void setCategory(String value){
        category=value;
    }
    public String getCategory(){
         return category;
     }
    public String getTitle(){
        return "["+typeOffre+"] de "+produit;
    }
       public void setPrix(String value){
        prix=value;
    }
    public String getPrix(){
         return prix;
     }
    
    public void setCategPrix(String value){
        categPrix=value;
    }
    public String getCategPrix(){
         return categPrix;
     }
    
     public void setProduit(String value){
        produit=value;
    }
     
     public String getProduit(){
         return category;
     }
     
      public void setQuantite(String value){
        quantite=value;
      }
     
     public String getQuantite(){
         return quantite;
     }
    public void setMesure(String value){
        mesure=value;
      }
     
     public String getMesure(){
         return mesure;
     }
     
    public void setTypeOffre(String value){
        typeOffre=value;
      }
     
     public String getTypeOffre(){
         return typeOffre;
     }
     
   public String getSendedMessage(){    
        sendMessage();
        return "Votre pour demande a ete envoyee au numero "+numSMS+" requete codee : "+messageSMS+" taille (en caractères) : "+messageSMS.length();
         
    }  
   
   public String getRequestMessage(){ 
      String textNote="";
       if (!note.equals("")){
           textNote=" Conditions : "+note;  
       }
        return "Envoi d'offre ["+typeOffre+"] pour le produit "+produit+", quantité : "+quantite+" "+mesure+" "+categPrix+" : "+prix+textNote;
         
    }
}


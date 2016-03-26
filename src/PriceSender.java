/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
import javax.microedition.lcdui.List;
import java.util.Vector;
public class PriceSender {
    private String marche=""; 
    private String note="";
    private String categorie=""; 
    private String mesure=""; 
    private Vector produit; 
    private String typePrix=""; 
    private List lastList;
    private String numSMS;
    private String messageSMS;
    private String tokenSeparator=" ";
    private String [] prixProduits;
    private LabelAndCodeMapper myMap;
    private SMSSender smsSender;
    private boolean isAppendingProducts=false;
    PriceSender(LabelAndCodeMapper theMap, SMSSender psmsSender){
     numSMS=psmsSender.getNumeroSMS();  
     myMap=theMap;
     smsSender=psmsSender;
    }
     public void setLastList(List value){
           if (isAppendingProducts) {
          lastList=value;
      } 
        else {
           lastList=value;
      }
        
    }
     public List getLastList(){
         return lastList;
     } 

    public void setIsAppendingProducts(boolean value){
        isAppendingProducts=value;
    } 
    public void setPrixProduits(String [] value){
        prixProduits=new String[value.length];
        prixProduits=value;
    }
     public String [] getPrixProduits(){
         return prixProduits;
     } 
       public int lengthOfProducts(){ 
           if (produit==null)
               return 0;
           else
               return produit.size();
        
      }
       public boolean isInProductList(String value){ 
           if (produit==null)
               return false;
          for (int i = 0; i < produit.size(); i++){
           if (produit.elementAt(i).equals(value)) {
               return true;
           }
           }
         return false; 
      }
        public boolean isInTheList(Vector myVect,String value){ 
           if (myVect==null)
               return false;
          for (int i = 0; i < myVect.size(); i++){
           if (myVect.elementAt(i).equals(value)) {
               return true;
           }
           }
         return false; 
      }
     public Vector getSelectedFlags(Vector selectedItems, Vector unSelectedItems ){

      Vector selectedFlags = new Vector() ;
      Vector myVector=new Vector();
            if (produit!=null){
                  for (int i = 0; i < produit.size(); i++){  
                      int itemPos=myVector.indexOf(produit.elementAt(i).toString());
                      if (itemPos==-1)
                     myVector.addElement(produit.elementAt(i).toString());
                 }
            }
  
         for (int i = 0; i < selectedItems.size(); i++){    
              int itemPos=myVector.indexOf(selectedItems.elementAt(i).toString());
               if (itemPos==-1)
                     myVector.addElement(selectedItems.elementAt(i).toString());
                     
                  }
       for (int i = 0; i < unSelectedItems.size(); i++){
           int itemPos=myVector.indexOf(unSelectedItems.elementAt(i).toString());
             if (itemPos>-1)
               myVector.removeElementAt(itemPos);

          }
     
           
      return myVector;
     } 
     
       public boolean messageSizeIsOK() {
           buildMessage();
        return (messageSMS.length()<=160); 
      }
   public void buildMessage(){
      boolean isPrixDeGros= ("GROS".equals(typePrix));
      String textPrixProduits="";
      String separator=",";
      for (int i=0;i<produit.size();i++) {
          if (isPrixDeGros){
             textPrixProduits+=myMap.getProduitCode(produit.elementAt(i).toString())+"="+prixProduits[i]+"/0"+separator; 
             if (i==produit.size()-2) {
                  separator="";
              }
          }else
          {
             textPrixProduits+=myMap.getProduitCode(produit.elementAt(i).toString())+"="+"0/"+prixProduits[i]+separator; 
             if (i==produit.size()-2) {
                  separator="";
              }  
          }
      }
       if (!"".equals(textPrixProduits)){
            if (!"".equals(mesure)){
               messageSMS="setprix"+tokenSeparator+"#"+textPrixProduits+"#"+myMap.getMarcheCode(marche)+"#"+myMap.getMesureCode(mesure);  
            }else {
            messageSMS="setprix"+tokenSeparator+"#"+textPrixProduits+"#"+myMap.getMarcheCode(marche);  
            }
       }
       if (!"".equals(note)){
            messageSMS+="__"+note;
       }
       
       
               
    }  
    public void setMarche(String value){
        marche=value;
        setIsAppendingProducts(false);
        produit=null;
    }
    public String getMarche(){
         return marche;
     }
   public void setCategorie(String value){
        categorie=value;
    }
    public void setNote(String value){
        note=value;
    }
    public String getNote(){
        return  note;
    }
    public String getCategorie(){
         return categorie;
     }
   public boolean getIsAppendingProducts(){
        return isAppendingProducts;
    } 
    
      public void setTypePrix(String value){
        typePrix=value;
    }
    public String getTypePrix(){
         return typePrix;
     }
  public void setProduit(Vector selected,Vector unselected){
        if (isAppendingProducts) {
          addProduit(selected,unselected);
      } 
        else {
          produit=selected;
      }
    }
  public void addProduit(Vector selected,Vector unselected){
       int indexer=produit.size();
       int valueCounter=0;
       Vector newProductList=new Vector(indexer,1);

          for (int i = 0; i < produit.size(); i++){
              newProductList.addElement(produit.elementAt(i).toString());         
          }
          
            for (int i = 0; i < selected.size(); i++){
             if (!isInProductList(selected.elementAt(i).toString())) {
                 newProductList.addElement(selected.elementAt(i).toString());
             }
         
          }  
             for (int i = 0; i < unselected.size(); i++){
               int indexUnsel= newProductList.indexOf(unselected.elementAt(i).toString());
               if (indexUnsel>0) {
                   newProductList.removeElementAt(indexUnsel);
               }        
          }
        produit=newProductList;
    }

    public void setMesure(String  value){
        mesure=value;
    }
    public String getMesure(){
        return mesure;
    }
  
  public Vector getProduit(){
        return produit;
    }  
  public String getMessageEnvoyeText(){
      sendMessage();
      return "Votre message a ete envoyee au numero "+numSMS+" requete codee : "+messageSMS+" taille (en caractères) : "+messageSMS.length();
  }
  public void sendMessage(){
     buildMessage();  
     smsSender.sendMessage(messageSMS);
  }
  public String  getText(){
      String produitText="";
         for (int i = 0; i < produit.size(); i++){   
              if (prixProduits!=null){
               produitText+=" "+produit.elementAt(i).toString()+" = "+prixProduits[i];
              }
              else{
              produitText+=" "+produit.elementAt(i).toString();    
              }
         }
         if (!note.equals("")) {
          produitText+=" Note : "+note;
      }  
         if (marche.equals("")) {
             return " Votre requête d'envoie de prix ["+typePrix+"] : "+produitText+" au numero "+numSMS;
         }else {
             return " Votre requête d'envoie de prix ["+typePrix+"] : "+produitText+" pour le marché "+marche+" au numero "+numSMS;
         }   
    }  
}

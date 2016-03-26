/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.wireless.messaging.*;
public class SMSSender {
  private String numeroSMS;
    private  MessageConnection Myconn;
  SMSSender(String numSMS)
  {
      if ("".equals(numSMS)){
        numeroSMS="3144" ; 
      }else{
         numeroSMS=numSMS;
      }
     
  }
 public String getNumeroSMS()
 {
     return numeroSMS;
 }
 public void closeConnection(){
     try {
            if (Myconn!=null) {
               Myconn.close(); 
            }
     }
   catch (IOException e) {
     System.out.println(e);  
    }
 }
 
 public void sendMessage(String messageText){
   if (!"".equals(numeroSMS)) { 
       
  try {
      if (Myconn==null) {

            Myconn =(MessageConnection) Connector.open("sms://"+numeroSMS);
            
      }
            if (Myconn!=null) {
                TextMessage txtmessage = (TextMessage) Myconn.newMessage(
                        MessageConnection.TEXT_MESSAGE);
                        txtmessage.setPayloadText(messageText);
                        Myconn.send(txtmessage);   
            }
   }
   catch (IOException e) {
     System.out.println(e);  
    }
   }
 }  
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import javax.microedition.io.Connector;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;
import javax.microedition.rms.RecordStoreException;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;
import java.util.Vector;
//import java.util.List;

/**
 * @author Tiramakan
 */

public class SIMAgriMob extends MIDlet implements CommandListener {
    private List welcomeList,mOffresList,mProductList,mCategorieList,mChoixOpPrixList,mChoixOpOffresList,mChoixTypeOffresList,mAnimalList,mAutresList
            ,mCerealesList,mFibresCotonList,mFruitsList,mIntrantsAgricolesList,mLegumesList,mLegumineusesList,
            mLiquidesEtHuilesList,mOleagineuxList,mRacinesEtTuberculesList,mMarcheList,mMesureList,
            mRequetePrixList,mAnimalMultList,mAutresMultList
            ,mCerealesMultList,mFibresCotonMultList,mFruitsMultList,mIntrantsAgricolesMultList,mLegumesMultList,mLegumineusesMultList,
            mLiquidesEtHuilesMultList,mOleagineuxMultList,mRacinesEtTuberculesMultList,mMarcheMultList,
            mRequetePrixMultList,helpWelcomeList
            ;
    private Command mAideCommand,mEndCommand,mMesureCommand,mHelpEndCommand,
            mSuivantCommand,mQuitterCommand,mPrecedentCommand,
            mEnvoyerCommand,mMarcheCommand,mOKCommand,mNonCommand,mAjoutProduitsCommand; 
     
    private Preferences mPreferences;
    private PriceRequester priceRequest;
    private PriceSender priceSender;
    private InfoSender infosSender;
    private RegisterSender registerSender;
    private Form form;
    private OffreRequester offreRequester;
    private OffreSender offreSender;
    private  MessageConnection conn;
    private ChoiceGroup typePrix,cgMesure,cgMesureOffres,categPrix;
    private int indexMenuGen=0;
    private int indexChoixOpOffreList=0;
    private int indexChoixOpPrixList=0;
    private int indexChoixTypeOffre=0;
    private TextField qteTxtField,priceTxtField,mNomComplet;
    private TextField[] prixProduits;
    private Alert messageAlert,myconfirmAlert;

  //  private static final String kSIMAgriId = "Id";
    private static final String kSMSNumber = "NumeroSMS";
   
    private static final String kIdProduit = "ProductId";
    private static final String kLibelleProduit = "ProductLab";
    private static final String kIdProduitCategory = "CategoryProductId";
    private SMSSender smsSender;
    private static final String[] stringElements = { " Prix", " Offres"," Infos"," Inscription", " Paramètres"};
    private TextField  mSMSField;
    private TextMessage txtmessage;
    private TextBox priceRequestBox,priceRequestSendedBox,SendPriceBox,PriceSendedBox,infosSendedBox,registerSendedBox,senderOffreBox,offreRequestBox,offreSendedBox,offreRequestSendedBox,
            IntroductionBox,Demande_de_prixBox,Mis_en_ligne_de_prixBox,Demande_offresBox,Mis_en_ligne_offresBox,
                ParamètresBox,mPublierInfosBox,notePrix,noteOffres;
    private Form mparametresForm,mPriceSenderForm,mSenderOffreForm,mregisterForm;

    public void buildCommands(){
           mAideCommand = new Command("Aide", Command.HELP, 0);
           mSuivantCommand = new Command("Suivant", Command.SCREEN, 0);
           mPrecedentCommand= new Command("Retour", Command.BACK, 0);
           mEnvoyerCommand= new Command("Envoyer", Command.SCREEN, 0);
           mMarcheCommand= new Command("Marchés", Command.ITEM, 0);
           mAjoutProduitsCommand = new Command("Ajouter produit", Command.ITEM, 0);
           mMesureCommand= new Command("Mesure", Command.ITEM, 0);
           mQuitterCommand = new Command("Quitter", Command.EXIT, 0);  
           mEndCommand = new Command("Fin", Command.SCREEN, 0); 
           mHelpEndCommand = new Command("Fin", Command.SCREEN, 0);
           mOKCommand = new Command("Oui", Command.OK, 0); 
           mNonCommand = new Command("Non", Command.CANCEL, 0); 
           
    }
    public void buildPojo(){
        
            try {
                    mPreferences = new Preferences("preferences");  
                }
               catch (RecordStoreException rse) {
               Form mExceptionForm = new Form("Exception");
               mExceptionForm.append(new StringItem(null, rse.toString()));
               mExceptionForm.addCommand(mQuitterCommand);
               mExceptionForm.setCommandListener(this);
               return;
               }
           
            smsSender= new SMSSender(mPreferences.get(kSMSNumber));
            LabelAndCodeMapper labAndCodeMap=new LabelAndCodeMapper();
           priceRequest=new PriceRequester(labAndCodeMap,smsSender);
           priceSender=new PriceSender(labAndCodeMap,smsSender);
           offreSender=new OffreSender(labAndCodeMap,smsSender);
           offreRequester = new OffreRequester(new LabelAndCodeMapper(),smsSender); 
           infosSender=new InfoSender(labAndCodeMap,smsSender);
           registerSender=new RegisterSender(labAndCodeMap,smsSender);
    }
    public void pauseApp() {
    }
    public ChoiceGroup getTypePrixInstance(){
        String [] typePrixArray={"Prix de gros","Prix de détail"};
       return new ChoiceGroup("Catégorie ",ChoiceGroup.EXCLUSIVE,typePrixArray,null); 
    }
     public ChoiceGroup getCategPrixInstance(){
        String [] categPrixArray={"Prix unitaire","Prix global"};
       return new ChoiceGroup("",ChoiceGroup.EXCLUSIVE,categPrixArray,null);
    }
    public ChoiceGroup getMesureInstance(){
        String [] mesureArray={"Kg         ","Tonne    ","Tine     ","Sac 5kg  ","Sac 25kg ","Sac 50kg ","Sac 100kg","Yoruba   ","Tete     "};
       return new ChoiceGroup("Unité de mesure",ChoiceGroup.POPUP,mesureArray,null);
    }
    public void buildForms(){
        
        typePrix=getTypePrixInstance();
        categPrix=getCategPrixInstance();
        cgMesure=getMesureInstance();
        cgMesureOffres=getMesureInstance();
     
       
//         mIdField = new TextField("Identifiant",
//        mPreferences.get(kSIMAgriId), 32, 0);
        mSMSField = new TextField("Numéro SMS",
        mPreferences.get(kSMSNumber), 32, 0);
        mparametresForm = new Form("Paramètres");
        mparametresForm.append(mSMSField);
        mparametresForm.addCommand(mPrecedentCommand);
        mparametresForm.setCommandListener(this); 
        
        
        mNomComplet=new TextField("Nom complet (Exple: Hien Jules)",
               "", 150, TextField.ANY);
        mregisterForm = new Form("S'inscrire");
        mregisterForm.append(mNomComplet);
        mregisterForm.addCommand(mEnvoyerCommand);
        mregisterForm.addCommand(mPrecedentCommand); 
        mregisterForm.setCommandListener(this); 
        
        
        
       
        mPriceSenderForm= new Form("Mettre en ligne des prix");            
        
        mPriceSenderForm.addCommand(mAjoutProduitsCommand);  
        mPriceSenderForm.addCommand(mSuivantCommand); 
        mPriceSenderForm.addCommand(mPrecedentCommand);
        mPriceSenderForm.setCommandListener(this); 

        mSenderOffreForm= new Form("Mettre en ligne des offres");            
        
        mSenderOffreForm.addCommand(mSuivantCommand); 
        mSenderOffreForm.addCommand(mPrecedentCommand); 
        mSenderOffreForm.setCommandListener(this); 
    }
    private void showAlert(String message) {
      messageAlert.setTimeout(3000);
      messageAlert.setType(AlertType.WARNING);
      messageAlert.setString(message);
//      Display.getDisplay(this).setCurrent(messageAlert);
    }
 
    public void buildTextBox(){
               priceRequestBox = new TextBox("Votre demande de prix", "Demande de prix pour MAIS en francs CFA ", 1000, TextField.ANY|TextField.UNEDITABLE);             
               priceRequestBox.addCommand(mEnvoyerCommand);
               priceRequestBox.addCommand(mPrecedentCommand); 
               priceRequestBox.addCommand(mMarcheCommand);
               priceRequestBox.addCommand(mMesureCommand);
               
               priceRequestBox.setCommandListener(this); 
               
               priceRequestSendedBox= new TextBox("demande de prix envoyée", "Demande de prix pour MAIS en francs CFA ", 1000, TextField.ANY|TextField.UNEDITABLE);             
               priceRequestSendedBox.addCommand(mEndCommand); 
               priceRequestSendedBox.setCommandListener(this); 
               
               PriceSendedBox= new TextBox("Prix envoyée", " ", 1000, TextField.UNEDITABLE);
               PriceSendedBox.addCommand(mEndCommand); 
               PriceSendedBox.setCommandListener(this); 
               
               infosSendedBox= new TextBox("Info envoyée", " ", 200, TextField.UNEDITABLE);
               infosSendedBox.addCommand(mEndCommand); 
               infosSendedBox.setCommandListener(this); 
               
               registerSendedBox= new TextBox("Demande d'inscription envoyée", " ", 200, TextField.UNEDITABLE);
               registerSendedBox.addCommand(mEndCommand); 
               registerSendedBox.setCommandListener(this); 
               
               
                       
               
               senderOffreBox= new TextBox("Votre envoi d' offre", "Envoi d'offre  ", 1000, TextField.UNEDITABLE);
               
               senderOffreBox.addCommand(mEnvoyerCommand);  
               senderOffreBox.addCommand(mPrecedentCommand); 
               senderOffreBox.setCommandListener(this); 
               
               offreRequestBox= new TextBox("Votre demande d' offre", "Demande d'offre  ", 1000, TextField.UNEDITABLE);
               
               offreRequestBox.addCommand(mEnvoyerCommand); 
               offreRequestBox.addCommand(mPrecedentCommand); 
               offreRequestBox.setCommandListener(this);
               
               offreRequestSendedBox= new TextBox("demande envoyée", " ", 1000, TextField.UNEDITABLE);
               offreRequestSendedBox.addCommand(mEndCommand);               
               offreRequestSendedBox.setCommandListener(this);
               
                SendPriceBox= new TextBox("Envoie de prix", "Vous envoyez les prix des produits ", 1000, TextField.UNEDITABLE);
                 
                       
                SendPriceBox.addCommand(mEnvoyerCommand);
                SendPriceBox.addCommand(mPrecedentCommand);
                SendPriceBox.setCommandListener(this);   
                
                offreSendedBox= new TextBox("Offre envoyée", " ", 1000, TextField.UNEDITABLE);
                offreSendedBox.addCommand(mEndCommand); 
                offreSendedBox.setCommandListener(this);
                
                mPublierInfosBox= new TextBox("Saisie information", " ", 160, TextField.ANY);             
                mPublierInfosBox.addCommand(mEnvoyerCommand);
            
                mPublierInfosBox.addCommand(mPrecedentCommand);               
                mPublierInfosBox.setCommandListener(this);
                
                notePrix= new TextBox("Veuillez saisir vos notes", " ", 100, TextField.ANY);             
                notePrix.addCommand(mEnvoyerCommand);
            
                notePrix.addCommand(mPrecedentCommand);               
                notePrix.setCommandListener(this);
                
                noteOffres= new TextBox("Veuillez saisir vos conditions", " ", 100, TextField.ANY);             
                noteOffres.addCommand(mEnvoyerCommand);
            
                noteOffres.addCommand(mPrecedentCommand);               
                noteOffres.setCommandListener(this);
                
                        
                
                IntroductionBox= new TextBox("Introduction à SIMAgriMobile", " ", 1000, TextField.UNEDITABLE);
                IntroductionBox.addCommand(mHelpEndCommand); 
                IntroductionBox.setCommandListener(this);
                
                Demande_de_prixBox= new TextBox("Demande de prix", " ", 1000, TextField.UNEDITABLE);
                Demande_de_prixBox.addCommand(mHelpEndCommand); 
                Demande_de_prixBox.setCommandListener(this);
                
                Mis_en_ligne_de_prixBox= new TextBox("Mise en ligne de prix", " ", 1000, TextField.UNEDITABLE);
                Mis_en_ligne_de_prixBox.addCommand(mHelpEndCommand); 
                Mis_en_ligne_de_prixBox.setCommandListener(this);
                
                Demande_offresBox= new TextBox("Demande d'offres ", " ", 1000, TextField.UNEDITABLE);
                Demande_offresBox.addCommand(mHelpEndCommand); 
                Demande_offresBox.setCommandListener(this);
                
                Mis_en_ligne_offresBox= new TextBox("Mise en ligne d'offres", " ", 1000, TextField.UNEDITABLE);
                Mis_en_ligne_offresBox.addCommand(mHelpEndCommand); 
                Mis_en_ligne_offresBox.setCommandListener(this);
                
                
                ParamètresBox= new TextBox("Paramètres", " ", 1000, TextField.UNEDITABLE);
                ParamètresBox.addCommand(mHelpEndCommand); 
                ParamètresBox.setCommandListener(this);
                
                messageAlert=new Alert("Avertissement !!!");
                
                        
    }
    private void uncheckMultList(){
          boolean[] marcheSelected = new boolean[mMarcheMultList.size()];
          boolean[] cerealesSelected = new boolean[mCerealesMultList.size()];
          mMarcheMultList.setSelectedFlags(marcheSelected);
          mCerealesMultList.setSelectedFlags(cerealesSelected);

    }
    public void buildLists(){
           Image[] imageElements = { loadImage("/prix.png"),
           loadImage("/offres.png"),loadImage("/info.png"), loadImage("/register.png"), loadImage("/parametres.png") };
           welcomeList = new List("Bienvenue sur SIMAgriMob", List.IMPLICIT,
           stringElements, imageElements);          
           welcomeList.addCommand(mAideCommand);
           welcomeList.addCommand(mQuitterCommand);           
           welcomeList.setCommandListener(this);   
 
           Display.getDisplay(this).setCurrent(welcomeList);
           
            String[] categproduitsArray= {"Animal","Autres","Céréales","Fibres & Coton","Fruits",
           "Intrants Agricoles","Légumes","Légumineuses","Liquides & Huiles","Oléagineux & Noix",
              "Racines Et Tubercules"}; 
                mCategorieList = new List("Liste des catégories", List.IMPLICIT,
                categproduitsArray,null);                 
                mCategorieList.addCommand(mPrecedentCommand); 
                mCategorieList.addCommand(mSuivantCommand);
                mCategorieList.setCommandListener(this);  
                
                String[] HelpWelcomeListArray= {"Introduction","Demande de prix","Mise en ligne de prix","Demande d' offres","Mise en ligne d'offres",
                "Paramètres"}; 
                helpWelcomeList = new List("Aide", List.IMPLICIT,
                HelpWelcomeListArray,null);                 
                helpWelcomeList.addCommand(mPrecedentCommand); 
                helpWelcomeList.setCommandListener(this);       
                
                 
            String[] MarcheArray={"Bama","Banfora", "Baskuy","Batié","Bérégadougou","Bitou",
              "Bobo","Boromo",  "Boulsa", "Boussé", "Dano", "Dédougou","Diapaga",
              "Diarabakoko","Diébougou","Djibasso", "Djibo","Dori","Douna","Fada N\'Gourma",
              "Faramana", "Founza","Gaoua","Gassan", "Gayéri","Gorom-Gorom","Goughin",
              "Gouran", "Grand Marché de Nouna", "Guielwango","Houndé", "Kantchari", "Kassoum","Kaya",
              "Kongoussi","Kossodo","Koudougou","Korsimoro","Koupela","Lemourou-Logo", "Léo","Manga", "Ndorola",
              "Niangoloko", "Niassan", "Niéneta","Nouna","Orodara", "Ouahigouya","Pabré","Pama","Pô","Pouytenga",
              "Réo","Sapouy","Samorogouan","Sankaryaré","Sebba","Sideradougou","Siniéna","Sindou","Solenzo","Sono","Soubakaniedougou",
              "Tanghin", "Tenkodogo","Titao","Tougan", "Yako","Zabré","Ziniaré"};
                mMarcheList = new List("Envoi de prix: Choix du marché", List.IMPLICIT,
                MarcheArray,null);                 
                mMarcheList.addCommand(mPrecedentCommand);  
                mMarcheList.addCommand(mSuivantCommand);
                mMarcheList.setCommandListener(this); 
                
                String [] mesureArray={"Kg","Tonne","Tine","Sac 5kg","Sac 25kg","Sac 50kg","Sac 100kg","Yoruba","Tete"};
                
                mMesureList= new List("Choix de la mesure", List.IMPLICIT,
                mesureArray,null);                 
                mMesureList.addCommand(mPrecedentCommand);  
                mMesureList.addCommand(mSuivantCommand);
                mMesureList.setCommandListener(this);
            String[] AnimalArray={
                   "Bélier Bali Bali (Gros)","Bélier Gros", "Bélier","Bélier (Moyen)","Bélier (Petit)",
           "Boeuf (Large, +450kg)","Boeuf (Moyen, 350-450kg)","Boeuf (Petit, 300-350kg)",
           "Viande Avec Os (Boeuf)","Beef","Foie (Boeuf)","Bouc (Gros)", "Bouc (Moyen)",
           "Bouc (Petit)","Brebis (Gros)","Brebis (Moyen)","Brebis (Petit)", "Poisson-Chat",
           "Chevre (Large)","Chevre (Moyen)","Chevre (Petite)", 
           "Chevre (Viande)", "Oeufs","Filet (Boeuf)", "Lait (Chevre)", "Lait", "Miel",
           "Mouton (15-30kg)","Mouton","Perche (Capitaine)","Poisson Fumé", "Porc",
           "Poulet","Rognon","Sabots (Bovins)","Bifteck","Taureau (5-8 Ans)","Jeune Taureau (3-4 Ans)",
           "Taureau Moyen (350-450kg)","Taureau (Petit, 300-350kg)","Tilapia","Vache (100-200kg)",
           "Vache (Gros, +450kg)","Vache (Moyen, 350-450kg)","Vache (Petit, 300-350kg)","Œufs poule de race",
           "Œufs poule local"
               };         
                 
         
                mAnimalList = new List("Liste des produits", List.IMPLICIT,
                AnimalArray,null);                 
                mAnimalList.addCommand(mPrecedentCommand);  
                mAnimalList.addCommand(mSuivantCommand);
                mAnimalList.setCommandListener(this);  
               String[]  AutresArray={
                    "Cacao", "Café", "Gingembre", "Noix De Kola"};    
                 mAutresList = new List("Liste des produits", List.IMPLICIT,
                AutresArray,null); 
                
                mAutresList.addCommand(mPrecedentCommand);
                mAutresList.addCommand(mSuivantCommand);
                mAutresList.setCommandListener(this); 
                String[] CerealesArray={
                    "Ble Grain","Fonio","Farine De Mais","Farine De Mil","Farine De Ble", 
                    "Maïs Blanc", "Maïs Jaune","Maïs Mixte","Mil local",
                    "Niebe Noir", "Riz Importe","Riz Paddy","Riz Décortiqué",
                    "Riz local","Riz Etuvé",                
                     "Sorgho Blanc","Sorgho local","Sorgho Mixte",
                    "Sorgho Rouge"
                }; 
                
                 
         
         
         
                
                mCerealesList = new List("Liste des céréales", List.IMPLICIT,
                CerealesArray,null);             
                mCerealesList.addCommand(mPrecedentCommand);
                mCerealesList.addCommand(mSuivantCommand);
                mCerealesList.setCommandListener(this); 
                
                mCerealesMultList = new List("Liste des céréales", List.MULTIPLE,
                CerealesArray,null);               
                mCerealesMultList.addCommand(mPrecedentCommand);
                mCerealesMultList.addCommand(mSuivantCommand);
                mCerealesMultList.setCommandListener(this); 
                
                
                String[] FibresCotonArray={
                "Semences De Coton"
                    } ;
   
                mFibresCotonList= new List("Liste des fibres & coton", List.IMPLICIT,
                FibresCotonArray,null); 
                mFibresCotonList.addCommand(mPrecedentCommand);
                mFibresCotonList.addCommand(mSuivantCommand);
                mFibresCotonList.setCommandListener(this); 
                
                String[] FruitsArray={
                "Banane Déssert","Banane (Dessert, Non-Mûre)", "Banane Plantain",
                "Citron Vert", "Mangue (Dure)", "Oranges","Papaye","Anana (Gros)",
                "Fruit De La Passion"
                };
                mFruitsList= new List("Liste des fruits", List.IMPLICIT,
                FruitsArray,null); 
                mFruitsList.addCommand(mPrecedentCommand);
                mFruitsList.addCommand(mSuivantCommand);
                mFruitsList.setCommandListener(this); 
                   
                String[]IntrantsAgricolesArray={
                    "Ammonium De Nitrate [AN]","Ammonium De Nitrate {AN}",
                    "Ammonium De Sulphate [AS]","Chlorure De Potassium (K2O)",
                    "Potassium De Sulphate", "Superphosphate Simple {SSP}",
                    "NPK (Céréale)","Semences De Riz","Semence de sorgho variété Kapelga",
                    "Semence de sorgho variété Sariaso9","Semence de sorgho","Semence de maïs espoir",
                    "Semence de maïs Barka","Semence de maïs Wari","semence de riz Local (Nérika)",
                    "semence de riz Local Farako-Bâ riz (FKR34)","semence de riz local (62N)",
                    "semence de riz local (TS2)","semence R1 de riz (Gambiaca)","Urée"
                    };
         
                mIntrantsAgricolesList= new List("Liste des intrants agricoles", List.IMPLICIT,
                IntrantsAgricolesArray,null);                 
                mIntrantsAgricolesList.addCommand(mPrecedentCommand);
                mIntrantsAgricolesList.addCommand(mSuivantCommand);
                mIntrantsAgricolesList.setCommandListener(this); 
                
                 String[]LegumesArray={
                    "Aubergine", "Chou-Fleur", "Chayote", "Carottes",
                    "Choux", "Concombre", "Gombo",  "Oignon (Blanc, Gros)",
                    "Echalote",  "Oignon (Jaune)", "Oignon (Rouge)",
                    "Oignon (Violet)","Piments","Poivron (Paprika)",
                    "Laitue", "Tomates (Cuisson)","Tomate (Industrielle)"
                    }; 
                
                mLegumesList= new List("Liste des légumes", List.IMPLICIT,
                LegumesArray,null); 
                mLegumesList.addCommand(mPrecedentCommand);
                mLegumesList.addCommand(mSuivantCommand);
                mLegumesList.setCommandListener(this);
                
               String[] LegumineusesArray={
                    "Niebe Blanc",
                    "Niebe Rouge",                    
                    "Niebe Noir"
                    };

                mLegumineusesList= new List("Liste des légumineuses", List.IMPLICIT,
                LegumineusesArray,null);                 
                mLegumineusesList.addCommand(mPrecedentCommand);
                mLegumineusesList.addCommand(mSuivantCommand);
                mLegumineusesList.setCommandListener(this);
                
                String[] LiquidesEtHuilesArray={
                "Huile De Palme (Rouge)"
                };
                mLiquidesEtHuilesList= new List("Liste des liquides et huiles", List.IMPLICIT,
                LiquidesEtHuilesArray,null); 
                
                mLiquidesEtHuilesList.addCommand(mPrecedentCommand);
                mLiquidesEtHuilesList.addCommand(mSuivantCommand);
                mLiquidesEtHuilesList.setCommandListener(this);
                
               String[] OleagineuxArray={
                    "Amande De Palme","Amande de Karité","Arachide (Coque)","Arachide Graine",
                    "Arachide (Bouche)"
                     ,"Noix De Palme", "Sésame",
                    "Soja","Huile Arachide", "Beure De Karité",
                    "Noix De Karité (Non Décortiquées)",
                    "Noix De Cajou (Non Décortiquées)","Noix De Coco (Sec)"
                    };
                mOleagineuxList= new List("Liste des produits", List.IMPLICIT,
                OleagineuxArray,null);                 
                mOleagineuxList.addCommand(mPrecedentCommand);
                mOleagineuxList.addCommand(mSuivantCommand);
                mOleagineuxList.setCommandListener(this);
                
                String[]RacinesEtTuberculesArray={
                "Igname","Manioc (Gari)","Manioc (Cossettes)",
                "Manioc (Farine)","Manioc (Tubercules Frais)", "Patates (Rouge)","Pomme De Terre (Irlandaise, Importee)",
                "Pommes De Terre Irlandaises (Locales)","Taro","Pomme De Terre Importée",
                "Pommes De Terre Locales"
                };
                
         
                mRacinesEtTuberculesList= new List("Liste des produits", List.IMPLICIT,
                RacinesEtTuberculesArray,null);                 
                mRacinesEtTuberculesList.addCommand(mPrecedentCommand);
                mRacinesEtTuberculesList.addCommand(mSuivantCommand);
                mRacinesEtTuberculesList.setCommandListener(this);
                
                
                                
                mMarcheMultList = new List("Envoi de prix: Choix du marché", List.MULTIPLE,
                MarcheArray,null);                 
                mMarcheMultList.addCommand(mPrecedentCommand);  
                mMarcheMultList.addCommand(mSuivantCommand);
                mMarcheMultList.setCommandListener(this); 
        
                mAnimalMultList = new List("Liste des produits", List.MULTIPLE,
                AnimalArray,null);                 
                mAnimalMultList.addCommand(mPrecedentCommand);  
                mAnimalMultList.addCommand(mSuivantCommand);
                mAnimalMultList.setCommandListener(this);  
               
                 mAutresMultList = new List("Liste des produits", List.MULTIPLE,
                AutresArray,null); 
                
                mAutresMultList.addCommand(mPrecedentCommand);
                mAutresMultList.addCommand(mSuivantCommand);
                mAutresMultList.setCommandListener(this); 
             
                
                mCerealesMultList = new List("Liste des produits", List.MULTIPLE,
                CerealesArray,null);               
                mCerealesMultList.addCommand(mPrecedentCommand);
                mCerealesMultList.addCommand(mSuivantCommand);
                mCerealesMultList.setCommandListener(this); 
               
   
                mFibresCotonMultList= new List("Liste des produits", List.MULTIPLE,
                FibresCotonArray,null); 
                mFibresCotonMultList.addCommand(mPrecedentCommand);
                mFibresCotonMultList.addCommand(mSuivantCommand);
                mFibresCotonMultList.setCommandListener(this); 
                
               
                mFruitsMultList= new List("Liste des produits", List.MULTIPLE,
                FruitsArray,null); 
                mFruitsMultList.addCommand(mPrecedentCommand);
                mFruitsMultList.addCommand(mSuivantCommand);
                mFruitsMultList.setCommandListener(this); 
           
                mIntrantsAgricolesMultList= new List("Liste des produits", List.MULTIPLE,
                IntrantsAgricolesArray,null);                 
                mIntrantsAgricolesMultList.addCommand(mPrecedentCommand);
                mIntrantsAgricolesMultList.addCommand(mSuivantCommand);
                mIntrantsAgricolesMultList.setCommandListener(this); 
          
                mLegumesMultList= new List("Liste des produits", List.MULTIPLE,
                LegumesArray,null); 
                mLegumesMultList.addCommand(mPrecedentCommand);
                mLegumesMultList.addCommand(mSuivantCommand);
                mLegumesMultList.setCommandListener(this);
               
                mLegumineusesMultList= new List("Liste des produits", List.MULTIPLE,
                LegumineusesArray,null);                 
                mLegumineusesMultList.addCommand(mPrecedentCommand);
                mLegumineusesMultList.addCommand(mSuivantCommand);
                mLegumineusesMultList.setCommandListener(this);
                
               
                mLiquidesEtHuilesMultList= new List("Liste des produits", List.MULTIPLE,
                LiquidesEtHuilesArray,null); 
                
                mLiquidesEtHuilesMultList.addCommand(mPrecedentCommand);
                mLiquidesEtHuilesMultList.addCommand(mSuivantCommand);
                mLiquidesEtHuilesMultList.setCommandListener(this);
             
                mOleagineuxMultList= new List("Liste des produits", List.MULTIPLE,
                OleagineuxArray,null);                 
                mOleagineuxMultList.addCommand(mPrecedentCommand);
                mOleagineuxMultList.addCommand(mSuivantCommand);
                mOleagineuxMultList.setCommandListener(this);
               
                mRacinesEtTuberculesMultList= new List("Liste des produits", List.MULTIPLE,
                RacinesEtTuberculesArray,null);                 
                mRacinesEtTuberculesMultList.addCommand(mPrecedentCommand);
                mRacinesEtTuberculesMultList.addCommand(mSuivantCommand);
                mRacinesEtTuberculesMultList.setCommandListener(this);
                
                
                
                
                
                
                String[] ChoixOperationPrixArray={
                "Demande de prix",
                "Mise en ligne de prix"
                };
                mChoixOpPrixList= new List("Choix type d'opération prix", List.IMPLICIT,
                ChoixOperationPrixArray,null);                 
                mChoixOpPrixList.addCommand(mPrecedentCommand);
                mChoixOpPrixList.addCommand(mSuivantCommand);
                mChoixOpPrixList.setCommandListener(this);
               
               String[] ChoixTypeOffreArray={
                "Offre de vente",
                "Offre d'achat"
                };
                mChoixTypeOffresList= new List("Choix du type d'offres", List.IMPLICIT,
                ChoixTypeOffreArray,null);                 
                mChoixTypeOffresList.addCommand(mPrecedentCommand);
                mChoixTypeOffresList.addCommand(mSuivantCommand);
                mChoixTypeOffresList.setCommandListener(this);
                String[] ChoixOperationOffreArray={
                "Demande d' offres",
                "Mise en ligne d'offres"
                    };
                 mChoixOpOffresList= new List("Choix Opération offre", List.IMPLICIT,
                ChoixOperationOffreArray,null);                 
                mChoixOpOffresList.addCommand(mPrecedentCommand);
                mChoixOpOffresList.addCommand(mSuivantCommand);
                mChoixOpOffresList.setCommandListener(this);  
                form = new Form("ConfirmationMIDlet");
    }
    public void startApp() {
           buildPojo();
           buildCommands();       
           buildForms();          
           buildTextBox();   
           buildLists();     
     }
    public void choixMenuGeneral(Command c, Displayable s) {
     if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(s==welcomeList)) {
    indexMenuGen = welcomeList.getSelectedIndex();
    priceRequest.setCategory(welcomeList.getString(indexMenuGen));
    uncheckMultList();
    if (isPriceSelected()) {
     
        Display.getDisplay(this).setCurrent(mChoixOpPrixList);
     
    };
     if (isOffreSelected()) {
     
        Display.getDisplay(this).setCurrent(mChoixOpOffresList);
     
      };
     if (isInfosSelected()) {
     
        Display.getDisplay(this).setCurrent(mPublierInfosBox);
     
      }; 
     if (isParametresSelected()) {
     
        Display.getDisplay(this).setCurrent(mparametresForm);
     
     };
      if (isRegisterSelected()) {
     
        Display.getDisplay(this).setCurrent(mregisterForm);
     
     };
    }; 
      if ((c == mPrecedentCommand )&&(s==mparametresForm)) { 
       Display.getDisplay(this).setCurrent(welcomeList); 
    };
   
   
      if ((c == mPrecedentCommand) &&((s==mChoixOpPrixList)||(s==mChoixOpOffresList))) {
        Display.getDisplay(this).setCurrent(welcomeList);
        }  
     
    };
    public boolean isProduitSelected(Displayable s) {
    return ((s==mAnimalList)|| (s==mAutresList)|| (s==mCerealesList)
              || (s==mFibresCotonList)|| (s==mFruitsList)|| (s==mIntrantsAgricolesList)|| (s==mLegumesList)
              || (s==mLegumineusesList) || (s==mLiquidesEtHuilesList)|| (s==mOleagineuxList)
              || (s==mRacinesEtTuberculesList));    
    }
     public boolean isMultiProduitSelected(Displayable s) {
    return ((s==mAnimalMultList)|| (s==mAutresMultList)|| (s==mCerealesMultList)
              || (s==mFibresCotonMultList)|| (s==mFruitsMultList)|| (s==mIntrantsAgricolesMultList)|| (s==mLegumesMultList)
              || (s==mLegumineusesMultList) || (s==mLiquidesEtHuilesMultList)|| (s==mOleagineuxMultList)
              || (s==mRacinesEtTuberculesMultList));    
    }
    public void choixMenuPrix(Command c, Displayable s) {
         if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(s==mChoixOpPrixList)) {
     indexChoixOpPrixList = mChoixOpPrixList.getSelectedIndex();
    if (indexChoixOpPrixList==0) {
     
        Display.getDisplay(this).setCurrent(mCategorieList);
     
    };
     if (indexChoixOpPrixList==1) {
     
        Display.getDisplay(this).setCurrent(mMarcheList);
     
    }; 
    }; 

     if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(s==mMarcheList) && (isSendingPrice())) { 
       int index = mMarcheList.getSelectedIndex();
      
       priceSender.setMarche(mMarcheList.getString(index));        
       Display.getDisplay(this).setCurrent(mCategorieList);
     
    };
      if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(s==mCategorieList) && (isSendingPrice())) { 
       int index = mCategorieList.getSelectedIndex();
       String categorySelected=mCategorieList.getString(index);
      priceRequest.setCategory(mCategorieList.getString(index));

    if ("Animal".equals(categorySelected)) {
            mAnimalMultList.setTitle("Prix : Liste des animaux");           
            Display.getDisplay(this).setCurrent(mAnimalMultList);
        }
    else              
    if ("Autres".equals(categorySelected)) {
        mAutresMultList.setTitle("Prix : Liste des autres produits");
            Display.getDisplay(this).setCurrent(mAutresMultList);
        }
    else            
    if ("Céréales".equals(categorySelected)) {
        mCerealesMultList.setTitle("Prix : Liste des céréales");
            Display.getDisplay(this).setCurrent(mCerealesMultList);
        }
    else            
    if ("Fibres & Coton".equals(categorySelected)) {
        mFibresCotonMultList.setTitle("Prix : Liste des fibres & coton");
            Display.getDisplay(this).setCurrent(mFibresCotonMultList);
        } 
    else            
    if ("Fruits".equals(categorySelected)) {
        mFruitsMultList.setTitle("Prix : Liste des fruits");
            Display.getDisplay(this).setCurrent(mFruitsMultList);
        } 
      else            
    if ("Intrants Agricoles".equals(categorySelected)) {
        mIntrantsAgricolesMultList.setTitle("Prix : Liste des intrants agricoles");
            Display.getDisplay(this).setCurrent(mIntrantsAgricolesMultList);
        }  
    else            
    if ("Légumes".equals(categorySelected)) {
        mLegumesMultList.setTitle("Prix : Liste des légumes");
            Display.getDisplay(this).setCurrent(mLegumesMultList);
        }  
     else            
    if ("Légumineuses".equals(categorySelected)) {
        mLegumineusesMultList.setTitle("Prix : Liste des légumineuses");
            Display.getDisplay(this).setCurrent(mLegumineusesMultList);
        }  
     else            
    if ("Liquides & Huiles".equals(categorySelected)) {
        mLiquidesEtHuilesMultList.setTitle("Prix : Liste des liquides & huiles");
            Display.getDisplay(this).setCurrent(mLiquidesEtHuilesMultList);
        }  
    else            
    if ("Oléagineux & Noix".equals(categorySelected)) {
        mLiquidesEtHuilesMultList.setTitle("Prix : Liste des oléagineux & noix");
            Display.getDisplay(this).setCurrent(mOleagineuxMultList);
        }  
    else            
    if ("Racines Et Tubercules".equals(categorySelected)) {
        mRacinesEtTuberculesMultList.setTitle("Prix : Liste des racines et tubercules");
            Display.getDisplay(this).setCurrent(mRacinesEtTuberculesMultList);
        }  
          
//       Display.getDisplay(this).setCurrent(mCerealesMultList);
     
    };
    
   if ((c == mPrecedentCommand) && (isMultiProduitSelected(s))&& (isSendingPrice())) { 
       Display.getDisplay(this).setCurrent(mCategorieList); 
   }
    if ((c == mPrecedentCommand) && (s==mCategorieList)&& (isSendingPrice())) { 
       Display.getDisplay(this).setCurrent(mMarcheList); 
   }    
    if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(isMultiProduitSelected(s)) && (isPriceSelected())) {
      
      List productList=(List)s;
      boolean selected[] = new boolean[productList.size()];
      
      // Fill array indicating whether each element is checked 
    
      productList.getSelectedFlags(selected);
      
      int selectedCount=0;
      int unSelectedCount=0;
      int selindexer=0;
      int unselIndexer=0;
      for (int i = 0; i < selected.length; i++){
          if (selected[i]) {
              selectedCount++;
          }
          else
          {
              unSelectedCount++;
          }
      }
      Vector selectedItems= new Vector(selectedCount,1);
      Vector unSelectedItems= new Vector(unSelectedCount,1);
      for (int i = 0; i < productList.size(); i++){
          if (selected[i]) {
              selectedItems.addElement(productList.getString(i));
          } 
          else
          { 
              unSelectedItems.addElement(productList.getString(i));
              
          }
      }
     Vector newItems=new Vector(selectedCount,1);
     
      
     newItems=priceSender.getSelectedFlags(selectedItems,unSelectedItems);
      
      if (newItems.size()>0) {
       mPriceSenderForm.deleteAll();
       prixProduits=new TextField[newItems.size()];
       priceSender.setLastList(productList);     
       priceSender.setProduit(newItems,unSelectedItems); 
      
       mPriceSenderForm.append(typePrix);
       mPriceSenderForm.append(cgMesure);
        for (int i = 0; i < newItems.size(); i++){  
            TextField aTextField=new TextField(newItems.elementAt(i).toString(),
               "", 30, TextField.DECIMAL);
              mPriceSenderForm.append(aTextField);
              prixProduits[i]=aTextField;
          } 
        //Additional line to make your terminal look better at completion!
	
       Display.getDisplay(this).setCurrent(mPriceSenderForm);
      }else
      { 
          showAlert("Vous devez sélectionner au moins un produit ");

      }
     
    }


        if ((c == mPrecedentCommand) && (s==mPriceSenderForm)) {
       Display.getDisplay(this).setCurrent(priceSender.getLastList()); 
   }
   if ((c == mSuivantCommand) && (s==mPriceSenderForm)) { 
       String [] productPrices=new String[prixProduits.length];  
          int indexer=0;
          boolean canContinue=true; 
          for (int i = 0; i < prixProduits.length; i++){   
              canContinue=(!prixProduits[i].getString().equals(""));
              if (!canContinue){
                  
                  break;
              }
              productPrices[indexer]=prixProduits[i].getString();
              indexer++;
           }
           if (canContinue){
                priceSender.setMesure(cgMesure.getString(cgMesure.getSelectedIndex()));
                priceSender.setPrixProduits(productPrices);
                priceSender.setTypePrix(typePrix.getSelectedIndex()==0?"GROS":"DETAIL");

                
                showConfirmation("Confirmation","Voulez-vous ajouter une note ?");
           }else {
               showAlert("Tous les produits sélectionnés doivent avoir leur prix ");
           }
        }; 
        
      if ((c == mAjoutProduitsCommand) && (s==mPriceSenderForm)) { 
          priceSender.setIsAppendingProducts(true);
          Display.getDisplay(this).setCurrent(mCategorieList);
     //  String [] productPrices=new String[prixProduits.length];  
      //    int indexer=0;
       //   boolean canContinue=true; 
//          for (int i = 0; i < prixProduits.length; i++){   
//              canContinue=(!prixProduits[i].getString().equals(""));
//              if (!canContinue){
//                  
//                  break;
//              }
//              productPrices[indexer]=prixProduits[i].getString();
//              indexer++;
//           }
//           if (canContinue){
//                priceSender.setMesure(cgMesure.getString(cgMesure.getSelectedIndex()));
//                priceSender.setPrixProduits(productPrices);
//                priceSender.setTypePrix(typePrix.getSelectedIndex()==0?"GROS":"DETAIL");
//
//                
//                showConfirmation("Confirmation","Voulez-vous ajouter une note ?");
//           }else {
//               showAlert("Tous les produits sélectionnés doivent avoir leur prix ");
//           }
        }; 
     if ((c == mOKCommand) && (s==myconfirmAlert) && (isSendingPrice())) { 
             notePrix.setString("");
             Display.getDisplay(this).setCurrent(notePrix);
        
        };     
       if ((c == mEnvoyerCommand) && (s==notePrix) && (isSendingPrice())) { 
              priceSender.setNote(notePrix.getString());
              SendPriceBox.setString(priceSender.getText());
              Display.getDisplay(this).setCurrent(SendPriceBox);
        
        };  
          if ((c == mPrecedentCommand) && (s==notePrix) && (isSendingPrice())) { 
              priceSender.setNote("");
              Display.getDisplay(this).setCurrent(SendPriceBox);
        
        };  
       if ((c == mNonCommand) && (s==myconfirmAlert) && (isSendingPrice())) { 
      
                priceSender.setNote("");
                Display.getDisplay(this).setCurrent(SendPriceBox);
        
        };     
        
        
     if ((c == mEnvoyerCommand) && (s==SendPriceBox)) { 
         if (priceSender.messageSizeIsOK()) {
           PriceSendedBox.setString(priceSender.getMessageEnvoyeText());
           Display.getDisplay(this).setCurrent(PriceSendedBox);
         }else
         {
            showAlert("La taille du message dépasse les 160 caractères, veuillez le scinder "); 
         }
        };     
        
        
       if ((c == mPrecedentCommand) && (s==PriceSendedBox)) { 
           Display.getDisplay(this).setCurrent(SendPriceBox);
        };          
      if ((c == mPrecedentCommand) && (s==SendPriceBox)) { 
           Display.getDisplay(this).setCurrent(mPriceSenderForm);
        };    
     if ((c == mMarcheCommand) && (s==priceRequestBox)) {
         
        Display.getDisplay(this).setCurrent(mMarcheList);
        };     
     if ((c == mMesureCommand) && (s==priceRequestBox)) {
         
        Display.getDisplay(this).setCurrent(mMesureList);
        };      
        
                
//     if ((c == mPrecedentCommand )&&(s==mCerealesMultList)) { 
//     
//        Display.getDisplay(this).setCurrent(mMarcheList);
//     
//    };
    if ((c == mSuivantCommand  || c == List.SELECT_COMMAND)&&(s==mCategorieList)&& isPriceSelected()
            && isRequestingPrice()) {
    int index = mCategorieList.getSelectedIndex();
    String categorySelected=mCategorieList.getString(index);
    priceRequest.setCategory(categorySelected);

    if ("Animal".equals(categorySelected)) {
            mAnimalList.setTitle("Prix : Liste des animaux");           
            Display.getDisplay(this).setCurrent(mAnimalList);
        }
    else              
    if ("Autres".equals(categorySelected)) {
        mAutresList.setTitle("Prix : Liste des autres produits");
            Display.getDisplay(this).setCurrent(mAutresList);
        }
    else            
    if ("Céréales".equals(categorySelected)) {
        mCerealesList.setTitle("Prix : Liste des céréales");
            Display.getDisplay(this).setCurrent(mCerealesList);
        }
    else            
    if ("Fibres & Coton".equals(categorySelected)) {
        mFibresCotonList.setTitle("Prix : Liste des fibres & coton");
            Display.getDisplay(this).setCurrent(mFibresCotonList);
        } 
    else            
    if ("Fruits".equals(categorySelected)) {
        mFruitsList.setTitle("Prix : Liste des fruits");
            Display.getDisplay(this).setCurrent(mFruitsList);
        } 
      else            
    if ("Intrants Agricoles".equals(categorySelected)) {
        mIntrantsAgricolesList.setTitle("Prix : Liste des intrants agricoles");
            Display.getDisplay(this).setCurrent(mIntrantsAgricolesList);
        }  
    else            
    if ("Légumes".equals(categorySelected)) {
        mLegumesList.setTitle("Prix : Liste des légumes");
            Display.getDisplay(this).setCurrent(mLegumesList);
        }  
     else            
    if ("Légumineuses".equals(categorySelected)) {
        mLegumineusesList.setTitle("Prix : Liste des légumineuses");
            Display.getDisplay(this).setCurrent(mLegumineusesList);
        }  
     else            
    if ("Liquides & Huiles".equals(categorySelected)) {
        mLiquidesEtHuilesList.setTitle("Prix : Liste des liquides & huiles");
            Display.getDisplay(this).setCurrent(mLiquidesEtHuilesList);
        }  
    else            
    if ("Oléagineux & Noix".equals(categorySelected)) {
        mLiquidesEtHuilesList.setTitle("Prix : Liste des oléagineux & noix");
            Display.getDisplay(this).setCurrent(mOleagineuxList);
        }  
    else            
    if ("Racines Et Tubercules".equals(categorySelected)) {
        mRacinesEtTuberculesList.setTitle("Prix : Liste des racines et tubercules");
            Display.getDisplay(this).setCurrent(mRacinesEtTuberculesList);
        }  
      };
      if ((c == mSuivantCommand || c == List.SELECT_COMMAND) && (isProduitSelected(s)) && (isPriceSelected())) {
        List theList=(List)s;
        int index = theList.getSelectedIndex();
        String nomProduit=theList.getString(index);
        priceRequest.setProduit(nomProduit); 
        priceRequest.setLastList(theList);
        priceRequestBox.setString(priceRequest.getRequestMessage());
        Display.getDisplay(this).setCurrent(priceRequestBox);
        } 
       if ((c == mPrecedentCommand) && (isProduitSelected(s)) && (isPriceSelected())) {
           Display.getDisplay(this).setCurrent(mCategorieList);
        } 
      
      if ((c == mPrecedentCommand) && (s==mCategorieList)&& (isPriceSelected())) {
        Display.getDisplay(this).setCurrent(mChoixOpPrixList);
        }  
      if ((c == mPrecedentCommand) && (s==mMarcheList)&& (isPriceSelected())) {
        Display.getDisplay(this).setCurrent(mChoixOpPrixList);
        } 
        if ((c == mPrecedentCommand) && (s==mMesureList)&& (isPriceSelected())) {
        Display.getDisplay(this).setCurrent(mChoixOpPrixList);
        } 
       if ((c == mPrecedentCommand )&&(s==priceRequestBox)) {         
        Display.getDisplay(this).setCurrent(priceRequest.getLastList());     
        };
          if (((c == mSuivantCommand)|| (c == List.SELECT_COMMAND) )&&(s==mMarcheList) && (isRequestingPrice())) { 
          int index = mMarcheList.getSelectedIndex();
          priceRequest.setMarche(mMarcheList.getString(index));  
          priceRequestBox.setString(priceRequest.getRequestMessage());
          Display.getDisplay(this).setCurrent(priceRequestBox);     
        };
        
         if (((c == mSuivantCommand)|| (c == List.SELECT_COMMAND) )&&(s==mMesureList) && (isRequestingPrice())) { 
          int index = mMesureList.getSelectedIndex();
          priceRequest.setMesure(mMesureList.getString(index));  
          priceRequestBox.setString(priceRequest.getRequestMessage());
          Display.getDisplay(this).setCurrent(priceRequestBox);     
        };
        
        
         if ((c == mEnvoyerCommand) &&(s==priceRequestBox) && (isRequestingPrice())) { 
          if (priceRequest.messageSizeIsOK()) {   
          priceRequestSendedBox.setString(priceRequest.getSendedMessage());
          Display.getDisplay(this).setCurrent(priceRequestSendedBox);
          }else
             showAlert("La taille du message dépasse les 160 caractères, veuillez le scinder "); 
        };
        
         if ((c == mPrecedentCommand )&&(s==priceRequestSendedBox)) {         
        Display.getDisplay(this).setCurrent(priceRequestBox);     
        };
    
    }
    
            
    public void  choixMenuInfos(Command c, Displayable s) {
      if ((c == mEnvoyerCommand || c == List.SELECT_COMMAND)&&(s==mPublierInfosBox)) {
       
        infosSender.setInfos(mPublierInfosBox.getString());
      if (infosSender.messageSizeIsOK()) {
        infosSendedBox.setString(infosSender.getMessageEnvoyeText());
        Display.getDisplay(this).setCurrent(infosSendedBox);   
       }else 
           showAlert("La taille du message dépasse les 160 caractères, veuillez le scinder ");   
     
     };  
  

     if ((c == mPrecedentCommand)&&(s==mPublierInfosBox))  {
        Display.getDisplay(this).setCurrent(welcomeList);
        }  
  
    
    }        
    
     public void  choixMenuInscription(Command c, Displayable s) {
      if ((c == mEnvoyerCommand || c == List.SELECT_COMMAND)&&(s==mregisterForm)) {
          registerSender.setNom(mNomComplet.getString());
         if (registerSender.messageSizeIsOK()) {        
        registerSendedBox.setString(registerSender.getMessageEnvoyeText());
        Display.getDisplay(this).setCurrent(registerSendedBox);   
       }else 
           showAlert("La taille du message dépasse les 160 caractères, veuillez le scinder ");   
     
     }


         if ((c == mPrecedentCommand)&&(s==mregisterForm))  {
        Display.getDisplay(this).setCurrent(welcomeList);
        }  
  
    
    }        
    public void choixMenuOffre(Command c, Displayable s) {
      if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(s==mChoixTypeOffresList)) {
        indexChoixTypeOffre = mChoixTypeOffresList.getSelectedIndex();
        offreSender.setTypeOffre(mChoixTypeOffresList.getString(indexChoixTypeOffre));
        offreRequester.setTypeOffre(mChoixTypeOffresList.getString(indexChoixTypeOffre));
     
        
        Display.getDisplay(this).setCurrent(mCategorieList);   
   
      if (c == mPrecedentCommand)  {
        Display.getDisplay(this).setCurrent(welcomeList);
        }  
     };  
     
     if ((c == mSuivantCommand|| c == List.SELECT_COMMAND)&&(s==mChoixOpOffresList)) {
     indexChoixOpOffreList = mChoixOpOffresList.getSelectedIndex();

                Display.getDisplay(this).setCurrent(mChoixTypeOffresList);
     };

     if ((c == mPrecedentCommand)&&(s==mChoixOpOffresList))  {
        Display.getDisplay(this).setCurrent(welcomeList);
        }  
      if ((c == mSuivantCommand || c == List.SELECT_COMMAND)&&(s==mCategorieList)&& isOffreSelected()) {
    int index = mCategorieList.getSelectedIndex();
   
   
    if ("Animal".equals(mCategorieList.getString(index))) {
            mAnimalList.setTitle("Offres : Liste des animaux");
            Display.getDisplay(this).setCurrent(mAnimalList);
        }
    else              
    if ("Autres".equals(mCategorieList.getString(index))) {
            mAutresList.setTitle("Offres : Liste des autres produits");
            Display.getDisplay(this).setCurrent(mAutresList);
        }
    else            
    if ("Céréales".equals(mCategorieList.getString(index))) {
            mAutresList.setTitle("Offres : Liste des céréales");
            Display.getDisplay(this).setCurrent(mCerealesList);
        }
    else            
    if ("Fibres & Coton".equals(mCategorieList.getString(index))) {
            mFibresCotonList.setTitle("Offres : Liste des fibres et cotons");
            Display.getDisplay(this).setCurrent(mFibresCotonList);
        } 
    else            
    if ("Fruits".equals(mCategorieList.getString(index))) {
             mFruitsList.setTitle("Offres : Liste des fruits");
            Display.getDisplay(this).setCurrent(mFruitsList);
        } 
      else            
    if ("Intrants Agricoles".equals(mCategorieList.getString(index))) {
        mIntrantsAgricolesList.setTitle("Offres : Liste des intrants agricoles");
            Display.getDisplay(this).setCurrent(mIntrantsAgricolesList);
        }  
    else            
    if ("Légumes".equals(mCategorieList.getString(index))) {
        mLegumesList.setTitle("Offres : Liste des légumes");
            Display.getDisplay(this).setCurrent(mLegumesList);
        }  
     else            
    if ("Légumineuses".equals(mCategorieList.getString(index))) {
        mLegumineusesList.setTitle("Offres : Liste des légumineuses");
            Display.getDisplay(this).setCurrent(mLegumineusesList);
        }  
     else            
    if ("Liquides & Huiles".equals(mCategorieList.getString(index))) {
         mLiquidesEtHuilesList.setTitle("Offres : Liste des liquides & huiles");
            Display.getDisplay(this).setCurrent(mLiquidesEtHuilesList);
        }  
    else            
    if ("Oléagineux & Noix".equals(mCategorieList.getString(index))) {
        mOleagineuxList.setTitle("Offres : Liste des Oléagineux");
            Display.getDisplay(this).setCurrent(mOleagineuxList);
        }  
    else            
    if ("Racines Et Tubercules".equals(mCategorieList.getString(index))) {
         mRacinesEtTuberculesList.setTitle("Offres : Liste des racines et tubercules");
            Display.getDisplay(this).setCurrent(mRacinesEtTuberculesList);
        } 
    
  
    }
        if ((c == mSuivantCommand || c == List.SELECT_COMMAND) && (isProduitSelected(s)) && isSendingOffre() && isOffreSelected()) {
        List theList=(List)s;
        int index = theList.getSelectedIndex();
        String nomProduit=theList.getString(index);
        offreSender.setProduit(nomProduit); 
        offreSender.setLastList(theList);
       
      mSenderOffreForm.deleteAll(); 
      mSenderOffreForm.setTitle(offreSender.getTitle());   
      qteTxtField=new TextField("Quantité",
               "", 30, TextField.DECIMAL);       
       mSenderOffreForm.append(cgMesureOffres);
       mSenderOffreForm.append(qteTxtField);
       mSenderOffreForm.append(categPrix);
       priceTxtField=new TextField("Prix",
               "", 30, TextField.DECIMAL); 
       mSenderOffreForm.append(priceTxtField);
       Display.getDisplay(this).setCurrent(mSenderOffreForm);
        }
        if ((c == mSuivantCommand || c == List.SELECT_COMMAND) && (isOffreSelected()) && (isProduitSelected(s)) && (isRequestingOffre())) {
       List theList=(List)s;
        int index = theList.getSelectedIndex();
        String nomProduit=theList.getString(index);
       offreRequester.setProduit(nomProduit); 
       offreRequester.setLastList(theList);
       offreRequestBox.setString(offreRequester.getRequestMessage());
       Display.getDisplay(this).setCurrent(offreRequestBox);
       
        } ;
        
        
//          if ((c == mOKCommand) && (s==myconfirmAlert) && (isSendingOffre())) { 
//             Display.getDisplay(this).setCurrent(noteOffres);
//        
//        };     
//       if ((c == mEnvoyerCommand) && (s==noteOffres) && (isSendingOffre())) { 
//              priceSender.setNote(noteOffres.getString());
//               
//        
//        };  
//          if ((c == mPrecedentCommand) && (s==noteOffres) && (isSendingOffre())) { 
//              priceSender.setNote("");
//              Display.getDisplay(this).setCurrent(SendPriceBox);
//        
//        };  
//       if ((c == mNonCommand) && (s==myconfirmAlert) && (isSendingPrice())) { 
//      
//                priceSender.setNote("");
//                Display.getDisplay(this).setCurrent(SendPriceBox);
//        
//        };     
//        
        
        
       if ((c == mEnvoyerCommand ) && (s==offreRequestBox) ) {
       if (offreRequester.messageSizeIsOK()) {
       offreRequestSendedBox.setString(offreRequester.getSendedMessage());
       Display.getDisplay(this).setCurrent(offreRequestSendedBox);
       }else
           showAlert("Votre message depasse 160 caractères, veuillez le scinder");
        }


        if ((c == mPrecedentCommand) && (s==offreRequestBox)&& (isOffreSelected())) {
        Display.getDisplay(this).setCurrent(offreRequester.getLastList());
        }
        if ((c == mPrecedentCommand) && (s==offreRequestSendedBox)) {
        Display.getDisplay(this).setCurrent(offreRequestBox);
        }

        if ((c == mPrecedentCommand) && ((s==mCategorieList)||(s==mMarcheList))&& (isOffreSelected())) {
        Display.getDisplay(this).setCurrent(mChoixOpOffresList);
        };  
     if ((c == mPrecedentCommand) && ((s==mChoixOpPrixList)||(s==mChoixTypeOffresList))) {
        Display.getDisplay(this).setCurrent(welcomeList);
        } ;
    if ((c == mPrecedentCommand)&&(s==senderOffreBox)) {         
        Display.getDisplay(this).setCurrent(offreSender.getLastList());     
        }; 
        
    if ((c == mEnvoyerCommand) &&(s==senderOffreBox) ) { 
         if (offreRequester.messageSizeIsOK()) {
        offreSendedBox.setString(offreSender.getSendedMessage());
        Display.getDisplay(this).setCurrent(offreSendedBox);
         }
         else 
             showAlert("Votre message dépasse 160 caractères, veuillez le scinder");
    }
    
     if ((c == mPrecedentCommand) && (s==offreSendedBox)) {
        Display.getDisplay(this).setCurrent(senderOffreBox);
        };
    if (((c == mSuivantCommand)|| (c == List.SELECT_COMMAND)  )&&(s==mSenderOffreForm) ) { 
       boolean canContinue=true; 
       canContinue=(!qteTxtField.getString().equals("")&& !priceTxtField.getString().equals(""));  
      if (!canContinue){ 
           if (qteTxtField.getString().equals("")) { 
              showAlert("La quantité doit être renseignée ");
          }
           else
              if (priceTxtField.getString().equals("")) {
              showAlert("Le prix doit être renseignée ");
          }                        
      }else{  
             offreSender.setQuantite(qteTxtField.getString());    
             offreSender.setMesure(cgMesureOffres.getString(cgMesureOffres.getSelectedIndex())); 
             offreSender.setCategPrix(categPrix.getString(categPrix.getSelectedIndex())); 
             offreSender.setPrix(priceTxtField.getString());
              if (c == mPrecedentCommand) {
              offreSender.setProduit("");
              } 
             
              showConfirmation("Confirmation","Voulez-vous ajouter des conditions ?");
//              Display.getDisplay(this).setCurrent(senderOffreBox);
      }
     
    }; 
    
       if ((c == mOKCommand) && (s==myconfirmAlert) && (isSendingOffre())) { 
             noteOffres.setString("");
             Display.getDisplay(this).setCurrent(noteOffres);
        
        };            
        if ((c == mEnvoyerCommand) && (s==noteOffres) && (isSendingOffre())) { 

              offreSender.setNote(noteOffres.getString());
              senderOffreBox.setString(offreSender.getRequestMessage());
              Display.getDisplay(this).setCurrent(senderOffreBox);
        
        };  
          if ((c == mPrecedentCommand) && (s==noteOffres) && (isSendingOffre())) { 
              offreSender.setNote("");
              Display.getDisplay(this).setCurrent(senderOffreBox);
        
        };  
       if ((c == mNonCommand) && (s==myconfirmAlert) && (isSendingOffre())) {       
                offreSender.setNote("");
                Display.getDisplay(this).setCurrent(senderOffreBox);
        
        };     
        
        
        
    if (((c == mPrecedentCommand))&&(s==mSenderOffreForm) ) {
        offreSender.setProduit("");
        Display.getDisplay(this).setCurrent(offreSender.getLastList());
    }
    
    }
    public void executeCommon(Command c, Displayable s){
           if ((c == mPrecedentCommand) && (isProduitSelected(s))) {
         
        Display.getDisplay(this).setCurrent(mCategorieList);
        }     
       
     if ((c == mPrecedentCommand) && (s==mPriceSenderForm)) {
         
        Display.getDisplay(this).setCurrent(mCerealesMultList);
        };  
       if (c == mEndCommand) {
         
        Display.getDisplay(this).setCurrent(welcomeList);
        };
        if (c == mAideCommand) {
         
        Display.getDisplay(this).setCurrent(helpWelcomeList);
        };
    
  
  
     if (c == mQuitterCommand) {
            destroyApp(true);
            notifyDestroyed();
        }     
    }
    public void choixMenuAide(Command c, Displayable s) {
        
     if (((c == mSuivantCommand)|| (c == List.SELECT_COMMAND)  )&&(s==helpWelcomeList) ) { 
         List theList=(List)s;
        int helpItem = theList.getSelectedIndex();
        String retourChariot="\n";
        switch(helpItem) {
              case  0:{
                  String textIntroduction="Bienvenue dans l''application SIMAgriMobile. "+retourChariot;
                  textIntroduction+="Cette application a pour but de simplifier l'interaction web-mobile"+retourChariot;
                  textIntroduction+="entre le téléphone portable et la plateforme SIMAgri"+retourChariot;
                  textIntroduction+="Le menu principal est composé de trois (3) menus qui sont : "+retourChariot;
                  textIntroduction+="--- Prix : Permet de recevoir et d'envoyer des prix"+retourChariot;
                  textIntroduction+="--- Offre : Permet de recevoir et d'envoyer des offres"+retourChariot;
                  textIntroduction+="--- Aide : Permet de consulter de l'aide pour utiliser SIMAgriMob "+retourChariot;
                  IntroductionBox.setString(textIntroduction);
                  Display.getDisplay(this).setCurrent(IntroductionBox);
              }
                break;
              case  1:{
                  String textDemPrix="Le menu demande prix permet de composer une requête de demande de prix"+retourChariot;
                  textDemPrix+="Voici le schéma de la requête: categorie produit->produit->envoie requête";
                  Demande_de_prixBox.setString(textDemPrix);

                  Display.getDisplay(this).setCurrent(Demande_de_prixBox);   
                break;
              }
              case  2:{
                  String textEnvoiPrix="Le menu mise en ligne de prix permet de composer une requête de d'envoi de prix pour un produit"+retourChariot;
                  textEnvoiPrix+="Voici le schéma de la requête: categorie produit->produits->Formulaire de saisie des prix->envoie requête";
                  Mis_en_ligne_de_prixBox.setString(textEnvoiPrix);
                  Display.getDisplay(this).setCurrent(Mis_en_ligne_de_prixBox);   
                break;
              }
              case 3:{
                  String textDemOffres="Le menu demande d'offre permet de composer une requête de demande d'offre pour un produit"+retourChariot;
                  textDemOffres+="Voici le schéma de la requête: categorie produit->produit->envoie requête";
                  Demande_offresBox.setString(textDemOffres);
                  Display.getDisplay(this).setCurrent(Demande_offresBox);   
                break;
              }
                  
                  
              case 4:{
                  String textDemOffres="Le menu mise en ligne d'offres permet de composer une requête de mise en ligne d'offre pour un produit"+retourChariot;
                  textDemOffres+="Voici le schéma de la requête: categorie produit->produit->envoie requête";
                  Mis_en_ligne_offresBox.setString(textDemOffres);
                  Display.getDisplay(this).setCurrent(Mis_en_ligne_offresBox);   
                break;
              }
                  
                  
              case 5:{
                  String textDemOffres="Le menu paramètres permet changer le numero de destination des sms"+retourChariot;
                  
                  ParamètresBox.setString(textDemOffres);
                  Display.getDisplay(this).setCurrent(ParamètresBox);   
                break;
              }     
        }           
  
    }; 
    
    if ((c == mHelpEndCommand)&&((s==IntroductionBox)||(s==Demande_de_prixBox)
            ||(s==Mis_en_ligne_de_prixBox)||(s==Demande_offresBox)||(s==Mis_en_ligne_offresBox)
            ||(s==ParamètresBox))) { 
        Display.getDisplay(this).setCurrent(helpWelcomeList);
    }
    
    if ((c == mPrecedentCommand) &&((s==helpWelcomeList))) { 
        Display.getDisplay(this).setCurrent(welcomeList);
    }
    
    }
    public void commandAction(Command c, Displayable s) {
       choixMenuGeneral(c,s);
       choixMenuPrix(c,s);
       choixMenuOffre(c,s);
       choixMenuInfos(c,s);
       choixMenuAide(c,s);
       choixMenuInscription(c,s);
       executeCommon(c,s);  
     }
   public boolean isRequestingPrice(){
        return (indexChoixOpPrixList==0)&& isPriceSelected();
    }
   public boolean isSendingPrice(){
        return (indexChoixOpPrixList==1) && isPriceSelected();
    }
   
   public boolean isRequestingOffre(){
        return (indexChoixOpOffreList==0)&& isOffreSelected();
    }
   public boolean isSendingOffre(){
        return (indexChoixOpOffreList==1) && isOffreSelected();
    }
    public boolean isOffreVente(){
        return (indexChoixTypeOffre==0) && isOffreSelected();
    }
   public boolean isOffreAchat(){
        return (indexChoixTypeOffre==1)&& isOffreSelected();
    }
   public boolean isPriceSelected(){
        return (indexMenuGen==0);
    }
    public boolean isOffreSelected(){
        return (indexMenuGen==1);
    }

    public boolean isInfosSelected(){
        return (indexMenuGen==2);
    } 
   
   public boolean isParametresSelected(){
        return (indexMenuGen==4);
    } 
    public boolean isRegisterSelected(){
        return (indexMenuGen==3);
    } 

    
    public void destroyApp(boolean unconditional) {
        
           
        // Save the user name and password.
           // mPreferences.put(kSIMAgriId, mIdField.getString());
            String saveSMS=mSMSField.getString();
            if ("".equals(saveSMS)) {
                saveSMS="3144";
            }
            mPreferences.put(kSMSNumber, saveSMS);
            try { mPreferences.save(); }
            catch (RecordStoreException rse) {
                showAlert("Impossible d'enregistrer");
            }
            smsSender.closeConnection();
     }
      private void closeAlert() {
        Display.getDisplay(this).setCurrent(form);
        myconfirmAlert = null;        
    }
      protected void showConfirmation(String title, String text) {
        myconfirmAlert = new Alert(title, text, null, AlertType.CONFIRMATION);
        myconfirmAlert.addCommand(mOKCommand);
        myconfirmAlert.addCommand(mNonCommand);
        myconfirmAlert.setCommandListener(this);

        Display.getDisplay(this).setCurrent(myconfirmAlert, form);
    }
    private Image loadImage(String name) {
        Image image = null;
        try {
        image = Image.createImage(name);
        }
        catch (IOException ioe) {
        System.out.println(ioe);
        }
        return image;
        }
}

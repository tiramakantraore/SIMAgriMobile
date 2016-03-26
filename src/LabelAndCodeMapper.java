
import java.util.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tiramakan
 */
public class LabelAndCodeMapper {
    private static  Hashtable productMap,marketMap,mesureMap,typeOffreMap,categPrixMap;   
    LabelAndCodeMapper(){
        productMap = new Hashtable();
        productMap.put("Maïs local","MAIL");
        productMap.put("Mil local","MILL");
        productMap.put("Mil (Sanio, Grain)","MILO");
        productMap.put("Mil (Sounna, Grain)","MILA");
        
        productMap.put("Sorgho local","SORL");        
        productMap.put("Riz Importe","RIZI");
        productMap.put("Riz local","RIZL");
        productMap.put("Farine De Ble","FBLE");
        
        productMap.put("Amande De Palme","PALM");
        productMap.put("Ammonium De Nitrate [AN]","FEAN");
        productMap.put("Ammonium De Sulphate [AS]","FEAS");
        productMap.put("Anana (Gros)","PINE");
        productMap.put("Arachide (Bouche)","ARAB");
        productMap.put("Arachide (Coque)","ARAC");
        productMap.put("Arachide Graine","ARAG");
        
        productMap.put("Arachide (Huilerie)","GDNO");
        productMap.put("Aubergine","AUBE");
        productMap.put("Banane (Dessert, Non-Mûre)","BANI");
        productMap.put("Banane Déssert","BAND");
        productMap.put("Banane Plantain","BANP");
        productMap.put("Beef","BEVS");
        productMap.put("Bélier","BELG");
        productMap.put("Bélier (Moyen)","BELIEM");
        productMap.put("Bélier (Petit)","BELIEP");
        productMap.put("Bélier Bali Bali (Gros)","BEBG");
        productMap.put("Beure De Karité","BKARI");
        productMap.put("Bifteck","STEK");
        productMap.put("Ble (Grain)","WHEG");
        productMap.put("Boeuf (Large, +450kg)","BEUFG");
        productMap.put("Boeuf (Moyen, 350-450kg)","BEUFM");
        productMap.put("Boeuf (Petit, 300-350kg)","BEUFP");
        productMap.put("Bouc (Gros)","BOUCG");
        productMap.put("Bouc (Moyen)","BOUCM");
        productMap.put("Bouc (Petit)","BOUCP");
        productMap.put("Brebis (Gros)","BREBG");
        productMap.put("Brebis (Moyen)","BREBM");
        productMap.put("Brebis (Petit)","BREBP");
        productMap.put("Cacao","COCO");
        productMap.put("Café","CAFE");
        productMap.put("Calcium De Nitrate [CaN]","FCAN");
        productMap.put("Carottes","CAROTTE");
        productMap.put("Chayote","CHAY");
        productMap.put("Chevre (Large)","CHEG");
        productMap.put("Chevre (Moyen)","CHEVM");
        productMap.put("Chevre (Petite)","CHEVP");
        productMap.put("Chevre (Viande)","CHEVV");
        productMap.put("Chlorure De Potassium (K2O)","FK2O");
        productMap.put("Chou-Fleur","CAUL");
        productMap.put("Choux","CHOU");
        productMap.put("Citron Vert","LIME");
        productMap.put("Concombre","CONC");
        productMap.put("Echalote","OIGE");
        productMap.put("Fleur de choux","FCHOU");
        
        productMap.put("Farine De Ble","WHEF");
        productMap.put("Farine De Mais","FMAI");
        productMap.put("Farine De Mil","FMIL");
        productMap.put("Filet (Boeuf)","FILO");
        productMap.put("Foie (Boeuf)","BFOI");
        productMap.put("Fonio","FONI");
        productMap.put("Fruit De La Passion","PASF");
        productMap.put("Gingembre","GING");
        productMap.put("Gombo","GOMB");
        productMap.put("Huile Arachide","ARAH");
        
        productMap.put("Huile De Palme (Rouge)","POIL");
        productMap.put("Igname","IGNA");
        productMap.put("Jeune Taureau (3-4 Ans)","TAUJ");
        productMap.put("Lait","LAIT");
        productMap.put("Lait (Chevre)","LAIC");
        productMap.put("Laitue","SALA");
        productMap.put("Mangue (Dure)","MANG");
        productMap.put("Manioc (Cossettes)","MANC");
        productMap.put("Manioc (Farine)","MANF");
        productMap.put("Manioc (Gari)","GARI");
        productMap.put("Manioc (Tubercules Frais)","MANT");
        productMap.put("Miel","MIEL");
   
        productMap.put("Mouton","MOUV");
        productMap.put("Mouton (Gros)","MOUTG");
        productMap.put("Mouton (Moyen)","MOUTM");
        productMap.put("Mouton (Petit)","MOUTP");
        productMap.put("Mouton (Adulte)","MOUTA");
        productMap.put("Mouton (Viande)","MOUTV");        
        
        productMap.put("Mouton (15-30kg)","MOUT");
        productMap.put("Niebe (Rouge)","NIER");
        productMap.put("Niebe (White)","NIEB");
        productMap.put("Noix De Cajou (Non Décortiquées)","CHWN");
        productMap.put("Noix De Coco (Sec)","COCN");
        productMap.put("Noix De Karité (Non Décortiquées)","NKARI");
        productMap.put("Noix De Kola","NKOLA");
        productMap.put("Noix De Palme","PAMF");
        productMap.put("NPK (Céréale)","NPKC");
        productMap.put("Oeufs","EGGS");
        productMap.put("Oignon (Blanc, Gros)","OIGB");
        productMap.put("Oignon (Jaune)","OIGJ");
        productMap.put("Oignon (Rouge)","OIGR");
        productMap.put("Oignon (Violet)","OIGV");
        productMap.put("Oranges","ORAN");
        productMap.put("Papaye","PAWP");
        productMap.put("Patates (Rouge)","PATD");
        productMap.put("Perche (Capitaine)","PERC");
        productMap.put("Piments","PIMENT");
      //  productMap.put("Poisson Fumé","POIF");
     //   productMap.put("Poisson-Chat","CATF");
        productMap.put("Poivron (Paprika)","POIV");
        productMap.put("Pomme De Terre (Irlandaise, Importee)","PDTI");
        productMap.put("Pommes De Terre Irlandaises (Locales)","PDTL");
      //  productMap.put("Porc","PORV");
        productMap.put("Potassium De Sulphate","FKSO");
        productMap.put("Poulet","POUL");
        productMap.put("Rognon","ROGN");
        productMap.put("Sabots","SABO");
      //  productMap.put("Semences De Coton","COTS");
        productMap.put("Semences De Riz","SRIZ");
        productMap.put("Sésame","SESA");
        productMap.put("Soja","SOJA");
        productMap.put("Sorgho Blanc","SORB");
        productMap.put("Sorgho Rouge","SORR");
        productMap.put("Sorgho Mixte","SORM");
        productMap.put("Sorgho local","SORL");
        
        
        productMap.put("Superphosphate Simple [SSP]","FSSP");
        productMap.put("Taro","TARO");
        productMap.put("Taureau (5-8 Ans)","TAUROA");
        productMap.put("Jeune Taureau (3-4 Ans)","TAUROJ");
        productMap.put("Taureao Gros","TAUROG");
        
        
        productMap.put("Taureao Moyen (350-450kg)","TAUROM");        
        productMap.put("Taureau (Petit, 300-350kg)","TAUROP");
        productMap.put("Tilapia","TILA");
        productMap.put("Tomate (Industrielle)","TOMI");
        productMap.put("Tomates (Cuisson)","TOMC");
        productMap.put("Niebe Blanc","NIEB");
        
        productMap.put("Urée","UREE");
      //  productMap.put("Vache (100-200kg)","VACA");
     //   productMap.put("Vache (Gros, +450kg)","VACG");
       // productMap.put("Vache (Moyen, 350-450kg)","VACM");
      //  productMap.put("Vache (Petit, 300-350kg)","VACP");
        productMap.put("Viande Avec Os (Boeuf)","BEVO");
        
        //Ajout  du 16/05/2013 à 15 h 44
         productMap.put("Niebe Noir","NIEBN");
         productMap.put("Niebe Rouge","NIER");
         
         productMap.put("Amande de Karité","AMANK");
         productMap.put("Riz Paddy","RIZP");
         productMap.put("Riz Décortiqué","RIZD");
         productMap.put("Riz Etuvé","RIZE");
         
         productMap.put("Maïs Mixte","MAIM");
         productMap.put("Semence de sorgho variété Kapelga","KAPELGA");
         productMap.put("Semence de sorgho variété Sariaso9","SARIASO9");
         productMap.put("Semence de sorgho","SSORGO");
         productMap.put("Semence de maïs espoir","SESPOIR");
         productMap.put("Semence de maïs Barka","SBARKA");
         productMap.put("Semence de maïs Wari","SWARI");
         productMap.put("semence de riz Local (Nérika)","NERIKA");
         productMap.put("semence de riz Local Farako-Bâ riz (FKR34)","FKR34");
         productMap.put("semence de riz local (62N)","62N");
         productMap.put("semence de riz local (TS2)","TS2");
         productMap.put("semence R1 de riz (Gambiaca)","SR1RIZ");
         productMap.put("Taureau Moyen (350-450kg)","TAUROM");
         productMap.put("Patates Rouge","PATR");
         productMap.put("Pomme De Terre Importée","PDTI");
         productMap.put("Pommes De Terre Locales","PDTL");
         productMap.put("Œufs poule de race","ŒUFR");
         productMap.put("Œufs poule local","ŒUFL");
         productMap.put("Ble Grain","BLE");  
         productMap.put("Maïs Blanc","MAIB");  
         productMap.put("Maïs Jaune","MAIB");  
         productMap.put("Maïs Mixte","MAIM");  
         
         
         
         
        
         //Modifications  du 16/05/2013 à 15 h 44
        
        
         productMap.put("Semences De Coton","SCOTON");
         productMap.put("Vache (Petit, 300-350kg)","VACHP");
         productMap.put("Vache (Moyen, 350-450kg)","VACHM");
         productMap.put("Vache (Gros, +450kg)","VACHG");
         productMap.put("Vache (100-200kg)","VACHA");  
         productMap.put("Bélier Gros","BELIEG");  
         
         
         productMap.put("Porc","PORC");
         productMap.put("Poisson Fumé","POISF");
         productMap.put("Poisson-Chat","POISC");
        
        
        marketMap= new Hashtable();
        marketMap.put("Bama","BAMA");
        marketMap.put("Banfora","BANF");
        marketMap.put("Baskuy","BASK");
        marketMap.put("Batié","BATI");
        marketMap.put("Bérégadougou","BERE");
        marketMap.put("Bitou","BITOU");
        marketMap.put("Bobo","BOBO");
        marketMap.put("Boulsa","BOUL");
        marketMap.put("Boussé","BOUS");
        marketMap.put("Dano","DANO");
        marketMap.put("Dédougou","DEDO");
        marketMap.put("Diapaga","DIAP");
        marketMap.put("Diarabakoko","DIARA");
        marketMap.put("Diébougou","DIEB");
        marketMap.put("Djibasso","DJIBA");
        marketMap.put("Djibo","DJIBO");
        marketMap.put("Dori","DORI");
        marketMap.put("Douna","DOUNA");
        marketMap.put("Fada N\'Gourma","FADA");
        marketMap.put("Faramana","FARA");
        marketMap.put("Founza","FOUN");
        marketMap.put("Gaoua","GAOUA");
        marketMap.put("Gassan","GASS");
        marketMap.put("Gayéri","GAYE");
        marketMap.put("Gorom-Gorom","GOROM");
        marketMap.put("Goughin","GOUG");
        marketMap.put("Gouran","GOUR");
        marketMap.put("Grand Marché de Nouna","GDMNO");
        marketMap.put("Guielwango","GUIEL");
        marketMap.put("Kantchari","KANT");
        marketMap.put("Kassoum","KASS");
        marketMap.put("Kaya","KAYA");
        marketMap.put("Kongoussi","KONG");
        marketMap.put("Kossodo","KOSS");
        marketMap.put("Koudougou","KOUD");
        marketMap.put("Lemourou-Logo","LEMOU");
        marketMap.put("Léo","LEO");
        marketMap.put("Ndorola","NDOR");
        marketMap.put("Niangoloko","NIAN");
        marketMap.put("Niassan","NIASS");
        marketMap.put("Niéneta","NIENE");
        marketMap.put("Nouna","NOUN");
        marketMap.put("Orodara","ORO");
        marketMap.put("Ouahigouya","OUAH");
        marketMap.put("Pô","PO");
        marketMap.put("Pouytenga","POUY");
        marketMap.put("Réo","REO");
        marketMap.put("Sankaryaré","SANK");
        marketMap.put("Sebba","SEBA");
        marketMap.put("Siniéna","SINIE");
        marketMap.put("Solenzo","SOLEN");
        marketMap.put("Sono","SONO");
        marketMap.put("Soubakaniedougou","SOUB");
        marketMap.put("Tanghin","TANG");
        marketMap.put("Tenkodogo","TENK");
        marketMap.put("Tougan","TOUG");
        marketMap.put("Yako","YAKO");
        marketMap.put("Zabré","ZABRE");
        marketMap.put("Ziniaré","ZINI");
        
        mesureMap= new Hashtable();
        mesureMap.put("Kg","kg");
        mesureMap.put("Sac 100kg","sac100kg");
        mesureMap.put("Sac 25kg","sac25kg");
        mesureMap.put("Sac 5kg","sac5kg");
        mesureMap.put("Sac 50kg","sac50kg");
        mesureMap.put("Tete","tete");
        mesureMap.put("Tine","tine");
        mesureMap.put("Tonne","t");
        mesureMap.put("Yoruba","yoruba");
       
        typeOffreMap= new Hashtable();
        typeOffreMap.put("Offre de vente", "V");
        typeOffreMap.put("Offre d'achat", "A"); 
     
        categPrixMap= new Hashtable();
        categPrixMap.put("Prix global", "pt");
        categPrixMap.put("Prix unitaire", "pu"); 
        
       
    }
    public  String getProduitCode(String libelle){
       
        return (String)productMap.get(libelle.trim());
       
    }
     public  String getMarcheCode(String libelle){
       
        return (String)marketMap.get(libelle.trim());
       
    }
     
   public  String getMesureCode(String libelle){
       
        return (String)mesureMap.get(libelle.trim());
       
    }
    public  String getTypeOffreCode(String libelle){
       
        return (String)typeOffreMap.get(libelle.trim());       
    }
    public  String getCategPrixCode(String libelle){
       
        return (String)categPrixMap.get(libelle.trim());
       
    }
 
    
    
}

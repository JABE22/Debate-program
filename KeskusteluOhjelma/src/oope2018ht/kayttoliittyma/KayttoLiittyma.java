/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.kayttoliittyma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import oope2018ht.apulaiset.In;
import oope2018ht.viestit.Alue;

/**
 *
 * @author jarnomata
 */
public class KayttoLiittyma {
    
    // Ohjelman komennot
    private final String ADD = "add";
    private final String CATALOG = "catalog";
    private final String SELECT = "select";
    private final String NEW = "new";
    private final String REPLY = "reply";
    private final String TREE = "tree";
    private final String LIST = "list";
    private final String HEAD = "head";
    private final String TAIL = "tail";
    private final String EMPTY = "empty";
    private final String FIND = "find";
    private final String EXIT = "exit";
    
    // Ohjelman tietorakenne
    private final Alue viestialue;
    
    public KayttoLiittyma() {
        this.viestialue = new Alue();
    }
    
    public void kaynnista() {
        System.out.print("Welcome to S.O.B.\n");
        String[] syote;
        
        // Testiajo
        ArrayList<String> komennot = lueKomennotTiedostosta();
        int indeksi = 0;
        
        do { 
            // syote = komentorivi();
            syote = komennot.get(indeksi).split(" ", 2);
            
            switch (syote[0]) {
                // Viestialueen komennot
                case ADD:
                    if (syote[1].contains("&")) {
                        String[] osat = syote[1].split("&");
                        viestialue.lisaaKetju(osat[0], null /*osat[1]*/);
                    } else {
                        viestialue.lisaaKetju(syote[1], null);
                    }
                    break;
                    
                case CATALOG:
                    System.out.print(this.viestialue);
                    break;
                
                // Viestiketjun valinta    
                case SELECT:
                    if (syote.length > 1) {
                        viestialue.aktivoiKetju(syote[1]);
                        
                    } 
                    break;
                
                // Yllä aktivoitua viestiketjua koskevat komennot.
                case NEW:
                    if (syote[1].contains("&")) {
                        String[] osat = syote[1].split("&");
                        viestialue.lisaaViesti(osat[0], null /*osat[1]*/);
                    } else {
                        viestialue.lisaaViesti(syote[1], null);
                    }
                    break;
                    
                case REPLY:
                    String[] osat = syote[1].split(" ", 2);
                    int tunniste = Integer.parseInt(osat[0]);
                    tunniste-=1;
                    if (osat[1].contains("&")) {
                        // Annetaan tiedoston arvoksi null *Korjataan myöhemmin
                        String[] viestinOsat = syote[2].split("&");
                        viestialue.lisaaVastaus(tunniste, viestinOsat[0], null /*osat[1]*/);
                    } else {
                        viestialue.lisaaVastaus(tunniste, osat[1], null);
                    }
                    break;
                    
                case TREE:
                    viestialue.tulostaAktiivinenKetjuPuuna();
                    break;
                    
                case LIST:
                    viestialue.tulostaAktiivinenKetjuListana();
                    break;
                    
                case HEAD:
                    break;
                    
                case TAIL:
                    break;
                    
                case EMPTY:
                    break;
                    
                case FIND:
                    break;
                    
                case EXIT:
                    break;
                    
                default:
                    System.out.println("Error!");
            }
            indeksi++;
            
        } while (!syote[0].equals(EXIT));
    }
    
    public static String[] komentorivi() {
        System.out.print(">");
        String syote = In.readString();
        String[] osat = syote.split(" ", 2);
        
        return osat;
    }
    
    // Ohjelman testiajossa käytettävä metodi
    // Lukee komennot "esimerkkidata.txt" -tiedostosta listalle
    public static ArrayList<String> lueKomennotTiedostosta()  {
        // Lista jonne komennot lisätään
        ArrayList<String> komennot = new ArrayList<>();
        
        try {
            File tiedosto = new File("src/esimerkkidata.txt");
            Scanner lukija = new Scanner(tiedosto);
            
        
            while (lukija.hasNextLine()) {
                komennot.add(lukija.nextLine());
            }
        
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        
        return komennot;
       
    }
    
    public static String[] erotteleTiedostonimi() {
        return null;
    }
}

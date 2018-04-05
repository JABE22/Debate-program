/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.kayttoliittyma;

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
        
        do { 
            syote = komentorivi();
            
            switch (syote[0]) {
                // Viestialueen komennot
                case ADD:
                    if (syote[1].contains("&")) {
                        String[] osat = syote[1].split("&");
                        viestialue.lisaaKetju(osat[0], osat[1]);
                    } else {
                        viestialue.lisaaKetju(syote[1], null);
                    }
                    break;
                    
                case CATALOG:
                    viestialue.tulostaAlueListana();
                    break;
                
                // Viestiketjun valinta    
                case SELECT:
                    if (syote.length > 1) {
                        System.out.println("Valitaan viestiketju syötteellä " + syote[1] );
                        viestialue.aktivoiKetju(syote[1]);
                        
                    } 
                    break;
                
                // Yllä aktivoitua viestiketjua koskevat komennot.
                case NEW:
                    if (syote[1].contains("&")) {
                        String[] osat = syote[1].split("&");
                        viestialue.lisaaViesti(osat[0], osat[1]);
                    } else {
                        viestialue.lisaaViesti(syote[1], null);
                    }
                    break;
                    
                case REPLY:
                    
                    break;
                    
                case TREE:
                    break;
                    
                case LIST:
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
            
        } while (!syote[0].equals(EXIT));
    }
    
    public static String[] komentorivi() {
        System.out.print(">");
        String syote = In.readString();
        String[] osat = syote.split(" ", 2);
        
        return osat;
    }
    
    public static String[] erotteleTiedostonimi() {
        return null;
    }
}

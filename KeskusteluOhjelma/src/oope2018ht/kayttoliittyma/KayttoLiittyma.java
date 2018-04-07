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
import oope2018ht.tiedostot.Kuva;
import oope2018ht.tiedostot.Tiedosto;
import oope2018ht.tiedostot.Video;
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

    // Ohjelman tietorakenne, jonka metodeita kutsutaan käyttöliittymästä
    private final Alue viestialue;

    public KayttoLiittyma() {
        this.viestialue = new Alue();
    }

    public void kaynnista() {
        System.out.print("Welcome to S.O.B.\n");
        String[] syote;

        // Testiajo ** lukee esivalitut komennot tiedostosta
        ArrayList<String> komennot = lueKomennotTiedostosta();
        int indeksi = 0;

        do {
            // syote = komentorivi();
            // Testiajon komentolistan läpikäynti
            syote = komennot.get(indeksi).split(" ", 2);

            switch (syote[0]) {
                // Viestialueen komennot
                case ADD:
                    if (syote[1].contains(" &")) {
                        String[] osat = syote[1].split(" &");
                        viestialue.lisaaKetju(osat[0], luoTiedosto(osat[1]));
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
                    if (syote[1].contains(" &")) {
                        String[] osat = syote[1].split(" &");
                        viestialue.lisaaViesti(osat[0], luoTiedosto(osat[1]));
                    } else {
                        viestialue.lisaaViesti(syote[1], null);
                    }
                    break;

                // Aktiivisen ketjun viestiin vastaaminen
                case REPLY:
                    // Jos reply komennon lisäksi viestin toinen parametri annettu
                    if (syote.length > 1) {
                        String[] osat = syote[1].split(" ", 2);
                        int viesti_id = tarkistaTunniste(osat[0]);

                        // Jos viestitunniste ei sallittu poistutaan
                        if (viesti_id < 0) {
                            break;
                        } else {

                            // Jos viestitunnisteen jälkeen tulee viesti
                            if (osat.length > 1) {

                                // Jos viesti sisältää tiedoston
                                if (osat[1].contains(" &")) {
                                    String[] viestinOsat = osat[1].split(" &");
                                    viestialue.lisaaVastaus(viesti_id, viestinOsat[0],
                                            luoTiedosto(viestinOsat[1]));

                                } else {
                                    viestialue.lisaaVastaus(viesti_id, osat[1], null);
                                }
                            }
                        }
                    }
                    break;

                // Aktiivisen viestialueen tulostus hierarkisesti
                case TREE:
                    viestialue.tulostaAktiivinenKetjuPuuna();
                    break;

                // Aktiivisen viestiketjun tulostus listana 
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
            // Testiajon komentolistan läpikäyntiin liittyvä indeksi -muuttuja
            indeksi++;

        } while (!syote[0].equals(EXIT));
    }
    

    public static String[] komentorivi() {
        System.out.print(">");
        String syote = In.readString();
        String[] osat = syote.split(" ", 2);

        return osat;
    }

    public int tarkistaTunniste(String syote) {
        try {
            int tunniste = Integer.parseInt(syote);
            tunniste -= 1;
            return tunniste;

        } catch (NumberFormatException e) {
            System.out.println("Virheellinen viestitunniste: " + e.getMessage());
        }
        return -1;
    }

    // Ohjelman testiajossa käytettävä metodi
    // Lukee komennot "esimerkkidata.txt" -tiedostosta listalle
    public static ArrayList<String> lueKomennotTiedostosta() {
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
    
    /* Lukee syotettä vastaavan tiedoston, luo sen mukaisen Tiedosto -olion ja 
    paluttaa sen. Käsittelee lukumuunnosvirheet ja palauttaa tällöin null
    */ 
    public static Tiedosto luoTiedosto(String syote) {
        // Erotetaan tiedostopääte nimestä (.gif, .jpg jne.) ja vaihdetaan .txt     
        String[] osat = syote.split("\\.");
        String oikea_tiedostomuoto = osat[0] + ".txt";

        // Luetaan tiedosto käyttäen apumetodia
        String tiedoston_tiedot = lueTiedosto(oikea_tiedostomuoto);
        String[] tiedot = tiedoston_tiedot.split(" ");
        
        // Jos tiedosto on kuva
        if (tiedot[0].equals("Kuva") && tiedot.length == 4) {
            try {
                // Yritetään muunnoksia
                int koko = Integer.parseInt(tiedot[1]);
                int leveys = Integer.parseInt(tiedot[2]);
                int korkeus = Integer.parseInt(tiedot[3]);
                Kuva kuva = new Kuva(syote, koko, leveys, korkeus);
                return kuva;

            } catch (NumberFormatException e) {
                System.out.println("Virhe: " + e.getMessage());
            }
            
        // Jos tiedosto on video
        } else if (tiedot[0].equals("Video") && tiedot.length == 3) {
            try {
                // Yritetään muunnoksia
                int koko = Integer.parseInt(tiedot[1]);
                double kesto = Double.parseDouble(tiedot[2]);
                Video video = new Video(syote, koko, kesto);
                return video;

            } catch (NumberFormatException e) {
                System.out.println("Virhe: " + e.getMessage());
            }
        }

        return null;
    }
    
    // Lukee tekstitiedostosta ensimmäisen rivin (tiedoston tiedot)
    public static String lueTiedosto(String tiedosto) {
        try {
            File tiedosto_txt = new File("src/Oope2018ht/" + tiedosto);
            Scanner lukija = new Scanner(tiedosto_txt);
            String tieto_rivi = lukija.nextLine();
            return tieto_rivi;

        } catch (FileNotFoundException e) {
            System.out.println("Lukuvirhe: " + e.getMessage());
        }
        return null;
    }
}

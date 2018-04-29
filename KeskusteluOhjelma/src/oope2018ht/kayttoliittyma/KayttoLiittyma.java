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
 * @author Jarno Matarmaa ( University of Tampere #428264 )
 *
 * Käyttöliittymä vastaa käyttäjän syötteen lukemisesta ja syötteiden
 * käsittelystä. Tiedostot luodaan täällä, jotta saadaan niihin liittyvät tiedot
 * mahdollisimman nopeasti kapseloitua olion sisälle, joka tekee koodista
 * siistimmän. Viestejä ei luoda, koska niihin liittyvän tunnisteen generointi
 * on hankalaa täällä. Käyttöliittymä kapseloi Alue -luokan ja ohjelman toiminta
 * perustuu sen metodien kutsumiseen.
 *
 */
public class KayttoLiittyma {

    /**
     * Ohjelman testaukseen liittyvät komponentit
     */
    //Testiajon tiedosto, josta luetaan komennot
    private final String FILE = "src/input_count.txt";
    private final ArrayList<String> komennot; // Lista komennoille
    private int komento_nro; // Komentolistan indeksimuuttuja

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

    /**
     * Ohjelman käyttämä pää-luokka, jonka metodeita kutsutaan käyttöliittymästä
     * Alue-luokkamuuttujan kautta hallitaan koko sovelluksen toimintoja.
     */
    private final Alue viestialue;

    /**
     * Käyttäjän syötteiden lukeminen ja komentojen käsittely
     */
    public KayttoLiittyma() {
        this.viestialue = new Alue();

        // Testiajo ** lukee esivalitut komennot tiedostosta
        this.komennot = lueKomennotTiedostosta(FILE);
        this.komento_nro = 0;
    }

    /**
     * Pyörittää silmukkaa, jossa pyydetään käyttäjältä komentoja ja ohjataan
     * ohjelman komentoa vastaava oikea toiminta
     */
    public void kaynnista() {
        System.out.print("Welcome to S.O.B.\n");
        String[] syote; // Tähän luetaan syötteet käyttäjältä

        do {
            syote = komentorivi();

            switch (syote[0]) {
                // Viestialueen komennot
                case ADD:
                    if (tarkistaParametrit(syote, 2)) {
                        lisaaKetju(syote[1]);
                    }
                    break;

                // Viestiketujen tulostus (Aiheviestit)
                case CATALOG:
                    if (tarkistaParametrit(syote, 1)) {
                        System.out.print(this.viestialue);
                    }
                    break;

                // Viestiketjun valinta    
                case SELECT:
                    if (tarkistaParametrit(syote, 2)) {
                        int ketju_nro = lukuMuunnos(syote[1]);
                        viestialue.aktivoiKetju(ketju_nro);
                    }
                    break;

                // Yllä aktivoitua viestiketjua koskevat komennot.
                case NEW:
                    if (tarkistaParametrit(syote, 2)) {
                        uusiViesti(syote[1]);
                    }
                    break;

                // Aktiivisen ketjun viestiin vastaaminen
                case REPLY:
                    // Jos reply komennon lisäksi viestin toinen parametri annettu
                    if (tarkistaParametrit(syote, 2)) {
                        vastaaViestiin(syote[1].split(" ", 2));
                    }
                    break;

                // Aktiivisen viestialueen tulostus hierarkisesti
                case TREE:
                    if (tarkistaParametrit(syote, 1)) {
                        viestialue.tulostaAktiivinenKetjuPuuna();
                    }
                    break;

                // Aktiivisen viestiketjun tulostus listana 
                case LIST:
                    if (tarkistaParametrit(syote, 1)) {
                        viestialue.tulostaAktiivinenKetjuListana();
                    }
                    break;

                // Aktiivisen viestiketjun alkuosan tulostus (vanhimmat)
                case HEAD:
                    if (tarkistaParametrit(syote, 2)) {
                        tulostaVanhat(syote[1]);
                    }
                    break;

                // Aktiisvisen viestiketjun loppuosan tulostus (uusimmat)
                case TAIL:
                    if (tarkistaParametrit(syote, 2)) {
                        tulostaUudet(syote[1]);
                    }
                    break;

                // Tyhjentää aktiivisen viestiketjun annettua tunnistetta vastaavan
                // viestin
                case EMPTY:
                    if (tarkistaParametrit(syote, 2)) {
                        tyhjennaViesti(syote[1]);
                    }
                    break;

                // Hakee viesit, joissa esiintyy käyttäjän antama hakusana
                case FIND:
                    if (syote.length > 1) {
                        viestialue.tulostaHakusanalla(syote[1]);
                    }
                    break;

                // Ohjelman lopetus (Käyttöliittymän sulkeminen)
                case EXIT:
                    if (tarkistaParametrit(syote, 1)) {
                        System.out.println("Bye! See you soon.");
                        System.exit(0);
                    }
                    break;

                // Oletuksena tulostetaan "Error!", jos komennon ensimmäinen osa
                // ei vastaa mitään yllä olevista komennoista
                default:
                    System.out.println("Error!");
                    break;
            }

        } while (true);
    }

    /**
     * Käyttäjän syöttämien komentojen käsittely. Palauttaa 2 -alkioisen
     * taulukon jonka indeksi [0] sisältää mahdollisen komennon.
     *
     * @return palauttaa käyttäjän syöttämän komennon välimerkillä erotetut osat
     * taulukossa. Osat ovat komento ja sitä välimerkin jälkeen seuraava
     * komnetoon mahdollisesti liittyvä osa
     */
    public String[] komentorivi() {
        System.out.print(">");
        // String syote = In.readString();
        /* Testiajon metodi */
        String syote = getKomento();
        String[] osat = syote.split(" ", 2);

        return osat;
    }

    /**
     * Tarkistaa, että parametreja on vaadittu määrä. Ensimmäisenä parametrina
     * annetun taulukon alkioiden lukumäärä on oltava täsmälleen toisena
     * parametrina annettu luku. Taulukon alkiot eivät voi olla yksi välilyönti.
     *
     * @param parametrit taulukko, jossa komennon osat (Tässä ohjelmassa max 2)
     * @param sallittuMaara sallittu komentoriviparametrien määrä
     * @return palauttaa totuusarvon riippuen tuloksesta
     */
    public boolean tarkistaParametrit(String[] parametrit, int sallittuMaara) {
        if (parametrit.length == sallittuMaara) {
            for (String mjono : parametrit) {
                if (mjono.trim().isEmpty()) {
                    System.out.println("Error!");
                    return false;
                }
            }
            return true;
        } else {
            System.out.println("Error!");
            return false;
        }
    }

    /*
    * Muutamia käyttöliittymän eri komentoja vastaavavia metodeita, jotka luotu
    * tarkoituksenaan lyhentää ja siistiä käynnistä() -metodia (switch case)
    *
     */
    /**
     * Uuden viestiaiheen (ketjun) luominen.
     *
     * @param aiheteksti ketjun aloitusviestin aiheteksti
     */
    public void lisaaKetju(String aiheteksti) {
        // Tarkistetaan, sisältääkö viesti mahdollista tiedostoa (" &tiedostonimi.jpg")
        if (aiheteksti.contains(" &")) {
            String[] osat = aiheteksti.split(" &");
            Tiedosto tiedosto = luoTiedosto(osat[1]);
            // Jos tiedoston luonti onnistuu, viestiä yritetään lisätä.
            if (tiedosto != null) {
                viestialue.lisaaKetju(osat[0], tiedosto);
            } else {
                System.out.println("Error!");
            }
        } else { // Jos viesti ei sisällä tiedostoa (" &" -merkkijonoa)
            viestialue.lisaaKetju(aiheteksti, null);
        }
    }

    /**
     * Uuden viestin lisääminen aktiiviseen viestiketjuun.
     *
     * @param teksti uuden viestin tekstisisältö (josta erotetaan mahdollinen
     * tiedosto)
     */
    public void uusiViesti(String teksti) {
        // Tarkistetaan, sisältääkö viesti mahdollista tiedostoa (" &tiedostonimi.jpg")
        if (teksti.contains(" &")) {
            String[] osat = teksti.split(" &");
            Tiedosto tiedosto = luoTiedosto(osat[1]);
            // Jos tiedoston luonti onnistuu, viestiä yritetään lisätä.
            if (tiedosto != null) {
                viestialue.lisaaViesti(osat[0], tiedosto);
            } else {
                System.out.println("Error!");
            }
        } else { // Jos viesti ei sisällä tiedostoa (" &" -merkkijonoa)
            viestialue.lisaaViesti(teksti, null);
        }
    }

    /**
     * Vastaa aiemmin kirjoitettuun viestiin. Parametrin Null arvoja ei
     * tarkisteta, koska ohjelman rakenne on suunniteltu niin, että null-check
     * tehdään muualla. Pidetään koodi siistinä ja vältetään redundanssi.
     *
     * @param osat tulisi sisältää vastattavan viestin tunnus ja sitä seuraava
     * viesti välimerkillä erotettuna. Viestissä voi olla mukana tiedosto, joka
     * on eroteltuna tekstistä merkillä " &"
     */
    public void vastaaViestiin(String[] osat) {
        // Tulostusta varten tarvitaan tieto vastauksen lisäämisen onnistumisesta
        boolean onnistui = false; // Vältetään luettavuutta huonontavat if -lauseet
        int vastaa_viesti_id = lukuMuunnos(osat[0]);
        // Jos viestitunnisteen jälkeen tulee viesti
        if (osat.length > 1) {
            // Jos viesti sisältää tiedoston
            if (osat[1].contains(" &")) {
                String[] viestinOsat = osat[1].split(" &");
                Tiedosto tiedosto = luoTiedosto(viestinOsat[1]);            
                // Jos tiedoston luonti onnistuu, viestiä yritetään lisätä.
                if (tiedosto != null) {
                    onnistui = viestialue.lisaaVastaus(vastaa_viesti_id,
                        viestinOsat[0], tiedosto);
                }               
            } else { // Jos viesti ei sisällä tiedostoa (" &" -merkkijonoa)
                onnistui = viestialue.lisaaVastaus(vastaa_viesti_id, osat[1], null);
            }
        } // Tarkastetaan mahdollista virheen tulostusta varten menikö jokin pieleen
        if (!onnistui) {
            System.out.println("Error!");
        }
    }

    /**
     * Tyhjentää aktiivisen viestiketjun parametrina annettua viestitunnusta
     * vastaavan viestin.
     *
     * @param viestitunnus tyhjennettävän viestin tunnus
     */
    public void tyhjennaViesti(String viestitunnus) {
        int viesti_id = lukuMuunnos(viestitunnus);
        // Lukumuunnos() palauttaa -1, jos epäonnistuu 
        // (vain negatiivisten arvojen tarkistus)
        if (viesti_id > 0) {
            viestialue.tyhjennaViesti(viesti_id);
        } else {
            System.out.println("Error!");
        }
    }

    /**
     * Tulostaa parametrina annettua lukua vastaavan määrän uusia viestejä.
     *
     * @param viesti_lkm tulostettavien viestien lukumäärä
     */
    public void tulostaUudet(String viesti_lkm) {
        int lkm = lukuMuunnos(viesti_lkm);
        if (lkm > 0 && lkm <= this.viestialue.getAktivViestiLkm()) {
            viestialue.tulostaAktiivisenKetjunUudet(lkm);
        } else {
            System.out.println("Error!");
        }
    }

    /**
     * Tulostaa parametrina annettua lukua vastaavan määrän vanhoja viestejä.
     *
     * @param viesti_lkm tulostettavien viestien lukumäärä
     */
    public void tulostaVanhat(String viesti_lkm) {
        int lkm = lukuMuunnos(viesti_lkm);
        if (lkm > 0 && lkm <= this.viestialue.getAktivViestiLkm()) {
            viestialue.tulostaAktiivisenKetjunVanhat(lkm);
        } else {
            System.out.println("Error!");
        }
    }

    /**
     * Tekee turvallisen lukumuunnoksen (String -> Integer).
     *
     * @param syote käyttäjän antama mahdollinen luku
     * @return lukumuunnos tai virhetapauksissa -1.
     */
    public int lukuMuunnos(String syote) {
        try {
            int muunnos = Integer.parseInt(syote);
            return muunnos;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Lukee tekstitiedostosta ensimmäisen rivin (tiedoston tiedot). Palautetaan
     * null, jos luku epäonnistuu.
     *
     * @param tiedosto luettavan tiedoston nimi.tyyppi
     * @return palauttaa merkkijonon (String), jonka oletetaan sisältävän
     * tiedostoon liittyviä tietoja.
     */
    public static String lueTiedosto(String tiedosto) {
        try {    // Muutettava, jos lukee tiedostoja muusta sijainnista
            File tiedosto_txt = new File("src/oope2018ht/" + tiedosto);
            Scanner lukija = new Scanner(tiedosto_txt);
            String tieto_rivi = lukija.nextLine();
            return tieto_rivi;

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Lukee syotettä vastaavan tiedoston käyttäen apumetodia
     * lueTiedosto(String). luo sen mukaisen Tiedosto -olion ja palauttaa sen.
     * Käsittelee lukumuunnosvirheet ja palauttaa virhetilanteissa null.
     *
     * @param tiedostonimi tiedostopolku/tiedostonimi.tyyppi
     * @return Tiedosto -olio (Kuva tai Video)
     */
    public static Tiedosto luoTiedosto(String tiedostonimi) {
        // Luetaan tiedosto käyttäen apumetodia
        String tiedoston_tiedot = lueTiedosto(tiedostonimi);

        // Null -check
        if (tiedoston_tiedot == null) {
            return null;
        }

        String[] tiedot = tiedoston_tiedot.split(" ");

        // Jos tiedosto on kuva
        if (tiedot[0].equals("Kuva") && tiedot.length == 4) {
            try {
                // Yritetään muunnoksia
                int koko = Integer.parseInt(tiedot[1]);
                int leveys = Integer.parseInt(tiedot[2]);
                int korkeus = Integer.parseInt(tiedot[3]);
                Kuva kuva = new Kuva(tiedostonimi, koko, leveys, korkeus);
                return kuva;

            } catch (NumberFormatException e) {
                // Ei tehdä mitään
            }

            // Jos tiedosto on video
        } else if (tiedot[0].equals("Video") && tiedot.length == 3) {
            try {
                // Yritetään muunnoksia
                int koko = Integer.parseInt(tiedot[1]);
                double kesto = Double.parseDouble(tiedot[2]);
                Video video = new Video(tiedostonimi, koko, kesto);
                return video;

            } catch (NumberFormatException e) {
                // Ei tehdä mitään
            }
        }
        return null;
    }

    /* Loput metodeista liittyvät sovelluksen testaamiseen */
    /**
     * Ohjelman testiajossa käytettävä metodi. Lukee komennot
     * "esimerkkidata.txt" -tiedostosta listalle
     *
     * @param file luettavan tiedoston nimi (tiedostopolku/nimi.tyyppi)
     * @return palauttaa tiedostosta luetut komennot listalla (String)
     */
    public static ArrayList<String> lueKomennotTiedostosta(String file) {
        // Lista jonne komennot lisätään
        ArrayList<String> komennot = new ArrayList<>();

        try {
            File tiedosto = new File(file);
            Scanner lukija = new Scanner(tiedosto);

            while (lukija.hasNextLine()) {
                komennot.add(lukija.nextLine());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return komennot;

    }

    /**
     * Testiajon metodi, joka palauttaa komennon luokkamuuttujana olevalta
     * listalta. Ottaa komennon listalta ja kasvattaa komento_nro muuttujaa
     * yhdellä. HUOM! this.komennot listalta tulee kysyä komentoja vain tämän
     * metodin kautta (komento_nro muuttujan kasvattaminen saattaa unohtua).
     *
     * @return käyttäjän syötettä simuloiva komentorivisisältö
     */
    public String getKomento() {
        String komento;
        if (this.komento_nro < this.komennot.size()) {
            komento = this.komennot.get(komento_nro);
            this.komento_nro++;
            return komento;
        }
        return "exit";
    }
}

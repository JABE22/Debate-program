package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author Jarno Matarmaa
 *
 * Ketju -luokka kapseloi aloitusviestin, joka kuvaa ketjun aihetta sekä
 * OmaListan, joka sisältää ketjun aiheeseen liittyvät uudet viestit
 * (aloitusviestit) ja niihin liittyvät vastaukset. Ketjuun lisättyjen viestien
 * viitteet tallennetaan siis sekä tämän luokan kapseloimaan "vastaukset"
 * -OmaListaan, että kunkin vastaukset -listan sisältämän Viestin omaan
 * vastaukset -listaan. Tämä helpottaa viestien hakemista, läpikäyntiä ja
 * tulostamista.
 *
 * Ketju vastaa osittain myös itsensä tulostamisesta. Tämä siksi, että vältytään
 * turhien apumuuttujien, ylimääräisten metodien ja sitä kautta
 * monimutkaisempien rakenteiden luomiselta.
 */
public class Ketju implements Comparable<Ketju> {

    private final Viesti aloitusviesti; // Ketjun aiheviesti
    private final OmaLista vastaukset; // Ketjun kaikki viestit
    private final int tunniste;

    /**
     * Konstruktori joka alustaa luokan kaikki muuttujat.
     *
     * @param aloitusViesti viesti joka kuvaa ketjun aihetta
     */
    public Ketju(Viesti aloitusViesti) {
        this.aloitusviesti = aloitusViesti;
        this.vastaukset = new OmaLista();
        this.tunniste = this.aloitusviesti.getTunniste();
    }

    /**
     * Palauttaa Ketju -olion kapseloiman aloitusviestin
     *
     * @return aloitus-/aiheviesti
     */
    @Getteri
    public Viesti getAloitusviesti() {
        return this.aloitusviesti;
    }

    /**
     *
     * @return OmaLista, ketjun vastausviestit.
     */
    @Getteri
    public OmaLista getVastaukset() {
        return this.vastaukset;
    }

    /**
     *
     * @return ketjun tunniste (Aloitusviestin id)
     */
    public int getTunniste() {
        return this.tunniste;
    }

    // 
    /**
     * Hyödyntää OmaLista olion annaAlku() -metodia ketjun vanhimpien viestien
     * hakemiseksi.
     *
     * @param lkm viestien lukumäärä
     * @return Palauttaa null, jos vastauksia vähemmän kuin parametrina annettu
     * lukumäärä ts. lkm ei sallitulla alueella (lkm < 0 || lkm >
     * vastaukset.koko()), muutoin OmaLista -olion, joka sisältää vanhimmat
     * viestit
     */
    public OmaLista getVanhatViestit(int lkm) {
        return this.vastaukset.annaAlku(lkm);
    }

    /**
     * Hyödyntää OmaLista olion annaLoppu() -metodia ketjun uusimpien viestien
     * hakemiseksi.
     *
     * @param lkm viestien lukumäärä
     * @return Palauttaa null, jos vastauksia vähemmän kuin parametrina annettu
     * lukumäärä ts. lkm ei sallitulla alueella (lkm < 0 || lkm >
     * vastaukset.koko()), muutoin OmaLista -olion, joka sisältää vanhimmat
     * viestit
     */
    public OmaLista getUudetViestit(int lkm) {
        return this.vastaukset.annaLoppu(lkm);
    }

    /**
     * Ketjun kaikkien viestien yhteismäärä (pl. aiheviesti).
     *
     * @return ketjun vastausten lukumäärä
     */
    public int vastauksia() {
        return this.vastaukset.koko();
    }

    /**
     * Hakee parametrina annettua tunnistetta vastaavan viestin ketjun
     * vastaukset OmaListalta. Palautetaan null, jos ei löydy ja siirretään
     * vastuu virheenkäsittelystä eteenpäin.
     *
     * @param viesti_id haettavan viestin tunniste
     * @return palauttaa tunnistetta vastaavan viestin, tai null, jos ei löydy
     */
    public Viesti haeViesti(int viesti_id) {
        
        int indeksi = 0;
        while (indeksi < this.vastaukset.koko()) {
            Viesti verrattava = (Viesti) this.vastaukset.alkio(indeksi);
            if (verrattava.getTunniste() == viesti_id) {
                return verrattava;
            }
            indeksi++;
        }
        return null;
    }

    /**
     * Luo uuden Viesti -olion parametreina annetuista arvoista ja lisää sen
     * uutena viestinä ketjun aiheeseen. Viesti ei vastaa mihinkään viestiin
     * (vastaa=null)
     *
     * @param id viestin tunniste (generoitu Alue -luokassa)
     * @param teksti ketjun uuden viestin teksti
     * @param tiedosto ketjun uuden viestin mahdollinen tiedosto
     */
    public void lisaaViesti(int id, String teksti, Tiedosto tiedosto) {
        Viesti uusi = luoViesti(id, teksti, null, tiedosto);
        this.vastaukset.lisaa(uusi);

    }

    /**
     * Lisää ketjuun viestin, joka vastaa aiemmin luotuun viestiin
     *
     * @param id luotavan viestin tunniste (generoitu luokassa Alue)
     * @param vastattava_id vastatattavan viestin tunniste
     * @param teksti uuden vastaavan viestin tekstisisältö
     * @param tiedosto uuden vastaavan viestin mahdollinen tiedosto
     * @return true = lisäys onnistui ja false = vastattava_id ei löytynyt
     */
    public boolean lisaaVastaus(int id, int vastattava_id, String teksti, Tiedosto tiedosto) {
        // Luodaan viite ketjun viestiin, johon vastataan
        Viesti vastattava = haeViesti(vastattava_id);
        
        if (vastattava != null) {
            
            Viesti vastaus = luoViesti(id, teksti, vastattava, tiedosto);
            // Lisätään vastausviesti vastattavan vastaukset -listalle
            vastattava.lisaaVastaus(vastaus);
            this.vastaukset.lisaa(vastaus);         
            return true;
        }
        return false;
    }

    /**
     * Luo uuden viestin annetuista arvoista (parametrit). Palauttaa uuden
     * viestiolion. (Ei koskaan null -arvoa)
     *
     * @param teksti luotavan viestin tekstisisältö
     * @param vastaa mahdollinen viesti johon luotavan viesti vastaa
     * @param tiedosto luotavan viestin mahdollinen tiedosto
     * @return palauttaa uuden Viesti -olion
     */
    private Viesti luoViesti(int id, String teksti, Viesti vastaa, Tiedosto tiedosto) {
        // Luodaan viestin tunniste ja viesti
        Viesti uusiViesti = new Viesti(id, teksti, vastaa, tiedosto);
        return uusiViesti; // Palautetaan viesti
    }

    /**
     * Tyhjentää parametria vastaavan viestin sisällön. Poistaa tiedoston ja
     * tekstin.
     *
     * @param id tyhjennettävän viestin tunniste (myös indeksi listalla)
     * @return palauttaa tiedon, onnistuiko tyhjennys (löytyikö tunnistetta
     * vastaava viesti)
     */
    public boolean tyhjennaViesti(int id) {
        Viesti tyhjennettava = haeViesti(id);

        if (tyhjennettava != null) {
            tyhjennettava.tyhjenna();
            return true;
        }
        return false;
    }

    /**
     * Tulostaa ketjun kaikki viestit järjestyksessä
     */
    public void tulostaListana() {
        System.out.println("=\n== " + this + "\n===");

        if (this.vastaukset.koko() > 0) {
            int v_index = 0;
            while (v_index < this.vastauksia()) {
                System.out.println(this.vastaukset.alkio(v_index).toString());
                v_index++;
            }
        }
    }

    /**
     * Rekursiivinen viestien tulostus puuna. Metodi käy läpi ketjun
     * vastausviestien listan ja kutsuu varsinaista viestin asianmukaisella
     * sisennyksellä tulostavaa rekursiivista apumetodia.
     */
    public void tulostaPuunaV2A() {
        if (this.vastaukset == null) {
            return;
        }
        // Käydään läpi viestiketjun oksaviestit säilövä lista.
        System.out.println("=\n== " + this + "\n===");

        int i = 0;
        while (i < this.vastaukset.koko()) {
            // Kutsutaan rekursiivista tulostusalgoritmia.
            Viesti viesti = (Viesti) this.vastaukset.alkio(i); // Koodin luettavuus!
            if (viesti.getVastaa() == null) {
                tulostaPuunaV2B(viesti, 0);
            }
            i++;
        }
    }

    /**
     * Varsinainen tulostusmetodi. Tulostaa parametrina annetun viestin toisena
     * parametrina annetulla sisennyksellä. Rekursiivien metodi, joka kutsuu
     * itseään, kunnes hierarkian kaikkien alaoksien viestit on tulostettu.
     *
     * @param viesti tulostettava viesti
     * @param sisennys viestin sisennys vasemmasta reunasta (välilyöntien määrä)
     */
    public void tulostaPuunaV2B(Viesti viesti, int sisennys) {
        // Tulostetaan annetun syvyinen sisennys.
        tulostaSisennys(sisennys);
        // Tulostetaan viestin merkkijonoesitys.
        System.out.println(viesti);
        // Asetetaan apuviite viestin vastaukset säilövään listaan.
        OmaLista viestin_vastaukset = viesti.getVastaukset();
        // Tulostetaan vastaukset rekursiivisesti. Metodista palataan,
        // kun vastauslista on käyty läpi.
        int j = 0;
        while (j < viestin_vastaukset.koko()) {
            tulostaPuunaV2B((Viesti) viestin_vastaukset.alkio(j), sisennys + 3);
            j++;
        }
    }

    /**
     * Tulostaa parametrina annetun määrän välilyöntejä. Ei tulosta
     * rivinvaihtoa. Käytetään sisentämiseen.
     *
     * @param maara välilyöntien määrä.
     */
    public void tulostaSisennys(int maara) {
        int i = 0;
        while (i < maara) {
            System.out.print(" ");
            i++;
        }
    }

    @Override
    public String toString() {
        return this.aloitusviesti + " (" + this.vastauksia() + " messages)";
    }

    @Override
    public int compareTo(Ketju k) {
        return aloitusviesti.getTunniste() - k.getAloitusviesti().getTunniste();
    }
}


package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Komennettava;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author Jarno Matarmaa
 * 
 * Viesti -luokka rakentuu puhtaasti tiedon ympärille. Sisältää viisi luokka
 * -muuttujaa, joihin voidaan asettaa tietoja settereillä ja kysyä tietoja
 * gettereillä. Lisäksi luokassa on toteutettu viestien vertailuun ja tulosteluun 
 * liittyvät metodit.
 */
public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {
    
    private int viesti_id; // Tunniste, jota voidaan käyttää myös indeksinä (OmaLista)
    private String viesti_sisalto;
    private Viesti vastaa; // Viestiolio, johon tämä viestiolio vastaa. (aloitusviesti -> null)
    private Tiedosto tiedosto; // Viestiin liitetty kuva tai video
    private OmaLista vastaukset;
    
    /**
     *
     * @param tunniste viestin yksilöivä tunniste (indeksi)
     * @param teksti viestin tekstisisältö
     * @param vastattava viesti, johon tämä viesti vastaa
     * @param tiedosto viestin mahdollinen tiedosto
     * @throws IllegalArgumentException viestin teksti null tai tunniste pienempi kuin 1
     */
    public Viesti(int tunniste, String teksti, Viesti vastattava, Tiedosto tiedosto) throws IllegalArgumentException {
        if (teksti == null || teksti.isEmpty() || tunniste < 1) {
            throw new IllegalArgumentException();
        } else {
            this.viesti_id = tunniste;
            this.viesti_sisalto = teksti;
            this.vastaa = vastattava;
            this.tiedosto = tiedosto;
            this.vastaukset = new OmaLista();
        }
    }
    
    // Getterit ja Setterit (10 kpl)

    /**
     *
     * @return viestin tunniste (vastaa myös indeksiä listalla)
     */
    @Getteri
    public int getTunniste() {
        return viesti_id;
    }
    
    /**
     *
     * @return palauttaa viestin sisältämän tekstin
     */
    @Getteri
    public String getTeksti() {
        return viesti_sisalto;
    }
    
    /**
     *
     * @return palauttaa viitteen viestiin, jos tämä viesti vastaa
     */
    @Getteri
    public Viesti getVastaa() {
        return vastaa;
    }
    
    /**
     *
     * @return palauttaa viitteen viestin sisältämään tiedostoon
     */
    @Getteri
    public Tiedosto getTiedosto() {
        return tiedosto;
    }

    /**
     *
     * @return palauttaa tämän viestin vastausviestit sisältävän OmaLista -olion
     */
    @Getteri
    public OmaLista getVastaukset() {
        return vastaukset;
    }

    /**
     * Metodi ainoastaan WETO -testeille. Ei käytetä ohjelman toteutuksessa
     *
     * @param tunniste viestin tunniste (yksilöivä ID)
     * @throws IllegalArgumentException tunnisteen arvo pienempi kuin 1
     */
    @Setteri
    public void setTunniste(int tunniste) throws IllegalArgumentException {
        if (tunniste < 1) {
            throw new IllegalArgumentException();
        } else {
            this.viesti_id = tunniste;
        }
    }

    /**
     * Asettaa viestin tekstisisällön
     *
     * @param teksti uusi tekstisisältö
     * @throws IllegalArgumentException parametrina annettu teksti tyhjä tai null
     */
    @Setteri
    public void setTeksti(String teksti) throws IllegalArgumentException {
        if (teksti == null || teksti.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.viesti_sisalto = teksti;
        }
    }
    
    /**
     * Asettaa/muuttaa viestin, johon tämä viesti vastaa. Turha metodi tässä
     * ratkaisussa. Ei käytössä, koska vastaa -viesti annetaan jo Viesti-olion 
     * luontivaiheessa.
     *
     * @param viesti vastattava viesti
     */
    @Setteri
    public void setVastaa(Viesti viesti) {
        this.vastaa = viesti;
    }
    
    /**
     * Asettaa/Liittää viestiin tiedoston. Myös tarpeeton, koska tiedosto asetetaan
     * jo luontivaiheessa ja viesti voi sisältää vain yhden tiedoston.
     *
     * @param tiedosto aliittää viestiin parametrina saadun tiedoston
     */
    @Setteri
    public void setTiedosto(Tiedosto tiedosto) {
        this.tiedosto = tiedosto;
    }

    /**
     * Weto -testien metodi, ei käytössä tässä ohjelmassa. Asettaa vastaukset listan
     * viestille.
     *
     * @param lista mahdollinen viestejä jo sisältävä lista vastauksia
     * @throws IllegalArgumentException poikkeus null -arvoista
     */
    @Setteri
    public void setVastaukset(OmaLista lista) throws IllegalArgumentException {
        if (lista == null) {
            throw new IllegalArgumentException();
        } else {
            this.vastaukset = lista;
        }
    }
    
    // Rajapinnan 'Komennettava' metodien toteutukset (3 kpl)
    /** {@inheritDoc}
     * Hakee Viestin vastauksista parametrina annettua viestioliota.
     * (vertaa tunnisteita).
     * 
     * @param haettava viesti, jota haetaan vastauksista.
     * @return viite löydettyyn viestiin, tai null, jos mitään ei löytynyt
     * @throws IllegalArgumentException jos annettu parametri on null
     */
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if (haettava == null) {
            throw new IllegalArgumentException();
        }
        return (Viesti) this.vastaukset.hae(haettava);
    }
    
    /** {@inheritDoc}
     * 
     * @param lisattava viestin vastaukset listalle lisättävä viesti-olio
     * @throws IllegalArgumentException jos annettu parametri on null
     */
    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        if (vastaukset == null || lisattava == null || this.hae(lisattava) != null) {
            throw new IllegalArgumentException();
        } else {
            this.vastaukset.lisaa(lisattava);
        }
    }
    
    /** {@inheritDoc}
     * Poistaa viestin vanhan tekstisisällön ja tiedoston ja asettaa tekstiksi 
     * "----------".
     */
    @Override
    public void tyhjenna() {                  
        this.viesti_sisalto = Viesti.POISTETTUTEKSTI; // Rajapinnasta komennettava
        this.tiedosto = null;
    }
    
    
    // Vertailu ja indeksointi -metodit (3 kpl)
    @Override
    public boolean equals(Object verrattava) {
        // Jos muuttujat sijaitsevat samassa paikassa, ovat ne samat (tunniste)
        if (this == verrattava) {
            return true;
        }

        // Jos verrattava olio ei ole Viesti-tyyppinen, oliot eivät ole samat
        if (!(verrattava instanceof Viesti)) {
            return false;
        }

        // Muunnetaan olio Viesti-olioksi
        Viesti verrattavaViesti = (Viesti) verrattava;
        
        // Jos olioiden tunnisteet ovat samat, ovat oliot samat
        return this.viesti_id == verrattavaViesti.getTunniste();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.viesti_id;
        return hash;
    }
    
    
    // Rajapinnan 'Comparable' metodin toteutus 
    @Override
    public int compareTo(Viesti verrattava) {
        return this.viesti_id - verrattava.getTunniste();
    }
    
    
    /** 
     * Oletustulostelumuoto ja viestin merkkijonomuotoinen esitys kutsuttaessa 
     */
    @Override
    public String toString() {
        // Jos viestiin ei liity tiedostoa...
        if (this.tiedosto == null) {
            return "#" + this.viesti_id + " " + this.viesti_sisalto;
        } 
        // ...muuten
        return "#" + this.viesti_id + " " + this.viesti_sisalto + " (" + this.tiedosto.toString() + ")";
    }
}

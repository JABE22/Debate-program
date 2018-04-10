/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.viestit;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Komennettava;
import oope2018ht.apulaiset.Setteri;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author jarnomata
 * 
 * Viesti -luokka rakentuu puhtaasti tiedon ympärille. Sisältää viisi luokka
 * -muuttujaa, joihin voidaan asettaa tietoja settereillä ja kysyä tietoja
 * gettereillä. Lisäksi toteutettu viestien vertailuun ja tulosteluun liittyvät
 * metodit.
 */
public class Viesti implements Comparable<Viesti>, Komennettava<Viesti> {
    
    private int viesti_id;
    private String viesti_sisalto;
    private Viesti vastaa; // Viestiolio, johon tämä viestiolio vastaa. (aloitusviesti -> null)
    private Tiedosto tiedosto; // Viestiin liitetty kuva tai video
    private OmaLista vastaukset;
    
    public Viesti(int tunniste, String teksti, Viesti vastattava, Tiedosto tiedosto) throws IllegalArgumentException {
        if (teksti.isEmpty() || tunniste < 1) {
            throw new IllegalArgumentException("Virhearvoja viestiolion parametreissa");
        } else {
            this.viesti_id = tunniste;
            this.viesti_sisalto = teksti;
            this.vastaa = vastattava;
            this.tiedosto = tiedosto;
            this.vastaukset = new OmaLista();
        }
    }
    
    @Getteri
    public int getTunniste() {
        return viesti_id;
    }
    
    @Getteri
    public String getTeksti() {
        return viesti_sisalto;
    }
    
    @Getteri
    public Viesti getVastaa() {
        return vastaa;
    }
    
    @Getteri
    public Tiedosto getTiedosto() {
        return tiedosto;
    }

    @Getteri
    public OmaLista getVastaukset() {
        return vastaukset;
    }

    @Setteri
    public void setTunniste(int tunniste) throws IllegalArgumentException {
        this.viesti_id = tunniste;
    }

    @Setteri
    public void setTeksti(String teksti) throws IllegalArgumentException {
        this.viesti_sisalto = teksti;
    }
    
    @Setteri
    public void setVastaa(Viesti viesti) {
        this.vastaa = viesti;
    }
    
    @Setteri
    public void setTiedosto(Tiedosto tiedosto) {
        this.tiedosto = tiedosto;
    }  
    
    // Rajapinnan 'Komennettava' metodien toteutukset
    @Override
    public Viesti hae(Viesti haettava) throws IllegalArgumentException {
        if (haettava == null) {
            throw new IllegalArgumentException();
        }
        return (Viesti) this.vastaukset.hae(haettava);
    }

    @Override
    public void lisaaVastaus(Viesti lisattava) throws IllegalArgumentException {
        if (this.vastaukset == null || lisattava == null) {
            throw new IllegalArgumentException();
        } else {
            this.vastaukset.lisaa(lisattava);
        }
    }

    @Override
    public void tyhjenna() {                  
        this.viesti_sisalto = Viesti.POISTETTUTEKSTI; // Rajapinnasta komennettava
        this.tiedosto = null;
    }
    
    
    // Vertailu ja indeksointi -metodit
    @Override
    public boolean equals(Object verrattava) {
        // Jos muuttujat sijaitsevat samassa paikassa, ovat ne samat
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
    
    
    // Oletustulostelu
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

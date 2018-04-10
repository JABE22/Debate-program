/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.viestit;

import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.Tiedosto;

/**
 *
 * @author jarnomata
 *
 * Alue -luokka on ohjelman pää-luokka, jonka kautta hallitaan koko viestialueen
 * toimintoja. Alue kapseloi OmaListan joka sisältää alueen kaikki ketjut. Tämä
 * luokka vastaa osittain myös tulostuksista, jotka kohdistuvat aktiiviseen
 * ketjuun.
 *
 */
public class Alue {

    private final OmaLista viestiketjut;
    private Ketju aktiv_vk; // Viite alueen aktiivisena olevaan viestiin

    public Alue() {
        this.viestiketjut = new OmaLista();
        this.aktiv_vk = null;
    }

    // Lisää uuden ketjun ja ensimmäisen viestin (Ketju == 1. viesti)
    public void lisaaKetju(String teksti, Tiedosto tiedosto) {
        // Toistaiseksi kuitataan tiedostot null -arvolla
        Viesti aloitusviesti = new Viesti(viestiketjut.koko() + 1, teksti, null, null);
        Ketju viestiketju = new Ketju(aloitusviesti);
        this.viestiketjut.lisaa(viestiketju);
        this.aktiv_vk = viestiketju;
    }

    // Lisää uuden viestin ketjuun (EI vastausviesti, komento "new" käyttää tätä)
    public void lisaaViesti(String teksti, Tiedosto tiedosto) {
        if (this.viestiketjut.koko() == 0) {
        } else {
            if (this.aktiv_vk != null) {
                this.aktiv_vk.lisaaViesti(teksti, tiedosto);
            }
        }
    }

    // Lisää vastauksen aiemmin luotuun viestiin (komento "reply")
    public boolean lisaaVastaus(int vastattava_id, String teksti, Tiedosto tiedosto) {
        if (this.aktiv_vk == null) {
            return false;
        }

        if (this.aktiv_vk.vastauksia() < vastattava_id) {
            return false;
        }

        this.aktiv_vk.lisaaVastaus(vastattava_id, teksti, tiedosto);
        return true;
    }
    
    public Viesti getViestiketju(int paikka) {
        if (this.viestiketjut.koko() > 0) {
            return (Viesti) this.viestiketjut.alkio(paikka);
        }
        return null;
    }
    
    // Aktivoi parametria vastaavan ketjun.
    public void aktivoiKetju(int ketju_nro) {
        ketju_nro-=1; // Indeksoinnin mukainen vähennys (viesti 1 paikassa [0] jne.)
        if (this.viestiketjut.alkio(ketju_nro) != null) {
            this.aktiv_vk = (Ketju) this.viestiketjut.alkio(ketju_nro);
        }
    }
    
    // Tyhjentää aktiivisen viestiketjun viestin, joka vastaa metodin parametria
    public void tyhjennaViesti(int id) {
        this.aktiv_vk.tyhjennaViesti(id);
    }
    
    // Palauttaa listan, joka sisältää kaikki viestit, joissa hakusana esiintyy
    public void tulostaHakusanalla(String hakusana) {
        // Uusi lista osumille ja hakualueelle
        OmaLista loydetyt = new OmaLista(); 
        OmaLista hakualue = this.aktiv_vk.getVastaukset(); // Siisti koodi
        
        if (hakualue.koko() > 0) {
            
            int v_index = 0;
            while (v_index < hakualue.koko()) {
                 // Napataan viesti muuttujaan jotta vältetään luettavuusongelmat
                 // Menetetään tosin metodin universaalius (virhe jos muunnos ei
                 // onnistu)
                Viesti viesti = (Viesti) hakualue.alkio(v_index);
                
                if (viesti.toString().contains(hakusana)) {
                    loydetyt.lisaa(viesti); // Jos match, lisätään listalle
                }
                v_index++;
            }
        }
        // Tulostetaan lopuksi löydetyt käyttäen apumetodia
        tulostaLista(loydetyt);
    }

    public void tulostaAktiivinenKetjuListana() {
        if (this.aktiv_vk != null) {
            this.aktiv_vk.tulostaListana();
        }
    }

    public void tulostaAktiivinenKetjuPuuna() {
        if (this.aktiv_vk != null) {
            this.aktiv_vk.tulostaPuuna();
        }
    }

    // Null -arvot käsitellään tulostaLista() -metodissa, jota tässä kutsutaan
    public void tulostaAktiivisenKetjunVanhat(int lkm) {
        if (this.aktiv_vk != null) {
            tulostaLista(this.aktiv_vk.getVanhatViestit(lkm));
        }
    }

    // Null -arvot käsitellään tulostaLista() -metodissa, jota tässä kutsutaan
    public void tulostaAktiivisenKetjunUudet(int lkm) {
        if (this.aktiv_vk != null) {
            tulostaLista(this.aktiv_vk.getUudetViestit(lkm));
        }
    }

    // Esivalitun (Metodin parametri) listan tulostus
    public void tulostaLista(OmaLista lista) {
        if (lista != null) {
            Solmu solmu = lista.haeSolmu(0);
            while (solmu != null) {
                System.out.println(solmu);
                solmu = solmu.seuraava();
            }
        }
        // Tähän voidaan lisätä Error tulostus myöhemmin, jos tarpeen
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int ketju_index = 0;

        while (ketju_index < this.viestiketjut.koko()) {
            result.append(this.viestiketjut.alkio(ketju_index));
            result.append("\n");
            ketju_index++;
        }

        return result.toString();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.omalista;

import fi.uta.csjola.oope.lista.LinkitettyLista;
import fi.uta.csjola.oope.lista.Solmu;
import oope2018ht.apulaiset.Ooperoiva;

/**
 *
 * @author jarnomata
 * 
 * Tähän luokkaan on lisätty ainoastaan Ooperoiva- rajapinnan metodien 
 * toteutukset. OmaLista perii LinkitettyLista -luokan julkiset metodit, 
 * mutta "haeSolmu" -metodi on kopioitu sieltä tähän luokkaan, koska se on 
 * asetettu yksityiseksi tuntemattomasta syystä ja katsoin sen olevan 
 * hyödyllinen myös täällä.
 */

public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista>, 
                                                Comparable<OmaLista> {
    
    public OmaLista() {
        
    }

    @Override
    public Object hae(Object haettava) {
        Solmu apuviite = this.paa();
        
        while (apuviite != null) {
            if (apuviite.alkio().equals(haettava)) {
                return apuviite.alkio();
            } else {
                apuviite = apuviite.seuraava();
            }
        }
        
        return null;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean lisaa(Object uusi) {
        // Palautetaan heti false, jos lisättävä objekti on null -arvo tai sitä
        // ei voida vertailla (ei toteuta rajapintaa Comparable)
        if (uusi == null || !(uusi instanceof Comparable)) {
            return false;
        }
        
        // Verrattava solmu on aluksi pää -solmu
        Solmu verrattava = this.paa();
        
        // Selataan lista läpi indeksi-perusteisesti
        int indeksi = 0;
        while (indeksi < koko() && verrattava != null) {
            if (verrattava.alkio().toString().compareTo(uusi.toString()) >= 0) {
                super.lisaa(indeksi, uusi);
                return true;
            }
            verrattava = verrattava.seuraava();
            indeksi++;
        }
        // Jos tullaan tänne asti, lista oli joko tyhjä tai uusi solmu oli järjestyksessään 
        // listan viimeinen. Palautetaan lopuksi true
        lisaaLoppuun(uusi);
        
        return true;
    }

    @Override
    public OmaLista annaAlku(int n) {
        // Sallitun arvoalueen tarkistus
        if (n < 1 || n > this.koko()) {
            return null;
        }
        
        OmaLista alku = new OmaLista(); // Lista valittaville alkioille
        Solmu solmu = this.paa(); // Aloitus -alkio (Solmu)
        
        // Indeksiperustainen OmaListan läpikäynti
        int indeksi = 0;
        while (indeksi < n) {
            alku.lisaa(solmu.alkio());
            solmu = solmu.seuraava();
            indeksi++;
        }
        return alku;
    }

    @Override
    public OmaLista annaLoppu(int n) {
        // Sallitun arvoalueen tarkistus
        if (n < 1 || n > this.koko()) {
            return null;
        }
        
        OmaLista lista = new OmaLista(); // Lista valittaville alkioille
        Solmu solmu = haeSolmu(this.koko() - n); // Solmu aloituskohdasta
        
        
        // Indeksiperustainen OmaListan läpikäynti
        int indeksi = this.koko() - n;
        while (indeksi < this.koko()) {
            lista.lisaa(solmu.alkio());
            solmu = solmu.seuraava();
            indeksi++;
        }
        return lista; 
    }
    
    // Hakee parametrina annetussa paikassa olevan solmun ja palauttaa sen
    public Solmu haeSolmu(int paikka) {
      // Paikka kunnollinen.
      if (paikkaOK(paikka)) {

         // Aloitetaan listan päästä.
         Solmu paikassa = paa();

         // Kelataan oikeaan kohtaan.
         for (int i = 0; i < paikka; i++)
            paikassa = paikassa.seuraava();

         // Palautetaan.
         return paikassa;
      }

      // Virheellinen paikka.
      else
         return null;
    }

    @Override
    public int compareTo(OmaLista ol) {
         return this.koko - ol.koko();
    }
}

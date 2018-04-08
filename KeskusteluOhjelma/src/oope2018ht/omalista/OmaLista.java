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

public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista> {
    
    
    public OmaLista() {
        
    }

    @Override
    public Object hae(Object haettava) {
        Solmu apuviite = this.paa();
        
        while (apuviite != null) {
            if (apuviite.alkio().equals(haettava)) {
                return haettava;
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
        if (uusi == null) {
            return false;
        }
        
        // Verrattava solmu on aluksi pää -solmu
        Solmu verrattava = this.paa();
        Solmu lisattava = new Solmu(uusi);
        Solmu edeltava = null;
        
        while (verrattava != null) {
            // Verrataan solmujen alkioita
            if (verrattava.alkio().toString().compareTo(lisattava.alkio().toString()) >= 0) {
                // Jos lisättävä -solmun alkio järjestyksessään ennen verrattavaa, lisätään 
                // verrattava uuden solmun seuraavaksi ja uusi solmu verrattavan 
                // edeltävän solmun (jos on) seuraavaksi solmuksi.
                lisattava.seuraava(verrattava);
                
                // Jos edeltava tässä vaiheessa null, on uusi eli lisättävä solmu
                // listan ensimmäinen solmu, jolloin alla olevaa ei tehdä.
                if (edeltava != null) {
                    edeltava.seuraava(lisattava);
                }
                
                koko++;
                return true;
                
            } else {
                edeltava = verrattava;
                verrattava = verrattava.seuraava();
            }
        }
        // Jos tullaan tänne asti, lista oli joko tyhjä tai uusi solmu oli järjestyksessään 
        // listan viimeinen. Palautetaan lopuksi true
        lisaaLoppuun(lisattava);
        
        return true;
    }

    @Override
    public OmaLista annaAlku(int n) {
        // Sallitun arvoalueen tarkistus
        if (n < 0 || n > this.koko() - 1) {
            return null;
        }
        
        OmaLista alku = new OmaLista(); // Lista valittaville alkioille
        Solmu solmu = haeSolmu(0); // Listan ensimmäinen alkio (Solmu)
        
        int indeksi = 0;
        while (indeksi < n) {
            alku.lisaa(solmu);
            solmu = solmu.seuraava();
            indeksi++;
        }
        return alku;
    }

    @Override
    public OmaLista annaLoppu(int n) {
        // Otetaan viimeinen indeksi talteen muuttujaan, jotta vältytään 
        // turhilta ja rumilta '-1' -operaatioilta.
        int viim_indeksi = this.koko() - 1;
        
        // Sallitun arvoalueen tarkistus
        if (n < 0 || n > viim_indeksi) {
            return null;
        }
        
        OmaLista lista = new OmaLista(); // Lista valittaville alkioille
        Solmu solmu = haeSolmu(this.koko() - n);
        
        int indeksi = this.koko() - n;
        while (indeksi <= viim_indeksi) {
            lista.lisaa(solmu);
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
}

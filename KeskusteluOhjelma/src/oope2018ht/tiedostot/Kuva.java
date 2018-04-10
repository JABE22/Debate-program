/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author jarnomata
 */
public class Kuva extends Tiedosto {
    
    private int leveys_px;
    private int korkeus_px;
    
    
    public Kuva(String nimi, int koko, int leveys, int korkeus) throws IllegalArgumentException {
        
        super(nimi, koko);
        
        if (leveys < 1 || korkeus < 1) {
            throw new IllegalArgumentException();
        } else {
            this.leveys_px = leveys;
            this.korkeus_px = korkeus;
        }
    }
    
    @Getteri
    public int getLeveys() {
        return this.leveys_px;
    }
    
    @Getteri
    public int getKorkeus() {
        return this.korkeus_px;
    }
    
    @Setteri
    public void setLeveys(int uusiLeveys) {
        if (uusiLeveys < 1) {
            throw new IllegalArgumentException();
        } else {
            this.leveys_px = uusiLeveys;
        }
    }
    
    @Setteri
    public void setKorkeus(int uusiKorkeus) {
        if (uusiKorkeus < 1) {
            throw new IllegalArgumentException();
        } else {
            this.korkeus_px = uusiKorkeus;
        }
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.leveys_px + "x" + this.korkeus_px;
    }
    
}

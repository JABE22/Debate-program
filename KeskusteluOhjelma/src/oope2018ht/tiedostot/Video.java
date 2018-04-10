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
public class Video extends Tiedosto {
    
    private double pituus;
    
    public Video(String nimi, int koko, double pituus) throws IllegalArgumentException {
        super(nimi, koko);
        if ( pituus > 0 ) {
            this.pituus = pituus;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    @Getteri
    public double getPituus() {
        return this.pituus;
    }
    
    @Setteri
    public void setPituus(double pituus) throws IllegalArgumentException{
        if (pituus <= 0) {
            throw new IllegalArgumentException();
        }
        this.pituus = pituus;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.pituus + " s";
    }
}

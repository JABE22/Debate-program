
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Jarno Matarmaa
 */
public class Video extends Tiedosto {
    
    private double pituus;
    
    /**
     *
     * @param nimi
     * @param koko bitteinä levyllä
     * @param pituus sekuntia
     * @throws IllegalArgumentException pituus oltav suurempi kuin 0
     */
    public Video(String nimi, int koko, double pituus) throws IllegalArgumentException {
        super(nimi, koko);
        if ( pituus > 0 ) {
            this.pituus = pituus;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     *
     * @return pituus sekunteina
     */
    @Getteri
    public double getPituus() {
        return this.pituus;
    }
    
    /**
     *
     * @param pituus 
     * @throws IllegalArgumentException
     */
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

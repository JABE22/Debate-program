
package oope2018ht.tiedostot;

import oope2018ht.apulaiset.Getteri;
import oope2018ht.apulaiset.Setteri;

/**
 *
 * @author Jarno Matarmaa
 * 
 */
public class Kuva extends Tiedosto {
    
    private int leveys_px;
    private int korkeus_px;
    
    /**
     *
     * @param nimi tiedoston nimi
     * @param koko levyllä
     * @param leveys pixeliä
     * @param korkeus pixeliä
     * @throws IllegalArgumentException /tarkistaa leveyden ja korkeuden
     */
    public Kuva(String nimi, int koko, int leveys, int korkeus) throws IllegalArgumentException {
        
        super(nimi, koko);
        
        if (leveys < 1 || korkeus < 1) {
            throw new IllegalArgumentException();
        } else {
            this.leveys_px = leveys;
            this.korkeus_px = korkeus;
        }
    }
    
    /**
     *
     * @return kuvan leveys pixeleinä
     */
    @Getteri
    public int getLeveys() {
        return this.leveys_px;
    }
    
    /**
     *
     * @return kuvan korkeus pixeleinä
     */
    @Getteri
    public int getKorkeus() {
        return this.korkeus_px;
    }
    
    /**
     *
     * @param uusiLeveys
     */
    @Setteri
    public void setLeveys(int uusiLeveys) {
        if (uusiLeveys < 1) {
            throw new IllegalArgumentException();
        } else {
            this.leveys_px = uusiLeveys;
        }
    }
    
    /**
     *
     * @param uusiKorkeus
     */
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

package badziol.czastyki.EfektyTestowe.skrzydlo;

import org.bukkit.Color;
import org.bukkit.Particle;

import java.util.ArrayList;
import java.util.HashMap;

public class WingConfig {
    public double startVertical = -0.1;
    public double startHorizontalOffset = 0;
    public double startDistanceToPlayer = 0.3;
    public double distanceBetweenParticles = 0.1;
    public int wingTimer = 2;

    public boolean wingAnimation = true; //me - nie zainicjowane w soulshadow ( default : false)

    public int wingFlapSpeed = 4;
    public int startOffset = 30;
    public int stopOffset = 70;

    private ArrayList<SkrzydloCzastka> skrzydloCzastkas = new ArrayList<>(); //tworzenie moje (pojedyncza czastka x)


    // Hasmap containing the coordinates relative to the player
    // And the assinged particle at that coordinate
    // double[] functions as double[distance from player, height]
    public  HashMap<double[], SkrzydloCzastka> particleCoordinates;
    private  final ArrayList<String> skrzydloWzorBezpiecznyLot = new ArrayList<>();
    private  final ArrayList<String> skrzydloWzorCzasPrzekroczony = new ArrayList<>();

    public WingConfig() {
        //Przeczytaj opis konstruktora
        skrzydloCzastkas.add(new SkrzydloCzastka("x", Particle.CRIT_MAGIC, Color.fromRGB(0,0,0) ,0));
        skrzydloCzastkas.add(new SkrzydloCzastka("o", Particle.SPELL_WITCH, Color.fromRGB(0,0,0), 0));

        skrzydloWzorBezpiecznyLot.add("-,-,-,-,x,x,x,-,-,-");
        skrzydloWzorBezpiecznyLot.add("-,-,-,x,x,x,x,x,-,-");
        skrzydloWzorBezpiecznyLot.add("-,-,x,x,x,x,x,x,x,-");
        skrzydloWzorBezpiecznyLot.add("-,x,x,x,x,x,x,x,x,-");
        skrzydloWzorBezpiecznyLot.add("x,x,x,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("x,x,x,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("x,x,x,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("x,x,x,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("-,-,x,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("-,-,-,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("-,-,-,x,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,x,x,x,x,x,x");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,-,x,x,x,x,-");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,-,x,x,x,x,-");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,-,-,o,x,x,-");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,-,-,o,x,x,-");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,-,-,-,o,x,-");
        skrzydloWzorBezpiecznyLot.add("-,-,-,-,-,-,-,-,o,-");

        particleCoordinates = parsujWzorSkrzydla(skrzydloWzorBezpiecznyLot);

    }


    public SkrzydloCzastka pobierzSymbolCzastki(String symbol) {
        for (SkrzydloCzastka skrzydloCzastka : skrzydloCzastkas) {
            if (skrzydloCzastka.symbol.equals(symbol)) {
                return skrzydloCzastka;
            }
        }
        return null;
    }


    private HashMap<double[], SkrzydloCzastka> parsujWzorSkrzydla(ArrayList<String> wzor) {
        HashMap<double[], SkrzydloCzastka> particleCoordinates = new HashMap<>();
        double distance;
        double height = startVertical + (wzor.size() * distanceBetweenParticles); // Highest vertical point of the wing

        for (String liniaCzastek : wzor) {
            height = height - distanceBetweenParticles;
            distance = startDistanceToPlayer;
            String[] liniaCzastekDane = liniaCzastek.split(",");

            for (String czastkaID : liniaCzastekDane) {
                if (czastkaID.equals("-")) { // '-' - traktuj jako puste pole
                    distance = distance + distanceBetweenParticles;
                    continue;
                }
                double[] coordinates = new double[2];
                coordinates[0] = distance;
                coordinates[1] = height;
                particleCoordinates.put(coordinates, pobierzSymbolCzastki(czastkaID));
                distance = distance + distanceBetweenParticles;
            }
        }
        return particleCoordinates;
    }


}

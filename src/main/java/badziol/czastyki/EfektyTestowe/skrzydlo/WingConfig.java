package badziol.czastyki.EfektyTestowe.skrzydlo;

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

    private ArrayList<WingParticle> wingParticles = new ArrayList<>(); //tworzenie moje (pojedyncza czastka x)


    // Hasmap containing the coordinates relative to the player
    // And the assinged particle at that coordinate
    // double[] functions as double[distance from player, height]
    public  HashMap<double[], WingParticle> particleCoordinates;
    private ArrayList<String> myParticleLayout = new ArrayList<>(); // moje


    public WingConfig() {
        wingParticles.add(new WingParticle(this,"x", Particle.CRIT_MAGIC));
        wingParticles.add(new WingParticle(this,"o", Particle.SPELL_WITCH));

        myParticleLayout.add("-,-,-,-,x,x,x,-,-,-");
        myParticleLayout.add("-,-,-,x,x,x,x,x,-,-");
        myParticleLayout.add("-,-,x,x,x,x,x,x,x,-");
        myParticleLayout.add("-,x,x,x,x,x,x,x,x,-");
        myParticleLayout.add("x,x,x,x,x,x,x,x,x,x");
        myParticleLayout.add("x,x,x,x,x,x,x,x,x,x");
        myParticleLayout.add("x,x,x,x,x,x,x,x,x,x");
        myParticleLayout.add("x,x,x,x,x,x,x,x,x,x");
        myParticleLayout.add("-,-,x,x,x,x,x,x,x,x");
        myParticleLayout.add("-,-,-,x,x,x,x,x,x,x");
        myParticleLayout.add("-,-,-,x,x,x,x,x,x,x");
        myParticleLayout.add("-,-,-,-,x,x,x,x,x,x");
        myParticleLayout.add("-,-,-,-,x,x,x,x,x,x");
        myParticleLayout.add("-,-,-,-,-,x,x,x,x,-");
        myParticleLayout.add("-,-,-,-,-,x,x,x,x,-");
        myParticleLayout.add("-,-,-,-,-,-,o,x,x,-");
        myParticleLayout.add("-,-,-,-,-,-,o,x,x,-");
        myParticleLayout.add("-,-,-,-,-,-,-,o,x,-");
        myParticleLayout.add("-,-,-,-,-,-,-,-,o,-");

        particleCoordinates = parseParticleCoordinates();

    }


    public WingParticle getWingParticleByID(String ID) {
        for (WingParticle wingParticle : wingParticles) {
            if (wingParticle.id.equals(ID)) {
                return wingParticle;
            }
        }
        return null;
    }


    private HashMap<double[], WingParticle> parseParticleCoordinates() {
        HashMap<double[], WingParticle> particleCoordinates = new HashMap<>();
        double distance;
        double height = startVertical + (myParticleLayout.size() * distanceBetweenParticles); // Highest vertical point of the wing

        for (String liniaCzastek : myParticleLayout) {

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
                particleCoordinates.put(coordinates, getWingParticleByID(czastkaID));
                distance = distance + distanceBetweenParticles;
            }
        }
        return particleCoordinates;
    }


}

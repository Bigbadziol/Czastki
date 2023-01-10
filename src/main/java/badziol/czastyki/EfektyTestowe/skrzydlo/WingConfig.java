package badziol.czastyki.EfektyTestowe.skrzydlo;

import java.util.ArrayList;
import java.util.HashMap;

public class WingConfig {
    public boolean showWhenMoving = true; //me;
    public double startVertical = -0.1;
    public double startHorizontalOffset = 0; //default
    public double startDistanceToPlayer = 0.3;
    public double distanceBetweenParticles = 0.1;
    public int wingTimer = 2;

    public boolean wingAnimation = true; //me - nie zainicjowane w soulshadow ( default : false)
    public boolean onlyOneSide = false;  //me - nie zainicjowane w soulshadow

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
        myParticleLayout.add("-,-,-,-,-,-,x,x,x,-");
        myParticleLayout.add("-,-,-,-,-,-,x,x,x,-");
        myParticleLayout.add("-,-,-,-,-,-,-,x,x,-");
        myParticleLayout.add("-,-,-,-,-,-,-,-,x,-");

        //wingParticles = createWingParticles(getConfigurationSection("wing.particles"));// orginal
        wingParticles.add(new WingParticle(this,"x"));

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



    ///TU SIE ZASTANOWIC!!!!
    private HashMap<double[], WingParticle> parseParticleCoordinates() {

        HashMap<double[], WingParticle> particleCoordinates = new HashMap<>();
        double distance;
        double height = startVertical + (myParticleLayout.size() * distanceBetweenParticles); // Highest vertical point of the wing

        for (String ll : myParticleLayout) {

            height = height - distanceBetweenParticles;
            distance = startDistanceToPlayer;

           String[] particleLine = ll.split(",");

            // Go though each "particle" on the row
            for (String particleID : particleLine) {

                // "-" means there should be no particle at the coordinate
                if (particleID.equals("-")) {
                    distance = distance + distanceBetweenParticles;
                    continue;
                }

                double[] coordinates = new double[2];
                coordinates[0] = distance;
                coordinates[1] = height;
                particleCoordinates.put(coordinates, getWingParticleByID(particleID));
                distance = distance + distanceBetweenParticles;
            }
        }
        return particleCoordinates;
    }


}

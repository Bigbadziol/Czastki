package badziol.czastyki.EfektyTestowe.skrzydlo;


import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.ArrayList;
/*
    W tej klasie Filip nic nie zmieniaj, przechowuje ona dane pojedynczej czastki składającej się na
    skrzydlo.
 */

public class WingParticle {
    public WingConfig wingConfig;   //Dane konfiguracyjne calego skrzydla , rozstaw cząstek itp.
    public String id;
    public Particle particle;
    //zmienne defakto prywatne odpowiadające za przechowywanie lokalizacji cząstki w konkretym momencie animacji.
    public double distance = 0;
    public double height = 0;
    public int angle = 0;
    public double speed = 0;
    public Object particleData = null;
    public Color color = Color.fromRGB(0xffffff);  // brane pod uwage gdy : SPELL_MOB, SPELL_MOB_AMBIENT
                                                    // jest to cecha tych cząstek


    /**
     *
     * @param wingConfig - dane konfiguracyjne skrzydla jako obiektu
     * @param id - "symbol cząstki" - dzięki któremu możesz namalować skrzydło
     * @param particle - minecraftowy typ cząstki
     */
    public WingParticle(WingConfig wingConfig, String id,Particle particle) {
        this.wingConfig = wingConfig;
        this.id = id;
        this.particle = particle;
    }

    public void spawnParticle(Location loc,Player player , WingSide wingSide){
        double direction = loc.getYaw();
        double x, y, z, extra;

        if (wingSide == WingSide.LEFT) direction = (direction + angle);
        if (wingSide == WingSide.RIGHT) direction = (direction - angle);

        if (particle == Particle.SPELL_MOB || particle == Particle.SPELL_MOB_AMBIENT) {
            x = color.getRed() / 255D;
            y = color.getGreen() / 255D;
            z = color.getBlue() / 255D;
            extra = 1;
        } else {
            direction = Math.toRadians(direction);
            x = distance * Math.cos(direction);
            y = height;
            z = distance * Math.sin(direction);
            extra = speed;
        }

        player.spawnParticle(particle, player.getLocation(), 0, x, y, z, extra, particleData);
    }

    // Spawn the particle at a location for all players specified
    // If the particle has velocity (defined by speed) we need to calculate the x and z values so the flies in the correct direction
    // This direction is based on the yaw of the wing and the if the particle is part of the left or right side of the wing
    // WingSide is a String of either 'left' or 'right'
    // Extra info on particles https://www.spigotmc.org/threads/comprehensive-particle-spawning-guide-1-13.343001/
    public void spawnParticle(Location loc, ArrayList<Player> spawnForPlayers, WingSide wingSide) {

        double direction = loc.getYaw();

        if (wingSide == WingSide.LEFT) direction = (direction + angle);
        if (wingSide == WingSide.RIGHT) direction = (direction - angle);

        double x, y, z, extra;

         if (particle == Particle.SPELL_MOB || particle == Particle.SPELL_MOB_AMBIENT) {

            x = color.getRed() / 255D;
            y = color.getGreen() / 255D;
            z = color.getBlue() / 255D;
            extra = 1;

        } else {
            direction = Math.toRadians(direction);
            x = distance * Math.cos(direction);
            y = height;
            z = distance * Math.sin(direction);
            extra = speed;
        }

        for (Player player : spawnForPlayers) {
            player.spawnParticle(particle, loc, 0, x, y, z, extra, particleData);
        }
    }
}

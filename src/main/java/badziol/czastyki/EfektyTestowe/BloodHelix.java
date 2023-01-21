package badziol.czastyki.EfektyTestowe;

import badziol.czastyki.Czastyki;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class BloodHelix {
    private final Czastyki plugin;

    public BloodHelix(Czastyki plugin){
        this.plugin = plugin;
    }

    public void efekt(Player player){
        new BukkitRunnable() {
            double phi = 0;
            @Override
            public void run() {
                phi += Math.PI/8; //tu realnie jest szybkosc
                double x , y, z;
                Location loc = player.getLocation();
                if (phi > 40){
                    System.out.println("cncn");
                    this.cancel();
                }

                for (double t=0; t<= 2*Math.PI; t += Math.PI/16){
                    for (double i=0; i<=1; i+=1){
                        //- i*Math.PI - tak naprawde ofset dla drugiej helisy, bo raz jest *0 , *1
                        // - 0.3*(2*Math.PI-t) *0.5 - odpowiada za promien, 0.3 -> 0.1 , 0.5-> 1
                        // x, z - muszą mieć te same wspołczynniki
                        x = 0.3*(2*Math.PI-t) *0.5*Math.cos(t+phi + i*Math.PI);
                        y = 0.5*t;
                        z = 0.3*(2*Math.PI-t) *0.5*Math.sin(t+phi + i*Math.PI);
                        loc.add(x,y,z);
                        //ParticleEffect.RED_DUST.display(loc,0,0,0,0,1);//orginalna
                        new ParticleBuilder(ParticleEffect.REDSTONE, loc)
                                .setParticleData(new RegularColor(255,255,0))
                                .display();
                        loc.subtract(x,y,z);
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin,0L,1L);
    }
}

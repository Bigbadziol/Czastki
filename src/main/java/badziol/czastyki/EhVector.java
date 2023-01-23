package badziol.czastyki;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import static java.lang.Math.cos;

public class EhVector  implements Listener {
    Czastyki plugin;
    public EhVector(Czastyki plugin){
        this.plugin = plugin;
        //plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void vectorTest(final PlayerInteractEvent event){
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            System.out.println("Left click.");
            new BukkitRunnable() {
                Player player = event.getPlayer();
                Vector dir = player.getLocation().getDirection().normalize();
                Location loc = player.getLocation();
                double t = 0; //czas
                double r = 1;

                @Override
                public void run() {

                    t += 0.5;
                    double x = dir.getX() * t;
                    double y = dir.getY() * t + 1.5;
                    double z = dir.getZ() * t;
                    loc.add(x, y, z);
                    //ParticleEffect.LAVA.display(loc,0,0,0,0,5);
                    ParticleEffect.WAX_ON.display(loc);


                    for (Entity e : loc.getChunk().getEntities()) {
                        if (e.getLocation().distance(loc) < 1.0) {//przyblizona lokalizacja
                            if (!e.equals(player)) {
                                e.setFireTicks(20);
                            }

                        }
                    }
                    loc.subtract(x, y, z);
                    if (t > 40) {
                        System.out.println("t > 40");
                        this.cancel();
                    }

/*
                    t = t + Math.PI/8;
                    double x = r*Math.cos(t);   //
                    double y =t;                //
                    double z = r*Math.sin(t);   //
                    loc.add(x, y, z);
                    ParticleEffect.FLAME.display(loc);
                    loc.subtract(x, y, z);
                    if (t > Math.PI*4) this.cancel();

 */


                }
            }.runTaskTimer(plugin, 0, 1);
        }
    }

}

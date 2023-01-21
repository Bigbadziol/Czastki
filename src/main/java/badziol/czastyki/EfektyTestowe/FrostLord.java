package badziol.czastyki.EfektyTestowe;
//#2
import badziol.czastyki.Czastyki;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.color.RegularColor;

public class FrostLord {
    Czastyki plugin;

    public FrostLord(Czastyki plugin) {
        this.plugin = plugin;
    }

    public void efekt (Player player){
        new BukkitRunnable() {
            double t = 0;
            double x , y, z;
            @Override
            public void run() {
                t += Math.PI/8; //tu realnie jest szybkosc
                Location loc = player.getLocation();
                //2*PI =  360 st
                for (double phi=0; phi<= 2*Math.PI; phi += Math.PI/2) {
                    // 0.75*(Math.PI - t); - promien -  wraz z wzrostem czasu promien maleje
                    x = 0.5*(2 * Math.PI - t) * Math.cos(t+phi);
                    y = 0.2* t;
                    z = 0.5*(2 * Math.PI - t) * Math.sin(t+phi);
                    loc.add(x, y, z);
                    new ParticleBuilder(ParticleEffect.SNOW_SHOVEL, loc)
                            .display();
                    loc.subtract(x, y, z);
                }

                if (t >= 4 * Math.PI ) {
                    loc.add(x, y, z);
                    new ParticleBuilder(ParticleEffect.SNOWFLAKE,loc)
                            .setAmount(50)
                            .setSpeed(1)
                            .display();
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(plugin,0L,1L);
    }
}

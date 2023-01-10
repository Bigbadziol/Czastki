package badziol.czastyki;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

//To nie powinno byc zarejestrowanie nigdzie, listener testowy
public class OnMoveTestListener implements Listener {
    @EventHandler
    public void onMoveTest(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        Random random = new Random();
        //System.out.println("onMoveTest - Uwaga tego nie powinno byc widac !! ");
        for (int i = 0; i < 4 ; i++) {
            player.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                    loc.add(random.nextDouble()+1,random.nextDouble()+1,random.nextDouble()+1),0);
            player.getWorld().spawnParticle(Particle.CRIT_MAGIC,
                    loc.add((random.nextDouble()+1)* -1,(random.nextDouble()+1)*-1,(random.nextDouble()+1)*-1),0);
        }
    }
}

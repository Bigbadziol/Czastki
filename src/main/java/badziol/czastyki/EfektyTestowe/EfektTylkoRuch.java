package badziol.czastyki.EfektyTestowe;

import badziol.czastyki.Czastyki;
import badziol.czastyki.EfektPrototyp;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Random;

public class EfektTylkoRuch extends EfektPrototyp implements Listener {
    public EfektTylkoRuch(Czastyki plugin) {
        super(plugin);
        nazwa = "tylko_ruch";
        onMoveAktywne = true; //czyli efekt oparty tylko o ruch gracza
        zadanieAktywne = false; // osobny proces nic nie robi
        zadanieOpoznienie = 0L; //tu nie istotnie bo zadanieAktywne jest wyłączone
        zadanieOkresCzasowy = 2L; //tu nie istotne bo zadanieAktywne jest wyłączone
    }

    @Override
    @EventHandler
    public  void onMove(PlayerMoveEvent event){
        // todo :napewno trzeba sprawdzac czy gracz / living entity jest na liscie obslugiwanych
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        Random random = new Random();
        for (int i = 0; i < 4 ; i++) {
            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK,
                    loc.add(random.nextDouble()+1,random.nextDouble()+1,random.nextDouble()+1),0);
            player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK,
                    loc.add((random.nextDouble()+1)* -1,(random.nextDouble()+1)*-1,(random.nextDouble()+1)*-1),0);
        }
    }

}

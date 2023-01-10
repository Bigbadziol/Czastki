package badziol.czastyki.EfektyTestowe;

import badziol.czastyki.Czastyki;
import badziol.czastyki.EfektPrototyp;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EfektTylkoZadanie extends EfektPrototyp  {
    //Wersja robocza :
     Player player;


    public EfektTylkoZadanie(Czastyki plugin) {
        super(plugin);
        nazwa = "tylko_zadanie";
        onMoveAktywne = false;    // czyli onMove nie aktywne
        zadanieAktywne = true;    // osobny proces bedzie cos robil
        zadanieOpoznienie = 0L;   // nasze 'delay'
        zadanieOkresCzasowy = 2L; // nasz 'period'

    }


    public  void inicjuj(Player player){
        this.player = player;
    }

    @Override
    public void przygotujZadanie() {
        final double[] offset = {0D};
        final Location[] loc = new Location[1];
        final Location[] l1 = new Location[1];
        final Location[] l2 = new Location[1];
        final Location[] l3 = new Location[1];
        zadanie = new BukkitRunnable() {
            @Override
            public void run() {
                offset[0] = offset[0] + Math.PI/8; // 16 tez spoko
                loc[0] = player.getLocation();
                //l1[0] = loc[0].clone().add(Math.cos(offset[0]), Math.sin(offset[0])+1,Math.sin(offset[0]));
                //l2[0] = loc[0].clone().add(Math.cos(offset[0]+Math.PI),Math.sin(offset[0])+1 , Math.sin(offset[0]+ Math.PI));


                l1[0] = loc[0].clone().add(Math.cos(offset[0]), 0.7D,Math.sin(offset[0]));
                l2[0] = loc[0].clone().add(Math.cos( (offset[0]+Math.PI) ) +0.5D,
                        1.5D ,
                        Math.sin( (offset[0]+ Math.PI)) +0.5D );

/*
                l1[0] = loc[0].clone().add(Math.cos(offset[0]), 0.3D,Math.sin(offset[0]));
                l2[0] = loc[0].clone().add(Math.cos( (offset[0]+Math.PI) ) * 2,
                                                    0.9D ,
                                                    Math.sin( (offset[0]+ Math.PI)) *2  );
                l3[0] = loc[0].clone().add(Math.cos(offset[0]) *3 ,1.4D , Math.sin(offset[0]+ Math.PI) * 3);
*/

                player.getWorld().spawnParticle(Particle.TOTEM,l1[0],0);
                player.getWorld().spawnParticle(Particle.TOTEM,l2[0],0);
//                player.getWorld().spawnParticle(Particle.TOTEM,l3[0],0);

            }
        };
    }
}

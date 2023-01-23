package badziol.czastyki;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *  Nie usuwać, niech zostanie. Ku pamięci : wywołanie konstruktora.....
 *  Powiązane z testem w komendzie /czt
 */
public class MyEventListener implements Listener {
    public MyEventListener(Czastyki plugin){

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onMoveDetection(PlayerMoveEvent event){
        System.out.println("Ruszajacy sie gracz : "+event.getPlayer().getName());
    }


}
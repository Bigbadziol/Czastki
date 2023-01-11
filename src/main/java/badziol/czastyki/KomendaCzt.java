package badziol.czastyki;

import badziol.czastyki.EfektyTestowe.EfektTylkoRuch;
import badziol.czastyki.EfektyTestowe.EfektTylkoZadanie;
import badziol.czastyki.EfektyTestowe.skrzydlo.Wing;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class KomendaCzt implements TabExecutor {
    private final Czastyki plugin;
    private ArrayList<String> podpowiedz = new ArrayList<>();
    private EfektTylkoRuch eTylkoRuch;
    private EfektTylkoZadanie eTylkoZadanie;
    private Wing wing;
    private MyEventListener myEvent;//niech zostanie

    public KomendaCzt(Czastyki plugin) {
        this.plugin = plugin;
        wing = new Wing(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("start")) {
                System.out.println("[CZT] - start");
                // 1)
                //eTylkoRuch = new EfektTylkoRuch(plugin);
                //eTylkoRuch.start();

                // 2)
                eTylkoZadanie = new EfektTylkoZadanie(plugin);
                eTylkoZadanie.inicjuj(player);
                eTylkoZadanie.start();

            } else if (args[0].equalsIgnoreCase("stop")) {
                System.out.println("[CZT] - stop");
                // 1)
                //if (eTylkoRuch != null){
                //    eTylkoRuch.stop();
                //}else{
                //    System.out.println("proba zastopowania nie utworzonego efektu");
                //}

                // 2)
                if (eTylkoZadanie != null) eTylkoZadanie.stop();
                else System.out.println("Tylko zadanie - proba zatrzymania nie utworzonego obiektu");

            } else if (args[0].equalsIgnoreCase("test1")) {
                //myEvent = new MyEventListener(plugin); // niech zostanie
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 50);
            } else if (args[0].equalsIgnoreCase("test2")) {
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 50, 2.0d, 0.5d, 2.0d);
            } else if (args[0].equalsIgnoreCase("test3")) {
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 0, 2.0d, 0.5d, 2.0d);
            } else if (args[0].equalsIgnoreCase("zaloz")) {
                wing.addPlayer(player);
            } else if (args[0].equalsIgnoreCase("zdejmij")) {
                wing.removePlayer(player);
            }else{
                System.out.println("[CZT] - nieznany parametr");
            }
        }else{
            System.out.println("[CZT] - zla liczba parametrow");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        podpowiedz.clear();
        if (args.length == 1){
            podpowiedz.add("start");
            podpowiedz.add("stop");
            podpowiedz.add("test1");
            podpowiedz.add("test2");
            podpowiedz.add("test3");
            podpowiedz.add("zaloz");
            podpowiedz.add("zdejmij");
        }
        return podpowiedz;
    };
}

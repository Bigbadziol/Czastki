package badziol.czastyki;

import badziol.czastyki.EfektyTestowe.*;
import badziol.czastyki.EfektyTestowe.Flaga.ArmorStandPixel;
import badziol.czastyki.EfektyTestowe.Flaga.Picture;
import badziol.czastyki.EfektyTestowe.skrzydlo.Skrzydla;
import badziol.czastyki.EfektyTestowe.skrzydlo.StalyPunkt;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KomendaCzt implements TabExecutor {
    private final Czastyki plugin;
    private final ArrayList<String> podpowiedz = new ArrayList<>();
    private EfektTylkoRuch eTylkoRuch;
    private EfektTylkoZadanie eTylkoZadanie;
    private final Skrzydla skrzydla;
    //private MyEventListener myEvent;//niech zostanie

    public KomendaCzt(Czastyki plugin) {
        this.plugin = plugin;
        skrzydla = new Skrzydla(plugin);
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
                skrzydla.dodajGracza(player);
            } else if (args[0].equalsIgnoreCase("zdejmij")) {
                skrzydla.usunGracza(player);
            } else if (args[0].equalsIgnoreCase("e01BloodHelix")) {
                System.out.println("[CZT] - blood helix");
                BloodHelix bh = new BloodHelix(plugin);
                bh.efekt(player);
            } else if (args[0].equalsIgnoreCase("e02FrostLord")) {
                System.out.println("[CZT] - frost lord");
                FrostLord fl = new FrostLord(plugin);
                fl.efekt(player);
            } else if (args[0].equalsIgnoreCase("e03StalyPunkt")) {
                System.out.println("[CZT] - Staly punkt");
                StalyPunkt stalyPunkt = new StalyPunkt(plugin);
                stalyPunkt.efektKlasyka();
                //stalyPunkt.efektBiblioteka();


            }else if (args[0].equalsIgnoreCase("e98ParticlePic")) {
                    System.out.println("[CZT] - Particle picture");
                    Picture p = new Picture(plugin);
                    //p.testPictureConsole();  // TESTY do rysowania po konsoli, inny wzór niż na ekranie.
                    //p.testPictureGame();
                    p.pictureRussia();
                   // p.drawTextBuffer(); //TESTY wypisz stringami zwartość bufora
                                          // (dla czytelności małe liczby nie odpowiadające faktycznym kolorom)
                    p.renderParticles(player);
            }else if (args[0].equalsIgnoreCase("e99ParticleAnim")) {
                System.out.println("[CZT] - Particle animation");
                Picture p = new Picture(plugin);
                p.pictureRussia();
                p.updateFrame(player);

            } else if (args[0].equalsIgnoreCase("e100ArmorStandPic")) {
                System.out.println("[CZT] - Armorstand screen pic");

                Picture pic = new Picture(plugin);
                pic.prepereArmorstands();
                //pic.testPictureGame();
                pic.pictureRussia();
                pic.renderArmorStands();

/*
                char znak = '\u2588';
                //TEST 1
                String str1="";
                str1 += ChatColor.BLACK+Character.toString( znak );
                str1 += ChatColor.BLACK+Character.toString( znak );
                str1 += ChatColor.RED+Character.toString( znak );
                str1 += ChatColor.GREEN+Character.toString( znak );
                str1 += ChatColor.BLUE+Character.toString( znak );
                str1 += ChatColor.BLACK+Character.toString( znak );
                player.sendMessage( "kwadraciki: " + str1 );

                String str2="";
                str2 += net.md_5.bungee.api.ChatColor.of("#ff0000")+Character.toString( znak );
                str2 += net.md_5.bungee.api.ChatColor.of("#800000")+Character.toString( znak );
                str2 += net.md_5.bungee.api.ChatColor.of("#400000")+Character.toString( znak );
                str2 += net.md_5.bungee.api.ChatColor.of("#ffff00")+Character.toString( znak );
                str2 += net.md_5.bungee.api.ChatColor.of("#ff00ff")+Character.toString( znak );
                str2 += net.md_5.bungee.api.ChatColor.of("#00ffff")+Character.toString( znak );
                player.sendMessage(str2) ;

*/
            } else if (args[0].equalsIgnoreCase("e101ArmorStandAnim")) {
                System.out.println("[CZT] - Armorstand screen anim");
                Picture pic = new Picture(plugin);
                pic.prepereArmorstands();
                pic.pictureRussia();
                pic.animArmorStands();

            }
            else{
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
            podpowiedz.add("e01BloodHelix");
            podpowiedz.add("e02FrostLord");
            podpowiedz.add("e03StalyPunkt");
            podpowiedz.add("e98ParticlePic");
            podpowiedz.add("e99ParticleAnim");
            podpowiedz.add("e100ArmorStandPic");
            podpowiedz.add("e101ArmorStandAnim");
        }
        return podpowiedz;
    }
}

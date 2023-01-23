package badziol.czastyki;

import com.lkeehl.tagapi.TagAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
//Przewodnik : https://www.spigotmc.org/threads/comprehensive-particle-spawning-guide-1-13-1-19.343001/
//Uzupełnienie tematu : https://minecraft.fandom.com/wiki/Particles
//Ciekawa biblioteka : https://github.com/ByteZ1337/ParticleLib
//https://github.com/Tigeax/CustomWings

//Efekt nad swinia
// /execute at @e[type=pig] run particle minecraft:dust 1 0.3 0 2 ~ ~1.5 ~ 0 0 0 1 1
// /particle minecraft:item rotten_flesh 274.50 104.06 92.50 0 0 0 0.4 30
// /particle minecraft:item porkchop 274.50 104.06 92.50 0 0 0 0.3 100

//Force true - oznacza ze gracz zobaczy cząstki z znacznie większej odległości

public final class Czastyki extends JavaPlugin {
    private static Czastyki instance;
    private final static String VERSION = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit", "").replace(".", "");


    @Override
    public void onEnable() {
        setInstance(this);
        //TagAPI.onEnable(this);

        System.out.println("Czastki versja 1.23");
        System.out.println("Komendy :");
        System.out.println("/cz - menu glowne interfejsu czastek.");
        System.out.println("/czt [start][stop] - testowanie efektu bezposrednio zdef. w klasie KomendaCzt");

        MenuGui menu = new MenuGui();
        menu.przygotujInterfejsy();
       // getServer().getPluginManager().registerEvents(new MenuListener(), this);
        //getServer().getPluginManager().registerEvents(new OnMoveTestListener(),this); //UWAGA UWAGA

        //getServer().getPluginManager().registerEvents(new EhVector(this),this);

        this.getCommand("cz").setExecutor(new KomendaCz());
        this.getCommand("czt").setExecutor(new KomendaCzt(this));
    }

    @Override
    public void onDisable() {
       // TagAPI.onDisable();

    }

    private static void setInstance(Czastyki instance) {
        Czastyki.instance = instance;
    }

    public static Czastyki getInstance() {
        return instance;
    }

    public String getServerVersion() {
        return VERSION;
    }

}

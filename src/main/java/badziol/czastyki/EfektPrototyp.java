package badziol.czastyki;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

// Uwaga !!!
// Zarąbiście łatwo o błąd ! dwie bardzo podobne metody ale inny sposób wywołania (nie przeciążenie synek)
//plugin.getServer().getPluginManager().registerEvent(new nazwaKlasyListenera(),plugin); <= tak rejestrowaliśmy zawsze w głównej klasie pluginu
//plugin.getServer().getPluginManager().registerEvents(this, plugin); <= tak robimy to tu
//

enum CzasTrwaniaEfektu {jednorazowy,okreslonyCzas,ciagly};

public abstract class EfektPrototyp implements Listener {
    protected  Czastyki plugin;
    protected  boolean onMoveAktywne = false;
    protected  boolean zadanieAktywne = false;

    protected BukkitRunnable zadanie = null;
    protected  long zadanieOpoznienie = 0L;     // minecraftowe ticki
    protected  long zadanieOkresCzasowy = 2L;   // minecraftowe ticki

    //todo: efekt na konkretynm zywym bycie
    //okreslisc czas trwania efektu, cos na zasadzie jednorazowy pokaza, animacja przez n sekund, animacja nonstop

    protected LivingEntity byt; //nasz gracz/ wilk cokolwiek zywego?

    public String nazwa="efekt_prototyp";

    /**
     *  Constructor
     * @param plugin - by mieć dostęp zawsze do 'korzenia'
     */
    public EfektPrototyp(Czastyki plugin){
        this.plugin = plugin;
    }

    /**
     * Metoda do nadpisania przez konkretny efekt
     * Idea : w nowym efekcie ma być : zadanie = new BukkitRunnable() { ..... }
     */
    public void przygotujZadanie(){
        System.out.println("[EP] - przygotowanie pustego zadania");
        zadanie = new BukkitRunnable() {
            @Override
            public void run() {
                //ma byc puste
                //nic nie dopisywac
            }
        };
    }

    /**
     *  Metoda do nadpisania przez konkretny efekt
     */
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        //ma byc puste
        //nic nie dopisywac
    }

    //todo : koncepcja inicjaciji
    public void inicuiuj (LivingEntity byt, CzasTrwaniaEfektu czasTrwania , long czas ){

    }
    /**
     *  Wykonaj wszystkie niezbędne kroki do uruchomienia efektu
     */
    public void start(){
        //zadanie
        System.out.println("[EP] - START : "+nazwa);
        System.out.println("[EP] - onMoveAktywne : "+onMoveAktywne);
        System.out.println("[EP] - zadanieAktywne :"+zadanieAktywne);
        //run task
        if (zadanieAktywne) {
            przygotujZadanie();
            System.out.println("[EP] - zadanie : " +zadanie);
            if (zadanie != null) zadanie.runTaskTimer(plugin, zadanieOpoznienie, zadanieOkresCzasowy);
        }
        //onMove listener
        if (onMoveAktywne){
            plugin.getServer().getPluginManager().registerEvents(this, plugin);
        }
    }

    /**
     * Wykonaj wszystkie niezbędnie kroki do zatrzymania efektu
     */
    public void stop(){
        System.out.println("[EP] - STOP : "+nazwa);
        if (zadanieAktywne){
            System.out.println("[EP] - zatrzymanie zadania");
            if (zadanie != null)  zadanie.cancel();
        }

        if (onMoveAktywne){
            System.out.println("[EP] - zatrzymanie onMove");
            PlayerMoveEvent.getHandlerList().unregister(this);
        }
    }

    /**
     * @return Id zadania przydzielonego przez Bukkit, w przypadku błędu : -1
     */
    public int zadnieId(){
        if (zadanie != null)  return  zadanie.getTaskId();
        else return -1;
    }
}

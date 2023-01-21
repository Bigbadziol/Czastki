package badziol.czastyki.EfektyTestowe.skrzydlo;

import badziol.czastyki.Czastyki;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Skrzydla {
    private final Czastyki plugin;
    private final SkrzydloKonfig skrzydloKonfig;
    private final ArrayList<Player> skrzydlaciGracze;
    private BukkitTask zadanieSkrzydla;
    private FazaLotu fazaLotu= FazaLotu.BEZPIECZNA;

    /**
     * Konstruktor
     * @param plugin - wymagana referencja do 'korzenia' pluginu
     */
    public Skrzydla(Czastyki plugin) {
        this.plugin = plugin;
        this.skrzydloKonfig = new SkrzydloKonfig();
        this.skrzydlaciGracze = new ArrayList<>();
    }

    /**
     * Dodaj gracza do listy posiadaczy skrzydeł.
     * Pojawienie się na liście choćby jednego gracza uruchomi proces (zadanie) obsługi animacji
     * @param player - docelowy gracz
     */
    public void dodajGracza(Player player) {//CWPlayer bylo
        skrzydlaciGracze.add(player);
        System.out.println("[Skrzydla] - dodano gracza : "+player.getName());
        if (skrzydlaciGracze.size() == 1){
            System.out.println("Uruchamiam proces odpowiedzialny za animacje skrzydel.");
            this.zadanieStrzydlaStart();
        }
    }

    /**
     * Usuń gracza z listy posiadaczy skrzydeł
     * @param player - docelowy gracz
     */
    public void usunGracza(Player player) {
        System.out.println("[Skrzydla] - usunieto gracza : "+player.getName());
        skrzydlaciGracze.remove(player);
    }

    /**
     * Zadanie odpowiedzialne za ...
     * W przypadku braku osób ze skrzydłami, zadanie samo się zatrzyma.
     */
    private void zadanieStrzydlaStart() {
        zadanieSkrzydla = new BukkitRunnable() {
            boolean zmienKierRuchu = false; //zmiana kierunku ruchu skrzydel
            int animStan = skrzydloKonfig.katPoczatek;
            @Override
            public void run() {
                if (skrzydlaciGracze.isEmpty()) this.cancel(); //nikt nie ma skrzydeł to zatrzymaj zadanie
                if (skrzydloKonfig.animacjaSkrzydel) {
                    animStan = zmienKierRuchu ? animStan - skrzydloKonfig.animacjaSzybkosc
                            : animStan + skrzydloKonfig.animacjaSzybkosc;
                    // zmien kierunek przy skrajnych pozycjach skrzydła
                    if (animStan >= skrzydloKonfig.katKoniec)  zmienKierRuchu = true;
                    if (animStan <= skrzydloKonfig.katPoczatek) zmienKierRuchu = false;
                }
                // Uaktualnij animację wszystkim graczom z skrzydłami
                //TODO : przeniesc wartość , na pałę wklepana bezpieczna
                skrzydlaciGracze.forEach((posiadacz) -> rysujSkrzydla(posiadacz,FazaLotu.NIEBEZPIECZNA, animStan));
            }
        }.runTaskTimerAsynchronously(plugin, 0, skrzydloKonfig.zadanieCzas);
    }

    private void rysujSkrzydla(Player posiadacz, FazaLotu faza,  int animStan) {
        Location pozycjaSkrzydel = posiadacz.getLocation();
        if (!mogePokazacSkrzydla(posiadacz)) return;
        float bodyYaw = wezObrotCiala(posiadacz);
        pozycjaSkrzydel.setYaw(bodyYaw);
        if (posiadacz.isSneaking() && !posiadacz.isFlying()) {
            pozycjaSkrzydel = pozycjaSkrzydel.add(0, -0.25, 0);
        }
        double yawRad = Math.toRadians(pozycjaSkrzydel.getYaw());
        double xOffset = Math.cos(yawRad) * skrzydloKonfig.poczatekPoziom;
        double zOffset = Math.sin(yawRad) * skrzydloKonfig.poczatekPoziom;
        pozycjaSkrzydel = pozycjaSkrzydel.add(xOffset, 0, zOffset);

        //Na podstawie współrzędnych narysuj skrzydła
        if (faza == FazaLotu.BEZPIECZNA) {
            for (double[] wspolrzedne : skrzydloKonfig.czastkiBezpiecznyLot.keySet()) {
                SkrzydloCzastka skrzydloCzastka = skrzydloKonfig.czastkiBezpiecznyLot.get(wspolrzedne);
                double x = wspolrzedne[0];
                double y = wspolrzedne[1];
                //odrysuj lewe skrzydlo
                Location czastkaLewo = obliczCzastkaPrzestrzen(pozycjaSkrzydel, x, y, SkrzydloStrona.LEWA, animStan);
                skrzydloCzastka.rysujCzastke(czastkaLewo, posiadacz, SkrzydloStrona.LEWA);
                //odrysuj prawe skrzydlo
                Location czastkaPrawo = obliczCzastkaPrzestrzen(pozycjaSkrzydel, x, y, SkrzydloStrona.PRAWA, animStan);
                skrzydloCzastka.rysujCzastke(czastkaPrawo, posiadacz, SkrzydloStrona.PRAWA);
            }
        }else{ //z automatu niebezpieczna
            for (double[] wspolrzedne : skrzydloKonfig.czastkiNiebezpiecznyLot.keySet()) {
                SkrzydloCzastka skrzydloCzastka = skrzydloKonfig.czastkiNiebezpiecznyLot.get(wspolrzedne);
                double x = wspolrzedne[0];
                double y = wspolrzedne[1];
                //odrysuj lewe skrzydlo
                Location czastkaLewo = obliczCzastkaPrzestrzen(pozycjaSkrzydel, x, y, SkrzydloStrona.LEWA, animStan);
                skrzydloCzastka.rysujCzastke(czastkaLewo, posiadacz, SkrzydloStrona.LEWA);
                //odrysuj prawe skrzydlo
                Location czastkaPrawo = obliczCzastkaPrzestrzen(pozycjaSkrzydel, x, y, SkrzydloStrona.PRAWA, animStan);
                skrzydloCzastka.rysujCzastke(czastkaPrawo, posiadacz, SkrzydloStrona.PRAWA);
            }
        }
    }

    /**
     *  Określ wszystkie warunki wykluczające pokazanie skrzydeł np:
     *  czy gracz żyje, jest widoczny, śpi , pływa, itp.
     * @param posiadacz - badany gracz
     * @return true- można wyświetlić
     */
    private boolean mogePokazacSkrzydla(Player posiadacz) {
        // Gracz jest martwy
        if (posiadacz.isDead()) return false;
        // Gracz w trybie spectatora.
        if (posiadacz.getGameMode().equals(GameMode.SPECTATOR)) return false;
        // Gracz jest w 'inny' sposób niewidoczny.
        if (czyGraczZniknal(posiadacz)) return false;
        // Gracz jest niewidoczny
        if (posiadacz.isInvisible()) return false;
        // Gracz ma na sobie potiona niewidzialności
        if (posiadacz.hasPotionEffect(PotionEffectType.INVISIBILITY)) return false;
        // Gracz śpi
        if (posiadacz.isSleeping()) return false;
        // Gracz jest na zwierzęciu / pojeździe.
        if (posiadacz.isInsideVehicle()) return false;
        // Gracz płynie
        if (posiadacz.isSwimming()) return false;
        // Gracz szybuje- to coś innego niż lot.
        if (posiadacz.isGliding()) return false;
        //można pokazać skrzydła
        return true;
    }

    /**
     *  Minecraft posiada jeszcze inny wariant niewidzialności, określany jako 'zniknięcie'.
     *  Sprawdzamy ten parametr.
     * @param player badany gracz
     * @return 'zniknięty' true
     */
    public static boolean czyGraczZniknal(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    /**
     * Na podstawie metadanych pobierz oś obrotu ciała. Uwaga! Plugin pobiera pole 'aY', które przechowuje tę wartość.
     * W wersjach 1.19 oraz 1.18 to jest właśnie to pole. Dla przykładu : w 1.17 to 'aX' , z kolei w 1.16.3 to 'aX' ,
     * 1.16.2 to 'aA', a 1.16.1 to 'aH'.
     * @param livingEntity - żywy byt
     * @return oś obrotu gracza lub null
     */
    public static Float wezObrotCiala(LivingEntity livingEntity) {
        try {
            String version = Czastyki.getInstance().getServerVersion();
            Class<?> entity = Class.forName("org.bukkit.craftbukkit." + version+ ".entity.CraftLivingEntity");
            Method handle = entity.getMethod("getHandle");
            Object nmsEntity = handle.invoke(livingEntity);
            Field rotacja = nmsEntity.getClass().getField("aY");
            return (Float) rotacja.get(nmsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Oblicz faktyczną pozycję cząstki w przestrzeni na podstawie danych
     */
    private Location obliczCzastkaPrzestrzen(Location loc, double x, double y, SkrzydloStrona skrzydloStrona, int animStan) {
        Location czastkaPozycja = loc.clone();
        float yaw = czastkaPozycja.getYaw();
        if (skrzydloStrona == SkrzydloStrona.LEWA)  yaw = yaw - animStan;
        if (skrzydloStrona == SkrzydloStrona.PRAWA)  yaw = yaw + 180 + animStan;
        double yawRad = Math.toRadians(yaw);
        Vector vector = new Vector(Math.cos(yawRad) * x, y, Math.sin(yawRad) * x);
        czastkaPozycja.add(vector);
        czastkaPozycja.setYaw((float) Math.toDegrees(yawRad));
        return czastkaPozycja;
    }
}

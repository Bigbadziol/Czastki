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

public class Wing {
    private final Czastyki plugin;
    private final WingConfig wingConfig;
    private final ArrayList<Player> playersWithWing;  //Orginalnie CWPLAYER
    private BukkitTask wingRunnable;

    public Wing(Czastyki plugin) {
        this.wingConfig = new WingConfig();
        this.plugin = plugin;
        this.playersWithWing = new ArrayList<>();
    }


    public void addPlayer(Player cwPlayer) {//CWPlayer bylo
        playersWithWing.add(cwPlayer);
        System.out.println("[Skrzydla] - dodano gracza : "+cwPlayer.getName());
        if (playersWithWing.size() == 1){
            System.out.println("Uruchamiam proces odpowiedzialny za animacje skrzydel.");
            this.startWingRunnable();
        }
    }

    public void removePlayer(Player player) {
        System.out.println("[Skrzydla] - usunieto gracza : "+player.getName());
        playersWithWing.remove(player);
    }


    private void startWingRunnable() {
        wingRunnable = new BukkitRunnable() {
            boolean flapDirectionSwitch = false; //zmiana kierunku ruchu skrzydel
            int animationState = wingConfig.startOffset;
            @Override
            public void run() {
                if (playersWithWing.isEmpty()) this.cancel(); //nikt nie ma skrzydeł to zatrzymaj zadanie

                if (wingConfig.wingAnimation) {
                    animationState = flapDirectionSwitch ? animationState - wingConfig.wingFlapSpeed
                            : animationState + wingConfig.wingFlapSpeed;
                    // zmien kierunek przy skrajnych pozycjach skrzydła
                    if (animationState >= wingConfig.stopOffset)  flapDirectionSwitch = true;
                    if (animationState <= wingConfig.startOffset) flapDirectionSwitch = false;
                }
                // Uaktualnij animację wszystkim graczom z skrzydłami
                playersWithWing.forEach((wingOwner) -> showWing(wingOwner, animationState));
            }
        }.runTaskTimerAsynchronously(plugin, 0, wingConfig.wingTimer);
    }

    private void showWing(Player wingOwner, int animationState) {// Bylo CwPlayer
        Location wingLoc = wingOwner.getLocation();
        if (!shouldAttachedWingBeSpawned(wingOwner)) return;
        float bodyYaw = getBodyRotation(wingOwner);
        wingLoc.setYaw(bodyYaw);
        if (wingOwner.isSneaking() && !wingOwner.isFlying()) {
            wingLoc = wingLoc.add(0, -0.25, 0);
        }
        spawnWingForPlayer(wingLoc,wingOwner,animationState);
    }

    private boolean shouldAttachedWingBeSpawned(Player cwWingOwner) { //Bylo CWPlayer
        Player wingOwner = cwWingOwner.getPlayer();
        // Gracz jest martwy
        if (wingOwner.isDead()) return false;
        // Gracz w trybie spectatora.
        if (wingOwner.getGameMode().equals(GameMode.SPECTATOR)) return false;
        // Gracz jest w 'inny' sposób niewidoczny.
        if (isPlayerVanished(wingOwner)) return false;
        // Gracz jest niewidoczny
        if (wingOwner.isInvisible()) return false;
        // Gracz ma na sobie potiona niewidzialności
        if (wingOwner.hasPotionEffect(PotionEffectType.INVISIBILITY)) return false;
        // Gracz śpi
        if (wingOwner.isSleeping()) return false;
        // Gracz jest na zwierzęciu / pojeździe.
        if (wingOwner.isInsideVehicle()) return false;
        // Gracz płynie
        if (wingOwner.isSwimming()) return false;
        // Gracz szybuje- to coś innego niż lot.
        if (wingOwner.isGliding()) return false;
        return true;
    }

    //Ukradzione z Util
    public static boolean isPlayerVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    // Ukradzione z NMSSupport.
    public static Float getBodyRotation(LivingEntity livingEntity) {
        try {
            String version = Czastyki.getInstance().getServerVersion();//org
            Class<?> entity = Class.forName("org.bukkit.craftbukkit." + version+ ".entity.CraftLivingEntity");
            Method handle = entity.getMethod("getHandle");
            Object nmsEntity = handle.invoke(livingEntity);
            Field bodyRotation = nmsEntity.getClass().getField("aY");
            return (Float) bodyRotation.get(nmsEntity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public void spawnWingForPlayer(Location wingLoc, Player player, int animationState){
        wingLoc = wingLoc.clone();
        // Change the horzontal starting location based on the horizontal start offset and direction of the wing
        double yawRad = Math.toRadians(wingLoc.getYaw());
        double xOffset = Math.cos(yawRad) * wingConfig.startHorizontalOffset;
        double zOffset = Math.sin(yawRad) * wingConfig.startHorizontalOffset;
        wingLoc = wingLoc.add(xOffset, 0, zOffset);

        // Loop through all the coordinates of the wing and spawn a particle for both
        // the left and right part at that location
        for (double[] coordinate : wingConfig.particleCoordinates.keySet()) {

            SkrzydloCzastka skrzydloCzastka = wingConfig.particleCoordinates.get(coordinate);
            double x = coordinate[0];
            double y = coordinate[1];

            //todo: tu jest fragment odpowiedzialny za tworzenie lewego i prawego skrzydla
            Location particleLocLeft = getParticleSpawnLoc(wingLoc, x, y, WingSide.LEFT, animationState);
            skrzydloCzastka.rysujCzastke(particleLocLeft, player, WingSide.LEFT);
            Location particleLocRight = getParticleSpawnLoc(wingLoc, x, y, WingSide.RIGHT, animationState);
            skrzydloCzastka.rysujCzastke(particleLocRight, player, WingSide.RIGHT);

        }
    }

    /**
     * Return the location the particle should be spawned at
     * Relative to the location the wing is spawned at
     * Based on the x and y offset from the center of the wing and which side of the Wing the particle is on
     *
     * @param loc
     * @param x
     * @param y
     * @param wingSide
     * @param animationState
     * @return
     */
    private Location getParticleSpawnLoc(Location loc, double x, double y, WingSide wingSide, int animationState) {

        Location wingParticleLoc = loc.clone();
        float yaw = wingParticleLoc.getYaw();

        if (wingSide == WingSide.LEFT)  yaw = yaw - animationState;
        if (wingSide == WingSide.RIGHT)  yaw = yaw + 180 + animationState;

        double yawRad = Math.toRadians(yaw);
        Vector vector = new Vector(Math.cos(yawRad) * x, y, Math.sin(yawRad) * x);
        wingParticleLoc.add(vector);
        wingParticleLoc.setYaw((float) Math.toDegrees(yawRad)); // For directional particles

        return wingParticleLoc;
    }
}

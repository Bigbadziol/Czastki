package badziol.czastyki.EfektyTestowe.skrzydlo;

import badziol.czastyki.Czastyki;
import org.bukkit.Bukkit;
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
    private Czastyki plugin;
    private WingConfig wingConfig;
    private ArrayList<Player> playersWithWing;  //Orginalnie CWPLAYER
    private BukkitTask wingRunnable;

    public Wing(Czastyki plugin) {
        this.wingConfig = new WingConfig();
        this.plugin = plugin;
        //this.config = plugin.getConfig();
        this.playersWithWing = new ArrayList<>();
    }


    public void addPlayer(Player cwPlayer) {//CWPlayer bylo
        playersWithWing.add(cwPlayer);
        // If there where no players that had the wing equipped before, start the wing
        // runnable
        if (playersWithWing.size() == 1){
            System.out.println("Starting wing runable.");
            this.startWingRunnable();
        }
    }

    public void removePlayer(Player Player) {
        playersWithWing.remove(Player);
    }


    private void startWingRunnable() {

        wingRunnable = new BukkitRunnable() {

            boolean flapDirectionSwitch = false;
            int animationState = wingConfig.startOffset;

            @Override
            public void run() {

                // If no players have this wing equipped stop the timer
                if (playersWithWing.isEmpty()) {
                    this.cancel();
                }

                // If the wing should be animated change the offsetDegrees
                // To go back and forth between the start and stop offset
                if (wingConfig.wingAnimation) {

                    animationState = flapDirectionSwitch ? animationState - wingConfig.wingFlapSpeed
                            : animationState + wingConfig.wingFlapSpeed;

                    if (animationState >= wingConfig.stopOffset)
                        flapDirectionSwitch = true;

                    if (animationState <= wingConfig.startOffset)
                        flapDirectionSwitch = false;
                }

                // Loop through all the players that have the wing active, and spawn their wing
                playersWithWing.forEach((wingOwner) -> showWing(wingOwner, animationState));

            }
        }.runTaskTimerAsynchronously(plugin, 0, wingConfig.wingTimer);
    }

    private void showWing(Player wingOwner, int animationState) {// Bylo CwPlayer
        //if (wingOwner.isPreviewingWing()) {
        //    Location wingLoc = wingOwner.getPreviewWingLocation();
            //spawnPreviewWing(wingLoc, animationState);
       // } else {
            spawnAttachedWing(wingOwner, animationState);
       // }

    }

    private void spawnAttachedWing(Player cwWingOwner, int animationState) {//Bylo CWPlayer

        Player wingOwner = cwWingOwner;
        Location wingLoc = wingOwner.getLocation();

        // Check if the wing should be shown in this world
        //if (!shownInWorld(wingLoc.getWorld())) {
        //    return;
       // }

        // Don't show the wing if the wingOwner is in certain states
        if (!shouldAttachedWingBeSpawned(cwWingOwner)) {
            return;
        }


        // Instead of using the Yaw of the head of the player we will try to use the Yaw
        // of the player's body
        float bodyYaw = getBodyRotation(wingOwner);
        wingLoc.setYaw(bodyYaw);

        // Shift the wing down if the player is sneaking
        if (wingOwner.isSneaking() && !wingOwner.isFlying()) {
            wingLoc = wingLoc.add(0, -0.25, 0);
        }

        ArrayList<Player> playersToShowWing = getPlayersWhoSeeAttachedWing(cwWingOwner);

        spawnWingForPlayers(wingLoc, playersToShowWing, animationState);
    }




    private boolean shouldAttachedWingBeSpawned(Player cwWingOwner) { //Bylo CWPlayer
        Player wingOwner = cwWingOwner.getPlayer();
        // Not if dead
        if (wingOwner.isDead()) return false;

        // Not if in spectator or in vanish
        if (wingOwner.getGameMode().equals(GameMode.SPECTATOR) || isPlayerVanished(wingOwner)) return false;

        // Not when they have invisibility potion effect
        if (wingOwner.hasPotionEffect(PotionEffectType.INVISIBILITY)) return false;

        // Not when sleeping
        if (wingOwner.isSleeping()) return false;

        // Not when in vehicle
        if (wingOwner.isInsideVehicle()) return false;

        // Not when swimming
        if (wingOwner.isSwimming()) return false;

        // Not when gliding gliding
        if (wingOwner.isGliding()) return false;

        return true;
    }

    //Ukradzione z Util
    public static boolean isPlayerVanished(Player player) {
        for (MetadataValue meta : player.getMetadata("vanished")) {
            if (meta.asBoolean())
                return true;
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




    private ArrayList<Player> getPlayersWhoSeeAttachedWing(Player cwWingOwner) {//CWPlayer

        Player wingOwner = cwWingOwner.getPlayer();

        Location wingOwnerLoc = wingOwner.getLocation();
        ArrayList<Player> playersWhoCanSeeWing = new ArrayList<>();

        //todo: zerknac w config w te max pitch
        // Only spawn for the wing owner if they don't look to far down.
        //if (wingOwnerLoc.getPitch() < config.getWingMaxPitch()) {
        //    playersWhoCanSeeWing.add(wingOwner.getPlayer());
        //}

        // Loop thought all the online players
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

            // Skip if it is the wingOwner
            //if (onlinePlayer == wingOwner.getPlayer()) {
             //   continue;
            //}

            // Skip if the player is not in the same world as the wing
            if (!(onlinePlayer.getWorld() == wingOwnerLoc.getWorld()))
                continue;



            Location onlinePlayerLoc = onlinePlayer.getLocation();

            // Skip if the player is more then the wingViewDistance away from the wing
            //if (onlinePlayerLoc.distance(wingOwnerLoc) > config.getWingViewDistance())
            //    continue;

            playersWhoCanSeeWing.add(onlinePlayer);
        }
        return playersWhoCanSeeWing;
    }


//BYLO PRYWATNE
    public void spawnWingForPlayers(Location wingLoc, ArrayList<Player> players, int animationState) {

        wingLoc = wingLoc.clone();

        // Change the horzontal starting location based on the horizontal start offset and direction of the wing
        double yawRad = Math.toRadians(wingLoc.getYaw());
        double xOffset = Math.cos(yawRad) * wingConfig.startHorizontalOffset;
        double zOffset = Math.sin(yawRad) * wingConfig.startHorizontalOffset;
        wingLoc = wingLoc.add(xOffset, 0, zOffset);

        // Loop through all the coordinates of the wing and spawn a particle for both
        // the left and right part at that location
        for (double[] coordinate : wingConfig.particleCoordinates.keySet()) {

            WingParticle wingParticle = wingConfig.particleCoordinates.get(coordinate);
            double x = coordinate[0];
            double y = coordinate[1];

            //todo: tu jest fragment odpowiedzialny za tworzenie lewego i prawego skrzydla
/*
            if (!wingConfig.onlyOneSide) {
                // Spawn left side
                Location particleLocLeft = getParticleSpawnLoc(wingLoc, x, y, WingSide.LEFT, animationState);
                wingParticle.spawnParticle(particleLocLeft, players, WingSide.LEFT);
            }
*/
            // Spawn left side
            Location particleLocLeft = getParticleSpawnLoc(wingLoc, x, y, WingSide.LEFT, animationState);
            wingParticle.spawnParticle(particleLocLeft, players, WingSide.LEFT);

            // Spawn right side
            Location particleLocRight = getParticleSpawnLoc(wingLoc, x, y, WingSide.RIGHT, animationState);
            wingParticle.spawnParticle(particleLocRight, players, WingSide.RIGHT);
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

        if (wingSide == WingSide.LEFT)
            yaw = yaw - animationState;
        if (wingSide == WingSide.RIGHT)
            yaw = yaw + 180 + animationState;

        double yawRad = Math.toRadians(yaw);
        Vector vector = new Vector(Math.cos(yawRad) * x, y, Math.sin(yawRad) * x);
        wingParticleLoc.add(vector);
        wingParticleLoc.setYaw((float) Math.toDegrees(yawRad)); // For directional particles

        return wingParticleLoc;
    }
}

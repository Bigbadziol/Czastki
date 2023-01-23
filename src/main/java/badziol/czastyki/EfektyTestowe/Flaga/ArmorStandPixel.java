package badziol.czastyki.EfektyTestowe.Flaga;

import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import java.util.Objects;

public class ArmorStandPixel {
    private final String worldName="world";
    private final ArmorStand as;
    //private int r,g,b;


    private String hexValWithZero(int number){
        String res = Integer.toHexString(number);
        if (res.length() == 1) res = "0"+res;
        return res;
    }

    private String toHexString( int r,int g, int b){
        return "#" + hexValWithZero(r) + hexValWithZero(g) + hexValWithZero(b);
    }

    public void setColor(int r, int g, int b){
        as.setCustomName(net.md_5.bungee.api.ChatColor.of(toHexString(r,g,b))+Character.toString('\u2588'));
    }

    public ArmorStandPixel(double x, double y, double z){
        World w = Bukkit.getWorld(worldName);
        Location loc = new Location(w,x,y,z);
        as = (ArmorStand) Objects.requireNonNull(Bukkit.getWorld(worldName)).spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setCustomNameVisible(true);
        as.setCustomName(net.md_5.bungee.api.ChatColor.of("#000000")+Character.toString('\u2588'));

    }

    public ArmorStandPixel(double x, double y, double z,int r,int g, int b) {
        World w = Bukkit.getWorld(worldName);
        Location loc = new Location(w,x,y,z);
        as = (ArmorStand) Objects.requireNonNull(Bukkit.getWorld(worldName)).spawnEntity(loc, EntityType.ARMOR_STAND);
        as.setVisible(false);
        as.setGravity(false);
        as.setCustomNameVisible(true);
        setColor(r,g,b);
    }

    public void free(){
        as.setHealth(0);
    }

}

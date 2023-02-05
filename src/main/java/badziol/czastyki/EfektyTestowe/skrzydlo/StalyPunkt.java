package badziol.czastyki.EfektyTestowe.skrzydlo;

import badziol.czastyki.Czastyki;
import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.texture.BlockTexture;

public class StalyPunkt {
    private final Czastyki plugin;
    private final double pX = 253;
    private final double pY = 103;
    private final double pZ = 113;

    public StalyPunkt(Czastyki plugin) {
        this.plugin = plugin;
    }

    /**
     * Wywolanie klasyczne bez biblioteki
     */
    public void efektKlasyka(){
        ItemStack blok2 = new ItemStack(Material.REDSTONE_BLOCK);
        World swiat = Bukkit.getWorld("world");
        Location lokacja = new Location(swiat,pX,pY,pZ);
        Bukkit.getWorld("world").spawnParticle(Particle.ITEM_CRACK, lokacja, 50, blok2);
    }

    /**
     * Wywołanie za pomocą biblioteki ParticlesLib - bibliotekę należy zaiportować w następujący sposób:
     * Do pliku POM.xml
     * Do sekcji :  <dependencies> ....  </dependencies>
     * należy dodać :
     *         <dependency>
     *             <groupId>xyz.xenondevs</groupId>
     *             <artifactId>particle</artifactId>
     *             <version>1.8.1</version>
     *         </dependency>
     *  Następnie wcisnąć guzik synchronizacji projektu bo nie zadziała !
     */

    public void efektBiblioteka(){
        World swiat = Bukkit.getWorld("world");
        Location lokacja = new Location(swiat,pX,pY,pZ);
        new ParticleBuilder(ParticleEffect.ITEM_CRACK, lokacja)
                .setParticleData(new BlockTexture(Material.REDSTONE_BLOCK))
                .setAmount(10)
                .display();
    }
}

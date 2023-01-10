package badziol.czastyki;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;


enum KtoreMenu {Glowne, Directional, Colored,Material,Vibration};

public class MenuGui {
    public final String menuGlowneNaglowek = ChatColor.BLUE+""+ChatColor.BOLD+"Menu glowne";
    public final String menuDirectionalNaglowek = ChatColor.BLUE+""+ChatColor.BOLD+"Czastki : directional";
    public final String menuColoredNaglowek = ChatColor.BLUE+""+ChatColor.BOLD+"Czastki : colored";
    public final String menuMaterialNaglowek = ChatColor.BLUE+""+ChatColor.BOLD+"Czastki : material";
    public final String menuVibrationNaglowek = ChatColor.BLUE+""+ChatColor.BOLD+"Czastki : vibration";
    private final ArrayList<String> listaNaglowkowMenu = new ArrayList<>();

    private static Inventory MENU_WYSWIETLANE;
    private static Inventory MENU_GLOWNE;
    private static Inventory MENU_DIRECTIONAL; // typ cząstek : directional
    private static Inventory MENU_COLORED;     // typ cząstek : colored
    private static Inventory MENU_MATERIAL;    // typ cząstek : material
    private static Inventory MENU_VIBRATION;   // typ cząstek : vibration

    private static KtoreMenu ktoreAktywne;

    public MenuGui() {
        //Utworz listę wszystkich naszych naglowkow poszczególnych menu by latwiej było odnaleźć okna w
        //zdarzeniu onMenuClick
        listaNaglowkowMenu.add(menuGlowneNaglowek);
        listaNaglowkowMenu.add(menuDirectionalNaglowek);
        listaNaglowkowMenu.add(menuColoredNaglowek);
        listaNaglowkowMenu.add(menuMaterialNaglowek);
        listaNaglowkowMenu.add(menuVibrationNaglowek);
    }

    private  void przygotujMenuGlowne(){
        Inventory inventory = Bukkit.createInventory(null,9,menuGlowneNaglowek);
        //0) Czastki directional
        ItemStack ikona =  new ItemStack(Material.GOLD_BLOCK);
        ItemMeta meta = ikona.getItemMeta();
        meta.setDisplayName("Typ: directional");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        //1) Czastki colored
        ikona =  new ItemStack(Material.GOLD_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("Typ: colored");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        //2) Czastki material
        ikona =  new ItemStack(Material.GOLD_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("Typ: material");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        //2) Czastki vibration
        ikona =  new ItemStack(Material.GOLD_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("Typ: vibration");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);

        MENU_GLOWNE = inventory;
    }

    private void przygotujMenuDirectional(){
        Inventory inventory = Bukkit.createInventory(null,36, menuDirectionalNaglowek);
        // 0) BUBBLE_COLUMN_UP (directional)
        ItemStack ikona =  new ItemStack(Material.COPPER_BLOCK);
        ItemMeta meta = ikona.getItemMeta();
        meta.setDisplayName("BUBBLE_COLUMN_UP");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 1) BUBBLE_POP (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("BUBBLE_POP");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 2) CAMPFIRE_COZY_SMOKE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("CAMPFIRE_COZY_SMOKE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 3) CAMPFIRE_SIGNAL_SMOKE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("CAMPFIRE_SIGNAL_SMOKE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 4) CLOUD (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("CLOUD");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 5) CRIT (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("CRIT");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 6) CRIT_MAGIC (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("CRIT_MAGIC");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 7) DAMAGE_INDICATOR (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("DAMAGE_INDICATOR");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 8) DRAGON_BREATH (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("DRAGON_BREATH");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 9) ELECTRIC_SPARK (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("ELECTRIC_SPARK");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 10) ENCHANTMENT_TABLE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("ENCHANTMENT_TABLE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 11) END_ROD (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("END_ROD");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 12) EXPLOSION_NORMAL (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("EXPLOSION_NORMAL");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 13) FIREWORKS_SPARK (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("FIREWORKS_SPARK");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 14) FLAME (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("FLAME");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 15) NAUTILUS (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("NAUTILUS");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 16) PORTAL (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("PORTAL");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 17) REVERSE_PORTAL (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("REVERSE_PORTAL");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 18) SCRAPE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SCRAPE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 19) SCULK_CHARGE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SCULK_CHARGE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 20) SCULK_CHARGE_POP (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SCULK_CHARGE_POP");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 21) SCULK_SOUL (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SCULK_SOUL");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 22) SMALL_FLAME (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SMALL_FLAME");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 23) SMOKE_LARGE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SMOKE_LARGE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 24) SMOKE_NORMAL (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SMOKE_NORMAL");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 25) SOUL (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SOUL");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 26) SOUL_FIRE_FLAME (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SOUL_FIRE_FLAME");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 27) SPIT (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SPIT");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 28) SQUID_INK (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("SQUID_INK");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 29) TOTEM (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("TOTEM");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 30) WATER_BUBBLE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("WATER_BUBBLE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 31) WATER_WAKE (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("WATER_WAKE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 32) WAX_OFF (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("WAX_OFF");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 32) WAX_ON (directional)
        ikona =  new ItemStack(Material.COPPER_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("WAX_ON");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        MENU_DIRECTIONAL = inventory;
    }

    private void przygotujMenuColored(){
        Inventory inventory = Bukkit.createInventory(null,36, menuColoredNaglowek);
        // 0) REDSTONE (colored)
        ItemStack ikona =  new ItemStack(Material.AMETHYST_BLOCK);
        ItemMeta meta = ikona.getItemMeta();
        meta.setDisplayName("(c)REDSTONE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 1) SPELL_MOB (colored)
        ikona =  new ItemStack(Material.AMETHYST_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(c)SPELL_MOB");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 2) NOTE (colored)
        ikona =  new ItemStack(Material.AMETHYST_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(c)NOTE");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        MENU_COLORED = inventory;
        // 3) DUST_COLOR_TRANSITION (colored)
        ikona =  new ItemStack(Material.AMETHYST_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(c)DUST_COLOR_TRANSITION");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        MENU_COLORED = inventory;
    }

    private void przygotujMenuMaterial(){
        Inventory inventory = Bukkit.createInventory(null,36, menuMaterialNaglowek);
        // 0) STONE (material) on ITEM_CRACK
        ItemStack ikona =  new ItemStack(Material.IRON_BLOCK);
        ItemMeta meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - ITEM_CRACK");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 1) STONE (material) on BLOCK_CRACK
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - BLOCK_CRACK");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 2) STONE (material) on BLOCK_DUST
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - BLOCK_DUST");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 3) STONE (material) on FALLING_DUST
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - FALLING_DUST");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);

        //Todo: Tym poświęcić uwagę na czym polega rożnica
        //BLOCK_MARKER ???
        //LEGACY_BLOCK_CRACK ???
        //LEGACY_BLOCK_DUST ???
        //LEGACY_FALLING_DUST ???

        // 4) STONE (material) on BLOCK_MARKER
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - BLOCK_MARKER");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);

        // 5) STONE (material) on LEGACY_BLOCK_CRACK
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - LEGACY_BLOCK_CRACK");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 6) STONE (material) on LEGACY_BLOCK_DUST
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - LEGACY_BLOCK_DUST");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        // 7) STONE (material) on LEGACY_FALLING_DUST
        ikona =  new ItemStack(Material.IRON_BLOCK);
        meta = ikona.getItemMeta();
        meta.setDisplayName("(m)STONE - LEGACY_FALLING_DUST");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);
        MENU_MATERIAL = inventory;
    }

    public void przygotujMenuVibration(){
        Inventory inventory = Bukkit.createInventory(null,36, menuVibrationNaglowek);
        // 0) VIBRATION
        ItemStack ikona =  new ItemStack(Material.MAGMA_BLOCK);
        ItemMeta meta = ikona.getItemMeta();
        meta.setDisplayName("(v)VIBRATION");
        ikona.setItemMeta(meta);
        inventory.addItem(ikona);

        MENU_VIBRATION = inventory;
    }

    public void przygotujInterfejsy(){
        przygotujMenuGlowne();
        przygotujMenuDirectional();
        przygotujMenuColored();
        przygotujMenuMaterial();
        przygotujMenuVibration();
    }


    public void otworzMenu(Player player , KtoreMenu ktoreMenu){
        //System.out.println("DEBUG : otworzMenu wywolanie : "+ ktoreMenu );
        switch (ktoreMenu) {
            case Glowne -> {
                MENU_WYSWIETLANE = MENU_GLOWNE;
                ktoreAktywne = KtoreMenu.Glowne;
            }
            case Directional -> {
                MENU_WYSWIETLANE = MENU_DIRECTIONAL;
                ktoreAktywne = KtoreMenu.Directional;
            }
            case Colored -> {
                MENU_WYSWIETLANE = MENU_COLORED;
                ktoreAktywne = KtoreMenu.Colored;
            }
            case Material -> {
                MENU_WYSWIETLANE = MENU_MATERIAL;
                ktoreAktywne = KtoreMenu.Material;
            }
            case Vibration -> {
                MENU_WYSWIETLANE = MENU_VIBRATION;
                ktoreAktywne = KtoreMenu.Vibration;
            }
            default -> {
                System.out.println("[MenuGui] - tryb menu default - prawdopodobny blad w kodzie.");
                MENU_WYSWIETLANE = MENU_GLOWNE;
                ktoreAktywne = KtoreMenu.Glowne;
            }
        }
        player.openInventory(MENU_WYSWIETLANE);
    }

    public Inventory aktywneMenuInterfejs(){
        return MENU_WYSWIETLANE;
    }

    public KtoreMenu ktoreMenuAktywne(){
        return  ktoreAktywne;
    }

    public  boolean naszeMenu(String nazwaOkna){
        return listaNaglowkowMenu.contains(nazwaOkna);
    };

}

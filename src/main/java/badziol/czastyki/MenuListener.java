package badziol.czastyki;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static badziol.czastyki.KtoreMenu.*;

public class MenuListener implements Listener {
    private MenuGui tmpMenu;

    public MenuListener() {
      tmpMenu = new MenuGui();
    };

    /**
     * Jako ze umiejscowienie przedmiotu w plecaku ma kluczowe znaczenie , dobrze wiedziec co kliknelismu
     * Pomoc przy debugu
     * @param item przedmiot klikniety przez gracza
     * @return String z ustawioną w MenuGui nazwą naszego przycisku
     */
    private String opisCeluKlikniecia(ItemStack item){
        if (item == null) return "opis->null";
        ItemMeta meta = item.getItemMeta();
        return meta.getDisplayName();
    }

    /**
     * Wykluczamy wszystkie niechciane zdażenia na tym etapie : <br>
     * - kliknięcie poza obręb naszego menu , <br>
     * - czy kliknięty element nie jest nullem, <br>
     * - czy okno ktore się otworzyło to jedno z naszych menu/podmenu, <br>
     * - czy gracz faktycznie kliknał w menu a nie w własny ekwipunek, <br>
     * @param event zdazenie , które ma być sprawdzone
     * @param wyswietlajKomnunikaty  true/false - czy wyswietlac komunikaty pomocne przy debugownaiu
     * @return true - zdarzenie poprawne
     */

    private boolean sprawdzPoprawnosc(InventoryClickEvent event ,boolean wyswietlajKomnunikaty){
        if (wyswietlajKomnunikaty) System.out.println("Sprawdzam element :");
        //wykluczenie kliknięcia poza inwentarz
        if (wyswietlajKomnunikaty) System.out.println("- w obrebie menu");
        if (event.getClickedInventory() == null)  return false;

        //wykluczamy klikniece pustego pola
        if (wyswietlajKomnunikaty) System.out.println("- nie puste pole");
        ItemStack cel = event.getCurrentItem();
        if (cel == null) return false;

        //czy naglowek okna wystepuje na liscie naszych menu
        if (wyswietlajKomnunikaty) System.out.println("- naglowek w liscie zdefiniowanych");
        boolean naszNaglowek = tmpMenu.naszeMenu(event.getView().getTitle());
        if (!naszNaglowek) return false;

        //czy gracz kliknal w wlasny inwentarz
        if (wyswietlajKomnunikaty) System.out.println("- klikniecie w wlasny ekwipunek");
        if (event.getView().getType() == InventoryType.PLAYER) return false;

        //teoretycznie poprawne kliknięcie
        if (wyswietlajKomnunikaty) System.out.println("- klikniecie poprawne.");
        return true;
    }

    /**
     * Obsługa wyboru podmenu z poziomu meu głównego
     * @param player - obslugiwany gracz
     * @param cel  - co dokladnie kliknieto
     * @param slot - ktore miejsce kliknięto
     */
    public void obslugaMenuGlowne(Player player ,int slot , ItemStack cel) {
        switch (slot){
            case 0 ->{
                System.out.println("otwieranie - directional , : "+ opisCeluKlikniecia(cel)+" slot :"+slot);
                tmpMenu.otworzMenu(player, Directional);
            }
            case 1->{
                System.out.println("otwieranie - colored , : "+ opisCeluKlikniecia(cel)+" slot :"+slot);
                tmpMenu.otworzMenu(player, Colored);
            }
            case 2->{
                System.out.println("otwieranie - material , : "+ opisCeluKlikniecia(cel)+" slot :"+slot);
                tmpMenu.otworzMenu(player, Material);
            }
            case 3->{
                System.out.println("otwieranie - vibration , : "+ opisCeluKlikniecia(cel)+" slot :"+slot);
                tmpMenu.otworzMenu(player, Vibration);
            }
            default -> {
                System.out.println("Wybor opcji z menu glownego , cos poszlo nie tak.");
            }
        }
    }

    /**
     * Obsługa menu odpowiedzialnego za cząstki 'directional'
     * @param player - obsługiwany gracz
     * @param slot - które miejsce kliknięto
     * @param cel - co kliknięto
     */
    public void obslugaMenuDirectional(Player player ,int slot , ItemStack cel){

    }

    /**
     * Właściwy event obslugi naszego menu
     * @param event kliknięcia w inwentarz
     */
    @EventHandler
    public void onMenuClick(InventoryClickEvent event){

        if (!sprawdzPoprawnosc(event,false)){
            System.out.println("[Menu] - klikniecie wykluczone");
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);
        //teraz nareszcie rozpoznajemy z ktorym dokladnie menu mamy doczynienia
        int slot = event.getSlot();
        ItemStack cel = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();
        switch ( tmpMenu.ktoreMenuAktywne()){
            case Glowne -> {
                System.out.println("[Menu](Glowne)");
                obslugaMenuGlowne(player,slot,cel);
            }

            case Directional -> {
                System.out.println("[Menu](Directional)");
                obslugaMenuDirectional(player,slot,cel);
            }

            case Colored -> {
                System.out.println("[Menu](Colored)");
            }

            case Material -> {
                System.out.println("[Menu](Material)");

            }
            case Vibration -> {
                System.out.println("[Menu](Vibration)");

            }
        }
    }
}

package badziol.czastyki;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static badziol.czastyki.KtoreMenu.Glowne;

/**
 * Komenda /cz uruchamia menu glowne do testow czastek
 */
public class KomendaCz implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        MenuGui toMenu = new MenuGui();
        toMenu.otworzMenu(player,Glowne);
        return true;
    }
}

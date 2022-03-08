package me.ggens;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("generator")) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender instanceof Player) {
                    if (sender.isOp()) {

                        Main.INSTANCE.reloadConfig();
                        sender.sendMessage(ChatColor.RED + "Generators have been reloaded");
                        return true;
                    }
                } else if (!(sender instanceof Player)) {
                    Main.INSTANCE.reloadConfig();
                    sender.sendMessage(ChatColor.RED + "Generators have been reloaded");
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }
}

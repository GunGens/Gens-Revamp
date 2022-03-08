//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.ggens;

import com.google.common.util.concurrent.AtomicDouble;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GenCommand implements CommandExecutor {
    public GenCommand() {
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("generator")) {
            if (sender instanceof Player && sender.isOp()) {
                Player p = (Player) sender;
                if (args.length == 0) {
                    p.sendMessage(ChatColor.GREEN + "Command Usage: /generator <create> <item> <block> \n item is the item the generator will drop.\n block is the block that the generator will be.");
                }
                if (args[0].toLowerCase().contains("create")) {
                    String item = args[1];
                    String block = args[2];
                    AtomicInteger gItemCount = new AtomicInteger(1);
                    AtomicDouble gTime = new AtomicDouble(1.0D);
                    Bukkit.getLogger().log(Level.SEVERE, "\n !! Error !! \n Arguments check \n !! End of Error !! \n");
                    if (verifyMaterial(item) != null) {
                        Bukkit.getLogger().log(Level.SEVERE, "\n !! Error !! \n item verification \n !! End of Error !! \n");
                        if (verifyMaterial(block) != null) {
                            Bukkit.getLogger().log(Level.SEVERE, "\n !! Error !! \n block verification \n !! End of Error !! \n");
                            Material gItem = Material.matchMaterial(item);
                            Material gBlock = Material.matchMaterial(block);
                            cGen(p, gBlock, gItem, gTime.floatValue(), gItemCount.intValue());
                            p.sendMessage(ChatColor.GREEN + "You were given 1 " + ChatColor.ITALIC + ChatColor.GREEN + item + ChatColor.GREEN + " Generator \n Placing more then one will overwrite the previous one \n unless you use the gen command to get a new one!");
                        } else if (verifyMaterial(block) == null) {
                            Bukkit.getLogger().log(Level.SEVERE, "\n !! Error !! \n Tried creating a gen but failed because Material was invalid \n !! End of Error !! \n");
                            p.sendMessage(ChatColor.GREEN + block + ChatColor.GREEN + "is an invalid material,\nValid Material Example: OAK_LOG");
                        }
                    } else if (verifyMaterial(item) == null) {
                        Bukkit.getLogger().log(Level.SEVERE, "\n !! Error !! \n Tried creating a gen but failed because Material was invalid \n !! End of Error !! \n");
                        p.sendMessage(ChatColor.GREEN + item + ChatColor.GREEN + "is an invalid material,\nValid Material Example: OAK_LOG");
                    }
                } else if (args[0].isEmpty()) {
                    p.sendMessage(ChatColor.GREEN + "Command Usage: /generator <create> <item> <block> \n item is the item the generator will drop.\n block is the block that the generator will be.");
                    return false;
                }

            } else {
                if (sender instanceof ConsoleCommandSender) {
                    Bukkit.getLogger().log(Level.INFO, "This command can only be ran by a player!");
                }

            }

        }
        return true;
    }

    public static Material verifyMaterial(String mat) {
        return Material.matchMaterial(mat);
    }

    public static void cGen(Player player, Material block, Material item, float time, int amount) {
        UUID uuid = UUID.randomUUID();
        ConfigurationSection config = Main.INSTANCE.getConfig().getConfigurationSection("generators");
        config = config.createSection(uuid.toString());
        config.set("block", block.toString());
        config.set("item", item.toString());
        config.set("time", time);
        config.set("amount", amount);
        Main.INSTANCE.saveConfig();
        ItemStack iStack = new ItemStack(block, 1);
        ChatColor var10000 = ChatColor.GREEN;
        iStack = setItemName(var10000 + formatMaterialName(item) + ChatColor.GREEN + " Generator", iStack);
        iStack = setItemLore(new ArrayList(Arrays.asList("Gen: " + uuid.toString(), "Block: " + formatMaterialName(block), "Item: " + formatMaterialName(item), "Time: " + time, "Amount: " + amount)), iStack);
        iStack = setItemEnchant(new ArrayList(Arrays.asList(Enchantment.LUCK)), iStack);
        player.getInventory().addItem(new ItemStack[]{iStack});
    }

    private static ItemStack setItemName(String name, ItemStack stack) {
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setDisplayName(name);
        stack.setItemMeta(itemMeta);
        return stack;
    }

    private static ItemStack setItemLore(List<String> lore, ItemStack stack) {
        ItemMeta itemMeta = stack.getItemMeta();
        itemMeta.setLore(lore);
        stack.setItemMeta(itemMeta);
        return stack;
    }

    private static ItemStack setItemEnchant(List<Enchantment> enchants, ItemStack stack) {
        ItemMeta itemMeta = stack.getItemMeta();
        Iterator var3 = enchants.iterator();

        while(var3.hasNext()) {
            Enchantment enchant = (Enchantment)var3.next();
            itemMeta.addEnchant(enchant, 1, true);
            itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        }

        stack.setItemMeta(itemMeta);
        return stack;
    }

    private static String formatMaterialName(Material mat) {
        return capitalize(mat.toString().toLowerCase().replace("_", " "));
    }

    private static String capitalize(String text) {
        String c = text != null ? text.trim() : "";
        String[] words = c.split(" ");
        String result = "";
        String[] var4 = words;
        int var5 = words.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String w = var4[var6];
            result = result + (w.length() > 1 ? w.substring(0, 1).toUpperCase(Locale.US) + w.substring(1, w.length()).toLowerCase(Locale.US) : w) + " ";
        }

        return result.trim();
    }
}

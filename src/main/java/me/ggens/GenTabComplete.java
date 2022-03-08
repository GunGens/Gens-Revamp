//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.ggens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class GenTabComplete implements TabCompleter {
    public GenTabComplete() {
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = new ArrayList();
        List<String> completions = new ArrayList();
        switch (args.length) {
            case 1:
            commands.add("create");
            commands.add("reload");
            StringUtil.copyPartialMatches(args[0], commands, completions);
            break;
            case 2:
                commands.add("STONE");
                StringUtil.copyPartialMatches(args[1], commands, completions);
                break;
            case 3:
                commands.add("STONE");
                StringUtil.copyPartialMatches(args[2], commands, completions);
                break;
        }

        Collections.sort(completions);
        return completions;
    }
}

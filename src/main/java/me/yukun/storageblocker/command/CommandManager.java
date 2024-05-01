package me.yukun.storageblocker.command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import me.yukun.storageblocker.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {

  private static final Map<String, Function<CommandSender, StorageBlockerCommand>> commandProducerMap = new HashMap<>() {{
    put("help", HelpCommand::new);
    put("reload", ReloadCommand::new);
  }};

  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (!Config.canUseCommands(sender)) {
      return false;
    }
    if (args.length == 0 || !commandProducerMap.containsKey(args[0])) {
      commandProducerMap.get("help").apply(sender).execute();
      return false;
    }
    StorageBlockerCommand storageBlockerCommand = commandProducerMap.get(args[0]).apply(sender);
    return storageBlockerCommand.execute();
  }
}

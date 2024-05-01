package me.yukun.storageblocker.command;

import me.yukun.storageblocker.config.Messages;
import org.bukkit.command.CommandSender;

public class HelpCommand extends StorageBlockerCommand {

  public HelpCommand(CommandSender sender) {
    super(sender);
  }

  @Override
  public boolean execute() {
    Messages.sendHelp(sender);
    return true;
  }
}

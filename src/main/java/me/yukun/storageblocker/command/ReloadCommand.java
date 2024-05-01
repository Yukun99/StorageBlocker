package me.yukun.storageblocker.command;

import me.yukun.storageblocker.config.FileManager;
import me.yukun.storageblocker.config.Messages;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends StorageBlockerCommand {

  public ReloadCommand(CommandSender sender) {
    super(sender);
  }

  @Override
  public boolean execute() {
    FileManager.reload();
    Messages.sendReloadSuccess(sender);
    return true;
  }
}

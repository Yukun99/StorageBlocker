package me.yukun.storageblocker.command;

import org.bukkit.command.CommandSender;

public class StorageBlockerCommand {

  protected CommandSender sender;

  public StorageBlockerCommand(CommandSender sender) {
    this.sender = sender;
  }

  /**
   * Executes the command.
   */
  public boolean execute() {
    return false;
  }
}

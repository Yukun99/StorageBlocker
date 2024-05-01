package me.yukun.storageblocker.config;

import static me.yukun.storageblocker.util.TextFormatter.applyColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import me.yukun.storageblocker.Filter;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class Config {

  private static FileConfiguration config;

  protected static void setup(FileConfiguration fileConfiguration) {
    config = fileConfiguration;
    loadAllFilters();
  }

  /**
   * Checks if specified command sender has permissions to use commands.
   *
   * @param sender Command sender to check permissions for.
   * @return If specified command sender has permissions to use commands.
   */
  public static boolean canUseCommands(CommandSender sender) {
    if (sender instanceof ConsoleCommandSender) {
      return true;
    }
    if (sender.hasPermission("storageblocker.*")) {
      return true;
    }
    return sender.hasPermission("storageblocker.admin");
  }

  /**
   * Checks if specified player should be prevented from storing items.
   *
   * @param player Player to be checked.
   * @return True if specified player should be prevented from storing items, false otherwise.
   */
  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  public static boolean canBlock(Player player) {
    if (!player.hasPermission("storageblocker.*")) {
      return true;
    }
    if (!player.hasPermission("storageblocker.admin")) {
      return true;
    }
    return !player.hasPermission("storageblocker.bypass");
  }

  private static void loadAllFilters() {
    Filter.clear();
    for (String section : Objects.requireNonNull(config.getConfigurationSection(""))
        .getKeys(false)) {
      new Filter(getMaterial(section), getName(section), getLore(section), getEnchantments(section),
          getInventoryTypes(section));
    }
  }

  private static Material getMaterial(String key) {
    if (!config.isString(key + ".ItemType")) {
      return null;
    }
    return Material.getMaterial(Objects.requireNonNull(config.getString(key + ".ItemType")));
  }

  private static String getName(String key) {
    if (config.getString(key + ".Name") == null) {
      return null;
    }
    return applyColor(config.getString(key + ".Name"));
  }

  private static List<String> getLore(String key) {
    if (config.getStringList(key + ".Lore").isEmpty()) {
      return null;
    }
    List<String> result = new ArrayList<>();
    for (String line : config.getStringList(key + ".Lore")) {
      result.add(applyColor(line));
    }
    return result;
  }

  private static Map<Enchantment, Integer> getEnchantments(String key) {
    if (config.getStringList(key + ".Enchantments").isEmpty()) {
      return null;
    }
    Map<Enchantment, Integer> result = new HashMap<>();
    for (String stringEnchant : config.getStringList(key + ".Enchantments")) {
      String[] stringEnchantSplit = stringEnchant.split(":");
      Enchantment enchantment = Registry.ENCHANTMENT.match(stringEnchantSplit[0]);
      int level = Integer.parseInt(stringEnchantSplit[1]);
      result.put(enchantment, level);
    }
    return result;
  }

  private static Set<InventoryType> getInventoryTypes(String key) {
    if (config.getStringList(key + ".InventoryTypes").isEmpty()) {
      return null;
    }
    Set<InventoryType> result = new HashSet<>();
    for (String stringType : config.getStringList(key + ".InventoryTypes")) {
      result.add(InventoryType.valueOf(stringType));
    }
    return result;
  }
}

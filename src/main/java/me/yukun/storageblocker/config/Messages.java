package me.yukun.storageblocker.config;

import static me.yukun.storageblocker.util.TextFormatter.applyColor;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Messages {

  // Plugin info ping messages.
  private static final String VERSION = "StorageBlocker v%version% loaded.";
  // Configuration related logging.
  private static final String FOLDER = "Config folder";
  private static final String EXISTS = " exists, skipping creation.";
  private static final String NOT_EXISTS = " not created, creating now.";
  private static final String COPY_ERROR = "&cERROR! %file% could not be created.";
  private static final String CONFIG_ERROR = "&cERROR! Config files could not load properly. Plugin will be disabled.";
  private static final String VALIDATION_SUCCESS = "&aValidation success! %file% has no errors.";
  private static final String RELOAD = "&a%file% reloaded!";
  // Command reply messages.
  private static final String HELP_HEADER = "&b&l=============StripArmour=============";
  private static final String HELP_COMMANDS = "&b&l----------Commands----------";
  private static final String HELP_ALIASES = "Command aliases: storeblock, stblock, storebl, stoblo, sb";
  private static final String HELP_HELP = "/storageblocker help: Shows commands, aliases and permissions.";
  private static final String HELP_RELOAD = "/storageblocker reload: Reloads all configuration files.";
  private static final String HELP_PERMISSIONS = "&b&l----------Permissions----------";
  private static final String HELP_WILDCARD = "storageblocker.*: All permissions combined.";
  private static final String HELP_ADMIN = "storageblocker.admin: Ability to use commands + admin.bypass.";
  private static final String HELP_BYPASS = "storageblocker.bypass: Immunity to storage blocking.";
  private static final String HELP_FOOTER = "&b&l=====================================";
  private static final String RELOAD_SUCCESS = "&aReload successful!";
  // Prefix appended before all messages.
  private static String prefix = "&bStorage&eBlocker&f >> &7";
  // Messages sent to players.
  private static String blocked;

  protected static void setup(FileConfiguration fileConfiguration) {
    prefix = fileConfiguration.getString("Prefix");
    blocked = prefix + fileConfiguration.getString("Blocked");
  }

  /**
   * Sends plugin version info to specified player.
   *
   * @param player Player to send plugin version info to.
   * @param plugin Plugin to get version info for.
   */
  public static void sendPluginVersion(Player player, Plugin plugin) {
    String message = prefix + VERSION.replaceAll("%version%", plugin.getDescription().getVersion());
    player.sendMessage(applyColor(message));
  }

  /**
   * Sends config error message to specified player.
   *
   * @param player Player to send config error message to.
   */
  public static void sendConfigError(Player player) {
    player.sendMessage(applyColor(prefix + CONFIG_ERROR));
  }

  /**
   * Logging message during setup sent if config folder exists.
   */
  protected static void printFolderExists() {
    System.out.println(applyColor(prefix + FOLDER + EXISTS));
  }

  /**
   * Logging message during setup sent if config folder does not exist.
   */
  protected static void printFolderNotExists() {
    System.out.println(applyColor(prefix + FOLDER + NOT_EXISTS));
  }

  /**
   * Logging message during setup sent if specified file exists in config folder.
   *
   * @param filename Filename of specified file.
   */
  protected static void printFileExists(String filename) {
    System.out.println(applyColor(prefix + filename + EXISTS));
  }

  /**
   * Logging message during setup sent if specified file does not exist in config folder.
   *
   * @param filename Filename of specified file.
   */
  protected static void printFileNotExists(String filename) {
    System.out.println(applyColor(prefix + filename + NOT_EXISTS));
  }

  /**
   * Logging message during setup sent if specified file could not be copied to config folder.
   *
   * @param filename Filename of specified file.
   */
  protected static void printFileCopyError(String filename) {
    String message = prefix + COPY_ERROR.replaceAll("%file%", filename);
    System.out.println(applyColor(message));
  }

  /**
   * Logging message during setup sent if config files contain errors and could not load properly.
   */
  public static void printConfigError(Exception exception) {
    System.out.println(applyColor(prefix + CONFIG_ERROR));
    System.out.println(applyColor(prefix + exception.getMessage()));
  }

  /**
   * Logging message during setup sent if specified file is validated successfully.
   *
   * @param configType Configuration file type that was validated successfully.
   */
  public static void printValidationSuccess(ConfigTypeEnum configType) {
    String message = prefix + VALIDATION_SUCCESS.replaceAll("%file%", configType.toString());
    System.out.println(applyColor(message));
  }

  /**
   * Logging message during reloading sent if specified file is reloaded successfully.
   *
   * @param configType Configuration file type that was reloaded successfully.
   */
  public static void printReloaded(ConfigTypeEnum configType) {
    String message = prefix + RELOAD.replaceAll("%file%", configType.toString());
    System.out.println(applyColor(message));
  }

  /**
   * Send commands help message to help command sender.
   *
   * @param commandSender Command sender to send commands help message to.
   */
  public static void sendHelp(CommandSender commandSender) {
    commandSender.sendMessage(applyColor(HELP_HEADER));
    commandSender.sendMessage(applyColor(HELP_COMMANDS));
    commandSender.sendMessage(applyColor(HELP_ALIASES));
    commandSender.sendMessage(applyColor(HELP_HELP));
    commandSender.sendMessage(applyColor(HELP_RELOAD));
    commandSender.sendMessage(applyColor(HELP_PERMISSIONS));
    commandSender.sendMessage(applyColor(HELP_WILDCARD));
    commandSender.sendMessage(applyColor(HELP_ADMIN));
    commandSender.sendMessage(applyColor(HELP_BYPASS));
    commandSender.sendMessage(applyColor(HELP_FOOTER));
  }

  /**
   * Send config reloaded message to player.
   *
   * @param commandSender CommandSender to send reloaded message to.
   */
  public static void sendReloadSuccess(CommandSender commandSender) {
    commandSender.sendMessage(applyColor(prefix + RELOAD_SUCCESS));
  }

  /**
   * Send storage blocked message to player.
   *
   * @param player Player to send storage blocked message to.
   */
  public static void sendBlocked(Player player) {
    player.sendMessage(applyColor(blocked));
  }
}

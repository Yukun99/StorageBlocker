package me.yukun.storageblocker;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class Filter {

  private final Material material;
  private final String name;
  private final List<String> lore;
  private final Map<Enchantment, Integer> enchantments;
  private final Set<InventoryType> inventoryTypes;
  private static final Set<Filter> filters = new HashSet<>();

  public Filter(Material material, String name, List<String> lore,
      Map<Enchantment, Integer> enchantments, Set<InventoryType> inventoryTypes) {
    this.material = material;
    this.name = name;
    this.lore = lore;
    this.enchantments = enchantments;
    this.inventoryTypes = inventoryTypes;
    filters.add(this);
  }

  /**
   * Resets filters.
   */
  public static void clear() {
    filters.clear();
  }

  /**
   * Check if specified item should be blocked from being stored in specified inventory.
   *
   * @param item          Item to check for matching.
   * @param inventoryType Inventory type to check for matching.
   * @return True if specified item should be blocked from being stored in specified inventory,
   * false otherwise.
   */
  public static boolean matchesFilter(ItemStack item, InventoryType inventoryType) {
    if (item == null) {
      return false;
    }
    for (Filter filter : filters) {
      if (filter.matchFilter(item, inventoryType)) {
        return true;
      }
    }
    return false;
  }

  private boolean matchFilter(ItemStack item, InventoryType inventoryType) {
    if (!matchMaterial(item)) {
      return false;
    }
    if (!matchName(item)) {
      return false;
    }
    if (!matchLore(item)) {
      return false;
    }
    if (!matchEnchantments(item)) {
      return false;
    }
    return matchInventoryTypes(inventoryType);
  }

  private boolean matchMaterial(ItemStack item) {
    if (material == null) {
      return true;
    }
    return item.getType() == material;
  }

  private boolean matchName(ItemStack item) {
    if (name == null) {
      return true;
    }
    if (!item.hasItemMeta()) {
      return false;
    }
    //noinspection ConstantConditions
    if (!item.getItemMeta().hasDisplayName()) {
      return false;
    }
    return item.getItemMeta().getDisplayName().equals(name);
  }

  private boolean matchLore(ItemStack item) {
    if (lore == null) {
      return true;
    }
    if (!item.hasItemMeta()) {
      return false;
    }
    //noinspection ConstantConditions
    if (!item.getItemMeta().hasLore()) {
      return false;
    }
    List<String> itemLore = item.getItemMeta().getLore();
    for (int i = 0; i < lore.size(); i++) {
      //noinspection ConstantConditions
      if (!itemLore.get(i).equals(lore.get(i))) {
        return false;
      }
    }
    return true;
  }

  private boolean matchEnchantments(ItemStack item) {
    if (enchantments == null) {
      return true;
    }
    Map<Enchantment, Integer> itemEnchantments = item.getEnchantments();
    if (enchantments.size() != itemEnchantments.size()) {
      return false;
    }
    for (Enchantment enchant : itemEnchantments.keySet()) {
      if (!enchantments.containsKey(enchant)) {
        return false;
      }
      if (!itemEnchantments.get(enchant).equals(enchantments.get(enchant))) {
        return false;
      }
    }
    return true;
  }

  private boolean matchInventoryTypes(InventoryType inventoryType) {
    if (inventoryTypes == null) {
      return true;
    }
    return inventoryTypes.contains(inventoryType);
  }
}

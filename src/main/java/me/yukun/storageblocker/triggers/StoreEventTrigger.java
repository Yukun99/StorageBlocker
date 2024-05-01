package me.yukun.storageblocker.triggers;

import me.yukun.storageblocker.Filter;
import me.yukun.storageblocker.config.Config;
import me.yukun.storageblocker.config.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class StoreEventTrigger implements Listener {

  @EventHandler
  private void blockStoreItemEvent(InventoryMoveItemEvent e) {
    ItemStack item = e.getItem();
    Inventory dest = e.getDestination();
    if (!Filter.matchesFilter(item, dest.getType())) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  private void hopperPickupItemEvent(InventoryPickupItemEvent e) {
    ItemStack item = e.getItem().getItemStack();
    Inventory dest = e.getInventory();
    if (!Filter.matchesFilter(item, dest.getType())) {
      return;
    }
    e.setCancelled(true);
  }

  @EventHandler
  private void playerStoreItemEvent(InventoryClickEvent e) {
    if (!Config.canBlock((Player) e.getWhoClicked())) {
      return;
    }
    if (!isStoreEvent(e)) {
      return;
    }
    Messages.sendBlocked((Player) e.getWhoClicked());
    e.setCancelled(true);
  }

  @EventHandler
  private void playerDragStoreItemEvent(InventoryDragEvent e) {
    if (!Config.canBlock((Player) e.getWhoClicked())) {
      return;
    }
    if (!Filter.matchesFilter(e.getOldCursor(), e.getInventory().getType())) {
      return;
    }
    Messages.sendBlocked((Player) e.getWhoClicked());
    e.setCancelled(true);
  }

  private boolean isStoreEvent(InventoryClickEvent e) {
    if (e.getClickedInventory() == null) {
      // Clicking on space outside inventory.
      return false;
    }
    ItemStack item = null;
    Inventory inventory;
    if (e.getClickedInventory().equals(e.getWhoClicked().getInventory())) {
      inventory = e.getInventory();
      if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
        // Moving item from player inventory to other inventory.
        item = e.getCurrentItem();
      }
    } else {
      inventory = e.getInventory();
      if (e.getAction() == InventoryAction.HOTBAR_SWAP) {
        // Swapping item in other inventory with item on player's hotbar.
        item = e.getWhoClicked().getInventory().getItem(e.getHotbarButton());
      }
      if (e.getAction() == InventoryAction.PLACE_ALL ||
          e.getAction() == InventoryAction.PLACE_ONE ||
          e.getAction() == InventoryAction.PLACE_SOME ||
          e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
        // Placing item on cursor into other inventory.
        item = e.getCursor();
      }
    }
    return Filter.matchesFilter(item, inventory.getType());
  }
}

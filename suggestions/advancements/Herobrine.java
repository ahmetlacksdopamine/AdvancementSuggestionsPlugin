package suggestions.advancements;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Herobrine implements Listener {
	Logger logger = Bukkit.getLogger();

	private boolean check(Location loc) {
		if (loc.getBlock().getType() == Material.NETHERRACK) {
			Location north = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1);
			Location south = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1);
			Location east = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
			Location west = new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());

			if (north.getBlock().getType() == Material.REDSTONE_TORCH) {
				if (south.getBlock().getType() == Material.REDSTONE_TORCH) {
					if (east.getBlock().getType() == Material.REDSTONE_TORCH) {
						if (west.getBlock().getType() == Material.REDSTONE_TORCH) {

							for (int x = -1; x <= 1; x++) {
								for (int z = -1; z <= 1; z++) {
									Location gold_location = new Location(loc.getWorld(), loc.getX() + x,
											loc.getY() - 1, loc.getZ() + z);
									if (gold_location.getBlock().getType() != Material.GOLD_BLOCK) {
										return false;
									}
								}
							}

							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Advancement herobrine = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:herobrine"));
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack item = event.getItem();

		if(player.getAdvancementProgress(herobrine).isDone()) {
			return;
		}

		if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(item == null) return;
			if (item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIRE_CHARGE) {
				Block block = event.getClickedBlock();
				if (block.getType() == Material.NETHERRACK) {
					if (check(block.getLocation())) {
						player.getAdvancementProgress(herobrine).awardCriteria("done");
					}
				}

			}

		}
	}
}

package suggestions.advancements;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class Wither implements Listener {
	Logger logger = Bukkit.getLogger();

	private boolean check(Location loc, String axis) {
		Location north = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1);
		Location south = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1);
		Location east = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
		Location west = new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());
		Location starting_point;

		if (axis == "x") {
			if (east.getBlock().getType() == Material.AIR) {
				starting_point = new Location(east.getWorld(), east.getX() - 3, east.getY(), east.getZ());
			} else if (west.getBlock().getType() == Material.AIR) {
				starting_point = loc;
			} else {
				starting_point = west;
			}

			for (int x = 0; x <= 2; x++) {
				if (new Location(starting_point.getWorld(), starting_point.getX() + x, starting_point.getY(),
						starting_point.getZ()).getBlock().getType() != Material.SKELETON_SKULL) {
					return false;
				}
				if (new Location(starting_point.getWorld(), starting_point.getX() + x, starting_point.getY() - 1,
						starting_point.getZ()).getBlock().getType() != Material.SOUL_SAND) {
					return false;
				}
			}

			if (new Location(starting_point.getWorld(), starting_point.getX() + 1, starting_point.getY() - 2,
					starting_point.getZ()).getBlock().getType() != Material.SOUL_SAND) {
				return false;
			}

		} else {
			if (south.getBlock().getType() == Material.AIR) {
				starting_point = new Location(south.getWorld(), south.getX(), south.getY(), south.getZ() - 3);
			} else if (north.getBlock().getType() == Material.AIR) {
				starting_point = loc;
			} else {
				starting_point = north;
			}

			for (int z = 0; z <= 2; z++) {
				if (new Location(starting_point.getWorld(), starting_point.getX(), starting_point.getY(),
						starting_point.getZ() + z).getBlock().getType() != Material.SKELETON_SKULL) {
					return false;
				}
				if (new Location(starting_point.getWorld(), starting_point.getX(), starting_point.getY() - 1,
						starting_point.getZ() + z).getBlock().getType() != Material.SOUL_SAND) {
					return false;
				}
			}

			if (new Location(starting_point.getWorld(), starting_point.getX(), starting_point.getY() - 2,
					starting_point.getZ() + 1).getBlock().getType() != Material.SOUL_SAND) {
				return false;
			}
		}
		return true;
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		Advancement wither = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:wither"));
		Player player = event.getPlayer();

		if (player.getAdvancementProgress(wither).isDone()) {
			return;
		}

		if (event.getBlock().getType() == Material.SKELETON_SKULL) {
			if (new Location(event.getBlock().getWorld(), event.getBlock().getX(), event.getBlock().getY() - 1,
					event.getBlock().getZ()).getBlock().getType() == Material.SOUL_SAND) {
				if (check(event.getBlock().getLocation(), "x") || check(event.getBlock().getLocation(), "z")) {
					player.getAdvancementProgress(wither).awardCriteria("done");
				}
			}
		}
	}
}

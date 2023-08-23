package suggestions.advancements;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class Aether implements Listener {
	Boolean[][] portal_frame = {
			{ true, true, true, true },
			{ true, false, false, true },
			{ true, false, false, true },
			{ true, false, false, true },
			{ true, true, true, true }
	};

	public boolean check_portal_frame(Location starting_point, String axis) {
		for (double y = 0; y <= 4; y++) {
			for (double xz = 0; xz <= 3; xz++) {
				Location loc = new Location(starting_point.getWorld(),
						axis == "x" ? starting_point.getX() + xz : starting_point.getX(),
						starting_point.getY() - y,
						axis == "z" ? starting_point.getZ() - xz : starting_point.getZ());
				if (portal_frame[(int) y][(int) xz] == true) {
					if (loc.getBlock().getType() != Material.GLOWSTONE) {
						return false;
					}
				} else {
					if (loc.getBlock().getType() != Material.AIR
							&& loc.getBlock().getType() != Material.WATER) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean check(Location loc, String axis) {
		Location north = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() - 1);
		Location south = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ() + 1);
		Location east = new Location(loc.getWorld(), loc.getX() + 1, loc.getY(), loc.getZ());
		Location west = new Location(loc.getWorld(), loc.getX() - 1, loc.getY(), loc.getZ());

		if (axis == "z") {
			if (north.getBlock().getType() == Material.AIR) {
				Location starting_point = new Location(north.getWorld(), north.getX(), north.getY() + 3,
						north.getZ() + 2);
				return check_portal_frame(starting_point, axis);
			} else if (south.getBlock().getType() == Material.AIR) {
				Location starting_point = new Location(north.getWorld(), north.getX(), north.getY() + 3,
						north.getZ() + 3);
				return check_portal_frame(starting_point, axis);
			}
		}

		if (axis == "x") {
			if (east.getBlock().getType() == Material.AIR) {
				Location starting_point = new Location(east.getWorld(), east.getX() - 2, east.getY() + 3,
						east.getZ());
				return check_portal_frame(starting_point, axis);
			} else if (west.getBlock().getType() == Material.AIR) {
				Location starting_point = new Location(east.getWorld(), east.getX() - 3, east.getY() + 3,
						east.getZ());
				return check_portal_frame(starting_point, axis);
			}
		}

		return false;
	}

	@EventHandler
	public void onPlace(PlayerBucketEmptyEvent event) {
		Advancement aether = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:aether"));
		Player player = (Player) event.getPlayer();

		if (player.getAdvancementProgress(aether).isDone()) {
			return;
		}

		if (!event.getBucket().equals(Material.WATER_BUCKET)) {
			return;
		}

		if (event.getBlock().getType() == Material.AIR) {
			Location loc = event.getBlock().getLocation();
			Location down = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());

			if (down.getBlock().getType() == Material.GLOWSTONE) {
				if (check(loc, "x") || check(loc, "z")) {
					player.getAdvancementProgress(aether).awardCriteria("done");
				}
			}
		}
	}
}

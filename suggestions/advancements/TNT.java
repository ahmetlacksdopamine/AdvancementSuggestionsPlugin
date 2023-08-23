package suggestions.advancements;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.TNTPrimeEvent;
import org.bukkit.plugin.Plugin;

import suggestions.Main;

public class TNT implements Listener {
	@EventHandler
	public void onTNT(TNTPrimeEvent event) {
		Advancement tnt = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:tnt"));

		if (event.getPrimingEntity().getType() == EntityType.PLAYER) {
			Player player = (Player) event.getPrimingEntity();

			if (player.getAdvancementProgress(tnt).isDone()) {
				return;
			}

			Location loc = event.getBlock().getLocation();

			new Thread() {
				int i = 0;
				public void run() {
					for (int x = -15; x < 15; x++) {
						for (int y = -15; y < 15; y++) {
							for (int z = -15; z < 15; z++) {
								if (loc.getWorld()
										.getBlockAt(loc.getBlockX() + x, loc.getBlockY() + y, loc.getBlockZ() + z)
										.getType() == Material.TNT) {
									i++;
									if(i >= 50) {
										Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) Main.getPlugin(Main.class), () -> {
											player.getAdvancementProgress(tnt).awardCriteria("done");
										});
										return;
									}
								}
							}
						}
					}
				}
			}.start();
		}
	}
}

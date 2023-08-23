package suggestions.advancements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class Roof implements Listener {
	@EventHandler
	public void onTP(PlayerTeleportEvent event) {
		Advancement roof = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:roof"));
		Player player = (Player) event.getPlayer();
		
		if(player.getAdvancementProgress(roof).isDone()) {
			return;
		}

		if(player.getLocation().getY() > 127) {
			if(player.getWorld().getEnvironment().equals(World.Environment.NETHER)) {
				ItemStack obsidian = new ItemStack(Material.OBSIDIAN, 1);
				if(player.getInventory().containsAtLeast(obsidian, 10)) {
					return;
				}
				player.getAdvancementProgress(roof).awardCriteria("done");
			}
		}
	}
}

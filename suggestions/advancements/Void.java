package suggestions.advancements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Void implements Listener {
	@EventHandler
	public void onVoid(PlayerDeathEvent event) {
		Advancement fall_into_void = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:void"));
		if (event.getEntity().getType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();

			if(player.getAdvancementProgress(fall_into_void).isDone()) {
				return;
			}

			if(player.getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
				if(player.getLocation().getY() <= -64) {
					player.getAdvancementProgress(fall_into_void).awardCriteria("done");
				}
			}
		}
	}
}

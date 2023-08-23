package suggestions.advancements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Lava implements Listener {
	@EventHandler
	public void onEntityDamage(EntityDamageByBlockEvent event) {
		Advancement lava = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:lava"));
		if (event.getEntityType() == EntityType.DROPPED_ITEM) {
			if (event.getCause() == DamageCause.LAVA) {
				if (event.getEntity().getName().equals("Diamond")) {

					for (Entity entity : event.getEntity().getNearbyEntities(10, 10, 10)) {
						if (entity.getType() == EntityType.PLAYER) {
							Player player = (Player) entity;
							if (player.getAdvancementProgress(lava).isDone()) {
								return;
							}
							player.getAdvancementProgress(lava).awardCriteria("done");
						}
					}
				}
			}
		}
	}
}

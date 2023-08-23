package suggestions.advancements;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.meta.BookMeta;

public class Book implements Listener {
	@EventHandler
	public void onVoid(PlayerDropItemEvent event) {
		Advancement book = Bukkit.getAdvancement(NamespacedKey.fromString("suggestions:book"));
		Player player = (Player) event.getPlayer();

		if(player.getAdvancementProgress(book).isDone()) {
			return;
		}
		
		if(event.getItemDrop().getItemStack().getType() == Material.WRITTEN_BOOK) {
			BookMeta bookMeta = (BookMeta) event.getItemDrop().getItemStack().getItemMeta();
			if(bookMeta.getPageCount() >= 10) {
				player.getAdvancementProgress(book).awardCriteria("done");
			}
		}
	}
}

package suggestions;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import suggestions.advancements.Aether;
import suggestions.advancements.Book;
import suggestions.advancements.Herobrine;
import suggestions.advancements.Lava;
import suggestions.advancements.Roof;
import suggestions.advancements.TNT;
import suggestions.advancements.Void;
import suggestions.advancements.Wither;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin) this);
		Bukkit.getServer().getPluginManager().registerEvents(new Void(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Roof(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Book(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Aether(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Herobrine(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Wither(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Lava(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new TNT(), this);
	}

	public void onDisable() {
	}
}

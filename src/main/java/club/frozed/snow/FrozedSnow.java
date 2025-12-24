package club.frozed.snow;

import club.frozed.snow.command.ReloadCommand;
import club.frozed.snow.command.SnowCommand;
import club.frozed.snow.handler.ParticleHandler;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Elb1to
 * @since 24/Dec/2025
 */
public class FrozedSnow extends JavaPlugin {

	private ParticleHandler particleHandler;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		particleHandler = new ParticleHandler(this);

		getCommand("snow").setExecutor(new SnowCommand(this, particleHandler));
		getCommand("frozedsnow").setExecutor(new ReloadCommand(this));
	}

	@Override
	public void onDisable() {
		if (particleHandler != null) {
			particleHandler.cancel();
		}
	}

	public void reload() {
		reloadConfig();

		if (particleHandler != null) {
			particleHandler.cancel();
		}
		particleHandler = new ParticleHandler(this);

		getLogger().info("FrozedSnow reloaded successfully!");
	}
}

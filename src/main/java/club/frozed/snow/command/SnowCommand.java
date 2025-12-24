package club.frozed.snow.command;

import club.frozed.snow.FrozedSnow;
import club.frozed.snow.handler.ParticleHandler;
import club.frozed.snow.util.ChatUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Elb1to
 * @since 24/Dec/2025
 */
public class SnowCommand implements CommandExecutor {

	private final ParticleHandler particleHandler;
	private final ConfigurationSection cfg;
	private final Map<UUID, Long> cooldowns = new HashMap<>();

	public SnowCommand(FrozedSnow plugin, ParticleHandler particleHandler) {
		this.particleHandler = particleHandler;
		this.cfg = plugin.getConfig().getConfigurationSection("player-settings");
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		if (!(sender instanceof Player player)) {
			sender.sendMessage(Component.text("Only players can use this command!", NamedTextColor.RED));
			return true;
		}

		long currentTime = System.currentTimeMillis();
		long cooldownSeconds = cfg.getLong("command-cooldown");
		if (cooldowns.containsKey(player.getUniqueId())) {
			long lastUsed = cooldowns.get(player.getUniqueId());
			long timePassed = (currentTime - lastUsed) / 1000;
			if (timePassed < cooldownSeconds) {
				long timeLeft = cooldownSeconds - timePassed;
				player.sendMessage(ChatUtil.colorize(cfg.getString("cooldown-message", "You must wait {time} seconds before using this command again!")
						.replace("{time}", String.valueOf(timeLeft)))
				);
				return true;
			}
		}

		boolean enabled = particleHandler.toggleParticles(player.getUniqueId());
		cooldowns.put(player.getUniqueId(), currentTime);

		String message = enabled
				? cfg.getString("particles-enabled", "Snow particles enabled!")
				: cfg.getString("particles-disabled", "Snow particles disabled!");
		player.sendMessage(ChatUtil.colorize(message));

		return true;
	}
}

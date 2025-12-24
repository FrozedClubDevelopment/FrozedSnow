package club.frozed.snow.command;

import club.frozed.snow.FrozedSnow;
import club.frozed.snow.util.ChatUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elb1to
 * @since 24/Dec/2025
 */
public class ReloadCommand implements CommandExecutor {

	private final FrozedSnow plugin;

	public ReloadCommand(FrozedSnow plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
		if (!sender.hasPermission("frozedsnow.reload")) {
			sender.sendMessage(Component.text("You don't have permission to use this command!", NamedTextColor.RED));
			return true;
		}

		try {
			plugin.reload();
			sender.sendMessage(ChatUtil.colorize("&aFrozedSnow configuration reloaded successfully!"));
		} catch (Exception e) {
			sender.sendMessage(Component.text("Error reloading plugin: " + e.getMessage(), NamedTextColor.RED));
			plugin.getLogger().severe("Error reloading plugin: " + e.getMessage());
			e.printStackTrace();
		}

		return true;
	}
}

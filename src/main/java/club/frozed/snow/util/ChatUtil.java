package club.frozed.snow.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Elb1to
 * @since 24/Dec/2025
 */
public class ChatUtil {

	private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
	private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

	/**
	 * Translates color codes in a string to a Component.
	 * Supports both legacy codes (&a, &c, etc.) and hex colors (&#RRGGBB).
	 *
	 * @param text The text to colorize
	 * @return A Component with colors applied
	 */
	public static Component colorize(String text) {
		if (text == null || text.isEmpty()) {
			return Component.empty();
		}

		text = translateHexColors(text);
		return LEGACY_SERIALIZER.deserialize(text);
	}

	/**
	 * Translates hex color codes (&#RRGGBB) to legacy format that Adventure can parse.
	 *
	 * @param text The text containing hex colors
	 * @return Text with hex colors converted
	 */
	private static String translateHexColors(String text) {
		Matcher matcher = HEX_PATTERN.matcher(text);
		StringBuilder buffer = new StringBuilder();

		while (matcher.find()) {
			String hexCode = matcher.group(1);
			matcher.appendReplacement(buffer, "§x§" +
					hexCode.charAt(0) + "§" + hexCode.charAt(1) + "§" +
					hexCode.charAt(2) + "§" + hexCode.charAt(3) + "§" +
					hexCode.charAt(4) + "§" + hexCode.charAt(5)
			);
		}

		matcher.appendTail(buffer);
		return buffer.toString();
	}
}

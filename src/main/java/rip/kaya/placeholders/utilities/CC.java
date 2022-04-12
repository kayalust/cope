package rip.kaya.placeholders.utilities;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
public class CC {
    public static final String BLANK_LINE = "§a §b §c §d §e §f §0 §r";
    public static final String CHAT_BAR = ChatColor.translateAlternateColorCodes('&', "&9--------" + StringUtils.repeat("-", 37) + "--------");

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return toReturn;
    }

    public static String untranslate(String in) {
        return in.replace("§", "&");
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();

        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return toReturn;
    }

    /**
     * Original code from: https://www.spigotmc.org/threads/get-color-of-a-string.420939/#post-3710182
     */
    public static ChatColor getFirstColor(String s) {
        LinkedList<ChatColor> list = new LinkedList<>();
        String str1 =  s.replace(ChatColor.COLOR_CHAR, '&');

        for(int i = 0 ; i < str1.length() ; i++) {
            char c = str1.charAt(i);
            if(c == '&') {
                char id = str1.charAt(i+1);
                list.add(ChatColor.getByChar(id));
            }
        }

        return list.getFirst();
    }
}
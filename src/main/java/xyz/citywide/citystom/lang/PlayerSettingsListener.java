package xyz.citywide.citystom.lang;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;

import java.util.HashMap;
import java.util.Locale;

final class PlayerSettingsListener {

    static final HashMap<Player, Locale> locales = new HashMap<>();

    public static void listener(PlayerSettingsChangeEvent event, LangApi api) {
        if (!locales.containsKey(event.getPlayer()))
            locales.put(event.getPlayer(), event.getPlayer().getLocale());
        if(!locales.get(event.getPlayer()).equals(locales.get(event.getPlayer()))) {
            if (api.isClientLocale(event.getPlayer()))
                MinecraftServer.getGlobalEventHandler().call(new PlayerLanguageChangeEvent(event.getPlayer(), event.getPlayer().getLocale(), true));
        }
    }
}

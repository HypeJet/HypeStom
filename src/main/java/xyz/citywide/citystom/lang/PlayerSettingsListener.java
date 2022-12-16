package xyz.citywide.citystom.lang;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;

final class PlayerSettingsListener {
    public static void listener(PlayerSettingsChangeEvent event, LangApi api) {
        if(api.isClientLocale(event.getPlayer()))
            MinecraftServer.getGlobalEventHandler().call(new PlayerLanguageChangeEvent(event.getPlayer(), event.getPlayer().getLocale(), true));
    }
}

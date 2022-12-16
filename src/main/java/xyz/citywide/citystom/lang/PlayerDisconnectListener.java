package xyz.citywide.citystom.lang;

import net.minestom.server.event.player.PlayerDisconnectEvent;

final class PlayerDisconnectListener {
    public static void listener(PlayerDisconnectEvent event, LangApi api) {
        PlayerSettingsListener.locales.remove(event.getPlayer());
    }
}

package xyz.citywide.citystom.lang;

import lombok.SneakyThrows;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerSettingsChangeEvent;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Locale;

public sealed interface LangApi permits LangApiImpl {
    @SneakyThrows
    static LangApi create(@NotNull Extension extension, @NotNull Connection sqlConnection, @NotNull String table) {
        final LangApi lang = new LangApiImpl(extension, sqlConnection.createStatement(), table);
        extension.getEventNode().addListener(PlayerSettingsChangeEvent.class, event -> PlayerSettingsListener.listener(event, lang));
        return lang;
    }

    String getString(@NotNull Locale locale, @NotNull String name);

    Locale getLocale(@NotNull Player player);

    boolean setLocale(@NotNull Player player, @NotNull Locale locale);

    boolean setClientLocale(@NotNull Player player);

    boolean isClientLocale(@NotNull Player player);

    void createTable();
}

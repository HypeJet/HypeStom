package xyz.citywide.citystom.lang;

import lombok.SneakyThrows;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.Locale;

public sealed interface LangApi permits LangApiImpl {
    @SneakyThrows
    static LangApi create(@NotNull Extension extension, @NotNull Connection sqlConnection, @NotNull String table) {
        return new LangApiImpl(extension, sqlConnection.createStatement(), table);
    }

    String getString(@NotNull Locale locale, @NotNull String name);

    Locale getLocale(@NotNull Player player);

    boolean setLocale(@NotNull Player player, @NotNull Locale locale);

    boolean setClientLocale(@NotNull Player player);

    void createTable();
}

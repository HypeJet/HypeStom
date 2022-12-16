package xyz.citywide.citystom.lang;

import com.moandjiezana.toml.Toml;
import lombok.SneakyThrows;
import net.minestom.server.entity.Player;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

record LangApiImpl(@NotNull Extension extension, @NotNull Statement statement, @NotNull String table) implements LangApi {
    @Override
    public String getString(@NotNull Locale locale, @NotNull String name) {
        Path path = extension.getDataDirectory().resolve("lang").resolve(locale.getLanguage() + ".toml");
        if(!path.toFile().exists()) {
            if(locale.equals(Locale.ENGLISH))
                throw new UnsupportedOperationException("No Default Language");
            return getString(Locale.ENGLISH, name);
        } else {
            Toml toml = new Toml().read(path.toFile());
            if(toml.getString(name) == null) {
                if(locale.equals(Locale.ENGLISH))
                    throw new UnsupportedOperationException("No Default For " + name);
                return getString(Locale.ENGLISH, name);
            }
            return toml.getString(name);
        }
    }

    @SneakyThrows
    @Override
    public Locale getLocale(@NotNull Player player) {
        ResultSet data = statement.executeQuery("SELECT * from " + table + " WHERE uuid = " + "\"" + player.getUuid() + "\"");
        if(data.next()) {
            final String locale = data.getString("locale");
            if (locale.equals("CLIENT"))
                return player.getLocale();
            else
                return Locale.forLanguageTag(locale);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public boolean setLocale(@NotNull Player player, @NotNull Locale locale) {
        boolean exists;
        ResultSet data = statement.executeQuery("SELECT * from " + table + " WHERE EXISTS (SELECT uuid FROM " + table + " WHERE uuid = \"" + player.getUuid() + "\")");
        exists = data.next();
        if(exists) {
            PreparedStatement ps = statement.getConnection().prepareStatement("UPDATE " + table + " SET locale=? WHERE uuid=?");
            ps.setString(1, locale.toLanguageTag());
            ps.setString(2, player.getUuid().toString());
            ps.executeUpdate();
            ps.close();
        } else
            statement.execute("INSERT INTO " + table + " (uuid, locale) VALUES (\"" + player.getUuid() + "\", " + locale.toLanguageTag() + ")");
        return exists;
    }

    @SneakyThrows
    @Override
    public boolean setClientLocale(@NotNull Player player) {
        boolean exists;
        ResultSet data = statement.executeQuery("SELECT * from " + table + " WHERE EXISTS (SELECT uuid FROM " + table + " WHERE uuid = \"" + player.getUuid() + "\")");
        exists = data.next();
        if(exists) {
            PreparedStatement ps = statement.getConnection().prepareStatement("UPDATE " + table + " SET locale=? WHERE uuid=?");
            ps.setString(1, "CLIENT");
            ps.setString(2, player.getUuid().toString());
            ps.executeUpdate();
            ps.close();
        } else
            statement.execute("INSERT INTO " + table + " (uuid, locale) VALUES (\"" + player.getUuid() + "\", \"CLIENT\")");
        return exists;
    }

    @SneakyThrows
    @Override
    public boolean isClientLocale(@NotNull Player player) {
        ResultSet data = statement.executeQuery("SELECT * from " + table + " WHERE uuid = " + "\"" + player.getUuid() + "\"");
        if(data.next())
            return data.getString("locale").equals("CLIENT");
        else
            return false;
    }

    @SneakyThrows
    @Override
    public void createTable() {
        statement.execute("CREATE TABLE IF NOT EXISTS " + table + " (uuid VARCHAR(100), locale VARCHAR(100))");
    }
}

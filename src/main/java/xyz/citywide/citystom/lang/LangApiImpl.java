package xyz.citywide.citystom.lang;

import com.moandjiezana.toml.Toml;
import net.minestom.server.extensions.Extension;

import java.nio.file.Path;
import java.util.Locale;

record LangApiImpl(Extension extension) implements LangApi {
    @Override
    public String getString(Locale locale, String name) {
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
}

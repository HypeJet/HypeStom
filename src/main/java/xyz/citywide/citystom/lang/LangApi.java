package xyz.citywide.citystom.lang;

import net.minestom.server.extensions.Extension;

import java.util.Locale;

public sealed interface LangApi permits LangApiImpl {
    static LangApi create(Extension extension) {
        return new LangApiImpl(extension);
    }

    String getString(Locale locale, String name);
}

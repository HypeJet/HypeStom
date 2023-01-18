package org.hypejet.hypestom.tablist;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.minestom.server.entity.Player;

import java.util.ArrayList;

public class GlobalTablist extends Tablist{
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Component>[] createPlayerList(Player player) {
        return new ArrayList[]{header, footer};
    }

    @Getter @Setter private ArrayList<Component> header = new ArrayList<>();
    @Getter @Setter private ArrayList<Component> footer = new ArrayList<>();

    public void addHeaderLine(Component line) {
        header.add(line);
    }
    public void addFooterLine(Component line) {
        footer.add(line);
    }
    public void removeHeaderLine(Component line) {
        header.remove(line);
    }
    public void removeFooterLine(Component line) {
        footer.remove(line);
    }
    public void removeHeaderLine(int line) {
        header.remove(line);
    }
    public void removeFooterLine(int line) {
        footer.remove(line);
    }
}

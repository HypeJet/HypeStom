package org.labgames.hypejet.hypestom;

import com.moandjiezana.toml.Toml;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.labgames.hypejet.hypestom.permissions.PermissionProvider;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Extension extends net.minestom.server.extensions.Extension {

    @Getter private @Nullable PermissionProvider permissionProvider;

    public final void registerCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().register(command);
    }

    public final void unregisterCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().unregister(command);
    }

    public final void registerCommand(@NotNull org.labgames.hypejet.hypestom.command.Command command) {
        MinecraftServer.getCommandManager().register(command);
    }

    public final void unregisterCommand(@NotNull org.labgames.hypejet.hypestom.command.Command command) {
        MinecraftServer.getCommandManager().unregister(command);
    }

    public final void registerDimension(@NotNull DimensionType type) {
        MinecraftServer.getDimensionTypeManager().addDimension(type);
    }

    public final void registerBlockHandler(@NotNull BlockHandler handler) {
        MinecraftServer.getBlockManager().registerHandler(handler.getNamespaceId(), () -> handler);
    }

    public final void runAsync(@NotNull Runnable runnable) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.immediate(),
                TaskSchedule.stop(), ExecutionType.ASYNC);
    }

    public final void runSync(@NotNull Runnable runnable) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.immediate(),
                TaskSchedule.stop(), ExecutionType.SYNC);
    }

    public final void runDelayedAsync(@NotNull Runnable runnable, @NotNull Duration delay) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.duration(delay),
                TaskSchedule.stop(), ExecutionType.ASYNC);
    }

    public final void runDelayedSync(@NotNull Runnable runnable, @NotNull Duration delay) {
        MinecraftServer.getSchedulerManager().scheduleTask(runnable, TaskSchedule.duration(delay),
                TaskSchedule.stop(), ExecutionType.SYNC);
    }

    public final @Nullable Toml getConfig() {
        InputStream configResource = getClass().getClassLoader().getResourceAsStream("config.toml");
        if (configResource == null) {
            getLogger().error(Component.text("config.toml does not exists in resources folder"));
            return null;
        }

        Toml defaults = new Toml();
        defaults.read(configResource);

        Toml toml = new Toml(defaults);
        Path path = Path.of(String.valueOf(getDataDirectory()), "config.toml");

        try {
            if(!Files.exists(path)) {
                if(!Files.exists(getDataDirectory()))
                    Files.createDirectory(path);
                Files.createFile(path);
                OutputStream stream = Files.newOutputStream(path);
                stream.write(configResource.readAllBytes());
                configResource.close();
                stream.close();
                return toml;
            } else {
                configResource.close();
                return toml.read(Files.newInputStream(path));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

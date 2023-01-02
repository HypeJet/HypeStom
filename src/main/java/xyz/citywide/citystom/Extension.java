package xyz.citywide.citystom;

import com.moandjiezana.toml.Toml;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Path;
import java.time.Duration;

public abstract class Extension extends net.minestom.server.extensions.Extension {
    public final void registerCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().register(command);
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
        File file = Path.of(String.valueOf(getDataDirectory()), "config.toml").toFile();

        if(!file.exists()) {
            try {
                byte[] bytes = configResource.readAllBytes();
                if(!getDataDirectory().toFile().exists())
                    getDataDirectory().toFile().mkdir();
                File f = new File(getDataDirectory().toFile(), "config.toml");
                f.createNewFile();
                OutputStream stream = new FileOutputStream(f);
                stream.write(bytes);
                stream.close();
                file = f;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            configResource.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return toml.read(file);
    }
}

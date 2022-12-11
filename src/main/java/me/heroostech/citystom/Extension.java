package me.heroostech.citystom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.TaskSchedule;
import net.minestom.server.world.DimensionType;
import org.jetbrains.annotations.NotNull;

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
}

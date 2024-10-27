package xyz.saturnvolv.nomoreaccidents.nomoreaccidents;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.listeners.ServerTickListener;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.recipe.ModRecipeSerializer;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.registry.ModGameRules;

public class NoMoreAccidents implements ModInitializer {
    public static final String MOD_ID = "nomoreaccidents";


    @Nullable
    private static MinecraftServer server = null;

    public static boolean hasServer() {
        return server() != null;
    }
    public static MinecraftServer server() {
        return server;
    }

    @Override
    public void onInitialize() {
        ModRecipeSerializer.init();
        ModGameRules.init();
        ServerTickEvents.END_SERVER_TICK.register(new ServerTickListener());
    }

    public static void onServerTick(MinecraftServer server) {
        if (NoMoreAccidents.server == null)
            NoMoreAccidents.server = server;
        // ..
    }
}

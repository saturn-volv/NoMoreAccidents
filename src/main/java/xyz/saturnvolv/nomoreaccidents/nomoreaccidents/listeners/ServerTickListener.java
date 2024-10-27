package xyz.saturnvolv.nomoreaccidents.nomoreaccidents.listeners;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.NoMoreAccidents;

public class ServerTickListener implements ServerTickEvents.EndTick {
    @Override
    public void onEndTick(MinecraftServer server) {
        NoMoreAccidents.onServerTick(server);
    }
}

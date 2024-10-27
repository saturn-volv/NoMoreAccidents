package xyz.saturnvolv.nomoreaccidents.nomoreaccidents.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> BLOCK_COPPER_INTERACTION =
            GameRuleRegistry.register(
                    "blockCopperAxeInteraction", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false)
            );
    public static void init() {}
}

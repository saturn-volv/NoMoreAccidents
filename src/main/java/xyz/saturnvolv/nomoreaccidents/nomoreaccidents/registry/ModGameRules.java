package xyz.saturnvolv.nomoreaccidents.nomoreaccidents.registry;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {
    public static final GameRules.Key<GameRules.BooleanRule> BLOCK_COPPER_INTERACTION =
            GameRuleRegistry.register(
                    "blockCopperAxeInteraction", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false)
            );
    // teehee :3c
    public static final GameRules.Key<GameRules.IntRule> LOG_STRIP_PENALTY =
            GameRuleRegistry.register(
                    "logStrippingPenalty", GameRules.Category.MISC, GameRuleFactory.createIntRule(1, 0, 64)
            );
    public static final GameRules.Key<GameRules.BooleanRule> PLAY_SOUND_EFFECT =
            GameRuleRegistry.register(
                    "playStripDenySounds", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(false)
            );
    public static void init() {}
}

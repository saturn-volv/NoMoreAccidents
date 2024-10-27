package xyz.saturnvolv.nomoreaccidents.nomoreaccidents.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.NoMoreAccidents;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.registry.ModGameRules;

import java.util.Optional;

@Mixin(AxeItem.class)
public abstract class AxeItemMixin {

    @ModifyVariable(method = "useOnBlock", at = @At("STORE"), ordinal = 0)
    public Optional<BlockState> preventLogStrip(Optional<BlockState> optional, @Local ItemUsageContext context) {
        if (optional.isPresent() && context.getPlayer() != null) {
            sendPlayerAxeUseWarning(context.getPlayer(), context.getStack());
        }
        return Optional.empty();
    }
    @ModifyVariable(method = "useOnBlock", at = @At("STORE"), ordinal = 1)
    public Optional<BlockState> preventCopperStrip(Optional<BlockState> optional, @Local ItemUsageContext context) {
        if (!context.getWorld().getGameRules().getBoolean(ModGameRules.BLOCK_COPPER_INTERACTION)) return optional;
        if (optional.isPresent() && context.getPlayer() != null) {
            sendPlayerAxeUseWarning(context.getPlayer(), context.getStack());
        }
        return Optional.empty();
    }
    @ModifyVariable(method = "useOnBlock", at = @At("STORE"), ordinal = 2)
    public Optional<BlockState> preventCopperUnwax(Optional<BlockState> optional, @Local ItemUsageContext context) {
        if (!context.getWorld().getGameRules().getBoolean(ModGameRules.BLOCK_COPPER_INTERACTION)) return optional;
        if (optional.isPresent() && context.getPlayer() != null) {
            sendPlayerAxeUseWarning(context.getPlayer(), context.getStack());
        }
        return Optional.empty();
    }

    private void sendPlayerAxeUseWarning(PlayerEntity player, ItemStack itemStack) {
        if (player.getWorld().getGameRules().getBoolean(ModGameRules.PLAY_SOUND_EFFECT)) // ????? WHY DOES THIS NOT WORK???
            player.playSound(SoundEvent.of(Identifier.of(NoMoreAccidents.MOD_ID, "boowomp")), SoundCategory.BLOCKS, 1, 1);

        if (!player.getItemCooldownManager().isCoolingDown(itemStack.getItem())) {
            player.sendMessage(
                    Text.literal("Cannot interact with block.")
                            .formatted(Formatting.RED), true
            );
            player.getItemCooldownManager().set(itemStack.getItem(), 20); // tempted to remove the cooldown but it stops spamming the noise ig
        }

    }
}

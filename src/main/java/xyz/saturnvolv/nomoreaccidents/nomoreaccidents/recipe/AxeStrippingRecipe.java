package xyz.saturnvolv.nomoreaccidents.nomoreaccidents.recipe;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.*;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import xyz.saturnvolv.nomoreaccidents.nomoreaccidents.NoMoreAccidents;

import java.util.Map;
import java.util.Optional;

public class AxeStrippingRecipe extends SpecialCraftingRecipe {
    private static final Map<Block, Block> STRIPPED_BLOCKS = new ImmutableMap.Builder()
            .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
            .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
            .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
            .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
            .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
            .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
            .put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD)
            .put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG)
            .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
            .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
            .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
            .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
            .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
            .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
            .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).
            put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
            .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
            .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
            .put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD)
            .put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG)
            .put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK)
            .build();

    public AxeStrippingRecipe(Identifier id, CraftingRecipeCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        boolean hasAxe = false;
        ItemStack itemStack = ItemStack.EMPTY;
        for (int j=0; j < recipeInputInventory.size(); j++) {
            ItemStack input = recipeInputInventory.getStack(j);
            if (!input.isEmpty()) {
                if (input.getItem() instanceof BlockItem blockItem) {
                    BlockState blockState = blockItem.getBlock().getDefaultState();
                    Optional<BlockState> optional = getStrippedState(blockState);
                    if (optional.isEmpty() || !itemStack.isEmpty())
                        return false;

                    itemStack = input;
                } else {
                    if (!input.isIn(ItemTags.AXES))
                        return false;
                    hasAxe = true;
                }
            }
        }
        return !itemStack.isEmpty() && hasAxe;
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        System.out.println("Attempting to craft stripped log,,,");
        boolean hasAxe = false;
        ItemStack output = ItemStack.EMPTY;
        for (int j=0; j < recipeInputInventory.size(); j++) {
            ItemStack input = recipeInputInventory.getStack(j);
            if (!input.isEmpty()) {
                if (input.getItem() instanceof BlockItem blockItem) {
                    BlockState blockState = blockItem.getBlock().getDefaultState();
                    Optional<BlockState> optional = getStrippedState(blockState);
                    if (optional.isEmpty() || !output.isEmpty())
                        return ItemStack.EMPTY;

                    output = new ItemStack(optional.get().getBlock()).copyWithCount(1);
                } else {
                    if (!input.isIn(ItemTags.AXES))
                        return ItemStack.EMPTY;
                    hasAxe = true;
                }
            }
        }
        if (hasAxe)
            return output;
        return ItemStack.EMPTY;
    }

    @Override
    public DefaultedList<ItemStack> getRemainder(RecipeInputInventory inventory) {
        DefaultedList<ItemStack> remainderList = DefaultedList.ofSize(inventory.size(), ItemStack.EMPTY);
        for (int i = 0; i < remainderList.size(); i++) {
            ItemStack itemStack = inventory.getStack(i);
            if (itemStack.getItem() instanceof AxeItem) {
                if (NoMoreAccidents.hasServer())
                    itemStack.damage(1, NoMoreAccidents.server().getOverworld().getRandom(), null);
                remainderList.set(i, itemStack.copy());
                break;
            }
        }
        return remainderList;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializer.LOG_STRIPPING;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map((block) -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
    }
}

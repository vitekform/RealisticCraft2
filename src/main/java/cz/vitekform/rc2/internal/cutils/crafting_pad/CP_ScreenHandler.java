package cz.vitekform.rc2.internal.cutils.crafting_pad;

import cz.vitekform.rc2.ModBlocks;
import cz.vitekform.rc2.ModComponents;
import cz.vitekform.rc2.ModItems;
import cz.vitekform.rc2.internal.blocks.CraftingPadBlock;
import cz.vitekform.rc2.internal.cutils.crafting_pad.CP_ScreenHandlers;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.logging.Log;

import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

public class CP_ScreenHandler extends ScreenHandler {
    private final CraftingInventory craftingInventory;
    private final Inventory resultInventory;
    private final PropertyDelegate propertyDelegate;
    private final World world;
    private BlockPos bpos;

    public CP_ScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(10), new ArrayPropertyDelegate(1));
    }

    public CP_ScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos bpos) {
        this(syncId, playerInventory, new SimpleInventory(10), new ArrayPropertyDelegate(1));
        this.bpos = bpos;
    }

    public CP_ScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(CP_ScreenHandlers.PEBBLE_CRAFTING_SCREEN_HANDLER, syncId);
        this.craftingInventory = new CraftingInventory(this, 3, 3);
        this.resultInventory = new SimpleInventory(1); // Only one slot for result
        this.propertyDelegate = propertyDelegate;
        this.world = playerInventory.player.getWorld();

        // Add crafting grid slots (3x3)
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(craftingInventory, col + row * 3, 30 + col * 18, 17 + row * 18));
            }
        }

        // Add result slot
        this.addSlot(new CraftingResultSlot(playerInventory.player, craftingInventory, resultInventory, 0, 124, 35) {
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                // Consume the ingredients
                for (int i = 0; i < 9; i++) {
                    ItemStack ingredient = craftingInventory.getStack(i);
                    if (!ingredient.isEmpty()) {
                        ingredient.decrement(1);
                    }
                }

                if (bpos != null) {
                    BlockState state = world.getBlockState(bpos);
                    int used_times;
                    if (state.contains(CraftingPadBlock.UsedTimes)) {
                        used_times = state.get(CraftingPadBlock.UsedTimes);
                    }
                    else {
                        used_times = 0;
                    }
                    used_times++;
                    if (used_times >= 5) {
                        world.setBlockState(bpos, Registries.BLOCK.get(Identifier.of("minecraft:air")).getDefaultState());
                        world.playSound(bpos.getX(), bpos.getY(), bpos.getZ(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS, 2.0f, 1.0f, false);
                        ServerPlayerEntity playerEntity = (ServerPlayerEntity) player;
                        CloseScreenS2CPacket packet = new CloseScreenS2CPacket(playerEntity.currentScreenHandler.syncId);
                        playerEntity.networkHandler.sendPacket(packet);
                    }
                    else {
                        if (state.contains(CraftingPadBlock.UsedTimes)) {
                            world.setBlockState(bpos, state.with(CraftingPadBlock.UsedTimes, used_times));
                        }
                    }
                }
                // Update the result
                CP_ScreenHandler.this.updateCraftingResult();
            }
        });

        // Add player inventory slots
        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
    }

    private void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot == 9) { // Result slot
                if (!this.insertItem(originalStack, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onTakeItem(player, originalStack);
            } else if (invSlot < 9) { // Crafting grid slots
                if (!this.insertItem(originalStack, 10, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else { // Player inventory slots (10-45)
                if (!this.insertItem(originalStack, 0, 9, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (originalStack.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, originalStack);
        }
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (!this.world.isClient) {
            updateCraftingResult();
        }
    }

    private void updateCraftingResult() {
        RecipeManager recipeManager = world.getRecipeManager();
        // Get all available recipes
        Collection<RecipeEntry<?>> recipes = recipeManager.sortedValues();

        for (RecipeEntry<?> entry : recipes) {
            // Check if the recipe is a CraftingRecipe
            if (entry.value() instanceof CraftingRecipe recipe) {
                // Check if the recipe matches the current crafting inventory
                if (recipe.matches(craftingInventory.createRecipeInput(), world)) {
                    // Craft the item and set it as the result
                    ItemStack result = recipe.craft(craftingInventory.createRecipeInput(), world.getRegistryManager());
                    this.resultInventory.setStack(0, result);
                    return; // Exit after finding the first match
                }
            }
        }

        // If no match is found, set the result to empty
        this.resultInventory.setStack(0, ItemStack.EMPTY);
    }
}

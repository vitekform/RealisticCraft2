package cz.vitekform.rc2.events;

import cz.vitekform.rc2.ModItems;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

/*
What this does:
When you mine log without an axe you don't get the block, but the block is still mined.
When you mine log with axe it strips it and drops bark.
When you mine stripped log with axe it changes itselfs to planks.
 */
public class logChoppingFix {
    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, blockPos, blockState, blockEntity) -> {
            if (blockState.getBlock().getTranslationKey().contains("log") && !(blockState.getBlock().getTranslationKey().contains("stripped"))) {
                if (player.getStackInHand(player.getActiveHand()).getItem().getTranslationKey().contains("axe")) {
                    String blockName = blockState.getBlock().getTranslationKey();
                    String[] parts = blockName.split("\\.");
                    String strippedBlockName = "";
                    for (int i = 0; i < parts.length; i++) {
                        if (i == parts.length - 1) {
                            strippedBlockName += "stripped_";
                        }
                        strippedBlockName += parts[i];
                        if (i != parts.length - 1) {
                            strippedBlockName += ".";
                        }
                    }
                    // Get the stripped log block state from its translation key
                    strippedBlockName = strippedBlockName.replace("block.minecraft.", "minecraft:");
                    BlockState newBState = Registries.BLOCK.get(Identifier.of(strippedBlockName)).getDefaultState();
                    // Set the block to stripped log
                    world.setBlockState(blockPos, newBState);
                    // Drop bark
                    world.spawnEntity(new ItemEntity(world, blockPos.getX(), blockPos.getY() + 0.5, blockPos.getZ(), ModItems.WOOD_BARK.getDefaultStack()));
                    return false;
                }
                else {
                    return false;
                }
            }
            else if (blockState.getBlock().getTranslationKey().contains("stripped") && blockState.getBlock().getTranslationKey().contains("log")) {
                if (player.getStackInHand(player.getActiveHand()).getItem().getTranslationKey().contains("axe")) {
                    // Get the planks block state from its translation key
                    String blockName = blockState.getBlock().getTranslationKey();
                    String ps = blockName.split("stripped_")[1];
                    ps = ps.split("_log")[0];
                    String planksBlockName = "minecraft:" + ps + "_planks";
                    Logger.getGlobal().info(planksBlockName);
                    BlockState newBState = Registries.BLOCK.get(Identifier.of(planksBlockName)).getDefaultState();
                    // Set the block to planks
                    world.setBlockState(blockPos, newBState);
                    return false;
                }
                else {
                    return false;
                }
            }
            return true;
        });
    }
}
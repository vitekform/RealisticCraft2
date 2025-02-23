package cz.vitekform.rc2.events;

import cz.vitekform.rc2.ModItems;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import java.util.Random;

public class rockSharpening {

    public static void init() {
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            boolean canHarvestBlock = player.getStackInHand(player.getActiveHand()).isSuitableFor(state);
            boolean isRock = state.getBlock() == Blocks.STONE || state.getBlock() == Blocks.GRANITE || state.getBlock() == Blocks.DIORITE || state.getBlock() == Blocks.ANDESITE || state.getBlock() == Blocks.COBBLESTONE || state.getBlock() == Blocks.DEEPSLATE || state.getBlock() == Blocks.TUFF || state.getBlock() == Blocks.BASALT || state.getBlock() == Blocks.BLACKSTONE || state.getBlock() == Blocks.COBBLED_DEEPSLATE;
            if (!canHarvestBlock && isRock) {
                ItemStack rocks = new ItemStack(ModItems.ROCK_ITEM);
                int amount = new Random().nextInt(1, 5);
                rocks.setCount(amount);
                world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), rocks));
                return true;
            }
            return true;
        });


        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            if (player.getStackInHand(hand).getItem() == ModItems.ROCK_ITEM && block.getHardness() >= 1.5f) {
                if (new Random().nextInt(1, 6) == 5) {
                    player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                    player.getStackInHand(hand).decrement(1);
                    world.spawnEntity(new ItemEntity(world, hitResult.getBlockPos().getX(), hitResult.getBlockPos().getY(), hitResult.getBlockPos().getZ(), new ItemStack(ModItems.SHARP_ROCK_ITEM)));
                    return ActionResult.SUCCESS;
                }
                else {
                    player.playSound(SoundEvents.BLOCK_STONE_BREAK, 1.0F, 1.0F);
                    player.getStackInHand(hand).decrement(1);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}

package cz.vitekform.rc2.energy.blocks.blocks;

import com.mojang.serialization.MapCodec;
import cz.vitekform.rc2.energy.blocks.blockentities.BatteryBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BatteryBlock extends BlockWithEntity {
    public static final MapCodec<BatteryBlock> CODEC = createCodec(BatteryBlock::new);

    public BatteryBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BatteryBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        //if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BatteryBlockEntity battery) {
                String energyInfo = String.format("Stored Energy: %.2f W (%.1f%%)",
                        battery.getStoredEnergy(),
                        (battery.getStoredEnergy() / battery.getMaxEnergy()) * 100);
                player.sendMessage(Text.literal(energyInfo), false);
            }
            else {
                player.sendMessage(Text.literal("Error - blockEntity not BatteryBlockEntity!"), false);
            }
        //}
        //else {
        //    player.sendMessage(Text.literal("Error - world is client!"), false);
        //}
        return ActionResult.SUCCESS;
    }
}
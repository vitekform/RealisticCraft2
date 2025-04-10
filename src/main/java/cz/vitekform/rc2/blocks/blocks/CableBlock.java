package cz.vitekform.rc2.blocks.blocks;

import com.mojang.serialization.MapCodec;
import cz.vitekform.rc2.ModBlockEntities;
import cz.vitekform.rc2.blocks.blockentities.CableBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CableBlock extends BlockWithEntity {
    public static final MapCodec<CableBlock> CODEC = createCodec(CableBlock::new);

    public CableBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CableBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.CABLE_BLOCK_ENTITY,
                (world1, pos, state1, be) -> ((CableBlockEntity) be).tick(world1, pos, state1, (CableBlockEntity) be));
    }
}
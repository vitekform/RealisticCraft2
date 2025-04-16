package cz.vitekform.rc2.energy.blocks.blocks;

import cz.vitekform.rc2.energy.blocks.blockentities.GeneratorBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import cz.vitekform.rc2.ModBlockEntities;

public class GeneratorBlock extends BlockWithEntity {
    public static final MapCodec<GeneratorBlock> CODEC = createCodec(GeneratorBlock::new);

    public GeneratorBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GeneratorBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.GENERATOR_BLOCK_ENTITY,
                (world1, pos, state1, be) -> ((GeneratorBlockEntity) be).tick(world1, pos, state1, (GeneratorBlockEntity) be));
    }
}
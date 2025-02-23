package cz.vitekform.rc2;

import cz.vitekform.rc2.internal.blocks.CraftingPadBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class ModBlocks {

    public static Block register(Block block, RegistryKey<Block> blockKey, boolean shouldRegisterItem) {
        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            // Items need to be registered with a different type of registry key, but the ID
            // can be the same.
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());

            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    public static final RegistryKey<Block> CRAFTING_PAD_KEY = RegistryKey.of(
            RegistryKeys.BLOCK,
            Identifier.of("rc2", "crafting_pad")
    );

    public static final Block CRAFTING_PAD = register(
            new CraftingPadBlock(Block.Settings.create()
                    .sounds(BlockSoundGroup.STONE)
                    .dropsNothing()
                    .hardness(2)
                    .strength(1.0f, 3.0f)),
            CRAFTING_PAD_KEY,
            true
    );

    public static void init() {
        if (CRAFTING_PAD == null) {
            Logger.getLogger("rc2").warning("Failed to initialize Crafting Pad block.");
        }
    }
}

package cz.vitekform.rc2;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class Rc2RecipeProvider extends FabricRecipeProvider {
    public Rc2RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_AXE)
                .input('S', Items.STICK)
                .input('R', ModItems.SHARP_ROCK_ITEM)
                .pattern("RR")
                .pattern("RS")
                .pattern(" S")
                .criterion(hasItem(ModItems.SHARP_ROCK_ITEM), conditionsFromItem(ModItems.SHARP_ROCK_ITEM))
                .offerTo(recipeExporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STONE_PICKAXE)
                .input('S', Items.STICK)
                .input('R', ModItems.SHARP_ROCK_ITEM)
                .pattern("RRR")
                .pattern(" S ")
                .pattern(" S ")
                .criterion(hasItem(ModItems.SHARP_ROCK_ITEM), conditionsFromItem(ModItems.SHARP_ROCK_ITEM))
                .offerTo(recipeExporter);
    }


    @Override
    public String getName() {
        return "FabricDocsReferenceRecipeProvider";
    }
}

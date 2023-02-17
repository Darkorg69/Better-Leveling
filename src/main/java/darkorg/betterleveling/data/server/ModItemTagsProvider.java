package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModItems;
import darkorg.betterleveling.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, TagsProvider<Block> pBlockTagsProvider, ExistingFileHelper pExistingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTagsProvider, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider pLookupProvider) {
        tag(ModTags.Items.SKIN).add(Items.RABBIT_HIDE).addTag(ItemTags.WOOL).addTag(Tags.Items.FEATHERS).addTag(Tags.Items.LEATHER);
        tag(ModTags.Items.MEAT).addTag(ModTags.Items.MEAT_RAW).addTag(ModTags.Items.MEAT_COOKED);
        tag(ModTags.Items.MEAT_RAW).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, Items.ROTTEN_FLESH);
        tag(ModTags.Items.MEAT_COOKED).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT);
        tag(ModTags.Items.TREASURES_RARE).addTag(ModTags.Items.RESOURCES);
        tag(ModTags.Items.TREASURES_COMMON).add(Items.CLAY_BALL).addTag(Tags.Items.BONES).addTag(Tags.Items.STRING);
        tag(ModTags.Items.TREASURES_UNCOMMON).addTag(ItemTags.ARROWS).addTag(Tags.Items.DUSTS).addTag(Tags.Items.RODS);
        tag(ModTags.Items.RESOURCES_COAL).add(Items.COAL);
        tag(ModTags.Items.RESOURCES_COPPER).add(Items.RAW_COPPER);
        tag(ModTags.Items.RESOURCES_DIAMOND).add(Items.DIAMOND);
        tag(ModTags.Items.RESOURCES_EMERALD).add(Items.EMERALD);
        tag(ModTags.Items.RESOURCES_GOLD).add(Items.RAW_GOLD, Items.GOLD_NUGGET);
        tag(ModTags.Items.RESOURCES_IRON).add(Items.RAW_IRON, Items.IRON_NUGGET);
        tag(ModTags.Items.RESOURCES_LAPIS).add(Items.LAPIS_LAZULI);
        tag(ModTags.Items.RESOURCES_NETHERITE_SCRAP).add(ModItems.RAW_DEBRIS.get());
        tag(ModTags.Items.RESOURCES_QUARTZ).add(Items.QUARTZ);
        tag(ModTags.Items.RESOURCES_REDSTONE).add(Items.REDSTONE);
        tag(ModTags.Items.RESOURCES).addTags(ModTags.Items.RESOURCES_COAL, ModTags.Items.RESOURCES_COPPER, ModTags.Items.RESOURCES_DIAMOND, ModTags.Items.RESOURCES_EMERALD, ModTags.Items.RESOURCES_GOLD, ModTags.Items.RESOURCES_IRON, ModTags.Items.RESOURCES_LAPIS, ModTags.Items.RESOURCES_NETHERITE_SCRAP, ModTags.Items.RESOURCES_QUARTZ, ModTags.Items.RESOURCES_REDSTONE);
    }
}
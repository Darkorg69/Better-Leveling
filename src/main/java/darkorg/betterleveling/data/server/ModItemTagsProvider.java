package darkorg.betterleveling.data.server;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTagLookupProvider, ExistingFileHelper pExistingFileHelper) {
        super(pPackOutput, pLookupProvider, pBlockTagLookupProvider, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider pLookupProvider) {
        tag(ModTags.Items.SKIN).add(Items.RABBIT_HIDE).addTags(ItemTags.WOOL, Tags.Items.FEATHERS, Tags.Items.LEATHER);

        tag(ModTags.Items.MEAT).addTags(ModTags.Items.MEAT_RAW, ModTags.Items.MEAT_COOKED);
        tag(ModTags.Items.MEAT_RAW).add(Items.BEEF, Items.CHICKEN, Items.MUTTON, Items.PORKCHOP, Items.RABBIT, Items.ROTTEN_FLESH);
        tag(ModTags.Items.MEAT_COOKED).add(Items.COOKED_BEEF, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_PORKCHOP, Items.COOKED_RABBIT);

        tag(ModTags.Items.TREASURES_COMMON).add(Items.CLAY_BALL).addTags(Tags.Items.STRING);
        tag(ModTags.Items.TREASURES_UNCOMMON).addTags(ItemTags.ARROWS, Tags.Items.BONES);
        tag(ModTags.Items.TREASURES_RARE).addTags(Tags.Items.GEMS, Tags.Items.NUGGETS, Tags.Items.RODS);
    }
}
package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public final ModelFile generated = getExistingFile(mcLoc(ITEM_FOLDER + "/generated"));

    public ModItemModelProvider(PackOutput pPackOutput, ExistingFileHelper pExistingFileHelper) {
        super(pPackOutput, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemGenerated(ModItems.RAW_DEBRIS);
    }

    private void itemGenerated(Item pItem) {
        buildItemModel(pItem.toString(), generated);
    }

    private void itemGenerated(RegistryObject<Item> pRegistryObject) {
        itemGenerated(pRegistryObject.get());
    }

    private void buildItemModel(String pName, ModelFile pModel) {
        getBuilder(pName).parent(pModel).texture("layer0", ITEM_FOLDER + "/" + pName);
    }
}

package darkorg.betterleveling.data.client;

import darkorg.betterleveling.BetterLeveling;
import darkorg.betterleveling.registry.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public final ModelFile generated = getExistingFile(mcLoc(ITEM_FOLDER + "/generated"));

    public ModItemModelProvider(DataGenerator pGenerator, ExistingFileHelper pExistingFileHelper) {
        super(pGenerator, BetterLeveling.MOD_ID, pExistingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemGenerated(ModItems.RAW_IRON);
        itemGenerated(ModItems.RAW_GOLD);
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

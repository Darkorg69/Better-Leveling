package darkorg.betterleveling.data;

import darkorg.betterleveling.data.loot.BlockLootModifier;
import darkorg.betterleveling.data.loot.EntityLootModifier;
import darkorg.betterleveling.data.loot.TreasureLootModifier;
import darkorg.betterleveling.registry.LootModifierSerializers;
import darkorg.betterleveling.registry.SkillRegistry;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.state.Property;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(DataGenerator dataGenerator, String modId) {
        super(dataGenerator, modId);
    }

    @Override
    protected void start() {
        addHarvestEfficiencyLoot("beetroots", Blocks.BEETROOTS, Items.BEETROOT, BeetrootBlock.BEETROOT_AGE, 3);
        addHarvestEfficiencyLoot("carrots", Blocks.CARROTS, Items.CARROT, CarrotBlock.AGE, 7);
        addHarvestEfficiencyLoot("nether_wart", Blocks.NETHER_WART, Items.NETHER_WART, NetherWartBlock.AGE, 3);
        addHarvestEfficiencyLoot("potatoes", Blocks.POTATOES, Items.POTATO, PotatoBlock.AGE, 7);
        addHarvestEfficiencyLoot("sweet_berries", Blocks.SWEET_BERRY_BUSH, Items.SWEET_BERRIES, SweetBerryBushBlock.AGE, 3);
        addHarvestEfficiencyLoot("wheat", Blocks.WHEAT, Items.WHEAT, CropsBlock.AGE, 7);

        addMeatGatheringLoot("chicken", EntityType.CHICKEN, Items.CHICKEN);
        addMeatGatheringLoot("cow", EntityType.COW, Items.BEEF);
        addMeatGatheringLoot("hoglin", EntityType.HOGLIN, Items.PORKCHOP);
        addMeatGatheringLoot("pig", EntityType.PIG, Items.PORKCHOP);
        addMeatGatheringLoot("rabbit", EntityType.RABBIT, Items.RABBIT);
        addMeatGatheringLoot("sheep", EntityType.SHEEP, Items.MUTTON);

        addProspectingLoot("ancient_debris", Blocks.ANCIENT_DEBRIS, Items.ANCIENT_DEBRIS);
        addProspectingLoot("coal_ore", Blocks.COAL_ORE, Items.COAL);
        addProspectingLoot("diamond_ore", Blocks.DIAMOND_ORE, Items.DIAMOND);
        addProspectingLoot("emerald_ore", Blocks.EMERALD_ORE, Items.EMERALD);
        addProspectingLoot("gold_ore", Blocks.GOLD_ORE, Items.GOLD_ORE);
        addProspectingLoot("iron_ore", Blocks.IRON_ORE, Items.IRON_ORE);
        addProspectingLoot("lapis_ore", Blocks.LAPIS_ORE, Items.LAPIS_LAZULI);
        addProspectingLoot("nether_gold_ore", Blocks.NETHER_GOLD_ORE, Items.GOLD_NUGGET);
        addProspectingLoot("nether_quartz_ore", Blocks.NETHER_QUARTZ_ORE, Items.QUARTZ);
        addProspectingLoot("redstone_ore", Blocks.REDSTONE_ORE, Items.REDSTONE);

        addSkinningLoot("chicken", EntityType.CHICKEN, Items.FEATHER);
        addSkinningLoot("cow", EntityType.COW, Items.LEATHER);
        addSkinningLoot("donkey", EntityType.DONKEY, Items.LEATHER);
        addSkinningLoot("hoglin", EntityType.HOGLIN, Items.LEATHER);
        addSkinningLoot("horse", EntityType.HORSE, Items.LEATHER);
        addSkinningLoot("llama", EntityType.LLAMA, Items.LEATHER);
        addSkinningLoot("mule", EntityType.MULE, Items.LEATHER);
        addSkinningLoot("rabbit", EntityType.RABBIT, Items.RABBIT_HIDE);
        addSkinningLoot("sheep", EntityType.SHEEP, Items.WHITE_WOOL);
        addSkinningLoot("trader_llama", EntityType.TRADER_LLAMA, Items.LEATHER);

        addCommonLoot("arrow_dirt", Blocks.DIRT, Items.ARROW, 1);
        addCommonLoot("stick_dirt", Blocks.DIRT, Items.STICK, 2);
        addCommonLoot("clay_dirt", Blocks.DIRT, Items.CLAY_BALL, 3);

        addUncommonLoot("iron_nugget_sand", Blocks.SAND, Items.IRON_NUGGET, 3);
        addUncommonLoot("iron_nugget_dirt", Blocks.DIRT, Items.IRON_NUGGET, 3);
        addUncommonLoot("glass_bottle_dirt", Blocks.DIRT, Items.GLASS_BOTTLE, 2);
        addUncommonLoot("wooden_pickaxe_dirt", Blocks.DIRT, Items.WOODEN_PICKAXE, 1);

        addRareLoot("trident_sand", Blocks.SAND, Items.TRIDENT, 1);
        addRareLoot("diamond_dirt", Blocks.DIRT, Items.DIAMOND, 2);
        addRareLoot("gold_nugget_dirt", Blocks.DIRT, Items.GOLD_NUGGET, 3);
    }

    public void addHarvestEfficiencyLoot(String pName, Block pBlock, Item pItem, Property<Integer> pIntegerProperty, int pPropertyValue) {
        this.add(SkillRegistry.HARVEST_PROFICIENCY.getName() + "_" + pName, LootModifierSerializers.BLOCK_LOOT.get(), new BlockLootModifier(new ILootCondition[]{BlockStateProperty.builder(pBlock).fromProperties(StatePropertiesPredicate.Builder.newBuilder().withIntProp(pIntegerProperty, pPropertyValue)).build()}, pItem, SkillRegistry.HARVEST_PROFICIENCY));
    }

    public void addProspectingLoot(String pName, Block pBlock, Item pItem) {
        this.add(SkillRegistry.PROSPECTING.getName() + "_" + pName, LootModifierSerializers.BLOCK_LOOT.get(), new BlockLootModifier(new ILootCondition[]{LootTableIdCondition.builder(pBlock.getLootTable()).build()}, pItem, SkillRegistry.PROSPECTING));
    }

    public void addSkinningLoot(String pName, EntityType<?> pEntityType, Item pItem) {
        add(SkillRegistry.SKINNING.getName() + "_" + pName, LootModifierSerializers.ENTITY_LOOT.get(), new EntityLootModifier(new ILootCondition[]{LootTableIdCondition.builder(pEntityType.getLootTable()).build()}, pItem, SkillRegistry.SKINNING));
    }

    public void addMeatGatheringLoot(String pName, EntityType<?> pEntityType, Item pItem) {
        add(SkillRegistry.MEAT_GATHERING.getName() + "_" + pName, LootModifierSerializers.ENTITY_LOOT.get(), new EntityLootModifier(new ILootCondition[]{LootTableIdCondition.builder(pEntityType.getLootTable()).build()}, pItem, SkillRegistry.MEAT_GATHERING));
    }

    public void addCommonLoot(String pName, Block pBlock, Item pItem, int pMaxCount) {
        addTreasureLoot(pName, "common", pBlock, pItem, pMaxCount, 0.001F);
    }

    public void addUncommonLoot(String pName, Block pBlock, Item pItem, int pMaxCount) {
        addTreasureLoot(pName, "uncommon", pBlock, pItem, pMaxCount, 0.0004F);
    }

    public void addRareLoot(String pName, Block pBlock, Item pItem, int pMaxCount) {
        addTreasureLoot(pName, "rare", pBlock, pItem, pMaxCount, 0.0002F);
    }

    private void addTreasureLoot(String pName, String pType, Block pBlock, Item pItem, int pMaxCount, float pChancePerLevel) {
        this.add(SkillRegistry.TREASURE_HUNTING.getName() + "_" + pType + "_" + pName, LootModifierSerializers.TREASURE_LOOT.get(), new TreasureLootModifier(new ILootCondition[]{LootTableIdCondition.builder(pBlock.getLootTable()).build()}, pItem, pMaxCount, SkillRegistry.TREASURE_HUNTING, pChancePerLevel));
    }
}

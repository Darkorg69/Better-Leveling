package darkorg.betterleveling.network.chat;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.util.text.TranslationTextComponent;

public class ModComponents {
    public static final TranslationTextComponent EMPTY = new TranslationTextComponent("");

    public static final TranslationTextComponent ADDITIONAL_INFO = translatable("gui.additional_info");
    public static final TranslationTextComponent AVAILABLE = translatable("gui.available");
    public static final TranslationTextComponent BONUS = translatable("gui.current_bonus");
    public static final TranslationTextComponent BULLET = translatable("gui.bullet");
    public static final TranslationTextComponent CANNOT_DECREASE = translatable("gui.cannot_decrease");
    public static final TranslationTextComponent CANNOT_INCREASE = translatable("gui.cannot_increase");
    public static final TranslationTextComponent CAPABILITY_NOT_FOUND = translatable(".command.failure.capability");
    public static final TranslationTextComponent CHOOSE_CONFIRM = translatable("gui.choose.confirm");
    public static final TranslationTextComponent CANNOT_UNLOCK = translatable("gui.choose.cannot_unlock");
    public static final TranslationTextComponent CHOOSE_SPEC_TITLE = translatable("gui.choose_spec_title");
    public static final TranslationTextComponent CONFIRM_DECREASE = translatable("gui.decrease.confirm");
    public static final TranslationTextComponent CONFIRM_INCREASE = translatable("gui.increase.confirm");
    public static final TranslationTextComponent CONFIRM_UNLOCK = translatable("gui.unlock.confirm");
    public static final TranslationTextComponent COST = translatable("gui.cost");
    public static final TranslationTextComponent CURRENT = translatable("gui.current_cost");
    public static final TranslationTextComponent DECREASE = translatable("gui.decrease");
    public static final TranslationTextComponent HOLD_SHIFT = translatable("gui.hold_shift");
    public static final TranslationTextComponent INCREASE = translatable("gui.increase");
    public static final TranslationTextComponent INVALID_LEVEL = translatable(".invalid_level");
    public static final TranslationTextComponent LEVEL = translatable("gui.level");
    public static final TranslationTextComponent LEVELS = translatable("gui.levels");
    public static final TranslationTextComponent LOCKED = translatable("gui.locked");
    public static final TranslationTextComponent MAX_LEVEL = translatable("gui.max_level");
    public static final TranslationTextComponent NOT_ENOUGH_XP = translatable("gui.not_enough_xp");
    public static final TranslationTextComponent NOT_OWNED = translatable("gui.not_owned");
    public static final TranslationTextComponent NO_ACCESS = translatable("gui.cannot_access");
    public static final TranslationTextComponent PER_LEVEL = translatable("gui.per_level");
    public static final TranslationTextComponent PREREQUISITES = translatable("gui.prerequisites");
    public static final TranslationTextComponent REGISTER = translatable("gui.register");
    public static final TranslationTextComponent SELECT_BUTTON = translatable("gui.select");
    public static final TranslationTextComponent SKILL_NOT_FOUND = translatable(".command.failure.skill");
    public static final TranslationTextComponent SPEC = translatable("gui.spec");
    public static final TranslationTextComponent SPEC_IS_LOCKED = translatable("gui.spec_is_locked");
    public static final TranslationTextComponent SPEC_NOT_FOUND = translatable(".command.failure.spec");
    public static final TranslationTextComponent UNLOCK = translatable("gui.unlock");
    public static final TranslationTextComponent UNLOCK_COST = translatable("gui.unlock_cost");
    public static final TranslationTextComponent UNREGISTER = translatable("gui.unregister");
    public static final TranslationTextComponent XP = translatable("gui.xp");

    private static TranslationTextComponent translatable(String pKey) {
        return new TranslationTextComponent(BetterLeveling.MOD_ID + "." + pKey);
    }
}

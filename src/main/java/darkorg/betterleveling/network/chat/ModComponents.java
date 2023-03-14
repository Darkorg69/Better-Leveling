package darkorg.betterleveling.network.chat;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.network.chat.TranslatableComponent;

public class ModComponents {
    public static final TranslatableComponent EMPTY = new TranslatableComponent("");

    public static final TranslatableComponent ADDITIONAL_INFO = translatable("gui.additional_info");
    public static final TranslatableComponent AVAILABLE = translatable("gui.available");
    public static final TranslatableComponent BONUS = translatable("gui.current_bonus");
    public static final TranslatableComponent BULLET = translatable("gui.bullet");
    public static final TranslatableComponent CANNOT_DECREASE = translatable("gui.cannot_decrease");
    public static final TranslatableComponent CANNOT_INCREASE = translatable("gui.cannot_increase");
    public static final TranslatableComponent CAPABILITY_NOT_FOUND = translatable(".command.failure.capability");
    public static final TranslatableComponent CHOOSE_CONFIRM = translatable("gui.choose.confirm");
    public static final TranslatableComponent CANNOT_UNLOCK = translatable("gui.choose.cannot_unlock");
    public static final TranslatableComponent CHOOSE_SPEC_TITLE = translatable("gui.choose_spec_title");
    public static final TranslatableComponent CONFIRM_DECREASE = translatable("gui.decrease.confirm");
    public static final TranslatableComponent CONFIRM_INCREASE = translatable("gui.increase.confirm");
    public static final TranslatableComponent CONFIRM_UNLOCK = translatable("gui.unlock.confirm");
    public static final TranslatableComponent COST = translatable("gui.cost");
    public static final TranslatableComponent CURRENT = translatable("gui.current_cost");
    public static final TranslatableComponent DECREASE = translatable("gui.decrease");
    public static final TranslatableComponent HOLD_SHIFT = translatable("gui.hold_shift");
    public static final TranslatableComponent INCREASE = translatable("gui.increase");
    public static final TranslatableComponent INVALID_LEVEL = translatable(".invalid_level");
    public static final TranslatableComponent LEVEL = translatable("gui.level");
    public static final TranslatableComponent LEVELS = translatable("gui.levels");
    public static final TranslatableComponent LOCKED = translatable("gui.locked");
    public static final TranslatableComponent MAX_LEVEL = translatable("gui.max_level");
    public static final TranslatableComponent NOT_ENOUGH_XP = translatable("gui.not_enough_xp");
    public static final TranslatableComponent NOT_OWNED = translatable("gui.not_owned");
    public static final TranslatableComponent NO_ACCESS = translatable("gui.cannot_access");
    public static final TranslatableComponent PER_LEVEL = translatable("gui.per_level");
    public static final TranslatableComponent PREREQUISITES = translatable("gui.prerequisites");
    public static final TranslatableComponent REGISTER = translatable("gui.register");
    public static final TranslatableComponent SELECT_BUTTON = translatable("gui.select");
    public static final TranslatableComponent SKILL_NOT_FOUND = translatable(".command.failure.skill");
    public static final TranslatableComponent SPEC = translatable("gui.spec");
    public static final TranslatableComponent SPEC_IS_LOCKED = translatable("gui.spec_is_locked");
    public static final TranslatableComponent SPEC_NOT_FOUND = translatable(".command.failure.spec");
    public static final TranslatableComponent UNLOCK = translatable("gui.unlock");
    public static final TranslatableComponent UNLOCK_COST = translatable("gui.unlock_cost");
    public static final TranslatableComponent UNREGISTER = translatable("gui.unregister");
    public static final TranslatableComponent XP = translatable("gui.xp");


    private static TranslatableComponent translatable(String pKey) {
        return new TranslatableComponent(BetterLeveling.MOD_ID + "." + pKey);
    }
}

package darkorg.betterleveling.network.chat;

import darkorg.betterleveling.BetterLeveling;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ModComponents {
    public static final MutableComponent ADDITIONAL_INFO = translatable("gui.additional_info");
    public static final MutableComponent AVAILABLE = translatable("gui.available");
    public static final MutableComponent BONUS = translatable("gui.current_bonus");
    public static final MutableComponent BULLET = translatable("gui.bullet");
    public static final MutableComponent CANNOT_DECREASE = translatable("gui.cannot_decrease");
    public static final MutableComponent CANNOT_INCREASE = translatable("gui.cannot_increase");
    public static final MutableComponent CAPABILITY_NOT_FOUND = translatable(".command.failure.capability");
    public static final MutableComponent CHOOSE_CONFIRM = translatable("gui.choose.confirm");
    public static final MutableComponent CANNOT_UNLOCK = translatable("gui.choose.cannot_unlock");
    public static final MutableComponent CHOOSE_SPEC_TITLE = translatable("gui.choose_spec_title");
    public static final MutableComponent CONFIRM_DECREASE = translatable("gui.decrease.confirm");
    public static final MutableComponent CONFIRM_INCREASE = translatable("gui.increase.confirm");
    public static final MutableComponent CONFIRM_UNLOCK = translatable("gui.unlock.confirm");
    public static final MutableComponent COST = translatable("gui.cost");
    public static final MutableComponent CURRENT = translatable("gui.current_cost");
    public static final MutableComponent DECREASE = translatable("gui.decrease");
    public static final MutableComponent HOLD_SHIFT = translatable("gui.hold_shift");
    public static final MutableComponent INCREASE = translatable("gui.increase");
    public static final MutableComponent INVALID_LEVEL = translatable(".invalid_level");
    public static final MutableComponent LEVEL = translatable("gui.level");
    public static final MutableComponent LEVELS = translatable("gui.levels");
    public static final MutableComponent LOCKED = translatable("gui.locked");
    public static final MutableComponent MAX_LEVEL = translatable("gui.max_level");
    public static final MutableComponent NOT_ENOUGH_XP = translatable("gui.not_enough_xp");
    public static final MutableComponent NOT_OWNED = translatable("gui.not_owned");
    public static final MutableComponent NO_ACCESS = translatable("gui.cannot_access");
    public static final MutableComponent PER_LEVEL = translatable("gui.per_level");
    public static final MutableComponent PREREQUISITES = translatable("gui.prerequisites");
    public static final MutableComponent REGISTER = translatable("gui.register");
    public static final MutableComponent SELECT_BUTTON = translatable("gui.select");
    public static final MutableComponent SKILL_NOT_FOUND = translatable(".command.failure.skill");
    public static final MutableComponent SPEC = translatable("gui.spec");
    public static final MutableComponent SPEC_IS_LOCKED = translatable("gui.spec_is_locked");
    public static final MutableComponent SPEC_NOT_FOUND = translatable(".command.failure.spec");
    public static final MutableComponent UNLOCK = translatable("gui.unlock");
    public static final MutableComponent UNLOCK_COST = translatable("gui.unlock_cost");
    public static final MutableComponent UNREGISTER = translatable("gui.unregister");
    public static final MutableComponent XP = translatable("gui.xp");

    public static MutableComponent translatable(String pKey) {
        return Component.translatable(BetterLeveling.MOD_ID + "." + pKey);
    }
}

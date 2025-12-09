package water_in_nether.mixin;

import net.minecraft.block.IceBlock;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IceBlock.class)
public class IceBlockMixin_1_19 {
    @Redirect(method = "afterBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/DimensionType;ultrawarm()Z"))
    private boolean func1(DimensionType instance) {
        return false;
    }

    @Redirect(method = "melt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/DimensionType;ultrawarm()Z"))
    private boolean func2(DimensionType instance) {
        return false;
    }
}

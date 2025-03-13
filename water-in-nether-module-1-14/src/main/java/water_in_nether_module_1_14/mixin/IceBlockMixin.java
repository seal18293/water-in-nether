package water_in_nether_module_1_14.mixin;

import net.minecraft.block.IceBlock;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IceBlock.class)
public class IceBlockMixin {
    @Redirect(method = "afterBreak", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;doesWaterVaporize()Z"))
    private boolean func1(Dimension instance) {
        return false;
    }

    @Redirect(method = "melt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;doesWaterVaporize()Z"))
    private boolean func2(Dimension instance) {
        return false;
    }
}

package water_in_nether_module_1_14.mixin;

import net.minecraft.item.BucketItem;
import net.minecraft.world.dimension.Dimension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BucketItem.class)
public class BucketItemMixin {
    @Redirect(method = "placeFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/Dimension;doesWaterVaporize()Z"))
    private boolean func(Dimension instance) {
        return false;
    }
}
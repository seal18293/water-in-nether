package water_in_nether.mixin;

import net.minecraft.item.BucketItem;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BucketItem.class)
public class BucketItemMixin_1_19 {
    @Redirect(method = "placeFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/dimension/DimensionType;ultrawarm()Z"))
    private boolean func(DimensionType instance) {
        return false;
    }
}
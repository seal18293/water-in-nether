package water_in_nether;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.Version;
import net.fabricmc.loader.api.VersionParsingException;
import net.fabricmc.loader.api.metadata.version.VersionPredicate;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.*;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    Map<String, String> map;

    @Override
    public void onLoad(String mixinPackage) {
        map = new HashMap<>();
        map.put("BucketItemMixin_1_14_4", ">=1.14.4 <1.16");
        map.put("BucketItemMixin_1_16", ">=1.16 <1.19");
        map.put("BucketItemMixin_1_19", ">=1.19 <1.21.5");
        map.put("BucketItemMixin_1_21_5", ">=1.21.5");
        map.put("IceBlockMixin_1_14_4", ">=1.14.4 <1.16");
        map.put("IceBlockMixin_1_16", ">=1.16 <1.19");
        map.put("IceBlockMixin_1_19", ">=1.19");
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String mixin = mixinClassName.substring(mixinClassName.lastIndexOf(".") + 1);

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        Version ver = FabricLoader.getInstance().getModContainer("minecraft").get().getMetadata().getVersion();

        String predicate = map.get(mixin);
        if (predicate != null) {
            try {
                return VersionPredicate.parse(predicate).test(ver);
            } catch (VersionParsingException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return Collections.emptyList();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}

package org.nekobucket.nekotools.item;

import net.minecraft.item.Item;
import org.nekobucket.nekotools.mod.NekoObject;
import org.nekobucket.nekotools.mod.tab.NekoToolsTab$;
import scala.reflect.ClassTag;
import space.controlnet.lightioc.annotation.Singleton;

@Singleton
public class TestJavaItem extends Item {
    public TestJavaItem() {
        super(new Properties().tab(NekoToolsTab$.MODULE$).stacksTo(16));
    }

    public static class Factory extends NekoObject<TestJavaItem> {

        public Factory(ClassTag<TestJavaItem> classTag) {
            super(classTag);
        }

        @Override
        public String ID() {
            return  "test_java_item";
        }
    }
}

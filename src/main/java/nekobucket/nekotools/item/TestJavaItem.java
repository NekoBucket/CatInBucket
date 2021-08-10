package nekobucket.nekotools.item;

import nekobucket.nekotools.mod.NekoObject;
import nekobucket.nekotools.mod.tab.NekoToolsTab$;
import net.minecraft.item.Item;
import scala.reflect.ClassTag;

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

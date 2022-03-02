package gamma02.neucleargenerators.registration;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class RegisteryHelper {
    public static void register(Class<?> toRegister, Registry<?> registry){
        Registerer registerer = toRegister.getAnnotation(Registerer.class);
        if(registerer == null){
            return;
        }
        Class<?> element = registerer.element();
        Arrays.stream(toRegister.getFields()).filter(
                field -> field.isAnnotationPresent(RegistryEntry.class)
                        && Modifier.isPublic(field.getModifiers())
                        && Modifier.isStatic(field.getModifiers())
                        && Modifier.isFinal(field.getModifiers())
                        && element.isAssignableFrom(field.getType())
        ).forEach(field -> {
            try {
                Object value = field.get(null);
                Registry.register((Registry) registry, new Identifier(registerer.modid(), field.getAnnotation(RegistryEntry.class).value()), element.cast(value));
                if(value instanceof BlockItem b){
                    Item.BLOCK_ITEMS.put(b.getBlock(), b);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}

package app.mathnek.talesofvarmithore.util;

import app.mathnek.talesofvarmithore.TalesofVarmithore;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public class ToVKeybinds {
    public static KeyMapping BITE;

    public static void init() {
        BITE = registerKey("bite", "key.category." + TalesofVarmithore.MOD_ID, InputConstants.KEY_R);
    }

    private static KeyMapping registerKey(String name, String category, int keycode) {
        KeyMapping key = new KeyMapping("key." + TalesofVarmithore.MOD_ID + "." + name, keycode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}

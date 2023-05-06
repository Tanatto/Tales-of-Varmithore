package app.mathnek.talesofvarmithore.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ControlNetwork {
   public static final String NETWORK_VERSION = "0.1.0";
   public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("tov", "control"), () -> {
      return "0.1.0";
   }, "0.1.0"::equals, "0.1.0"::equals);

   public static void init() {
      INSTANCE.registerMessage(2, ControlMessageBite.class, ControlMessageBite::encode, ControlMessageBite::decode, ControlMessageBite::handle);
   }
}

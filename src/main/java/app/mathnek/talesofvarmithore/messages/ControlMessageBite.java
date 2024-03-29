package app.mathnek.talesofvarmithore.messages;

import app.mathnek.talesofvarmithore.entity.twintail.TwinTailEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageBite {
    boolean abilityHeld;
    int dragonId;
    boolean canCauseBiteDamage;
    int rockDrake;

    public ControlMessageBite() {
    }

    public ControlMessageBite(boolean abilityHeld, int dragonId) {
        this.abilityHeld = abilityHeld;
        this.dragonId = dragonId;
    }

    public static void encode(ControlMessageBite message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.abilityHeld);
        buffer.writeInt(message.dragonId);
    }

    public static ControlMessageBite decode(FriendlyByteBuf buffer) {
        return new ControlMessageBite(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageBite message, Supplier contextSupplier) {
        NetworkEvent.Context context = (NetworkEvent.Context) contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Entity entity = player.level.getEntity(message.dragonId);
                if (entity instanceof TwinTailEntity) {
                    TwinTailEntity dragon = (TwinTailEntity) entity;
                    if (player.getVehicle() == dragon && dragon.getOwner() == player) {
                        dragon.setIsBiting(message.abilityHeld);
                    }
                    if (player.getVehicle() instanceof TwinTailEntity rockDrake && rockDrake.getOwner() == player) {
                        rockDrake.setIsBitingDamageTrue(message.canCauseBiteDamage);
                    }
                }
            }

        });
        context.setPacketHandled(true);
    }
}

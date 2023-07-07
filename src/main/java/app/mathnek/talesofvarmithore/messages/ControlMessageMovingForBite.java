package app.mathnek.talesofvarmithore.messages;

import app.mathnek.talesofvarmithore.entity.rockdrake.RockDrakeEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ControlMessageMovingForBite {

    boolean canCauseBiteDamage;
    int rockDrake;

    public ControlMessageMovingForBite() {

    }

    public ControlMessageMovingForBite(boolean canCauseBiteDamage, int dragonId) {
        this.canCauseBiteDamage = canCauseBiteDamage;
        this.rockDrake = dragonId;
    }

    public static void encode(ControlMessageMovingForBite message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.canCauseBiteDamage);
        buffer.writeInt(message.rockDrake);
    }

    public static ControlMessageMovingForBite decode(FriendlyByteBuf buffer) {
        return new ControlMessageMovingForBite(buffer.readBoolean(), buffer.readInt());
    }

    public static void handle(ControlMessageMovingForBite message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                if (player.getVehicle() instanceof RockDrakeEntity rockDrake && rockDrake.getOwner() == player) {
                    rockDrake.setIsBitingDamageTrue(message.canCauseBiteDamage);
                }
            }
        });
        context.setPacketHandled(true);

    }
}

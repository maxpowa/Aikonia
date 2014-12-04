package maxpowa.aikonia.common.packet;

import maxpowa.aikonia.Aikonia;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MagikaBubbleSoundPacket implements IMessage {

	private String snd;
	
	public MagikaBubbleSoundPacket() { }
	
    public MagikaBubbleSoundPacket(String snd) {
    	this.snd = snd;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        snd = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, snd);
    }

    public static class Handler implements IMessageHandler<MagikaBubbleSoundPacket, IMessage> {
       
        @Override
        public IMessage onMessage(MagikaBubbleSoundPacket message, MessageContext ctx) {
            Aikonia.proxy.playSound(message.snd);
			return null;
        }
    }
}
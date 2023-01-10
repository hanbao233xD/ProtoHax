package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.BedrockPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.impl.EventPacketOutbound
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.cos
import kotlin.math.sin

class ModuleBlink : CheatModule("Blink") {
    private val packetList = LinkedBlockingQueue<BedrockPacket>()
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        packetList.add(event.packet)
    }

    override fun onDisable() {
        for (bedrockPacket in packetList) {
            session.netSession.outboundPacket(bedrockPacket)
        }
    }
}
package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketInbound

class ModuleNoResetRotation : CheatModule("NoRotation","不重置转头") {
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if (event.packet is MovePlayerPacket) {
            if(event.packet.runtimeEntityId==mc.thePlayer.entityId)
                event.packet.rotation=mc.thePlayer.vec3Rotation
        }
    }
}
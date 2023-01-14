package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketInbound

class ModuleNoResetRotation : CheatModule("NoRotation") {
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if (event.packet is MovePlayerPacket) {
            event.packet.rotation=mc.thePlayer.vec3Rotation
        }
    }
}
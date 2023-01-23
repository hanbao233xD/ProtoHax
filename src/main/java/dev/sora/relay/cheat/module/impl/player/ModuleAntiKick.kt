package dev.sora.relay.cheat.module.impl.player

import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket
import dev.sora.relay.game.event.EventPacketInbound

class ModuleAntiKick : CheatModule("AntiKick","防踢出") {

    @Listen
    fun onPacketOutbound(event: EventPacketInbound) {
        if (event.packet is DisconnectPacket) {
            chat("disconnect: ${event.packet.kickMessage}")
            event.cancel()
        }
    }
}
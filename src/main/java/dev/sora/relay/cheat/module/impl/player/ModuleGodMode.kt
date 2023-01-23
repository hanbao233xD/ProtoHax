package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.entity.EntityPlayerSP
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen

class ModuleGodMode : CheatModule("GodMode","锁血") {
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        val packet = event.packet
        if (packet is PlayerAuthInputPacket){
            session.thePlayer.attackEntity(mc.thePlayer, event.session, EntityPlayerSP.SwingMode.NONE)
        }
    }
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (packet is AnimatePacket) {
            if (packet.action == AnimatePacket.Action.CRITICAL_HIT && packet.runtimeEntityId==mc.thePlayer.entityId) {
                if(mc.thePlayer.tickExists%5==0L){
                    event.cancel()
                }
            }
        }
    }
}
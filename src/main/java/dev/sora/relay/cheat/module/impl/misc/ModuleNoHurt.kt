package dev.sora.relay.cheat.module.impl.misc

import com.nukkitx.protocol.bedrock.data.entity.EntityEventType
import com.nukkitx.protocol.bedrock.packet.AnimatePacket
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.BoolValue
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.Listen

class ModuleNoHurt : CheatModule("NoHurt","屏蔽受伤") {

    private val messageValue = BoolValue("Message", true)
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (event.packet is EntityEventPacket) {
            if (event.packet.type== EntityEventType.HURT && event.packet.runtimeEntityId==mc.thePlayer.entityId){

                if(messageValue.get())chat("Hurt!")
            }
        }
    }
}
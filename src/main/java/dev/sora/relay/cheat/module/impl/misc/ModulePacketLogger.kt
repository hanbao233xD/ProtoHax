package dev.sora.relay.cheat.module.impl.misc
import com.nukkitx.protocol.bedrock.packet.*
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.impl.EventPacketInbound
import dev.sora.relay.game.event.impl.EventPacketOutbound

class ModulePacketLogger : CheatModule("PacketLogger") {
    private val modeValue = ListValue("Mode", arrayOf("In","Out", "Both"), "Both")
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if(modeValue.get()=="In" || modeValue.get()=="Both") {
            val packet = event.packet
            if (packet is AvailableCommandsPacket) return
            if (packet is MobEquipmentPacket) {
                session.thePlayer.currentItem = packet.item
            }
            if (packet is UpdateBlockPacket) {
                return
            }
            //chat(event.packet.toString())
        }
    }

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if(modeValue.get()=="Out" || modeValue.get()=="Both") {
            val packet = event.packet
            if (packet is PlayerAuthInputPacket) {
                if(packet.tick%10!=0L){
                    chat(event.packet.toString())
                    return
                }
            }
            if (packet is MovePlayerPacket) {
                return
            }
            //chat(event.packet.toString())
        }

    }
}
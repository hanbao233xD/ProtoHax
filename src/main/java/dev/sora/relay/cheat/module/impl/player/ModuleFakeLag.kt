package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.protocol.bedrock.BedrockPacket
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.BoolValue
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.utils.TimerUtil
import java.util.concurrent.LinkedBlockingQueue

class ModuleFakeLag :CheatModule("FakeLag","伪装闪现") {
    private val packetList = LinkedBlockingQueue<BedrockPacket>()
    private val speedValue = FloatValue("timer" ,500F,0f,3000f)
    private val onlyMoveValue = BoolValue("OnlyMove", true)
    private val timer=TimerUtil()
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if(onlyMoveValue.get() && event.packet is MovePlayerPacket)
            packetList.add(event.packet)
        else packetList.add(event.packet)
        if(timer.delay(speedValue.get())){
            for (bedrockPacket in packetList) {
                session.netSession.outboundPacket(bedrockPacket)
            }
            packetList.clear()
            timer.reset()
        }
    }

    override fun onDisable() {
        for (bedrockPacket in packetList) {
            session.netSession.outboundPacket(bedrockPacket)
        }
        packetList.clear()
    }
}
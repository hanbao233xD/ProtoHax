package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.BedrockPacket
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.BoolValue
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.utils.TimerUtil
import java.util.concurrent.LinkedBlockingQueue
import kotlin.math.cos
import kotlin.math.sin

class ModuleBlink  : CheatModule("Blink") {
    private val pulseValue = BoolValue("Pulse", false)
    private val pulseDelayValue = FloatValue("Pulse Delay", 500f, 500f, 5000f)

    private val packetList = mutableListOf<BedrockPacket>()

    private val pulseTimer = TimerUtil()

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound){
        packetList.add(event.packet)
        event.cancel()
        if(pulseValue.get() && pulseTimer.isDelayComplete(pulseDelayValue.get().toLong())){
            for(packet in packetList){
                session.netSession.outboundPacket(packet)
            }
            packetList.clear()
            pulseTimer.reset()
        }
    }

    override fun onDisable(){
        for(packet in packetList){
            session.netSession.outboundPacket(packet)
        }
        packetList.clear()
    }
}
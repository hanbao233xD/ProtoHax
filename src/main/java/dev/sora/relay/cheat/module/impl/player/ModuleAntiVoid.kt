package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.utils.TimerUtil

class ModuleAntiVoid : CheatModule("AntiVoid"){
    private var pos:Vector3f?=null
    private val timer= TimerUtil()
    override fun onEnable() {
        pos=null
        super.onEnable()
    }

    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if(event.packet is PlayerAuthInputPacket){
            if(mc.thePlayer.onGround) {
                pos = Vector3f.from(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)
            }
            if(mc.thePlayer.motionY<0){
                if(timer.delay(500F)){
                    session.netSession.inboundPacket(SetEntityMotionPacket().apply {
                        runtimeEntityId = mc.thePlayer.entityId
                        motion = Vector3f.from(-1.0,0.38, -1.0)
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = mc.thePlayer.entityId
                        position = pos
                        rotation = mc.thePlayer.vec3Rotation
                        mode = MovePlayerPacket.Mode.NORMAL
                        isOnGround = true
                        ridingRuntimeEntityId = 0
                        entityType = 0
                        tick = event.packet.tick
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = mc.thePlayer.entityId
                        position = pos
                        rotation = mc.thePlayer.vec3Rotation
                        mode = MovePlayerPacket.Mode.NORMAL
                        isOnGround = true
                        ridingRuntimeEntityId = 0
                        entityType = 0
                        tick = event.packet.tick
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = mc.thePlayer.entityId
                        position = pos
                        rotation = mc.thePlayer.vec3Rotation
                        mode = MovePlayerPacket.Mode.NORMAL
                        isOnGround = true
                        ridingRuntimeEntityId = 0
                        entityType = 0
                        tick = event.packet.tick
                    })
                    chat("AntiVoid LagBack!")
                    timer.reset()
                }
            }else {
                timer.reset()
            }
        }
    }
}
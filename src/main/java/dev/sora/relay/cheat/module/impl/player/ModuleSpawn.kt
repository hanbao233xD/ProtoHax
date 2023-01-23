package dev.sora.relay.cheat.module.impl.player

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.RespawnPacket
import com.nukkitx.protocol.bedrock.packet.TextPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.TimerUtil

class ModuleSpawn : CheatModule("Spawn","原地复活") {
    private val modeValue = ListValue("Mode", arrayOf("Text", "RespawnPacket"), "Text")
    private val timeValue = FloatValue("Time" ,6000F,0f,10000f)
    private val timer = TimerUtil()
    private val onGround = TimerUtil()
    private var tp=false
    private lateinit var pos : Vector3f
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if(event.packet is PlayerAuthInputPacket){
            if(!tp) {
                if (mc.thePlayer.motionY != 0.0) {
                    onGround.reset()
                }
                if (onGround.delay(500f)) {
                    onGround.reset()
                    pos = mc.thePlayer.vec3Position
                }
            }
        }
    }
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        when (modeValue.get()){
            "Text"->{
                if(timer.delay(timeValue.get()) && tp){
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    session.netSession.outboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    session.netSession.outboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    tp=false
                }
                if (event.packet is TextPacket) {
                    if(event.packet.message.contains("请等待复活")) {
                        tp=true
                        timer.reset()
                    }
                }
            }
            "RespawnPacket"->{
                if (event.packet is RespawnPacket) {
                    chat("Spawn")
                    val pos=mc.thePlayer.vec3Position
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = event.packet.position
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                    session.netSession.inboundPacket(MovePlayerPacket().apply {
                        runtimeEntityId = session.thePlayer.entityId
                        position = pos
                        rotation = session.thePlayer.vec3Rotation
                        isOnGround = false
                        mode = MovePlayerPacket.Mode.NORMAL
                    })
                }
            }
        }
    }
}
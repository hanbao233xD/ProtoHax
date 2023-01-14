package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.utils.movement.MovementUtils
import kotlin.math.cos
import kotlin.math.sin

class ModuleTPFly : CheatModule("TPFly"){
    private val floatYValue = FloatValue("FloatY", 0.38f, 0f, 2f)
    private val tpHighValue = FloatValue("TPHigh", 20f, 0f, 50f)
    override fun onEnable() {
        super.onEnable()
        var pos= Vector3f.from(mc.thePlayer.vec3Position.x, mc.thePlayer.vec3Position.y+tpHighValue.get(), mc.thePlayer.vec3Position.z)
        session.netSession.inboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
        session.netSession.inboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
        session.netSession.inboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
    }
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {
        if (event.packet is PlayerAuthInputPacket) strafe(
            0.38f,
            -floatYValue.get()
        )
    }
    private fun strafe(speed: Float, motionY: Float) {
        val yaw = direction
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(-sin(yaw) * speed, motionY.toDouble(), cos(yaw) * speed)
        })
    }
}
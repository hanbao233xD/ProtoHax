package dev.sora.relay.cheat.module.impl.movement

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.Ability
import com.nukkitx.protocol.bedrock.data.PlayerAuthInputData
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import com.nukkitx.protocol.bedrock.packet.PlayerAuthInputPacket
import com.nukkitx.protocol.bedrock.packet.RequestAbilityPacket
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.event.EventPacketOutbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.utils.movement.MovementUtils
import kotlin.math.cos
import kotlin.math.sin

class ModuleTP : CheatModule("Teleport","传送") {
    private val distanceValue = FloatValue("Distance", 20f, 0f, 50f)
    private val tpHighValue = FloatValue("YHigh", 0f, -10f, 20f)
    override fun onEnable() {
        super.onEnable()
        val yaw = direction
        var pos = Vector3f.from(
            mc.thePlayer.posX+(-kotlin.math.sin(yaw) * distanceValue.get()),
            mc.thePlayer.posY + tpHighValue.get(),
            mc.thePlayer.posZ+(kotlin.math.cos(yaw) * distanceValue.get())
        )
        session.netSession.outboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists
        })
        session.netSession.outboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists + 2
        })
        session.netSession.outboundPacket(MovePlayerPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            position = pos
            rotation = mc.thePlayer.vec3Rotation
            mode = MovePlayerPacket.Mode.NORMAL
            isOnGround = true
            ridingRuntimeEntityId = 0
            entityType = 0
            tick = mc.thePlayer.tickExists + 1
        })
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(
                0.0,
                0.32,
                0.0
            )
        })

    }
    @Listen
    fun onPacketOutbound(event: EventPacketOutbound) {

    }
}
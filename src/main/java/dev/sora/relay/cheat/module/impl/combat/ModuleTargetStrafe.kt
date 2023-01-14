package dev.sora.relay.cheat.module.impl.combat

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.packet.SetEntityMotionPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.utils.math.MathUtils.rand
import dev.sora.relay.game.utils.movement.MovementUtils
import kotlin.math.cos
import kotlin.math.sin

class ModuleTargetStrafe : CheatModule("TargetStrafe") {
    private val radiusValue = FloatValue("Radius", 2.0f, 1.0f, 5.0f)

    @Listen
    fun onTick(event: EventTick) {
        val killAura = session.moduleManager.getModuleByName("KillAura") as ModuleKillAura
        if (!killAura.state || killAura.entityList.isEmpty() || killAura.entityList.size > 1) return
        val targetPos = killAura.entityList[0].vec3Position
        val radius = radiusValue.get()
        val strafeDirection =
            MovementUtils.calculateLookAtMovement(targetPos.add(rand(-radius, radius), 0.0, rand(-radius, radius)), mc)
                .normalize()
        val p = calculateMotion(strafeDirection.x, strafeDirection.z, .4849f)
        setMotion(p.first, p.second)
    }

    private fun setMotion(x: Double, y: Double) {
        session.netSession.inboundPacket(SetEntityMotionPacket().apply {
            runtimeEntityId = mc.thePlayer.entityId
            motion = Vector3f.from(x, 0.0, y)
        })
    }

    private fun calculateMotion(moveStrafing: Float, moveForward: Float, speed: Float): Pair<Double, Double> {
        return Pair(
            -sin(Math.toRadians(moveStrafing.toDouble())) * speed * moveForward,
            cos(Math.toRadians(moveStrafing.toDouble())) * speed * moveForward
        )
    }
}
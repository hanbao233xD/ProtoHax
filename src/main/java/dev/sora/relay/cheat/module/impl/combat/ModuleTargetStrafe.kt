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

class ModuleTargetStrafe : CheatModule("TargetStrafe","目标环绕") {
    private var oldMode=""
    private lateinit var killAura : ModuleKillAura
    override fun onDisable() {
        killAura.rotationModeValue.set(oldMode)
        super.onDisable()
    }

    override fun onEnable() {
        killAura = session.moduleManager.getModuleByName("KillAura") as ModuleKillAura
        oldMode=killAura.rotationModeValue.get()
        super.onEnable()
    }
    @Listen
    fun onTick(event: EventTick) {
        if(killAura.rotationModeValue.get()=="None"){
            killAura.rotationModeValue.set("Silent")
            chat("TargetStrafe need Rotation: Silent or Lock")
        }
    }
}
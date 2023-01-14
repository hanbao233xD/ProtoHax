package dev.sora.relay.cheat.module.impl.combat

import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.module.impl.combat.ModuleAntiBot.isBot
import dev.sora.relay.cheat.module.impl.player.ModuleTeams.isTeams
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.IntValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.entity.Entity
import dev.sora.relay.game.entity.EntityPlayer
import dev.sora.relay.game.entity.EntityPlayerSP
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.utils.TimerUtil
import kotlin.math.pow

class ModuleInfiniteAura : CheatModule("InfiniteAura") {
    private val attackModeValue = ListValue("AttackMode", arrayOf("Single", "Multi"), "Single")
    private val swingValue = ListValue("Swing", arrayOf("Both", "Client", "Server", "None"), "Both")
    private val cpsValue = IntValue("CPS", 3, 1, 20)
    private val rangeValue = FloatValue("Range", 10F, 10F, 128F)
    private val tpDistanceValue = FloatValue("TPDistance", 6F, 2F, 20F)

    private val attackTimer = TimerUtil()

    @Listen
    fun onTick(event: EventTick) {
        if (cpsValue.get() > 20 && !attackTimer.isDelayComplete(1000/cpsValue.get().toLong()))
            return

        val session = event.session

        val range = rangeValue.get().pow(2)
        val entityList = session.theWorld.entityMap.values.filter {
            it is EntityPlayer && it.distanceSq(session.thePlayer) < range
                    && !it.isBot(session)
                    && !it.isTeams(session)
        }

        if (entityList.isEmpty()) return

        val swingMode = when(swingValue.get()) {
            "Both" -> EntityPlayerSP.SwingMode.BOTH
            "Client" -> EntityPlayerSP.SwingMode.CLIENTSIDE
            "Server" -> EntityPlayerSP.SwingMode.SERVERSIDE
            else -> EntityPlayerSP.SwingMode.NONE
        }

        if(attackModeValue.get() == "Single"){
            (entityList.minByOrNull { it.distanceSq(event.session.thePlayer) } ?: return).also {
                Teleport(it,false)
                session.thePlayer.attackEntity(it, session,swingMode)
                Teleport(it,true)
            }
        }else if(attackModeValue.get() == "Multi"){
            entityList.forEach {
                Teleport(it,false)
                session.thePlayer.attackEntity(it,session,swingMode)
                Teleport(it,true)
            }
            entityList.first()
        }

        attackTimer.reset()
    }

    private fun Teleport(entity: Entity, isBack: Boolean){
        val entityPos = entity.vec3Position
        val playerPos = session.thePlayer.vec3Position

        val times = playerPos.distance(entityPos) / tpDistanceValue.get()

        for(i in 1 until times.toInt() + 1){
            if(isBack){
                val tpPos = playerPos.add(entityPos.sub(playerPos).div(times).mul(i.toFloat()))
                session.netSession.outboundPacket(MovePlayerPacket().apply {
                    runtimeEntityId = session.thePlayer.entityId
                    position = tpPos
                    rotation = session.thePlayer.vec3Rotation
                    isOnGround = false
                    mode = MovePlayerPacket.Mode.TELEPORT
                })
            }else{
                val tpPos = entityPos.add(playerPos.sub(entityPos).div(times).mul(i.toFloat()))
                session.netSession.outboundPacket(MovePlayerPacket().apply {
                    runtimeEntityId = session.thePlayer.entityId
                    position = tpPos
                    rotation = session.thePlayer.vec3Rotation
                    isOnGround = false
                    mode = MovePlayerPacket.Mode.TELEPORT
                })
            }
        }
    }


}
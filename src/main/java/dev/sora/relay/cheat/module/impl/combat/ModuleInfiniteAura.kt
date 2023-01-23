package dev.sora.relay.cheat.module.impl.combat

import com.nukkitx.math.vector.Vector3f
import com.nukkitx.protocol.bedrock.data.entity.EntityEventType
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.module.impl.combat.ModuleAntiBot.isBot
import dev.sora.relay.cheat.module.impl.player.ModuleTeams.isTeams
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.entity.Entity
import dev.sora.relay.game.entity.EntityPlayer
import dev.sora.relay.game.entity.EntityPlayerSP
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventTick
import dev.sora.relay.game.utils.TimerUtil
import kotlin.math.pow

class ModuleInfiniteAura : CheatModule("InfiniteAura","百米大刀") {
    private val attackModeValue = ListValue("AttackMode", arrayOf("Single", "Multi"), "Single")
    private val swingValue = ListValue("Swing", arrayOf("Both", "Client", "Server", "None"), "Both")
    private val cpsValue = FloatValue("delay", 3000f, 1000f, 5000f)
    private val rangeValue = FloatValue("Range", 10F, 10F, 128F)
    private val tpDistanceValue = FloatValue("TPDistance", 6F, 2F, 20F)

    private val attackTimer = TimerUtil()
    private var back = false
    private var count = 0
    private lateinit var pos : Vector3f
    override fun onEnable() {
        super.onEnable()
        pos=mc.thePlayer.vec3Position.add(0.0,1.1,0.0)
    }
    @Listen
    fun onTick(event: EventTick) {
        if(back && count>=tpDistanceValue.get()){
            back=false
            count=0
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
        }else if(back){
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = attack.vec3Position
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
            count++
            val swingMode = when (swingValue.get()) {
                "Both" -> EntityPlayerSP.SwingMode.BOTH
                "Client" -> EntityPlayerSP.SwingMode.CLIENTSIDE
                "Server" -> EntityPlayerSP.SwingMode.SERVERSIDE
                else -> EntityPlayerSP.SwingMode.NONE
            }
            session.thePlayer.attackEntity(attack, session, swingMode)
        }
        if (attackTimer.delay(cpsValue.get())) {
            val session = event.session
            val range = rangeValue.get().pow(2)
            val entityList = session.theWorld.entityMap.values.filter {
                it is EntityPlayer && it.distanceSq(session.thePlayer) < range
                        && !it.isBot(session)
                        && !it.isTeams(session)
            }

            if (entityList.isEmpty()) return

            val swingMode = when (swingValue.get()) {
                "Both" -> EntityPlayerSP.SwingMode.BOTH
                "Client" -> EntityPlayerSP.SwingMode.CLIENTSIDE
                "Server" -> EntityPlayerSP.SwingMode.SERVERSIDE
                else -> EntityPlayerSP.SwingMode.NONE
            }
            if (attackModeValue.get() == "Single") {
                (entityList.minByOrNull { it.distanceSq(event.session.thePlayer) } ?: return).also {
                    session.thePlayer.attackEntity(it, session, swingMode)
                    teleport(it, false)
                    session.thePlayer.attackEntity(it, session, swingMode)
                    attack=it as EntityPlayer
                }
            } else if (attackModeValue.get() == "Multi") {
                entityList.forEach {
                    session.thePlayer.attackEntity(it, session, swingMode)
                    teleport(it, false)
                    session.thePlayer.attackEntity(it, session, swingMode)
                    attack=it as EntityPlayer
                }
                entityList.first()
            }
            back=true
            attackTimer.reset()
        }
    }
    private lateinit var attack:EntityPlayer
    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        if (event.packet is EntityEventPacket) {
            if (event.packet.type== EntityEventType.HURT && event.packet.runtimeEntityId==attack.entityId){
                chat("Attack successful!")
            }
        }
    }
    private fun teleport(entity: Entity, isBack: Boolean){
        val entityPos = entity.vec3Position.add(0.0,1.1,0.0)
        val playerPos = session.thePlayer.vec3Position.add(0.0,1.1,0.0)
        if(isBack){
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = playerPos
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = playerPos
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = playerPos
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
        }else{
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = entityPos
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = entityPos
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
            session.netSession.inboundPacket(MovePlayerPacket().apply {
                runtimeEntityId = session.thePlayer.entityId
                position = entityPos
                rotation = session.thePlayer.vec3Rotation
                isOnGround = false
                mode = MovePlayerPacket.Mode.NORMAL
            })
        }
        /*val times = playerPos.distance(entityPos) / tpDistanceValue.get()

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
        }*/
    }


}
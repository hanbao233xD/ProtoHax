package dev.sora.relay.cheat.module.impl.player

import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.module.impl.combat.ModuleAntiBot
import dev.sora.relay.cheat.module.impl.combat.ModuleAntiBot.isBot
import dev.sora.relay.cheat.value.FloatValue
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.GameSession
import dev.sora.relay.game.entity.Entity
import dev.sora.relay.game.entity.EntityPlayer
import dev.sora.relay.game.entity.EntityPlayerSP
import dev.sora.relay.game.event.Listen
import dev.sora.relay.game.event.EventTick
import org.w3c.dom.NameList
import java.util.concurrent.LinkedBlockingQueue

object ModuleTeams : CheatModule("Teams") {

    private val modeValue = ListValue("Mode", arrayOf("Round","Name","Arrow"), "Round")
    private val rangeValue = FloatValue("Range", 150f, 20f, 300f)
    lateinit var list:List<Entity>
    lateinit var nameList: LinkedBlockingQueue<Entity>
    fun EntityPlayer.isTeams(session: GameSession): Boolean {
        if(!state) return false
        if (modeValue.get() == "Round") {
            return list.filterIsInstance<EntityPlayer>().any { it.username.equals(this.username, true) }
        }else if (modeValue.get() == "Name") {
            return nameList.filterIsInstance<EntityPlayer>().any { it.username.equals(this.username, true) }
        }
        return false
    }

    override fun onEnable() {
        super.onEnable()
        when (modeValue.get()) {
            "Round" -> {
                list=session.theWorld.entityMap.values.filter { it is EntityPlayer && it.distanceSq(session.thePlayer) < rangeValue.get() && !it.isBot(session) }
                for (entity in list) {
                    val a=entity as EntityPlayer
                    chat("Added to teams "+a.username)
                }
                state=false
            }
            "Name" -> {
                list=session.theWorld.entityMap.values.filter { it is EntityPlayer && !it.isBot(session) }
                for (entity in list) {
                    val a=entity as EntityPlayer
                    if(mc.thePlayer.username.contains(a.username.substring(0,4))) {
                        chat("Added to teams " + a.username)
                        nameList.add(entity)
                    }
                }
            }
            else -> {
                /// TODO
            }
        }
    }
    @Listen
    fun onTick(event: EventTick) {

    }
}
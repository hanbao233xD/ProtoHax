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

object ModuleTeams : CheatModule("Teams","智能队友") {

    private val modeValue = ListValue("Mode", arrayOf("Round","Name","Arrow"), "Round")
    private val rangeValue = FloatValue("Range", 150f, 20f, 300f)
    var list:List<Entity>?= null
    var nameList: LinkedBlockingQueue<Entity>? = null
    fun EntityPlayer.isTeams(session: GameSession): Boolean {
        if (modeValue.get() == "Round") {
            if(list.isNullOrEmpty()) return false
            return list!!.filterIsInstance<EntityPlayer>().any { it.username.equals(this.username, true) }
        }else if (modeValue.get() == "Name") {
            if(nameList.isNullOrEmpty()) return false
            return nameList!!.filterIsInstance<EntityPlayer>().any { it.username.equals(this.username, true) }
        }
        return false
    }

    override fun onEnable() {
        super.onEnable()
        if(!nameList.isNullOrEmpty()) nameList!!.clear()
        chat("Your Name: " + mc.displayName)
        when (modeValue.get()) {
            "Round" -> {
                list=session.theWorld.entityMap.values.filter { it is EntityPlayer && it.distanceSq(session.thePlayer) < rangeValue.get() && !it.isBot(session) }
                for (entity in list!!) {
                    val a=entity as EntityPlayer
                    chat("Added to teams "+a.username)
                }
                state=false
            }
            "Name" -> {
                nameList=LinkedBlockingQueue<Entity>()
                nameList!!.clear()
                for (entity in session.theWorld.entityMap.values.filter { it is EntityPlayer && !it.isBot(session) }) {
                    val a=entity as EntityPlayer
                    if(mc.thePlayer.username.contains(a.username.substring(0,4))) {
                        chat("Added to teams " + a.username)
                        nameList!!.add(entity)
                    }
                }
            }
            else -> {
                /// TODO
            }
        }
        state=false
    }
    @Listen
    fun onTick(event: EventTick) {

    }
}
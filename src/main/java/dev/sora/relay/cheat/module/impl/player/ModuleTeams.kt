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
import dev.sora.relay.game.event.impl.EventTick

object ModuleTeams : CheatModule("Teams") {

    private val modeValue = ListValue("Mode", arrayOf("Round","Name","Arrow"), "Round")
    private val rangeValue = FloatValue("Range", 15f, 2f, 30f)
    lateinit var list:List<Entity>
    fun EntityPlayer.isTeams(session: GameSession): Boolean {
        if (this is EntityPlayerSP || !state) return false
        if (modeValue.get() == "Range") {
            return list.filterIsInstance<EntityPlayer>().any { it.username.equals(this.username, true) }
        }
        return false
    }
    @Listen
    fun onTick(event: EventTick) {
        when (modeValue.get()) {
            "Round" -> {
                list=session.theWorld.entityMap.values.filter { it is EntityPlayer && it.distanceSq(session.thePlayer) < rangeValue.get() && !it.isBot(session) }
                for (entity in list) {
                    val a=entity as EntityPlayer
                    chat("Added to teams "+a.username)
                }
                state=false
            }
            else -> {
                /// TODO
            }
        }

    }
}
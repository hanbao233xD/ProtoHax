package dev.sora.relay.cheat.module

import dev.sora.relay.cheat.module.impl.combat.*
import dev.sora.relay.cheat.module.impl.misc.*
import dev.sora.relay.cheat.module.impl.movement.ModuleFly
import dev.sora.relay.cheat.module.impl.movement.ModuleSpeed
import dev.sora.relay.cheat.module.impl.player.*
import dev.sora.relay.game.GameSession

class ModuleManager(private val session: GameSession) {

    val modules = mutableListOf<CheatModule>()
    fun getModuleByName(name:String):CheatModule?{
        for (module in modules) {
            if(module.name.equals(name,true)) return module
        }
        return null
    }
    fun registerModule(module: CheatModule) {
        module.session = session
        module.mc = session
        modules.add(module)
        session.eventManager.registerListener(module)
    }

    fun init() {
        registerModule(ModuleFly())
        registerModule(ModuleVelocity())
        registerModule(ModuleKillAura())
        registerModule(ModuleSpammer())
        registerModule(ModuleBGM())
        registerModule(ModuleDisabler())
        registerModule(ModuleOpFightBot())
        registerModule(ModuleNoSkin())
        registerModule(ModuleDeviceSpoof())
        registerModule(ModulePacketLogger())
        registerModule(ModuleResourcePackSpoof())
        registerModule(ModuleRemoteStore())
        registerModule(ModuleAntiBot)
        registerModule(ModuleNoFall())
        registerModule(ModuleAntiKick())
        registerModule(ModuleAntiCrasher())
        registerModule(ModuleAntiVoid())
        registerModule(ModuleCrasher())
        registerModule(ModuleScaffold())
        registerModule(ModuleHUD())
        registerModule(ModuleTeams)
        registerModule(ModuleSpeed())
        registerModule(ModuleBlink())
        registerModule(ModuleFakeLag())
        registerModule(ModuleAntiBlind())
        registerModule(ModuleFastBreak())
        registerModule(ModuleTargetStrafe())
        for (module in modules) {
            module.session.moduleManager=this
        }
    }
}
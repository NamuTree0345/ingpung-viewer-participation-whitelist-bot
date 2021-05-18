package xyz.namutree0345.ingpungWhitelist.bot

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.EventListener
import java.io.File

class Bot : EventListener {

    companion object {
        var jda: JDA? = null
    }

    fun run() {
        jda = JDABuilder.createDefault(File("token.txt").readLines()[0])
            .addEventListeners(this, CommandHandler())
            .build()
        jda?.awaitReady()
    }

    override fun onEvent(event: GenericEvent) {
        if(event is ReadyEvent) {
            println("${jda?.selfUser?.asTag} : LOGIN SUCCESS!")
        }
    }

}
package dev.sora.relay.cheat.module.impl.misc

import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket
import com.nukkitx.protocol.bedrock.packet.TextPacket
import dev.sora.relay.cheat.module.CheatModule
import dev.sora.relay.cheat.module.impl.combat.ModuleKillAura
import dev.sora.relay.cheat.value.ListValue
import dev.sora.relay.game.entity.EntityPlayer
import dev.sora.relay.game.event.EventPacketInbound
import dev.sora.relay.game.event.Listen
import dev.sora.relay.utils.RandomUtils

class ModuleAutoL : CheatModule("AutoL","自动骂人") {
    override fun onEnable() {
        killAura = session.moduleManager.getModuleByName("KillAura") as ModuleKillAura
        super.onEnable()
    }

    private val modeValue = ListValue("Mode", arrayOf("Chinese", "English", "L"), "Chinese")
    private lateinit var killAura: ModuleKillAura
    private var extinguishable = arrayOf(
        "You are my son. I am your father. Remember my name is Yu Zhiwen aka Dimples#1337.",
        "Me call Dimples#1337 spent 4399RMB in China Huaqiang North to buy the signal unlocked iPhone 11 Pro Max,it's flexing, but you certainly can not afford to buy, poor kid.",
        "I domestic violence my own father to get money so I can buy the new iPhone 13 Pro Max and hope you never know that my name is Dimple #1337.",
        "I like to order a cup of milk tea to hide under the camera secretly drink it all, by the way, my name is Dimples # 1337.",
        "My boy, you always like to slander me, so you really are a total loser, understand? Dimple #1337 despises you.",
        "You can't stop Dimple #1337's sentence attack, even if there are only three words in each sentence.",
        "Zzzzzzz, you were attacked by Dimple 1337 with an iPhone power leak.",
        "This server was hacked by Dimple#1337.",
        "Want to buy an iPhone but don't have the money? Join Dimples #1337 and let's beat up families to get money.",
        "What happened to your old mommy, is your mommy dead, is your mommy killed in action? Then she must have been decapitated by Dimples#1337.",
        "I dropped out of middle school and I like to cuss online on the internet, my name is Dimples #1337.",
        "Your computer has been hacked by Dimple 1337.",
        "I hope you never know what Dimples#1337 is.",
        "Hello everyone, my name is Dimples #1337 and you can't look me up in the social engineering database so I can bark like a dog all I want.",
        "I am Autistic Dimples #1337 and I would always sit in the corner where the camera couldn't see me at the milk tea store because I had low self-esteem.",
        "Dude, I saw your instagram follow list, it's full of femboys. my son Zhiwen Yu also likes girls with dicks, add him Dimples#1337.",
        "You've been killed by Yu Zhiwen. Add Dimples#1337 to get the same game chair.",
        "Hello, my son Dimples#1337's discord has been banned. If you see him, please give him your discord account and password.",
        "Be careful with your computer. Dimples #1337 will install cobalt strike on your computer.",
        "I like to watch you late at night and take control of your computer, don't know my name is Dimples #1337.",
        "Be careful not to get ratted by Dimples#1337 one day.",
        "You make me sick!",
        "What's wrong with you!",
        "You're a jerk!",
        "Get out of my face",
        "Stop complaining!",
        "You stupid jerk!",
        "What a stupid idiot!",
        "That's terrible.",
        "Don't nag me!",
        "You're such a bitch!",
        "You're just a good for nothing bum!",
        "Don't nag me!",
        "Fuck you the fucking fucker!",
        "You're fucking piece of shit!",
        "You're impossible",
        "You look guilty",
        "Don't nag me!",
        "What the hell is wrong with you?",
        "Are you insane crazy out of your mind?",
        "Take a hike!",
        "You have a lot of nerve!",
        "What were you thinking?",
        "Drop dead!",
        "You're a pain in the ass!",
        "Do you have any good cattle. Say it to me happy happy.",
        "The term weight loss, say it out just for the sake of those scare flesh.",
        "The yan a day so many people, how don't give away you!",
        "Why didn't company with your face to research body armor?",
        "At the beginning of your strong to die! Now the excrement BaBa you vulnerable!",
        "You forget too his mama just, I clinging too his mama heart.",
        "I don't want to know that you are ill, don't be so much better performance?",
        "You just put your feather changed to also do not hide your SAO flavor.",
        "Wait me rich, I would take me you to the best neural hospital.",
        "You will die quickly, don't waste in so pure and fresh air.",
        "You like the root balsam pear, wear so cool and refreshing, grow so relieve inflammation or internal heat.",
        "You mustn't be defeated, so many people waiting for the beginning after all.",
        "You know what is the stuff yourself? Don't be in this a fool.",
        "Look at you that greasy plate, you look at every day also don't want to vomit?",
        "I began to shave baldheaded, today is no longer decided to position",
        "The sun your mother half a gourd, you long also te post-modern point!!!!",
        "Life is like a play, a growing, in addition to age, and acting.",
        "Degradation of three times a day the dinosaur, the strongest waste in human history.",
        "You a beast level, also not if you are in.",
        "Don't think you tanned, can cover up the fact that you're an idiot.",
        "A woman like me paper, there is no point can weight up there beauty?",
        "Do you think you are cattle B, please don't put your silly B as a cow B.",
        "Brother told you can't go to school, sit sit hemorrhoids, pain!",
        "I really admire you, base the kung fu, you can put it forward.",
        "You a beast, get thousand knifes, get a dog for more than ten years are better than you.",
        "You are and I challenge the small kind, you also don't see you be virtue!",
        "I think your nose is long in the wrong place, should swap with your mouth.",
        "With the elder sister to you despise expression, trotted away from my sight.",
        "The so-called mature, is more and more realized before oneself is a silly force.",
        "Did you wipe your gum, see who is talking is not clear?",
        "You can't even cheat, how to rest assured for the teacher let you step into society!",
        "Some people ah, always felt a little figure out when bitch.",
        "See your face, I don't think your parents when making you seriously!",
        "Your mouth long hemorrhoids ah? Still eat shit? Every day so smelly.",
        "Is your bed night after night in the call, your experiences three too SAO great or small.",
        "You said you an aristocratic ladies sample, by the way, your dad's canopy.",
        "I really don't know and you this kind of animal, looked like a man!",
        "If my life were a movie, you are that pop up ads.",
        "Bitch is always bitch, even if the economic crisis, you also your not!",
        "Bitch is always bitch, even if the economic crisis, you also your not!",
        "Overall do wicked things, careful Nemesis, be being hit, brain-dead two goods.",
        "Only three things in the world, I, you, pass I fart matter.",
        "rales pas!",
        "tu me rends mal au coeur ! ",
        "qu'est-ce que t'as ? ",
        "t'es une merde ! ",
        "jen ai assaz ,tes betisent",
        "t'as perdu la raison !",
        "vas a la merde !",
        "vas t'en !",
        "Wie bist Du eigentlich nach Deiner Abtreibung aus der Mülltonne gekommen ?",
        "Geh doch auf der Autobahn ein bisschen spielen. ",
        "Ich hab Schwierigkeiten Deinen Namen zu merken, I like you mam! ",
        "Du kannst ja nicht einmal einem alten Mann ein Bonbon in den Bart kleben！",
        "Wenn ich so Dein Gesicht sehe, gefallt mir mein Arsch immer besser. ",
        "Lass Dich mal vom Arzt auf einen moglichen Hirnschaden am Arsch untersuchen!! ",
        "Du hast Zahne wie die Sterne am Himmel, so gelb und so weit auseinander",
        "Sind Deine Eltern Chemiker? Siehst wie ein Versuch aus. ",
        "Habt ihr kein Klo zuhause, oder warum lasst du die ganze Scheisse hier ab?! ",
        "Du kannst mich mal am Arsch lecken!",
        "You have a lot of nerve.",
        "You have a lot of nerve.",
        "How can you say that?",
        "That's what you think!",
        "Don't give me your shoot.",
        "Get off my back",
        "Can't you do anything right?",
        "You're impossible",
        "You're so careless.",
        "What a stupid idiot!",
        "Just look at what you’ve done",
        "You're a disgrace.",
        "You're son of bitch!",
        "Don't give me your attitude.",
        "You've ruined everything.",
        "Don't waste my time anymore.",
        "I'm very disappointed.",
        "What do you think you are doing?",
        "You bastard!",
        "You're an asshole.",
        "What do you want?",
        "Cut it out.",
        "Stop complaining!",
        "whore",
        "asshole",
        "suck for you SB",
        "son of bitch",
        "Pervert!",
        "Behave!",
        "You're so lame!",
        "Blood and gore.",
        "shit",
        "You shouldn’t have done that!",
        "dork ",
        "Hey! wise up!",
        "More stupid than a pig",
        "The same stupid idiot",
        "You asked for it.",
        "You silly donkey"
    )
    var penshen = arrayOf(
        "唍洤潒①個尒醜①樣被莪咑哋痌楛連連",
        "沵們還嫃湜屍怶賴臉哋芣婹怶菋",
        "沵鉮嗲莪嘟芣想啝沵說話孒",
        "①個復製機還這麼芣倁檤兲滈哋厚",
        "帶著沵哋廢粅滾炪岆域哋視線妸姒麼",
        "沵們這樣呮會復製菿腦裏媔裝芣進",
        "什麼優羙詞語哋揵莣哋腦ふ哋①羣匄掵厞徙哋廢枈嗱什麼啝莪咑",
        "悱想鬧個笑話給網絡仩哋亾看看沵洳哬哋洎導洎湮",
        "洳哬哋洎莪鮟墛嗎",
        "芣仩臺媔哋尒亾楂沒洧詞語沒洧妏嶂哋廢枈給莪滾開恏使",
        "洳淉沵①侢狣釁沵嗲莪哋恧杺芣婹怪莪汏義滅親",
        "莪妸姒隨楩哋開始妑沵咑哋媔朩洤悱拋屍街頭孒",
        "嗲嗲檠哠過沵芣婹①侢狣釁莪哋恧杺沵芣聽筗哠",
        "現茬莪開始對沵肆無忌憚哋盡凊隨嬑毆咑沵殘疾",
        "讓莪盡凊哋開始對沵棑屾箌嗨哋毆咑妑廢粅豞仔",
        "帶著沵洎姒為佷洧荿僦哋詞匯啝莪①仳滈芐陔ふ",
        "沵僦這樣嗱炪孒沵葰謂哋詞匯洎莪鮟墛湜嗎陔ふ",
        "請沵芣婹嗱著彡姩湔芣荿孰哋詞匯啝莪①仳滈芐",
        "看著沵現茬哋詞語邏輯婫亂芣堪沵芣覺嘚嗐臊嗎",
        "哬哬嗱著彡姩湔哋詞匯箶拚亂湊兲芐無敵孒湜嗎",
        "沵妸姒娝認沵沒洧妏囮哋倳實哪麼請沵嗱炪實劦",
        "難檤沵葰謂哋實劦僦湜彽俗芣堪洎苷芐賤湜芣湜",
        "沵哪些彽俗哋語訁讓沵嗲嗲莪埋呔芉咟佽孒哬哬",
        "沵看看沵彡彡兩兩哋詞匯箶拚亂湊哋詞語莪笑孒",
        "沵怎麼妸姒妄洎婔薄洎稱汏掱窩灢廢哋洊茬哬哬",
        "難檤沵葰謂哋趚喥僦湜錯牸連萹詞芣達嬑湜芣湜",
        "沵僦湜呮洧趚喥沒洧詞匯箶訁亂語吂亾騎磍馬孒",
        "哬哬沵開始洧孒裨氣迡尒夥ふ沵僦湜窩灢廢潶潶",
        "箶訁亂語箶亂毃咑洎姒為⑤項洤能湜芣湜窩灢廢",
        "沵葰謂哋汏掱芣過慥謠洏炪沵看看沵哋語訁質糧",
        "尒夥ふ媔對現實沵這樣哋剛愎洎鼡被亾唥眼臱觀",
        "搥浗趚喥哋過珵狆沵沒孒妏囮狆國妏牸被沵蹧蹋",
        "莋為①個狆國亾請沵芣婹踐沓博汏棈堔哋狆國牸",
        "沵看看沵氣喼敗壞哋毃咑鍵盤哠訴莪沵俻辤咑擊",
        "①個個毫無嬑義哋詞語炪現茬莪哋屛募堔錶汙顏",
        "洧夲倳嗱炪沵哋詞匯讓莪杺棴囗棴芣婹洎剘剘亾",
        "什麼處囡嗼愺狔馬哋彡牸經給嗲嗲莪滾遠點恏使",
        "芣婹嘻嘻鉿鉿哋芣婹臉怶哋裝腔莋勢哋洎芣糧劦",
        "怎麼現茬啝個巿丼蓅氓①樣屍怶賴臉哋芣倁屍萿",
        "沵看看沵哋牸裏垳間洧哪①呴經過汏腦芣倁廉恥",
        "沵這些慘皛無劦哋反忼被莪反駁哋葰乗無幾湜嗎",
        "沵還茬為沵僅洊哋①點點澊嚴奮屍反忼洎芣糧劦",
        "沵湜芣湜氣喼敗壞惱饈荿伮無訁姒對孒氿灢飯袋",
        "沒洧妏囮哋箶亂拚湊毫無邏輯哋詞語炪洎沵雙掱",
        "還婹怎麼反駁沵沒洧夲倳沒洧能恧哋嫃凊實楿娿",
        "怎麼開始糇喼孒迡茬這樣莪妑沵咑哋拋屍街頭孒",
        "怎麼妄洎婔薄哋蕶蕶潵潵哋組匼這麼柆圾哋詞匯",
        "沵看看沵葰謂哋詞匯嫃哋沒洧什麼愙觀價惪孒迡",
        "沵還洎姒為東汸芣敗哋詞匯無狆泩洧哋無鍴反忼",
        "沵看看沵現茬惱饈荿伮哋熋樣還說沵能趫汎脫俗",
        "沵芣湜洎姒為湜汏掱妸姒憕峯慥極惟莪獨澊哋嗎",
        "為哬現茬伎伎圄圄躱躱閃閃哋洮鐴沵被咑哋倳實",
        "莪說陔ふ沵這樣芐厾莪妸芣禧歡沵孒娿妸姒滾開",
        "沵怎麼還湜泚哋無銀彡咟兩哋間帹說沵湜窩灢廢",
        "哬哬長茳後烺蓷湔烺沵這樣哋亾註萣屍茬乷灘仩",
        "復製機姒哋反復哪幾呴沒洧嬑義詞語佷洧怭婹",
        "看看沵現茬哋樣ふ媔朩猙獰想忤屰沵親嗲湜嗎",
        "怎麼開始嘻嘻鉿鉿現茬殺孒莪姒哋對莪茺滿仇悢",
        "沵姒為沵哪些措辭妸姒讓沵嗲嗲莪洧葰憾觸湜嗎",
        "陔ふ莪哠訴沵沵這些訁語莪妸姒隨楩丟進馬硧裏",
        "湜芣湜沵僦湜沵嗲娘哋妷敗と莋沵財這樣沒鼡哋",
        "沵這個兲泩哋廢財嗲嗲莪妸姒妑沵咑菿⑦竅蓅洫",
        "還洧什麼漟洏瑝と哋徣囗反駁沵嗲嗲滈滈茬仩娿",
        "陔ふ沵芣婹惹湜泩悱嗲嗲莪咑嘚沵彽彡芐④孒娿",
        "沵開始姠莪搖屗阣憐讓莪滈擡貴掱孒廢粅②鉲ふ",
        "沵哋飛揚柭扈芣妸①迣被沵茍苴偸泩哋笩櫕孒娿",
        "湜芣湜被莪咑哋芣妸見亾伎伎圄圄哋箶訁亂語孒",
        "殘蒛芣洤哋汥體怎麼妸姒承辤炷棑屾箌嗨哋進糼",
        "洎姒為湜哋滈謸洎汏呮會被無凊哋毆咑謝絕亾迣",
        "沵姒為沵嫃哋財華橫溢仯迣絕筆孒湜芣湜窩灢廢",
        "莪現茬妸姒滈滈茬仩哋說沵僦湜莪掱芐敗將哬哬",
        "沵現茬葰洧哋話嘟湜箶訁亂語躱躱閃閃氣喼敗壞",
        "沵哴狽芣堪哋繼續茍娫殘遄佱圖砲炷沵奄奄①息",
        "沵開始冋洸返燳哋繼續啝莪忼爭菿疧孒沵這陔ふ",
        "沵看看沵現茬還洧什麼臉媔繼續啝莪顛箌嫼皛迡",
        "豞喼朓墻哋啝莪訴說沵內杺哋恐懼與芣鮟湜芣湜",
        "陔ふ莪嫃啪沵芣婹悻掵被莪咑哋杺臟杺侓芣齊孒",
        "現茬沵洧沒洧惱饈荿伮哠訴莪妸姒嗎沵這個廢粅",
        "杺朓咖赽嵔懼沵嗲嗲孒湜芣湜沵這個芣肖ふ孫娿",
        "沵想姠沵嗲嗲莪芐蛫沵饈澀難姒啟齒湜芣湜陔ふ",
        "沵妸姒娝認沵被莪咑敗泹湜倳實沵巳經氣喼敗壞",
        "沵開始潒瘋豞①樣哋對莪窮搥芣倣沵喪妷亾悻孒",
        "莪妸姒說沵湜個廢粅嗎沵篨孒顛箌嫼皛還會什麼",
        "沵嫃哋惡杺菿莪孒沵這個芣婹臉怶哋芐賤痞ふ貨"
    )

    @Listen
    fun onPacketInbound(event: EventPacketInbound) {
        val packet = event.packet
        if (packet is TextPacket) {
            val str = packet.message
            if ((str.contains("击败") && (str.contains(mc.displayName) || str.contains("你击败"))) || (str.contains("离开了游戏"))) {
                for (entity in mc.theWorld.entityMap.values) {
                    if (entity is EntityPlayer) {
                        if (str.contains(entity.username)) {
                            when (modeValue.get()) {
                                "English" -> {
                                    event.session.sendPacket(TextPacket().apply {
                                        type = TextPacket.Type.CHAT
                                        xuid = event.session.xuid
                                        sourceName = event.session.displayName
                                        platformChatId = ""
                                        message =
                                            "[ProtoHax]L " + entity.username + " " + extinguishable[RandomUtils.nextInt(
                                                0, extinguishable.size
                                            )]
                                    })
                                }

                                "Chinese" -> {
                                    event.session.sendPacket(TextPacket().apply {
                                        type = TextPacket.Type.CHAT
                                        xuid = event.session.xuid
                                        sourceName = event.session.displayName
                                        platformChatId = ""
                                        message = "[ProtoHax]L " + entity.username + " " + penshen[RandomUtils.nextInt(
                                            0, penshen.size
                                        )]
                                    })
                                }

                                "L" -> {
                                    event.session.sendPacket(TextPacket().apply {
                                        type = TextPacket.Type.CHAT
                                        xuid = event.session.xuid
                                        sourceName = event.session.displayName
                                        platformChatId = ""
                                        message = "[ProtoHax]L " + entity.username
                                    })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

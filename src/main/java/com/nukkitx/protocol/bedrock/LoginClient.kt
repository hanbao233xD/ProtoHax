package com.nukkitx.protocol.bedrock

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.net.URL
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class LoginClient(uri: URI) : WebSocketClient(uri) {

    private val scanner = Scanner(System.`in`)

    val account = ""
    val password = ""
    val ip = ""
    val hwid = ""
    var key = ""
    init {
        connect()
    }

    override fun onOpen(handshakedata: ServerHandshake?) {
        login("account1","password1")
    }
    fun getPublicIP(): String? {
        try {
            val url = URL("http://checkip.dyndns.org")
            val connection = url.openConnection()
            connection.connect()
            val inputStream = connection.getInputStream()
            val response = inputStream.bufferedReader().use { it.readText() }
            inputStream.close()
            val start = response.indexOf("Address: ") + 9
            val end = response.indexOf("</body>")
            return response.substring(start, end)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        println("Connection closed")
    }
    fun decrypt(encryptedText: String, secretKey: String): String {
        val key = SecretKeySpec(secretKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decodedEncryptedBytes = Base64.getDecoder().decode(encryptedText)
        val decryptedBytes = cipher.doFinal(decodedEncryptedBytes)
        return String(decryptedBytes)
    }
    fun encrypt(plainText: String, secretKey: String): String {
        val key = SecretKeySpec(secretKey.toByteArray(), "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(plainText.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }
    override fun onMessage(message: String?) {
        if(message!!.startsWith("S:")){
            try {
                key=message.replace("S:","")
                println(key)
                send("$account:$password:${encrypt("114514",key)}:$hwid")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        println(message)
    }
    fun login(account:String,password:String){
        try {
            send("C:NONE")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onError(ex: Exception?) {
        println("Error: $ex")
    }
}

fun main() {
    val client = LoginClient(URI("ws://localhost:8080"))
    while (client.isOpen) {
        // Wait for server response
    }
}
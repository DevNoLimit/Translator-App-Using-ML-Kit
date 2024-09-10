package com.devnolimit.translatorusinmlkit

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

object LanguagesUtils {

    fun getAllLanguagesCode(): List<String> = TranslateLanguage.getAllLanguages()


    fun translationInit(text: String, outputLanguage: String, onSuccess: String.() -> Unit){
        val option = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(outputLanguage)
            .build()
        val client = Translation.getClient(option)

        //Download Model if Needed
        client.downloadModelIfNeeded(
            DownloadConditions.Builder()
                .requireWifi()
                .requireCharging()
                .build()
        ).addOnSuccessListener {
            client.translateLanguage(text, onSuccess)
        }.addOnFailureListener {
            Log.e("TranslationInit", "Failure ==> ${it.localizedMessage.orEmpty()}")
        }
    }


    fun Translator.translateLanguage(text: String, onSuccess: String.() -> Unit){
        translate(text).addOnSuccessListener {
            onSuccess(it.orEmpty())
        }.addOnFailureListener {
            Log.e("TranslateLanguage", "Failure ==> ${it.localizedMessage.orEmpty()}")
        }
    }

}
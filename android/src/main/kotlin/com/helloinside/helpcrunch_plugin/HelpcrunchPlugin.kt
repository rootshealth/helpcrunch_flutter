package com.helloinside.helpcrunch_plugin


import androidx.annotation.NonNull
import com.helpcrunch.library.core.Callback
import com.helpcrunch.library.core.HelpCrunch
import com.helpcrunch.library.core.models.user.HCUser

import io.flutter.embedding.engine.plugins.FlutterPlugin
import java.lang.Exception


class HelpcrunchPlugin : FlutterPlugin {


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {

        Pigeon.HelpCrunchPlugin.setup(
            flutterPluginBinding.binaryMessenger,
            HelpCrunchPluginImpl()
        )
    }


    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    }


}

/**
 * actual implementation of the generated api. for the API definition, see pigeon/host_api.dart.
 */
private class HelpCrunchPluginImpl : Pigeon.HelpCrunchPlugin {

    override fun showChatScreen(resultCallback: Pigeon.Result<Void>?) {
        HelpCrunch.showChatScreen(
            options = null,
            callback = object : Callback<Any?>() {
                override fun onSuccess(result: Any?) {
                    resultCallback?.success(null)
                }

                override fun onError(message: String) {
                    resultCallback?.error(Exception(message))
                }
            })
    }

    override fun updateUser(user: Pigeon.HelpCrunchUser, resultCallback: Pigeon.Result<Void>?) {
        HelpCrunch.updateUser(user = HCUser.build {
            userId = user.id
            name = user.name
            email = user.email
            company = user.company
            phone = user.phone
            customData = user.customData
        }, callback = object : Callback<HCUser>() {
            override fun onSuccess(result: HCUser) {
                resultCallback?.success(null)
            }

            override fun onError(message: String) {
                resultCallback?.error(Exception(message))
            }
        })
    }

    override fun logoutUser(resultCallback: Pigeon.Result<Void>?) {
        HelpCrunch.logout(callback = object : Callback<Any?>() {
            override fun onSuccess(result: Any?) {
                resultCallback?.success(null)
            }

            override fun onError(message: String) {
                resultCallback?.error(Exception(message))
            }
        })
    }

    override fun initialize(
        params: Pigeon.HelpCrunchInitializationParams,
        resultCallback: Pigeon.Result<Void>?
    ) {

        HelpCrunch.initialize(
            organization = params.organizationName,
            appId = params.helpCrunchAppId.toInt(),
            secret = params.appSecret,
            callback = object : Callback<Any>() {
                override fun onSuccess(result: Any) {
                    resultCallback?.success(null)
                }

                override fun onError(message: String) {
                    resultCallback?.error(Exception(message))
                }
            })

    }


}
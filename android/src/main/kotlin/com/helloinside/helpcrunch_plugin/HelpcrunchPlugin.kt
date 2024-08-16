package com.helloinside.helpcrunch_plugin

import androidx.annotation.NonNull
import com.helpcrunch.library.core.Callback
import com.helpcrunch.library.core.HelpCrunch
import com.helpcrunch.library.core.models.user.HCUser
import com.helpcrunch.library.core.options.HCOptions
import com.helpcrunch.library.core.options.theme.HCNotificationsTheme
import com.helpcrunch.library.core.options.theme.HCTheme
import com.helloinside.helpcrunch_plugin.R
import com.helpcrunch.library.core.options.theme.HCAvatarTheme
import com.helpcrunch.library.core.options.theme.HCChatAreaTheme
import com.helpcrunch.library.core.options.theme.HCMessageAreaTheme
import com.helpcrunch.library.core.options.theme.HCPreChatTheme
import com.helpcrunch.library.core.options.theme.HCSystemAlertsTheme
import com.helpcrunch.library.core.options.theme.HCToolbarAreaTheme

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

    override fun showChatScreen(resultCallback: Pigeon.Result<Void>) {
        HelpCrunch.showChatScreen(
            callback = object : Callback<Any?>() {
                override fun onSuccess(result: Any?) {
                    resultCallback.success(null)
                }

                override fun onError(message: String) {
                    resultCallback.error(Exception(message))
                }
            })
    }

    override fun updateUser(user: Pigeon.HelpCrunchUser, resultCallback: Pigeon.Result<Void>) {
        HelpCrunch.updateUser(
            user = HCUser.Builder().apply {
                userId = user.id
                name = user.name
                email = user.email
                company = user.company
                phone = user.phone
                customData = user.customData
            }.build(),
            callback = object : Callback<HCUser>() {
                override fun onSuccess(result: HCUser) {
                    resultCallback.success(null)
                }

                override fun onError(message: String) {
                    resultCallback.error(Exception(message))
                }
            })
    }

    override fun logoutUser(resultCallback: Pigeon.Result<Void>) {
        HelpCrunch.logout(callback = object : Callback<Any?>() {
            override fun onSuccess(result: Any?) {
                resultCallback.success(null)
            }

            override fun onError(message: String) {
                resultCallback.error(Exception(message))
            }
        })
    }

    override fun registerForRemoteMessages(result: Pigeon.Result<Void>) {
        result.error(Exception("not yet implemented"))
    }

    override fun getNumberOfUnreadChats(resultCallback: Pigeon.Result<Long>) {

        HelpCrunch.getUnreadChatsCount(object : Callback<Int>() {
            override fun onSuccess(result: Int) {
                resultCallback.success(result.toLong())
            }

            override fun onError(message: String) {
                resultCallback.error(Exception(message))
            }
        })
    }

    override fun initialize(
        params: Pigeon.HelpCrunchInitializationParams,
        resultCallback: Pigeon.Result<Void>
    ) {
        val color = params.notificationColor

        val avatarTheme = HCAvatarTheme.build {
            setUseDefaultAvatarColors(false)
            setPlaceholderBackgroundColor(color?.toInt() ?: 0xffffffff.toInt())
        }


        val notificationTheme = if (color != null) {
            HCNotificationsTheme.build {
                this.setColor(color.toInt())
                this.setSmallIconRes(R.drawable.notification_icon_helpcrunch)
                this.setAvatarTheme(avatarTheme)
            }
        } else {
            null
        }

        val theme = if (notificationTheme != null) {
            HCTheme.build {
                this.setNotificationsTheme(notificationTheme)

                this.setToolbarAreaTheme(HCToolbarAreaTheme.build {
                    this.setAvatarTheme(avatarTheme)
                })
                this.setChatAreaTheme(HCChatAreaTheme.build {
                    this.setOutcomingBubbleColor(0xff585FDF.toInt())
                    this.setAvatarTheme(avatarTheme)
                })
            }
        } else {
            null
        }

        val options =
            if (theme != null)
                HCOptions.Builder().apply {
                    setTheme(theme)
                }.build() else null

        HelpCrunch.initialize(
            organization = params.organizationName,
            appId = params.helpCrunchAppId.toInt(),
            secret = params.appSecret,
            options = options,
            callback = object : Callback<Any>() {
                override fun onSuccess(result: Any) {
                    resultCallback.success(null)
                }

                override fun onError(message: String) {
                    resultCallback.error(Exception(message))
                }
            })
    }

    override fun isReady(): Boolean {
        return HelpCrunch.getState().isReady()
    }

    override fun hasError(): Boolean {
        return HelpCrunch.getState().isError()
    }


}
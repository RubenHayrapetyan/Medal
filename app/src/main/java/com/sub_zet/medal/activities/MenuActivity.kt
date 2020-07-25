package com.sub_zet.medal.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.gson.reflect.TypeToken
import com.sub_zet.medal.R
import com.sub_zet.medal.api.ApiCallBack
import com.sub_zet.medal.api.ApiManager
import com.sub_zet.medal.helpers.MyUniqueID
import com.sub_zet.medal.models.UserBlockStatusModel
import kotlinx.android.synthetic.main.activity_menu.*
import okhttp3.ResponseBody
import org.koin.java.KoinJavaComponent
import retrofit2.Call

class MenuActivity : BaseActivity() {

    private val myUniqueIDLazy = KoinJavaComponent.inject(MyUniqueID::class.java)
    private var userBlockDialog: Dialog? = null

    private val ID_JOYSTICK = 1
    private val ID_PAYMENT = 2
    private val ID_PROFILE = 3
    private val ID_HELP = 4

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        getUserBlockStatus(myUniqueIDLazy.value.loadUniqueId().toString())
        initNavigation()
        animatedBottomNavigation()
    }

    private fun initNavigation() {
        navController = findNavController(R.id.gamesMenuFragment)
    }

    private fun animatedBottomNavigation() {
        bottom_navigation.add(MeowBottomNavigation.Model(1, R.drawable.joystick_icon))
        bottom_navigation.add(MeowBottomNavigation.Model(2, R.drawable.payment_icon))
        bottom_navigation.add(MeowBottomNavigation.Model(3, R.drawable.profile_icon))
        bottom_navigation.add(MeowBottomNavigation.Model(4, R.drawable.help_icon))

        bottom_navigation.show(1)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.gamesMenuFragmentNavigation -> bottom_navigation.show(1)
                R.id.paymentsMenuFragmentNavigation -> bottom_navigation.show(2)
                R.id.profileMenuFragmentNavigation -> bottom_navigation.show(3)
                R.id.helpMenuFragmentNavigation -> bottom_navigation.show(4)
            }
        }

        bottom_navigation.setOnClickMenuListener {
            when (it.id) {
                ID_JOYSTICK -> {
                    navController.popBackStack(R.id.gamesMenuFragmentNavigation, false)
                    navController.navigate(R.id.gamesMenuFragmentNavigation)
                }
                ID_PAYMENT -> {
                    navController.popBackStack(R.id.paymentsMenuFragmentNavigation, false)
                    navController.navigate(R.id.paymentsMenuFragmentNavigation)
                }
                ID_PROFILE -> {
                    navController.popBackStack(R.id.profileMenuFragmentNavigation, false)
                    navController.navigate(R.id.profileMenuFragmentNavigation)
                }
                ID_HELP -> {
                    navController.popBackStack(R.id.helpMenuFragmentNavigation, false)
                    navController.navigate(R.id.helpMenuFragmentNavigation)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (navController.popBackStack() == false) {
            super.onBackPressed()
        } else {
            if (navController.currentDestination?.id != R.id.gamesMenuFragmentNavigation &&
                    navController.currentDestination?.id != R.id.paymentsMenuFragmentNavigation &&
                    navController.currentDestination?.id != R.id.profileMenuFragmentNavigation &&
                    navController.currentDestination?.id != R.id.helpMenuFragmentNavigation)
                navController.navigateUp()
            return
        }
    }

    private fun getUserBlockStatus(id: String) {
        ApiManager.getInstance().getUserBlockStatus(id)
                .enqueue(object : ApiCallBack<UserBlockStatusModel?>(object : TypeToken<UserBlockStatusModel?>() {}) {

                    override fun onSuccess(call: Call<ResponseBody>?, response: UserBlockStatusModel?) {
                        if (response != null) {
                            if (response.blockStatus.equals("true")) {
                                // User is blocked
                                userIsBlokedDialog()
                            }
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
    }
    private fun userIsBlokedDialog() {
        if (userBlockDialog == null) {
            userBlockDialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
            userBlockDialog!!.setCancelable(false)
            userBlockDialog!!.setContentView(R.layout.dialog_user_is_blocked)
        }
        userBlockDialog!!.show()
    }
}

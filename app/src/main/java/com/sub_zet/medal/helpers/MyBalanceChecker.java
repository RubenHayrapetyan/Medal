package com.sub_zet.medal.helpers;

public class MyBalanceChecker {

    public static boolean checkBalance(float userBalance, float choosenBalance) {

        return userBalance >= choosenBalance;
    }


    public static boolean canUserCashOut(float userBalance, float choosenBalance) {
            float mMinimumCashOut = (float) (500 * 0.5 / 100 + 100);
            float mChoosenBalance = (float) (choosenBalance * 0.5 / 100 + choosenBalance);

        if (choosenBalance >= (mMinimumCashOut) && userBalance >= mChoosenBalance) return true;
        else return false;
    }
}

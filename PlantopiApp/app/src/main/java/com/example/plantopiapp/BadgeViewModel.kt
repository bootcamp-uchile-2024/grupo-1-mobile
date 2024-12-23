package com.example.plantopiapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BadgeViewModel : ViewModel() {
    val badgeCount = MutableLiveData<Int>()

    fun updateBadgeCount(count: Int) {
        badgeCount.value = count
    }
}

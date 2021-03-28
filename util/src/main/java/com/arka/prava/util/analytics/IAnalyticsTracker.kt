package com.arka.prava.util.analytics

import com.arka.prava.util.utils.AppExperimental

/**
 * A Simple analytics tracker contract
 */
@AppExperimental
interface IAnalyticsTracker {
    /**
	 * Properties which will be invoked on every [sendEvent] calls.
	 *
	 * These typically need to be set once in a app lifecycle, ie, in
	 * Application onCreate()
	 */
    fun setSessionProperties(propertyMap: () -> Map<String, Any>)

    fun sendEvent(eventName: String, propertyMap: Map<String, Any>?)

	/**
	 * Properties specific to the logged in user.
	 */
	fun setUserData(userId: String, propertyMap: Map<String, Any>?)

	/**
	 * Specific update operation for user details.
	 */
	fun updateUserData(propertyMap: Map<String, Any>?)

	/**
	 * Reset properties specific to the logged in user.
	 */
	fun resetUserData()
}

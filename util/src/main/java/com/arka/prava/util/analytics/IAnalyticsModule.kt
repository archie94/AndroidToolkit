package com.arka.prava.util.analytics

interface IAnalyticsModule : AutoCloseable {
    fun withTracker(trackerId: AnalyticsEngineId): IAnalyticsTracker

    fun withTrackers(vararg trackerIds: AnalyticsEngineId): List<IAnalyticsTracker>
}

fun List<IAnalyticsTracker>.sendEvent(eventName: String, propertyMap: Map<String, Any>?) {
    forEach { tracker -> tracker.sendEvent(eventName, propertyMap) }
}

class AnalyticsModule(
    private val engineList: List<IAnalyticsEngine>
) : IAnalyticsModule {
    override fun withTracker(trackerId: AnalyticsEngineId): IAnalyticsTracker =
        engineList.find { it.getIdentifier() == trackerId }
            ?: throw IllegalStateException("Analytics engine not registered")

    override fun withTrackers(vararg trackerIds: AnalyticsEngineId): List<IAnalyticsTracker> {
        return engineList.filter { it.getIdentifier() in trackerIds }
    }

    override fun close() {
        engineList.forEach { it.close() }
    }
}

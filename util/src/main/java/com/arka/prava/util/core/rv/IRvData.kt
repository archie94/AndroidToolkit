package com.arka.prava.util.core.rv

/**
 * Created by Arka Prava Basu <arkaprava94@gmail.com> on 6/2/19.
 */
interface IRvData {
    /**
     * The view type for this data
     */
    fun getType(): Int

    /**
     * An id of the data
     */
    fun getId(): String

    override fun equals(other: Any?): Boolean
}

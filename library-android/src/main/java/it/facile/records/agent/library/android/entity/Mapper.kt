package it.facile.records.agent.library.android.entity

/**
 *
 *  Base class for Mapper class used to pass data from architecture bounderies
 * */
interface Mapper<in E, T> {
    fun mapFrom(from: E): T
}

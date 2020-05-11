package lib.util

/**
 *
 */
open class Cache {
    private val map = mutableMapOf<String, Any?>()
    operator fun <T> invoke(name: String, data: () -> T): T {
        @Suppress("UNCHECKED_CAST")
        return map.getOrPut(key = name, defaultValue = data) as T
    }

    fun isClear(name: String): Boolean = !map.containsKey(name)

    /**
     * The default cache.
     * For big caches, create a cache object in order to remove any chance of two sources
     * overwriting the same key.
     */
    companion object Default : Cache() {

    }
}

class SingletonCache<T> {
    private var array = arrayOfNulls<Any>(1)

    /**
     * @throws ClassCastException if there is a
     */
    operator fun invoke(data: () -> T): T {
        if (array[0] == null) {
            array[0] = data()
        }
        @Suppress("UNCHECKED_CAST")
        return array[0] as T
    }
}
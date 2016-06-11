/**
 * Copyright (C) 2013-2016 The Rythm Engine project
 * for LICENSE and other details see:
 * https://github.com/rythmengine/rythmengine
 */
package org.rythmengine.extension;

import java.io.Serializable;

/**
 * Define cache service
 */
public interface ICacheService {
    /**
     * Store an item into the cache service by key and set ttl value
     *
     * @param key
     * @param value
     * @param ttl   time to live of the cached item. Time unit is second.
     *              If set to negative number, then it will never expire.
     *              If set to zero then the default ttl value will be used
     */
    void put(String key, Serializable value, int ttl);

    /**
     * Store an item into the cache by key and use default ttl
     *
     * @param key
     * @param value
     */
    void put(String key, Serializable value);

    /**
     * Remove an item from cache service by key
     *
     * @param key
     * @return the value associated with the key
     * @deprecated use {@link #evict(String)} instead
     */
    Serializable remove(String key);

    /**
     * Remove an item from the cache service by key
     * @param key
     */
    void evict(String key);

    /**
     * Return an item from the cache service by key
     *
     * @param key
     * @return the value associated with the key
     */
    Serializable get(String key);

    /**
     * Check if the cache contains key
     *
     * @param key
     * @return true if key is found in the cache
     * @deprecated
     */
    boolean contains(String key);

    /**
     * Remove all cached items
     */
    void clear();

    /**
     * Set default ttl value which will be used if user pass 0 as ttl or not specified ttl
     *
     * @param ttl
     */
    void setDefaultTTL(int ttl);

    /**
     * Shutdown the cache service
     */
    void shutdown();

    /**
     * Restart the cache service after shutdown
     * 
     * <p>Note, by default the cache service
     * should be started after initialized</p>
     */
    void startup();
}

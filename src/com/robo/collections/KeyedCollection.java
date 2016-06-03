/**
 * Copyright (c) 2016 Robo Creative - https://robo-creative.github.io.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.robo.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.robo.reflect.TypeUtils;

public abstract class KeyedCollection<K, V> implements Collection<V> {

	private Map<K, V> mInner;
	private List<K> mKeys;

	protected KeyedCollection() {
		this(new HashMap<K, V>());
	}

	protected KeyedCollection(Map<K, V> map) {
		mInner = map;
		mKeys = new ArrayList<>();
	}

	@Override
	public boolean add(V e) {
		K key = getKeyForItem(e);
		if (addKey(key)) {
			mInner.put(key, e);
			return true;
		}
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends V> c) {
		boolean insertionResult = false;
		for (V v : c) {
			insertionResult |= add(v);
		}
		return insertionResult;
	}

	public V get(K key) {
		if (containsKey(key)) {
			return mInner.get(key);
		}
		return null;
	}

	public V getAt(int index) {
		if (isValidIndex(index)) {
			return get(mKeys.get(index));
		}
		return null;
	}

	@Override
	public void clear() {
		mKeys.clear();
		mInner.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		return null != o && o.getClass().equals(TypeUtils.getGenericParameterType(this, 1))
				&& containsKey(getKeyForItem((V) o));
	}

	public boolean containsKey(K key) {
		return null != key && mKeys.contains(key);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		if (null == c || c.isEmpty()) {
			return false;
		}
		boolean result = true;
		for (Object o : c) {
			result &= contains(o);
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return size() > 0;
	}

	@Override
	public Iterator<V> iterator() {
		return mInner.values().iterator();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Object o) {
		return removeByKey(getKeyForItem((V) o));
	}

	public boolean removeAt(int index) {
		return isValidIndex(index) && removeByKey(mKeys.get(index));
	}

	public boolean removeByKey(K key) {
		if (removeKey(key)) {
			mInner.remove(key);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (null == c || c.isEmpty()) {
			return false;
		}
		boolean removalResult = true;
		for (Object o : c) {
			removalResult &= remove(o);
		}
		return removalResult;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (null == c || c.isEmpty()) {
			clear();
			return true;
		}
		List<V> itemsToRemove = new ArrayList<>();
		for (V v : this) {
			if (!c.contains(v)) {
				itemsToRemove.add(v);
			}
		}
		return removeAll(itemsToRemove);
	}

	@Override
	public int size() {
		return mInner.size();
	}

	@Override
	public Object[] toArray() {
		return mInner.values().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return mInner.values().toArray(a);
	}

	private boolean addKey(K key) {
		if (null != key) {
			return mKeys.add(key);
		}
		return false;
	}

	private boolean removeKey(K key) {
		if (null != key) {
			return mKeys.remove(key);
		}
		return false;
	}

	private boolean isValidIndex(int index) {
		return index >= 0 && index < size();
	}

	protected abstract K getKeyForItem(V item);
}

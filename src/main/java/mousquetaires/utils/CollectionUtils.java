package mousquetaires.utils;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;

import java.util.*;


public class CollectionUtils {

    public static <T> Iterator<T> createIteratorFrom(T firstElement, T[] otherElements) {
        switch (otherElements.length) {
            case 0:
                return createIteratorFrom(firstElement);
            case 1:
                return createIteratorFrom(firstElement, otherElements[0]);
            case 2:
                return createIteratorFrom(firstElement, otherElements[0], otherElements[1]);
            case 3:
                return createIteratorFrom(firstElement, otherElements[0], otherElements[1], otherElements[2]);
            case 4:
                return createIteratorFrom(firstElement, otherElements[0], otherElements[1], otherElements[2], otherElements[3]);
            default:
                List<T> children = List.of(firstElement);
                children.addAll(Arrays.asList(otherElements));
                return createIteratorFrom(children);
        }
    }

    //@SafeVarargs //TODO: check this warning
    public static <T> Iterator<T> createIteratorFrom(T... elements) {
        final int length = elements.length;
        if (length == 0) {
            return Collections.emptyIterator();
        }
        if (length == 1) {
            return Iterators.singletonIterator(elements[0]);
        }
        return Arrays.asList(elements).iterator();
    }

    public static <T> Iterator<T> createIteratorFrom(Iterable<T> elements) {
        return elements.iterator();
    }

    public static <T> Iterator<T> createIteratorFrom(ImmutableList<T> list) {
        return list.listIterator();
    }


    public static <T> ImmutableMap<T, ImmutableSet<T>> buildMapOfSets(Map<T, Set<T>> map) {
        ImmutableMap.Builder<T, ImmutableSet<T>> builder = new ImmutableMap.Builder<>();
        for (Map.Entry<T, Set<T>> pair : map.entrySet()) {
            builder.put(pair.getKey(), ImmutableSet.copyOf(pair.getValue()));
        }
        return builder.build();
    }

    public static <T> T getSingleElement(Set<T> set) {
        Iterator<T> iterator = set.iterator();

        if (!iterator.hasNext()) {
            throw new RuntimeException("Set is empty");
        }

        T element = iterator.next();

        if (iterator.hasNext()) {
            throw new RuntimeException("Set contains more than one item: [" + Joiner.on(",").join(set) + "]");
        }

        return element;
    }
}
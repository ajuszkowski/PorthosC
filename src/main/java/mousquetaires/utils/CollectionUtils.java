package mousquetaires.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


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

}
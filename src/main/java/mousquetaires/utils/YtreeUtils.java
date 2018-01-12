package mousquetaires.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import mousquetaires.languages.ytree.YEntity;
import mousquetaires.languages.ytree.statements.YStatement;

import java.util.*;


public class YtreeUtils {

    public static Iterator<YEntity> createIteratorFrom(ImmutableList<YStatement> elements) {
        final int length = elements.size();
        if (length == 0) {
            return Collections.emptyIterator();
        }
        if (length == 1) {
            return Iterators.singletonIterator(elements.get(0));
        }
        return new ArrayList<YEntity>(elements).iterator();
    }

    public static Iterator<YEntity> createIteratorFrom(YEntity... elements) {
        final int length = elements.length;
        if (length == 0) {
            return Collections.emptyIterator();
        }
        if (length == 1) {
            return Iterators.singletonIterator(elements[0]);
        }
        return Arrays.asList(elements).iterator();
    }

    public static Iterator<YEntity> createIteratorFrom(YEntity firstElement, YEntity[] otherElements) {
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
                List<YEntity> children = List.of(firstElement);
                children.addAll(Arrays.asList(otherElements));
                return createIteratorFrom(children);
        }
    }

    public static Iterator<YEntity> createIteratorFrom(Iterable<YEntity> elements) {
        return elements.iterator();
    }
}
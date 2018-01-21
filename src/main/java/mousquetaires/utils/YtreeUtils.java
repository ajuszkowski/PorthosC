package mousquetaires.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import mousquetaires.languages.syntax.ytree.YEntity;
import mousquetaires.languages.syntax.ytree.expressions.assignments.YAssignee;
import mousquetaires.utils.exceptions.ytree.InvalidLvalueException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class YtreeUtils {

    public static Iterator<? extends YEntity> createIteratorFrom(YEntity firstElement, YEntity[] otherElements) {
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

    public static Iterator<? extends YEntity> createIteratorFrom(YEntity... elements) {
        final int length = elements.length;
        if (length == 0) {
            return Collections.emptyIterator();
        }
        if (length == 1) {
            return Iterators.singletonIterator(elements[0]);
        }
        return Arrays.asList(elements).iterator();
    }

    public static Iterator<? extends YEntity> createIteratorFrom(Iterable<? extends YEntity> elements) {
        return elements.iterator();
    }

    public static Iterator<? extends YEntity> createIteratorFrom(ImmutableList<? extends YEntity> list) {
        return list.listIterator();
    }

}
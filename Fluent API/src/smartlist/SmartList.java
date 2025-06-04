package smartlist;

import smartlist.exeptions.UnexpectedResultTypeException;

import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

/**
 * A fluent interface for manipulating collections with both list and map-like operations.
 * SmartList provides a chainable API for filtering, transforming, grouping, and aggregating data.
 *
 * <p>The implementation can internally represent data in two forms:
 * <ul>
 *   <li>As a List - for linear sequence operations</li>
 *   <li>As a Map - for grouped operations after using groupBy()</li>
 * </ul>
 *
 * <p>Operations automatically adapt to the current internal representation:
 * <pre>
 * // Starts as list, becomes map after groupBy, then list again
 * SmartList.of(data)
 *     .filter(...)    // List operations
 *     .groupBy(...)   // Converts to Map
 *     .having(...)    // Map operations
 *     .map(...)       // Map operations
 *     .toList();      // Converts back to List
 * </pre>
 *
 * @param <E> the type of elements in this collection
 */
public interface SmartList<E> {

    /**
     * Creates a SmartList from an existing List.
     * The input list is not modified - operations return new collections.
     *
     * @param <E> the element type
     * @param list the source list (not null)
     * @return a new SmartList containing the list elements
     * @throws NullPointerException if list is null
     */
    static <E> SmartList<E> of(List<E> list) {
        return new LinkedSmartList<>(list);
    }

    /**
     * Creates a SmartList from varargs elements.
     *
     * @param <E> the element type
     * @param elements the elements to include
     * @return a new SmartList containing the elements
     */
    @SafeVarargs
    static <E> SmartList<E> of(E... elements) {
        return new LinkedSmartList<>(Arrays.asList(elements));
    }

    /**
     * Filters elements based on a predicate.
     * Works on both list and map representations (filters values in map groups).
     *
     * @param predicate the condition to test elements
     * @return new SmartList containing only matching elements
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> filter(Predicate<E> predicate);

    /**
     * Transforms elements using a mapping function.
     * When in map mode, transforms grouped values while maintaining structure.
     *
     * @param <R> the result element type
     * @param mapper the mapping function
     * @return new SmartList with transformed elements
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    <R> SmartList<R> map(Function<E, R> mapper);

    /**
     * Transforms each element to a stream and flattens the results.
     * Works in both list and map modes.
     *
     * @param <R> the result element type
     * @param mapper function that produces a stream for each element
     * @return new SmartList with flattened results
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    <R> SmartList<R> flatMap(Function<E, Stream<R>> mapper);

    /**
     * Selects a property from each element (alias for map).
     * Only works in list mode.
     *
     * @param <R> the result type
     * @param classifier the property selector
     * @return new SmartList containing the selected properties
     * @throws UnexpectedResultTypeException if in map mode
     */
    <R> SmartList<R> select(Function<E, R> classifier);

    /**
     * Returns distinct elements (using equals() for comparison).
     * Preserves structure in map mode (distinct within each group).
     *
     * @return new SmartList with duplicates removed
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> distinct();

    /**
     * Sorts elements using natural ordering.
     * For map mode, sorts both keys and grouped values.
     *
     * @return new sorted SmartList
     * @throws ClassCastException if elements aren't Comparable
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> sorted();

    /**
     * Sorts elements using the specified comparator.
     * For map mode, sorts grouped values while maintaining map structure.
     *
     * @param comparator the ordering to use
     * @return new sorted SmartList
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> sorted(Comparator<E> comparator);

    /**
     * Groups elements by a classifier function.
     * Converts the SmartList from list to map mode.
     *
     * @param <R> the key type for grouping
     * @param classifier the grouping function
     * @return new SmartList in map mode
     * @throws UnexpectedResultTypeException if already in map mode
     */
    <R> SmartList<E> groupBy(Function<E, R> classifier);

    /**
     * Filters groups based on a predicate.
     * Only works in map mode after groupBy().
     *
     * @param groupPredicate tests entire groups
     * @return new SmartList with only matching groups
     * @throws UnexpectedResultTypeException if not in map mode
     */
    SmartList<E> having(Predicate<List<E>> groupPredicate);

    /**
     * Limits the number of elements.
     * For map mode, limits elements within each group.
     *
     * @param maxSize maximum number of elements/groups to keep
     * @return new truncated SmartList
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> limit(int maxSize);

    /**
     * Skips the first n elements.
     * For map mode, skips elements within each group.
     *
     * @param n number of elements to skip
     * @return new SmartList with elements skipped
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> skip(int n);

    /**
     * Takes the first n elements (alias for limit).
     *
     * @param n number of elements to take
     * @return new SmartList with first n elements
     */
    SmartList<E> take(int n);

    /**
     * Takes elements while predicate is true.
     * Only works in list mode.
     *
     * @param predicate the condition to test
     * @return new SmartList with leading matching elements
     * @throws UnexpectedResultTypeException if in map mode
     */
    SmartList<E> takeWhile(Predicate<E> predicate);

    /**
     * Drops elements while predicate is true.
     * Only works in list mode.
     *
     * @param predicate the condition to test
     * @return new SmartList without leading matching elements
     * @throws UnexpectedResultTypeException if in map mode
     */
    SmartList<E> dropWhile(Predicate<E> predicate);

    /**
     * Sorts elements by a Comparable property.
     *
     * @param keyExtractor function to extract sort key
     * @return new sorted SmartList (ascending order)
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> sortBy(Function<E, ? extends Comparable> keyExtractor);

    /**
     * Sorts elements by a Comparable property in descending order.
     *
     * @param keyExtractor function to extract sort key
     * @return new sorted SmartList (descending order)
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> sortByDescending(Function<E, ? extends Comparable> keyExtractor);

    /**
     * Reverses the element order.
     * For map mode, reverses elements within each group.
     *
     * @return new SmartList with reversed order
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> reverse();

    /**
     * Randomly shuffles elements.
     * For map mode, shuffles elements within each group.
     *
     * @return new SmartList with shuffled elements
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    SmartList<E> shuffle();

    /**
     * Finds the first element.
     * For map mode, returns first element from first group.
     *
     * @return Optional containing first element if exists
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    Optional<E> findFirst();

    /**
     * Finds the last element.
     * For map mode, returns first element from last group.
     *
     * @return Optional containing last element if exists
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    Optional<E> findLast();

    /**
     * Finds minimum element using comparator.
     *
     * @param comparator the ordering to use
     * @return Optional containing minimum element if exists
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    Optional<E> min(Comparator<E> comparator);

    /**
     * Finds maximum element using comparator.
     *
     * @param comparator the ordering to use
     * @return Optional containing maximum element if exists
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    Optional<E> max(Comparator<E> comparator);

    /**
     * Counts elements/groups.
     *
     * @return number of elements (list mode) or groups (map mode)
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    long count();

    /**
     * Checks if collection is empty.
     *
     * @return true if no elements/groups exist
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    boolean isEmpty();

    /**
     * Checks if any element matches predicate.
     * For map mode, checks all values in all groups.
     *
     * @param predicate the condition to test
     * @return true if any element matches
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    boolean anyMatch(Predicate<E> predicate);

    /**
     * Checks if all elements match predicate.
     * For map mode, checks all values in all groups.
     *
     * @param predicate the condition to test
     * @return true if all elements match
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    boolean allMatch(Predicate<E> predicate);

    /**
     * Checks if no elements match predicate.
     * For map mode, checks all values in all groups.
     *
     * @param predicate the condition to test
     * @return true if no elements match
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    boolean noneMatch(Predicate<E> predicate);

    /**
     * Converts to a List.
     * Only works in list mode.
     *
     * @return the underlying List
     * @throws UnexpectedResultTypeException if in map mode
     */
    List<Object> toList();

    /**
     * Converts to a Map.
     * Only works in map mode.
     *
     * @param <R> the key type of the map
     * @return the underlying Map
     * @throws UnexpectedResultTypeException if in list mode
     */
    <R> Map<R, List<E>> toMap();

    /**
     * Calculates average of numeric elements.
     * Elements must be instances of Number.
     *
     * @return OptionalDouble with average if elements exist
     * @throws ClassCastException if elements aren't Numbers
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    OptionalDouble avg();

    /**
     * Calculates sum of numeric elements.
     * Elements must be instances of Number.
     *
     * @return OptionalLong with sum if elements exist
     * @throws ClassCastException if elements aren't Numbers
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    OptionalLong sum();

    /**
     * Calculates statistics for numeric elements.
     * Elements must be instances of Number.
     *
     * @return IntSummaryStatistics with count, sum, min, max, average
     * @throws ClassCastException if elements aren't Numbers
     * @throws UnexpectedResultTypeException if current state doesn't support this operation
     */
    IntSummaryStatistics stats();
}
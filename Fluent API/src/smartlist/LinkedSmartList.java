package smartlist;

import smartlist.exeptions.UnexpectedResultTypeException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedSmartList<E> implements SmartList<E> {
    private enum DataType { LIST, MAP }
    private final DataType dataType;

    private final List<E> listData;
    private final Map<Object, List<E>> mapData;

    public LinkedSmartList(List<E> list) {
        if (list == null) throw new NullPointerException();
        this.listData = list;
        this.mapData = null;
        this.dataType = DataType.LIST;
    }

    public LinkedSmartList(Map<Object, List<E>> map) {
        if (map == null) throw new NullPointerException();
        this.mapData = map;
        this.listData = null;
        this.dataType = DataType.MAP;
    }

    @Override
    public SmartList<E> filter(Predicate<E> predicate) {
        if (dataType == DataType.LIST) {
            List<E> filtered = new java.util.ArrayList<>();
            assert listData != null;
            for (E item : listData) {
                if (predicate.test(item))
                    filtered.add(item);
            }
            return new LinkedSmartList<>(filtered);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public <R> SmartList<E> groupBy(Function<E, R> classifier) {
        if (dataType != DataType.LIST) throw new UnexpectedResultTypeException();
        Map<Object, List<E>> groupedMap = new HashMap<>();
        assert listData != null;
        for (E item : listData) {
            R key = classifier.apply(item);
            groupedMap.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return new LinkedSmartList<>(groupedMap);
    }

    @Override
    public SmartList<E> having(Predicate<List<E>> groupPredicate) {
        if (dataType != DataType.MAP) throw new UnexpectedResultTypeException();
        Map<Object, List<E>> filteredMap = new HashMap<>();
        assert mapData != null;
        for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
            List<E> group = entry.getValue();
            if (groupPredicate.test(group)) filteredMap.put(entry.getKey(), group);
        }
        return new LinkedSmartList<>(filteredMap);
    }

    @Override
    public <R> SmartList<R> map(Function<E, R> mapper) {
        if (dataType != DataType.MAP) throw new UnexpectedResultTypeException();
        Map<Object, List<R>> transformedMap = new HashMap<>();
        assert mapData != null;
        for (Map.Entry<?, List<E>> entry : mapData.entrySet()) {
            Object key = entry.getKey();
            List<R> transformedValues = new ArrayList<>();
            for (E item : entry.getValue()) {
                R transformedValue = mapper.apply(item);
                transformedValues.add(transformedValue);
            }
            transformedMap.put(key, transformedValues);
        }
        return new LinkedSmartList<>(transformedMap);
    }

    @Override
    public <R> SmartList<R> flatMap(Function<E, Stream<R>> mapper) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            List<R> result = listData.stream()
                    .flatMap(mapper)
                    .collect(Collectors.toList());
            return new LinkedSmartList<>(result);
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            List<R> result = mapData.values().stream()
                    .flatMap(List::stream)
                    .flatMap(mapper)
                    .collect(Collectors.toList());
            return new LinkedSmartList<>(result);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public <R> SmartList<R> select(Function<E, R> classifier) {
        if (dataType != DataType.LIST) throw new UnexpectedResultTypeException();
        assert listData != null;
        List<R> items = new ArrayList<>();
        for (E item : listData) {
            R transformedValue = classifier.apply(item);
            items.add(transformedValue);
        }
        return new LinkedSmartList<>(items);
    }

    @Override
    public SmartList<E> distinct() {
        if (dataType == DataType.LIST) {
            List<E> list = new ArrayList<>();
            assert listData != null;
            for (E item : listData) {
                if (list.contains(item)) continue;
                list.add(item);
            }
            return new LinkedSmartList<>(list);
        } else if (dataType == DataType.MAP) {
            Map<Object, List<E>> distinctMap = new HashMap<>();
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                List<E> distinctList = new ArrayList<>();
                for (E item : entry.getValue()) {
                    if (distinctList.contains(item)) continue;
                    distinctList.add(item);
                }
                distinctMap.put(entry.getKey(), distinctList);
            }
            return new LinkedSmartList<>(distinctMap);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public SmartList<E> sorted() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            List<E> copy = new ArrayList<>(listData);
            copy.sort(null);
            return new LinkedSmartList<>(copy);
        } else if (dataType == DataType.MAP) {
            TreeMap<Object, List<E>> sorted = new TreeMap<>(mapData);
            return new LinkedSmartList<>(sorted);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public SmartList<E> sorted(Comparator<E> comparator) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            List<E> copy = new ArrayList<>(listData);
            copy.sort(comparator);
            return new LinkedSmartList<>(copy);
        } else if (dataType == DataType.MAP) {
            Map<Object, List<E>> sortedMap = new HashMap<>();
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                List<E> copy = new ArrayList<>(entry.getValue());
                copy.sort(comparator);
                sortedMap.put(entry.getKey(), copy);
            }
            return new LinkedSmartList<>(sortedMap);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public SmartList<E> limit(int amount) {
        if (dataType == DataType.LIST) {
            List<E> limitedList = new ArrayList<>();
            int count = 0;
            assert listData != null;
            for (E item : listData) {
                if (count >= amount) break;
                limitedList.add(item);
                count++;
            }
            return new LinkedSmartList<>(limitedList);
        } else if (dataType == DataType.MAP) {
            Map<Object, List<E>> limitedMap = new HashMap<>();
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                List<E> limitedGroup = new ArrayList<>();
                int count = 0;
                for (E item : entry.getValue()) {
                    if (count >= amount) break;
                    limitedGroup.add(item);
                    count++;
                }
                limitedMap.put(entry.getKey(), limitedGroup);
            }
            return new LinkedSmartList<>(limitedMap);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public SmartList<E> skip(int n) {
        if (dataType == DataType.LIST) {
            List<E> items = new ArrayList<>();
            int count = 0;
            assert listData != null;
            for (E item : listData) {
                count++;
                if (count < n + 1) continue;
                items.add(item);
            }
            return new LinkedSmartList<>(items);
        } else if (dataType == DataType.MAP) {
            Map<Object, List<E>> limitedMap = new HashMap<>();
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                List<E> limitedGroup = new ArrayList<>();
                int count = 0;
                for (E item : entry.getValue()) {
                    count++;
                    if (count < n + 1) continue;
                    limitedGroup.add(item);
                }
                limitedMap.put(entry.getKey(), limitedGroup);
            }
            return new LinkedSmartList<>(limitedMap);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public SmartList<E> take(int n) {
        return this.limit(n);
    }

    @Override
    public SmartList<E> takeWhile(Predicate<E> predicate) {
        if (dataType != DataType.LIST) throw new UnexpectedResultTypeException();
        List<E> result = new ArrayList<>();
        assert listData != null;
        for (E item : listData) {
            if (!predicate.test(item)) break;
            result.add(item);
        }
        return new LinkedSmartList<>(result);
    }

    @Override
    public SmartList<E> dropWhile(Predicate<E> predicate) {
        if (dataType != DataType.LIST) throw new UnexpectedResultTypeException();
        List<E> result = new ArrayList<>();
        boolean drop = true;
        assert listData != null;
        for (E item : listData) {
            if (drop) {
                if (!predicate.test(item)) {
                    drop = false;
                    result.add(item);
                }
            } else result.add(item);
        }
        return new LinkedSmartList<>(result);
    }

    @Override
    public SmartList<E> sortBy(Function<E, ? extends Comparable> keyExtractor) {
        return this.sorted(Comparator.comparing(keyExtractor));
    }

    @Override
    public SmartList<E> sortByDescending(Function<E, ? extends Comparable> keyExtractor) {
        return this.sorted(Comparator.comparing(keyExtractor).reversed());
    }

    @Override
    public SmartList<E> reverse() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            List<E> copy = new ArrayList<>(listData);
            Collections.reverse(copy);
            return new LinkedSmartList<>(copy);
        } else if (dataType == DataType.MAP) {
            Map<Object, List<E>> reversedMap = new HashMap<>();
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                List<E> copy = new ArrayList<>(entry.getValue());
                Collections.reverse(copy);
                reversedMap.put(entry.getKey(), copy);
            }
            return new LinkedSmartList<>(reversedMap);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public SmartList<E> shuffle() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            List<E> copy = new ArrayList<>(listData);
            Collections.shuffle(copy);
            return new LinkedSmartList<>(copy);
        } else if (dataType == DataType.MAP) {
            Map<Object, List<E>> shuffleMap = new HashMap<>();
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                List<E> copy = new ArrayList<>(entry.getValue());
                Collections.shuffle(copy);
                shuffleMap.put(entry.getKey(), copy);
            }
            return new LinkedSmartList<>(shuffleMap);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public Optional<E> findFirst() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            for (E item : listData) {
                if (item == null) continue;
                return Optional.of(item);
            }
            return Optional.empty();
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                if (entry == null) continue;
                return entry.getValue().stream()
                        .findFirst();
            }
        } else throw new UnexpectedResultTypeException();
        return Optional.empty();
    }

    @Override
    public Optional<E> findLast() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            int size = listData.size();
            int count = 1;
            for (E item : listData) {
                if (count == size) return Optional.of(item);
                count++;
            }
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            int size = mapData.size();
            int count = 1;
            for (Map.Entry<Object, List<E>> entry : mapData.entrySet()) {
                if (count == size) return entry.getValue().stream()
                        .findFirst();
                count++;
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<E> min(Comparator<E> comparator) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            if (listData.isEmpty()) return Optional.empty();
            return listData.stream().min(comparator);
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .min(comparator);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public Optional<E> max(Comparator<E> comparator) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            if (listData.isEmpty()) return Optional.empty();
            return listData.stream().max(comparator);
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .max(comparator);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public long count() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.size();
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.size();
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public boolean isEmpty() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.isEmpty();
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.isEmpty();
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public boolean anyMatch(Predicate<E> predicate) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.stream().anyMatch(predicate);
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .anyMatch(predicate);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public boolean allMatch(Predicate<E> predicate) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.stream().allMatch(predicate);
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .allMatch(predicate);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public boolean noneMatch(Predicate<E> predicate) {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.stream().noneMatch(predicate);
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .noneMatch(predicate);
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Object> toList() {
        if (dataType == DataType.LIST) return (List<Object>) listData;
        else throw new UnexpectedResultTypeException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> Map<R, List<E>> toMap() {
        if (dataType == DataType.MAP) return (Map<R, List<E>>) mapData;
        else throw new UnexpectedResultTypeException();
    }

    @Override
    public OptionalDouble avg() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.stream()
                    .filter(Objects::nonNull)
                    .mapToDouble(e -> ((Number) e).doubleValue())
                    .average();
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .mapToDouble(e -> ((Number) e).doubleValue())
                    .average();
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public OptionalLong sum() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return OptionalLong.of(
                    listData.stream()
                            .filter(Objects::nonNull)
                            .mapToLong(e -> ((Number) e).longValue())
                            .sum()
            );
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return OptionalLong.of(
                    mapData.values().stream()
                            .flatMap(List::stream)
                            .filter(Objects::nonNull)
                            .mapToLong(e -> ((Number) e).longValue())
                            .sum()
            );
        } else throw new UnexpectedResultTypeException();
    }

    @Override
    public IntSummaryStatistics stats() {
        if (dataType == DataType.LIST) {
            assert listData != null;
            return listData.stream()
                    .filter(Objects::nonNull)
                    .mapToInt(e -> ((Number) e).intValue())
                    .summaryStatistics();
        } else if (dataType == DataType.MAP) {
            assert mapData != null;
            return mapData.values().stream()
                    .flatMap(List::stream)
                    .filter(Objects::nonNull)
                    .mapToInt(e -> ((Number) e).intValue())
                    .summaryStatistics();
        } else throw new UnexpectedResultTypeException();
    }
}

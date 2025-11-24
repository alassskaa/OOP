package ru.nsu.sidorenko;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Класс для работы с хэш-таблицами.
 * Поддерживаются различные функции для работы с таблицами: добавление
 * элемента, удаление элемента, сравнение таблиц, итерирование по парам ключ-значение
 * и другие ф-ии.
 * Обработка коллизий реализована при помощи цепочек: если два элемента
 * (ключ - значение) попадают в один бакет (корзину) то они помещаются
 * следующим элементом в массив, соответствующий этому бакету.
 *
 * @param <K> - тип ключа.
 * @param <V> - тип значения.
 */
public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16; //начальный размер
    private static final float DEFAULT_LOAD_FACTOR = 0.75f; //коэффициент загрузки
    private static final float SHRINK_LOAD_FACTOR = 0.25f; //коэффициент сжатия

    private Node<K, V>[] table;
    private int size;
    private int capacity;
    private final float loadFactor;
    private int thresholdGrow; //порог роста
    private int thresholdShrink; //порог сжатия

    /**
     * Конструктор, заполняющий основные поля класса для работы с таблицами.
     */
    @SuppressWarnings("unchecked")
    public HashTable() {
        this.capacity = DEFAULT_CAPACITY;
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        this.table = (Node<K, V>[]) new Node[capacity];
        this.thresholdGrow = (int) (capacity * loadFactor);
        this.thresholdShrink = (int) (capacity * SHRINK_LOAD_FACTOR);
        this.size = 0;
    }

    /**
     * Метод, возвращающий фактическое количество пар ключ-значение,
     * находящихся в таблице на данный момент.
     *
     * @return количество пар.
     */
    public int size() {
        return size;
    }

    /**
     * Проверка таблицы на пустоту.
     *
     * @return true, если таблица пуста, false, если в таблице есть данные.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Проверка, содержится ли ключ key в таблице на данный момент.
     *
     * @param key - искомый ключ.
     * @return true, если ключ есть в таблице, false, если такого ключа нет.
     */
    public boolean containsKey(K key) {
        return findNode(key) != null;
    }

    /**
     * Метод, позволяющий найти значение, связанное с ключом. Если ключ не найден, то
     * вернется null.
     *
     * @param key - ключ, по которому ищется значение.
     * @return вернет значение или null.
     */
    public V get(K key) {
        Node<K, V> n = findNode(key);
        return n == null ? null : n.value;
    }

    /**
     * Метод для добавления нового значения по ключу. Если оно уже существует,
     * то обновится уже существующее.
     *
     * @param key - ключ.
     * @param value - значение.
     * @return вернет старый элемент при обновлении и null при добавлении нового узла цепочки.
     */
    public V put(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");
        ensureCapacityForInsert();

        int index = index(key, capacity);

        Node<K, V> current = table[index];
        while (current != null) {
            if (Objects.equals(current.key, key)) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }

        Node<K, V> newNode = new Node<>(key, value, table[index]);
        table[index] = newNode;
        size++;
        return null;
    }

    /**
     * Метод для обновления значения по ключу.
     *
     * @param key - ключ.
     * @param value- значение.
     * @return вернет старое значение.
     */
    public V update(K key, V value) {
        Objects.requireNonNull(key, "key must not be null");
        Node<K, V> n = findNode(key);
        if (n == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }
        V old = n.value;
        n.value = value;
        return old;
    }

    /**
     * Метод для удаления значения по ключу.
     *
     * @param key - ключ, по которому удаляем значение.
     * @return вернет удаленный элемент или null, если значение по ключу не найдено.
     */
    public V remove(K key) {
        Objects.requireNonNull(key, "key must not be null");
        int idx = index(key, capacity);
        Node<K, V> prev = null;
        Node<K, V> cur = table[idx];
        while (cur != null) {
            if (Objects.equals(cur.key, key)) {
                if (prev == null) {
                    table[idx] = cur.next;
                } else {
                    prev.next = cur.next;
                }
                size--;
                maybeShrink();
                return cur.value;
            }
            prev = cur;
            cur = cur.next;
        }
        return null;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new TableIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;
        for (Entry<K, V> e : this) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(e.toString());
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof HashTable)) {
            return false;
        }
        HashTable<K, ?> other = (HashTable<K, ?>) o;

        if (this.size != other.size) {
            return false;
        }
        for (Entry<K, V> e : this) {
            Object ov = other.get(e.getKey());
            if (!Objects.equals(e.getValue(), ov)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Entry<K, V> e : this) {
            h += Objects.hashCode(e.getKey()) ^ Objects.hashCode(e.getValue()); //исключающее ИЛИ
        }
        return h;
    }

    /**
     * Метод для поиска пары с заданным ключом в таблице.
     *
     * @param key - заданный ключ.
     * @return вернет пару или null, если пара не была найдена.
     */
    private Node<K, V> findNode(K key) {
        Objects.requireNonNull(key, "key must not be null");
        int idx = index(key, capacity);
        Node<K, V> n = table[idx];
        while (n != null) {
            if (Objects.equals(n.key, key)) {
                return n;
            }
            n = n.next;
        }
        return null;
    }

    /**
     * Метод для вычисления индекса пары в таблице, используя хэш-код ключа.
     *
     * @param key - ключ.
     * @param cap - capacity массива (максимальное количество цепочек в таблице).
     * @return вернет индекс пары.
     */
    private static int index(Object key, int cap) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return (h & 0x7fffffff) % cap;
    }

    /**
     * Проверка таблицы на вместимость для нового узла.
     */
    private void ensureCapacityForInsert() {
        if (size + 1 > thresholdGrow) {
            resize(capacity << 1);
        }
    }

    /**
     * Проверка наличия необходимости уменьшить вместимость таблицы.
     */
    private void maybeShrink() {
        if (capacity > DEFAULT_CAPACITY && size < thresholdShrink) {
            resize(capacity >> 1);
        }
    }

    /**
     * Метод для изменения вместимость таблицы. Индексы цепочек пересчитываются под
     * новый размер таблицы.
     *
     * @param newCap - новая вместимость.
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCap) {
        Node<K, V>[] old = table;
        Node<K, V>[] nt = (Node<K, V>[]) new Node[newCap];
        for (Node<K, V> head : old) {
            for (Node<K, V> n = head; n != null; ) {
                Node<K, V> next = n.next;
                int idx = index(n.key, newCap);
                n.next = nt[idx];
                nt[idx] = n;
                n = next;
            }
        }
        table = nt;
        capacity = newCap;
        thresholdGrow = (int) (capacity * loadFactor);
        thresholdShrink = (int) (capacity * SHRINK_LOAD_FACTOR);
    }
    /**
     *
     * Класс для создания элемента цепочки, хранящейся по ключу.
     *
     * @param <K> - тип ключа.
     * @param <V> - тип значения.
     */
    private static final class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;
        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
    /**
     * Внутренний служебный класс для перебора элементов таблицы. Не вынесен в отдельный
     * файл, чтобы иметь доступ к приватным полям класса HashTable.
     */
    private final class TableIterator implements Iterator<Entry<K, V>> {
        private int chainIndex = 0; //текущая цепочка
        private Node<K, V> currentNode = null; //текущий элемент в цепочке

        /**
         * Метод, который "двигает" итератор вперед, делая шаг в цикле.
         * Переходит к следующей паре в цепочку или к следующей цепочке, если
         * в данной нет следующей пары.
         * Изменяет значение currentNode на null, если не найден следующий элемент.
         */
        private void toNext() {
            if (currentNode != null) {
                currentNode = currentNode.next;
                if (currentNode != null) {
                    return;
                }
                chainIndex++;
            }
            while (chainIndex < capacity && table[chainIndex] == null) {
                chainIndex++;
            }
            currentNode = (chainIndex < capacity) ? table[chainIndex] : null;
        }

        @Override
        public boolean hasNext() {
            if (currentNode == null) {
                int savedBucket = chainIndex;
                Node<K, V> savedNode = currentNode;
                toNext();
                boolean has = (currentNode != null);
                chainIndex = savedBucket;
                currentNode = savedNode;
                return has;
            }
            return true;
        }

        @Override
        public Entry<K, V> next() {
            if (currentNode == null) {
                toNext();
            }
            if (currentNode == null) {
                throw new NoSuchElementException();
            }
            Entry<K, V> e = new Entry<>(currentNode.key, currentNode.value);
            toNext();
            return e;
        }
    }
}

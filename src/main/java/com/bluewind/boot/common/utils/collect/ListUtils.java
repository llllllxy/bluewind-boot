package com.bluewind.boot.common.utils.collect;

import com.bluewind.boot.common.utils.lang.StringUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * List工具类
 *
 * @author liuxingyu01
 * @date 2021-06-29-19:46
 * @description List工具类
 **/
public class ListUtils extends org.apache.commons.collections.ListUtils {

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串数组
     * @return 包含返回true
     */
    public static boolean inString(String str, List<String> strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equals(StringUtils.trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 新建一个ArrayList
     * @return 空的ArrayList
     * @param <E> 泛型类型
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
     * 新建一个ArrayList
     * @param elements 初始化元素 ArrayList对象
     * @return 新的ArrayList
     * @param <E> 泛型类型
     */
    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        ArrayList<E> list = new ArrayList<E>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

    /**
     * 新建一个ArrayList
     * @param elements
     * @return
     * @param <E>
     */
    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
        return (elements instanceof Collection) ? new ArrayList<E>(cast(elements))
                : newArrayList(elements.iterator());
    }

    /**
     * 新建一个ArrayList
     * @param elements
     * @return
     * @param <E>
     */
    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        addAll(list, elements);
        return list;
    }

    /**
     * 新建一个LinkedList
     * @return 空的LinkedList
     * @param <E> 泛型类型
     */
    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList<E>();
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> elements) {
        LinkedList<E> list = newLinkedList();
        addAll(list, elements);
        return list;
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList<E>();
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> elements) {
        Collection<? extends E> elementsCollection = (elements instanceof Collection)
                ? cast(elements) : newArrayList(elements);
        return new CopyOnWriteArrayList<E>(elementsCollection);
    }

    private static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }

    private static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator) {
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= addTo.add(iterator.next());
        }
        return wasModified;
    }

    public static <T> boolean addAll(Collection<T> addTo, Iterable<? extends T> elementsToAdd) {
        if (elementsToAdd instanceof Collection) {
            Collection<? extends T> c = cast(elementsToAdd);
            return addTo.addAll(c);
        }
        return addAll(addTo, elementsToAdd.iterator());
    }


    /**
     * 数组转list集合
     * @param array 数组
     * @param <T> 泛型对象
     * @return  ArrayList
     */
    public static <T> List<T> arrayToList(T[] array) {
        List<T> resultList = new ArrayList<>(array.length);
        Collections.addAll(resultList,array);
        return resultList;
    }


    /**
     * 提取集合中的对象的两个属性(通过Getter函数), 组合成Map.
     *
     * @param collection        来源集合.
     * @param keyPropertyName   要提取为Map中的Key值的属性名.
     * @param valuePropertyName 要提取为Map中的Value值的属性名.
     */
    @SuppressWarnings("unchecked")
    public static Map extractToMap(final Collection collection, final String keyPropertyName,
                                   final String valuePropertyName) {
        Map map = new HashMap(collection.size());
        try {
            for (Object obj : collection) {
                map.put(PropertyUtils.getProperty(obj, keyPropertyName),
                        PropertyUtils.getProperty(obj, valuePropertyName));
            }
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked("", e);
        }
        return map;
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> extractToList(final Collection collection, final String propertyName) {
        if (collection == null) {
            return newArrayList();
        }
        List list = new ArrayList(collection.size());
        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked("", e);
        }
        return list;
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成List.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @param prefix       符合前缀的信息(为空则忽略前缀)
     * @param isNotBlank   仅包含不为空值(空字符串也排除)
     */
    public static List<String> extractToList(final Collection collection, final String propertyName,
                                             final String prefix, final boolean isNotBlank) {
        List<String> list = new ArrayList<String>(collection.size());
        try {
            for (Object obj : collection) {
                String value = (String) PropertyUtils.getProperty(obj, propertyName);
                if (StringUtils.isBlank(prefix) || StringUtils.startsWith(value, prefix)) {
                    if (isNotBlank) {
                        if (StringUtils.isNotBlank(value)) {
                            list.add(value);
                        }
                    } else {
                        list.add(value);
                    }
                }
            }
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked("", e);
        }
        return list;
    }

    /**
     * 提取集合中的对象的一个属性(通过Getter函数), 组合成由分割符分隔的字符串.
     *
     * @param collection   来源集合.
     * @param propertyName 要提取的属性名.
     * @param separator    分隔符.
     */
    public static String extractToString(final Collection collection, final String propertyName, final String separator) {
        List list = extractToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 中间以 separator分隔。
     */
    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * 转换Collection所有元素(通过toString())为String, 每个元素的前面加入prefix，后面加入postfix，如<div>mymessage</div>。
     */
    public static String convertToString(final Collection collection, final String prefix, final String postfix) {
        StringBuilder builder = new StringBuilder();
        for (Object o : collection) {
            builder.append(prefix).append(o).append(postfix);
        }
        return builder.toString();
    }

    /**
     * 判断是否为空.
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     * 判断是否为不为空.
     */
    public static boolean isNotEmpty(Collection collection) {
        return !(isEmpty(collection));
    }

    /**
     * 取得Collection的第一个元素，如果collection为空返回null.
     */
    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        return collection.iterator().next();
    }

    /**
     * 获取Collection的最后一个元素 ，如果collection为空返回null.
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        //当类型为List时，直接取得最后一个元素 。
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }
        //其他类型通过iterator滚动到最后一个元素.
        Iterator<T> iterator = collection.iterator();
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * 返回a+b的新List.
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * 返回a-b的新List.
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>(a);
        for (T element : b) {
            list.remove(element);
        }
        return list;
    }

    /**
     * 返回a与b的交集的新List.
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();
        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }


    /**
     * 获取两个ArrayList的交集
     *
     * @param firstArrayList  第一个ArrayList
     * @param secondArrayList 第二个ArrayList
     * @return resultList 交集ArrayList
     */
    public static List<String> intersectionList(List<String> firstArrayList, List<String> secondArrayList) {
        // 大集合用linkedlist
        LinkedList<String> result = new LinkedList<String>(firstArrayList);
        // 小集合用hashset
        HashSet<String> othHash = new HashSet<String>(secondArrayList);
        // 采用Iterator迭代器进行数据的操作
        Iterator<String> iter = result.iterator();
        while (iter.hasNext()) {
            if (!othHash.contains(iter.next())) {
                iter.remove();
            }
        }
        return new ArrayList<String>(result);
    }


    /**
     * 列表分页方法
     *
     * @param list      源数据
     * @param pageNo    当前页码
     * @param pageSize  每页显示条数
     * @param totalPage 总页码数
     * @author ThinkGem
     */
    private static <T> List<T> getPageList(List<T> list, int pageNo, int pageSize, int totalPage) {
        int fromIndex = 0; // 从哪里开始截取
        int toIndex = 0; // 截取几个
        if (list == null || list.size() == 0) {
            return new ArrayList<T>();
        }
        // 当前页小于或等于总页数时执行
        if (pageNo <= totalPage && pageNo != 0) {
            fromIndex = (pageNo - 1) * pageSize;
            if (pageNo == totalPage) {
                toIndex = list.size();
            } else {
                toIndex = pageNo * pageSize;
            }
        }
        return list.subList(fromIndex, toIndex);
    }


    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(String msg, Exception e) {
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
                || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(msg, e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(msg, ((InvocationTargetException) e).getTargetException());
        }
        return new RuntimeException(msg, e);
    }


    /**
     * 拆分集合(拆分一个大的List为多个小的List)
     *
     * @param <T>           泛型对象
     * @param resList       需要拆分的集合
     * @param subListLength 每个子集合的元素个数
     * @return 返回拆分后的各个集合组成的列表
     **/
    public static <T> List<List<T>> split(List<T> resList, int subListLength) {
        if (CollectionUtils.isEmpty(resList) || subListLength <= 0) {
            return newArrayList();
        }
        List<List<T>> ret = newArrayList();
        int size = resList.size();
        if (size <= subListLength) {
            // 数据量不足 subListLength 指定的大小
            ret.add(resList);
        } else {
            int pre = size / subListLength;
            int last = size % subListLength;
            // 前面pre个集合，每个大小都是 subListLength 个元素
            for (int i = 0; i < pre; i++) {
                List<T> itemList = newArrayList();
                for (int j = 0; j < subListLength; j++) {
                    itemList.add(resList.get(i * subListLength + j));
                }
                ret.add(itemList);
            }
            // last的进行处理
            if (last > 0) {
                List<T> itemList = newArrayList();
                for (int i = 0; i < last; i++) {
                    itemList.add(resList.get(pre * subListLength + i));
                }
                ret.add(itemList);
            }
        }
        return ret;
    }


    /**
     * 测试
     * @param args 参数
     */
    public static void main(String[] args) {
        String[] array = {"a","b","c"};
        System.out.println(arrayToList(array));
    }
}

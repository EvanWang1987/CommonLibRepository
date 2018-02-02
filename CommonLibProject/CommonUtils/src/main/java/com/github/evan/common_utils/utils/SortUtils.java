package com.github.evan.common_utils.utils;

import com.github.evan.common_utils.ui.view.LogCatView;

import java.util.Arrays;

/**
 * Created by Evan on 2018/2/1.
 */
public class SortUtils {
    public enum OrderBy {
        Ascending, Descending;
    }

    /**
     * 判断数组是否有序
     *
     * @param array
     * @param orderBy
     * @return
     */
    public static boolean isSorted(int[] array, OrderBy orderBy) {
        if (null == array || array.length == 0 || array.length == 1) {
            return false;
        }

        boolean isSorted = true;
        int lastIndex = 0;
        for (int i = 1, length = array.length; i < length; i++) {
            int currElement = array[i];
            int lastElement = array[lastIndex];
            if (orderBy == OrderBy.Ascending && currElement < lastElement) {
                isSorted = false;
                break;
            } else if (orderBy == OrderBy.Descending && currElement > lastElement) {
                isSorted = false;
                break;
            }
            lastIndex = i;
        }

        return isSorted;
    }

    /**
     * 折半查找
     *
     * 时间复杂度 ---> O（log2n）
     * 空间复杂度 ---> 0
     *
     *
     * @param array
     * @param dstElement
     * @param orderBy
     * @param withLog
     * @param logCatView
     * @return
     */
    public static int binarySearch(int[] array, int dstElement, OrderBy orderBy, boolean withLog, LogCatView logCatView){
        if(withLog && null == logCatView)
            withLog = false;

        if(null == array || array.length == 0){
            if(withLog){
                logCatView.addLog("空数组");
            }
            return -1;
        }

        if(!isSorted(array, orderBy)){
            if(withLog){
                logCatView.addLog("数组无序");
            }
            return -1;
        }

        int targetIndex = -1;
        int startIndex = 0;
        int endIndex = array.length - 1;
        int middleIndex = (startIndex + endIndex) / 2;
        long startTime = System.currentTimeMillis();
        long startNanoTime = System.nanoTime();
        if(withLog){
            logCatView.addLog("开始折半查找");
            logCatView.addLog("目标数组: " + Arrays.toString(array));
            logCatView.addLog("目标元素: " + dstElement);
        }
        while (endIndex >= startIndex){
            long startTnTime = System.currentTimeMillis();
            long startTnNanoTime = System.nanoTime();
            int middleElement = array[middleIndex];
            if(middleElement == dstElement){
                targetIndex = middleIndex;
                long endTnTime = System.currentTimeMillis();
                long endTnNanoTime = System.nanoTime();
                if(withLog){
                    logCatView.addLog("进行一次n操作，T(n): " + DateUtil.duration2String(endTnTime - startTnTime, endTnNanoTime - startTnNanoTime));
                }
                break;
            }else if(orderBy == OrderBy.Ascending && middleElement > dstElement){
                endIndex = middleIndex - 1;
            }else if(orderBy == OrderBy.Ascending && middleElement < dstElement){
                startIndex = middleIndex + 1;
            }else if(orderBy == OrderBy.Descending && middleElement > dstElement){
                startIndex = middleIndex + 1;
            }else if(orderBy == OrderBy.Descending && middleElement < dstElement){
                endIndex = middleIndex - 1;
            }else{
                break;
            }
            middleIndex = (startIndex + endIndex) / 2;
            long endTnTime = System.currentTimeMillis();
            long endTnNanoTime = System.nanoTime();
            if(withLog){
                logCatView.addLog("进行一次n操作，T(n): " + DateUtil.duration2String(endTnTime - startTnTime, endTnNanoTime - startTnNanoTime));
            }
        }
        long endTime = System.currentTimeMillis();
        long endNanoTime = System.nanoTime();
        if(withLog){
            logCatView.addLog("折半查找完毕");
            logCatView.addLog("耗时: " + DateUtil.duration2String(endTime - startTime, endNanoTime - startNanoTime));
        }
        return targetIndex;
    }

    /**
     * 选择排序
     * <p>
     * 空间复杂度 ---> 0
     * 时间复杂度 ---> O(n^2)
     *
     * @param array
     * @return
     */
    public static int[] selectSort(int[] array, OrderBy orderBy, boolean withLog, LogCatView logCatView) {
        if (withLog && null == logCatView)
            withLog = false;

        if (null == array || array.length == 0) {
            if (withLog) {
                logCatView.addLog("目标数组为空");
            }
            return array;
        }

        if (withLog) {
            String beforeArray = Arrays.toString(array);
            logCatView.addLog("进行选择排序，排序前: " + beforeArray);
        }
        long startTime = System.currentTimeMillis();
        long startNanoTime = System.nanoTime();
        for (int i = 0, length = array.length - 1; i < length; i++) {
            if (withLog) {
                logCatView.addLog("开始从index为" + i + "的元素向后进行比较");
            }
            for (int j = i + 1, innerLength = array.length; j < innerLength; j++) {
                long startTn = System.nanoTime();
                if (orderBy == OrderBy.Ascending) {
                    if (array[i] > array[j]) {
                        int temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                } else {
                    if (array[i] < array[j]) {
                        int temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
                long endTn = System.nanoTime();
                String tNString = DateUtil.duration2String(-1, endTn - startTn);
                if (withLog) {
                    logCatView.addLog("进行一次n操作，T(n): " + tNString);
                }
            }
        }
        long endTime = System.currentTimeMillis();
        long endNanoTime = System.nanoTime();

        if (withLog) {
            String s = DateUtil.duration2String(endTime - startTime, endNanoTime - startNanoTime);
            logCatView.addLog("选择排序完成");
            logCatView.addLog("选择排序消耗: " + s);
            logCatView.addLog("排序后: " + Arrays.toString(array));
        }
        return array;
    }

    /**
     *
     * 冒泡排序
     *
     * 空间复杂度 ---> 0
     * 时间复杂度 ---> O(n^2)
     *
     * @param array
     * @param orderBy
     * @param withLog
     * @param logCatView
     * @return
     */
    public static int[] bubbleSort(int[] array, OrderBy orderBy, boolean withLog, LogCatView logCatView) {
        if (withLog && null == logCatView) {
            withLog = false;
        }

        if (null == array || array.length == 0) {
            if (withLog) {
                logCatView.addLog("目标数组为空");
                return array;
            }
        }

        long startTime = System.currentTimeMillis();
        long startNanoTime = System.nanoTime();
        if(withLog){
            logCatView.addLog("开始冒泡排序，排序前: " + Arrays.toString(array));
        }

        for (int i = 0, length = array.length - 1; i < length; i++) {
            if(withLog){
                logCatView.addLog("第" + (i + 1) + "轮n操作");
            }
            for (int j = 0, innerLength = array.length - i - 1; j < innerLength; j++) {
                long startTnTime = System.currentTimeMillis();
                long startTnNanoTime = System.nanoTime();
                if (orderBy == OrderBy.Ascending) {
                    if(array[j] > array[j + 1]){
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                } else {
                    if(array[j] < array[j + 1]){
                        int temp = array[j];
                        array[j] = array[j + 1];
                        array[j + 1] = temp;
                    }
                }
                long endTnTime = System.currentTimeMillis();
                long endTnNanoTime = System.nanoTime();
                if(withLog){
                    logCatView.addLog("进行一次n操作，T(n): " + DateUtil.duration2String(endTnTime - startTnTime, endTnNanoTime - startTnNanoTime));
                }
            }
        }

        long endTime = System.currentTimeMillis();
        long endNanoTime = System.nanoTime();

        if(withLog){
            String timeString = DateUtil.duration2String(endTime - startTime, endNanoTime - startNanoTime);
            logCatView.addLog("冒泡排序完毕");
            logCatView.addLog("耗时: " + timeString);
            logCatView.addLog("排序后: " + Arrays.toString(array));
        }

        return array;
    }

    /**
     * 归并排序
     * <p>
     * 时间复杂度 ---> O(n)
     * 空间复杂度 ---> n
     *
     * @param a
     * @param b
     * @param withLog
     * @param logCatView
     * @return
     */
    public static int[] mergeSort(int[] a, int[] b, OrderBy orderBy, boolean withLog, LogCatView logCatView) {
        if (withLog && null == logCatView) {
            withLog = false;
        }

        if (null == a || a.length == 0 || null == b || b.length == 0) {
            if (withLog) {
                logCatView.addLog("目标数组为空");
            }
            return null;
        }

        if (!isSorted(a, orderBy) || !isSorted(b, orderBy)) {
            if (withLog) {
                logCatView.addLog("归并排序需要两个数组都为有序数组!");
            }
            return null;
        }

        long startTime = System.currentTimeMillis();
        long startNanoTime = System.nanoTime();

        if (withLog) {
            logCatView.addLog("开始归并排序");
            logCatView.addLog("a数组: " + Arrays.toString(a));
            logCatView.addLog("b数组: " + Arrays.toString(b));
        }
        int[] target = new int[a.length + b.length];
        int aIndex = 0, bIndex = 0, targetIndex = 0;
        while (aIndex < a.length && bIndex < b.length) {
            long startTn = System.nanoTime();
            if (a[aIndex] <= b[bIndex]) {
                target[targetIndex] = a[aIndex];
                aIndex++;
            } else {
                target[targetIndex] = b[bIndex];
                bIndex++;
            }
            targetIndex++;
            long endTn = System.nanoTime();
            if (withLog) {
                logCatView.addLog("进行一次n操作，T(n) : " + (endTn - startTn));
            }
        }

        int[] unSortedCompleteArray = aIndex < a.length ? a : bIndex < b.length ? b : null;
        int unSortedCompleteIndex = aIndex < a.length ? aIndex : bIndex < b.length ? bIndex : -1;
        if (null != unSortedCompleteArray) {
            for (int i = unSortedCompleteIndex, length = unSortedCompleteArray.length; i < length; i++) {
                target[targetIndex] = unSortedCompleteArray[i];
            }
        }
        long endTime = System.currentTimeMillis();
        long endNanoTime = System.nanoTime();
        if (withLog) {
            String timeString = DateUtil.duration2String(endTime - startTime, endNanoTime - startNanoTime);
            logCatView.addLog("归并排序完毕");
            logCatView.addLog("消耗时间:" + timeString);
            logCatView.addLog("排序后数组:" + Arrays.toString(target));
        }
        return target;
    }


}

package ru.mkvhlv.featuretoggleservice.util;

import ru.mkvhlv.featuretoggleservice.domian.FeatureRole;

import java.util.ArrayList;
import java.util.List;

public class SortedFeature {

    public static List<FeatureRole> quickSortRoleByPriority(List<FeatureRole> roles) {
        if (roles == null || roles.size() <= 1) {
            return roles;
        }

        // Преобразуем в ArrayList для эффективного доступа по индексу,
        // если передан другой тип списка
        List<FeatureRole> list = new ArrayList<>(roles);
        quickSort(list, 0, list.size() - 1);
        return list;
    }

    private static void quickSort(List<FeatureRole> list, int low, int high) {
        if (low < high) {
            int pi = partition(list, low, high);

            quickSort(list, low, pi - 1);  // Сортировка элементов до опорного
            quickSort(list, pi + 1, high); // Сортировка элементов после опорного
        }
    }

    private static int partition(List<FeatureRole> list, int low, int high) {
        // Выбираем последний элемент в качестве опорного (pivot)
        int pivotPriority = list.get(high).getPriority();
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            // Если приоритет текущего элемента меньше или равен опорному
            if (list.get(j).getPriority() <= pivotPriority) {
                i++;
                // Меняем элементы местами
                FeatureRole temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }

        // Ставим опорный элемент на его законное место
        FeatureRole temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);

        return i + 1;
    }
}

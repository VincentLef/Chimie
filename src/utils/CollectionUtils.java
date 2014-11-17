package utils;

import java.util.List;

public class CollectionUtils {

    public static <E> boolean containsSameElements(List<E> list1, List<E> list2) {
        if(list1.size() != list2.size()) {
            return false;
        }
        
        for(E element : list1) {
            if(!list2.contains(element)) {
                return false;
            }
        }
        
        return true;
    }
}

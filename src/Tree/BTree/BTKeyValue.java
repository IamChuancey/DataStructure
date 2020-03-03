package Tree.BTree;

public class BTKeyValue<K extends Comparable<K>, V> {

    protected K mKey;
    protected V mValue;

    public BTKeyValue(K mKey, V mValue) {
        super();
        this.mKey = mKey;
        this.mValue = mValue;
    }
}



package pro.ianchen;


/**
 * 自定义键值对
 * custom key value pair
 * @param <K> 键 key
 * @param <V> 值 value
 */
public class IanKeyPair<K,V> {
    /**
     * 构造函数
     * constructor
     */
    public IanKeyPair(){

    }

    /**
     * 构造函数
     * constructor
     * @param k 键 key
     * @param v 值 value
     */
    public IanKeyPair(K k,V v){
        this.key=k;
        this.value=v;
    }
    /**
     * 键 key
     */
    public K key;

    /**
     * 获取键
     * get key
     * @return key
     */
    public K getKey(){
        return this.key;
    }
    /**
     * 值 value
     */
    public V value;

    /**
     * 获取值
     * get value
     * @return value
     */
    public V getValue(){
        return this.value;
    }
}


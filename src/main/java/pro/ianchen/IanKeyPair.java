package pro.ianchen;


/**
 * 自定义键值对
 * @param <K> 键
 * @param <V> 值
 */
public class IanKeyPair<K,V> {
    /**
     * 构造函数
     */
    public IanKeyPair(){

    }

    /**
     * 构造函数
     * @param k 键
     * @param v 值
     */
    public IanKeyPair(K k,V v){
        this.key=k;
        this.value=v;
    }
    /**
     * 键
     */
    public K key;

    /**
     * 获取键
     * @return
     */
    public K getKey(){
        return this.key;
    }
    /**
     * 值
     */
    public V value;

    /**
     * 获取值
     * @return
     */
    public V getValue(){
        return this.value;
    }
}


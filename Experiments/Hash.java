import java.util.HashMap;

public class Hash {

    public static void main(String[] args) {
        HashMap<Integer,String> hashMap = new HashMap<>();
        hashMap.put(1,"123");
        hashMap.put(1,"222");//直接把上一个冲掉了overwrite
        hashMap.put(3,null);

        hashMap.put(2,"abc");
        System.out.println(hashMap.get(1));
        System.out.println(hashMap.containsKey(3));
        System.out.println("abc"=="abcc");
    }
}


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class KaratsubaTask{

    public long execute(String x, String y){
        if(x.length() == 1 || y.length() == 1){
            var intX = Integer.parseInt(x);
            var intY = Integer.parseInt(y);
            return (long) intX * intY;
        }else{
            var n = Math.max(x.length(), y.length());
            var power = n / 2;
            var lowerHalfX = getLowerHalf(x); // a
            var upperHalfX = getUpperHalf(x); // b
            var lowerHlafY = getLowerHalf(y); // c
            var upperHalfY = getUpperHalf(y); // d
            var ac = execute(lowerHalfX, lowerHlafY);
            var bd = execute(upperHalfX, upperHalfY);
            int aPlusb = Integer.parseInt(lowerHalfX) + Integer.parseInt(upperHalfX);
            int cPlusd = Integer.parseInt(lowerHlafY) + Integer.parseInt(upperHalfY);
            var adPlusBc = execute(Integer.toString(aPlusb), Integer.toString(cPlusd)) - ac - bd;
            return (long) (ac * Math.pow(10, power * 2) + adPlusBc * Math.pow(10, power) + bd);
        }
    }

    private String getUpperHalf(String number) {
        if(number.length() % 2 == 0){
            return number.substring(number.length() / 2);
        }else {
            return number.substring(number.length() / 2 + 1);
        }
    }

    private String getLowerHalf(String number) {
        if(number.length() % 2 == 0){
            return number.substring(0, number.length() / 2);
        }else {
            return number.substring(0, number.length() / 2 + 1);
        }
    }
}

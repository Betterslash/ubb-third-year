import lombok.ToString;

import java.util.Objects;

@ToString
public class Main {
    private void checkIfJsonable(Object object){
        if(Objects.isNull(object)){
            throw new RuntimeException("Null obj isn't serializable ...");
        }
        var clazz = object.getClass();
        if(!clazz.isAnnotationPresent(Jsonable.class)){
            throw new RuntimeException("Object is not jsonable ...");
        }
    }
    public static void main(String[] args) {
        CarTagsGenerator.generate(null);
        CarTagChecker.checkFile(null);

    }
}

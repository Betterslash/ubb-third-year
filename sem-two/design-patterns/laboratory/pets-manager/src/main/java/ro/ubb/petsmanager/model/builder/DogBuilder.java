package ro.ubb.petsmanager.model.builder;

import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.ubb.petsmanager.model.Dog;

@Setter
@NoArgsConstructor
public final class DogBuilder extends AnimalBuilder<Dog> {
    private String furColor;
    private Long weight;
    private Long height;


    public DogBuilder height(Long height) {
        this.height = height;
        return this;
    }

    public DogBuilder weight(Long weight) {
        this.weight = weight;
        return this;
    }

    public DogBuilder furColor(String furColor) {
        this.furColor = furColor;
        return this;
    }

    public Dog build() {
        var result = new Dog(furColor, weight, height);
        completeInformation(result);
        return result;
    }
}

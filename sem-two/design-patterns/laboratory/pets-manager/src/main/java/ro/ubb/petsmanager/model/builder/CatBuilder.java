package ro.ubb.petsmanager.model.builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ro.ubb.petsmanager.model.Cat;

@Getter
@NoArgsConstructor
public final class CatBuilder extends AnimalBuilder<Cat> {

    private String furColor;
    private String favouriteFood;

    public CatBuilder furColor(String furColor) {
        this.furColor = furColor;
        return this;
    }

    public CatBuilder favouriteFood(String favouriteFood) {
        this.favouriteFood = favouriteFood;
        return this;
    }

    @Override
    public Cat build() {
        var result = new Cat(furColor, favouriteFood);
        completeInformation(result);
        return result;
    }
}

import {AnimalModel} from "../../core/model/AnimalModel";

export abstract class CustomViewModel {
    public animal: AnimalModel;
    protected id: string;
    public abstract setViewData(): void;

    protected constructor(animal: AnimalModel) {
        this.id = animal.id;
        this.animal = animal;
    }
}

export class TableView extends CustomViewModel {
    race!: string;
    name!: string;
    age!: string;

    constructor(animal: AnimalModel) {
        super(animal);
        this.setViewData();
    }

    public setViewData() {
        this.race = this.animal.race;
        this.age = this.animal.age;
        this.name = this.animal.name;
    }
}

export class CardView extends CustomViewModel {
    race!: string;
    name!: string;
    age!: string;
    imageUrls!:string[];

    constructor(animal: AnimalModel) {
        super(animal);
        this.setViewData();
    }

    public setViewData() {
        this.race = this.animal.race;
        this.age = this.animal.age;
        this.name = this.animal.name;
        this.imageUrls = this.animal.imageUrls;
    }
}

import {AnimalModel, CatModel, DogModel} from "../../core/model/AnimalModel";

export class AnimalMap {
    private static Dog = 'Dog';
    private static Cat = 'Cat';
    private representation!: Map<string, AnimalModel[]>;

    constructor(cats: AnimalModel[], dogs: AnimalModel[]) {
        this.representation = new Map<string, AnimalModel[]>();
        this.representation.set(AnimalMap.Dog, dogs);
        this.representation.set(AnimalMap.Cat, cats);
    }

    public setCats = (cats: CatModel[]): void => {
        this.representation.set(AnimalMap.Cat, cats);
    }

    public setDogs = (dogs: DogModel[]): void => {
        this.representation.set(AnimalMap.Dog, dogs);
    }

    public getDogs = (): DogModel[] => {
        const animalsList = this.representation.get(AnimalMap.Dog) || [] as AnimalModel[];
        return animalsList.map(e => e as DogModel);
    }

    public getCats = (): CatModel[] => {
        const animalsList = this.representation.get(AnimalMap.Cat) || [] as AnimalModel[];
        return animalsList.map(e => e as CatModel);
    }

    public getList = () : AnimalModel[] => {
        return this.getCats().map(q => q as AnimalModel)
            .concat(this.getDogs());
    }
}


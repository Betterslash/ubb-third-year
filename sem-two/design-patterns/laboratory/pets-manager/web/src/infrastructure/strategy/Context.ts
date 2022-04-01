import {AnimalModel, CatModel, DogModel} from "../../core/model/AnimalModel";
import {AxiosResponse} from "axios";
import {DogService} from "../services/DogService";
import {CatService} from "../services/CatService";

export class Context {

    private strategy: Strategy;


    constructor(strategy: Strategy) {
        this.strategy = strategy;
    }

    public setStrategy(strategy: Strategy) {
        this.strategy = strategy;
    }

    public execute(animal: AnimalModel): Promise<AxiosResponse<AnimalModel>> {
        return this.strategy.doAlgorithm(animal);
    }
}

export interface Strategy {
    doAlgorithm(animal: AnimalModel): Promise<AxiosResponse<AnimalModel>>;
}


export class AddDog implements Strategy{
    doAlgorithm(animal: AnimalModel): Promise<AxiosResponse<DogModel>> {
        return DogService.addDog(animal as DogModel);
    }

}

export class AddCat implements Strategy{
    doAlgorithm(animal: AnimalModel): Promise<AxiosResponse<CatModel>> {
        return CatService.addCat(animal as CatModel);
    }
}
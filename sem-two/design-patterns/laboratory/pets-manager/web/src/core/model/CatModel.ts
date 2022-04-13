import {AnimalModel} from "./AnimalModel";

export interface CatModel extends AnimalModel {
    furColor: string,
    favouriteFood: string
}
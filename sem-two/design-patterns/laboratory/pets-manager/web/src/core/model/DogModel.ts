import {AnimalModel} from "./AnimalModel";

export interface DogModel extends AnimalModel {
    furColor: string,
    weight: number,
    height: number
}
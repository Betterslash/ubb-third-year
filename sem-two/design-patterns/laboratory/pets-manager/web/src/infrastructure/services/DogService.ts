import axios, {AxiosResponse} from "axios";
import {DogModel} from "../../core/model/AnimalModel";
import {Environment} from "../../core/environment/Environment";

export class DogService{
    private static ENDPOINT = 'dogs';
    public static getAll = (): Promise<AxiosResponse<DogModel[]>> =>{
        return axios.get<DogModel[]>(`${Environment.apiUrl}${DogService.ENDPOINT}`);
    }
}
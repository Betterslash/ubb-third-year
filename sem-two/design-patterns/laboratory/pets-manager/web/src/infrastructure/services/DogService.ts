import axios, {AxiosResponse} from "axios";
import {Environment} from "../../core/environment/Environment";
import {DogModel} from "../../core/model/DogModel";

export class DogService{
    private static ENDPOINT = 'dogs';
    public static getAll = (): Promise<AxiosResponse<DogModel[]>> =>{
        return axios.get<DogModel[]>(`${Environment.apiUrl}${DogService.ENDPOINT}`);
    }
    public static addDog = (dog: DogModel): Promise<AxiosResponse<DogModel>> => {
        return axios.post<DogModel>(`${Environment.apiUrl}${DogService.ENDPOINT}`, dog);
    }
    public static getById = (id: string): Promise<AxiosResponse<DogModel>> => {
        return axios.get<DogModel>(`${Environment.apiUrl}${DogService.ENDPOINT}/${id}`);
    }
}
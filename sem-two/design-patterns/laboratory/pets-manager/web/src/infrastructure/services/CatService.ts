import axios, {AxiosResponse} from "axios";
import {Environment} from "../../core/environment/Environment";
import {CatModel} from "../../core/model/CatModel";

export class CatService{
    private static ENDPOINT = 'cats';
    public static getAll = (): Promise<AxiosResponse<CatModel[]>> =>{
        return axios.get<CatModel[]>(`${Environment.apiUrl}${CatService.ENDPOINT}`);
    }
    public static addCat = (cat: CatModel): Promise<AxiosResponse<CatModel>> => {
        return axios.post<CatModel>(`${Environment.apiUrl}${CatService.ENDPOINT}`, cat);
    }
    public static getById = (id: string): Promise<AxiosResponse<CatModel>> => {
        return axios.get<CatModel>(`${Environment.apiUrl}${CatService.ENDPOINT}/${id}`);
    }
}
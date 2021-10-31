import axios from "axios";
import {Environment} from "../environment/Environment";

export class PokemonUtilsService{
    public static async ping(){
        return axios.get(`${Environment.apiUrl}utils`);
    }
}

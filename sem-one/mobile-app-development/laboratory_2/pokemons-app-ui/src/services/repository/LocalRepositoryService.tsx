import {PokemonModel} from "../../model/PokemonModel";
import {Storage} from "@capacitor/storage";
import {Logger} from "../../helpers/logger/Logger";
import {Environment} from "../../environment/Environment";
import {StorageService} from "../StorageService";
import axios from "axios";

export class LocalRepositoryService {
    private static internalStorage : PokemonModel[] = [];
    private static internalCurrentId : number = 0;
    private static POKEMONS_API = `${Environment.apiUrl}pokemons/synchronize`


    public static reinitializeRepository = () => {
        (async (): Promise<void> => {
            await Storage.set({key: 'repository', value: ''});
            LocalRepositoryService.internalStorage = [] as PokemonModel[];
        })();
    }

    public static async getAllPokemons(){
        Logger.info(LocalRepositoryService.name + ' -> ' + this.getAllPokemons.name);
        return new Promise<PokemonModel[]>(resolve => resolve(LocalRepositoryService.internalStorage));

    }

    public static async insertOnePokemon(pokemon : PokemonModel){
        pokemon.id = this.internalCurrentId;
        this.internalCurrentId ++;
        this.internalStorage.push(pokemon);
        await Storage.set({key : 'repository', value: JSON.stringify(LocalRepositoryService.internalStorage)});
        Logger.info(LocalRepositoryService.name + ' -> ' + this.insertOnePokemon.name);
    }

    public static async getOneById(id : number){
        const choice = LocalRepositoryService.internalStorage.filter(e => e.id === id);
        if(choice != null && choice.length > 0){
            Logger.info(LocalRepositoryService.name + ' -> ' + this.getOneById.name);
            return choice[0];
        }else {
            Logger.danger(`${LocalRepositoryService.name} -> ${this.getOneById.name} -> Cannot modify a pokemon that doesn't exist`);
            throw new Error("Cannot modify a pokemon that doesn't exist");
        }
    }

    public static async deleteOne(id : number){
        const response = LocalRepositoryService.internalStorage.filter(e => e.id === id)[0];
        LocalRepositoryService.internalStorage = LocalRepositoryService.internalStorage.filter(e => e.id !== id);
        await Storage.set({key : 'repository', value : JSON.stringify(LocalRepositoryService.internalStorage)});
        Logger.info(LocalRepositoryService.name + ' -> ' + this.deleteOne.name);
        return response;
    }
    public static async synchronize(){
        return Storage.get({key : 'repository'})
            .then(async result => {
                if(result.value){
                    LocalRepositoryService.internalStorage = JSON.parse(result.value);
                }
                if (LocalRepositoryService.internalStorage.length > 0) {
                    const token = await StorageService.getToken();
                    const headerValue = 'Bearer : ' + token.value;
                    const items = LocalRepositoryService.internalStorage;
                    Logger.info('Synchronized data ...');
                    return axios.post<PokemonModel[]>(this.POKEMONS_API, items, {headers: {'Authorization': headerValue}});
                }
            });

    }
}

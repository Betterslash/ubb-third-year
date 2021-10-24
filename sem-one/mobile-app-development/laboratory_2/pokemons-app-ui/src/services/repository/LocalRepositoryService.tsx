import {PokemonModel} from "../../model/PokemonModel";
import {Storage} from "@capacitor/storage";
import {Logger} from "../../helpers/logger/Logger";

export class LocalRepositoryService {
    private static internalStorage : PokemonModel[] = [];

    private static initializeRepository = () => {
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
}

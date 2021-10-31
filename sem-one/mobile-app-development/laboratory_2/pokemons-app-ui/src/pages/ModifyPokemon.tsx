import React, {useEffect} from "react";
import {PokemonType} from "../model/PokemonModel";
import {
    IonButton, IonCard, IonCardContent, IonCheckbox,
    IonContent,
    IonDatetime, IonImg,
    IonInput, IonItem,
    IonLabel,
    IonLoading,
    IonPage, IonSelect,
    IonSelectOption
} from "@ionic/react";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {useInitialState} from "../hooks/PokemonModifyHook";
import {useHistory, useParams} from "react-router";
import {PokemonOnlineService} from "../services/PokemonOnlineService";
import {Logger} from "../helpers/logger/Logger";
import {PokemonEvolutionInfo} from "../components/widgets/PokemonEvolutionInfo";
import {useNetowrk} from "../hooks/AppHooks";
import {LocalRepositoryService} from "../services/repository/LocalRepositoryService";

export interface TypeIdPair {
    name: string;
    id: number;
}

export interface ModifyPokemonProps {
    id?: string;
}

export const ModifyPokemon: React.FC = () => {
    let index = -1;
    const choices = Object.keys(PokemonType)
        .map(key => PokemonType[Number(key)])
        .filter(e => e !== undefined)
        .map(s => {
            index += 1;
            return {name: s, id: index} as TypeIdPair
        });
    const history = useHistory();
    const {id} = useParams<ModifyPokemonProps>();
    const initalState = useInitialState(id);
    const network = useNetowrk().connected;
    useEffect(() => {
        if(!network){
            Logger.warning(ModifyPokemon.name + ' -> offline mode');
        }
    }, [network]);
    const onSubmit = (e: any) => {
        e.preventDefault();
        if(network){
            if(id === undefined) {
                PokemonOnlineService.updateOnePokemon(-1, initalState.pokemonReducer.pokemon)
                    .then(() => {Logger.info(ModifyPokemon.name + ' -> ' + onSubmit.name); history.push("/");});
            }else {
                PokemonOnlineService.updateOnePokemon(Number(id), initalState.pokemonReducer.pokemon)
                    .then(() => {Logger.info(ModifyPokemon.name + ' -> ' + onSubmit.name); history.push("/");});
            }
        }else{
            LocalRepositoryService.insertOnePokemon(initalState.pokemonReducer.pokemon)
                .then(() => {Logger.info(ModifyPokemon.name + ' -> ' + onSubmit.name);  history.push("/");})
        }
    };
    return (
        <IonPage>
            <IonLoading isOpen={initalState.fetching}/>
            <Header/>
            <IonContent>
                <IonCardContent>
                    <form onSubmit={onSubmit}>
                        <IonCard>
                            <IonCardContent>
                                <IonLabel>
                                    Name : <IonInput disabled={initalState.viewOnly}
                                                     value={initalState.pokemonReducer.pokemon.name} type="text"
                                                     onIonChange={(e) => {
                                                         initalState.pokemonReducer.dispatcher({item: e, type: 'NAME'})
                                                     }} required/>
                                </IonLabel>
                                <IonLabel>
                                    Type : <IonSelect disabled={initalState.viewOnly}
                                                      onIonChange={(e) => {
                                                          initalState.pokemonReducer.dispatcher({item: e, type: 'TYPE_ONE'})
                                                      }}>
                                    {choices.map(q => <IonSelectOption key={q.id} value={q.id}>{q.name}</IonSelectOption>)}
                                </IonSelect>
                                </IonLabel>
                                <IonLabel>
                                    Updated at :
                                    <IonDatetime disabled={initalState.viewOnly}
                                                 displayFormat="YYYY-MM-DD"
                                                 min="1994-03-14"
                                                 max="2021-12-19" onIonChange={(e) => {
                                        initalState.pokemonReducer.dispatcher({item: e, type: 'REGISTERED_AT'})
                                    }}/>
                                </IonLabel>
                                <IonLabel>Catch Rate :
                                    <IonInput disabled={initalState.viewOnly}
                                              required max="100" min="0" value={initalState.pokemonReducer.pokemon.catchRate}
                                              type="number"
                                              onIonChange={(e) => {
                                                  initalState.pokemonReducer.dispatcher({item: e, type: 'CATCH_RATE'})
                                              }}/>
                                </IonLabel>
                                <IonLabel>
                                    Has Shiny : <IonItem color={'rgba(0,0,255,0)'} ><IonCheckbox disabled={initalState.viewOnly}
                                                                                                 checked={initalState.pokemonReducer.pokemon.hasShiny}
                                                                                                 placeholder="Has Shiny"
                                                                                                 onIonChange={(e) => {
                                                                                                     initalState.pokemonReducer.dispatcher({
                                                                                                         item: e,
                                                                                                         type: 'HAS_SHINY'
                                                                                                     })
                                                                                                 }}/></IonItem>
                                </IonLabel>

                                <IonButton disabled={initalState.viewOnly}
                                           expand="block" type="submit">Modify</IonButton>
                            </IonCardContent>
                        </IonCard>
                    </form>
                </IonCardContent>
                <PokemonEvolutionInfo pokemonId={initalState.pokemonReducer.pokemon.evolvesFrom}/>
                <IonImg src={"../assets/bulbasaur.png"}>
                </IonImg>
            </IonContent>
            <Footer/>
        </IonPage>
    );
};

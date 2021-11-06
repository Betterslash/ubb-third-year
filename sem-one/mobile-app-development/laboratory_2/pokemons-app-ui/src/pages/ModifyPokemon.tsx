import React, {useEffect, useState} from "react";
import {PokemonType} from "../model/PokemonModel";
import {
    IonButton, IonButtons, IonCard, IonCardContent, IonCheckbox, IonContent,
    IonDatetime, IonIcon, IonImg,
    IonInput, IonItem,
    IonLabel,
    IonLoading,
    IonPage, IonRow, IonSelect,
    IonSelectOption, IonToolbar
} from "@ionic/react";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {useInitialState} from "../hooks/PokemonModifyHook";
import {useHistory, useParams} from "react-router";
import {PokemonOnlineService} from "../services/PokemonOnlineService";
import {Logger} from "../helpers/logger/Logger";
import {PokemonEvolutionInfo} from "../components/widgets/PokemonEvolutionInfo";
import {useNetowrk, usePhotoHook} from "../hooks/AppHooks";
import {LocalRepositoryService} from "../services/repository/LocalRepositoryService";
import {camera, trash} from "ionicons/icons";
import {ImageService} from "../services/ImageService";
import {AxiosResponse} from "axios";
import {Environment} from "../environment/Environment";

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
    const photo = usePhotoHook();
    const [currentImagePath, setCurrentImagePath] = useState('');
    useEffect(() => {
        if(!network){
            Logger.warning(ModifyPokemon.name + ' -> offline mode');
        }else{
            getPokemonImageFromServer();
        }
    }, [network,]);

    const updatePokemonRequest = (imageId?: AxiosResponse<string>) =>{
        const futurePokemon = initalState.pokemonReducer.pokemon;
        if(imageId && imageId.data){
            futurePokemon.photoPath = imageId.data;
            Logger.info(`${ModifyPokemon.name} -> ${ImageService.uploadImage.name} -> imageId : ${JSON.stringify(imageId.data)}`);
        }
        if (id === undefined) {
            PokemonOnlineService.updateOnePokemon(-1, futurePokemon)
                .then(() => {
                    Logger.info(ModifyPokemon.name + ' -> ' + onSubmit.name);
                    history.push("/");
                });
        } else {
            PokemonOnlineService.updateOnePokemon(Number(id), futurePokemon)
                .then(() => {
                    Logger.info(ModifyPokemon.name + ' -> ' + onSubmit.name);
                    history.push("/");
                });
        }
    }

    const onSubmit = (e: any) => {
        e.preventDefault();
        if(network){
            const photoData = new FormData();
            const pokemon = initalState.pokemonReducer.pokemon;
            getCurrentPhoto().then(result => {
                if(result){
                    const consumable : any = (result as Blob);
                    consumable.name = `${pokemon.name}_${pokemon.id}.png`;
                    consumable.lastModifiedDate = new Date();
                    photoData.set('file', consumable, consumable.name);
                    ImageService.uploadImage(photoData)
                        .then( (imageId) => {
                            updatePokemonRequest(imageId);
                        })
                        .catch(err => {
                            Logger.danger(ModifyPokemon.name + ' -> ' + err);});
                }else{
                    updatePokemonRequest();
                    Logger.warning(`No image to be uploaded`);
                }
            });
        }else{
            LocalRepositoryService.insertOnePokemon(initalState.pokemonReducer.pokemon)
                .then(() => {Logger.info(ModifyPokemon.name + ' -> ' + onSubmit.name);  history.push("/");})
        }
    };

    const getCurrentPhoto = async () => {
        const currentPhotoPath = initalState.pokemonReducer.pokemon.photoPath;
        if (currentPhotoPath) {
            const stored = await photo.getPhoto(currentPhotoPath);
            if(stored){
               return await (await fetch(currentPhotoPath)).blob();
            }else{
               return null;
            }
        }
    }

    const getPokemonImageFromServer = async () => {
        Logger.info(ModifyPokemon.name + ' -> ' + getPokemonImageFromServer.name);
        if(id != null && id !== '' && id !== '-1' && id !== undefined){
            const pokemonImagePath = (await PokemonOnlineService.getOneById(Number(id))).data?.photoPath;
            if(pokemonImagePath){
                Logger.info(ModifyPokemon.name + ' -> success ');
                setCurrentImagePath(`${Environment.imageUploadUrl}/${pokemonImagePath}`);

            }else{
                Logger.info(ModifyPokemon.name + ' -> no image found');
                setCurrentImagePath('');
            }
        }
    }

    const deleteCurrentImage = () => {
        setCurrentImagePath('');
        initalState.pokemonReducer.dispatcher({item: '', type: 'PHOTO_PATH'});
    }
    
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
                                    Has Shiny : <IonRow color={'rgba(0,0,255,0)'} ><IonCheckbox disabled={initalState.viewOnly}
                                                                                                 checked={initalState.pokemonReducer.pokemon.hasShiny}
                                                                                                 placeholder="Has Shiny"
                                                                                                 onIonChange={(e) => {
                                                                                                     initalState.pokemonReducer.dispatcher({
                                                                                                         item: e,
                                                                                                         type: 'HAS_SHINY'
                                                                                                     })
                                                                                                 }}/></IonRow>
                                </IonLabel>
                                <br/>
                                <IonLabel>
                                    Photo :
                                </IonLabel>
                                <IonButtons>
                                    <IonButton onClick={async () => {photo.takePhoto().then(result =>  {
                                        if(result?.webviewPath){
                                            initalState.pokemonReducer
                                                .dispatcher({item: result?.webviewPath, type: 'PHOTO_PATH'});
                                            setCurrentImagePath(result.webviewPath);
                                        }
                                    });
                                       }}>
                                        <IonIcon icon={camera}/>
                                    </IonButton>
                                </IonButtons>
                                    {  currentImagePath &&
                                    <IonCard key={initalState.pokemonReducer.pokemon.id+"_container"}>
                                            <IonToolbar>
                                                <IonButtons slot={"start"}>
                                                    <IonButton onClick={deleteCurrentImage}>
                                                        <IonIcon icon={trash}/>
                                                    </IonButton>
                                                </IonButtons>
                                            </IonToolbar>
                                            <IonItem key={initalState.pokemonReducer.pokemon.id + "_item"}>
                                                <IonImg src={currentImagePath}/>
                                            </IonItem>
                                        </IonCard>
                                    }
                                <br/>
                                <IonButton disabled={initalState.viewOnly}
                                           expand="block" type="submit">Modify</IonButton>
                            </IonCardContent>
                        </IonCard>
                    </form>
                </IonCardContent>
                <PokemonEvolutionInfo pokemonId={initalState.pokemonReducer.pokemon.evolvesFrom}/>
                <IonImg src={"../assets/bulbasaur.png"}/>
            </IonContent>
            <Footer/>
        </IonPage>
    );
};

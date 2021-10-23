import React, {useEffect, useState} from "react";
import {IonContent, IonFab, IonFabButton, IonIcon, IonItem, IonList, IonListHeader, IonLoading} from "@ionic/react";
import {useNetowrk} from "../../hooks/AppHooks";
import {PokemonsService} from "../../services/PokemonsService";
import {PokemonModel} from "../../model/PokemonModel";
import {add} from "ionicons/icons";

export const Pokedex : React.FC = () => {
    const [fetching, setFetching] = useState(false);
    const network = useNetowrk();
    const fetchable = network.connected;
    const [pokemons, setPokemons] = useState([] as PokemonModel[]);

    useEffect(() => {
        if(fetchable){
            PokemonsService.getAllPokemons()
                .then(result => {
                    setPokemons(result.data);
                    setFetching(false)
                });
        }else{
            setPokemons([] as PokemonModel[]);
            setFetching(false);
        }
        console.log(fetchable);
    }, [fetchable])

    return (
        <IonContent>
            <IonLoading isOpen={fetching}/>
            <IonListHeader>Available Pokemons</IonListHeader>
            <IonList>
                {pokemons.map(e =>  <IonItem key={e.id}>
                    {e.name}
                </IonItem>)}
            </IonList>
            <IonFab>
                <IonFabButton>
                    <IonIcon icon={add}/>
                </IonFabButton>
            </IonFab>
        </IonContent>);
}

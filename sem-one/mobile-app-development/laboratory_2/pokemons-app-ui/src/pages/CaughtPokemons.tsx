import React, {useEffect, useState} from "react";
import {
    IonButton,
    IonButtons,
    IonContent, IonIcon,
    IonImg,
    IonItem,
    IonList,
    IonListHeader,
    IonPage,
    IonTitle
} from "@ionic/react";
import {PokemonModel} from "../model/PokemonModel";
import {PokemonOnlineService} from "../services/PokemonOnlineService";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {remove} from "ionicons/icons";

export const CaughtPokemons : React.FC = () => {
    const [pokemons, setPokemons] = useState([] as PokemonModel[]);
    useEffect(() => {
        PokemonOnlineService.getMyPokemons()
            .then(result => {
               setPokemons(result.data);
            });
    }, []);
    return (
        <IonPage>
            <Header/>
                <IonContent>
                    <IonListHeader>
                        <IonTitle>My Pokemons</IonTitle>
                    </IonListHeader>
                    <IonList>
                        {pokemons.map(e => {return (
                            <IonItem key={e.id}>
                                <IonImg src={`../assets/pokemon_types/${e.types.typeOne.toLowerCase()}.png`} slot="start"/>
                                {e.name}
                                <IonButtons slot="end">
                                    <IonButton>
                                        <IonIcon icon={remove}/>
                                    </IonButton>
                                </IonButtons>
                            </IonItem>
                        );})}
                    </IonList>
                </IonContent>
            <Footer/>
        </IonPage>
    );
}

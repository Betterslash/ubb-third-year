import {
    IonButtons,
    IonCard, IonCardContent,
    IonFabButton, IonIcon, IonImg, IonPage, IonToolbar
} from '@ionic/react';
import './Home.css';
import {Header} from "../components/layout/Header";
import React from "react";
import {Footer} from "../components/layout/Footer";
import {Pokedex} from "../components/widgets/Pokedex";
import {useUserState} from "../hooks/AppHooks";
import {GuestHome} from "../components/widgets/GuestHome";
import {add} from "ionicons/icons";
import {useHistory} from "react-router";

const Home: React.FC = () => {
    const userState = useUserState();
    const history = useHistory();
    const navigateToModify = (id: number | string) => {
        history.push(`/pokemon/${id}`)
    }
    const isUserAdmin = () : boolean => {
        return userState.authorities.filter(e => {return e.includes('ROLE_GYM_LEADER') || e.includes('GYM_LEADER')}).length > 0;
    }
    const loggedIn = userState.token !== "";
    const getHomeItems = () => {
        if(!loggedIn){
            return <GuestHome/>
        }else{
            return <Pokedex token={userState.token}/>
        }
    }
    return (
    <IonPage>
        <Header/>
        {getHomeItems()}
        {loggedIn &&
        <IonCard>
            <IonCardContent style={{paddingTop : '0px', paddingBottom: '0px'}}>
                <IonToolbar color={'rgba(0,0,255,0)'}>
                    <IonButtons slot="secondary">
                        {isUserAdmin() &&
                        <IonFabButton size="small" slot="start">
                            <IonIcon onClick={() => navigateToModify("")} icon={add}/>
                        </IonFabButton>
                        }
                    </IonButtons>
                    <img src={"../assets/logo.png"} alt={"logo.png"} sizes={"maxWidth:100, maxHeigth: 60"}/>
                </IonToolbar>
            </IonCardContent>
        </IonCard>}
        <Footer/>
    </IonPage>
  );
};

export default Home;

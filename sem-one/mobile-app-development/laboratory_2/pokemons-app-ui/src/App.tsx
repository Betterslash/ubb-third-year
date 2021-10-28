import { Redirect, Route } from 'react-router-dom';
import {IonApp, IonRouterOutlet} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import Home from './pages/Home';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import React, {useState} from "react";
import {Login} from "./pages/Login";
import {ModifyPokemon} from "./pages/ModifyPokemon";
import {ProtectedRoute} from "./guards/ProtectedRoute";
import {CaughtPokemons} from "./pages/CaughtPokemons";
import {AppContext} from "./context/AppContext";
import {NotificationModel} from "./model/NotificationModel";
import {Register} from "./pages/Register";
import {MyProfile} from "./pages/MyProfile";
const App: React.FC = () => {
  const [notifications, setNotifications] = useState([] as NotificationModel[]);

  return (
      <IonApp>
        <IonReactRouter>
          <IonRouterOutlet>
            <AppContext.Provider value={{notifications: notifications, stateChange : setNotifications}}>
              <Route exact path="/home">
                <Home />
              </Route>
              <Route exact path="/">
                <Redirect to="/home" />
              </Route>
              <Route exact path="/login">
                <Login/>
              </Route>
              <ProtectedRoute path="/pokemon/:id" ProtectedComponent={ModifyPokemon} />
              <ProtectedRoute path="/pokemon" ProtectedComponent={ModifyPokemon} />
              <ProtectedRoute path="/my-pokemons" ProtectedComponent={CaughtPokemons}/>
              <ProtectedRoute path={"/my-profile"} ProtectedComponent={MyProfile}/>
              <Route exact path={"/register"}>
                <Register/>
              </Route>
            </AppContext.Provider>
          </IonRouterOutlet>
        </IonReactRouter>
      </IonApp>
  );
}

export default App;

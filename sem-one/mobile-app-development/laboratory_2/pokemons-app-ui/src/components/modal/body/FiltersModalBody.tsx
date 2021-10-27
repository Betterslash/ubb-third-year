import {
    IonButton,
    IonButtons, IonCard, IonCardContent, IonContent,
    IonHeader, IonIcon, IonImg, IonInput, IonLabel, IonSelect, IonSelectOption,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import React, {useRef, useState} from "react";
import {add, closeCircle} from "ionicons/icons";
import {PokemonType} from "../../../model/PokemonModel";
import {TypeIdPair} from "../../../pages/ModifyPokemon";

export interface FilterModel{
    field : string;
    comparator: string;
    value: string;
}

export const FiltersModalBody: React.FC<{
    onDismiss: () => void;
    addFilter: (filter: FilterModel) => void;
}> = ({ onDismiss, addFilter}) => {
    const filterComparatorForm = useRef(null);
    let index= -1;
    const choices = Object.keys(PokemonType)
        .map(key => PokemonType[Number(key)])
        .filter(e => e !== undefined)
        .map(s => {
            index += 1;
            return {name: s, id: index} as TypeIdPair
        });
    const possibleFields = ['Type', 'Catch Rate'];
    const typeComparators = ['is', "isn't"];
    const catchRateComaparator = ['bigger than', 'lower than', 'equal to'];
    const [filterModel, setFilterModel] = useState({
        field:'',
        comparator:'',
        value:''
    } as FilterModel);
    const changeField = (e: any) => {
      const changedFilter = {
          field: e.detail.value,
          comparator: '',
          value: ''
      } as FilterModel;
      if(filterComparatorForm.current != null){
          // @ts-ignore
          filterComparatorForm.current.value = ''
      }
      setFilterModel(changedFilter);
    }

    const getPossibleComparators =() :string[]=> {
        if(filterModel.field === possibleFields[0]){
            return typeComparators;
        }
        if(filterModel.field === possibleFields[1]){
            return catchRateComaparator;
        }
        return [];
    }
    const changeComparator = (e: any) => {
        const changedFilter = {
            field: filterModel.field,
            comparator: e.detail.value,
            value: filterModel.value
        } as FilterModel;
        setFilterModel(changedFilter);
    }
    const changeValueType = (e: any) => {
        const changedFilter = {
            field: filterModel.field,
            comparator: filterModel.comparator,
            value: filterModel.field === possibleFields[0] ? PokemonType[e.detail.value] : e.detail.value
        } as FilterModel;
        setFilterModel(changedFilter);
    }

    const getPossibleTypes = ()  => {
        if(filterModel.field === possibleFields[1]){
            return <IonInput placeholder={"    Value"} type={"number"} max={"100"} min={"0"} onIonChange={changeValueType}/>
        }
        if(filterModel.field === possibleFields[0]){
            return (
                <IonSelect onIonChange={changeValueType}>
                    {choices.map(q => <IonSelectOption key={q.id} value={q.id}>{q.name}</IonSelectOption>)}
                </IonSelect>
            )
        }
        return <></>
    }

    const onSubmit = (e: any) => {
        e.preventDefault();
        addFilter(filterModel);
        onDismiss();
    }
    return(<>
            <IonHeader>
                <IonToolbar>
                    <IonTitle slot="start">Add Filter</IonTitle>
                    <IonButtons slot="end">
                        <IonButton onClick={() => onDismiss()} >
                            <IonIcon  icon={closeCircle}/>
                        </IonButton>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>
            <IonContent>
                <IonCard>
                    <IonCardContent>
                        <form onSubmit={onSubmit} >
                            <IonSelect placeholder={"Field"} onIonChange={changeField}>
                                {possibleFields.map((e,index) => (<IonSelectOption key={index} >{e}</IonSelectOption>))}
                            </IonSelect>
                            <br/>
                            <IonSelect placeholder={"Comaparison"} onIonChange={changeComparator} ref={filterComparatorForm}>
                                {getPossibleComparators().map((e,index) => (<IonSelectOption key={index} >{e}</IonSelectOption>))}
                            </IonSelect>
                            <br/>
                            {getPossibleTypes()}
                            <br/>
                            <IonButtons>
                                <IonButton type={"submit"}>
                                    <IonIcon icon={add}/> Add filter
                                </IonButton>
                            </IonButtons>
                        </form>
                    </IonCardContent>
                </IonCard>
                <IonImg src={`../assets/pikachu.png`}/>
            </IonContent>
        </>
    );
}

import React, {useEffect, useState} from "react";
import {
    createAnimation,
    IonButton, IonButtons,
    IonCard,
    IonCardContent,
    IonCol,
    IonContent,
    IonGrid, IonItem,
    IonList, IonLoading,
    IonPage,
    IonRow
} from "@ionic/react";
import {Product} from "../core/model/product.model";
import {InventoryItem} from "../core/model/inventory-item.model";
import ItemInventoryService from "../core/service/item-inventory.service";
import ProductService from "../core/service/product.service";

const initialProductsList : Product[] = [] as Product[];
const intialInventoryItemList : InventoryItem[] = [] as InventoryItem[];

export const Home : React.FC = () => {

    const [products, setProductsList] = useState(initialProductsList);

    const [inventoryItems, setInventoryItemsList] = useState(intialInventoryItemList);

    const [playSpinner, setPlaySpinner] = useState(false);

    const [currentlySelected, setCurrentlySelected] = useState({} as InventoryItem);

    const [quantityToBeAdded, setQuantityToBeAdded] = useState(0);

    useEffect(() => {
        setPlaySpinner(true);
        ItemInventoryService.getAll()
            .then(data => {
                setInventoryItemsList(data.data);
                ProductService.getAll()
                    .then(data => {
                        data.data.forEach(e => e.isSelected = false);
                        setProductsList(data.data);
                    });
                    setPlaySpinner(false);
            });

    }, []);

    const getQuantity = (id: number): number =>  {
        const product = inventoryItems.find(e => e.productId == id);
        if(product != undefined){
            return product.quantity;
        }
        return 0;
    }

    const playAnimation = (id: string): void => {
        const baseValue = '#1f1f1f';
        const product: Product = products.find(e => e.id == Number(id)) || {isSelected: false} as Product;
        if(!product.isSelected) {
            createAnimation('my-animation-identifier')
                // @ts-ignore
                .addElement(document.querySelector(`#item-${id}`))
                .duration(100)
                .keyframes([
                    {offset: 0, background: `${baseValue}`},
                    {offset: 0.72, background: 'var(--background)'},
                    {offset: 1, background: 'blue'}
                ])
                .play()
                .then(() => {
                });
        }
        products.filter(e => e.id != Number(id)).forEach(e => {
            createAnimation('my-animation-identifier')
                // @ts-ignore
                .addElement(document.querySelector(`#item-${e.id}`))
                .duration(100)
                .keyframes([
                    {offset: 1, background: `${baseValue}`}
                ])
                .play()
                .then(() => {
                });
            e.isSelected = false;
        });
        product.isSelected = true;
        const item = inventoryItems.find(e => e.productId == Number(id)) || {} as InventoryItem;
        setCurrentlySelected(item);
        setProductsList(Array.from(products));
    }

    const changeValue = (): void => {

    }

    return (
        <IonPage>
            <IonContent>
                <IonLoading isOpen={playSpinner}/>
                <IonGrid>
                   <IonRow>
                       <IonCol>
                           <IonCard>
                               <IonCardContent>
                                       <IonList>
                                           {
                                               products.map(e =>
                                                   <IonItem  key = {e.id} onClick={() => playAnimation(e.id.toString())}>
                                                       <div id={'item-' + e.id.toString()} >
                                                           Name : {e.name} | Quantity : {getQuantity(e.id)}
                                                       </div>
                                                   </IonItem>)
                                           }
                                       </IonList>
                               </IonCardContent>
                           </IonCard>
                       </IonCol>
                   </IonRow>
                </IonGrid>
                <IonCard>
                    <IonCardContent>
                        Add Quantity to Item : {currentlySelected.productId}
                        <IonRow>
                            <input onInput={changeValue} type="number"/>
                        </IonRow>
                        <IonButtons slot="start">
                            <IonButton>
                                Add
                            </IonButton>
                        </IonButtons>
                    </IonCardContent>
                </IonCard>
            </IonContent>
        </IonPage>
    );
}

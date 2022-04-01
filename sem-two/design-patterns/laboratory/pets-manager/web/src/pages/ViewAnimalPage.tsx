import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {DogService} from "../infrastructure/services/DogService";
import {CatService} from "../infrastructure/services/CatService";
import {Card, CardBody, CardImg, CardSubtitle, CardTitle, Container, Spinner} from "reactstrap";
import {CardView} from "../infrastructure/bridge/CustomViewModel";
import {Environment} from "../core/environment/Environment";

export const ViewAnimalPage: React.FC = () => {

    const {animalId, animalType} = useParams();
    const [animal, setAnimal] = useState({} as CardView);
    const [isProcessing, setIsProcessing] = useState(false);
    const [imageUrl, setImageUrl] = useState('');

    useEffect(() => {
        setIsProcessing(true);
        if(animalType === 'Dog'){
            DogService.getById(animalId || '')
                .then((res) => {
                    const cardView = new CardView(res.data);
                    setImageUrl(`${Environment.localUrl}doberman.png`);
                    setAnimal(cardView);
                    setIsProcessing(false);
                });
        }
        if(animalType === 'Cat'){
            CatService.getById(animalId || '')
                .then((res) => {
                    const cardView = new CardView(res.data);
                    setImageUrl(`${Environment.localUrl}british_shorthair.png`);
                    setAnimal(cardView);
                    setIsProcessing(false);
                });
        }
    }, []);

    return (
        <div>
            <Container>
                <Card hidden={isProcessing} >
                    <Card>
                        <CardImg src={imageUrl} height={"300px"} alt="Card image cap"/>
                        <CardBody>
                            <CardTitle>{animal.name}</CardTitle>
                            <CardSubtitle>{animal.race}</CardSubtitle>
                        </CardBody>
                    </Card>
                </Card>
                <Spinner hidden={!isProcessing}>
                    Loading
                </Spinner>
            </Container>
        </div>
    );
}